package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.CameraInfoDTO;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.user.CameraInfoService;
import com.icss.newretail.user.dao.CameraInfoMapper;
import com.icss.newretail.user.entity.CameraInfo;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 摄像头配置信息 服务实现类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-13
 */
@Service
public class CameraInfoServiceImpl implements CameraInfoService {
	@Autowired
    private CameraInfoMapper cameraInfoMapper;

    /**
	 * 获取摄像头配置列表
	 */
	@Override
	public ResponseRecords<CameraInfoDTO> getCameraInfoList() {
		//获取租户ID
		String token = ContextUtils.getInvocationContext().getContext("token");
		String tenantId = "";
		if (!StringUtils.isEmpty(token)) {
			String subject = JwtTokenUtil.getClaimFromToken(token).getSubject();
			if (!StringUtils.isEmpty(subject)) {
				String[] temp = subject.split(",");
				tenantId = temp[1];
			}
		}
	    List<CameraInfo> cameraInfoList = cameraInfoMapper.selectList(new QueryWrapper<CameraInfo>()
	    		.select("org_seq", "camera_id", "camera_location")
	    		.eq(!StringUtils.isEmpty(tenantId), "tenant_id", tenantId).eq("status", 1));
	    List<CameraInfoDTO> cameraInfoDTOList = Object2ObjectUtil.parseList(cameraInfoList, CameraInfoDTO.class);
		ResponseRecords<CameraInfoDTO> responseResult = new ResponseRecords<CameraInfoDTO>();
	    responseResult.setCode(1);
		responseResult.setRecords(cameraInfoDTOList);
		return responseResult;
	}
	/**
	 * 获取摄像头配置列表
	 */
	@Override
	public ResponseRecords<CameraInfoDTO> getCameraInfoListByOrgSeq(String orgSeq, Integer cameraLocation) {
		//获取租户ID
		String token = ContextUtils.getInvocationContext().getContext("token");
		String tenantId = "";
		if (!StringUtils.isEmpty(token)) {
			String subject = JwtTokenUtil.getClaimFromToken(token).getSubject();
			if (!StringUtils.isEmpty(subject)) {
				String[] temp = subject.split(",");
				tenantId = temp[1];
			}
		}
		List<CameraInfo> cameraInfoList = cameraInfoMapper.selectList(new QueryWrapper<CameraInfo>()
				.select("org_seq", "camera_id", "camera_location")
				.eq(!StringUtils.isEmpty(tenantId), "tenant_id", tenantId).eq("status", 1)
				.eq("org_seq", orgSeq).eq(cameraLocation != null, "camera_location", cameraLocation));
		List<CameraInfoDTO> cameraInfoDTOList = Object2ObjectUtil.parseList(cameraInfoList, CameraInfoDTO.class);
		ResponseRecords<CameraInfoDTO> responseResult = new ResponseRecords<CameraInfoDTO>();
		responseResult.setCode(1);
		responseResult.setRecords(cameraInfoDTOList);
		return responseResult;
	}
	/**
	 * 获取摄像头配置
	 */
	@Override
	public ResponseResult<CameraInfoDTO> getCameraInfoByCameraId(String cameraId, Integer cameraLocation) {
		CameraInfo cameraInfo = cameraInfoMapper.selectOne(new QueryWrapper<CameraInfo>()
				.select("org_seq", "camera_id", "camera_location")
				.eq("status", 1)
				.eq("camera_id", cameraId).eq("camera_location", cameraLocation));
		CameraInfoDTO cameraInfoDTO = Object2ObjectUtil.parseObject(cameraInfo, CameraInfoDTO.class);
		ResponseResult<CameraInfoDTO> responseResult = new ResponseResult<CameraInfoDTO>();
		responseResult.setCode(1);
		responseResult.setResult(cameraInfoDTO);
		return responseResult;
	}
}
