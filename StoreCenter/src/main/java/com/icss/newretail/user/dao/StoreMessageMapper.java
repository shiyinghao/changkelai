package com.icss.newretail.user.dao;

import java.util.List;

import com.icss.newretail.user.entity.UserStoreMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.StoreMessageRequest;
import com.icss.newretail.model.UserStoreMessageCountDTO;
import com.icss.newretail.model.UserStoreMessageDTO;

@Repository
public interface StoreMessageMapper extends BaseMapper<UserStoreMessage>{
    Page<UserStoreMessageDTO> receiveMessages(@Param("page") Page<UserStoreMessageDTO> page,
                                              @Param("storeMessageRequest") StoreMessageRequest storeMessageRequest, @Param("tenantId") String tenantId);

    Integer readMessage(@Param("messageId") String messageId);

    List<UserStoreMessageCountDTO> countMessages(@Param("storeId") String storeId);
}