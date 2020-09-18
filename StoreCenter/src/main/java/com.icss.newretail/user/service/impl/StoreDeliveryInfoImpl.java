package com.icss.newretail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreDeliveryInfoService;
import com.icss.newretail.user.dao.OrganizationMapper;
import com.icss.newretail.user.dao.StoreDeliveryInfoMapper;
import com.icss.newretail.user.entity.StoreDeliveryInfo;
import com.icss.newretail.user.entity.UserOrganization;
import com.icss.newretail.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StoreDeliveryInfoImpl implements StoreDeliveryInfoService {

    @Autowired
    private StoreDeliveryInfoMapper storeDeliveryInfoMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    /**
     * 新增   店铺id必须要传
     *
     * @param storeDeliveryInfoDTO
     * @return
     */
    @Override
    public ResponseBase addStoreDeliveryInfo(StoreDeliveryInfoDTO storeDeliveryInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(0).setMessage("新增失败");
        if (storeDeliveryInfoDTO.checkArgs()) {
            responseBase.setMessage("店铺id不能为空");
            return responseBase;
        }
        try {
            //判断当前组织是否为店铺 组织类型 是否为4 门店   门店信息配置表
            QueryWrapper<UserOrganization> qw1 = new QueryWrapper();
            qw1.eq( "org_seq", storeDeliveryInfoDTO.getOrgSeq());
            qw1.eq( "org_type", 4);
            UserOrganization userOrganization = organizationMapper.selectOne(qw1);
            if(userOrganization!=null){
                responseBase.setMessage("当前用户组织不是店铺,无法新增店铺配送信息！");
            }
            //判断当前店铺是否存在
            QueryWrapper<StoreDeliveryInfo> qw = new QueryWrapper();
            qw.eq("org_seq", storeDeliveryInfoDTO.getOrgSeq());
            Integer integer = storeDeliveryInfoMapper.selectCount(qw);
            if (integer > 0) {
                responseBase.setMessage("无法新增,已存在配置记录");
            } else {
                StoreDeliveryInfo storeDeliveryInfo = new StoreDeliveryInfo();
                BeanUtils.copyProperties(storeDeliveryInfoDTO, storeDeliveryInfo);
                storeDeliveryInfo.setUuid(UUID.randomUUID().toString());
                storeDeliveryInfo.setCreateTime(LocalDateTime.now());
                storeDeliveryInfo.setCreateUser(JwtTokenUtil.currUser());
                storeDeliveryInfoMapper.insert(storeDeliveryInfo);
                responseBase.setCode(1).setMessage("新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBase.setMessage("新增失败");
        }
        return responseBase;
    }

    /**
     * 根据uuid- 更新
     *
     * @param storeDeliveryInfoDTO
     * @return
     */
    @Override
    public ResponseBase updateStoreDeliveryInfo(StoreDeliveryInfoDTO storeDeliveryInfoDTO) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(0);
        if (storeDeliveryInfoDTO.checkUpdate()) {
            responseBase.setMessage("主键uuid和店铺id不能为空");
            return responseBase;
        }
        try {
            StoreDeliveryInfo storeDeliveryInfo = new StoreDeliveryInfo();
            BeanUtils.copyProperties(storeDeliveryInfoDTO, storeDeliveryInfo);
            storeDeliveryInfo.setUpdateTime(LocalDateTime.now());
            storeDeliveryInfo.setUpdateUser(JwtTokenUtil.currUser());
            storeDeliveryInfoMapper.updateById(storeDeliveryInfo);
            responseBase.setCode(1).setMessage("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBase.setMessage("更新失败");
        }
        return responseBase;
    }

    /**
     * 根据uuid删除记录
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseBase deleteStoreDeliveryInfo(String orgSeq) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(0);
        if (orgSeq == null || orgSeq.equals("")) {
            responseBase.setMessage("uuid不能为空");
        }
        try {
            QueryWrapper<StoreDeliveryInfo> qw = new QueryWrapper<>();
            qw.eq("org_seq", orgSeq);
            storeDeliveryInfoMapper.delete(qw);
            responseBase.setCode(1).setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseBase.setMessage("删除失败");
        }
        return responseBase;
    }

    /**
     * 单个查询
     *
     * @param orgSeq
     * @return
     */
    @Override
    public ResponseResult<StoreDeliveryInfoDTO> queryStoreDeliveryInfoOne(String orgSeq) {
        ResponseResult responseBase = new ResponseResult();
        responseBase.setCode(0).setMessage("查询失败");
        if (orgSeq == null || orgSeq.equals("")) {
            responseBase.setMessage("店铺id不能为空");
        }
        try {
            QueryWrapper<StoreDeliveryInfo> qw = new QueryWrapper<>();
            qw.eq("org_seq", orgSeq);
            StoreDeliveryInfo storeDeliveryInfo = storeDeliveryInfoMapper.selectOne(qw);
            responseBase.setMessage("查询成功");
            responseBase.setResult(storeDeliveryInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBase;
    }

    //分页查询

    @Override
    public ResponseResultPage<StoreDeliveryInfoDTO> queryStoreDeliveryInfo(PageData<StoreDeliveryInfoDTO> page) {
        ResponseResultPage responseResultPage = new ResponseResultPage();
        responseResultPage.setCode(0).setMessage("查询失败");
        try {
            Page<StoreDeliveryInfo> pag = new Page<>();
            pag.setCurrent(page.getSize()).setCurrent(page.getCurrent()).setAsc(page.getAscs()).setDesc(page.getDescs());
            IPage<StoreDeliveryInfo> storeDeliveryInfoIPage = storeDeliveryInfoMapper.selectPage(pag, null);
            responseResultPage.setRecords(storeDeliveryInfoIPage.getRecords());
        }catch (Exception e){
            e.printStackTrace();
            responseResultPage.setCode(1).setMessage("查询成功");
        }
        return responseResultPage;
    }
}
