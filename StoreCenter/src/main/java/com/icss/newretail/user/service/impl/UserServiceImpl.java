package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.BaseDTO;
import com.icss.newretail.model.MemberUserDTO;
import com.icss.newretail.model.MenuDTO;
import com.icss.newretail.model.OrgList;
import com.icss.newretail.model.OrgReslutDTO;
import com.icss.newretail.model.OrganizationDTO;
import com.icss.newretail.model.OrganizationTypeDTO;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.RoleDTO;
import com.icss.newretail.model.RoleList;
import com.icss.newretail.model.UserCustomizedParamDTO;
import com.icss.newretail.model.UserInfoDTO;
import com.icss.newretail.model.UserInfoParamDTO;
import com.icss.newretail.model.UserMenuRequest;
import com.icss.newretail.model.UserOrganizationInfoDTO;
import com.icss.newretail.model.UserRequest;
import com.icss.newretail.model.UserTypeDTO;
import com.icss.newretail.service.user.UserService;
import com.icss.newretail.user.dao.OrganizationMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import com.icss.newretail.user.dao.UserLoginInfoMapper;
import com.icss.newretail.user.dao.UserMenuMapper;
import com.icss.newretail.user.dao.UserOrgRelationMapper;
import com.icss.newretail.user.dao.UserRoleMapper;
import com.icss.newretail.user.dao.UserUserRoleRelationMapper;
import com.icss.newretail.user.entity.UserInfo;
import com.icss.newretail.user.entity.UserLoginInfo;
import com.icss.newretail.user.entity.UserOrgRelation;
import com.icss.newretail.user.entity.UserType;
import com.icss.newretail.user.entity.UserUserRoleRelation;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.MD5Util;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserUserRoleRelationMapper userUserRoleRelationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMenuMapper userMenuMapper;

    @Autowired
    private UserTypeMapper userTypeMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserOrgRelationMapper userOrgRelationMapper;

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    @Override
    @Transactional
    public ResponseBase createUser(UserInfoDTO userInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        Map<String, Object> map = new HashMap<String, Object>();

        if (userInfoDTO.getUserType() == null || userInfoDTO.getUserType().equals("")) {
            responseBase.setCode(0).setMessage("用户类型不能为空");
            return responseBase;
        }
        try {
            //维护老字段 用户信息表中的org_seq  取其中第一个
            if (userInfoDTO.getOrgList() != null && userInfoDTO.getOrgList().get(0).getOrgSeq() != null) {
                userInfoDTO.setOrgSeq(userInfoDTO.getOrgList().get(0).getOrgSeq());
            }
            UserInfo userInfo = Object2ObjectUtil.parseObject(userInfoDTO, UserInfo.class);
            //如果创建成功  这个就是用户id
            userInfo.setUserId(UUID.randomUUID().toString());
            //如果  创建的账号组织属于店铺-----
            //人员  属于店主  或者  店长  需要先查询  该店铺是否存在店主  该店铺是否存在店长
            List<OrgList> orgList1 = userInfoDTO.getOrgList();
            //update ----20200403 --yanghu  --增加之后一对多组织  一个用户可以有多个组织
            //1.0 插入表格 用户组织关系表
            for (OrgList orgList : orgList1) {
                if ("4".equals(userInfoDTO.getUserType())) {
                    int i = organizationMapper.queryCount(orgList.getOrgSeq(), 4);
                    if (i > 0) {
                        throw new Exception("该门店已有店主!创建用户失败");
                    }
                }
                if ("5".equals(userInfoDTO.getUserType())) {
                    int i = organizationMapper.queryCount(orgList.getOrgSeq(), 5);
                    if (i > 0) {
                        throw new Exception("该门店已有店长!创建用户失败");
                    }
                }
                UserOrgRelation userOrgRelation = new UserOrgRelation();
                userOrgRelation.setUuid(UUID.randomUUID().toString()).setStatus(1).setCreateTime(LocalDateTime.now())
                        .setCreateUser(JwtTokenUtil.currUser()).setUserId(userInfo.getUserId()).setOrgSeq(orgList.getOrgSeq()).
                        setUserType(Integer.parseInt(userInfo.getUserType())).setUpdateUser(JwtTokenUtil.currUser());
                userOrgRelationMapper.insert(userOrgRelation);
            }
            if (userInfoDTO.getPassword() == null || userInfoDTO.getPassword().equals("")) {
                throw new Exception("密码不能为空");
            }
            userInfo.setPassword(MD5Util.toMD5(userInfoDTO.getPassword()));

            //验证用户名、手机号、邮箱是否重复、是否为空
            if (StringUtils.isBlank(userInfo.getUserName())) {
                throw new Exception("账号不能为空");
            } else {
                map.put("user_name", userInfo.getUserName());
                if (userInfoMapper.selectByMap(map).size() > 0) {
                    throw new Exception("账号已被注册");
                } else {
//                    userInfoMapper.createAccount(userInfo, "userName");
                    UserLoginInfo userLoginInfo = new UserLoginInfo().setUuid(UUID.randomUUID().toString()).setUserId(userInfo.getUserId())
                            .setPassword(userInfo.getPassword()).setOrgSeq(userInfoDTO.getOrgSeq()).setStatus(1)
                            .setAuthCode(userInfo.getUserName()).setCreateUser(JwtTokenUtil.currUser()).setUpdateUser(JwtTokenUtil.currUser())
                            .setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
                    userLoginInfoMapper.insert(userLoginInfo);
                }
            }
            if (StringUtils.isNotBlank(userInfo.getEmail())) {
                map.clear();
                map.put("email", userInfo.getEmail());
                if (userInfoMapper.selectByMap(map).size() > 0) {
                    throw new Exception("邮箱已被注册");
                }
            }
            if (StringUtils.isNotBlank(userInfo.getTel())) {
                map.clear();
                map.put("tel", userInfo.getTel());
                if (userInfoMapper.selectByMap(map).size() > 0) {
                    throw new Exception("手机号已被注册");
                }
            }
            userInfo.setCreateUser(JwtTokenUtil.currUser());
            userInfo.setUpdateUser(JwtTokenUtil.currUser());
            userInfo.setUpdateTime(LocalDateTime.now());
            userInfoMapper.insert(userInfo);
            responseBase.setCode(1);
            responseBase.setMessage("账号创建成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            responseBase.setMessage(ex.getMessage());
            responseBase.setCode(0);
            log.error("UserServiceImpl|createUser->创建账号[" + ex.getLocalizedMessage() + "]");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
        }
        return responseBase;
    }

    /**
     * 根据店铺 id 查询店长或者店员信息
     *
     * @param orgSeq
     * @param userType : 用户类型
     * @return
     */
    public ResponseRecords<UserInfoDTO> queryShopOwner(String orgSeq, String userType) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        try {
            List<UserInfoDTO> userInfoList = userInfoMapper.queryShopOwner(orgSeq, userType);
            if (CollectionUtils.isEmpty(userInfoList)) {
                result.setCode(0);
                result.setMessage("没有查询到用户信息");
            } else {
                result.setCode(1);
                result.setMessage("查询到用户信息");
                result.setRecords(userInfoList);
            }
        } catch (Exception e) {
            log.error("log.queryShopOwner -> 查询店长/店员失败");
            result.setCode(0);
            result.setMessage("log.queryShopOwner ->" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据用户 id 查询用户手机号
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoDTO queryTel(String userId) {
        return userInfoMapper.queryTel(userId);
    }

    /**
     * 查询基地经理
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseRecords<UserInfoDTO> queryManagerList(String orgSeq) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        try {
            List<UserInfoDTO> list = new ArrayList<>();
            OrganizationDTO organization = userInfoMapper.queryOrgType(orgSeq);
            List<String> orgList = new ArrayList<>();
            if ("2".equals(organization.getOrgType())) {
                //通过 up_org_seq 查询下级组织
                List<BaseDTO> bases = userInfoMapper.queryManagerListByUpOrg(orgSeq);
                for (BaseDTO base : bases) {
                    orgList.add(base.getOrgSeq());
                }
                list = userInfoMapper.queryManagerList(orgList);
            } else if ("3".equals(organization.getOrgType())) {
                orgList.add(orgSeq);
                list = userInfoMapper.queryManagerList(orgList);
            }
            result.setCode(1);
            result.setMessage("查询成功");
            result.setRecords(list);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("查询用户信息失败");
            return result;
        }
        return result;
    }

    /**
     * 根据基地 orgSeq 查询战区信息
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseResult<OrganizationDTO> queryUpOrg(String orgSeq) {
        ResponseResult<OrganizationDTO> result = new ResponseResult<>();
        try {
            OrganizationDTO warZone = userInfoMapper.queryUpOrg(orgSeq);
            result.setCode(1);
            result.setMessage("查询战区信息成功");
            result.setResult(warZone);
        } catch (Exception e) {
            log.error("" + e.getMessage());
            result.setCode(0);
            result.setMessage("查询战区信息失败");
        }
        return result;
    }

    /**
     * 2019-3-20：需要确认一下如果修改组织机构，除了修改数据库信息，还有没有其他的注意事项。
     * 可以以先直接使用修改用户的功能，将来如果有特殊的诉求，再将组织机构修改独立出去。
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public ResponseBase modifyUser(UserInfoDTO user) {
        ResponseBase responseBase = new ResponseBase();
        try {
            if (user != null && StringUtils.isNotBlank(user.getUserId())) {

                //modifyUser 修改手机号时要判断  手机号不能重复
                if (user.getTel() == null || user.getTel().equals("")) {
                    responseBase.setCode(0).setMessage("手机号不能为空");
                    return responseBase;
                }
                QueryWrapper<UserInfo> qw = new QueryWrapper();
                qw.eq("tel", user.getTel());
                qw.ne("user_id", user.getUserId());
                Integer integer = userInfoMapper.selectCount(qw);
                if (integer > 0) {
                    responseBase.setCode(0).setMessage("手机号已经存在");
                    return responseBase;
                }
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(user, userInfo);
                if (StringUtils.isNotBlank(user.getPassword())) {
                    userInfo.setPassword(MD5Util.toMD5(user.getPassword()));
                }
                userInfo.setUpdateTime(LocalDateTime.now());
                userInfo.setUpdateUser(JwtTokenUtil.currUser());
                int res = userInfoMapper.updateById(userInfo);
                if (res == 1) {
                    if (StringUtils.isNotBlank(user.getUserName())) {
                        UserLoginInfo userLoginInfo = new UserLoginInfo()
                                .setUserId(userInfo.getUserId()).setAuthCode(user.getUserName())
                                .setUpdateUser(JwtTokenUtil.currUser()).setOrgSeq(user.getOrgList().get(0).getOrgSeq());
                        userLoginInfoMapper.updateUserLoginInfo(userLoginInfo);
                    }
                    if (CollectionUtils.isNotEmpty(user.getOrgList())) {
                        userOrgRelationMapper.delete(new QueryWrapper<UserOrgRelation>().eq("user_id", user.getUserId()));
                        List<UserOrgRelation> list = new ArrayList<>();
                        for (OrgList org : user.getOrgList()) {
                            if ("4".equals(user.getUserType())) {
                                int i = organizationMapper.queryCount(org.getOrgSeq(), 4);
                                if (i > 0) {
                                    throw new Exception("该门店已有店主!创建用户失败");
                                }
                            }
                            if ("5".equals(user.getUserType())) {
                                int i = organizationMapper.queryCount(org.getOrgSeq(), 5);
                                if (i > 0) {
                                    throw new Exception("该门店已有店长!创建用户失败");
                                }
                            }
                            UserOrgRelation userOrgRelation = new UserOrgRelation();
                            userOrgRelation.setUuid(UUID.randomUUID().toString());
                            userOrgRelation.setUserId(user.getUserId());
                            userOrgRelation.setUserType(Integer.parseInt(user.getUserType()));
                            userOrgRelation.setCreateUser(JwtTokenUtil.currUser());
                            userOrgRelation.setUpdateUser(JwtTokenUtil.currUser());
                            userOrgRelation.setOrgSeq(org.getOrgSeq());
                            list.add(userOrgRelation);
                        }
                        userOrgRelationMapper.batchAdd(list);
                    }
                    responseBase.setCode(1);
                    responseBase.setMessage("修改用户信息成功");
                } else {
                    responseBase.setCode(0);
                    responseBase.setMessage("修改用户信息失败");
                }
            } else {
                throw new RuntimeException("参数异常");
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
            log.error("UserServiceImpl|modifyUser->更新异常[" + e.getLocalizedMessage() + "]");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //事务回滚
        }
        return responseBase;
    }

    @Override
    public ResponseBase deleteUser(String userId) {
        return null;
    }

    /**
     * 修改用户密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Override
    public ResponseBase changePassword(String userId, String oldPassword, String newPassword) {
        ResponseBase responseBase = new ResponseBase();
        try {
            UserLoginInfo oldUser = userLoginInfoMapper.selectOne(new QueryWrapper<UserLoginInfo>().eq("auth_code",userId));
            if (oldUser == null) {
                responseBase.setCode(0);
                responseBase.setMessage("要修改密码的用户不存在");
                return responseBase;
            }
            if (!MD5Util.toMD5(oldPassword).equals(oldUser.getPassword())) {
                responseBase.setCode(0);
                responseBase.setMessage("输入的旧密码不正确");
                return responseBase;
            }
            oldUser.setPassword(MD5Util.toMD5(newPassword));
            int res = userLoginInfoMapper.updateById(oldUser);
            if (res == 1) {
                responseBase.setCode(1);
                responseBase.setMessage("修改密码成功");
                log.info("用户密码修改成功!账号：{}", userId);
            } else {
                responseBase.setCode(0);
                responseBase.setMessage("修改密码失败");
                log.info("用户密码修改失败!账号：{}", userId);
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
            log.info("用户密码修改失败!账号：{}", userId);
        }
        return responseBase;
    }

    /**
     * 设置用户角色
     *
     * @param userId
     * @param roleCodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseBase setUserRoles(String userId, List<String> roleCodes) {
        ResponseBase responseBase = new ResponseBase();
        try {
            String logingUserId = JwtTokenUtil.currUser();
            List<UserUserRoleRelation> list = new ArrayList<>();
            for (String roleCode : roleCodes) {
                UserUserRoleRelation userRoleRelation = new UserUserRoleRelation();
                userRoleRelation.setUserRoleRelationId(UUID.randomUUID().toString());
                userRoleRelation.setRoleId(roleCode);
                userRoleRelation.setUserId(userId);
                userRoleRelation.setStatus(1);
                userRoleRelation.setCreateUser(logingUserId);
                userRoleRelation.setUpdateUser(logingUserId);
                list.add(userRoleRelation);
            }
            userUserRoleRelationMapper.batchDelUserRoleRelationByUserIdAndRoleId(userId);
            int res = userUserRoleRelationMapper.bathcAdd(list);
            if (res > 0) {
                responseBase.setMessage("设置用户角色成功");
                responseBase.setCode(1);
            } else {
                responseBase.setMessage("设置用户角色失败");
                responseBase.setCode(0);
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
        }

        return responseBase;
    }


    @Override
    public ResponseBase forgetPassword(String userAuthId, Integer authType) {
        return null;
    }

    /**
     * 重置密码
     *
     * @param userId
     * @param newPassword
     * @return
     */
    @Override
    public ResponseBase resetPassword(String userId, String newPassword) {
        ResponseBase responseBase = new ResponseBase();
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setPassword(newPassword);
            int res = userInfoMapper.updateById(userInfo);
            if (res > 0) {
                responseBase.setCode(1);
                responseBase.setMessage("密码重置成功");
            } else {
                responseBase.setCode(0);
                responseBase.setMessage("密码重置失败");
            }
        } catch (Exception e) {
            responseBase.setCode(0);
            responseBase.setMessage(e.getMessage());
            log.error("重置用户{}密码异常---{}", userId, e.getMessage());
        }

        return responseBase;
    }

    /**
     * 根据用户id查询用户配置信息
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseRecords<UserCustomizedParamDTO> queryUserCustomizedParams(String userId) {
        ResponseRecords<UserCustomizedParamDTO> result = new ResponseRecords<>();
        List<UserCustomizedParamDTO> lists = userInfoMapper.queryUserCustomizedParams(userId);
        if (lists.size() > 0) {
            result.setRecords(lists);
            result.setCode(1);
            result.setMessage("查询到" + lists.size() + "条结果");
        } else {
            result.setCode(0);
            result.setMessage("没有查询到结果");
        }
        return result;
    }

    /**
     * 根据用户单位、岗位查询用户信息
     *
     * @param pageData
     * @return
     */
    @Override
    public ResponseResultPage<UserInfoDTO> queryUsers(PageData<UserRequest> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            page.setAsc(pageData.getAscs());
            page.setDesc(pageData.getDescs());
            Page<UserInfoDTO> resultPage = userInfoMapper.queryUsers(page, pageData.getCondition());
            if (ToolUtil.notEmpty(resultPage) && ToolUtil.notEmpty(resultPage.getRecords())) {
                List<UserInfoDTO> list = resultPage.getRecords();
                for (UserInfoDTO userInfoDTO : list) {
                    List<String> accounts = userInfoMapper.queryAccountById(userInfoDTO.getUserId());
                    if (ToolUtil.notEmpty(accounts)) {
                        userInfoDTO.setAccounts(accounts);
                    }
                    List<OrgList> orgList = userInfoMapper.queryOrgListById(userInfoDTO.getUserId());
                    if (ToolUtil.notEmpty(orgList)) {
                        userInfoDTO.setOrgList(orgList);
                    }
                    List<RoleList> roleList = userInfoMapper.queryRoleListById(userInfoDTO.getUserId());
                    if (ToolUtil.notEmpty(roleList)) {
                        userInfoDTO.setRoleList(roleList);
                    }
                }
            }
            result.setRecords(resultPage.getRecords());
            result.setCode(1);
            result.setSize(pageData.getSize());
            result.setCurrent(pageData.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setMessage("查询到" + resultPage.getRecords().size() + "条用户信息");
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryUsers->(根据用户单位、岗位)查询用户信息[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 根据用户ID查询用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseRecords<RoleDTO> getUserRoles(String userId) {
        ResponseRecords<RoleDTO> responseResult = new ResponseRecords<>();
        try {
            List<RoleDTO> userRoles = userRoleMapper.getUserRolesByUserId(userId);
            responseResult.setCode(1);
            responseResult.setMessage("查询成功");
            responseResult.setRecords(userRoles);
        } catch (Exception e) {
            responseResult.setCode(0);
            responseResult.setMessage(e.getMessage());
        }
        return responseResult;
    }

    /**
     * 根据用户ID获取用户菜单
     *
     * @param para
     * @return
     */
    @Override
    public ResponseRecords<MenuDTO> getUserMenus(UserMenuRequest para) {
        ResponseRecords<MenuDTO> responseResult = new ResponseRecords<>();
        try {
            List<MenuDTO> userMenus = userMenuMapper.getUserMenus(para);
            responseResult.setCode(1);
            responseResult.setMessage("查询成功");
            responseResult.setRecords(userMenus);
        } catch (Exception e) {
            responseResult.setCode(0);
            responseResult.setMessage(e.getMessage());
        }
        return responseResult;
    }

    /**
     * 用户信息查询（根据用户ID）
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult<UserInfoDTO> queryUserById(String userId) {
        ResponseResult<UserInfoDTO> result = new ResponseResult<>();
        try {
            UserInfoDTO userInfo = userInfoMapper.queryUserById(userId);
            if (userInfo != null) {
                result.setCode(1);
                result.setMessage("查询成功");
                result.setResult(userInfo);
            } else {
                result.setCode(0);
                result.setMessage("没有查询到用户");
            }
        } catch (Exception ex) {
            log.error("UserServiceImpl|queryUserById->用户信息查询[" + ex.getLocalizedMessage() + "]");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /**
     * 用户类型添加
     *
     * @param userTypeDTO
     * @return
     */
    @Override
    public ResponseBase createUserType(UserTypeDTO userTypeDTO) {
        ResponseBase base = new ResponseBase();
        try {
            UserType userType = Object2ObjectUtil.parseObject(userTypeDTO, UserType.class);
            userType.setCreateUser(JwtTokenUtil.currUser());
            userType.setUpdateUser(JwtTokenUtil.currUser());
            Integer count = userTypeMapper.insert(userType);
            base.setCode(1);
            base.setMessage("用户类型添加完成，受影响数据条数count=" + count);
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("UserServiceImpl|createUserType->用户类型添加[" + ex.getMessage() + "]");
        }
        return base;
    }

    /**
     * 用户类型修改
     *
     * @param userTypeDTO
     * @return
     */
    @Override
    public ResponseBase modifyUserType(UserTypeDTO userTypeDTO) {
        ResponseBase base = new ResponseBase();
        try {
            UserType userType = Object2ObjectUtil.parseObject(userTypeDTO, UserType.class);
            userType.setUpdateUser(JwtTokenUtil.currUser());
            Integer i = userTypeMapper.updateById(userType);
            base.setMessage("用户类型修改完成");
            base.setCode(1);
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("UserServiceImpl|modifyUserType->用户类型修改" + ex.getMessage());
        }
        return base;
    }

    /**
     * 删除用户类型
     *
     * @param userType
     * @return
     */
    @Override
    public ResponseBase deleteUserType(String userType) {
        ResponseBase base = new ResponseBase();
        try {
            int count = userTypeMapper.deleteById(userType);
            base.setCode(1);
            base.setMessage("用户类型删除完成，受影响数据行数count=" + count);
        } catch (Exception ex) {
            base.setCode(0);
            base.setMessage(ex.getMessage());
            log.error("UserServiceImpl|deleteUserType->删除用户类型" + ex.getMessage());
        }
        return base;
    }

    /**
     * 用户类型查询（单个）
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult<UserTypeDTO> queryUserTypeById(String id) {
        ResponseResult<UserTypeDTO> result = new ResponseResult<>();
        try {
            UserType userType = userTypeMapper.selectById(id);
            UserTypeDTO userTypeDTO = Object2ObjectUtil.parseObject(userType, UserTypeDTO.class);
            result.setCode(1);
            result.setResult(userTypeDTO);
            result.setMessage("用户类型查询(单个)完成，userTypeDTO=" + userTypeDTO);
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryUserTypeById->用户类型查询（单个）[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 用户类型查询（根据userTypeName模糊查询）
     *
     * @param userTypeName
     * @return
     */
    @Override
    public ResponseRecords<UserTypeDTO> queryUserTypes(String userTypeName) {
        ResponseRecords<UserTypeDTO> result = new ResponseRecords<>();
        try {
            List<UserTypeDTO> list = userTypeMapper.queryUserTypes(userTypeName);
            result.setRecords(list);
            result.setCode(1);
            result.setMessage("用户类型查询完成，共有" + list.size() + "条数据");
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryUserTypes->用户类型查询（根据userTypeName模糊查询）[" + ex.getMessage() + "]");
        }
        return result;
    }

    /**
     * 店长授权
     *
     * @param orgSeq
     * @param password
     * @return
     */
    @Override
    public ResponseBase grantByStoreManager(String orgSeq, String password) {
        ResponseBase base = new ResponseBase();
        UserInfoDTO userInfoDTO = userInfoMapper.queryStoreManagerByStoreId(orgSeq);
        if (MD5Util.toMD5(password).equals(userInfoDTO.getPassword())) {
            base.setMessage("店长授权成功");
            base.setCode(1);
        } else {
            base.setMessage("店长授权失败，密码不正确");
            base.setCode(0);
        }
        return base;
    }


    /**
     * 组织类型查询
     *
     * @param orgTypeName
     * @return
     */
    @Override
    public ResponseRecords<OrganizationTypeDTO> getUserOrgType(String orgType, String orgTypeName) {
        ResponseRecords<OrganizationTypeDTO> responseResult = new ResponseRecords<>();
        try {
            List<OrganizationTypeDTO> orgTypeMenus = organizationMapper.getUserOrgType(orgType, orgTypeName);
            responseResult.setCode(1);
            responseResult.setMessage("查询成功");
            responseResult.setRecords(orgTypeMenus);
        } catch (Exception e) {
            responseResult.setCode(0);
            responseResult.setMessage(e.getMessage());
        }
        return responseResult;
    }

    @Override
    public ResponseRecords<OrgReslutDTO> queryStoreOrgseq(String orgSeq) {

        ResponseRecords<OrgReslutDTO> responseRecords = new ResponseRecords<>();
        List<OrgReslutDTO> orgResluList = organizationMapper.queryStoreOrgseq(orgSeq);
        System.out.println("获取的组织机构树为>>>>>>" + orgResluList);
        if (orgResluList != null) {
            responseRecords.setCode(1);
            responseRecords.setMessage("获取组织机构树成功");
            responseRecords.setRecords(orgResluList);
        } else {
            responseRecords.setCode(0);
            responseRecords.setMessage("获取组织机构树为空");
        }
        return responseRecords;
    }

    @Override
    public OrganizationDTO getOrgSeqType(String orgSeq) {
        OrganizationDTO userOrganization = organizationMapper.getOrgSeqType(orgSeq);
        return userOrganization;
    }

    @Override
    public ResponseResultPage<UserInfoDTO> queryUsersById(PageData<UserRequest> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            page.setAsc(pageData.getAscs());
            page.setDesc(pageData.getDescs());
            Page<UserInfoDTO> resultPage = userInfoMapper.queryUsersById(page, pageData.getCondition());
            result.setRecords(resultPage.getRecords());
            result.setCode(1);
            result.setSize(pageData.getSize());
            result.setCurrent(pageData.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setMessage("查询到" + resultPage.getRecords().size() + "条用户信息");
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryUsersById->(根据用户单位、岗位)查询用户信息[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseRecords<UserInfoDTO> queryStorePerson(UserRequest userRequest) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        return result;
    }

    @Override
    public ResponseRecords<UserInfoDTO> queryManage(UserRequest userRequest) {
        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
        return result;
    }

    //==============================================


//    @Override
//    public ResponseRecords<UserInfoDTO> queryStorePerson(UserRequest userRequest) {
//        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
//        Boolean isNeed = true;
//        try {
//            List<UserInfoDTO> list = userInfoMapper.queryStorePerson(userRequest);
//            for (UserInfoDTO userInfoDTO : list) {
//                //遍历集合,取出门店orgSeq和userId ,查询门店下的某个人的拥有的会员数量
//                String userId = userInfoDTO.getUserId();
////                System.out.println("userId++++++" + userId);
//                String orgSeq = userInfoDTO.getOrgSeq();
//                MemAmtAndSerCountDTO memAmtAndSerCount = memberService.getMemAmtAndSerCount(userId, orgSeq,isNeed);
//                //查询出会员数量和服务次数
//                userInfoDTO.setMemberAmount(memAmtAndSerCount.getMemberAmount());
//                userInfoDTO.setServiceCount(memAmtAndSerCount.getServiceCount());
//                //取出会员id集合,该店员下面所有会员消费金额
//                List<String> mList = memAmtAndSerCount.getMemberIdList();
//                BigDecimal totalMoney = BigDecimal.ZERO;
//                for (String memberId : mList) {
//                    BigDecimal money = dataService.queryMemberAmount(memberId, orgSeq).getResult();
//                    totalMoney = totalMoney.add(money);
//                }
//                userInfoDTO.setTotalMoney(totalMoney);
//            }
//            result.setRecords(list);
//            result.setCode(1);
//            result.setMessage("门店查询完成，共有" + list.size() + "条数据");
//        } catch (Exception ex) {
//            result.setCode(0);
//            result.setMessage(ex.getMessage());
//            log.error("UserServiceImpl|queryMananger->门店查询（根据orgSeq查出店主和店长）[" + ex.getMessage() + "]");
//        }
//        return result;
//    }

//    @Override
//    public ResponseRecords<UserInfoDTO> queryMananger(UserRequest userRequest) {
//        ResponseRecords<UserInfoDTO> result = new ResponseRecords<>();
//        Boolean isNeed = false;
//        try {
//            List<UserInfoDTO> list = userInfoMapper.queryMananger(userRequest);
//            for (UserInfoDTO userInfoDTO : list) {
//                //遍历集合,取出门店orgSeq和userId ,查询门店下的某个人的拥有的会员数量
//                String userId = userInfoDTO.getUserId();
//                String orgSeq = userInfoDTO.getOrgSeq();
//                MemAmtAndSerCountDTO memAmtAndSerCount = memberService.getMemAmtAndSerCount(userId, orgSeq,isNeed);
//                //查询出会员数量和服务次数
//                userInfoDTO.setMemberAmount(memAmtAndSerCount.getMemberAmount());
//            }
//            result.setRecords(list);
//            result.setCode(1);
//            result.setMessage("门店查询完成，共有" + list.size() + "条数据");
//        } catch (Exception ex) {
//            result.setCode(0);
//            result.setMessage(ex.getMessage());
//            log.error("UserServiceImpl|queryMananger->门店查询（根据orgSeq查出店主和店长）[" + ex.getMessage() + "]");
//        }
//        return result;
//    }

    @Override
    public ResponseResultPage<UserInfoDTO> queryStoreUsers(PageData<UserRequest> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<>();
        try {
            Page<UserInfoDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
            page.setAsc(pageData.getAscs());
            page.setDesc(pageData.getDescs());
            Page<UserInfoDTO> resultPage = userInfoMapper.queryStoreUsers(page, pageData.getCondition());
            result.setRecords(resultPage.getRecords());
            result.setCode(1);
            result.setSize(pageData.getSize());
            result.setCurrent(pageData.getCurrent());
            result.setTotal(resultPage.getTotal());
            result.setMessage("查询到" + resultPage.getRecords().size() + "条用户信息");
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryStoreUsers->(根据用户单位、岗位)查询用户信息[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseResult<UserInfoDTO> queryManangerById(String orgSeq) {
        ResponseResult<UserInfoDTO> result = new ResponseResult<>();
        try {
            UserInfoDTO list = userInfoMapper.queryManangerById(orgSeq);
            if (list != null && list.getUserId() != null) {
                result.setResult(list);
                result.setCode(1);
                result.setMessage("店长查询完成");
            } else {
                throw new Exception("未查到该门店的店长信息");
            }
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage(ex.getMessage());
            log.error("UserServiceImpl|queryManangerById->门店查询（根据orgSeq查出店长）[" + ex.getMessage() + "]");
        }
        return result;
    }

    @Override
    public ResponseRecords queryShopAssNameList(Map<String, String> map) {
        String orgSeq = map.get("orgSeq");
        String userId = map.get("userId");

        ResponseRecords result = new ResponseRecords<>();
        if (!StringUtils.isBlank(orgSeq) && !StringUtils.isBlank(userId)) {
            QueryWrapper qw = new QueryWrapper();
            qw.eq("org_seq", orgSeq);
            qw.eq("user_type", 6);//用户类型为店员
            qw.notIn("user_id", userId);
            List<UserInfo> nameList = userInfoMapper.selectList(qw);
            List<Map<String, String>> uList = new ArrayList<>();
           
            if (nameList != null && nameList.size() > 0) {
                for (UserInfo ui : nameList) {
                    Map<String, String> umap = new HashMap<>();
                    umap.put("userId", ui.getUserId());
                    umap.put("realName", ui.getRealName());
                    uList.add(umap);
                }
                result.setCode(ResponseBase.SUCCESS);
                result.setMessage("获取到店员名称列表");
            }
            result.setRecords(uList);
          
        } else {
            result.setCode(ResponseBase.FAILED);
            result.setMessage("传入参数有误");
        }

        return result;
    }

    @Override
    public ResponseResult<MemberUserDTO> queryMemberUserById(String userId) {
        ResponseResult<MemberUserDTO> result = new ResponseResult<>();
        MemberUserDTO userInfoDTO = userInfoMapper.queryMemberUserById(userId);
        if (userInfoDTO != null && userInfoDTO.getUserId() != null) {
            result.setCode(1);
            result.setMessage("查询成功");
            result.setResult(userInfoDTO);
        } else {
            result.setCode(0);
            result.setMessage("查询失败");
        }
        return result;
    }

    @Override
    public ResponseResult<MemberUserDTO> queryManagerById(String orgSeq) {
        ResponseResult<MemberUserDTO> result = new ResponseResult<>();
        MemberUserDTO userInfoDTO = userInfoMapper.queryManagerById(orgSeq);
        if (userInfoDTO != null && userInfoDTO.getUserId() != null) {
            result.setCode(1);
            result.setMessage("查询成功");
            result.setResult(userInfoDTO);
        } else {
            result.setCode(0);
            result.setMessage("查询失败");
        }
        return result;
    }

    /**
     * 智能巡店-员工信息
     *
     * @param orgSeq
     * @return
     * @Author wangjie
     */
    @Override
    public ResponseResultPage<UserInfoDTO> queryOrgUserList(PageData<UserInfoParamDTO> pageData) {
        ResponseResultPage<UserInfoDTO> result = new ResponseResultPage<UserInfoDTO>();
        try {
            if (ToolUtil.notEmpty(pageData.getCondition().getOrgList())) {
                Page<UserInfoDTO> page = new Page<UserInfoDTO>(pageData.getCurrent(), pageData.getSize());
                Page<UserInfoDTO> pageList = userInfoMapper.queryUserInfoList(page, pageData.getCondition());
                result.setCode(1);
                result.setMessage("查询成功");
                result.setCurrent(page.getCurrent());
                result.setSize(page.getSize());
                result.setTotal(pageList.getTotal());
                result.setRecords(pageList.getRecords());
            } else {
                result.setCode(0);
                result.setMessage("查询失败，门店编码不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("查询失败，系统异常");
        }
        return result;
    }

    @Override
    public List<BaseDTO> queryManagerListByUpOrg(String orgSeq) {
        List<BaseDTO> list = new ArrayList<>();
        if ("2".equals(userInfoMapper.queryOrgType(orgSeq).getOrgType())) {
            list = userInfoMapper.queryManagerListByUpOrg(orgSeq);
        } else {
            //查询门店信息
            BaseDTO base = userInfoMapper.queryOrg(orgSeq);
            list.add(base);
        }
        return list;
    }

    @Override
    public String queryOrgLV(String orgSeq) {
        OrganizationDTO organizationDTO = userInfoMapper.queryOrgType(orgSeq);
        return organizationDTO.getOrgType();
    }

    @Override
    public List<OrganizationDTO> queryOrgInfo(List<String> orgSeqList) {
        return userInfoMapper.queryOrgInfo(orgSeqList);
    }

    @Override
    public OrganizationDTO queryOrgInfoByOrgSeq(String orgSeq) {
        return userInfoMapper.queryOrgInfoByOrgSeq(orgSeq);
    }

    @Override
    public List<OrganizationDTO> queryDownOrg(String orgSeq) {
        return userInfoMapper.queryDownOrg(orgSeq);
    }

    /**
     * 根据基地orgSeqList查询基地业务经理信息
     *
     * @param orgSeqList
     * @return
     */
    @Override
    public ResponseRecords<UserInfoDTO> queryBaseUserList(List<String> orgSeqList) {
        ResponseRecords<UserInfoDTO> records = new ResponseRecords<>();
        try {
            List<UserInfoDTO> list = userInfoMapper.queryBaseUserList(orgSeqList);
            records.setCode(1);
            records.setMessage("查询成功");
            records.setRecords(list);
        } catch (Exception e) {
            e.printStackTrace();
            records.setCode(0);
            records.setMessage("查询失败，系统异常");
            return records;
        }
        return records;
    }

    /**
     * 根据基地经理用户id查询所在基地及战区
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult<UserOrganizationInfoDTO> queryUserOrganizationInfo(String userId) {
        ResponseResult<UserOrganizationInfoDTO> result = new ResponseResult<>();
        try {
            List<UserOrganizationInfoDTO> list = userInfoMapper.queryUserOrganizationInfo(userId);
            result.setCode(1);
            result.setMessage("查询成功");
            result.setResult(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("查询失败，系统异常");
            return result;
        }
        List<UserOrganizationInfoDTO> list = userInfoMapper.queryUserOrganizationInfo(userId);
        return result;
    }

    @Override
    public Map<String,String> queryUserAllByIds(Set<String> userIds) {
        List<UserInfo> list = userInfoMapper.selectList(new QueryWrapper<UserInfo>().select("user_id,real_name")
                    .in("user_id", userIds));
        return list.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getRealName));
    }

	@Override
	public ResponseRecords<UserInfoDTO> queryUserListByOrgSeq(String orgSeq) {
		// TODO Auto-generated method stub
		 ResponseRecords result = new ResponseRecords<>();
	        if (!StringUtils.isBlank(orgSeq)) {
	            QueryWrapper qw = new QueryWrapper();
	            qw.eq("org_seq", orgSeq);
	            List<UserInfo> nameList = userInfoMapper.selectList(qw);
	            List<Map<String, String>> uList = new ArrayList<>();
	           
	            if (nameList != null && nameList.size() > 0) {
	                for (UserInfo ui : nameList) {
	                    Map<String, String> umap = new HashMap<>();
	                    umap.put("userId", ui.getUserId());
	                    umap.put("realName", ui.getRealName());
	                    uList.add(umap);
	                }
	                result.setCode(ResponseBase.SUCCESS);
	                result.setMessage("获取到店员名称列表");
	            }
	            result.setRecords(uList);
	          
	        } else {
	            result.setCode(ResponseBase.FAILED);
	            result.setMessage("传入参数有误");
	        }
		return result;
	}
}
