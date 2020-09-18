package com.icss.newretail.util;

public class MathUtil {

	/** 
     * 计算两个经纬度坐标之间的距离 
     * @param lng1: 第1个坐标的经度 
     * @param lat1: 第1个坐标的纬度 
     * @param lng2: 第2个坐标的经度 
     * @param lat2: 第2个坐标的纬度 
     * @return 
     */  
    public static double getDistance_ny(double lng1,double lat1,double lng2,double lat2){  
        return (111120 * 1 / 0.017453292) * Math.acos((Math.sin(lat1*0.017453292) * Math.sin(lat2*0.017453292)) + ((Math.cos(lat1*0.017453292) * Math.cos(lat2*0.017453292)) * Math.cos(lng2*0.017453292 - lng1*0.017453292)));  
    }
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
    	double distance;
    	try {
    		if (lon1 == 0 || lat1 == 0 || lon2 == 0 || lat2 == 0) {
    			distance = -1;
			}else{
				double DEF_PI = 3.14159265359; // PI
				double DEF_2PI = 6.28318530712; // 2*PI
				double DEF_PI180 = 0.01745329252; // PI/180.0
				double DEF_R = 6370693.5; // radius of earth
				double ew1, ns1, ew2, ns2;
				double dx, dy, dew;
				// 角度转换为弧度
				ew1 = lon1 * DEF_PI180;
				ns1 = lat1 * DEF_PI180;
				ew2 = lon2 * DEF_PI180;
				ns2 = lat2 * DEF_PI180;
				// 经度差
				dew = ew1 - ew2;
				// 若跨东经和西经180 度，进行调整
				if (dew > DEF_PI)
					dew = DEF_2PI - dew;
				else if (dew < -DEF_PI)
					dew = DEF_2PI + dew;
				dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
				dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
				// 勾股定理求斜边长
				distance = Math.sqrt(dx * dx + dy * dy);
			}
    	} catch (Exception e) {
			e.printStackTrace();
			distance = -1;
		}
		return distance;
	}

}
