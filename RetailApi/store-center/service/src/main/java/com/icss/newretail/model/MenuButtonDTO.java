package com.icss.newretail.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单按钮表
 * </p>
 *
 * @author jc
 * @since 2020-03-17
 */
@Data
public class MenuButtonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String uuid;

    @ApiModelProperty(value = "菜单id")
    private String menuId;

    @ApiModelProperty(value = "按钮名称")
    private String btnName;

    @ApiModelProperty(value = "按钮编码")
    private String btnCode;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;
    
    @ApiModelProperty(value = "按钮")
    private List<MenuButtonDTO> btnChildren;

}
