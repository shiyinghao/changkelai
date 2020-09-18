package com.icss.newretail.util;

/**
 * ID生成器
 * Created by David on 2020/04/24.
 */
public class IdGenerator {
    private static Snowflake snowflake = null;

    static {
        snowflake = new Snowflake(2,5);
    }

    public static String TH = "TH";// 退货单前缀
    public static String RK = "RK";//入库单前缀
    public static String PD = "PD";//盘点单前缀
    public static String SY = "SY";//损溢单前缀

    /**
     * 生成退货单
     * @return 退货单号
     */
    public static String backStockId(){
        return nextId(TH);
    }


    /**
     * 生成损溢单号
     * @return 损溢单号
     */
    public static String ceId(){
        return nextId(SY);
    }

    /**
     * 生成盘点单
     * @return 盘点单号
     */
    public static String checkStockId(){
        return nextId(PD);
    }

    /**
     * 生成入库单
     * @return 入库单号
     */
    public static String enterStockId(){
        return nextId(RK);
    }




    /**
     * 生成编号 按前缀生成
     * @param prefix  前缀
     * @return 编号
     */
    public static String nextId(String prefix){
        return prefix + String.valueOf(snowflake.nextId());
    }

    public static void main(String [] args ){
        // 连续产生6个序号
        for (int i = 0; i < 5; i++) {
            System.out.println(String.valueOf( IdGenerator.ceId()));
        }
    }
}
