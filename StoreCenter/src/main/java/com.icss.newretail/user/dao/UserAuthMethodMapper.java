package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.UserOrgRelationDTO;
import com.icss.newretail.user.entity.UserAuthMethod;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthMethodMapper extends BaseMapper<UserAuthMethod> {

    UserAuthMethod queryUserByAccount(@Param("account") String account);

//    String queryUserType(@Param("userId") String userId);

    List<UserOrgRelationDTO> queryUserType(@Param("userId") String userId);
}
