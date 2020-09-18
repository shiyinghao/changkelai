package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : yanghu
 * @date : Created in 2020/7/16 17:08
 * @description : TODO
 * @modified By :
 * @version: : TODO
 */

@Data
@Accessors(chain = true)
public class StoreCardDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "名片类型(1-商品2-活动3-店铺)")
    private Integer cardType;

    @ApiModelProperty(value = "名片名称")
    private String cardName;

    @ApiModelProperty(value = "名片图片")
    private String cardPic;

    @ApiModelProperty(value = "字体颜色")
    private String fontColor;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "发布状态(1-发布/0-不发布)")
    private Integer publishStatus;

    @ApiModelProperty(value = "状态(1-启用/0-弃用)")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     *  2020-08-05  add
     */
    @ApiModelProperty(value = "背景底色")
    private String background;
//-----------------------------

    //新增前端虚拟字段

    @ApiModelProperty(value = "活动名片和商品名片是否过期  1进行中  0已结束")
    private Integer expiredStatus;

    @ApiModelProperty(value = "是否特殊(1-是/0-否)")
    private Integer isSpecial;
}
