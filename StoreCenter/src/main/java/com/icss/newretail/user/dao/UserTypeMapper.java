package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.UserTypeDTO;
import com.icss.newretail.user.entity.UserType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserTypeMapper extends BaseMapper<UserType> {
    List<UserTypeDTO> queryUserTypes(@Param("userTypeName") String userTypeName);
}
