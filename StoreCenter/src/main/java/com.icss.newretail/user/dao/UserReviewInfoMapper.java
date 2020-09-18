package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.UserReviewInfoDTO;
import com.icss.newretail.model.UserReviewRequest;
import com.icss.newretail.user.entity.UserReviewInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 店铺审核信息表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-03-23
 */
public interface UserReviewInfoMapper extends BaseMapper<UserReviewInfo> {

    Page<UserReviewInfoDTO> queryStoreInfo(@Param("page") Page<UserReviewInfoDTO> page, @Param("userReviewRequest") UserReviewRequest userReviewRequest);

    Page<UserReviewInfoDTO> queryStoreInfoByJD(@Param("page") Page<UserReviewInfoDTO> page,@Param("userReviewRequest") UserReviewRequest userReviewRequest, @Param("baseCode") String baseCode);

    Page<UserReviewInfoDTO> queryStoreInfoByMD(@Param("page") Page<UserReviewInfoDTO> page,@Param("userReviewRequest") UserReviewRequest userReviewRequest,@Param("orgSeq") String orgSeq);
    Integer getSumNumber(String authCode);
}
