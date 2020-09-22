/*******************************************************************************
 * Copyright(c) ICSS Corporation and others. All rights reserved.
 ******************************************************************************/
package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信小程序登录入口
 * 
 * 
 * @author zhangzhijia
 * @date Apr 18, 2019
 */
@Data
public class LoginMaRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    private String code;
    private String state;

    private String corpId;
    private String corpSecret;

}
