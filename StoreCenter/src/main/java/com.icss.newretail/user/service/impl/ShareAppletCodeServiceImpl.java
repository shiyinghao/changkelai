package com.icss.newretail.user.service.impl;

import com.icss.newretail.model.ShareAppletRequest;
import com.icss.newretail.service.user.ShareAppletCodeService;
import com.icss.newretail.util.OBSUtil;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ShareAppletCodeServiceImpl implements ShareAppletCodeService {

	
	@Override
	public Map<String,Object> appletCode(HttpServletRequest request,ShareAppletRequest shareApplet) {
		Map<String,Object> map = new HashMap<String, Object>();
		RestTemplate rest = new RestTemplate();
			InputStream inputStream = null;
			OutputStream outputStream = null;
			String access_token = getAccess_token(shareApplet.getAppid(), shareApplet.getSecret());
			if(access_token!=null && access_token!="") {
			try {
				String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" +access_token;
				Map<String,Object> param = new HashMap<>();
				param.put("scene", "xxxx");//未发布小程序无法读取，所以将参数注释scene 默认设置为xxxx
//				param.put("page", shareApplet.getPage());
//				param.put("width", shareApplet.getWidth());
//				param.put("auto_color", false);
				Map<String,Object> line_color = new HashMap<>();
//				line_color.put("r", 0);
//				line_color.put("g", 0);
//				line_color.put("b", 0);
				param.put("line_color", line_color);
				MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
				HttpEntity<String> requestEntity = new HttpEntity<String>(JSONObject.fromObject(param).toString(), headers);
				ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
				byte[] result = entity.getBody();
	            inputStream = new ByteArrayInputStream(result);
//	            File file = new File("F:/3.png");
//    			if (!file.exists()) {
//    				try {
//    					file.createNewFile();
//    				} catch (IOException e) {
//    					e.printStackTrace();
//    				}
//    			}
//    			outputStream = new FileOutputStream(file);
//    			int len = 0;
//    			byte[] in_b  = null;
//    			byte[] buf = new byte[8192];
//    			while ((len = inputStream.read(buf, 0, 8192)) != -1) {
//    				outputStream.write(buf, 0, len);
//    			}
//    			outputStream.flush();
//	            String basePath = "http://localhost:18080/api/file-service/v1/file/uploadFile";
	            MultipartFile multipartFile = new MockMultipartFile("ces", inputStream);
    			String oldFileName = multipartFile.getOriginalFilename();
    			String newFileName = OBSUtil.getNewFileName(oldFileName);//新文件名
    			//获取保存路径
    			DynamicStringProperty myprop = DynamicPropertyFactory.getInstance().getStringProperty("servicecomb.uploads.directory", "");
    			String uploadDir = myprop.getValue() + "/" + OBSUtil.getUploadDir();
    			//文件写入本地
    			String locaurl = "F:/home/file/";
	    		File targetFile = new File(locaurl);
	    		if(!new File(locaurl).exists()){
                    new File(locaurl).mkdirs();
				}
	    		targetFile = new File(locaurl+newFileName);
//    			File targetFile = new File("F:/home/file" + uploadDir + newFileName);
    			multipartFile.transferTo(targetFile);
    			//上传obs
    			String fileUrl = OBSUtil.putObject(newFileName, targetFile);
    			map.put("code", 1);
    			map.put("message","调用成功");
    			map.put("url", fileUrl);
    			File locaFile = new File(locaurl+newFileName);
    			if ( locaFile.exists() ){//存在则删除
    				locaFile.delete();	
    				System.out.println("文件删除成功！");
    			}
			} catch (Exception e) {
				System.out.println("调用小程序生成微信永久小程序码URL接口异常"+e);
				map.put("code", 0);
				map.put("message", "调用失败-"+e);
			} finally {
				if(inputStream != null){
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(outputStream != null){
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	return map;
}

public String getAccess_token( String appID,String appScret) {
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
		+ appScret;
		String access_token = null;
		String expires_in=null;
		try {
			URL getUrl=new URL(url);
			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream(); 
			int size = is.available(); 
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");
			JSONObject json = JSONObject.fromObject(message);
			access_token = json.getString("access_token");
			expires_in =json.getString("expires_in");
		} catch (MalformedURLException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
			return access_token;
		}
}

