package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.OperationLogDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.service.user.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Override
    public List<OperationLogDTO> queryOperationLogByDeviceAndOpeType(String deviceId, String operationType) {
	return null;
    }

    @Override
    public ResponseBase createOperationLog(OperationLogDTO para) {
	return null;
    }

}
