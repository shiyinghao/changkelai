package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.DeviceTypeDTO;
import com.icss.newretail.user.entity.UserDeviceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceTypeMapper extends BaseMapper<UserDeviceType> {

    DeviceTypeDTO queryDeviceTypeById(@Param("deviceType")String deviceType);

    List<DeviceTypeDTO> queryDeviceTypes( @Param("deviceTypeName") String deviceTypeName);
}
