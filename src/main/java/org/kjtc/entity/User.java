package org.kjtc.entity;

import java.util.Date;

public class User {

    private String UserID;

    private String UserName;

    private String Password;

    private String OldPassword;

    private boolean Enabled;

    private String CreateUser;

    private Date CreateDTTM;

    private String UpdateUser;

    private Date UpdateDTTM;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
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
