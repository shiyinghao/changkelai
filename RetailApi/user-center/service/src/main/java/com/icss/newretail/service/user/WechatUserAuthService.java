package com.icss.newretail.service.user;

import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.WechatUserAuthDTO;

import java.util.List;

/**
 * <p>
 * 微信人员认证关系表 服务类
 * </p>
 *
 * @author zhangzhijia
 * @since 2019-05-10
 */
public interface WechatUserAuthService {
	/**
	 * 根据人员编码获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByOpenId(String authType);
	/**
	 * 根据人员编码获取认证信息列表
	 */
	public ResponseRecords<WechatUserAuthDTO> getWechatUserAuthListByOpenId(List<String> authTypes);
	/**
	 * 效验当前微信人员是否认证过任一角色
	 */
	public ResponseResult<Integer> checkAuthAnyOfUser(List<String> authTypes);
	/**
	 * 根据认证账号获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccount(String authType, String authAccount);
	/**
	 * 根据认证账号获取认证信息
	 */
	public ResponseResult<WechatUserAuthDTO> getWechatUserAuthByAuthAccountOpenId(String authType, String authAccount);
	/**
	 * 新增
	 * @author
	 * @createtime
	 */
	public ResponseBase insertWechatUserAuth(WechatUserAuthDTO wechatUserAuthDTO);
	/**
	 * 修改
	 * @author
	 * @createtime
	 */
	public ResponseBase updateWechatUserAuth(WechatUserAuthDTO wechatUserAuthDTO);
	/**
	 * 删除
	 * @author
	 * @createtime
	 */
	public ResponseBase deleteWechatUserAuth(String authUuid);
	/**
	 * 取消绑定
	 */
	public ResponseBase updateWechatUserAuthStatus(String authType);
}
