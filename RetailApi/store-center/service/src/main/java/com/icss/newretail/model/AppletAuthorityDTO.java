package com.icss.newretail.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jc
 * @date 2020/4/20 17:43
 */
@Data
public class AppletAuthorityDTO implements Serializable {

	private static final long serialVersionUID = 8218344164083065760L;

	private String userId;

	private Integer userType;

	private String orgSeq;

	private List<ShopAppletAuthortiryDto> authorityList;

}
