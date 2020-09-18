package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.DeviceDTO;
import com.icss.newretail.model.DeviceRequest;
import com.icss.newretail.user.entity.UserStoreDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMapper extends BaseMapper<UserStoreDevice>{

    Page<DeviceDTO> queryDevices(@Param("page") Page page,
                                 @Param("deviceRequest") DeviceRequest deviceRequest);

    DeviceDTO queryDeviceById(@Param("deviceId") String deviceId);

    Integer deleteDevice(@Param("deviceId")String deviceId);

    DeviceDTO getDeviceByNum(@Param("deviceNum") String deviceNum);

    List<String> getPOSDeviceIdByStoreId(String orgSeq);
    List<String> getPOSDeviceIdByStoreIds(List<String>  orgSeqList );

}
