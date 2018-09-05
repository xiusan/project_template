package org.kjtc.entity;

import java.util.Date;

public class ChargingStation {

    private String ChargingStationID;

    private String ChargingStationName;

    private String ProjectNo;

    private String ChargingStationAddress;

    private double Longitude;

    private double Latitude;

    private String CreateUser;

    private Date CreateDTTM;

    private String UpdateUser;

    private Date UpdateDTTM;

    public String getChargingStationID() {
        return ChargingStationID;
    }

    public void setChargingStationID(String chargingStationID) {
        ChargingStationID = chargingStationID;
    }

    public String getChargingStationName() {
        return ChargingStationName;
    }

    public void setChargingStationName(String chargingStationName) {
        ChargingStationName = chargingStationName;
    }

    public String getProjectNo() {
        return ProjectNo;
    }

    public void setProjectNo(String projectNo) {
        ProjectNo = projectNo;
    }

    public String getChargingStationAddress() {
        return ChargingStationAddress;
    }

    public void setChargingStationAddress(String chargingStationAddress) {
        ChargingStationAddress = chargingStationAddress;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
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