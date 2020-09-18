package com.icss.newretail.api.user;


import com.icss.newretail.model.*;
import com.icss.newretail.service.user.CodeService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestSchema(schemaId = "codeapi")
@RequestMapping(path = "/v1/code")
public class CodeApi {

    @Autowired
    private CodeService codeService;

    /**
     * 编码字典分页查询
     *
     * @param pageData
     * @return
     */
    @PostMapping(path = "querycode")
    public ResponseResultPage<UserDictDTO> querycode(@RequestBody PageData<CodeMessage> pageData) {
        return codeService.querycode(pageData);
    }

    /**
     * 编码字典增加
     *
     * @param codeMessage
     * @return
     */
    @PostMapping(path = "saveCode")
    public ResponseBase saveCode(@RequestBody CodeMessage codeMessage) {
        return codeService.saveCode(codeMessage);
    }

    /**
     * 编码字典修改
     *
     * @param codeMessage
     * @return
     */
    @PutMapping(path = "updateCode")
    public ResponseBase updateCode(@RequestBody CodeMessage codeMessage) {
        return codeService.updateCode(codeMessage);
    }

    /**
     * 编码字典删除（逻辑删除）
     *
     * @param uuid
     * @return
     */
    @DeleteMapping(path = "deleteCode")
    public ResponseBase deleteCode(@ApiParam(value = "ID", name = "uuid", required = true) String uuid) {
        return codeService.deleteCode(uuid);
    }

    /**
     * 编码字典子表分页查询
     *
     * @param pageData
     * @return
     */
    @PostMapping(path = "querycodeByName")
    public ResponseResultPage<UserDictValueDTO> querycodeByName(@RequestBody PageData<CodeChidMessage> pageData) {
        return codeService.querycodeByName(pageData);
    }

    /**
     * 编码字典子表增加
     *
     * @param codeChidMessage
     * @return
     */
    @PostMapping(path = "savecodeclid")
    public ResponseBase savecodeclid(@RequestBody CodeChidMessage codeChidMessage) {
        return codeService.savecodeclid(codeChidMessage);
    }

    /**
     * 编码字典子表修改
     *
     * @param codeChidMessage
     * @return
     */
    @PutMapping(path = "updateCodeChild")
    public ResponseBase updateCodeChild(@RequestBody CodeChidMessage codeChidMessage) {
        return codeService.updateCodeChild(codeChidMessage);
    }

    /**
     * 编码字典子表删除（逻辑删除）
     *
     * @param uuid
     * @return
     */
    @DeleteMapping(path = "deleteCodeChild")
    public ResponseBase deleteCodeChild(@ApiParam(value = "ID", name = "uuid", required = true) String uuid) {
        return codeService.deleteCodeChild(uuid);
    }
}


