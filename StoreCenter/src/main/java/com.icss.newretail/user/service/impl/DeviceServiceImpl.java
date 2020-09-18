package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.DeviceDTO;
import com.icss.newretail.model.DeviceRequest;
import com.icss.newretail.model.DeviceTypeDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.service.user.DeviceService;
import com.icss.newretail.user.dao.DeviceMapper;
import com.icss.newretail.user.dao.DeviceTypeMapper;
import com.icss.newretail.user.dao.StoreMonitorMapper;
import com.icss.newretail.user.entity.StoreMonitor;
import com.icss.newretail.user.entity.UserDeviceType;
import com.icss.newretail.user.entity.UserStoreDevice;
import com.icss.newretail.util.DateUtils;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private DeviceMapper deviceMapper;

  @Autowired
  private DeviceTypeMapper deviceTypeMapper;

  @Autowired
  private StoreMonitorMapper storeMonitorMapper;

  @Autowired
  protected StringRedisTemplate stringRedisTemplate;

  public static final String QUEUE_NAME = "MONITOR_QUEUE";

  /**
   * 终端设备添加
   *
   * @param device
   * @return
   */
  @Override
  public ResponseBase createDevice(DeviceDTO device) {
    ResponseBase base = new ResponseBase();
    try {
      UserStoreDevice userStoreDevice=Object2ObjectUtil.parseObject(device, UserStoreDevice.class);
      userStoreDevice.setCreateUser(JwtTokenUtil.currUser());
      userStoreDevice.setUpdateUser(JwtTokenUtil.currUser());
      if (StringUtils.isEmpty(userStoreDevice.getDeviceId())) {
        userStoreDevice.setDeviceId(UUID.randomUUID().toString());
      } else if (deviceMapper.selectById(userStoreDevice.getDeviceId()) != null) {
        base.setCode(1);
        base.setMessage("该终端设备已经被注册过");
        return base;
      }
      int i = deviceMapper.insert(userStoreDevice);
      base.setCode(1);
      base.setMessage("终端设备添加完成");
      log.info("t_user_store_device表受影响的数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|createDevice->终端设备添加:" + ex.getMessage());
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;
  }

  /**
   * 终端设置更新
   *
   * @param device
   * @return
   */
  @Override
  public ResponseBase modifyDevice(DeviceDTO device) {
    ResponseBase base = new ResponseBase();
    try {
      UserStoreDevice userStoreDevice=Object2ObjectUtil.parseObject(device, UserStoreDevice.class);
      userStoreDevice.setUpdateUser(JwtTokenUtil.currUser());
      userStoreDevice.setUpdateTime(LocalDateTime.now());
      int i = deviceMapper.update(userStoreDevice,new UpdateWrapper<UserStoreDevice>().eq("device_id",userStoreDevice.getDeviceId()));
      base.setCode(1);
      base.setMessage("终端设置信息修改完成");
      log.info("t_user_store_device表受影响的数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|modifyDevice->终端设置更新" + ex.getMessage());
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;
  }

  /**
   * 终端设备删除
   *
   * @param deviceId
   * @return
   */
  @Override
  public ResponseBase deleteDevice(String deviceId) {
    ResponseBase base = new ResponseBase();
    try {
      Integer i = deviceMapper.deleteById(deviceId);
      base.setCode(1);
      base.setMessage("终端设备信息删除完成");
      log.info("t_user_store_device表受影响的数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|deleteDevice->终端设备删除[" + ex.getMessage() + "]");
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;
  }

  /**
   * 终端数设备明细查询
   *
   * @param deviceId
   * @return
   */
  @Override
  public ResponseResult<DeviceDTO> queryDeviceById(String deviceId) {
    ResponseResult<DeviceDTO> result = new ResponseResult<>();
    try {
      DeviceDTO deviceDTO = deviceMapper.queryDeviceById(deviceId);
      result.setCode(1);
      if (deviceDTO == null) {
        result.setMessage("设备未注册");
        result.setResult(new DeviceDTO());
      } else {
        result.setResult(deviceDTO);
        result.setMessage("设备查询完成");
      }
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|queryDeviceById->终端数设备明细查询" + ex.getMessage());
      result.setMessage(ex.getMessage());
      result.setCode(0);
    }
    return result;
  }

  /**
   * 终端数设备查询(按照组织机构查询)
   *
   * @return
   */
  @Override
  public ResponseResultPage<DeviceDTO> queryDevices(PageData<DeviceRequest> pageData) {
    ResponseResultPage<DeviceDTO> result = new ResponseResultPage<>();
    try {
      Page<DeviceDTO> page = new Page<DeviceDTO>(pageData.getCurrent(), pageData.getSize());
      page.setDesc(pageData.getDescs());
      page.setAsc(pageData.getAscs());
      Page<DeviceDTO> pageResult = deviceMapper.queryDevices(page,  pageData.getCondition());
      result.setCode(1);
      result.setMessage("终端数设备查询完成(按照组织机构查询)");
      result.setRecords(pageResult.getRecords());
      result.setTotal(pageResult.getTotal());
      result.setCurrent(pageData.getCurrent());
      result.setSize(pageData.getSize());
      log.info("共查询到"+" + pageResult.getRecords().size() + "+"条信息");
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error("DeviceServiceImpl|queryDevices->终端数设备查询" + ex.getMessage());
      result.setMessage(ex.getMessage());
      result.setCode(0);
    }
    return result;
  }

  /**
   * 根据终端编号获取终端信息（单个）
   *
   * @param deviceNum
   * @return
   */
  @Override
  public ResponseResult<DeviceDTO> getDeviceByNum(String deviceNum) {
    ResponseResult<DeviceDTO> result = new ResponseResult<>();
    try {
      DeviceDTO deviceDTO = deviceMapper.getDeviceByNum(deviceNum);
      result.setCode(1);
      result.setMessage("根据终端编号获取终端信息完成");
      result.setResult(deviceDTO);
    } catch (Exception ex) {
      result.setCode(0);
      result.setMessage(ex.getMessage());
      log.error("DeviceServiceImpl|getDeviceByNum->根据终端编号获取终端信息" + ex.getMessage());
    }
    return result;
  }

  @Override
  public ResponseRecords<DeviceDTO> getStoreDevicesByType(String storeId, String deviceType) {
    ResponseRecords<DeviceDTO> records = new ResponseRecords<DeviceDTO>();
    try {
      List<UserStoreDevice> userStoreDevices = deviceMapper.selectList(new QueryWrapper<UserStoreDevice>().eq("org_seq",storeId)
              .eq("device_type",deviceType).eq("status",1));
      List<DeviceDTO> deviceDTOList = Object2ObjectUtil.parseList(userStoreDevices,DeviceDTO.class);
      records.setRecords(deviceDTOList);
      records.setCode(1);
      records.setMessage("根据终端类型获取门店终端信息完成");
      log.info("共查询到"+deviceDTOList.size()+"条信息");
    }catch (Exception ex) {
      records.setCode(0);
      records.setMessage(ex.getMessage());
      log.error("DeviceServiceImpl|getStoreDevicesByType->根据终端类型获取门店终端信息" + ex.getMessage());
    }
    return records;
  }

  /**
   * 终端设备类型添加
   *
   * @param deviceTypeDTO
   * @return
   */
  @Override
  public ResponseBase createDeviceType(DeviceTypeDTO deviceTypeDTO) {
    ResponseBase base = new ResponseBase();
    try {
      UserDeviceType userDeviceType = Object2ObjectUtil.parseObject(deviceTypeDTO,UserDeviceType.class);
      userDeviceType.setCreateUser(JwtTokenUtil.currUser());
      userDeviceType.setDeviceType(UUID.randomUUID().toString());
      Integer i = deviceTypeMapper.insert(userDeviceType);
      base.setCode(1);
      base.setMessage("终端设备类型添加完成");
      log.info("t_user_device_type表受影响数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|createDeviceType->添加终端设备类型" + ex.getMessage());
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;
  }

  /**
   * 终端设置类型更新
   *
   * @param deviceTypeDTO
   * @return
   */
  @Override
  public ResponseBase modifyDeviceType(DeviceTypeDTO deviceTypeDTO) {
    ResponseBase base = new ResponseBase();
    try {
      UserDeviceType userDeviceType = Object2ObjectUtil.parseObject(deviceTypeDTO,UserDeviceType.class);
      userDeviceType.setUpdateUser(JwtTokenUtil.currUser());
      userDeviceType.setUpdateTime(LocalDateTime.now());
      Integer i = deviceTypeMapper.update(userDeviceType,new UpdateWrapper<UserDeviceType>()
              .eq("device_type",userDeviceType.getDeviceType()));
      base.setCode(1);
      base.setMessage("终端设备类型更新完成");
      log.info("t_user_device_type表受影响数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|modifyDeviceType->终端设置类型更新" + ex.getMessage());
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;
  }

  /**
   * 终端设备类型删除
   *
   * @param deviceType
   * @return
   */
  @Override
  public ResponseBase deleteDeviceType(String deviceType) {
    ResponseBase base = new ResponseBase();
    try {
      int i = deviceTypeMapper.deleteById(deviceType);
      base.setCode(1);
      base.setMessage("终端设备类型信息删除完成");
      log.info("t_user_device_type表受影响的数据行数"+i);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|deleteDeviceType->终端设备类型删除" + ex.getMessage());
      base.setMessage(ex.getMessage());
      base.setCode(0);
    }
    return base;

  }

  /**
   * 终端数设备类型明细查询
   *
   * @param deviceType
   * @return
   */
  @Override
  public ResponseResult<DeviceTypeDTO> queryDeviceTypeById(String deviceType) {
    ResponseResult<DeviceTypeDTO> result = new ResponseResult<>();
    try {
      DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.queryDeviceTypeById(deviceType);
      result.setCode(1);
      result.setMessage("查询设备类型记录完成");
      result.setResult(deviceTypeDTO);
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|queryDeviceTypeById->终端数设备类型明细查询" + ex.getMessage());
      result.setMessage(ex.getMessage());
      result.setCode(0);
    }
    return result;
  }

  /**
   * 终端数设备类型查询(按照组织机构查询)
   *
   * @return
   */
  @Override
  public ResponseRecords<DeviceTypeDTO> queryDeviceTypes(String deviceTypeName) {
    ResponseRecords<DeviceTypeDTO> result = new ResponseRecords<DeviceTypeDTO>();
    try {
      List<DeviceTypeDTO> deviceTypeDTOS = deviceTypeMapper.queryDeviceTypes(deviceTypeName);
      result.setCode(1);
      result.setRecords(deviceTypeDTOS);
      result.setMessage("查询设备类型信息完成");
      log.info("共查询到"+deviceTypeDTOS.size()+"条终端数设备类型信息");
    } catch (Exception ex) {
      log.error("DeviceServiceImpl|queryDeviceTypes->queryDeviceTypes" + ex.getMessage());
      result.setMessage(ex.getMessage());
      result.setCode(0);
    }
    return result;
  }

  /**
   * 记录终端在线监测数据
   * 补全内容
   */
  @Override
  public ResponseBase monitor(String storeId, String deviceId, String date,Integer isRunback) {
    ResponseBase responseBase = new ResponseBase();
    try {
      StoreMonitor entity = new StoreMonitor(storeId, deviceId,isRunback);
      entity.setMonitorDate(DateUtils.formatTime(date, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
      entity.setMonitorTime(DateUtils.strToDate(date, "yyyy-MM-dd HH:mm:ss"));
      int res = storeMonitorMapper.insert(entity);
      if (res > 0) {
        responseBase.setCode(1);
        responseBase.setMessage("新增成功");
        log.info("插入数据库成功");
      } else {
        throw new RuntimeException("新增失败");
      }
    } catch (Exception e) {
      responseBase.setCode(0);
      responseBase.setMessage("新增异常");
      log.error("新增异常---" + e.getMessage());
    }
    return responseBase;
  }

}
