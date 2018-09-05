package org.kjtc.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.kjtc.entity.Equipment;

public class EquipmentProvider {

    public String getEquipmentList(Equipment equipment) {

        String newSql = " equipment.EquipmentID," +
                " equipment.EquipmentName," +
                " equipment.ChargingStationID," +
                " equipment.EquipmentType," +
                " equipment.EquipmentPowerType," +
                " equipment.EquipmentManufacturer," +
                " equipment.EquipmentBatch," +
                " equipment.ManufactureTime," +
                " equipment.ProtocolType," +
                " c.ChargingStationName" +
                " from equipment equipment" +
                " left join chargingstation c on equipment.ChargingStationID = c.ChargingStationID" +
                " where 1=1";
        if (!StringUtils.isEmpty(equipment.getChargingStationID())) {
            newSql += " and equipment.ChargingStationID = #{ChargingStationID}";
        };

        if (!StringUtils.isEmpty(equipment.getEquipmentID())) {
            newSql += " and equipment.EquipmentID = #{EquipmentID}";
        };
        SQL sql = new SQL().SELECT(newSql);
        sql.ORDER_BY("equipment.EquipmentID ");
        return sql.toString();
    }
    public String delEquipment(Equipment equipment) {
        String newSql = " equipment where EquipmentID in (";
        String[] split = equipment.getEquipmentID().split(",");
        for (int i = 0; i < split.length - 1; i++) {
            newSql += "'";
            newSql += split[i];
            newSql += "'";
            newSql += ",";
        }
        newSql += "'";
        newSql += split[split.length - 1];
        newSql += "'";
        newSql += ")";
        SQL sql = new SQL().DELETE_FROM(newSql);
        return sql.toString();
    }

    public String getEquipmentName(Equipment equipment) {

        String newSql = " EquipmentID, EquipmentName from equipment where 1=1";
        if (!StringUtils.isEmpty(equipment.getChargingStationID())) {
            newSql += " and ChargingStationID=#{ChargingStationID}";
        }
        if (!StringUtils.isEmpty(equipment.getEquipmentID()) && !"ALL".equals(equipment.getChargingStationID().toUpperCase())) {
            newSql += " and EquipmentID=#{EquipmentID}";
        }
        SQL sql = new SQL().SELECT(newSql);

        return sql.toString();
    }
}
