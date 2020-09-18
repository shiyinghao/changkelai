package com.icss.newretail.user.dao;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.user.entity.WechatOrgCard;

/**
 * <p>
 * 店铺微信二维码信息 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
public interface WechatOrgCardMapper extends BaseMapper<WechatOrgCard> {
	
	WechatOrgCard getWechatOrgCard(Map<String,Object> map);

}
