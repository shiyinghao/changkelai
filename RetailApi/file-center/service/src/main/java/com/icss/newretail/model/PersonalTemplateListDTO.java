package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonalTemplateListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品模板编号")
    @JsonProperty("GoodsTemplateID")
    private String goodsTemplateID;

    @ApiModelProperty(value = "模板缩略图")
    @JsonProperty("ThumbImagePath")
    private String thumbImagePath;

    // 不带中间小图的模板图访问地址（展示模板）
    @ApiModelProperty(value = "展示模板")
    @JsonProperty("ShowImagePath")
    private String showImagePath;

    // 不带中间小图的模板图访问地址（生成作品的大图）
    @ApiModelProperty(value = "生成作品的大图")
    @JsonProperty("OriginImagePath")
    private String originImagePath;

    @ApiModelProperty(value = "大图宽度")
    @JsonProperty("OriginImageWidth")
    private String originImageWidth;

    @ApiModelProperty(value = "大图高度")
    @JsonProperty("OriginImageHeight")
    private String originImageHeight;

    @JsonProperty("ConfigContent")
    private List<PersonalTemplateConfigDTO> configContent;

}