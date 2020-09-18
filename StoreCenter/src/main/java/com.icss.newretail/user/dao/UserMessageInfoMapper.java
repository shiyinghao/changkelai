package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.UserMessageInfoDTO;
import com.icss.newretail.model.UserMessageReadRecordDTO;
import com.icss.newretail.user.entity.UserMessageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息通知表 Mapper 接口
 * </p>
 *
 * @author syh
 * @since 2020-08-20
 */
public interface UserMessageInfoMapper extends BaseMapper<UserMessageInfo> {

    Page<UserMessageInfoDTO> queryUserMessage(@Param("pg") Page<UserMessageInfoDTO> page,@Param("para") UserMessageInfoDTO condition);

    Page<UserMessageReadRecordDTO> queryUserMessageInfo(@Param("pg") Page<UserMessageReadRecordDTO> page,@Param("para") UserMessageReadRecordDTO condition);
}
