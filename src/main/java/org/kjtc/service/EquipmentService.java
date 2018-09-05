package org.kjtc.service;


import org.kjtc.entity.Equipment;
import org.kjtc.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("equipmentService")
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    public List<Equipment> getEquipmentList(Equipment equipment) {
        return equipmentMapper.getEquipmentList(equipment);
    }

    public List<Equipment> getEquipmentName(Equipment equipment) {
        return equipmentMapper.getEquipmentName(equipment);
    }

    public Integer checkOnly(String equipmentID) {
        return equipmentMapper.checkOnly(equipmentID);
    }

    @Transactional
    public int saveEquipment(Equipment equipment) {
        return equipmentMapper.saveEquipment(equipment);
    }

    @Transactional
    public int updateEquipment(Equipment equipment) {
        return equipmentMapper.updateEquipment(equipment);
    }

    @Transactional
    public int delEquipment(Equipment equipment) {
        return equipmentMapper.delEquipment(equipment);
    }
}
