package com.icss.newretail.user.dao;

import com.icss.newretail.user.entity.MenuButton;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单按钮表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2020-03-17
 */
public interface MenuButtonMapper extends BaseMapper<MenuButton> {

    /**
     * 根据菜单ID集合查询菜单按钮
     *
     * @param list
     * @return
     */
    List<MenuButton> getMenuButtonByMenuIds(List<String> menuIds);
}
