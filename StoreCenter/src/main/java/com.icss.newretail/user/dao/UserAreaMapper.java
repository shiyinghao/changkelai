package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.AreaInfoDTO;
import com.icss.newretail.user.entity.UserArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 行政区域Mapper接口
 */
@Repository
public interface UserAreaMapper extends BaseMapper<UserArea> {
    
    int modifyAreaInfo(AreaInfoDTO areaInfoDTO);
    
    AreaInfoDTO getAreaInfoById(String id);

    List<AreaInfoDTO> countStoreByArea(@Param("areaSeq") String areaSeq,@Param("status") String status,@Param("tenantId") String tenantId);

}
