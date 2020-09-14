package com.icss.newretail.handler;

import com.icss.newretail.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.apache.servicecomb.core.Handler;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.swagger.invocation.AsyncResponse;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统认证
 *
 * @author zhangzhijia
 * @date Apr 23, 2019
 */
public class AuthHandler implements Handler {

    @Override
    public void handle(Invocation invocation, AsyncResponse asyncResponse) throws Exception {
        String microserviceName = invocation.getMicroserviceName();
        String operationName = invocation.getOperationName();
        if (
                (microserviceName.equals("user-service")
                        && (
                        operationName.equals("login")
                                || operationName.equals("loginByPhoneregister")
                                || operationName.equals("checkToken")
                                || operationName.equals("applyShop")
                                || operationName.equals("getStoreByAuthCode")
                                || operationName.equals("querybindexpiredDay")
                                || operationName.equals("loginByPhone")
                                || operationName.equals("queryShopDetailById")
                                || operationName.equals("recommend")
                                || operationName.equals("queryStored")
                                || operationName.equals("getUms")
                                || operationName.equals("sendMsg")
                                || operationName.equals("footPrint")
                                || operationName.equals("register")
                                || operationName.equals("tokenCheck")

                                || operationName.equals("getWarzoneList")

                                || operationName.equals("getUmsDto")
                                || operationName.equals("getUserAndStoreByAuthCodeTel")
                                || operationName.equals("shopAuth")
                                || operationName.equals("system")
                                || operationName.equals("querySystemParam")
                                || operationName.equals("telLogin")
                                || operationName.equals("sendVerificationCode")
                                || operationName.equals("resetPassword")
                                || operationName.equals("loginIn")
                                || operationName.equals("getAppletById")

                )
                )
                        || (microserviceName.equals("file-service")
                        && (operationName.equals("loginPara")
                        || (operationName.equals("download")
                        || operationName.equals("fileUploadIcbc")
                        || operationName.equals("downloadZip")
                        || operationName.equals("getToken")
                        || operationName.equals("iptest")
                )
                )

                )
                        || (
                        invocation.getMicroserviceName().equals("goods-service")
                                && (invocation.getOperationName().equals("getGoodstypeList")
                                || invocation.getOperationName().equals("queryGoodsTag")
                                || invocation.getOperationName().equals("queryShopGoodsTagList")
                                || invocation.getOperationName().equals("queryShopGoodsTypeList")
                                || invocation.getOperationName().equals("queryShopGoodsInfoAll")
                                || invocation.getOperationName().equals("queryGoodsCustomizeConfig")
                                || invocation.getOperationName().equals("warzoneGoodsAutoCompletedOnById")
                                || invocation.getOperationName().equals("warzoneGoodsAutoCompletedOffById")
                        )
                )
                        || (microserviceName.equals("member-service") &&
                        (operationName.equals("loginmem")
                                || operationName.equals("loginMember")
                                || operationName.equals("removeBind")
                                || operationName.equals("removeWeakBind")
                                || operationName.equals("clearTotalMoney")
                                || operationName.equals("invoice")
                                || operationName.equals("proofAuditNotification")

                        )
                ) ||
                        (microserviceName.equals("promotion-service") &&
                                (operationName.equals("queryShopConfigurationInfor")
                                        || operationName.equals("queryPageConfigurationInfor")
                                        || operationName.equals("getIpAddress")
                                        || operationName.equals("qryShareInfo")
                                        || operationName.equals("qryPlanInfo")
                                        || operationName.equals("returnPacketTimeOut")
                                        || operationName.equals("bonuspointOrderAutoCompleted")
                                        || operationName.equals("getPromotionDeciduousIndexWindow")
                                )
                        ) ||
                        (microserviceName.equals("pay-service") &&
                                (operationName.equals("onlinePayAnswer")
                                        || operationName.equals("onlinePayTest")
                                        || operationName.equals("getToken")
                                )
                        )
                        ||
                        (microserviceName.equals("trade-service") &&
                                (operationName.equals("orderAutoCompleted")
                                        || operationName.equals("orderAutoCompletedById")
                                        || operationName.equals("orderAutoCanceled")
                                        || operationName.equals("orderAutoCanceledById")
                                        || operationName.equals("orderAutoPayThaw")
                                        || operationName.equals("groupSMSActivite")
                                )
                        )
                        ||
                        (microserviceName.equals("data-service") &&
                                (operationName.equals("heartbeat")
                                        || operationName.equals("deleteWain")
                                )
                        )
        ) {
            invocation.next(asyncResponse);
        } else {
            String token = invocation.getContext("token");
            if (token == null) {
                throw new InvocationException(403, "", "Token is not valid.");
            }
            // 验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = JwtTokenUtil.isTokenExpired(token);
                if (flag) {
                    throw new InvocationException(403, "", "Token is expired.");
                }
            } catch (JwtException e) {
                // 有异常就是token解析失败
                throw new InvocationException(403, "", "Token is error.");
            }
            invocation.next(asyncResponse);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequestEx requestEx = invocation.getRequestEx();
        Enumeration<String> headerNames = requestEx.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            // 排除Cookie字段
            if (key.equalsIgnoreCase("Cookie")) {
                continue;
            }
            if (key.equalsIgnoreCase("X-Forwarded-For")) {
                String value = requestEx.getHeader(key);
                invocation.addContext("ip",value);
                System.out.println("》》》》》》》" + key + "<<<<<<<" + value);

            }
        }
    }
}

