package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jc
 * @date 2020/6/22 17:44
 */
@Data
public class StoreAccountDTO implements Serializable {

	private static final long serialVersionUID = 6334665715316511014L;

	private String userId;

	private String orgSeq;

	private String authCode;

	private String password;

}
