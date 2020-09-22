package com.icss.newretail.service.user;

import com.icss.newretail.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    // User ===================================================
    public ResponseBase createUser(UserInfoDTO user);

    /**
     * 2019-3-20：需要确认一下如果修改组织机构，除了修改数据库信息，还有没有其他的注意事项。
     * 可以以先直接使用修改用户的功能，将来如果有特殊的诉求，再将组织机构修改独立出去。
     * 
     * @param user
     * @return
     */
    public ResponseBase modifyUser(UserInfoDTO user);

    public ResponseBase deleteUser(String userId);

    public ResponseBase changePassword(String userId, String oldPassword, String newPassword);

    public ResponseBase setUserRoles(String userId, List<String> roleCodes);

    // Password ===================================================
    /**
     * 1. Send email to user with reset password URL 2.
     * 
     * @param userAuthId:用户登录用的信息，能唯一确定一个用户
     * @param authType:认证方式
     * @return
     */
    public ResponseBase forgetPassword(String userAuthId, Integer authType);

    public ResponseBase resetPassword(String userId, String newPassword);


    /**
     * 根据用户ID查询用户配置信息
     * 
     * @param userId
     * @return List<UserCustomizedParamDTO>
     */
    public ResponseBase queryUserCustomizedParams(String userId);

    /**
     * 根据用户单位、岗位查询用户信息
     * 
     * @param pageData
     * @return
     */
    public ResponseResultPage<UserInfoDTO> queryUsers(PageData<UserRequest> pageData);

    public ResponseRecords<RoleDTO> getUserRoles(String userId);

    /**
     * 查询用户菜单列表
     * 
     * @param para
     * @return
     */
    public ResponseRecords<MenuDTO> getUserMenus( UserMenuRequest para);

    /**
     * 根据用户ID查询基本信息
     * 
     * @param userId
     * @return
     */
    public ResponseResult<UserInfoDTO> queryUserById(String userId);
    
    // UserType ===================================================
    public ResponseBase createUserType(UserTypeDTO userType);

    public ResponseBase modifyUserType(UserTypeDTO userType);

    public ResponseBase deleteUserType(String userType);

    public ResponseResult<UserTypeDTO> queryUserTypeById(String userType);
    
    public ResponseRecords<UserTypeDTO> queryUserTypes(String userTypeName);

    /**
     * 店长授权
     *
     * @param orgSeq
     * @param password
     * @return
     */
    public ResponseBase grantByStoreManager(String orgSeq, String password);
    /**
     * 组织类型列表查询
    * @author fangxm 
    * @date 2019年10月23日 
    * @param 
    * @return
    * @description:
     */
    public ResponseRecords<OrganizationTypeDTO> getUserOrgType( String orgType,String orgTypeName);


    public ResponseRecords<OrgReslutDTO> queryStoreOrgseq(String orgSeq);

    public OrganizationDTO getOrgSeqType(String orgSeq);

    ResponseResultPage<UserInfoDTO> queryUsersById(PageData<UserRequest> pageData);

    ResponseRecords<UserInfoDTO> queryStorePerson(UserRequest userRequest);

    ResponseRecords<UserInfoDTO> queryManage(UserRequest userRequest);

    ResponseResultPage<UserInfoDTO> queryStoreUsers(PageData<UserRequest> pageData);

    ResponseResult<UserInfoDTO> queryManangerById(String orgSeq);

    ResponseResult<MemberUserDTO> queryMemberUserById(String userId);

    ResponseResult<MemberUserDTO> queryManagerById(String orgSeq);
    /**
     *查询店员列表
     * @return
     * @Author zys
     */
    ResponseRecords queryShopAssNameList(Map<String,String> map);

    /**
     * 根据店铺 id 查询店长或者店员信息
     * @param orgSeq
     * @param userType : 用户类型
     * @return
     */
    ResponseRecords<UserInfoDTO> queryShopOwner(String orgSeq, String userType);

    /**
     * 根据用户 id 查询用户手机号
     * @param userId
     * @return
     */
    UserInfoDTO queryTel(String userId);

    /**
     * 查询基地经理
     * @param orgSeq
     * @return
     */
    ResponseRecords<UserInfoDTO> queryManagerList(String orgSeq);

    /**
     * 根据基地 orgSeq 查询战区信息
     * @param orgSeq
     * @return
     */
    ResponseResult<OrganizationDTO> queryUpOrg(String orgSeq);

    /**
     * 智能巡店-员工信息
     * @param orgSeq
     * @Author wangjie
     * @return
     */
    public ResponseResultPage<UserInfoDTO> queryOrgUserList(PageData<UserInfoParamDTO> pageData);


    List<BaseDTO> queryManagerListByUpOrg(String orgSeq);

    String queryOrgLV(String orgSeq);

    List<OrganizationDTO> queryOrgInfo(List<String> orgSeqList);

    OrganizationDTO queryOrgInfoByOrgSeq(String orgSeq);


    List<OrganizationDTO> queryDownOrg(String orgSeq);

    /**
     * 根据基地orgSeqList查询基地业务经理信息
     * @param orgSeqList
     * @return
     */
    ResponseRecords<UserInfoDTO> queryBaseUserList(List<String> orgSeqList);

    /**
     * 根据基地经理用户id查询所在基地及战区
     * @param userId
     * @return
     */
    ResponseResult<UserOrganizationInfoDTO> queryUserOrganizationInfo(String userId);

    Map<String,String> queryUserAllByIds(Set<String> userIds);
    
    /**
     * 根据orgSeq 查询用户列表信息系
     * lq 2020.7.23
     * @param orgSeq
     * @return
     */
    ResponseRecords<UserInfoDTO> queryUserListByOrgSeq(String orgSeq);
}