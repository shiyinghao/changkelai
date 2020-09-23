package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserStoreInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMapper extends BaseMapper<UserStoreInfo> {

  List<OrganizationInfoDTO> queryStores(@Param("request") StoreRequest storeRequest,
                                        @Param("tenantId") String tenantId);

  Page<OrganizationInfoDTO> queryStoreByAreaSeq(@Param("page") Page<OrganizationInfoDTO> page,
                                                @Param("condition") StoreRequest condition,
                                                @Param("tenantId") String tenantId);

  List<OrganizationInfoDTO> getRecoStore(@Param("lng") String lng, @Param("lat") String lat,
                                         @Param("tenantId") String tenantId);

  Page<StoreInfoDTO> queryStoreInfo(@Param("page") Page<StoreInfoDTO> page,
                                    @Param("storeInfoRequest") StoreInfoRequest storeInfoRequest, @Param("list") String[] list, @Param("zqList") String[] zqlist);

  List<StoreInfoDTO> getStoresByUpOrgSeq(String upOrgSeq);

  StoreInfoDTO getStoreInfo(@Param("orgSeq") String orgSeq);

  List<StoreInfoDTO> getStoresByUserId(String userId, String tenantId);

  Page<UserStoreInfoDTO> queryStored(@Param("pg") Page<UserStoreInfoDTO> page, @Param("request") StoreMessage storeMessage);

  List<UserStoreInfoDTO> queryStoreById(@Param("request") StoreMessage storeMessage);

  UserStoreInfo queryStoreByLngAndlat(@Param("request") ConsumerInfoRequest consumerInfoRequest);

  List<RecommendStoreDTO> queryfootPrint(@Param("list") List<String> list);

  StoreInfoDTO queryStoreNameById(String orgSeq);

  List<RecommendStoreDTO> searchStore(@Param("list") List<String> list);

    Page<UserStoreInfoDTO> queryOftenStores(@Param("pg") Page<UserStoreInfoDTO> page, @Param("request") StoreMessage condition, @Param("list") List<String> list);

}
