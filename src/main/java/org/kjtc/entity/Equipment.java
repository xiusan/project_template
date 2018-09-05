package org.kjtc.entity;

import org.apache.commons.lang.StringUtils;
import org.kjtc.util.Tools;

import java.util.Date;

public class Equipment {

    private String EquipmentID;

    private String EquipmentName;

    private String ChargingStationID;

    private String ChargingStationName;

    private String EquipmentType;

    private String EquipmentPowerType;

    private String EquipmentManufacturer;

    private Date ManufactureTime;

    private String ManufactureDateTime;

    private String EquipmentBatch;

    private String ProtocolType;

    private String CreateUser;

    private Date CreateDTTM;

    private String UpdateUser;

    private Date UpdateDTTM;

    public String getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        EquipmentID = equipmentID;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        EquipmentName = equipmentName;
    }

    public String getChargingStationName() {
        return ChargingStationName;
    }

    public void setChargingStationName(String chargingStationName) {
        ChargingStationName = chargingStationName;
    }

    public String getChargingStationID() {
        return ChargingStationID;
    }

    public void setChargingStationID(String chargingStationID) {
        ChargingStationID = chargingStationID;
    }

    public String getEquipmentType() {
        return EquipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        EquipmentType = equipmentType;
    }

    public String getEquipmentPowerType() {
        return EquipmentPowerType;
    }

    public void setEquipmentPowerType(String equipmentPowerType) {
        EquipmentPowerType = equipmentPowerType;
    }

    public String getEquipmentManufacturer() {
        return EquipmentManufacturer;
    }

    public void setEquipmentManufacturer(String equipmentManufacturer) {
        EquipmentManufacturer = equipmentManufacturer;
    }

    public Date getManufactureTime() {
        return ManufactureTime;
    }

    public void setManufactureTime(Date manufactureTime) {
        ManufactureTime = manufactureTime;
    }

    public String getManufactureDateTime() {
        return ManufactureDateTime;
    }

    public void setManufactureDateTime(String manufactureDateTime) {
        ManufactureDateTime = manufactureDateTime;
        if (!StringUtils.isEmpty(manufactureDateTime)) {
            setManufactureTime(Tools.str2Date(manufactureDateTime));
        }
    }

    public String getEquipmentBatch() {
        return EquipmentBatch;
    }

    public void setEquipmentBatch(String equipmentBatch) {
        EquipmentBatch = equipmentBatch;
    }

    public String getProtocolType() {
        return ProtocolType;
    }

    public void setProtocolType(String protocolType) {
        ProtocolType = protocolType;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public Date getCreateDTTM() {
        return CreateDTTM;
    }

    public void setCreateDTTM(Date createDTTM) {
        CreateDTTM = createDTTM;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }

    public Date getUpdateDTTM() {
        return UpdateDTTM;
    }

    public void setUpdateDTTM(Date updateDTTM) {
        UpdateDTTM = updateDTTM;
    }
}