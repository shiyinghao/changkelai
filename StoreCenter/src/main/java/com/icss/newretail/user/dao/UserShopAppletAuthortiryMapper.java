package com.icss.newretail.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.model.ShopAppletAuthortiryDto;
import com.icss.newretail.user.entity.UserShopAppletAuthortiry;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商家端小程序权限表 Mapper 接口
 * </p>
 *
 * @author mx
 * @since 2020-04-11
 */
@Repository
public interface UserShopAppletAuthortiryMapper extends BaseMapper<UserShopAppletAuthortiry> {

    List<UserShopAppletAuthortiry> selectAll();

    List<ShopAppletAuthortiryDto> getAuthority(@Param("orgSeq") String orgSeq, @Param("userId") String userId);

    List<ShopAppletAuthortiryDto> getAllAuthority();
}
