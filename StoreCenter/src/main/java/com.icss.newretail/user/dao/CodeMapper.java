package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.CodeMessage;
import com.icss.newretail.model.UserDictDTO;
import com.icss.newretail.user.entity.UserDict;
import org.apache.ibatis.annotations.Param;

public interface CodeMapper  extends BaseMapper<UserDict> {
    Page<UserDictDTO> querycodeByName(@Param("pg") Page<UserDictDTO> page,@Param("code") CodeMessage condition);

    int deleteCode(String uuid);

    Page<UserDictDTO> querycode(@Param("pg")Page<UserDictDTO> page,@Param("code") CodeMessage condition);
}
