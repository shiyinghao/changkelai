package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.StoreTypeDTO;
import com.icss.newretail.model.StoreTypeRequest;
import com.icss.newretail.user.entity.UserStoreType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreTypeMapper extends BaseMapper<UserStoreType> {


    List<StoreTypeDTO> queryStoreTypes(@Param("request") StoreTypeRequest para, @Param("tenantId") String tenantId);
}
