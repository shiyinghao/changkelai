package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.user.dao.WechatTipsAgreeMapper;
import com.icss.newretail.user.dao.WechatTipsMapper;
import com.icss.newretail.user.entity.WechatTips;
import com.icss.newretail.user.entity.WechatTipsAgree;
import com.icss.newretail.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.TipsDTO;
import com.icss.newretail.model.TipsRequest;
import com.icss.newretail.service.user.TipsService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Service
@Slf4j
public class TipsServiceImpl implements TipsService {

  @Autowired
  private WechatTipsMapper wechatTipsMapper;

  @Autowired
  private WechatTipsAgreeMapper wechatTipsAgreeMapper;

  /**
   * 获取使用条款
   * @param appId
   * @return
   */
  @Override
  public ResponseResult<TipsDTO> getTips(String appId) {
    ResponseResult<TipsDTO> result = new ResponseResult<>();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      String userId = JwtTokenUtil.currUser();
      TipsDTO tipsDTO = wechatTipsMapper.getTips(appId,userId,tenantId);
      result.setCode(1);
      result.setResult(tipsDTO);
    }catch(Exception e){
      result.setMessage(e.getMessage());
      log.error("获取使用条款异常---"+e.getMessage());
    }
    return result;
  }

  /**
   * 拒绝使用条款
   * @param tipsId
   * @return
   */
  @Override
  public ResponseBase disagreeTips(String tipsId) {
    ResponseBase responseBase = new ResponseBase();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      String userId = JwtTokenUtil.currUser();//微信小程序登录时获得的就是openId
      WechatTips wechatTips = wechatTipsMapper.selectById(tipsId);
      WechatTipsAgree wechatTipsAgree = new WechatTipsAgree(wechatTips,"N",userId);
      wechatTipsAgree.setCreateTime(LocalDateTime.now());
      wechatTipsAgree.setCreateUser(userId);
      wechatTipsAgree.setTenantId(tenantId);
      wechatTipsAgreeMapper.insert(wechatTipsAgree);
      responseBase.setCode(1);
      responseBase.setMessage("已拒绝使用条款");
    }catch(Exception e){
      responseBase.setMessage(e.getMessage());
      log.error("拒绝使用条款异常---"+e.getMessage());
    }
    return responseBase;
  }

  /**
   * 同意使用条款
   * @param tipsId
   * @return
   */
  @Override
  public ResponseBase agreeTips(String tipsId) {
    ResponseBase responseBase = new ResponseBase();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      String userId = JwtTokenUtil.currUser();//微信小程序登录时获得的就是openId
      WechatTips wechatTips = wechatTipsMapper.selectById(tipsId);

      WechatTipsAgree wechatTipsAgree = new WechatTipsAgree(wechatTips,"Y",userId);
      wechatTipsAgree.setCreateTime(LocalDateTime.now());
      wechatTipsAgree.setCreateUser(userId);
      wechatTipsAgree.setTenantId(tenantId);
      wechatTipsAgreeMapper.insert(wechatTipsAgree);
      responseBase.setCode(1);
      responseBase.setMessage("已同意使用条款");
    }catch(Exception e){
      responseBase.setMessage(e.getMessage());
      log.error("同意使用条款异常---"+e.getMessage());
    }
    return responseBase;
  }

  /**
   * 添加使用条款
   * @param device
   * @return
   */
  @Override
  public ResponseBase createTips(TipsDTO device) {
    ResponseBase responseBase = new ResponseBase();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      String userId = JwtTokenUtil.currUser();
      WechatTips tips = new WechatTips(device);
      tips.setStatus(1);
      tips.setTenantId(tenantId);
      tips.setCreateUser(userId);
      tips.setCreateTime(LocalDateTime.now());
      wechatTipsMapper.insert(tips);
      responseBase.setCode(1);
      responseBase.setMessage("添加使用条款成功");
    }catch(Exception e){
      responseBase.setMessage(e.getMessage());
      log.error("添加使用条款异常---"+e.getMessage());
    }
    return responseBase;
  }

  /**
   * 使用条款修改
   * @param device
   * @return
   */
  @Override
  public ResponseBase modifyTips(TipsDTO device) {
    ResponseBase responseBase = new ResponseBase();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      String userId = JwtTokenUtil.currUser();
      WechatTips tips = new WechatTips(device);
      tips.setUuid(device.getUuid());
      tips.setTenantId(tenantId);
      tips.setUpdateUser(userId);
      tips.setUpdateTime(LocalDateTime.now());
      wechatTipsMapper.updateById(tips);
      responseBase.setCode(1);
      responseBase.setMessage("使用条款修改成功");
    }catch(Exception e){
      responseBase.setMessage(e.getMessage());
      log.error("使用条款修改异常");
    }
    return responseBase;
  }

  /**
   * 使用条款明细
   * @param tipsId
   * @return
   */
  @Override
  public ResponseResult<TipsDTO> queryTipsById(String tipsId) {
    ResponseResult<TipsDTO> result = new ResponseResult<>();
    try{
      TipsDTO tipsDTO = wechatTipsMapper.queryTipsById(tipsId);
      if(tipsDTO==null){
        result.setMessage("未查询到相关使用条款明细");
      }else{
        result.setMessage("使用条款明细查询成功");
      }
      result.setCode(1);
      result.setResult(tipsDTO);
    }catch(Exception e){
      result.setMessage(e.getMessage());
      log.error("使用条款明细查询异常---"+e.getMessage());
    }
    return result;
  }

  @Override
  public ResponseResultPage<TipsDTO> queryTips(PageData<TipsRequest> pageData) {
    ResponseResultPage<TipsDTO> responseResultPage = new ResponseResultPage<>();
    try{
      String tenantId = JwtTokenUtil.currTenant();
      Page<TipsDTO> page = new Page<>(pageData.getCurrent(),pageData.getSize());
      page.setDesc(pageData.getDescs());
      page.setAsc(pageData.getAscs());
      Page<TipsDTO> resultPage = wechatTipsMapper.queryTips(page,pageData.getCondition(),tenantId);

    }catch(Exception e){
      responseResultPage.setMessage(e.getMessage());
      log.error("使用条款查询异常---"+e.getMessage());

    }
    return responseResultPage;
  }

  /**
   * 使用条款删除（物理删除）
   * @param tipsId
   * @return
   */
  @Override
  public ResponseBase deleteTips(String tipsId) {
    ResponseBase responseBase = new ResponseBase();
    try{
      wechatTipsMapper.deleteById(tipsId);
      responseBase.setCode(1);
      responseBase.setMessage("使用条款删除成功");
    }catch(Exception e){
      responseBase.setMessage(e.getMessage());
      log.error("使用条款删除异常---"+e.getMessage());
    }
    return responseBase;
  }
}
