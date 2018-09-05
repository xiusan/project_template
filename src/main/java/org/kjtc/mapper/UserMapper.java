package org.kjtc.mapper;

import org.apache.ibatis.annotations.*;
import org.kjtc.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @SelectProvider(type = UserProvider.class, method = "getUserList")
    List<User> getUserList(User user);

    @SelectProvider(type = UserProvider.class, method = "getUser")
    User getUser(User user);

    @Insert("INSERT INTO user (" +
            "UserID," +
            "UserName," +
            "Password," +
            "Enabled," +
            "CreateUser," +
            "CreateDTTM," +
            "UpdateUser," +
            "UpdateDTTM" +
            ")" +
            "VALUES" +
            "(" +
            "#{UserID}," +
            " #{UserName}," +
            "#{Password}," +
            "'1'," +
            "#{CreateUser}," +
            "SYSDATE()," +
            "#{UpdateUser}," +
            "SYSDATE())")
    int saveUser(User user);

    @Update("UPDATE user" +
            " SET" +
            " UserName = #{UserName}," +
            " Enabled = #{Enabled}" +
//            " UpdateUser = #{UpdateUser}," +
//            " UpdateDTTM = SYSDATE()" +
            " WHERE" +
            " UserID = #{UserID}")
    int updateUser(User user);

    @Update("UPDATE user" +
            " SET" +
            " Password = #{Password}," +
            " UpdateUser = #{UpdateUser}," +
            " UpdateDTTM = #{UpdateDTTM}" +
            " WHERE" +
            " UserID = #{UserID} and Password = #{OldPassword} and Enabled = 1")
    int updatePassword(User user);
}
