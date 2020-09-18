package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.TipsDTO;
import com.icss.newretail.model.TipsRequest;
import com.icss.newretail.user.entity.WechatTips;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 微信应用使用条款 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-07-10
 */
public interface WechatTipsMapper extends BaseMapper<WechatTips> {
  TipsDTO getTips(@Param("appId") String appId,@Param("openId")String openId, @Param("tenantId") String tenantId);
  TipsDTO queryTipsById(@Param("tipsId") String tipsId);
  Page<TipsDTO> queryTips(@Param("pg")Page<TipsDTO> page, @Param("param")TipsRequest param, @Param("tenantId")String tenantId);
}
