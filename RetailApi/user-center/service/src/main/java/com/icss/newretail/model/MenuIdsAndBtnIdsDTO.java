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
public class MenuIdsAndBtnIdsDTO {

    @ApiModelProperty(value = "菜单menuIds")
    private List<String> menuIds;

    @ApiModelProperty(value = "按钮btnIds")
    private List<String> btnIds;


}
