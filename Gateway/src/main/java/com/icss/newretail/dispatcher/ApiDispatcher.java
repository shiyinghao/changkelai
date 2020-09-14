package com.icss.newretail.dispatcher;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.edge.core.EdgeInvocation;

import java.util.Map;

/**
 * 网关路由-终端请求转发中心
 */
@Slf4j
public class ApiDispatcher extends AbstractEdgeDispatcher {
    @Override
    public int getOrder() {
        return 10002;
    }

    /*
     * 路由转发处理初始化
     */
    @Override
    public void init(Router router) {
        String regex = "/api/([^\\/]+)/(.*)"; // 转发规则regex
        router.routeWithRegex(regex).handler(CookieHandler.create());
        router.routeWithRegex(regex).handler(createBodyHandler());
        router.routeWithRegex(regex).failureHandler(this::onFailure).handler(this::onRequest);
        log.info("router-init success !}");
    }

    /**
     * 请求处理逻辑
     *
     * @param context
     */
    protected void onRequest(RoutingContext context) {
        EdgeInvocation invoker = new EdgeInvocation() {
            // 认证鉴权：构造Invocation的时候，设置会话信息。如果是认证请求，则添加Cookie。
            protected void createInvocation() {
                super.createInvocation();
                // 从cookie里面读取token，也从header里面读取，方便各种独立的测试工具联调
                String token = context.request().getHeader("token");
//                Map<String, Object> map = new HashMap<String, Object>();
//                HttpServletRequestEx requestEx = invocation.getRequestEx();
//                Enumeration<String> headerNames = requestEx.getHeaderNames();
//                while (headerNames.hasMoreElements()) {
//                    String key = (String) headerNames.nextElement();
//                    // 排除Cookie字段
//                    if (key.equalsIgnoreCase("Cookie")) {
//                        continue;
//                    }
//                    if (key.equalsIgnoreCase("X-Forwarded-For")) {
//                        String value = requestEx.getHeader(key);
//                        System.out.println("》》》》》》》" + key + "<<<<<<<" + value);
////                        String ip = context.request().getHeader("value");
//                        this.invocation.addContext("ip", value);
//                    }
//                }
                if (token != null) {
                    this.invocation.addContext("token", token);

                } else {
                    Cookie tokenCookie = context.getCookie("token");
                    if (tokenCookie != null) {
                        this.invocation.addContext("token", tokenCookie.getValue());
                    }
                }
            }
        };
        Map<String, String> pathParams = context.pathParams();
        String microserviceName = pathParams.get("param0");
        String path = "/" + pathParams.get("param1");
        log.info("invoker-init: microserviceName={} path={} body={}", microserviceName, path, context.getBodyAsString());
        invoker.init(microserviceName, context, path, httpServerFilters);
        invoker.edgeInvoke();
    }
}
