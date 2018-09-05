package org.kjtc.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.kjtc.entity.ChargingStation;
import org.kjtc.entity.Equipment;

public class ChargingStationProvider {

    public String getChargingStationList(ChargingStation chargingStation) {

        String newSql = " ChargingStationID," +
                " ChargingStationName," +
                " ProjectNo," +
                " ChargingStationAddress," +
                " Longitude," +
                " Latitude," +
                " from chargingStation" +
                " where 1=1";
        if (!StringUtils.isEmpty(chargingStation.getChargingStationID())) {
            newSql += " and ChargingStationID = #{ChargingStationID}";
        }

        SQL sql = new SQL().SELECT(newSql);
        sql.ORDER_BY("ChargingStationID ");
        return sql.toString();
    }

    public String getChargingStationName(ChargingStation chargingStation) {

        String newSql = " ChargingStationID, ChargingStationName from chargingstation where 1=1";
        if (!StringUtils.isEmpty(chargingStation.getChargingStationID())) {
            newSql += " and ChargingStationID=#{ChargingStationID}";
        }
        SQL sql = new SQL().SELECT(newSql);

        return sql.toString();
    }
}
