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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping("/user")
    public String mainPage(){
        return "user";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public Object getUser(HttpServletRequest request, User user){

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "");
        if (request.getSession().getAttribute("UUID") != null) {
            User userRedis = (User)redisTemplate.opsForValue().get((String) request.getSession().getAttribute("UUID"));
            user.setUserID(userRedis.getUserID());
            User userDB = userService.getUser(user);
            userDB.setPassword(null);
            if (userDB != null) {
                map.put("user", userDB);
            }
        }
        return map;
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public Object updatePassword(HttpServletRequest request, User user){

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isEmpty(user.getUserID())) {
                map.put("result", "UserIDEmpty");
                return map;
            }
            if (StringUtils.isEmpty(user.getOldPassword())) {
                map.put("result", "OldPasswordEmpty");
                return map;
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                map.put("result", "PasswordEmpty");
                return map;
            }
            map.put("result", "NotFound");
            if (request.getSession().getAttribute("UUID") != null) {
                User userRedis = (User) redisTemplate.opsForValue().get((String) request.getSession().getAttribute("UUID"));
                if (userRedis.isEnabled() && userRedis.getPassword().equals(Tools.encodePassword("MD5", user.getOldPassword(), userRedis.getUpdateDTTM()))) {
                    user.setUserID(userRedis.getUserID());
                    user.setUpdateUser(userRedis.getUserID());
                    user.setUpdateDTTM(new Date());
                    user.setPassword(Tools.encodePassword("MD5", user.getPassword(), user.getUpdateDTTM()));
                    user.setOldPassword(userRedis.getPassword());
                    int count = userService.updatePassword(user);
                    if (count > 0) {
                        map.put("result", "Success");
                    }
                } else {
                    map.put("result", "PasswordError");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "Error");
            logger.error("用户" + user.getUserID() + "修改密码异常：" + e.getMessage());
        }
        return map;
    }
}