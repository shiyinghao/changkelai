package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.user.entity.UserOrganization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationMapper extends BaseMapper<UserOrganization> {

}
