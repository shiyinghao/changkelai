package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.UserSystemParamService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @author wy
 * @date 2020/3/26 16:05
 */
@RestSchema(schemaId = "system")
@RequestMapping(path = "/v1/system")
public class SystemApi {

    @Autowired
    private UserSystemParamService userSystemParamService;

    /**
     * 系统配置查询
     *
     * @param name,code
     * @return
     */
    @PostMapping(path = "queryUserSystemParam")
    public ResponseRecords<UserSystemParamDTO> queryUserSystemParam(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "code", required = false) String code) {
        return userSystemParamService.queryUserSystemParam(name, code);
    }

    @PostMapping(path = "querySystemParam")
    public ResponseResultPage<UserSystemParamDTO> querySystemParam(@RequestBody PageData<UserSystemParamDTO> pageData) {
        return userSystemParamService.querySystemParam(pageData);
    }

    /**
     * 添加系统配置
     *
     * @param systemParam
     * @return
     */
    @PostMapping(path = "createSystemParam")
    public ResponseBase createSystemParam(@RequestBody UserSystemParamDTO systemParam) {
        return userSystemParamService.createSystemParam(systemParam);
    }

    /**
     * 修改系统配置
     * s
     *
     * @param systemParam
     * @return
     */
    @PutMapping(path = "modifySystemParam")
    public ResponseBase modifySystemParam(@RequestBody UserSystemParamDTO systemParam) {
        return userSystemParamService.modifySystemParam(systemParam);
    }

    /**
     * 删除系统配置
     *
     * @param uuid
     * @return
     */
    @DeleteMapping(path = "deleteSystemParam")
    public ResponseBase deleteSystemParam(
            @NotEmpty(message = "参数uuid不能为空") @ApiParam(name = "uuid", value = "系统配置uuid", required = true) String uuid) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(uuid)) {
            responseBase.setCode(0);
            responseBase.setMessage("参数uuid不能为空");
            return responseBase;
        }
        return userSystemParamService.deleteSystemParam(uuid);
    }

    /**
     * 系统配置值查询
     *
     * @param paramId
     * @return
     */
    @PostMapping(path = "queryUserSystemParamValue")
    public ResponseRecords<UserSystemParamValueDTO> queryUserSystemParamValue(@RequestParam String paramId) {
        return userSystemParamService.queryUserSystemParamValue(paramId);
    }

    /**
     * 添加系统配置
     *
     * @param systemParamValue
     * @return
     */
    @PostMapping(path = "createSystemParamValue")
    public ResponseBase createSystemParamValue(@RequestBody UserSystemParamValueDTO systemParamValue) {
        return userSystemParamService.createSystemParamValue(systemParamValue);
    }

    /**
     * 修改系统配置
     * s
     *
     * @param systemParamValue
     * @return
     */
    @PutMapping(path = "modifySystemParamValue")
    public ResponseBase modifySystemParamValue(@RequestBody UserSystemParamValueDTO systemParamValue) {
        return userSystemParamService.modifySystemParamValue(systemParamValue);
    }

    /**
     * 删除系统配置
     *
     * @param uuid
     * @return
     */
    @DeleteMapping(path = "deleteSystemParamValue")
    public ResponseBase deleteSystemParamValue(
            @NotEmpty(message = "参数uuid不能为空") @ApiParam(name = "uuid", value = "系统配置uuid", required = true) String uuid) {
        ResponseBase responseBase = new ResponseBase();
        if (StringUtils.isBlank(uuid)) {
            responseBase.setCode(0);
            responseBase.setMessage("参数uuid不能为空");
            return responseBase;
        }
        return userSystemParamService.deleteSystemParamValue(uuid);
    }

    @GetMapping(path = "querybindexpiredDay")
    public ResponseResult<UserSystemParamDTO> querybindexpiredDay(String code) {
        return userSystemParamService.querybindexpiredDay(code);
    }

}
