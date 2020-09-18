package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.AppModuleRelation;
import com.icss.newretail.model.ModuleDTO;
import com.icss.newretail.user.entity.UserModule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserModuleMapper extends BaseMapper<UserModule>{
    List<ModuleDTO> getAllModules();

    List<AppModuleRelation> getAppModuleRelationByAppid(@Param("appId") String appId);

    ModuleDTO queryModuleById(@Param("moduleId") String moduleId);

    List<ModuleDTO> queryModules(@Param("appId") String appId,@Param("userId") String userId);

    /*List<Map<String,Object>> queryModuleByAppId(@Param("appId") String appId);*/
}
