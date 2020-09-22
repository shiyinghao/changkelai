package com.icss.newretail.api.file;

import com.icss.newretail.model.FileBankDTO;
import com.icss.newretail.model.ShareAppletRequest;
import com.icss.newretail.service.file.ShareAppletCodeService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 作者：	叶稻田
 * @version 创建时间：2020年4月2日 下午2:52:08
 * @ClassName 类名称
 * @Description 类描述
 */
@RestSchema(schemaId = "share")
@RequestMapping(path = "/v1/share")
@Slf4j
public class ShareAppletCodeApi {

	@Autowired
	private ShareAppletCodeService shareAppletCodeService;


	/**
	 * 小程序分享码
	 *
	 * @param shareApplet
	 * @return
	 */
	@PostMapping("appletCode")
	public Map<String, Object> appletCode(@RequestBody ShareAppletRequest shareApplet) {
		return shareAppletCodeService.appletCode(shareApplet);
	}

	/**
	 * 二维码服务
	 *
	 * @param parm
	 * @return
	 */
	@GetMapping("matrixToImageWriter")
	public Map<String, Object> matrixToImageWriter(@RequestParam String parm) {
		return shareAppletCodeService.matrixToImageWriter(parm);
	}


	/**
	 * 模板文件下载
	 *
	 * @param tempType type 1-模板 2-打印件
	 * @param type
	 * @return
	 */
	@GetMapping("/download")
	@ApiResponses({
			@ApiResponse(code = 200, response = File.class, message = "")
	})
	public ResponseEntity<Resource> download(@RequestParam Integer tempType, @RequestParam Integer type) {
		try {
			Map<String, Object> map = shareAppletCodeService.download(tempType, type);
			if (map == null) {
				return new ResponseEntity<Resource>(HttpStatus.OK);
			}
			ResourceLoader loader = new DefaultResourceLoader();
			Resource resource = loader.getResource(map.get("urlPath").toString());
			String fileName = map.get("fileFullName").toString();

			HttpHeaders headers = new HttpHeaders();
			//指定以流的形式下载文件
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//指定文件名
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			log.error("下载报错》》》》》》》》》》》》》》》》》" + e);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	/**
	 * 文件下载，压缩包
	 *
	 * @param fileNameZip
	 * @return
	 */
	@GetMapping("/downloadZip")
	@ApiResponses({
			@ApiResponse(code = 200, response = File.class, message = "")
	})
	public ResponseEntity<byte[]> downloadZip(@RequestBody List<FileBankDTO> fileBankDTOS, @RequestParam String fileNameZip) {
		try {
			// ---------------------------压缩文件处理-------------------------------
			ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
			// 压缩文件
			ZipOutputStream zipOut = new ZipOutputStream(byteOutPutStream);
			try {
				for (int i = 0; i < fileBankDTOS.size(); i++) {
					URL ur = new URL(fileBankDTOS.get(i).getFilePath());
					BufferedInputStream in = null;
					ByteArrayOutputStream out = null;
					try {
						in = new BufferedInputStream(ur.openStream());
						out = new ByteArrayOutputStream(1024);
						byte[] temp = new byte[1024];
						int size = 0;
						while ((size = in.read(temp)) != -1) {
							out.write(temp, 0, size);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					byte[] content = out.toByteArray();
					// 使用指定名称创建新的 ZIP 条目 （通俗点就是文件名）
					ZipEntry zipEntry = new ZipEntry(fileBankDTOS.get(i).getFileName());
					// 开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处
					zipOut.putNextEntry(zipEntry);
					//直接写入到压缩输出流中即可
					zipOut.write(content);
					zipOut.closeEntry();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// ---------------------------压缩文件处理-------------------------------
			HttpHeaders headers = new HttpHeaders();
			try {
				//设置压缩包名称
				fileNameZip = new String(fileNameZip.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//指定以流的形式下载文件
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//指定文件名
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileNameZip, "UTF-8"));
			return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			log.error("下载报错》》》》》》》》》》》》》》》》》" + e);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

//	/**
//	 * 二维码服务
//	 *
//	 * @return
//	 */
//	@PostMapping("insertAll")
//	public ResponseBase insertAll(@RequestBody ShareAppletRequest shareApplet) {
//		return shareAppletCodeService.insertAll(shareApplet);
//	}
}
