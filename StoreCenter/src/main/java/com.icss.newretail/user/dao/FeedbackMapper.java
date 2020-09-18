package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.FeedbackDTO;
import com.icss.newretail.user.entity.Feedback;
import org.apache.ibatis.annotations.Param;

public interface FeedbackMapper extends BaseMapper<Feedback> {
    Page<FeedbackDTO> queryFeedback(@Param("pg") Page<FeedbackDTO> page, @Param("para") FeedbackDTO condition);

    FeedbackDTO queryFeedbackInfoById(String uuid);
}
