package com.icss.newretail.user.dao;

import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserCardInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 名片信息表 Mapper 接口
 * </p>
 *
 * @author yanghu
 * @since 2020-07-16
 */
@Repository
public interface UserCardInfoMapper extends BaseMapper<UserCardInfo> {

    Integer updateStoreCardConfig(@Param("request") StoreCardReq storeCardReq);

    CardConfigDTO queryStoreCardConfig(@Param("request") StoreCardReq storeCardReq);

    List<StoreUsableCardDTO> queryStoreUsableCard(@Param("orgSeq") String orgSeq);

    ServiceRecordDTO queryStoreInfo(@Param("orgSeq") String orgSeq);

    ServiceRecordDTO queryUserInfo(@Param("orgSeq") String orgSeq, @Param("userId") String userId);

    void updateNewestActivityDownload();

    UserOrgRelationDTO queryUserType(@Param("userId") String userId);

    void updateStoredz(@Param("para") StoreCardReq storeCardReq);

    void updateStoreManager(@Param("para") StoreCardReq storeCardReq);
}
