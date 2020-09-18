package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.CodeChidMessage;
import com.icss.newretail.model.UserDictValueDTO;
import com.icss.newretail.user.entity.UserDictValue;
import org.apache.ibatis.annotations.Param;

public interface CodeChildMapper extends BaseMapper<UserDictValue> {
    Page<UserDictValueDTO> querycodeByName(@Param("pg") Page<UserDictValueDTO> page, @Param("code") CodeChidMessage condition);

    int deleteCodeChild(String uuid);
}
