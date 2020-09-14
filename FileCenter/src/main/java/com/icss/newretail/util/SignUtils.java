package com.icss.newretail.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class SignUtils {

    public static String getSign(Map<String, Object> map) {
        Map<String, Object> map1 = removeMapEmptyValue(map);
        if (ToolUtil.isEmpty(map1)) {
            return "map1为空";
        }
        Map<String, Object> map2 = sortMapByKey(map1);
        if (ToolUtil.isEmpty(map2)) {
            return "map2为空";
        }
        String url = getUrlParamsByMap(map2);
        if (ToolUtil.isEmpty(url)) {
            return "url为空";
        }
        String result = toMD5(url);
        return result;
    }

    public static void main(String[] args) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("a","张三");
        map.put("b","12343421");
        map.put("c","张三我");
        map.put("d","");
        map.put("e",null);
        String  a  = getSign(map);
        System.out.println("===="+a);
//        String a = "Action=guanying.proof.submit&Address=农大南路100号&City=北京市&County=海淀区&InvoiceItemTypeID=2&InvoiceTypeID=3&MobileNumber=18311111111&OutsideCoreOrderID= 20200001&OutsideInvoiceID= 20200001&Province=北京&PushCallbackUrl=http://api.c0331.dev.guanyingyun.com/mall/v1/test&RealName=张三&SecretKey=8B72E5CB394E3CABB56DC8E04AC4DE7F";
//        String b = "Action=guanying.proof.submit&Address=%E5%86%9C%E5%A4%A7%E5%8D%97%E8%B7%AF100%E5%8F%B7&City=%E5%8C%97%E4%BA%AC%E5%B8%82&County=%E6%B5%B7%E6%B7%80%E5%8C%BA&InvoiceItemTypeID=2&InvoiceTypeID=3&MobileNumber=18311111111&OutsideCoreOrderID=+20200001&OutsideInvoiceID=+20200001&Province=%E5%8C%97%E4%BA%AC&PushCallbackUrl=http%3A%2F%2Fapi.c0331.dev.guanyingyun.com%2Fmall%2Fv1%2Ftest&RealName=%E5%BC%A0%E4%B8%89&SecretKey=8B72E5CB394E3CABB56DC8E04AC4DE7F";
//        System.out.println(">>>" + toMD5(a) + "<<" + toMD5(b));
    }

    /**
     * 去除map里的空参
     *
     * @param paramMap
     * @return
     */
    public static Map<String, Object> removeMapEmptyValue(Map<String, Object> paramMap) {
        Set<String> set = paramMap.keySet();
        Iterator<String> it = set.iterator();
        List<String> listKey = new ArrayList<String>();
        while (it.hasNext()) {
            String str = it.next();
            if (paramMap.get(str) == null || "".equals(paramMap.get(str))) {
                listKey.add(str);
            }
        }
        for (String key : listKey) {
            paramMap.remove(key);
        }
        return paramMap;
    }

    /**
     * 根据map的key进行字典升序排序
     *
     * @param map
     * @return map
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        Map<String, Object> treemap = new TreeMap<String, Object>(map);
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(treemap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return treemap;
    }

    /**
     * 将map转换成url参数
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue().toString());
                sb.append("&");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = sb.toString();
        String str = "";
        if (s.endsWith("&")) {
            s = s.concat("SecretKey=8B72E5CB394E3CABB56DC8E04AC4DE7F");
        }
        return s;
    }

    public static String toMD5(String plainText) {
        String result = "";
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

