package com.icss.newretail.service.user;

import com.icss.newretail.model.CameraInfoDTO;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;

/**
 * <p>
 * 摄像头配置信息 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
public interface CameraInfoService {
	/**
	 * 获取摄像头配置列表
	 */
	public ResponseRecords<CameraInfoDTO> getCameraInfoList();
	/**
	 * 获取摄像头配置列表-根据门店编码
	 */
	public ResponseRecords<CameraInfoDTO> getCameraInfoListByOrgSeq(String orgSeq, Integer cameraLocation);
	/**
	 * 获取摄像头配置列表-根据摄像头ID
	 */
	public ResponseResult<CameraInfoDTO> getCameraInfoByCameraId(String cameraId, Integer cameraLocation);
}
