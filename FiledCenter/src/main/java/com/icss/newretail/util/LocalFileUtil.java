package com.icss.newretail.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.UUID;
public class LocalFileUtil {
//	private static String uploadDir = ResourceBundle.getBundle("file-upload").getString("uploadDir");// 上传文件的目录
//	private static String downloadUrl = ResourceBundle.getBundle("file-upload").getString("downloadUrl");// 上传文件的访问网络地址
//	private static String downloadUrlFdfs = ResourceBundle.getBundle("file-upload").getString("downloadUrlFdfs");// 上传文件的访问网络地址
	private static String uploadDir = "/home/file/";// 上传文件的目录
	private static String downloadUrl = "";// 上传文件的访问网络地址
	private static String downloadUrlFdfs = "";// 上传文件的访问网络地址
	/**
	 * 上传文件-本地路径
	 */
	public static boolean uploadFile(File file, String uploadDir, String fileName){
		boolean result = true;
		RandomAccessFile savedFile = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			//写文件
			savedFile = new RandomAccessFile(uploadDir + fileName, "rw");
			byte[] buf = new byte[10240];
			int size = 0;
			while ((size = fis.read(buf)) != -1) {
				savedFile.write(buf, 0, size);
			}
			savedFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (savedFile != null) {
				try {
					savedFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 上传文件-本地路径
	 */
	public static boolean uploadFile(InputStream is, String uploadDir, String fileName){
		boolean result = true;
		RandomAccessFile savedFile = null;
		try {
			//写文件
			savedFile = new RandomAccessFile(uploadDir + fileName, "rw");
			byte[] buf = new byte[10240];
			int size = 0;
			while ((size = is.read(buf)) != -1) {
				savedFile.write(buf, 0, size);
			}
			savedFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (savedFile != null) {
				try {
					savedFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 获取文件上传路径
	 */
	public static String getUploadDir() {
		String dateFolder = DateUtils.getCurrDate("yyyyMMdd");
		String yearFolder = DateUtils.getCurrDate("yyyy");
		//文件上传路径
		String uploadDirN = uploadDir + yearFolder + "/" + dateFolder + "/";
		//如果目录不存在，则创建
		File filePath = new File(uploadDirN);
		if(filePath != null && !filePath.exists()){//文件夹不存在，创建文件夹
			filePath.mkdirs();
		}
		return uploadDirN;
	}
	/**
	 * 获取新文件名及网络地址
	 */
	public static String getNewFileName(String oldFileName) {
		String ext = getFileExt(oldFileName);
		//新文件名
		String newFileName = UUID.randomUUID().toString() + "_" + DateUtils.getCurrDate("yyyyMMdd") + ext;
		return newFileName;
	}
	/**
	 * 获取缩略图片名
	 */
	public static String getImageNameThumb(String imageName) {
		return addFileSuffix(imageName, "_s");
	}
	/**
	 * 音频文件名称 转 MP3名称
	 */
	public static String convertAudio2Mp3(String fileName) {
		return replaceFileExt(fileName, ".mp3");
	}
	/**
	 * 视频文件名称 转 MP4名称
	 */
	public static String convertMedia2Mp4(String fileName) {
		return replaceFileExt(fileName, ".mp4");
	}
	/**
	 * 获取视频封面图片名称
	 */
	public static String getMediaCoverName(String fileName) {
		return replaceFileExt(fileName, ".jpg");
	}
	/**
	 * 文件重命名
	 */
	public static void renameTo(String uploadDir, String fileName, String newFileName) {
		new File(uploadDir, fileName).renameTo(new File(uploadDir, newFileName));
	}
	/**
	 * 获取文件后缀名
	 */
	public static String getFileExt(String fileName) {
		String ext = "";//文件后缀名
		if (fileName != null && !"".equals(fileName)) {
			ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return ext;
	}
	/**
	 * 文件加后缀重命名
	 */
	public static String addFileSuffix(String fileName, String suffix) {
		String ext = getFileExt(fileName);
		String fileNameNew = "";
		if (fileName != null && !"".equals(fileName)) {
			fileNameNew = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + suffix + ext;
		}
		return fileNameNew;
	}
	/**
	 * 文件加后缀重命名
	 */
	public static String addFileSuffix(String fileName, String suffix, String ext) {
		String fileNameNew = addFileSuffix(fileName, suffix);
		fileNameNew = replaceFileExt(fileNameNew, ext);
		return fileNameNew;
	}
	/**
	 * 替换文件后缀，返回新文件名
	 */
	public static String replaceFileExt(String fileName, String ext) {
		String fileNameNew = "";
		if (fileName != null && !"".equals(fileName)) {
			fileNameNew = fileName.substring(0, fileName.lastIndexOf(".")) + ext;
		}
		return fileNameNew;
	}
	/**
	 * 获取文件网络地址
	 */
	public static String getDownloadUrl(String fileName) {
		String downloadUrlN = "";
		if (fileName != null && !"".equals(fileName)) {
			downloadUrlN = downloadUrl + fileName;
		}
		return downloadUrlN;
	}
	/**
	 * 获取文件网络地址
	 */
	public static String getDownloadUrlFdfs(String fdfs_filepath) {
		String downloadUrlN = "";
		if (fdfs_filepath != null && !"".equals(fdfs_filepath)) {
			downloadUrlN = downloadUrlFdfs + fdfs_filepath;
		}
		return downloadUrlN;
	}
	/**
	 * 判断是否是图片
	 */
	public static boolean isImage(String fileName) {
		String[] formats = new String[]{".jpg",".bmp",".jpeg",".png",".gif"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 判断是否是gif动图
	 */
	public static boolean isGif(String fileName) {
		String[] formats = new String[]{".gif"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 判断是否是音频
	 */
	public static boolean isAudio(String fileName) {
		String[] formats = new String[]{".cd",".ogg",".mp3",".amr",".asf","wma",".wav",".rm",".real",".ape",".module",".midi",".vqf"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 判断是否是MP3
	 */
	public static boolean isMp3(String fileName) {
		String[] formats = new String[]{".mp3"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 判断是否是视频
	 */
	public static boolean isMedia(String fileName) {
		String[] formats = new String[]{".rm",".rmvb",".wmv",".avi",".mp4",".3gp",".mkv"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 判断是否是MP4
	 */
	public static boolean isMp4(String fileName) {
		String[] formats = new String[]{".mp4"};
		String ext = getFileExt(fileName);
		return checkRight(ext, formats);
	}
	/**
	 * 效验结果
	 */
	private static boolean checkRight(String right, String[] rightArray) {
		boolean result = false;
		if (right != null && !"".equals(right)) {
			for (int i = 0; i < rightArray.length; i++) {
				if (right.equalsIgnoreCase(rightArray[i])) {//有匹配项
					result = true;
					break;
				}
			}
		}
		return result;
	}
}
