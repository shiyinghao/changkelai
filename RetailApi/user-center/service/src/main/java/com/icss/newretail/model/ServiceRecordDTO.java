package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yanghu
 * @date : Created in 2020/7/21 16:23
 * @description : 商家服务记录 用户中心跨库查询
 * @modified By :
 * @version: : 1.0.0
 */

@Data
public class ServiceRecordDTO {
    /**
     * //用户中心
     * *   `up_org_zone` varchar(64) DEFAULT NULL COMMENT '战区名称',
     * *   `base_name` varchar(64) DEFAULT NULL COMMENT '基地名称',
     * *   `auth_code` varchar(64) DEFAULT NULL COMMENT '店铺授权编码',
     * *   `store_name` varchar(64) DEFAULT NULL COMMENT '店铺名称',
     * *   `user_type_name` varchar(64) DEFAULT NULL COMMENT '维护人岗位',
     * *   `user_name` varchar(64) DEFAULT NULL COMMENT '维护人姓名',
     */
    @ApiModelProperty(value = "战区名称")
    private String upOrgZone;

    @ApiModelProperty(value = "基地名称")
    private String baseName;

    @ApiModelProperty(value = "店铺授权编码")
    private String authCode;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "维护人岗位")
    private String userTypeName;

    @ApiModelProperty(value = "维护人姓名")
    private String userName;
}
