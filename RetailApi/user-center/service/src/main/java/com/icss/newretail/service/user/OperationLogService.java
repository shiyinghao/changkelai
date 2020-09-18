package com.icss.newretail.service.user;

import com.icss.newretail.model.OperationLogDTO;
import com.icss.newretail.model.ResponseBase;

import java.util.List;

public interface OperationLogService {
    public List<OperationLogDTO> queryOperationLogByDeviceAndOpeType(String deviceId, String operationType);

    /**
     * 保存用户操作记录
     * 
     * @param para
     * @return
     */
    public ResponseBase createOperationLog(OperationLogDTO para);
}
