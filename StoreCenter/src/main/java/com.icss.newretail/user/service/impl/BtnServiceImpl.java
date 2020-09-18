package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icss.newretail.model.MenuButtonDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.user.BtnService;
import com.icss.newretail.user.dao.MenuButtonMapper;
import com.icss.newretail.user.entity.MenuButton;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
@Slf4j
public class BtnServiceImpl implements BtnService {

    @Autowired
    private MenuButtonMapper MenuButtonMapper;

    @Override
    public ResponseBase updateBtn(MenuButtonDTO menuButtonDTO) {
        if (menuButtonDTO.getUuid() == null || menuButtonDTO.getUuid().equals("")) {
            menuButtonDTO.setUuid(UUID.randomUUID().toString());
            menuButtonDTO.setCreateTime(LocalDateTime.now());
            //创建人为当前用户ID
            String currUser = JwtTokenUtil.currUser();
            menuButtonDTO.setCreateUser(currUser);
            MenuButton menuButton = Object2ObjectUtil.parseObject(menuButtonDTO, MenuButton.class);
            int i = MenuButtonMapper.insert(menuButton);
            return i == 1 ? new ResponseBase(1, "新增成功") : new ResponseBase(0, "新增失败");
        } else {
            menuButtonDTO.setUpdateTime(LocalDateTime.now());
            menuButtonDTO.setUpdateUser(JwtTokenUtil.currUser());
            int i = MenuButtonMapper.updateById(Object2ObjectUtil.parseObject(menuButtonDTO, MenuButton.class));
            return i == 1 ? new ResponseBase(1, "修改成功") : new ResponseBase(0, "修改失败");
        }

    }

    @Override
    public ResponseBase deleteBtn(String uuid) {
        int i = MenuButtonMapper.deleteBatchIds(Arrays.asList(uuid.split(",")));
        return i>0? new ResponseBase(1, "删除成功") : new ResponseBase(0, "删除失败");
    }

    @Override
    public ResponseResult queryBtn(MenuButtonDTO menuButtonDTO) {
        QueryWrapper qw = new QueryWrapper<MenuButtonDTO>();
        qw.eq(menuButtonDTO.getUuid()!=null, "uuid", menuButtonDTO.getUuid());
        qw.eq(menuButtonDTO.getMenuId()!=null, "menu_id", menuButtonDTO.getMenuId());
        qw.eq(menuButtonDTO.getStatus()!=null, "status", menuButtonDTO.getStatus());
        qw.eq(menuButtonDTO.getBtnName()!=null, "btn_name", menuButtonDTO.getBtnName());
        qw.eq(menuButtonDTO.getBtnCode()!=null, "btn_code", menuButtonDTO.getBtnCode());
        List<MenuButton> menuButton = MenuButtonMapper.selectList(qw);
        ResponseResult result = new ResponseResult();
        result.setCode(1);
        result.setMessage("查询成功");
        result.setResult(menuButton);
        return result;
    }

}
