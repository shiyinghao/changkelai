package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ydt
 * @since 2020-05-25
 */
@Data
public class UserIcbcFilingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "工行建档文件类型(1-法定代表人授权书,2-信息查询使用授权书,3-收单业务书,4-非金融企业划型信息采集表,5-对公客户信息采集表" +
            ",6-非自然人客户受益所有人采集表,7-单位税收居民声明文件)")
    private Integer fileType;

    @ApiModelProperty(value = "模板id")
    private String fileTempId;

    @ApiModelProperty(value = "扫描件")
    private String fileScanningCopy;

    @ApiModelProperty(value = "备注")
    private String fileRemarks;

    @ApiModelProperty(value = "文件状态（1-启用，0-失效）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "店铺申请信息id")
    private String reviewId;

    @ApiModelProperty(value = "店铺主键id")
    private String storeId;

    @ApiModelProperty(value = "模板信息")
    private UserIcbcTempDTO UserIcbcTempDTO;

    @ApiModelProperty(value = "工行文件标识key")
    private String icbckey;

    @ApiModelProperty(value = "扫描件名称")
    private String fileScanningCopyName;

}
