package org.kjtc.mapper;

import org.apache.ibatis.annotations.*;
import org.kjtc.entity.ChargingStation;
import org.kjtc.entity.Equipment;

import java.util.List;

@Mapper
public interface ChargingStationMapper {

    @SelectProvider(type = ChargingStationProvider.class, method = "getChargingStationList")
    List<ChargingStation> getChargingStationList(ChargingStation chargingStation);

    @Select("select count(*) from chargingstation  where ChargingStationID = #{ChargingStationID}")
    Integer checkOnly(String chargingStationID);

    @SelectProvider(type = ChargingStationProvider.class, method = "getChargingStationName")
    List<ChargingStation> getChargingStationName(ChargingStation chargingStation);
}
