package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.CodeMessage;
import com.icss.newretail.model.UserDictDTO;
import com.icss.newretail.model.UserSystemParamDTO;
import com.icss.newretail.model.UserSystemParamRequest;
import com.icss.newretail.user.entity.UserSystemParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统参数表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-03-26
 */
public interface UserSystemParamMapper extends BaseMapper<UserSystemParam> {

    List<UserSystemParamDTO> queryUserSystemParam(@Param("name") String name, @Param("code") String code);

    Page<UserSystemParamDTO> querySystemParam(@Param("pg")Page<UserDictDTO> page, @Param("code")UserSystemParamDTO condition);
}
