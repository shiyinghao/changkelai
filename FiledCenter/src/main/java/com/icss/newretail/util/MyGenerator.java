package com.icss.newretail.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author 
 * @since 2019-03-19
 */
public class MyGenerator {
    	// private static String[] tables = new String[]{"t_cc_valuables", "t_uc_shift_turnover", "t_uc_xsm_licence", "t_uc_user_info"};
    	// private static String[] tables = new String[]{"t_user_operator_log"};
	 private static String[] tables = new String[]{"t_user_wechat_info","t_user_wechat_org_card","t_user_wechat_template","t_user_wechat_user_auth", "t_user_camera_info", "t_user_camera_callback_log"};
	// private static String[] tablePrefixs = new String[]{"t_","t_goods_"};
	private static String[] tablePrefixs = new String[]{"t_user_", "t_goods_", "t_member_", "t_pay_","t_promotion_","t_trade_","t_deliver_", "t_"};
    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String outputDir = "d:/beans/hw";
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setAuthor("zhangzhijia");
        gc.setOpen(true);
        gc.setSwagger2(true);
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // dsc.setUrl("jdbc:mysql://114.115.250.159:3306/new_retail?zeroDateTimeBehavior=convertToNull&amp;rewriteBatchedStatements=true");
        // dsc.setUsername("root");
        // dsc.setPassword("MT@zhmd2019");
        dsc.setUrl("jdbc:mysql://114.115.250.159:3306/nr_user");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("MT@zhmd2019"); 
        autoGenerator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("user");
        pc.setParent("com.icss.newretail");
        pc.setMapper("dao");
        pc.setXml("dao");
        pc.setEntity("entity");
        autoGenerator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(tablePrefixs);
        strategy.setEntityLombokModel(false);
        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setSuperControllerClass("com.icss.ah.base.controller.BaseController");
        strategy.setInclude(tables);
        strategy.setEntityLombokModel(true);
        autoGenerator.setStrategy(strategy);
        
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

}
