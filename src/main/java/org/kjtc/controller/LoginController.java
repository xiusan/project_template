package org.kjtc.controller;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kjtc.entity.User;
import org.kjtc.service.UserService;
import org.kjtc.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("")
    public String login(){
        return "login";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/main")
    public String mainPage(){
        return "main";
    }

    @RequestMapping("home")
    public String homePage(){
        return "home";
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public Object loginCheck(HttpServletRequest request, HttpServletResponse response, User user){

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(user.getUserID()) || StringUtils.isEmpty(user.getPassword())) {
            map.put("result", "EmptyError");
            return map;
        }
        try {
            map.put("result", "NotFound");
            User userDB = userService.getUser(user);
            if (userDB != null) {
                String requestMD5 = Tools.encodePassword("MD5", user.getPassword(), userDB.getUpdateDTTM());
                if (userDB.isEnabled() && requestMD5.equals(userDB.getPassword())) {
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    redisTemplate.opsForValue().set(uuid, userDB, 1, TimeUnit.HOURS);
                    request.getSession().setAttribute("UUID", uuid);
                    map.put("result", "Success");
                    map.put("url", "main");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "Error");
            logger.error("用户" + user.getUserID() + "登录异常：" + e.getMessage());
        }

        return map;
    }
    @RequestMapping(value = "/getMenuData")
    @ResponseBody
    public Object getMenuData(HttpServletRequest request){

        Map<String, Object> map = new HashMap<String, Object>();
        String uuid = null;
        if (request.getSession().getAttribute("UUID") != null) {
            uuid = (String)request.getSession().getAttribute("UUID");
        }
        if (!StringUtils.isEmpty(uuid) && redisTemplate.hasKey(uuid)) {
            User user = (User)redisTemplate.opsForValue().get(uuid);
            map.put("userName", user.getUserName());
        } else {
            map.put("userName", "无效");
        }
        map.put("systemDate", new Date());
        return map;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (request.getSession().getAttribute("UUID") != null) {
                redisTemplate.delete((String)request.getSession().getAttribute("UUID"));
            }
            session.invalidate();
        }
        return "login";
    }
}