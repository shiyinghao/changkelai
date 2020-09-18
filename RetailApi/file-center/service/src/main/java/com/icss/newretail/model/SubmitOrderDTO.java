package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubmitOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String outsideWorksID; // 外部作品编号

    private String goodsTemplateID; // 作品使用的商品模板编号

    private String finalImagePath; // 作品生成的大图下载地址

    private String showImagePath; // 作品大图和瓶子图合成图下载地址

    private String worksbackUrl; // 作品审核回调地址

    private int ownerTypeID; // 客户类型（1：公司，2：个人）
    private String ownerName; // 公司/个人名称
    private String licenseNumber; // 公司营业执照号码
    private String licensePath; // 营业执照图片下载地址
    private String idNumber; // 个人身份证号码
    private String idFrontPath; // 身份证正面图片下载地址
    private String idBackPath; // 身份证背面图片下载地址

}