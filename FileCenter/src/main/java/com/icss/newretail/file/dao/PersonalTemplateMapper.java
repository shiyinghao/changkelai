package com.icss.newretail.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icss.newretail.file.entity.RequestLog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public interface PersonalTemplateMapper extends BaseMapper<RequestLog> {

    int insertRequestLog(RequestLog log);

}
