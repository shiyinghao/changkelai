package com.icss.newretail.user.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icss.newretail.model.*;
import com.icss.newretail.service.user.StoreDownloadCountService;
import com.icss.newretail.user.dao.StoreDownloadCountMapper;
import com.icss.newretail.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * @author : yanghu
 * @date : Created in 2020/7/7 21:01
 * @description : 店铺下载排名查询实现类
 * @modified By :
 * @version: : 1.0.0
 */

@Service
public class StoreDownloadCountServiceImpl implements StoreDownloadCountService {

    @Autowired
    StoreDownloadCountMapper storeDownloadCountMapper;

    @Override
    public ResponseResultPage<WarZoneDownloadCountDTO> getWarZoneCount(PageData<DownloadCountReq> pageData) {
        Page<WarZoneDownloadCountDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        if (ToolUtil.isEmpty(pageData.getCondition().getSort())) {
            pageData.getCondition().setSort(1);
        }
        Page<WarZoneDownloadCountDTO> warZoneCount = storeDownloadCountMapper.getWarZoneCount(page, pageData.getCondition());
        ResponseResultPage<WarZoneDownloadCountDTO> result = new ResponseResultPage<>();
        result.setRecords(warZoneCount.getRecords());
        result.setCode(1);
        result.setSize(pageData.getSize());
        result.setCurrent(pageData.getCurrent());
        result.setTotal(warZoneCount.getTotal());
        result.setMessage("查询到" + warZoneCount.getRecords().size() + "条信息");
        return result;
    }

    @Override
    public ResponseResultPage<WarZoneDownloadCountDTO> getBase(PageData<DownloadCountReq> pageData) {
        Page<WarZoneDownloadCountDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        if (ToolUtil.isEmpty(pageData.getCondition().getSort())) {
            pageData.getCondition().setSort(1);
        }
        Page<WarZoneDownloadCountDTO> warZoneCount = storeDownloadCountMapper.getBase(page, pageData.getCondition());
        ResponseResultPage<WarZoneDownloadCountDTO> result = new ResponseResultPage<>();
        result.setRecords(warZoneCount.getRecords());
        result.setCode(1);
        result.setSize(pageData.getSize());
        result.setCurrent(pageData.getCurrent());
        result.setTotal(warZoneCount.getTotal());
        result.setMessage("查询到" + warZoneCount.getRecords().size() + "条信息");
        return result;
    }

    @Override
    public ResponseResultPage<WarZoneDownloadCountDTO> getStore(PageData<DownloadCountReq> pageData) {
        Page<WarZoneDownloadCountDTO> page = new Page<>(pageData.getCurrent(), pageData.getSize());
        if (ToolUtil.isEmpty(pageData.getCondition().getSort())) {
            pageData.getCondition().setSort(1);
        }
        Page<WarZoneDownloadCountDTO> warZoneCount = storeDownloadCountMapper.getStore(page, pageData.getCondition());
        ResponseResultPage<WarZoneDownloadCountDTO> result = new ResponseResultPage<>();
        result.setRecords(warZoneCount.getRecords());
        result.setCode(1);
        result.setSize(pageData.getSize());
        result.setCurrent(pageData.getCurrent());
        result.setTotal(warZoneCount.getTotal());
        result.setMessage("查询到" + warZoneCount.getRecords().size() + "条信息");
        return result;
    }

    @Override
    public ResponseEntity<byte[]> exportWarZoneDate(DownloadCountReq req) {
        String[] codeNames = {"grade", "upOrgZone", "activityCardDownloadCount", "storeCardDownloadCount", "goodsCardDownloadCount", "sumCount", "baseName", "authCode", "orgSeq", "storeName", "uuid"};
        String[] strName = {"排名", "战区", "活动名片下载次数", "商品名片下载次数", "店铺名片下载次数", "总下载次数", "", "", "", "", "", ""};
        String fileName = "warZoneRank" + System.currentTimeMillis() + ".xlsx";
        return poiExportDate(storeDownloadCountMapper.getWarZoneCount(req), codeNames, strName, fileName);
    }

    @Override
    public ResponseEntity<byte[]> exportBase(DownloadCountReq req) {
        String[] codeNames = {"grade", "upOrgZone", "baseName", "activityCardDownloadCount", "storeCardDownloadCount", "goodsCardDownloadCount", "sumCount", "authCode", "orgSeq", "storeName", "uuid"};
        String[] strName = {"排名", "战区", "基地", "活动名片下载次数", "商品名片下载次数", "店铺名片下载次数", "总下载次数", "", "", "", "", ""};
        String fileName = "baseRank" + System.currentTimeMillis() + ".xlsx";
        return poiExportDate(storeDownloadCountMapper.getBase(req), codeNames, strName, fileName);
    }

    @Override
    public ResponseEntity<byte[]> exportStore(DownloadCountReq req) {
        String[] codeNames = {"grade", "upOrgZone", "baseName", "authCode", "storeName", "activityCardDownloadCount", "storeCardDownloadCount", "goodsCardDownloadCount", "sumCount", "orgSeq", "uuid"};
        String[] strName = {"排名", "战区", "基地", "店铺授权码", "店铺名称", "活动名片下载次数", "商品名片下载次数", "店铺名片下载次数", "总下载次数", "店铺组织编码", "UUID"};
        String fileName = "storeRank" + System.currentTimeMillis() + ".xlsx";
        return poiExportDate(storeDownloadCountMapper.getStore(req), codeNames, strName, fileName);
    }

    /**
     * type    1活动名片   2商品名片    3店铺名片
     *
     * @param orgSeq
     * @param type   type    1活动名片   2商品名片    3店铺名片   注意这里与数据库注释反了  但是不影响结果
     * @param cardId
     * @return
     */
    @Override
    public ResponseBase addCardAmount(String orgSeq, String type, String cardId) {
        ResponseBase responseBase = new ResponseBase();
        responseBase.setCode(1).setMessage("更新成功");
        try {
            //更新之前  我需要查询  是否存在这条记录
            Integer count = storeDownloadCountMapper.queryOrgSeq(orgSeq);
            if (count == 0) {
                //初始化这个店铺的信息
                WarZoneDownloadCountDTO warZoneDownloadCountDTO = storeDownloadCountMapper.queryStoreCardByOrgSeq(orgSeq);
                warZoneDownloadCountDTO.setActivityCardDownloadCount(0);
                warZoneDownloadCountDTO.setGoodsCardDownloadCount(0);
                warZoneDownloadCountDTO.setStoreCardDownloadCount(0);
                warZoneDownloadCountDTO.setSumCount(0);
                Integer i = storeDownloadCountMapper.insertOne(warZoneDownloadCountDTO);
            }
            //  如果类型是活动  看是否是最新活动  若是最新活动   需要
            Integer i = 0;
            if (type != null && "1".equals(type)) {
                String uuid = storeDownloadCountMapper.queryNewActivity();
                if (uuid != null && cardId != null && uuid.equals(cardId)) {
                    i = storeDownloadCountMapper.addCardAmountAddCardId(orgSeq);
                } else {
                    i = storeDownloadCountMapper.addCardAmount(orgSeq, type);
                }
            } else {
                i = storeDownloadCountMapper.addCardAmount(orgSeq, type);
            }
            if (i > 0) {
                return responseBase;
            } else {
                responseBase.setCode(0).setMessage("更新失败");
                return responseBase;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseBase.setCode(0).setMessage("更新失败");
            return responseBase;
        }

    }

    private ResponseEntity<byte[]> poiExportDate(List<WarZoneDownloadCountDTO> list, String[] codeNames, String[] strName, String fileName) {
        ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        //指定以流的形式下载文件
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //指定文件名
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = ExcelUtil.getBigWriter();
        // 定义单元格
        writer.setColumnWidth(-1, 25);
        for (int i = 0; i < codeNames.length; i++) {
            writer.addHeaderAlias(codeNames[i], strName[i]);
        }
        writer.renameSheet("StoreReviewProfile");
        // 一次性写出内容，使用默认样式
        writer.write(list, true);
        writer.flush(byteOutPutStream);
        writer.close();
        return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);

    }


}
