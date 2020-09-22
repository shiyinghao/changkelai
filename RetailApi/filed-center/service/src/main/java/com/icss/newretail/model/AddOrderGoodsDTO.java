package com.icss.newretail.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddOrderGoodsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("OutsideGoodsID")
    @JSONField(name="OutsideGoodsID")
    private String OutsideGoodsID; // 云店商品唯一标识

    @JsonProperty("FinalPrice")
    @JSONField(name="FinalPrice")
    private String FinalPrice; // 商品单价

    @JsonProperty("GoodsCount")
    @JSONField(name="GoodsCount")
    private int GoodsCount; // 商品数量

    @JsonProperty("GoodsName")
    @JSONField(name="GoodsName")
    private String GoodsName; // 商品名称

    @JsonProperty("PackingUnit")
    @JSONField(name="PackingUnit")
    private String PackingUnit; // 商品销售单位：瓶、箱、件

    @JsonProperty("GoodsImagePath")
    @JSONField(name="GoodsImagePath")
    private String GoodsImagePath; // 商品缩略图地址

    @JsonProperty("GoodsTemplateID")
    @JSONField(name="GoodsTemplateID")
    private String GoodsTemplateID; // 作品使用的商品模板编号

    @JsonProperty("OwnerTypeID")
    @JSONField(name="OwnerTypeID")
    private int OwnerTypeID; // 客户类型（1：公司，2：个人）	1

    @JsonProperty("OwnerName")
    @JSONField(name="OwnerName")
    private String OwnerName; // 公司/个人名称	五粮创艺

    @JsonProperty("LicenseNumber")
    @JSONField(name="LicenseNumber")
    private String LicenseNumber; // 公司营业执照号码	客户类型为1时必传

    @JsonProperty("LicensePath")
    @JSONField(name="LicensePath")
    private String LicensePath; // 营业执照图片下载地址	客户类型为1时必传

    @JsonProperty("IDNumber")
    @JSONField(name="IDNumber")
    private String IDNumber; // 个人身份证号码	客户类型为2时必传

    @JsonProperty("IDFrontPath")
    @JSONField(name="IDFrontPath")
    private String IDFrontPath; // 身份证正面图片下载地址	客户类型为2时必传

    @JsonProperty("IDBackPath")
    @JSONField(name="IDBackPath")
    private String IDBackPath; // 身份证背面图片下载地址	客户类型为2时必传

    @JsonProperty("OutsideWorksID")
    @JSONField(name="OutsideWorksID")
    private String OutsideWorksID; // 云店作品唯一标识(同一个作品，在通知审核结果前，不能重复下单)

    @JsonProperty("FinalImagePath")
    @JSONField(name="FinalImagePath")
    private String FinalImagePath; // 作品生成的大图下载地址

    @JsonProperty("ShowImagePath")
    @JSONField(name="ShowImagePath")
    private String ShowImagePath; // 作品大图和瓶子图合成图下载地址

}