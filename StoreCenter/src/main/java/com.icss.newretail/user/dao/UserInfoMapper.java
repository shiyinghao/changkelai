package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-04-08
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {


    List<UserCustomizedParamDTO> queryUserCustomizedParams(@Param("userId") String userId);

    Page<UserInfoDTO> queryUsers(@Param("page") Page<UserInfoDTO> page, @Param("request") UserRequest userRequest);

    UserInfoDTO queryUserById(@Param("userId") String userId);

    Integer createAccount(@Param("user") UserInfo user, @Param("type") String type);

    TenantDTO queryHomepageInfo(@Param("tenantId") String tenantId);

    UserInfoDTO queryStoreManagerByStoreId(String orgSeq);

    Page<UserInfoDTO> qryClerk(@Param("page") Page<UserInfoDTO> page, @Param("userclerkrequest") UserClerkRequest userclerkrequest);

    Page<UserQryStoreDTO> qryStore
            (@Param("page") Page<UserQryStoreDTO> page, @Param("userstorerequest") UserStoreRequest userstorerequest,
             @Param("type") String type, @Param("str") String str);

    List<UserStoreNameDTO> queryStoreByUserId(@Param("userId") String userId);

    Integer updateAuthInfo(@Param("userId") String userId, @Param("account") String account, @Param("type") int type);

    Page<UserInfoDTO> queryUsersById(@Param("pg") Page<UserInfoDTO> page, @Param("para") UserRequest condition);

    List<UserInfoDTO> queryStorePerson(@Param("para") UserRequest userRequest);

    List<UserInfoDTO> queryManage(@Param("para") UserRequest userRequest);

    Page<UserInfoDTO> queryStoreUsers(@Param("pg") Page<UserInfoDTO> page,@Param("para") UserRequest condition);

    ShopUserInfoDTO queryUserInfo(@Param("userId") String userId);

    UserInfoDTO queryManangerById(String orgSeq);

    OrganizationDTO queryOrgType(@Param("orgSeq") String orgSeq);

    /**
     * 根据基地 orgSeq 查询战区信息
     * @param orgSeq
     * @return
     */
    OrganizationDTO queryUpOrg(@Param("orgSeq")String orgSeq);

    MemberUserDTO queryMemberUserById(String userId);

    MemberUserDTO queryManagerById(String orgSeq);

    String getPhoneAsByOwn(@Param("phone") String phone);

    /**
     * 查询基地经理
     * @param orgSeq
     * @return
     */
    List<UserInfoDTO> queryManagerList(@Param("orgSeqs") List<String> orgSeqs);

    /**
     * 根据店铺 id 查询店长或者店员信息
     * @param orgSeq
     * @param userType : 用户类型
     * @return
     */
    List<UserInfoDTO> queryShopOwner(@Param("orgSeq") String orgSeq, @Param("userType")String userType);

    /**
     * 根据用户 id 查询用户手机号
     * @param userId
     * @return
     */
    UserInfoDTO queryTel(@Param("userId") String userId);

    Page<StoreUserInfoDTO> queryStoreUserInfo(@Param("page") Page<UserInfo> page, @Param("storeUserInfoDTO") StoreUserInfoDTO storeUserInfoDTO, @Param("type") String type);

    Page<UserInfoDTO> queryUserInfoList(@Param("page") Page<UserInfoDTO> page,@Param("param") UserInfoParamDTO param);

    List<UserInfoDTO> queryUserInfoList(@Param("orgList") List<String> orgList,@Param("realName") String realName);

    /**
     * 通过 up_org_seq 查询下级组织
     * @param orgSeq
     * @return
     */
    List<BaseDTO> queryManagerListByUpOrg(@Param("orgSeq")String orgSeq);

    BaseDTO queryOrg(@Param("orgSeq")String orgSeq);

    List<OrganizationDTO> queryOrgInfo(@Param("orgSeqList") List<String> orgSeqList);

    OrganizationDTO queryOrgInfoByOrgSeq(@Param("orgSeq") String orgSeq);

    List<OrganizationDTO> queryDownOrg(@Param("orgSeq") String orgSeq);

    List<UserInfoDTO> queryBaseUserList(@Param("orgSeqList") List<String> orgSeqList);

    List<UserOrganizationInfoDTO> queryUserOrganizationInfo(@Param("userId") String userId);

    List<UserReviewRecordDTO> qryStoreRecedListInfo(@Param("auth_code") String auth_code);

    List<String> queryAccountById(@Param("userId") String userId);

    List<OrgList> queryOrgListById(@Param("userId") String userId);

    List<RoleList> queryRoleListById(@Param("userId") String userId);

    Page<UserStoreVerifyDTO> getAllAuthCode(@Param("page") Page<AddAuthCode> page, @Param("param") AddAuthCode param);

    ArrayList<UserQryStoreDTO> qryStoreAmount(@Param("userstorerequest") UserStoreRequest userStoreInfo);
}
