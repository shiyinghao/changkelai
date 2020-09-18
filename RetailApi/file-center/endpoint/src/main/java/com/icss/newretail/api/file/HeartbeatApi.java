package com.icss.newretail.api.file;

import com.icss.newretail.model.WarningRequest;
import com.icss.newretail.service.file.FileHeartbeatService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "heartbeat")
@RequestMapping(path = "/v1/heartbeat")
public class HeartbeatApi {

    @Autowired
    FileHeartbeatService heartbeatService;

    @GetMapping("heartbeat")
    public WarningRequest heartbeat() {
        return heartbeatService.heartbeat();
    }
}
