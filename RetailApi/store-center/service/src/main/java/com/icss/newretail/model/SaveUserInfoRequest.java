package com.icss.newretail.model;


import lombok.Data;

@Data
public class SaveUserInfoRequest {

	private String memberId;

	public String encryptedData;

	public String iv;

	private String corpId;
	private String corpSecret;
	
	public String openId;

	public String code;

	public String state;
}
