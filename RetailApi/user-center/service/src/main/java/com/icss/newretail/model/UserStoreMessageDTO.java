package com.icss.newretail.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 终端通知信息
 * </p>
 *
 * @author 
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user_store_message")
@ApiModel(value="UserStoreMessage对象", description="终端通知信息")
public class UserStoreMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "消息ID")
    private String messageId;

    @ApiModelProperty(value = "组织编码(一次只能推到摸个门店，不能指定具体终端)")
    private String orgSeq;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "内容分类(1.商品；2活动；3告知)")
    private Integer contentsType;

    @ApiModelProperty(value = "推送方式(1.手工；2系统)")
    private Integer pushmethod;

    @ApiModelProperty(value = "消息标题")
    private String messageTitle;

    @ApiModelProperty(value = "消息内容")
    private String messageContents;

    @ApiModelProperty(value = "详细内容的URL")
    private String detailedcontentsUrl;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "有效时间")
    private LocalDateTime validTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否阅读(1已经阅读，0未阅读)")
    private Integer isReaded;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
