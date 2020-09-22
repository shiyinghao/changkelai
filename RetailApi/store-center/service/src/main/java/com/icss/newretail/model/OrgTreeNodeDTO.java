package com.icss.newretail.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.icss.newretail.util.ToolUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织机构编码信息
 *
 * @author cym
 * @date 2020-04-05
 */
@Data
public class OrgTreeNodeDTO {

	@ApiModelProperty(value = "组织编码")
	private String orgSeq;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "上级组织编码")
	private String upOrgSeq;

	@ApiModelProperty(value = "组织类型")
	private String orgType;

	@ApiModelProperty(value = "状态(停用/启用)")
	private Integer status;
	
	@ApiModelProperty(value = "是否有子结点")
    private int hasChildren;
	
	@ApiModelProperty(value = "子结点")
	private String childrenList;

	@ApiModelProperty(value = "门店结点")
	private String shopList;
	
	@ApiModelProperty(value = "子结点")
	private List<OrgTreeNodeDTO> childList;
	
	@ApiModelProperty(value = "战区名称")
    private String warzoneName;
    
    @ApiModelProperty(value = "基地名称")
    private String baseName;
    
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    
    @ApiModelProperty(value = "经销商名称")
    private String companyName;
	
	@ApiModelProperty(value = "授权码")
	private String authCode;

	@ApiModelProperty(value = "门店结点")
	private List<OrgTreeNodeDTO> storeList;
	
	@Autowired
	public int getHasChildren(){
		if(ToolUtil.isEmpty(childList)){
			return 0;
		}else{
			return 1;
		}
	}

	@Autowired
	public String getChildrenList() {
		String ret = null;
    	if(ToolUtil.isEmpty(childList)){
    		ret = "";
    	}else{
    		StringBuffer sb = new StringBuffer();
    		for (OrgTreeNodeDTO orgTreeNodeDTO : childList) {
    			sb.append(orgTreeNodeDTO.getOrgSeq()).append(",");
			}
    		if(sb.length() > 0){
    			ret = sb.substring(0, sb.length() - 1).toString();
    		}else{
    			ret = "";
    		}
    	}
		return ret;
	}
	
	@Autowired
	public String getShopList() {
		String ret = null;
    	if(ToolUtil.isEmpty(storeList)){
    		ret = "";
    	}else{
    		StringBuffer sb = new StringBuffer();
    		for (OrgTreeNodeDTO orgTreeNodeDTO : storeList) {
    			sb.append(orgTreeNodeDTO.getOrgSeq()).append(",");
			}
    		if(sb.length() > 0){
    			ret = sb.substring(0, sb.length() - 1).toString();
    		}else{
    			ret = "";
    		}
    	}
		return ret;
	}
}
