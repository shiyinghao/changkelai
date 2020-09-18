package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.StoreGradelevelRuleDTO;
import com.icss.newretail.model.StoreGradelevelRuleRequst;
import com.icss.newretail.model.StoreRuleDTO;
import com.icss.newretail.service.user.StoreGradelevelRuleService;
import com.icss.newretail.user.dao.StoreGradelevelRuleMapper;
import com.icss.newretail.user.dao.UserInfoMapper;
import com.icss.newretail.user.entity.StoreGradelevelRule;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import com.icss.newretail.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 门店等级规则 服务实现类
 * </p>
 *
 * @author yanghu
 * @since 2020-04-02
 */
@Slf4j
@Service
public class StoreGradelevelRuleServiceImpl implements StoreGradelevelRuleService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private StoreGradelevelRuleMapper storeGradelevelRuleMapper;

    @Override
    public ResponseResultPage<StoreGradelevelRuleDTO> queryStoreGradelevelLule(PageData<StoreGradelevelRuleRequst> pageData) {
        ResponseResultPage<StoreGradelevelRuleDTO> rr = new ResponseResultPage<>();
        QueryWrapper<StoreGradelevelRule> qw = new QueryWrapper<>();
        Page<StoreGradelevelRule> page = new Page<>();
        page.setCurrent(pageData.getCurrent());
        page.setSize(pageData.getSize());
        page.setAsc("begin_score");
        qw.eq(!StringUtils.isEmpty(pageData.getCondition().getUuid()), "uuid", pageData.getCondition().getUuid());
        qw.like(!StringUtils.isEmpty(pageData.getCondition().getGradelevelName()), "gradelevel_name", pageData.getCondition().getGradelevelName());
        if(pageData.getCondition().getStatus()==null){
            pageData.getCondition().setStatus(1);  //默认状态等于一
        }
        qw.eq("status", pageData.getCondition().getStatus());
        IPage<StoreGradelevelRule> soreGradelevelRule = null;

        try {
            soreGradelevelRule = storeGradelevelRuleMapper.selectPage(page, qw);
            List<StoreGradelevelRuleDTO> storeGradelevelRuleDTO = Object2ObjectUtil.parseList(soreGradelevelRule.getRecords(),StoreGradelevelRuleDTO.class);
            rr.setTotal(soreGradelevelRule.getTotal());
            rr.setRecords(storeGradelevelRuleDTO);
            rr.setCurrent(soreGradelevelRule.getCurrent()).setSize(soreGradelevelRule.getSize());
            rr.setCode(1).setMessage("查询成功");
            return rr;
        } catch (Exception e) {
            rr.setCode(0);
            rr.setMessage("查询失败");
            return rr;
        }
    }

    @Override
    public ResponseBase addStoreGradelevelLule(StoreGradelevelRuleRequst storeGradelevelRuleRequst) {
        StoreGradelevelRule storeGradelevelRuleDTO = Object2ObjectUtil.parseObject(storeGradelevelRuleRequst, StoreGradelevelRule.class);
        storeGradelevelRuleDTO.setCreateTime(LocalDateTime.now());
        String currUser = JwtTokenUtil.currUser();
        //查询当前用户是否为   type=1 的用户  总部人员和  超级管理员
//        QueryWrapper qw = new QueryWrapper();
//        if(currUser==null||currUser.equals("")){
//
//        }
//        qw.eq(, )
//        userInfoMapper
        storeGradelevelRuleDTO.setCreateUser(currUser);
        storeGradelevelRuleDTO.setUuid(UUID.randomUUID().toString());
        storeGradelevelRuleDTO.setStatus(1);
        ResponseBase result = new ResponseResult<>();
        try {
            storeGradelevelRuleMapper.insert(storeGradelevelRuleDTO);
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("新增失败");
            return result;
        }
        result.setCode(1);
        result.setMessage("新增成功");
        return result;
    }

    @Override
    public ResponseBase deleteStoreGradelevelLule(String uuids) {
        ResponseBase result = new ResponseResult<>();
        StoreGradelevelRule storeGradelevelRuleDTO = new StoreGradelevelRule();
        storeGradelevelRuleDTO.setStatus(0);
        QueryWrapper<StoreGradelevelRule> qw = new QueryWrapper<>();
        qw.in("uuid", uuids.split(","));
        try {
            storeGradelevelRuleMapper.update(storeGradelevelRuleDTO, qw);
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("删除失败");
        }
        result.setCode(1);
        result.setMessage("删除成功");
        return result;
    }

    @Override
    public ResponseBase updateStoreGradelevelLule(StoreGradelevelRuleRequst storeGradelevelRuleRequst) {
        StoreGradelevelRule storeGradelevelRuleDTO = Object2ObjectUtil.parseObject(storeGradelevelRuleRequst, StoreGradelevelRule.class);
        ResponseBase result = new ResponseResult<>();
        storeGradelevelRuleDTO.setUpdateTime(LocalDateTime.now());
        storeGradelevelRuleDTO.setUpdateUser(JwtTokenUtil.currUser());
        try {
            storeGradelevelRuleMapper.updateById(storeGradelevelRuleDTO);
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("修改失败");
        }
        result.setCode(1);
        result.setMessage("修改成功");
        return result;
    }

    @Override
    public ResponseRecords<StoreGradelevelRuleDTO> queryStoreGradelevelRule() {
        ResponseRecords<StoreGradelevelRuleDTO> result =new ResponseRecords<>();
        try {
            List<StoreGradelevelRuleDTO> memberGoodsInfoDTO =storeGradelevelRuleMapper.queryStoreGradelevelRule();
            if (memberGoodsInfoDTO != null) {
                result.setCode(1);
                result.setMessage("门店等级查询成功");
                result.setRecords(memberGoodsInfoDTO);
            } else {
                result.setCode(0);
                result.setMessage("门店等级查询失败");
            }
        }catch (Exception e) {
            result.setCode(0);
            result.setMessage("门店等级查询异常" + e.getMessage());
            log.error("门店等级列表查询异常" + e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseRecords<StoreGradelevelRuleDTO> queryStoreRule(StoreRuleDTO storeRuleDTO) {
        ResponseRecords<StoreGradelevelRuleDTO> result =new ResponseRecords<>();
        try {
            if (storeRuleDTO.getRuleId()!=null){
                List<StoreGradelevelRuleDTO> memberGoodsInfoDTO =storeGradelevelRuleMapper.queryStoreRule(storeRuleDTO);
                if (memberGoodsInfoDTO != null) {
                    result.setCode(1);
                    result.setMessage("门店等级查询成功");
                    result.setRecords(memberGoodsInfoDTO);
                } else {
                    result.setCode(0);
                    result.setMessage("门店等级查询失败");
                }
            }
        }catch (Exception e) {
            result.setCode(0);
            result.setMessage("门店等级查询异常" + e.getMessage());
            log.error("门店等级列表查询异常" + e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult<StoreGradelevelRuleDTO> queryRuleById(String storeLevelId) {
        ResponseResult<StoreGradelevelRuleDTO> result =new ResponseResult<>();
        try {
            StoreGradelevelRuleDTO memberGoodsInfoDTO =storeGradelevelRuleMapper.querygoodById(storeLevelId);
            if (memberGoodsInfoDTO != null) {
                result.setCode(1);
                result.setMessage("门店等级信息查询成功");
                result.setResult(memberGoodsInfoDTO);
            } else {
                result.setCode(0);
                result.setMessage("门店等级信息查询失败");
            }
        }catch (Exception e) {
            result.setCode(0);
            result.setMessage("门店等级信息查询异常" + e.getMessage());
            log.error("门店等级信息查询异常" + e.getMessage());
        }
        return result;
    }


    @Override
    public ResponseResult<StoreGradelevelRuleDTO> queryGradelevel(Integer score) {
        ResponseResult<StoreGradelevelRuleDTO> rr = new ResponseResult<>();
        StoreGradelevelRuleDTO storeGradelevelRuleDTO = new StoreGradelevelRuleDTO();
        rr.setCode(0).setMessage("查询失败,数据异常");
        if(score==null||score<0){
            return  rr;
        }
        try{
        StoreGradelevelRule storeGradelevelRule = storeGradelevelRuleMapper.maxGradelevel();
        if(score>= storeGradelevelRule.getEndScore().intValue()){
            rr.setCode(1).setMessage("查询成功");
            BeanUtils.copyProperties(storeGradelevelRule, storeGradelevelRuleDTO);
            rr.setResult(storeGradelevelRuleDTO);
            return rr;
        }
            QueryWrapper<StoreGradelevelRule> qw= new QueryWrapper();
            qw.le("begin_score", score);
            qw.gt("end_score", score);
            storeGradelevelRule = storeGradelevelRuleMapper.selectOne(qw);
            BeanUtils.copyProperties(storeGradelevelRule, storeGradelevelRuleDTO);
            rr.setResult(storeGradelevelRuleDTO).setCode(1).setMessage("查询成功");
        }catch (Exception e){
            e.printStackTrace();

        }
        return rr;
    }
}
