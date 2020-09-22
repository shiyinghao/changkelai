/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.icss.newretail.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * jwt token工具类
 * </p>
 * 
 * <pre>
 *     jwt的claim里一般包含以下几种数据:
 *         1. iss -- token的发行者
 *         2. sub -- 该JWT所面向的用户
 *         3. aud -- 接收该JWT的一方
 *         4. exp -- token的失效时间
 *         5. nbf -- 在此时间段之前,不会被处理
 *         6. iat -- jwt发布时间
 *         7. jti -- jwt唯一标识,防止重复使用
 * </pre>
 *
 * @author fengshuonan
 * @Date 2017/8/25 10:59
 */
public class JwtTokenUtil {

    /**
     * 当前用户Id
     * 
     * @return
     */
    public static String currUser() {
	return currUserInfo().get("userId");
    }

    /**
     * 当前租户Id
     * 
     * @return
     */
    public static String currTenant() {
	return currUserInfo().get("tenantId");
    }

    /**
     * 获取用户相关信息
     * 
     * @return
     */
    public static Map<String, String> currUserInfo() {
	Map<String, String> map = new HashMap<String, String>();
	String token = ContextUtils.getInvocationContext().getContext("token");
	String subject = getClaimFromToken(token).getSubject();
	if (StringUtils.isNotBlank(subject)) {
	    String[] temp = subject.split(",");
	    map.put("userId", temp[0]);
	    map.put("tenantId", temp[1]);
	} else {
	    throw new RuntimeException("获取登录信息失败");
	}
	return map;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
	return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token) {
	return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public static String getAudienceFromToken(String token) {
	return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public static String getPrivateClaimFromToken(String token, String key) {
	return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token) {
	return Jwts.parser().setSigningKey(JwtConstants.SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 解析token是否正确,不正确会报异常<br>
     */
    public static void parseToken(String token) throws JwtException {
	Jwts.parser().setSigningKey(JwtConstants.SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public static Boolean isTokenExpired(String token) {
	try {
	    final Date expiration = getExpirationDateFromToken(token);
	    return expiration.before(new Date());
	} catch (ExpiredJwtException expiredJwtException) {
	    return true;
	}
    }

    /**
     * 生成token(通过用户名和签名时候用的随机数)
     */
    public static String generateToken(String userId) {
	Map<String, Object> claims = new HashMap<>();
	return doGenerateToken(claims, userId);
    }

    /**
     * 生成token
     */
    private static String doGenerateToken(Map<String, Object> claims, String subject) {
	final Date createdDate = new Date();
	final Date expirationDate = new Date(createdDate.getTime() + JwtConstants.EXPIRATION * 1000);

	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
		.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET).compact();
    }

    /**
     * 获取混淆MD5签名用的随机字符串
     */
    /*
     * public static String getRandomKey() { return ToolUtil.getRandomString(6); }
     */
}