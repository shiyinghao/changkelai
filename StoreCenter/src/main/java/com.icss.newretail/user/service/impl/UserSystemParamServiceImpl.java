package com.icss.newretail.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.PageData;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseRecords;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ResponseResultPage;
import com.icss.newretail.model.UserDictDTO;
import com.icss.newretail.model.UserSystemParamDTO;
import com.icss.newretail.model.UserSystemParamValueDTO;
import com.icss.newretail.service.user.UserSystemParamService;
import com.icss.newretail.user.dao.UserSystemParamMapper;
import com.icss.newretail.user.dao.UserSystemParamValueMapper;
import com.icss.newretail.user.entity.UserSystemParam;
import com.icss.newretail.user.entity.UserSystemParamValue;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.Object2ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 系统参数表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2020-03-26
 */
@Service
@Slf4j
public class UserSystemParamServiceImpl implements UserSystemParamService {

    @Autowired
    private UserSystemParamMapper userSystemParamMapper;

    @Autowired
    private UserSystemParamValueMapper userSystemParamValueMapper;

    @Override
    public ResponseRecords<UserSystemParamDTO> queryUserSystemParam(String name, String code) {
        ResponseRecords<UserSystemParamDTO> result = new ResponseRecords<UserSystemParamDTO>();
        String tenantId = JwtTokenUtil.currTenant();
        List<UserSystemParamDTO> userSystemParamDTOS = userSystemParamMapper.queryUserSystemParam(name, code);
        result.setMessage("查询到" + userSystemParamDTOS.size() + "条系统配置信息");
        result.setCode(1);
        if (userSystemParamDTOS.size() > 0) {
            result.setRecords(userSystemParamDTOS);
        } else {
            result.setRecords(new ArrayList<>());
        }
        return result;
    }

    @Override
    public ResponseBase createSystemParam(UserSystemParamDTO systemParam) {
        ResponseBase base = new ResponseBase();
        try {
            UserSystemParam userSystemParam = Object2ObjectUtil.parseObject(systemParam, UserSystemParam.class);

            userSystemParam.setUuid(UUID.randomUUID().toString());
            userSystemParam.setName(systemParam.getName());
            userSystemParam.setCode(systemParam.getCode());
            userSystemParam.setValue(systemParam.getValue());
            userSystemParam.setCreateUser(JwtTokenUtil.currUser());
            userSystemParam.setUpdateUser(JwtTokenUtil.currUser());
            userSystemParam.setCreateTime(LocalDateTime.now());
            userSystemParam.setUpdateTime(LocalDateTime.now());
            userSystemParam.setStatus(1);
            userSystemParamMapper.insert(userSystemParam);
            base.setMessage("添加成功");
            base.setCode(1);
        } catch (Exception ex) {
            base.setCode(0);
            log.error("PrivilegeServiceImpl|createSystemParam->添加系统配置:" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseBase modifySystemParam(UserSystemParamDTO systemParam) {
        ResponseBase base = new ResponseBase();
        try {
            systemParam.setUpdateTime(LocalDateTime.now());
            systemParam.setUpdateUser(JwtTokenUtil.currUser());
            UserSystemParam userSystemParam = Object2ObjectUtil.parseObject(systemParam, UserSystemParam.class);
            Integer i = userSystemParamMapper.updateById(userSystemParam);
            if (i > 0) {
                base.setCode(1);
                base.setMessage("系统配置修改成功");
            } else {
                base.setMessage("系统配置修改失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("PrivilegeServiceImpl|modifySystemParam->修改系统配置" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseBase deleteSystemParam(String uuid) {
        ResponseBase base = new ResponseBase();
        UserSystemParam uspv = new UserSystemParam();
        uspv.setUuid(uuid).setStatus(2);
        try {
            int i = userSystemParamMapper.updateById(uspv);
            if (i > 0) {
                base.setCode(1);
                base.setMessage("系统配置删除成功");
            } else {
                base.setMessage("系统配置删除失败");
            }
        } catch (Exception ex) {
            log.info("PrivilegeServiceImpl|deleteSystemParam->删除系统配置:" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseRecords<UserSystemParamValueDTO> queryUserSystemParamValue(String paramId) {
        ResponseRecords<UserSystemParamValueDTO> result = new ResponseRecords<UserSystemParamValueDTO>();
        List<UserSystemParamValueDTO> userSystemParamValueDTOS = new ArrayList<>();
        List<UserSystemParamValue> userSystemParamValueS = userSystemParamValueMapper.selectList(
                new QueryWrapper<UserSystemParamValue>().eq("param_id", paramId).eq("status", 1));

        userSystemParamValueDTOS = copy(userSystemParamValueS, UserSystemParamValueDTO.class);

        result.setMessage("查询到" + userSystemParamValueDTOS.size() + "条系统配置信息");
        result.setCode(1);
        if (userSystemParamValueDTOS.size() > 0) {
            result.setRecords(userSystemParamValueDTOS);
        } else {
            result.setRecords(new ArrayList<>());
        }
        return result;
    }

    @Override
    public ResponseBase deleteSystemParamValue(String uuid) {
        ResponseBase base = new ResponseBase();
        UserSystemParamValue uspv = new UserSystemParamValue();
        uspv.setUuid(uuid).setStatus(0);
        try {
            int i = userSystemParamValueMapper.updateById(uspv);
            if (i > 0) {
                base.setCode(1);
                base.setMessage("系统配置删除成功");
            } else {
                base.setMessage("系统配置删除失败");
            }
        } catch (Exception ex) {
            log.info("PrivilegeServiceImpl|deleteSystemParamValue->删除系统配置:" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseBase modifySystemParamValue(UserSystemParamValueDTO systemParamValue) {
        ResponseBase base = new ResponseBase();
        try {
            systemParamValue.setUpdateTime(new Date());
            systemParamValue.setUpdateUser(JwtTokenUtil.currUser());
            UserSystemParamValue userSystemParam = Object2ObjectUtil.parseObject(systemParamValue, UserSystemParamValue.class);
            Integer i = userSystemParamValueMapper.updateById(userSystemParam);
            if (i > 0) {
                base.setCode(1);
                base.setMessage("系统配置修改成功");
            } else {
                base.setMessage("系统配置修改失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("PrivilegeServiceImpl|modifySystemParamValue->修改系统配置" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseBase createSystemParamValue(UserSystemParamValueDTO systemParamValue) {
        ResponseBase base = new ResponseBase();
        try {
            UserSystemParamValue userSystemParam = Object2ObjectUtil.parseObject(systemParamValue, UserSystemParamValue.class);

            userSystemParam.setUuid(UUID.randomUUID().toString());
            userSystemParam.setName(systemParamValue.getName());
            userSystemParam.setCode(systemParamValue.getCode());
            userSystemParam.setParamId(systemParamValue.getParamId());
            userSystemParam.setCreateUser(JwtTokenUtil.currUser());
            userSystemParam.setUpdateUser(JwtTokenUtil.currUser());
            userSystemParam.setCreateTime(LocalDateTime.now());
            userSystemParam.setUpdateTime(LocalDateTime.now());
            userSystemParam.setStatus(1);
            userSystemParamValueMapper.insert(userSystemParam);
            base.setMessage("添加成功");
            base.setCode(1);
        } catch (Exception ex) {
            base.setCode(0);
            log.error("PrivilegeServiceImpl|createSystemParamValue->添加系统配置:" + ex.getLocalizedMessage());
            base.setMessage(ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseResultPage<UserSystemParamDTO> querySystemParam(PageData<UserSystemParamDTO> para) {
        ResponseResultPage<UserSystemParamDTO> result = new ResponseResultPage<>();
        try {
            // 设置分页信息
            Page<UserDictDTO> page = new Page<>(para.getCurrent(), para.getSize());
            page.setDesc(para.getDescs());
            page.setAsc(para.getAscs());
            // 获取分页数据
            Page<UserSystemParamDTO> dtoPage = userSystemParamMapper.querySystemParam(page, para.getCondition());
            result.setCode(1);
            result.setSize(dtoPage.getSize());
            result.setCurrent(dtoPage.getCurrent());
            result.setTotal(dtoPage.getTotal());
            result.setRecords(dtoPage.getRecords());
            result.setMessage("查询到" + dtoPage.getRecords().size() + "条字典编码记录列表");
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setMessage(ex.getMessage());
            result.setCode(0);
            log.error("字典编码分页查询异常：" + ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult<UserSystemParamDTO> querybindexpiredDay(String code) {

        ResponseResult<UserSystemParamDTO> responseResult = new ResponseResult<>();
        UserSystemParam userSystemParam = null;
        userSystemParam = userSystemParamMapper.selectOne(new QueryWrapper<UserSystemParam>().eq("code",code));
//        System.out.println("userSystemParam========>"+userSystemParam);
        if (userSystemParam != null) {

            responseResult.setCode(1);
            responseResult.setMessage("查询系统参数成功!");
            UserSystemParamDTO userSystemParamDTO = Object2ObjectUtil.parseObject(userSystemParam, UserSystemParamDTO.class);
            responseResult.setResult(userSystemParamDTO);

        } else {
            responseResult.setCode(1);
            responseResult.setMessage("查询系统参数为空!");
        }
        return responseResult;
    }


    public static <T> List<T> copy(List<?> list, Class<T> clazz) {
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

}
