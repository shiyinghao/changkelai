package com.icss.newretail.file.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.icss.newretail.file.ma.WxMaJavaTool;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.model.ShareAppletRequest;
import com.icss.newretail.model.UserIcbcTempDTO;
import com.icss.newretail.service.file.ShareAppletCodeService;
import com.icss.newretail.service.user.ForeignService;
import com.icss.newretail.service.user.ReviewService;
import com.icss.newretail.util.MatrixToImageWriter;
import com.icss.newretail.util.OBSUtil;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
@Slf4j
public class ShareAppletCodeServiceImpl implements ShareAppletCodeService {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	@RpcReference(microserviceName = "user-service", schemaId = "review")
	private ReviewService reviewService;


	@RpcReference(microserviceName = "user-service", schemaId = "foreign")
	private ForeignService foreignService;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private RedissonClient redissonClient;
	@Value("${obs.endPoint}")
	private String endPoint;// 存储点
	@Value("${obs.bucketName}")
	private String bucketName;// 文件桶
	@Value("${servicecomb.credentials.accessKey}")
	private String ak;// accessKey
	@Value("${servicecomb.credentials.secretKey}")
	private String sk;// secretKey

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

//	@Override
//	public ResponseBase insertAll(ShareAppletRequest shareApplet) {
//		try {
//			WxMaCodeLineColor wxMaCodeLineColor = new WxMaCodeLineColor("0", "0", "0");
//			WxMaService wxMaService = WxMaJavaTool.getWxMaService(shareApplet.getAppid(), shareApplet.getSecret(), jedisPool, redissonClient);
//			WxMaQrcodeService wxMaQrcodeService = wxMaService.getQrcodeService();
//
//			//获取数据源
//			List<UserOrgRelationDTO> userOrgRelationDTOS = foreignService.getAllOrgReletion();
//			if (userOrgRelationDTOS != null && userOrgRelationDTOS.size() > 0) {
//				for (int i = 0; i < userOrgRelationDTOS.size(); i++) {
//
//					File file = wxMaQrcodeService.createWxaCodeUnlimit(
//							userOrgRelationDTOS.get(i).getUuid(), shareApplet.getPage(), shareApplet.getWidth(), false, wxMaCodeLineColor, false);
//					//上传obs
//					String fileUrl = OBSUtil.putObject(generateRandomFilename(), file);
//					//插入数据
//					ResponseBase responseBase = foreignService.insertByAppletShare(userOrgRelationDTOS.get(i).getUserId(), userOrgRelationDTOS.get(i).getOrgSeq(), fileUrl);
//				}
//			}
//		} catch (Exception e) {
//			System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
//		}
//		return null;
//	}

	public static void writeToStream(BitMatrix matrix, String format,
									 OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	public static void main(String[] args) throws Exception {
		String text = "11111"; // 二维码内容
		MatrixToImageWriter.writeToFile(text);
	}

	public Map<String, String> writeToFile(String text)
			throws IOException, WriterException {
		Map<String, String> map = new HashMap<>();
		String fileUrl = "";
		int width = 300; // 二维码图片宽度
		int height = 300; // 二维码图片高度
		String format = "jpg";// 二维码的图片格式
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

		BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
				BarcodeFormat.QR_CODE, width, height, hints);
		// 生成二维码/home/file/
		Random random = new Random();
		String name = random.nextInt(10000) + System.currentTimeMillis() + ".jpg";
		String url = "F:" + File.separator + "home" + File.separator + "file" + File.separator;
		String newUrl = url + name;
		if (!new File(url).exists()) {
			new File(url).mkdirs();
		}
		File outputFile = new File(newUrl);
		BufferedImage image = toBufferedImage(bitMatrix);
		if (!ImageIO.write(image, format, outputFile)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + outputFile);
		}
		//上传obs
		fileUrl = putObjectNew(name, outputFile);
		map.put("url", fileUrl);
		outputFile.delete();
		return map;
	}

	@Override
	public Map<String, Object> appletCode(ShareAppletRequest shareApplet) {
		Map<String, Object> map = new HashMap<String, Object>();
		RestTemplate rest = new RestTemplate();
		String phone = shareApplet.getPhone();
		//查看小程序码 是否已经存在 存在则返回图片
		Boolean type = false;
		if (shareApplet.getUserId() != null) {
			type = true;
			if (shareApplet.getType() == 1 || "1".equals(shareApplet.getType())) {
				ResponseResult responseResult = foreignService.getAppletShare
						(shareApplet.getUserId(), shareApplet.getOrgSeq(), phone, shareApplet.getType() == null ? 1 : shareApplet.getType());
				if (responseResult.getCode() == 1 || "1".equals(responseResult.getCode())) {
					map.put("code", 1);
					map.put("message", "调用成功");
					map.put("url", responseResult.getResult());
					return map;
				}
			}
		}
		//替换参数
		if (shareApplet.getSceneStr() != null) {
			Integer id = foreignService.insertAppletById(shareApplet.getSceneStr());
			System.out.println("--参数替换成功---获取主键id=" + id);
			shareApplet.setSceneStr(id.toString());
		}
		try {
			WxMaCodeLineColor wxMaCodeLineColor = new WxMaCodeLineColor("0", "0", "0");
			WxMaService wxMaService = WxMaJavaTool.getWxMaService(shareApplet.getAppid(), shareApplet.getSecret(), jedisPool, redissonClient);
			WxMaQrcodeService wxMaQrcodeService = wxMaService.getQrcodeService();
			File file = wxMaQrcodeService.createWxaCodeUnlimit(
					shareApplet.getSceneStr(), shareApplet.getPage(), shareApplet.getWidth(), false, wxMaCodeLineColor, false);
			//上传obs
			String fileUrl = putObjectNew(generateRandomFilename(), file);
//			String fileUrl = OBSUtil.putObject(generateRandomFilename(), file);
			//插入数据
			if (type) {
				ResponseBase responseBase = foreignService.insertByAppletShare
						(shareApplet.getUserId(), shareApplet.getOrgSeq(), fileUrl, phone, shareApplet.getType() == null ? 1 : shareApplet.getType());
			}
			map.put("code", 1);
			map.put("message", "调用成功");
			map.put("url", fileUrl);
		} catch (Exception e) {
			System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
			map.put("code", 0);
			map.put("message", "调用失败-" + e);
		}
		return map;
	}

	@Override
	public Map<String, Object> matrixToImageWriter(String parm) {
		Map<String, Object> map = new HashMap<String, Object>();
		String fileUrl = "";
		try {
			Map<String, String> urlmap = writeToFile(parm);
			fileUrl = urlmap.get("url");
		} catch (Exception e) {
			log.error("二维码生成图片报错》》》》》》" + e);
			map.put("code", 0);
			map.put("message", "调用失败");
			map.put("url", fileUrl);
			return map;
		}
		map.put("code", 1);
		map.put("message", "调用成功");
		map.put("url", fileUrl);
		return map;
	}

	public String getAccess_token(String appID, String appScret) {
		// 访问微信服务器
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
				+ appScret;
		String access_token = null;
		String expires_in = null;
		try {
			URL getUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
			expires_in = json.getString("expires_in");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_token;
	}

	@Override
	public Map<String, Object> download(Integer tempType, Integer type) {
		Map<String, Object> map = new HashMap<>();
		String urlPath = "";
		String fileFullName = "";
		ResponseResult<List<UserIcbcTempDTO>> userIcbcTempDTOList = reviewService.queryUserIcbcTemp(tempType);
		if (userIcbcTempDTOList.getResult() != null && userIcbcTempDTOList.getResult().size() > 0) {
			List<UserIcbcTempDTO> userIcbcTempDTOS = userIcbcTempDTOList.getResult();
			for (int i = 0; i < userIcbcTempDTOS.size(); i++) {
				//此处使用的配置文件里面取出的文件服务器地址,拼凑成完整的文件服务器上的文件路径
				//写demo时，可以直接写成http://xxx/xx/xx.txt.这种形式
				urlPath = userIcbcTempDTOS.get(i).getTempUrl();
				fileFullName = userIcbcTempDTOS.get(i).getTempName();
				if (type == 1) {
					urlPath = userIcbcTempDTOS.get(i).getPrintUrl();
					fileFullName = userIcbcTempDTOS.get(i).getPrintName();
				}
				map.put("urlPath", urlPath);
				map.put("fileFullName", fileFullName);
			}
		}
		return map;
	}

	//生成随机文件名，防止上传文件后文件名重复
	public String generateRandomFilename() {
		String RandomFilename = "";
		Random rand = new Random();//生成随机数
		int random = rand.nextInt();

		Calendar calCurrent = Calendar.getInstance();
		int intDay = calCurrent.get(Calendar.DATE);
		int intMonth = calCurrent.get(Calendar.MONTH) + 1;
		int intYear = calCurrent.get(Calendar.YEAR);
		String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
				String.valueOf(intDay) + "_";

		RandomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

		return RandomFilename;
	}

	private String putObjectNew(String objectKey, File file) throws IOException, ObsException {
		ObsClient obsClient = null;
		try {
			ObsConfiguration config = new ObsConfiguration();
			config.setSocketTimeout(30000);
			config.setConnectionTimeout(10000);
			config.setEndPoint(endPoint);
			obsClient = new ObsClient(ak, sk, config);
			// 判断文件桶是不是存在，不存在则创建
			if (!obsClient.headBucket(bucketName)) {
				obsClient.createBucket(bucketName);
			}
			PutObjectResult putObjectResult = obsClient.putObject(bucketName, OBSUtil.getUploadDir() + objectKey, file);
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
}

