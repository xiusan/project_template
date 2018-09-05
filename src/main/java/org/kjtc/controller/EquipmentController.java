package org.kjtc.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kjtc.entity.ChargingStation;
import org.kjtc.entity.Equipment;
import org.kjtc.service.EquipmentService;
import org.kjtc.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *
 *@program:org.kjtc.controller
 *@description: equipment 充电桩
 *@author: dongteng
 *@create:2018-04-16 10:09
 */
@Controller
public class EquipmentController {

    private static Logger logger = Logger.getLogger(EquipmentController.class);

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/equipment")
    public String equipmentPage(){
        return "equipment";
    }

    /**
     *
     * @Description: getEquipmentName
     * @Param: [equipment]
     * @return: java.util.List<org.kjtc.entity.Equipment>
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @RequestMapping("/getEquipmentName")
    @ResponseBody
    public List<Object> getEquipmentName(Equipment equipment) {
        List<Object> list = new ArrayList<Object>();
        if (!StringUtils.isEmpty(equipment.getChargingStationID())) {
            try {
                List<Equipment> equipmentNameList = equipmentService.getEquipmentName(equipment);
                for (Equipment equipmentTemp : equipmentNameList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("equipmentID", equipmentTemp.getEquipmentID());
                    map.put("equipmentName", equipmentTemp.getEquipmentName());
                    list.add(map);
                }
            } catch (Exception e) {
                logger.error("下拉列表-充电桩名称查询异常:" + e.getMessage());
            }
        }
        return list;
    }

    /**
     *
     * @Description: geEquipmentList
     * @Param: [equipment, pageNum, pageSize]
     * @return: java.lang.Object
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @RequestMapping(value = "/getEquipmentList")
    @ResponseBody
    public Object getEquipmentList(Equipment equipment,
        @RequestParam(value="pageNum", defaultValue="1") Integer pageNum, @RequestParam(value="pageSize", defaultValue="10") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Equipment> equipmentList = equipmentService.getEquipmentList(equipment);

        PageInfo pageInfo = new PageInfo(equipmentList);

        return pageInfo;
    }

    /**
     *
     * @Description: saveEquipment
     * @Param: [equipment]
     * @return: java.lang.Object
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @PostMapping("/saveEquipment")
    @ResponseBody
    public Object saveEquipment(Equipment equipment) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(equipment.getChargingStationID())) {
            map.put("result", "ChargingStationIDEmpty");
            return map;
        }

        if (StringUtils.isEmpty(equipment.getEquipmentID())) {
            map.put("result", "EquipmentIDEmpty");
            return map;
        }

        try {
            equipment.setCreateUser(Tools.getUser(redisTemplate));
            equipment.setUpdateUser(Tools.getUser(redisTemplate));
            int count = equipmentService.saveEquipment(equipment);
            if (count > 0) {
                map.put("result", "Success");
            } else {
                map.put("result", "Error");
            }
        }  catch (DuplicateKeyException ex) {
            String[] error = ex.getCause().getMessage().split("'");
            if ("PRIMARY".equals(error[3].trim())) {
                map.put("result", "EquipmentIDExist");
            } else if ("EquipmentName".equals(error[3].trim())) {
                map.put("result", "EquipmentNameExist");
            } else {
                map.put("result", "DuplicateError");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "Error");
        }
        return map;
    }

    /**
     *
     * @Description: updateEquipment
     * @Param: [equipment]
     * @return: java.lang.Object
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @PostMapping("/updateEquipment")
    @ResponseBody
    public Object updateEquipment(Equipment equipment) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(equipment.getChargingStationID())) {
            map.put("result", "ChargingStationIDEmpty");
            return map;
        }

        if (StringUtils.isEmpty(equipment.getEquipmentID())) {
            map.put("result", "EquipmentIDEmpty");
            return map;
        }
        try {
            equipment.setUpdateUser(Tools.getUser(redisTemplate));
            int count = equipmentService.updateEquipment(equipment);
            if (count > 0) {
                map.put("result", "Success");
            } else {
                map.put("result", "NotFound");
            }
        }  catch (DuplicateKeyException ex) {
            String[] error = ex.getCause().getMessage().split("'");
            if ("EquipmentName".equals(error[3].trim())) {
                map.put("result", "EquipmentNameExist");
            } else {
                map.put("result", "DuplicateError");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "Error");

        }
        return map;
    }

    /**
     *
     * @Description: delEquipment
     * @Param: [equipment]
     * @return: java.lang.Object
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @RequestMapping(value = "/delEquipment")
    @ResponseBody
    public Object delEquipment(Equipment equipment) {

        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(equipment.getEquipmentID())){
            map.put("result", "EquipmentIDEmpty");
            return map;
        }
        try {
            int count = equipmentService.delEquipment(equipment);
            if (count > 0) {
                map.put("result", "Success");
            } else {
                map.put("result", "NotFound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "Error");
        }
        return map;
    }
}
