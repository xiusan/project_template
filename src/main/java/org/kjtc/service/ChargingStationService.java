package org.kjtc.service;


import org.kjtc.entity.ChargingStation;
import org.kjtc.entity.Equipment;
import org.kjtc.mapper.ChargingStationMapper;
import org.kjtc.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("chargingStationService")
public class ChargingStationService {

    @Autowired
    private ChargingStationMapper chargingStationMapper;

    public List<ChargingStation> getEquipmentList(ChargingStation chargingStation) {
        return chargingStationMapper.getChargingStationList(chargingStation);
    }

    public List<ChargingStation> getChargingStationName(ChargingStation chargingStation) {
        return chargingStationMapper.getChargingStationName(chargingStation);
    }

    public Integer checkOnly(String chargingStationID) {
        return chargingStationMapper.checkOnly(chargingStationID);
    }
}
