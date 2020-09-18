package com.icss.newretail.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.util.StringUtils;

/**
 * 快速打jar包
 */
public class BuilderJarUtil {
  //打包命令
  private static String packageCmd = "cmd /c mvn clean package -Pprod -Dmaven.test.skip=true";
  //本地的工作空间
  private static String workPath = "D:\\workspace\\maven-hw\\";
  private static String copyJarPath = "D:\\jar\\";
  public static void main(String[] args) {
//    buildJar("Gateway", "gateway-0.0.1-SNAPSHOT.jar");
//    buildJar("DeliveryCenter", "delivery-center-0.0.1-SNAPSHOT.jar");
    buildJar("MemberCenter", "member-center-0.0.1-SNAPSHOT.jar");
//    buildJar("PayCenter", "pay-center-0.0.1-SNAPSHOT.jar");
//    buildJar("PromotionCenter", "promotion-center-0.0.1-SNAPSHOT.jar");
//    buildJar("TradeCenter", "trade-center-0.0.1-SNAPSHOT.jar");
//    buildJar("UserCenter", "user-center-0.0.1-SNAPSHOT.jar");
//    buildJar("FileCenter", "file-center-0.0.1-SNAPSHOT.jar");
//    buildJar("GoodsCenter", "goods-center-0.0.1-SNAPSHOT.jar");

//先打jar包，然后用crt工具上传到/jar目录，再运行下面的命令
//nohup java -jar gateway-0.0.1-SNAPSHOT.jar &
//nohup java -jar delivery-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar member-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar pay-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar promotion-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar trade-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar user-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar file-center-0.0.1-SNAPSHOT.jar &
//nohup java -jar goods-center-0.0.1-SNAPSHOT.jar &
  }
  /**
   * 执行cmd命令
   */
  private static void buildJar(String packageName, String jarName) {
    //调用mvn命令打jar包
    process(new File(workPath + packageName), packageCmd);
    //统一复制jar包到一个目录，方便使用
    File filePath = new File(copyJarPath);
    if(filePath != null && !filePath.exists()){
      filePath.mkdirs();
    }
    String copyCmd = String.format("cmd /c copy /y %s%s\\target\\%s %s", workPath, packageName, jarName, copyJarPath);
    process(null, copyCmd);
  }
  /**
   * 执行cmd命令
   */
  private static String process(File directory, String... cmds) {
    // 单条命令的情况
    if (1 == cmds.length) {
      final String cmd = cmds[0];
      if (StringUtils.isEmpty(cmd)) {
        throw new NullPointerException("Command is empty !");
      }
      cmds = cmd.split(" ");
    }
    //用本命令名和命令的参数选项构造ProcessBuilder对象
    ProcessBuilder pb = new ProcessBuilder(cmds);
    pb.directory(directory);
    String result = "";
    Process process = null;
    try {
      // 启动一个进程,返回一个Process对象
      process = pb.start();
      // 获得Process对象的标准输出流
      InputStream is = process.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
      String line = null;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        result += line;
      }
      //挂起线程直至线程执行结束
      int exeResult = process.waitFor();
      if (exeResult != 0) {
        if (result == null || "".equals(result)) {
          InputStream isError = process.getErrorStream();
          BufferedReader brError = new BufferedReader(new InputStreamReader(isError, "GBK"));
          String lineError = null;
          while ((lineError = brError.readLine()) != null) {
            result += lineError;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (process != null) {
        process.destroy();
      }
    }
    return result;
  }
}
