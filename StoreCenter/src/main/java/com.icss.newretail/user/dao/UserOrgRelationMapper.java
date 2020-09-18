package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.UserClerkDTO;
import com.icss.newretail.user.entity.UserOrgRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户组织机构关联表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-04-01
 */
public interface UserOrgRelationMapper extends BaseMapper<UserOrgRelation> {

	Integer batchAdd(List<UserOrgRelation> list);


	List<UserOrgRelation> getOrgRelationZQ(@Param("orgSeq") String orgSeq);

	Integer getIsWs(@Param("orgSeq") String orgSeq, @Param("userId") String userId);

	void updateByStore(@Param("userclerk") UserClerkDTO userclerk, @Param("currUser") String currUser);
}
