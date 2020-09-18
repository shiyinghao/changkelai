package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.AppletAuthorityDTO;
import com.icss.newretail.model.AuthorityParamsDTO;
import com.icss.newretail.model.MUserOrganizationDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ShopAppletAuthortiryDto;
import com.icss.newretail.model.ShopGoodsParamDTO;
import com.icss.newretail.model.UserAuthorityRelationDto;
import com.icss.newretail.model.UserAuthorityRelationReq;
import com.icss.newretail.model.UserStoreInfoDTO;
import com.icss.newretail.service.user.ShopGoodsDetailService;
import com.icss.newretail.user.dao.ShopGoodsDetailMapper;
import com.icss.newretail.user.dao.UserAuthorityRelationMapper;
import com.icss.newretail.user.dao.UserOrgRelationMapper;
import com.icss.newretail.user.dao.UserShopAppletAuthortiryMapper;
import com.icss.newretail.user.entity.UserAuthorityRelation;
import com.icss.newretail.user.entity.UserShopAppletAuthortiry;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MathUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ShopGoodsDetailServiceImpl implements ShopGoodsDetailService {
	@Autowired
	private ShopGoodsDetailMapper shopGoodsDetailMapper;

	@Autowired
	private UserAuthorityRelationMapper userAuthorityRelationMapper;

	@Autowired
	private UserShopAppletAuthortiryMapper userShopAppletAuthortiryMapper;

	@Autowired
	private UserOrgRelationMapper userOrgRelationMapper;

	private final static String userId = "userId";
	private final static LocalDateTime dTime = LocalDateTime.now();

	@SuppressWarnings("unused")
	@Override
	public ResponseResult<UserStoreInfoDTO> queryShopDetailById(ShopGoodsParamDTO param) {
		ResponseResult<UserStoreInfoDTO> result = new ResponseResult<UserStoreInfoDTO>();
		try {
			UserStoreInfoDTO shop = shopGoodsDetailMapper.queryShopDetailById(param);
			if (shop != null) {
				if (param.getLng() != null && param.getLat() != null) {
					if (!"".equals(param.getLng()) && !"".equals(param.getLat())) {
						Double distance = MathUtil.getDistance(Double.valueOf(param.getLng().doubleValue()),
								Double.valueOf(param.getLat().doubleValue()), Double.parseDouble(shop.getStoreLng()),
								Double.parseDouble(shop.getStoreLat()));
						shop.setDistance(distance.intValue());
					}
				}
				result.setCode(1);
				result.setMessage("商铺详情信息查询成功");
				result.setResult(shop);
			} else {
				result.setCode(0);
				result.setMessage("商铺详情信息查询失败");
			}
		} catch (

		Exception e) {
			result.setCode(0);
			result.setMessage("商铺详情信息查询异常" + e.getMessage());
			log.error("商铺详情信息查询异常" + e.getMessage());
		}
		return result;
	}

	@Override
	public ResponseRecords<AuthorityParamsDTO> queryShopUserDetail(AuthorityParamsDTO para) {
		ResponseRecords<AuthorityParamsDTO> result = new ResponseRecords<AuthorityParamsDTO>();
		try {
			List<AuthorityParamsDTO> authorityParamsDTOS = shopGoodsDetailMapper.queryShopUserDetail(para);
			result.setCode(1);
			result.setRecords(authorityParamsDTOS);
			result.setMessage("查询成功！");
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage("查询异常" + e.getMessage());
			log.error("查询异常" + e.getMessage());
		}
		return result;
	}

	@Override
	public ResponseBase addAuthority(AuthorityParamsDTO para) {
		ResponseBase responseBase = new ResponseBase();
		String authorityId = UUID.randomUUID().toString();
		// 设置权限
		UserShopAppletAuthortiry _userShopAppletAuthortiry = new UserShopAppletAuthortiry();
		_userShopAppletAuthortiry.setUuid(authorityId);
		_userShopAppletAuthortiry.setAuthorityName(para.getAuthorityName());
		_userShopAppletAuthortiry.setAuthorityUri(para.getAuthorityUri());
		_userShopAppletAuthortiry.setDescription(para.getDesc());
		_userShopAppletAuthortiry.setStatus(1);
		_userShopAppletAuthortiry.setUpdateUser(userId);
		_userShopAppletAuthortiry.setUpdateTime(dTime);
		int ress = userShopAppletAuthortiryMapper.insert(_userShopAppletAuthortiry);
		// 权限关联
		String uuid = UUID.randomUUID().toString();
		UserAuthorityRelation _userAuthorityRelation = new UserAuthorityRelation();
		_userAuthorityRelation.setUuid(uuid);
		_userAuthorityRelation.setAuthorityId(authorityId);
		_userAuthorityRelation.setUserId(para.getUserId());
		_userAuthorityRelation.setOrgSeq(para.getOrgSeq());
		_userAuthorityRelation.setCreateUser(userId);
		_userAuthorityRelation.setCreateTime(dTime);
		int res = userAuthorityRelationMapper.insert(_userAuthorityRelation);

		if (ress > 0 && res > 0) {
			responseBase.setCode(1);
			responseBase.setMessage("设置成功。");
			log.info("addAuthority > 设置成功。");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("设置失败。");
		}
		return responseBase;
	}

	@Override
	public ResponseBase updateAuthority(AuthorityParamsDTO para) {
		ResponseBase responseBase = new ResponseBase();
		String authorityId = para.getAuthorityId();
		// 设置权限
		UserShopAppletAuthortiry _userShopAppletAuthortiry = new UserShopAppletAuthortiry();
		_userShopAppletAuthortiry.setAuthorityName(para.getAuthorityName());
		_userShopAppletAuthortiry.setAuthorityUri(para.getAuthorityUri());
		_userShopAppletAuthortiry.setDescription(para.getDesc());
		_userShopAppletAuthortiry.setStatus(1);
		_userShopAppletAuthortiry.setUpdateUser(userId);
		_userShopAppletAuthortiry.setUpdateTime(dTime);
		_userShopAppletAuthortiry.setUuid(authorityId);
		int ress = userShopAppletAuthortiryMapper.updateById(_userShopAppletAuthortiry);
		// 权限关联
		UserAuthorityRelation _userAuthorityRelation = new UserAuthorityRelation();
		_userAuthorityRelation.setAuthorityId(authorityId);
		_userAuthorityRelation.setUserId(para.getUserId());
		_userAuthorityRelation.setOrgSeq(para.getOrgSeq());
		_userAuthorityRelation.setCreateUser(userId);
		_userAuthorityRelation.setCreateTime(dTime);
		int res = userAuthorityRelationMapper.updateById(_userAuthorityRelation);
		if (ress > 0 && res > 0) {
			responseBase.setCode(1);
			responseBase.setMessage("修改成功。");
			log.info("addAuthority > 修改成功。");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("修改失败。");
		}
		return responseBase;
	}

	@Override
	public ResponseBase delAuthority(AuthorityParamsDTO para) {
		ResponseBase responseBase = new ResponseBase();
		String authorityId = para.getAuthorityId();
		// 设置权限
		UserShopAppletAuthortiry _userShopAppletAuthortiry = new UserShopAppletAuthortiry();
		_userShopAppletAuthortiry.setAuthorityName(para.getAuthorityName());
		_userShopAppletAuthortiry.setAuthorityUri(para.getAuthorityUri());
		_userShopAppletAuthortiry.setDescription(para.getDesc());
		_userShopAppletAuthortiry.setStatus(1);
		_userShopAppletAuthortiry.setUpdateUser(userId);
		_userShopAppletAuthortiry.setUpdateTime(dTime);
		_userShopAppletAuthortiry.setUuid(authorityId);
		int ress = userShopAppletAuthortiryMapper.deleteById(_userShopAppletAuthortiry);
		// 权限关联
		UserAuthorityRelation _userAuthorityRelation = new UserAuthorityRelation();
		_userAuthorityRelation.setAuthorityId(authorityId);
		_userAuthorityRelation.setUserId(para.getUserId());
		_userAuthorityRelation.setOrgSeq(para.getOrgSeq());
		_userAuthorityRelation.setCreateUser(userId);
		_userAuthorityRelation.setCreateTime(dTime);
		int res = userAuthorityRelationMapper.deleteById(_userAuthorityRelation);
		if (ress > 0 && res > 0) {
			responseBase.setCode(1);
			responseBase.setMessage("删除成功。");
			log.info("addAuthority > 删除成功。");
		} else {
			responseBase.setCode(0);
			responseBase.setMessage("删除失败。");
		}
		return responseBase;
	}

	@Override
	public ResponseRecords<MUserOrganizationDTO> getWarzoneList(ShopGoodsParamDTO para) {
		ResponseRecords<MUserOrganizationDTO> result = new ResponseRecords<MUserOrganizationDTO>();

		List<MUserOrganizationDTO> list = shopGoodsDetailMapper.getWarzone(para);
		result.setCode(1);
		result.setMessage("查询成功。");
		result.setRecords(list);
		return result;
	}

	@Override
	public ResponseRecords<MUserOrganizationDTO> getStoreList(ShopGoodsParamDTO para) {
		ResponseRecords<MUserOrganizationDTO> result = new ResponseRecords<MUserOrganizationDTO>();
		List<MUserOrganizationDTO> list = shopGoodsDetailMapper.getStoreList(para);
		result.setCode(1);
		result.setMessage("查询成功。");
		result.setRecords(list);
		return result;
	}

	@Override
	public ResponseRecords<ShopAppletAuthortiryDto> queryAuthority() {
		ResponseRecords<ShopAppletAuthortiryDto> rr = new ResponseRecords<>();

		List<UserShopAppletAuthortiry> userShopAppletAuthortiries = userShopAppletAuthortiryMapper.selectAll();

		List<ShopAppletAuthortiryDto> list = Object2ObjectUtil.parseList(userShopAppletAuthortiries,
				ShopAppletAuthortiryDto.class);
		rr.setRecords(list);
		rr.setCode(1).setMessage("查询成功");
		return rr;
	}

	@Override
	@Transactional
	public ResponseBase saveAuthority(UserAuthorityRelationReq para) {
		ResponseBase rb = new ResponseBase();

		try {
			userAuthorityRelationMapper.delete(new QueryWrapper<UserAuthorityRelation>().eq("org_seq", para.getOrgSeq())
					.eq("authority_id", para.getAuthorityId()));

			for (String userId : para.getUserList()) {
				UserAuthorityRelation userAuthorityRelation = new UserAuthorityRelation()
						.setUuid(UUID.randomUUID().toString()).setAuthorityId(para.getAuthorityId())
						.setOrgSeq(para.getOrgSeq()).setUserId(userId).setCreateTime(LocalDateTime.now());
				userAuthorityRelationMapper.insert(userAuthorityRelation);
			}
			rb.setCode(1).setMessage("权限设置成功");
		} catch (Exception e) {
			log.error("系统异常{}", e.getMessage());
			rb.setCode(0).setMessage("系统异常");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rb;
	}

	@Override
	public ResponseBase deleteAuthority(UserAuthorityRelationDto para) {
		ResponseBase rb = new ResponseBase();
		if (para.getUuid() == null || para.getUuid().equals("")) {
			rb.setCode(0).setMessage("权限移除失败");
		}
		int i = userAuthorityRelationMapper.deleteById(para.getUuid());
		rb = i > 0 ? rb.setMessage("移除权限成功").setCode(1) : rb.setCode(0).setMessage("权限移除失败");
		return rb;
	}

	@Override
	public ResponseBase updateAppletAuthority(ShopAppletAuthortiryDto para) {
		ResponseBase responseBase = new ResponseBase();
		if (para.updateCheckArgs()) {
			return responseBase.setCode(0).setMessage("uuid不能为空");
		}
		try {
			UserShopAppletAuthortiry userShopAppletAuthortiry = new UserShopAppletAuthortiry();
			BeanUtils.copyProperties(para, userShopAppletAuthortiry);
			userShopAppletAuthortiry.setUpdateTime(LocalDateTime.now());
			userShopAppletAuthortiry.setUpdateUser(JwtTokenUtil.currUser());
			userShopAppletAuthortiryMapper.updateById(userShopAppletAuthortiry);
			responseBase.setCode(1).setMessage("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseBase.setMessage("更新失败").setCode(0);
		}

		return responseBase;
	}

	@Override
	public ResponseBase addAppletAuthority(ShopAppletAuthortiryDto para) {
		ResponseBase responseBase = new ResponseBase();
		if (para.addCheckArgs()) {
			return responseBase.setCode(0).setMessage("参数不能为空");
		}
		try {
			UserShopAppletAuthortiry userShopAppletAuthortiry = new UserShopAppletAuthortiry();
			BeanUtils.copyProperties(para, userShopAppletAuthortiry);
			userShopAppletAuthortiry.setUuid(UUID.randomUUID().toString());
			userShopAppletAuthortiry.setCreateTime(LocalDateTime.now());
			userShopAppletAuthortiry.setCreateUser(JwtTokenUtil.currUser());
			userShopAppletAuthortiry.setStatus(1);
			userShopAppletAuthortiryMapper.insert(userShopAppletAuthortiry);
			responseBase.setCode(1).setMessage("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseBase.setMessage("增加失败").setCode(0);
		}

		return responseBase;
	}

	@Override
	public ResponseBase deleteAppletAuthority(String uuid) {
		ResponseBase responseBase = new ResponseBase();
		if (uuid == null || uuid.equals("")) {
			responseBase.setCode(0).setMessage("参数uuid不能为空");
		}
		try {
			int i = userShopAppletAuthortiryMapper.deleteById(uuid);
		} catch (Exception e) {
			e.printStackTrace();
			responseBase.setCode(0).setMessage("删除失败");
		}
		responseBase.setCode(1).setMessage("删除成功");
		return responseBase;
	}

	@Override
	public ResponseResult<MUserOrganizationDTO> getWarzoneName(ShopGoodsParamDTO para) {
		ResponseResult<MUserOrganizationDTO> result = new ResponseResult<MUserOrganizationDTO>();

		MUserOrganizationDTO dto = shopGoodsDetailMapper.getWarzoneName(para);
		// MUserOrganizationDTO dto = shopGoodsDetailMapper.getWarzone(para);
		result.setCode(1);
		result.setMessage("查询成功。");
		result.setResult(dto);
		return result;
	}

	@Override
	public ResponseResult<AppletAuthorityDTO> getAuthority(AppletAuthorityDTO dto) {
		ResponseResult<AppletAuthorityDTO> responseResult = new ResponseResult<>();
		try {
			List<ShopAppletAuthortiryDto> list = dto.getUserType() == 4 || dto.getUserType() == 5
					? userShopAppletAuthortiryMapper.getAllAuthority()
					: userShopAppletAuthortiryMapper.getAuthority(dto.getOrgSeq(), dto.getUserId());
			dto.setAuthorityList(list);
			responseResult.setResult(dto).setCode(1).setMessage("获取成功");
		} catch (Exception e) {
			log.error("获取权限失败:{}", e.getMessage());
			responseResult.setCode(0).setMessage("获取权限失败");
		}
		return responseResult;
	}

	@Override
	public ResponseRecords<MUserOrganizationDTO> getStoreListByWar(ShopGoodsParamDTO para) {
		ResponseRecords<MUserOrganizationDTO> rr = new ResponseRecords<>();
		try {
			List<MUserOrganizationDTO> list = shopGoodsDetailMapper.getStoreListByWar(para);
			rr.setRecords(list);
			rr.setCode(1);
			rr.setMessage("查询成功");
		} catch (Exception e) {
			log.error("查詢异常{}", e.getMessage());
			rr.setCode(0);
			rr.setMessage("查詢异常");
		}
		return rr;
	}

	@Override
	public ResponseResult<MUserOrganizationDTO> getWarzoneNameByOrgSeq(ShopGoodsParamDTO para) {
		ResponseResult<MUserOrganizationDTO> result = new ResponseResult<MUserOrganizationDTO>();

		MUserOrganizationDTO dto = shopGoodsDetailMapper.getWarzoneNameByOrgSeq(para);
		result.setCode(1);
		result.setMessage("查询成功。");
		result.setResult(dto);
		return result;
	}

	@Override
	public ResponseRecords<UserStoreInfoDTO> queryStoreListByKey(ShopGoodsParamDTO para) {
		ResponseRecords<UserStoreInfoDTO> result = new ResponseRecords<UserStoreInfoDTO>();
		try {
			List<UserStoreInfoDTO> list = shopGoodsDetailMapper.queryStoreListByKey(para);
			result.setCode(1);
			result.setMessage("查询成功。");
			result.setRecords(list);
		} catch (Exception e) {
			log.error("查詢异常{}", e.getMessage());
			result.setCode(0);
			result.setMessage("查詢异常");
		}
		return result;
	}

}