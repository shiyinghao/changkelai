package com.icss.newretail.api.user;

import com.icss.newretail.model.OperationLogDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.service.user.OperationLogService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestSchema(schemaId = "operationLog")
@RequestMapping(path = "/v1/operationLog")
public class OperationLogApi {
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 用户操作记录查询
     * 
     * @param deviceId
     * @param operationType
     * @return
     */
    @GetMapping(path = "queryOperationLogByDeviceAndOpeType")
    public List<OperationLogDTO> queryOperationLogByDeviceAndOpeType(@RequestParam(name = "deviceId") String deviceId,
	    @RequestParam(name = "operationType") String operationType) {
	return operationLogService.queryOperationLogByDeviceAndOpeType(deviceId, operationType);
    }
    
    /**
     * 用户操作记录保存（直接插入数据）
     * 
     * @param para
     * @return
     */
    @PostMapping(path = "createOperationLog")
    public ResponseBase createOperationLog(@RequestBody OperationLogDTO para) {
	return operationLogService.createOperationLog(para);
    }
}
