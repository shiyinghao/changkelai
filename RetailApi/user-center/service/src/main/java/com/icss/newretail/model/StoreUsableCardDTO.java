package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yanghu
 * @date : Created in 2020/7/17 13:31
 * @description : 店铺可用名片DTO
 * @modified By :
 * @version: : 1.0.0
 */

@Data
public class StoreUsableCardDTO {

    @ApiModelProperty(value = "卡片uuid")
    private String uuid;

    @ApiModelProperty(value = "风格名称--card_name")
    private String cardName;

    @ApiModelProperty(value = "字体颜色")
    private String fontColor;


    @ApiModelProperty(value = "风格图片--card_pic")
    private String cardPic;

    @ApiModelProperty(value = "是否被选中  1 当前被选中")
    private Integer checked;

    /**
     *  2020-08-05  add
     */
    @ApiModelProperty(value = "背景底色")
    private String background;

    private String isSpecial;
}
