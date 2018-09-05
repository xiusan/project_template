package org.kjtc.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.kjtc.entity.User;

public class UserProvider {
    public String getUserList(User user) {

        String newSql = " UserID," +
                " UserName," +
                " Enabled" +
                " from user" +
                " where 1=1";
        if (!StringUtils.isEmpty(user.getUserID())) {
            newSql += " and (UserID = #{UserID} or UserName = #{UserID})";
        }

        if (!StringUtils.isEmpty(user.getUserName())) {
            newSql += " and UserName = #{UserName}";
        }

        SQL sql = new SQL().SELECT(newSql);
        sql.ORDER_BY("UserID ");
        return sql.toString();
    }
    public String getUser(User user) {

        String newSql = " UserID," +
                " UserName," +
                " Password," +
                " Enabled," +
                " UpdateDTTM" +
                " from user" +
                " where 1=1";
        if (!StringUtils.isEmpty(user.getUserID())) {
            newSql += " and (UserID = #{UserID} or UserName = #{UserID})";
        }

        if (!StringUtils.isEmpty(user.getUserName())) {
            newSql += " and UserName = #{UserName}";
        }

        SQL sql = new SQL().SELECT(newSql);
        return sql.toString();
    }
}
