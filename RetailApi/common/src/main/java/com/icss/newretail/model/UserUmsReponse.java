package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jc
 * @since 2020-04-26
 */
@Data
public class UserUmsReponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "短信平台检测方式（1 --- 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出；" +
            "不为1 或该参数不存在 --- 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出）")
    private Integer f;

    @ApiModelProperty(value = "发送短信号码")
    private String phone;

    @ApiModelProperty(value = "短信模板id")
    private String code;

    @ApiModelProperty(value = "短信内容")
    private String messageContent;

    @ApiModelProperty(value = "自定义流水号，查询回执时使用")
    private String serialNumber;

    @ApiModelProperty(value = "发送时间设置（五分钟以内，默认即时发送）")
    private String cheduleTime;

    @ApiModelProperty(value = "创建人员id")
    private String createUser;

    @ApiModelProperty(value = "短信发送状态（0-标识成功）")
    private String resultStatus;

    @ApiModelProperty(value = "短信描述")
    private String description;

    @ApiModelProperty(value = "失败号码列表")
    private String faillist;

    @ApiModelProperty(value = "任务编号")
    private String taskid;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "短信验证码")
    private String verificationCode;
}
