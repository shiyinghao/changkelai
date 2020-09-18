package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreBonuspointRuleService;
import com.icss.newretail.user.dao.StoreBonuspointRuleMapper;
import com.icss.newretail.user.entity.StoreBonuspointRule;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 门店积分规则表 服务实现类
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
@Slf4j
@Service
public class StoreBonuspointRuleServiceImpl implements StoreBonuspointRuleService {

    @Autowired
    private StoreBonuspointRuleMapper storeBonuspointRuleMapper;

    @Override
    public ResponseBase addStoreBonuspoint(StoreBonuspointRuleRequst storeBonuspointRuleRequst) {
        StoreBonuspointRule storeBonuspointRule = Object2ObjectUtil.parseObject(storeBonuspointRuleRequst, StoreBonuspointRule.class);
        storeBonuspointRule.setCreateTime(LocalDateTime.now());
        storeBonuspointRule.setCreateUser(JwtTokenUtil.currUser());
        storeBonuspointRule.setUuid(UUID.randomUUID().toString());
        ResponseBase result = new ResponseResult<>();
        try {
            storeBonuspointRuleMapper.insert(storeBonuspointRule);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("新增失败");
            return  result;
        }
        result.setCode(1);
        result.setMessage("新增成功");
        return result;
    }

    @Override
    public ResponseBase deleteStoreBonuspoint(String uuids) {
        ResponseBase result = new ResponseResult<>();
        try {
            storeBonuspointRuleMapper.deleteBatchIds( Arrays.asList(uuids.split(",")));
            result.setCode(1);
            result.setMessage("删除成功");
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("删除失败");
        }

        return result;
    }

    @Override
    public ResponseBase updateStoreBonuspoint(StoreBonuspointRuleRequst storeBonuspointRuleRequst) {
        StoreBonuspointRule storeBonuspointRule = Object2ObjectUtil.parseObject(storeBonuspointRuleRequst, StoreBonuspointRule.class);
        ResponseBase result = new ResponseResult<>();
        storeBonuspointRule.setUpdateTime(LocalDateTime.now());
        storeBonuspointRule.setUpdateUser(JwtTokenUtil.currUser());
        try {
            storeBonuspointRuleMapper.updateById(storeBonuspointRule);
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("修改失败");
            return  result;
        }
        result.setCode(1);
        result.setMessage("修改成功");
        return result;
    }

    @Override
    public ResponseResultPage<StoreBonuspointRuleDTO> queryStoreBonuspoint(PageData<StoreBonuspointRuleRequst> pageData) {
        ResponseResultPage<StoreBonuspointRuleDTO> rr = new ResponseResultPage<>();
        QueryWrapper<StoreBonuspointRule> qw = new QueryWrapper<>();
        Page<StoreBonuspointRule> page = new Page<>();
        page.setCurrent(pageData.getCurrent());
        page.setSize(pageData.getSize());
        page.setAsc("begin_score");
//        page.setDesc(pageData.getDescs());
        qw.eq(!StringUtils.isEmpty(pageData.getCondition().getUuid()), "uuid", pageData.getCondition().getUuid());
        qw.eq( "status", 1);
        IPage<StoreBonuspointRule> storeBonuspointRule = null;




        try {
            storeBonuspointRule = storeBonuspointRuleMapper.selectPage(page, qw);
            List<StoreBonuspointRuleDTO> storeBonuspointRuleDTOS = Object2ObjectUtil.parseList(storeBonuspointRule.getRecords(), StoreBonuspointRuleDTO.class);
            rr.setTotal(storeBonuspointRule.getTotal()).setRecords(storeBonuspointRuleDTOS);
            rr.setCurrent(storeBonuspointRule.getCurrent()).setSize(storeBonuspointRule.getSize());
            rr.setCode(1).setMessage("查询成功");
        } catch (Exception e) {
            rr.setCode(0);
            rr.setMessage("查询失败");
        }
        return rr;
    }

    @Override
    public ResponseResult<StoreBonuspointRuleDTO> queryStoreBonuspointOne() {
        ResponseResult<StoreBonuspointRuleDTO> rr = new ResponseResult<>();
        StoreBonuspointRuleDTO storeBonuspointRuleDTO = storeBonuspointRuleMapper.queryStoreBonuspointOne();
        rr.setCode(1).setMessage("查询成功");
        rr.setResult(storeBonuspointRuleDTO);
        return rr;
    }
}
