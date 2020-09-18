package com.icss.newretail.service.file;

import com.icss.newretail.model.AppletsDTO;
import com.icss.newretail.model.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	/**
	 * 上传文件
	 * @param file
	 * @param fileProject
	 * @param fileExt
	 * @param compross
	 * @return
	 */
	public ResponseResult<String> uploadFile(MultipartFile file, String fileProject, String fileExt, String compross);

	ResponseResult uploadFileByTourService(MultipartFile file);

	/**
	 * 生成二维码图片，上传到文件服务器，并返回图片路径
	 * @param fileExt
	 * @param content
	 * @return
	 */
	public ResponseResult<String> createQrcode(String fileExt, String content);

	ResponseResult<byte []>  getObjFileStr(String objectKey);

	  /**
	   * 文件上传
	   *
	   * @param file
	   * @param fileName
	   * @return ResponseResult
	   */
	ResponseResult<String> fileUploadIcbc(MultipartFile file);

	ResponseResult<String> uploadPic(MultipartFile file, String appId, String secret);

	ResponseResult<String> uploadWord( AppletsDTO appletsDTO);
}
