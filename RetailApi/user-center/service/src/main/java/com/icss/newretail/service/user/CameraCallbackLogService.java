package com.icss.newretail.service.user;

import com.icss.newretail.model.CameraCallbackLogDTO;
import com.icss.newretail.model.ResponseBase;

/**
 * <p>
 * 摄像头回写日志 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
public interface CameraCallbackLogService {
	/**
	 * 保存摄像头回写日志
	 */
	public ResponseBase insertCameraCallbackLog(CameraCallbackLogDTO cameraCallbackLogDTO);
}
