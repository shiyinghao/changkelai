package com.icss.newretail.api.user;

import com.icss.newretail.model.MenuButtonDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.user.BtnService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotEmpty;

/**
 * 按钮级别操作  btn
 * @author yanghu
 * Create by yang on 2020.03.26
 */
@RestSchema(schemaId = "BtnApi")
@RequestMapping(path = "/v1/BtnApi")
public class BtnApi {
    @Autowired
    private BtnService btnService;

    /**
     * 添加Btn
     * ---添加时----uuid必须为空
     * @param menuButtonDTO
     * @return
     */
    @PostMapping(path = "createBtn")
    public ResponseBase createBtn(@RequestBody  MenuButtonDTO menuButtonDTO) {
        return btnService.updateBtn(menuButtonDTO);
    }

    /**
     * 删除Btn
     *-----同时删除多个  传输参数的时候使用,分隔
     * @param uuid
     * @return
     */
    @PostMapping(path = "deleteBtn")
    public ResponseBase deleteBtn(
            @NotEmpty(message = "参数uuid不能为空") @ApiParam(name = "uuid", value = "按钮uuid", required = true) String uuid) {
        return btnService.deleteBtn(uuid);
    }

    /**
     *
     * 通过id修改Btn
     *
     * @param menuButtonDTO
     * @return
     */
    @PostMapping(path = "updateBtn")
    public ResponseBase updateBtn(@RequestBody MenuButtonDTO menuButtonDTO) {
        return btnService.updateBtn(menuButtonDTO);
    }

    /**
     * 查询Btn
     *  根据ID查询单个按钮
     * @param menuButtonDTO
     * @return
     */
    @PostMapping(path = "queryBtn")
    public ResponseResult queryBtn(@RequestBody MenuButtonDTO menuButtonDTO) { return btnService.queryBtn(menuButtonDTO); }


}
