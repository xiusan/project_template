package org.kjtc.mapper;

import org.apache.ibatis.annotations.*;
import org.kjtc.entity.Equipment;

import java.util.List;

@Mapper
public interface EquipmentMapper {

    @SelectProvider(type = EquipmentProvider.class, method = "getEquipmentList")
    List<Equipment> getEquipmentList(Equipment equipment);

    @Insert("INSERT INTO equipment (" +
            "EquipmentID," +
            "EquipmentName," +
            "ChargingStationID," +
            "EquipmentType," +
            "EquipmentPowerType," +
            "EquipmentManufacturer," +
            "ManufactureTime," +
            "EquipmentBatch," +
            "ProtocolType," +
            "CreateUser," +
            "CreateDTTM," +
            "UpdateUser," +
            "UpdateDTTM" +
            ")" +
            "VALUES" +
            "(" +
            "#{EquipmentID}," +
            " #{EquipmentName}," +
            "#{ChargingStationID}," +
            "#{EquipmentType}," +
            "#{EquipmentPowerType}," +
            "#{EquipmentManufacturer}," +
            "#{ManufactureTime}," +
            "#{EquipmentBatch}," +
            "#{ProtocolType}," +
            "#{CreateUser}," +
            "SYSDATE()," +
            "#{UpdateUser}," +
            "SYSDATE())")
    int saveEquipment(Equipment equipment);

    @Select("select count(*) from equipment where EquipmentID = #{EquipmentID}")
    int checkOnly(String equipmentID);

    @SelectProvider(type = EquipmentProvider.class, method = "getEquipmentName")
    List<Equipment> getEquipmentName(Equipment equipment);

    @Update("UPDATE equipment" +
            " SET" +
            " EquipmentName = #{EquipmentName}," +
            " ChargingStationID = #{ChargingStationID}," +
            " EquipmentType = #{EquipmentType}," +
            " EquipmentPowerType = #{EquipmentPowerType}," +
            " EquipmentManufacturer = #{EquipmentManufacturer}," +
            " ManufactureTime = #{ManufactureTime}," +
            " EquipmentBatch = #{EquipmentBatch}," +
            " ProtocolType = #{ProtocolType}," +
            " UpdateUser = #{UpdateUser}," +
            " UpdateDTTM = SYSDATE()" +
            " WHERE" +
            " EquipmentID = #{EquipmentID}")
    int updateEquipment(Equipment equipment);

    @DeleteProvider(type = EquipmentProvider.class, method = "delEquipment")
    int delEquipment(Equipment equipment);
}
