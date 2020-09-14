package com.icss.newretail.util;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 * 华为云文件存储
 *
 * @author zhangzhijia
 * @date Jun 13, 2019
 */
@Slf4j
public class OBSUtil {

	@Value("${obs.endPoint}")
	private static String endPoint;// 存储点

	@Value("${obs.bucketName}")
	private static String bucketName;// 文件桶

	@Value("${servicecomb.credentials.accessKey}")
	private static String ak;// accessKey

	@Value("${servicecomb.credentials.secretKey}")
	private static String sk;// secretKey

	private static ObsConfiguration config;

	// 私有化构造函数
	private OBSUtil() {

	}

	// 初始化配置信息
	static {
		config = new ObsConfiguration();
		config.setSocketTimeout(30000);
		config.setConnectionTimeout(10000);
		config.setEndPoint(endPoint);
	}

	/**
	 * 上传文件
	 *
	 * @param objectKey
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ObsException
	 */
	public static String putObject(String objectKey, File file) throws IOException, ObsException {
		ObsClient obsClient = null;
		try {
			obsClient = new ObsClient(ak, sk, config);
			// 判断文件桶是不是存在，不存在则创建
			if (!obsClient.headBucket(bucketName)) {
				obsClient.createBucket(bucketName);
			}
			PutObjectResult putObjectResult = obsClient.putObject(bucketName, getUploadDir() + objectKey, file);
			return putObjectResult.getObjectUrl();
		} catch (ObsException e) {
			log.error(e.getMessage());
			throw new ObsException(e.getErrorMessage());
		} finally {
			if (obsClient != null) {
				try {
					obsClient.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new IOException(e.getMessage());
				}
			}
		}
	}

	/**
	 * 上传文件
	 *
	 * @param objectKey
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ObsException
	 */
	public static String putObject(String objectKey, InputStream is) throws IOException, ObsException {
		ObsClient obsClient = null;
		try {
			obsClient = new ObsClient(ak, sk, config);
			PutObjectResult putObjectResult = obsClient.putObject(bucketName, getUploadDir() + objectKey, is);
			return putObjectResult.getObjectUrl();
		} catch (ObsException e) {
			log.error(e.getMessage());
			throw new ObsException(e.getErrorMessage());
		} finally {
			if (obsClient != null) {
				try {
					obsClient.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					throw new IOException(e.getMessage());
				}
			}
		}
	}

	/**
	 * 根据对象名获取文件流
	 * @param objectKey
	 */
	public static byte [] getFileStr(String objectKey) {
		byte [] fileStr = null;
		ObsClient obsClient = null;
			obsClient = new ObsClient(ak, sk, config);
			ObsObject  obsObject = obsClient.getObject(bucketName, objectKey);

			InputStream input = obsObject.getObjectContent();
			byte[] b = new byte[1024];
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int len;
				while ((len=input.read(b)) != -1){
					bos.write(b, 0, len);
				}

//				fileStr = new String(bos.toByteArray());
				bos.close();
				input.close();
				fileStr = bos.toByteArray();
			}catch (IOException e){
				log.error(e.getMessage());
				throw new ObsException(e.getMessage());			}
			return fileStr;
	}


	/**
	 * 获取文件上传路径
	 */
	public static String getUploadDir() {
		String dateFolder = DateUtils.getCurrDate("yyyyMMdd");
		String yearFolder = DateUtils.getCurrDate("yyyy");
		String uploadDir = yearFolder + "/" + dateFolder + "/";
		return uploadDir;
	}

	/**
	 * 获取新文件名
	 */
	public static String getNewFileName(String oldFileName) {
		String ext = getFileExt(oldFileName);
		return UUID.randomUUID().toString() + ext;
	}

	public static String getNewFileName() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取文件后缀名
	 */
	public static String getFileExt(String fileName) {
		String ext = "";
		if (fileName != null && !"".equals(fileName)) {
			ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return ext;
	}

	/**
	 * 列举对象（文件）的信息
	 */
	public static void listFile() throws IOException {
		ObsClient obsClient = new ObsClient(config);
		ObjectListing objList = obsClient.listObjects(bucketName);
		for (ObsObject obj : objList.getObjects()) {
			log.info(" https:" + bucketName + "." + endPoint + "/" + obj.getObjectKey() + " (size="
					+ obj.getMetadata().getContentLength() + ")");
		}
		if (obsClient != null) {
			try {
				obsClient.close();
			} catch (IOException e) {
				throw new IOException(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(putObject(getNewFileName("E:/img/10.jpg"), new File("E:/img/10.jpg")));
			listFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
