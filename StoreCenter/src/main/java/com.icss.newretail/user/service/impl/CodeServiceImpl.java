package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.CodeService;
import com.icss.newretail.user.dao.CodeChildMapper;
import com.icss.newretail.user.dao.CodeMapper;
import com.icss.newretail.user.entity.UserDict;
import com.icss.newretail.user.entity.UserDictValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class CodeServiceImpl implements CodeService
{

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private CodeChildMapper codeChildMapper;

    @Override
    public ResponseResultPage<UserDictValueDTO> querycodeByName(PageData<CodeChidMessage> para) {
        ResponseResultPage<UserDictValueDTO> result = new ResponseResultPage<>();
        try {
            // 设置分页信息
            Page<UserDictValueDTO> page = new Page<>(para.getCurrent(), para.getSize());
            page.setDesc(para.getDescs());
            page.setAsc(para.getAscs());
            // 获取分页数据
            Page<UserDictValueDTO> dtoPage = codeChildMapper.querycodeByName(page, para.getCondition());
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
        return  result;
    }

    @Override
    public ResponseBase saveCode(CodeMessage codeMessage) {
        UserDict userDict =new UserDict();
        userDict.setUuid(UUID.randomUUID().toString());
        userDict.setCode(codeMessage.getCode());
        userDict.setName(codeMessage.getName());
        userDict.setStatus(1);
        userDict.setCreateTime(LocalDateTime.now());
        int result = codeMapper.insert(userDict);
        if (result>0) {
            return new ResponseBase(1, "保存成功");
        } else {
            return new ResponseBase(0, "保存失败");
        }
    }

    @Override
    public ResponseBase updateCode(CodeMessage codeMessage) {
        UserDict userDict =new UserDict();
        userDict.setUuid(codeMessage.getUuid());
        userDict.setCode(codeMessage.getCode());
        userDict.setName(codeMessage.getName());
        userDict.setUpdateTime(LocalDateTime.now());
        int  result = codeMapper.updateById(userDict);
        if (result>0){
            return new ResponseBase(1, "修改成功");
        }else {
            return new ResponseBase(0, "修改失败");
        }
    }

    @Override
    public ResponseBase deleteCode(String uuid) {
        int  result = codeMapper.deleteCode(uuid);
        if (result>0){
            return new ResponseBase(1, "删除成功");
        }else {
            return new ResponseBase(0, "删除失败");
        }
    }

    @Override
    public ResponseResultPage<UserDictDTO> querycode(PageData<CodeMessage> para) {
        ResponseResultPage<UserDictDTO> result = new ResponseResultPage<>();
        try {
            // 设置分页信息
            Page<UserDictDTO> page = new Page<>(para.getCurrent(), para.getSize());
            page.setDesc(para.getDescs());
            page.setAsc(para.getAscs());
            // 获取分页数据
            Page<UserDictDTO> dtoPage = codeMapper.querycode(page, para.getCondition());
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
        return  result;
    }

    @Override
    public ResponseBase savecodeclid(CodeChidMessage codeChidMessage) {
        UserDictValue userDictValue =new UserDictValue();
        userDictValue.setUuid(UUID.randomUUID().toString());
        userDictValue.setCode(codeChidMessage.getCode());
        userDictValue.setValue(codeChidMessage.getValue());
        userDictValue.setCreateTime(LocalDateTime.now());
        userDictValue.setStatus(1);
        userDictValue.setDictId(codeChidMessage.getDictId());
        int result =codeChildMapper.insert(userDictValue);
        if (result>0) {
            return new ResponseBase(1, "保存成功");
        } else {
            return new ResponseBase(0, "保存失败");
        }
    }

    @Override
    public ResponseBase updateCodeChild(CodeChidMessage codeChidMessage) {
        UserDictValue userDictValue=new UserDictValue();
        userDictValue.setUuid(codeChidMessage.getUuid());
        userDictValue.setCode(codeChidMessage.getCode());
        userDictValue.setValue(codeChidMessage.getCode());
        userDictValue.setUpdateTime(LocalDateTime.now());
        int  result = codeChildMapper.updateById(userDictValue);
        if (result>0){
            return new ResponseBase(1, "修改成功");
        }else {
            return new ResponseBase(0, "修改失败");
        }
    }

    @Override
    public ResponseBase deleteCodeChild(String uuid) {
        int  result = codeChildMapper.deleteCodeChild(uuid);
        if (result>0){
            return new ResponseBase(1, "删除成功");
        }else {
            return new ResponseBase(0, "删除失败");
        }
    }
}
