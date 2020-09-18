package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.user.entity.UserUserRoleRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关联关系mapper接口
 */
@Repository
public interface UserUserRoleRelationMapper extends BaseMapper<UserUserRoleRelation> {
    /**
     * 批量新增用户角色关联关系
     * @param userRoleRelations
     * @return
     */
    int bathcAdd(List<UserUserRoleRelation> userRoleRelations);

    int batchDelUserRoleRelationByUserIdAndRoleId(@Param("userId")String userId);
}
