package com.icss.newretail.user.dao;

import java.util.List;
import java.util.Map;

import com.icss.newretail.model.GrantUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.user.entity.GrantPassword;

/**
 * @ClassName: GrantPasswordMapper
 * @Description: 终端授权改价密码mapper
 * @author jc
 * @date 2019年8月2日
 *
 */
@Repository
public interface GrantPasswordMapper extends BaseMapper<GrantPassword> {

	public List<GrantUserDTO> getGrantUserList(@Param("orgSeq") String orgSeq);

	public GrantUserDTO getGrantPassword(@Param("userId") String userId, @Param("orgSeq") String orgSeq);
}
