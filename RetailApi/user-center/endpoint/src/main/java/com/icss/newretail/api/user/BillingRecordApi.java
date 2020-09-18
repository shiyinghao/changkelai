package com.icss.newretail.api.user;

import com.icss.newretail.model.*;
import com.icss.newretail.service.user.BillingRecordService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "billingapi")
@RequestMapping(path = "/v1/billingRecord")
public class BillingRecordApi {

    @Autowired
    private BillingRecordService billingRecordService;

    /**
     *  新增店铺开票记录
     *
     * @return
     */
    @PostMapping(path = "addBillingRecord")
    public ResponseBase addBillingRecord(@RequestBody BillingRecordDTO para) {
        return billingRecordService.addBillingRecord(para);
    }

    /**
     * 门店查询当前问题 传参 门店orgSeq
     *
     * @return
     */
    @PostMapping(path = "queryBillingRecord")
    public ResponseResultPage<BillingRecordDTO> queryBillingRecord(@RequestBody PageData<BillingRecordDTO> para) {
        return billingRecordService.queryBillingRecord(para);
    }

    /**
     * 根据uuid查出明细 带出门店信息
     * @param uuid
     * @return
     */
    @GetMapping(path = "queryBillingRecordById")
    public ResponseResult<BillingRecordDTO> queryBillingRecordById(
            @ApiParam(name = "orgseq", value = "orgseq", required = false) String orgseq,
            @ApiParam(name = "uuid", value = "uuid", required = true) String uuid) {
        ResponseResult<BillingRecordDTO> result = new ResponseResult<>();
        if (StringUtils.isBlank(uuid)) {
            result.setCode(0);
            result.setMessage("传入的参数不能为空");
            return result;
        }
        return billingRecordService.queryBillingRecordById(orgseq,uuid);
    }


    /**
     *  修改店铺开票记录
     *
     * @return
     */
    @PostMapping(path = "updateBillingRecordById")
    public ResponseBase updateBillingRecordById(@RequestBody BillingRecordDTO para) {
        return billingRecordService.updateBillingRecordById(para);
    }
    /**
     * 根据region查出明细 带出电话信息
     * @param para
     * @return
     */
    @PostMapping(path = "queryRegionTel")
    public ResponseResultPage<BillingRegionDTO> queryRegionTel(@RequestBody PageData<BillingRecordDTO> para) {
        return billingRecordService.queryRegionTel(para);
    }


}
