package com.icss.newretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.io.Serializable;

@Data
public class PersonalTemplateTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品瓶子图，用于生成作品后，附上作品查看作品效果的图片
    @ApiModelProperty(value = "商品瓶子图")
    private String goodsImage;

    // 模板分类列表
    private List<PersonalTemplateTypeListDTO> categoryList;

}