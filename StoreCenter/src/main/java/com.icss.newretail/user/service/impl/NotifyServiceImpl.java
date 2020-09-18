package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.NoticeDTO;
import com.icss.newretail.model.NoticeRequest;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserStoreMessageDTO;
import com.icss.newretail.service.user.NoticeService;
import com.icss.newretail.service.user.OrganizationService;
import com.icss.newretail.user.dao.DeviceMapper;
import com.icss.newretail.user.dao.NoticeMapper;
import com.icss.newretail.user.entity.Notice;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NotifyServiceImpl implements NoticeService {

  @Autowired
  private NoticeMapper noticeMapper;
  @Autowired
  private DeviceMapper deviceMapper;
  @Autowired
  private OrganizationService organizationService;
  /**
   * 公告发布（向本单位所有终端发送通知消息（organizationService.sendMessages）并更新发布信息）
   *
   * @param notifyMsg
   * @return
   */
  @Override
  @Transactional(rollbackFor = { Exception.class })
  public ResponseBase release(NoticeDTO notifyMsg) {
    ResponseBase base = new ResponseBase();
    try {
      String userId = JwtTokenUtil.currUser();
      Notice notice = noticeMapper.selectById(notifyMsg.getUuid());
      //查询本单位所有终端设备
      List<String> deviceIds = deviceMapper.getPOSDeviceIdByStoreId(notifyMsg.getOrgSeq());

      //发送通知消息（organizationService.sendMessages）并更新发布信息
      UserStoreMessageDTO message = new UserStoreMessageDTO();
      message.setContentsType(3);
      message.setOrgSeq(notifyMsg.getOrgSeq());
      message.setPushmethod(2);
      //如果前端传过来的标题或者内容为空，则使用数据库的
      message.setMessageTitle(notifyMsg.getTitle());
      if(StringUtils.isBlank(notifyMsg.getTitle())){
        message.setMessageTitle(notice.getTitle());
      }
      message.setMessageContents(notifyMsg.getContent());
      if(StringUtils.isBlank(notifyMsg.getContent())){
        message.setMessageTitle(notice.getContent());
      }
      message.setSendTime(LocalDateTime.now());
      message.setStatus(1);
      message.setIsReaded(0);//未阅读
      organizationService.sendMessages(deviceIds,message);

      Notice updateNotice = new Notice(notifyMsg);
      updateNotice.setUpdateUser(userId);
      updateNotice.setStatus(1);//已发布
      //如果前端没传发布人，则将当前登录人设置为发布人
      if(StringUtils.isBlank(updateNotice.getPublisher())){
        updateNotice.setPublisher(userId);
      }
      noticeMapper.updateById(updateNotice);
      base.setCode(1);
      base.setMessage("通知公告发布成功。");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("NotifyServiceImpl|release|" + e.getMessage());
    }
    return base;
  }

  /**
   * 通知公告添加
   *
   * @param noticeDTO
   * @return
   */
  @Override
  @Transactional(rollbackFor = { Exception.class })
  public ResponseBase createNotice(NoticeDTO noticeDTO) {
    ResponseBase base = new ResponseBase();
    try {
      Notice notice = Object2ObjectUtil.parseObject(noticeDTO,Notice.class);
      notice.setCreateTime(LocalDateTime.now());
      notice.setCreateUser(JwtTokenUtil.currUser());
      if(notice.getStatus() == null){
        notice.setStatus(0);//未发布
      }
      noticeMapper.insert(notice);
      base.setCode(1);
      base.setMessage("通知公告添加成功。");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("NotifyServiceImpl|createNotice|" + e.getMessage());
    }
    return base;
  }

  /**
   * 通知公告修改
   *
   * @param noticeDTO
   * @return
   */
  @Override
  @Transactional(rollbackFor = { Exception.class })
  public ResponseBase modifyNotice(NoticeDTO noticeDTO) {
    ResponseBase base = new ResponseBase();
    try {
      Notice notice = Object2ObjectUtil.parseObject(noticeDTO,Notice.class);
      notice.setUpdateUser(JwtTokenUtil.currUser());
      noticeMapper.updateById(notice);
      base.setCode(1);
      base.setMessage("通知公告修改成功");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("NotifyServiceImpl|modifyNotice|" + e.getMessage());
    }
    return base;
  }

  /**
   * 通知公告删除
   *
   * @param noticeId
   * @return
   */
  @Override
  public ResponseBase deleteNotice(String noticeId) {
    ResponseBase base = new ResponseBase();
    try {
      noticeMapper.deleteById(noticeId);
      base.setCode(1);
      base.setMessage("通知公告删除成功");
    }catch (Exception e){
      base.setCode(0);
      base.setMessage(e.getMessage());
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
      log.error("NotifyServiceImpl|deleteNotice|" + e.getMessage());
    }
    return base;
  }

  /**
   * 通知公告明细查询
   *
   * @param noticeId
   * @return
   */
  @Override
  public ResponseResult<NoticeDTO> queryNoticeById(String noticeId) {
    ResponseResult<NoticeDTO> result = new ResponseResult<NoticeDTO>();
    NoticeDTO noticeDTO = noticeMapper.queryNoticeById(noticeId);
    result.setCode(1);
    result.setMessage("通知公告明细查询成功");
    result.setResult(noticeDTO);
    return result;
  }

  /**
   * 通知广告查询
   *
   * @param pageData
   * @return
   */
  @Override
  public ResponseResultPage<NoticeDTO> queryNotices(PageData<NoticeRequest> pageData) {
    ResponseResultPage<NoticeDTO> result = new ResponseResultPage<NoticeDTO>();
    Page<NoticeDTO> page = new Page<>(pageData.getCurrent(),pageData.getSize());
    page.setAsc(pageData.getAscs());
    page.setDesc(pageData.getDescs());
    Page<NoticeDTO> resultPage = noticeMapper.queryNotices(page,pageData.getCondition(),JwtTokenUtil.currTenant());
    result.setCode(1);
    result.setMessage("通知广告查询成功");
    result.setCurrent(pageData.getCurrent());
    result.setSize(pageData.getSize());
    result.setTotal(resultPage.getTotal());
    result.setRecords(resultPage.getRecords());
    return result;
  }

  /**
   * 公共的消息推送调用，用于跨模块的调用,contentsType 消息分类1.商品；2活动；3告知；4提醒
   *   pushmethod 推送方式   推送方式(1.手工；2系统)',
  * @author fangxm 
  * @date 2019年11月5日 
  * @param 
  * @return
  * @description:
   */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseBase releaseComm(NoticeDTO notifyMsg,int contentsType,int pushmethod, List<String>  orgSeqList) {
	    ResponseBase base = new ResponseBase();
	    try {
	     List<String> deviceIds=new ArrayList<String>();
	      String userId = JwtTokenUtil.currUser();
	      Notice notice = noticeMapper.selectById(notifyMsg.getUuid());
	      //查询本单位所有终端设备,有可能指定门店，如果为空，就不会指定门店,查询所有的门店
	      if(com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(orgSeqList)){
	    	  deviceIds = deviceMapper.getPOSDeviceIdByStoreId(notifyMsg.getOrgSeq());
	      }else{
	    	  deviceIds = deviceMapper.getPOSDeviceIdByStoreIds(orgSeqList);
	      }
	      //发送通知消息（organizationService.sendMessages）并更新发布信息
	      UserStoreMessageDTO message = new UserStoreMessageDTO();
	      message.setContentsType(contentsType);
	      message.setOrgSeq(notifyMsg.getOrgSeq());
	      message.setPushmethod(pushmethod);
	      //如果前端传过来的标题或者内容为空，则使用数据库的
	      message.setMessageTitle(notifyMsg.getTitle());
	      if(StringUtils.isBlank(notifyMsg.getTitle())){
	        message.setMessageTitle(notice.getTitle());
	      }
	      message.setMessageContents(notifyMsg.getContent());
	      if(StringUtils.isBlank(notifyMsg.getContent())){
	        message.setMessageTitle(notice.getContent());
	      }
	      message.setSendTime(LocalDateTime.now());
	      message.setStatus(1);
	      message.setIsReaded(0);//未阅读
	      organizationService.sendMessages(deviceIds,message);
	
	      Notice updateNotice = new Notice(notifyMsg);
	      updateNotice.setUpdateUser(userId);
	      updateNotice.setStatus(1);//已发布
	      //如果前端没传发布人，则将当前登录人设置为发布人
	      if(StringUtils.isBlank(updateNotice.getPublisher())){
	        updateNotice.setPublisher(userId);
	      }
	      noticeMapper.updateById(updateNotice);
	      base.setCode(1);
	      base.setMessage("通知公告发布成功。");
	    }catch (Exception e){
	      base.setCode(0);
	      base.setMessage(e.getMessage());
	      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚事务
	      log.error("NotifyServiceImpl|release|" + e.getMessage());
	    }
	    return base;
	}

 
  
  
}
