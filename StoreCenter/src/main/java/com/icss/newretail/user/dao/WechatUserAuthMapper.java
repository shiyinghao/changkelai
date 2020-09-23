package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.WechatUserAuthDTO;
import com.icss.newretail.user.entity.WechatUserAuth;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 微信人员认证关系表 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
public interface WechatUserAuthMapper extends BaseMapper<WechatUserAuth> {
	WechatUserAuthDTO getWechatUserAuthByOpenId(HashMap<String, Object> query);

	List<WechatUserAuthDTO> getWechatUserAuthListByOpenId(HashMap<String, Object> query);

	Integer checkAuthAnyOfUser(HashMap<String, Object> query);

	WechatUserAuthDTO getWechatUserAuthByAuthAccount(HashMap<String, Object> query);

	WechatUserAuthDTO getWechatUserAuthByAuthAccountOpenId(HashMap<String, Object> query);
}
