package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jc
 * @date 2020/3/20 17:06
 */
@Data
public class ResourceTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "当前节点id")
	private String id;// 当前节点id

	@ApiModelProperty(value = "父节点id")
	private String pId;// 父节点id

	@ApiModelProperty(value = "节点名称")
	private String name;// 名称

	@ApiModelProperty(value = "是否选中")
	private boolean checked;//是否选中

	@ApiModelProperty(value = "主键")
	private String uuid;

	@ApiModelProperty(value = "资源等级")
	private String resourceLevel;

	private List<ResourceTree> children;
	
	private List<ResourceTree> btnChildren;
	
	@ApiModelProperty(value = "角色id")
	private String rId;

}
