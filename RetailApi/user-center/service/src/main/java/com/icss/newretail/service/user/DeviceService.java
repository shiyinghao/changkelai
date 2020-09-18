package com.icss.newretail.service.user;

import com.icss.newretail.model.DeviceDTO;
import com.icss.newretail.model.DeviceRequest;
import com.icss.newretail.model.DeviceTypeDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;

public interface DeviceService {

  public ResponseBase createDevice(DeviceDTO device);

  public ResponseBase modifyDevice(DeviceDTO device);

  public ResponseBase deleteDevice(String deviceId);

  public ResponseResult<DeviceDTO> queryDeviceById(String deviceId);

  public ResponseResultPage<DeviceDTO> queryDevices(PageData<DeviceRequest> pageData);

  public ResponseBase createDeviceType(DeviceTypeDTO deviceType);

  public ResponseBase modifyDeviceType(DeviceTypeDTO deviceType);

  public ResponseBase deleteDeviceType(String deviceId);

  public ResponseResult<DeviceTypeDTO> queryDeviceTypeById(String deviceId);

  public ResponseRecords<DeviceTypeDTO> queryDeviceTypes(String deviceTypeName);

  public ResponseBase monitor(String storeId, String deviceId, String date, Integer isRunback);

  public ResponseResult<DeviceDTO> getDeviceByNum(String deviceNum);

  public ResponseRecords<DeviceDTO> getStoreDevicesByType(String storeId, String deviceType);
}
