package com.icss.newretail.file.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icss.newretail.file.dao.PersonalTemplateMapper;
import com.icss.newretail.file.entity.RequestLog;
import com.icss.newretail.model.*;
import com.icss.newretail.service.file.PersonalTemplateService;
import com.icss.newretail.util.JwtTokenUtil;
import com.icss.newretail.util.SignUtils;
import com.icss.newretail.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PersonalTemplateServiceImpl implements PersonalTemplateService {

    private static String SECRET_KEY = "8B72E5CB394E3CABB56DC8E04AC4DE7F";

    // 接口URL
//    private static String SERVICE_URL_DEV = "http://api.c0331.dev.guanyingyun.com/adapter/v1/custom"; // 开发
//    private static String SERVICE_URL_PRD = "http://api.c0331.zonea.guanyingyun.com/adapter/v1/custom"; // 生产
//    private static String SERVICE_URL = SERVICE_URL_DEV;
	@Value("${chuangyi.serviceUrl}")
	private String SERVICE_URL;
	
    // 创艺-获取模板分类接口
    private static String TYPE_ACTION = "guanying.customcategory.list.get";

    // 创艺-获取模板列表接口
    private static String TEMPLATE_ACTION = "guanying.customtemplate.list.get";

    // 创艺-获取模板详情接口
    private static String DETAIL_ACTION = "guanying.goodstemplate.detail.query";

    // 创艺-提交订制酒服务单
    private static String ADD_ORDER_ACTION = "guanying.adapter.agentorder.down";

    // 创艺-订单取消
    private static String CANCEL_ORDER_ACTION = "guanying.adapter.order.cancel";

    // 创艺-作品提交
    private static String SUBMIT_ORDER_ACTION = "guanying.works.submit";

    @Autowired
    private PersonalTemplateMapper personalTemplateMapper;

    /**
     * 获取token
     * @param
     * @return ResponseBaseToken
     */
    @Override
    public ResponseBaseToken getToken(GetTokenDTO tokenDTO) {
        ResponseBaseToken resp = new ResponseBaseToken();
        try {
            // 获取公钥
            String KEY = "CAE6A51E59AF3C468E679C561917A870";
            if (tokenDTO != null && ToolUtil.notEmpty(tokenDTO.getKey())) {
                if (KEY.equals(tokenDTO.getKey())) {
                    Map<String, Object> map = new HashMap<>();
                    // map.put("ACTION", "template." + tokenDTO.getAction());
                    map.put("KEY", tokenDTO.getKey());
                    String token= JwtTokenUtil.generateToken(map.toString());
                    if (ToolUtil.notEmpty(token)) {
                        resp.setCode(1);
                        resp.setMessage("获取token成功");
                        resp.setToken(token);
                    } else {
                        resp.setCode(0);
                        resp.setMessage("获取token异常,token生成异常");
                        return resp;
                    }
                } else {
                    resp.setCode(0);
                    resp.setMessage("获取token异常,秘钥不正确");
                    return resp;
                }
            } else {
                resp.setCode(0);
                resp.setMessage("获取token异常,未传递秘钥");
                return resp;
            }
        } catch (Exception e) {
            resp.setCode(0);
            resp.setMessage("获取token异常：" + e.getMessage());
            log.error("获取token异常---" + e.getMessage());
        }
        return resp;
    }

    @Override
    public String accesscy(AccesscyDTO param) {
        HashMap<String, String> actionMap = new HashMap<String, String>();
        actionMap.put("guanying.customcategory.list.get", "获取模板分类接口");
        actionMap.put("guanying.customtemplate.list.get", "获取模板列表接口");
        actionMap.put("guanying.goodstemplate.detail.query", "获取模板详情接口");
        actionMap.put("guanying.adapter.agentorder.down", "提交订制酒服务单接口");
        actionMap.put("guanying.adapter.order.cancel", "取消订制酒服务单接口");
        actionMap.put("guanying.works.submit", "作品提交接口");
        actionMap.put("guanying.proof.submit", "专票资质提交接口");
        actionMap.put("guanying.invoice.submit", "发票申请提交接口");

        // 请求
        LocalDateTime createTime = LocalDateTime.now();
        String result = HttpRequest.post(SERVICE_URL).header("Content-Type", "application/json").body(param.getJson()).execute().body();
        JSONObject jsonResult = JSONObject.parseObject(result);
        int res = 1; // 1成功、0失败
        if (!"Success".equals(jsonResult.getString("Status"))) {
            res = 0;
        }
        // 保存访问记录
        RequestLog log = new RequestLog();
        log.setUuid(UUID.randomUUID().toString());
        log.setServiceAction(param.getAction());
        log.setServiceName(actionMap.get(param.getAction()));
        log.setType(1);
        log.setResult(res);
        log.setInParam(param.getJson());
        log.setOutParam(result);
        log.setCreateTime(createTime);
        personalTemplateMapper.insertRequestLog(log);
        return result;
    }

    @Override
    public ResponseResult<PersonalTemplateTypeDTO> queryTemplateTypeList(String OutsideGoodsID) {
        ResponseResult<PersonalTemplateTypeDTO> base = new ResponseResult<PersonalTemplateTypeDTO>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Action", TYPE_ACTION);
            map.put("OutsideGoodsID", OutsideGoodsID);

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {
                JSONObject json = new JSONObject();
                json.put("Action", TYPE_ACTION);
                json.put("OutsideGoodsID", OutsideGoodsID);
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(TYPE_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    JSONObject data = jsonResult.getJSONObject("Data");
                    String GoodsImage = data.getString("GoodsImage");
                    List<PersonalTemplateTypeListDTO> CategoryList = JSONArray.toJavaObject(data.getJSONArray("CategoryList"), List.class);
                    PersonalTemplateTypeDTO typeDTO = new PersonalTemplateTypeDTO();
                    typeDTO.setGoodsImage(GoodsImage);
                    typeDTO.setCategoryList(CategoryList);
                    base.setResult(typeDTO);
                    base.setCode(1);
                    base.setMessage("模板分类获取成功");
                } else {
                    base.setCode(0);
                    base.setMessage("模板分类获取失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("模板分类获取出错：" + ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseRecords<PersonalTemplateListDTO> queryTemplateList(PageData<PersonalTemplateParamDTO> param) {
        ResponseRecords<PersonalTemplateListDTO> base = new ResponseRecords<PersonalTemplateListDTO>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Action", TEMPLATE_ACTION);
            map.put("OutsideGoodsID", param.getCondition().getGoodsID());
            map.put("TemplateCategoryID", param.getCondition().getCategoryID());
            map.put("PageID", param.getCurrent());
            map.put("Pagesize", param.getSize());

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {
                JSONObject json = new JSONObject();
                json.put("Action", TEMPLATE_ACTION);
                json.put("OutsideGoodsID", param.getCondition().getGoodsID());
                json.put("TemplateCategoryID", param.getCondition().getCategoryID());
                json.put("PageID", param.getCurrent());
                json.put("Pagesize", param.getSize());
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(TEMPLATE_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    List<PersonalTemplateListDTO> listDTO = JSONArray.toJavaObject(jsonResult.getJSONArray("Data"), List.class);
                    base.setRecords(listDTO);
                    base.setCode(1);
                    base.setMessage("模板获取成功");
                } else {
                    base.setCode(0);
                    base.setMessage("模板获取失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("模板获取出错：" + ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseResult<JSONObject> queryTemplateDetail(String GoodsTemplateID) {
        ResponseResult<JSONObject> base = new ResponseResult<JSONObject>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Action", DETAIL_ACTION);
            map.put("GoodsTemplateID", GoodsTemplateID);

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {
                JSONObject json = new JSONObject();
                json.put("Action", DETAIL_ACTION);
                json.put("GoodsTemplateID", GoodsTemplateID);
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(DETAIL_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                System.out.println("创艺-获取模板详情-结果：" + result);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    base.setResult(jsonResult.getJSONObject("Data"));
                    base.setCode(1);
                    base.setMessage("模板详情获取成功");
                } else {
                    base.setCode(0);
                    base.setMessage("模板详情获取失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("模板详情获取出错：" + ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseResult<JSONObject> addTemplateOrder(AddOrderDTO param) {
        ResponseResult<JSONObject> base = new ResponseResult<JSONObject>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Action", ADD_ORDER_ACTION);
            map.put("OutsideCoreOrderID", param.getOutsideCoreOrderID());
            map.put("StoreName", param.getStoreName());
            map.put("StoreCompanyName", param.getStoreCompanyName());
            map.put("RealName", param.getRealName());
            map.put("MobileNumber", param.getMobileNumber());
            map.put("Province", param.getProvince());
            map.put("City", param.getCity());
            map.put("County", param.getCounty());
            map.put("Address", param.getAddress());
            map.put("OrderAmount", param.getOrderAmount());
            map.put("OrderGoods", new JSONObject().toJSONString(param.getOrderGoods().get(0))); // 创艺按对象接收商品
            map.put("CallbackUrlList", new JSONObject().toJSONString(param.getCallbackUrlList()));
            System.out.println("创艺-下单-签名-参数：" + map.toString());

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {

                JSONObject json = new JSONObject();
                json.put("Action", ADD_ORDER_ACTION);
                json.put("OutsideCoreOrderID", param.getOutsideCoreOrderID());
                json.put("StoreName", param.getStoreName());
                json.put("StoreCompanyName", param.getStoreCompanyName());
                json.put("RealName", param.getRealName());
                json.put("MobileNumber", param.getMobileNumber());
                json.put("Province", param.getProvince());
                json.put("City", param.getCity());
                json.put("County", param.getCounty());
                json.put("Address", param.getAddress());
                json.put("OrderAmount", param.getOrderAmount());
                json.put("OrderGoods", new JSONObject().toJSONString(param.getOrderGoods().get(0))); // 创艺按对象接收商品
                json.put("CallbackUrlList", new JSONObject().toJSONString(param.getCallbackUrlList()));
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(ADD_ORDER_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                System.out.println("创艺-下单-结果：" + result);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    // AddOrderResultDTO resultDTO = JSONObject.toJavaObject(jsonResult.getJSONObject("Data"), AddOrderResultDTO.class);
                    // resultDTO = resultDTO == null ? new AddOrderResultDTO() : resultDTO;
                    JSONObject data1 = jsonResult.getJSONObject("Data");
                    base.setResult(data1);
                    base.setCode(1);
                    base.setMessage("订制服务单保存成功");
                } else {
                    base.setCode(0);
                    base.setMessage("订制服务单保存失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("订制服务单保存出错：" + ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseResult<JSONObject> cancelTemplateOrder(AddOrderDTO param) {
        ResponseResult<JSONObject> base = new ResponseResult<JSONObject>();
        try {
            LocalDateTime createTime = LocalDateTime.now();
            Map<String, Object> map = new HashMap<>();
            map.put("Action", CANCEL_ORDER_ACTION);
            map.put("OutsideCoreOrderID", param.getOutsideCoreOrderID());
            map.put("CancelTime", createTime);
            System.out.println("创艺-取消-签名-参数：" + map.toString());

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {
                JSONObject json = new JSONObject();
                json.put("Action", CANCEL_ORDER_ACTION);
                json.put("OutsideCoreOrderID", param.getOutsideCoreOrderID());
                json.put("CancelTime", createTime);
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(CANCEL_ORDER_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                System.out.println("创艺-取消-结果：" + result);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    // AddOrderResultDTO resultDTO = JSONObject.toJavaObject(jsonResult.getJSONObject("Data"), AddOrderResultDTO.class);
                    // resultDTO = resultDTO == null ? new AddOrderResultDTO() : resultDTO;
                    base.setCode(1);
                    base.setMessage("订制服务单取消成功");
                } else {
                    base.setCode(0);
                    base.setMessage("订制服务单取消失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("订制服务单取消出错：" + ex.getMessage());
        }
        return base;
    }

    @Override
    public ResponseResult<String> submitTemplateOrder(SubmitOrderDTO param) {
        ResponseResult<String> base = new ResponseResult<String>();
        try {
        	param.setFinalImagePath(param.getFinalImagePath().replace("%2F", "/"));
        	param.setShowImagePath(param.getShowImagePath().replace("%2F", "/"));
        	
            Map<String, Object> map = new HashMap<>();
            map.put("Action", SUBMIT_ORDER_ACTION);
            map.put("OutsideWorksID", param.getOutsideWorksID());
            map.put("GoodsTemplateID", param.getGoodsTemplateID());
//            map.put("OwnerTypeID", param.getOwnerTypeID());
//            map.put("OwnerName", param.getOwnerName());
//            map.put("LicenseNumber", param.getLicenseNumber());
//            map.put("LicensePath", param.getLicensePath());
//            map.put("IdNumber", param.getIdNumber());
//            map.put("IdFrontPath", param.getIdFrontPath());
//            map.put("IdBackPath", param.getIdBackPath());
            map.put("FinalImagePath", param.getFinalImagePath());
            map.put("ShowImagePath", param.getShowImagePath());
            map.put("WorksbackUrl", param.getWorksbackUrl());
            System.out.println("创艺-作品提交-获取签名-参数：" + map.toString());

            String sign = SignUtils.getSign(map);
            if (!ToolUtil.isEmpty(sign)) {
                JSONObject json = new JSONObject();
                json.put("Action", SUBMIT_ORDER_ACTION);
                json.put("OutsideWorksID", param.getOutsideWorksID());
                json.put("GoodsTemplateID", param.getGoodsTemplateID());
//                json.put("OwnerTypeID", param.getOwnerTypeID());
//                json.put("OwnerName", param.getOwnerName());
//                json.put("LicenseNumber", param.getLicenseNumber());
//                json.put("LicensePath", param.getLicensePath());
//                json.put("IdNumber", param.getIdNumber());
//                json.put("IdFrontPath", param.getIdFrontPath());
//                json.put("IdBackPath", param.getIdBackPath());
                json.put("FinalImagePath", param.getFinalImagePath());
                json.put("ShowImagePath", param.getShowImagePath());
                json.put("WorksbackUrl", param.getWorksbackUrl());
                json.put("SecretKey", SECRET_KEY);
                json.put("Sign", sign);

                AccesscyDTO accesscy = new AccesscyDTO();
                accesscy.setAction(SUBMIT_ORDER_ACTION);
                accesscy.setJson(json.toJSONString());
                String result = this.accesscy(accesscy);
                System.out.println("创艺-作品提交-结果：" + result);
                JSONObject jsonResult = JSONObject.parseObject(result);
                if ("Success".equals(jsonResult.getString("Status"))) {
                    JSONObject data = jsonResult.getJSONObject("Data");
                    String worksID = data.getString("WorksID");
                    base.setResult(worksID);
                    base.setCode(1);
                    base.setMessage("作品提交成功");
                } else {
                    base.setCode(0);
                    base.setMessage("作品提交失败：" + jsonResult.getString("ErrMsg"));
                }
            } else {
                base.setCode(0);
                base.setMessage("获取签名失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            base.setCode(0);
            base.setMessage("作品提交出错：" + ex.getMessage());
        }
        return base;
    }

}