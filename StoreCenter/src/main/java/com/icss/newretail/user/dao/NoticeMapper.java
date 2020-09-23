package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.NoticeDTO;
import com.icss.newretail.model.NoticeRequest;
import com.icss.newretail.user.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 通知公告表 Mapper 接口
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-07-11
 */
public interface NoticeMapper extends BaseMapper<Notice> {

  NoticeDTO queryNoticeById(String noticeId);

  Page<NoticeDTO> queryNotices(@Param("pg") Page<NoticeDTO> page, @Param("request") NoticeRequest condition, @Param("tenantId") String tenantId);
}
