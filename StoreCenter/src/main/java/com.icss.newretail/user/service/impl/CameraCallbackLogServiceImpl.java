package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.CameraCallbackLogDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.service.user.CameraCallbackLogService;
import com.icss.newretail.user.dao.CameraCallbackLogMapper;
import com.icss.newretail.user.entity.CameraCallbackLog;
import com.icss.newretail.util.Object2ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 视像头回写日志 服务实现类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
@Service
public class CameraCallbackLogServiceImpl implements CameraCallbackLogService {
	@Autowired
    private CameraCallbackLogMapper cameraCallbackLogMapper;

    /**
	 * 保存日志
	 */
	public ResponseBase insertCameraCallbackLog(CameraCallbackLogDTO cameraCallbackLogDTO) {
		CameraCallbackLog cameraCallbackLog = Object2ObjectUtil.parseObject(cameraCallbackLogDTO, CameraCallbackLog.class);
		cameraCallbackLog.setCreateTime(LocalDateTime.now());
		cameraCallbackLog.setUpdateTime(LocalDateTime.now());
		cameraCallbackLogMapper.insert(cameraCallbackLog);
		return new ResponseBase(1, "保存成功");
	}
}
