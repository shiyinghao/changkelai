package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginByAuthCode implements Serializable{

    private static final long serialVersionUID = 189548253786L;

    private String realName;
    private String headPicUrl;
    private String sex;
    private String openId;
    private String account;
    private String authCode;
    private String orgSeq;
//    private String inviteCode;
}
