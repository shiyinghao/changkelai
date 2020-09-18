package com.icss.newretail.api.file;

import com.alibaba.fastjson.JSONObject;
import com.icss.newretail.model.*;
import com.icss.newretail.service.file.PersonalTemplateService;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestSchema(schemaId = "PersonalTemplateApi")
@RequestMapping(path = "/v1/template")
public class PersonalTemplateApi {

    @Autowired
    private PersonalTemplateService personalTemplateService;

    /**
     * 获取客户端IP测试
     * @param request
     * @return
     */
    /*
    @GetMapping("iptest")
    public String iptest(@RequestHeader HttpServletRequest request) {
        System.out.println("==========iptest==========");
        String getHeader_ip = request.getHeader("X-Real-IP");
        String getAttribute_ip = (String) request.getAttribute("X-Real-IP");
        return getHeader_ip + " | " + getAttribute_ip;
    }
    */

    /**
     * 提供给创艺-获取token
     * @param
     * @return ResponseBaseToken
     */
    @PostMapping(path = "getToken")
    public ResponseBaseToken getToken(@RequestBody GetTokenDTO tokenDTO) {
        return personalTemplateService.getToken(tokenDTO);
    }

    /**
     * @apiNote 统一访问创艺接口
     * @author WXG
     * @param param
     * @return
     */
    @PostMapping(path = "accesscy")
    public String accesscy(@RequestBody AccesscyDTO param) {
        String result = personalTemplateService.accesscy(param);
        return result;
    }

    /**
     * @apiNote 查询模板分类
     * @author WXG
     * @param OutsideGoodsID
     * @return
     */
    @GetMapping("queryTemplateTypeList")
    public ResponseResult<PersonalTemplateTypeDTO> queryTemplateTypeList(@ApiParam(name = "OutsideGoodsID", value = "商品ID", required = true) String OutsideGoodsID) {
        ResponseResult<PersonalTemplateTypeDTO> result = personalTemplateService.queryTemplateTypeList(OutsideGoodsID);
        return result;
    }

    /**
     * @apiNote 查询模板列表
     * @author WXG
     * @return
     */
    @PostMapping(path = "queryTemplateList")
    public ResponseRecords<PersonalTemplateListDTO> queryTemplateList(@RequestBody PageData<PersonalTemplateParamDTO> param) {
        return personalTemplateService.queryTemplateList(param);
    }

    /**
     * @apiNote 查询模板详情
     * @author WXG
     * @param GoodsTemplateID
     * @return
     */
    @GetMapping("queryTemplateDetail")
    @ResponseBody
    public ResponseResult<JSONObject> queryTemplateDetail(@ApiParam(name = "GoodsTemplateID", value = "模板ID", required = true) String GoodsTemplateID) {
        ResponseResult<JSONObject> result = personalTemplateService.queryTemplateDetail(GoodsTemplateID);
        return result;
    }

    /**
     * @apiNote 提交订制酒服务单
     * @author WXG
     * @return
     */
    @PostMapping(path = "addTemplateOrder")
    public ResponseResult<JSONObject> addTemplateOrder(@RequestBody AddOrderDTO param) {
        return personalTemplateService.addTemplateOrder(param);
    }

    /**
     * @apiNote 取消订制酒服务单
     * @author WXG
     * @return
     */
    @PostMapping(path = "cancelTemplateOrder")
    public ResponseResult<JSONObject> cancelTemplateOrder(@RequestBody AddOrderDTO param) {
        return personalTemplateService.cancelTemplateOrder(param);
    }

    /**
     * @apiNote 作品提交
     * @author WXG
     * @return
     */
    @PostMapping(path = "submitTemplateOrder")
    public ResponseResult<String> submitTemplateOrder(@RequestBody SubmitOrderDTO param) {
        return personalTemplateService.submitTemplateOrder(param);
    }

}