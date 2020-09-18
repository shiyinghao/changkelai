package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.WarningRequest;
import com.icss.newretail.service.user.UserHeartbeatService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class HeartbeatServiceImpl implements UserHeartbeatService {

    @Override
    public WarningRequest heartbeat() {
        WarningRequest request = new WarningRequest();
        Runtime run = Runtime.getRuntime();
        //已分配内存
        long total = run.totalMemory() / 1024 / 1024;
        //已分配内存中的剩余空间
        long free = run.freeMemory() / 1024 / 1024;
        //最大内存
        long max = run.maxMemory() / 1024 / 1024;
        //最大可用内存
        long usable = max - total + free;
        request.setTotal(total);
        request.setFree(free);
        request.setMax(max);
        request.setUsable(usable);
        return request;
    }
}
