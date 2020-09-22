package com.icss.newretail.model;

import com.icss.newretail.util.ToolUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 特征参数
 * </p>
 *
 * @author cym
 * @since 2020-04-08
 */
@Data
public class GoodsArrayParamDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID串")
    private String goods;
    
    @ApiModelProperty(value = "门店编码")
    private String storeSeq;
    
    @ApiModelProperty(value = "战区编码")
    private String warzoneSeq;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "防全查")
    private String empty;

    @ApiModelProperty(value = "预警信息列表")
    private List<ShopGoodsWarnDTO> goodsWarnList;

    @ApiModelProperty(value = "商品编码（主要用于 小程序分享码 解析获取商品信息）")
    private String goodsSeq;

    @Autowired
    public String getEmpty() {
        if (ToolUtil.isEmpty(goods) && ToolUtil.isEmpty(goodsName) && ToolUtil.isEmpty(goodsSeq)) {
            empty = "empty";
        }
        return empty;
    }
}
