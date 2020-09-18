package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.WechatInfoDTO;
import com.icss.newretail.user.entity.WechatInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 店铺公众号信息 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
public interface WechatInfoMapper extends BaseMapper<WechatInfo> {

  WechatInfoDTO getWechatInfoByOrgSeq(@Param("orgSeq") String orgSeq);

  String getWechatInfoByUserId(String shareId);

  String getorgSeqByUserId(String shareId);
}
