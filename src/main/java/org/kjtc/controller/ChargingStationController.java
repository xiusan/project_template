package org.kjtc.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kjtc.entity.ChargingStation;
import org.kjtc.entity.Equipment;
import org.kjtc.service.ChargingStationService;
import org.kjtc.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *
 *@program:org.kjtc.controller
 *@description: ChargingStation 充电站
 *@author: dongteng
 *@create:2018-04-16 10:09
 */
@Controller
public class ChargingStationController {

    private static Logger logger = Logger.getLogger(ChargingStationController.class);

    @Autowired
    private ChargingStationService chargingStationService;

    @RequestMapping(value = "/chargingStation")
    public String chargingStationPage(){
        return "chargingStation";
    }

    /**
     *
     * @Description: getChargingStationName
     * @Param: [chargingStation]
     * @return: java.util.List<org.kjtc.entity.ChargingStation>
     * @Author: dongteng
     * @Date:2018/05/14
     * @time:10:00
     *
     */
    @RequestMapping("/getChargingStationName")
    @ResponseBody
    public List<Object> getChargingStationName(ChargingStation chargingStation) {

        List<Object> list = new ArrayList<Object>();
        try {
            List<ChargingStation> chargingStationNameList = chargingStationService.getChargingStationName(chargingStation);
            for (ChargingStation chargingStationTemp : chargingStationNameList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("chargingStationID", chargingStationTemp.getChargingStationID());
                map.put("chargingStationName", chargingStationTemp.getChargingStationName());
                list.add(map);
            }
        } catch (Exception e) {
            logger.error("下拉列表-充电站名称查询异常:" + e.getMessage());
        }
        return list;
    }
}
