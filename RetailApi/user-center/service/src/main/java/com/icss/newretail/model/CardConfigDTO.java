package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : yanghu
 * @date : Created in 2020/7/17 11:37
 * @description : TODO
 * @modified By :
 * @version: : TODO
 */

@Data
public class CardConfigDTO {

    @ApiModelProperty(value = "门头照片地址")
    private String doorwayPic;

    @ApiModelProperty(value = "店铺介绍")
    private String storeDesc;

    @ApiModelProperty(value = "店铺对应卡片风格name")
    private String cardName;

    @ApiModelProperty(value = "背景图片")
    private String cardPic;

    @ApiModelProperty(value = "字体颜色")
    private String fontColor;

    /**
     *  2020-08-05  add
     */
    @ApiModelProperty(value = "背景底色")
    private String background;
    private String headPicUrl;

    private Integer isSpecial;

}
