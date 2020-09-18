package com.icss.newretail.user.dao;

import com.icss.newretail.model.StoreParamDTO;
import com.icss.newretail.user.entity.UserStoreParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreParamMapper {

    Integer saveStoreParams(List<UserStoreParam> userStoreParams);

    Integer deleteStoreParam(@Param("paramId") String paramId);

    List<StoreParamDTO> queryStoreParams(@Param("storeId")String storeId);
}
