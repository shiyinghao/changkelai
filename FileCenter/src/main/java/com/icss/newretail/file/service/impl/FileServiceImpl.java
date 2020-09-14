package com.icss.newretail.file.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.icss.newretail.file.ma.WxMaJavaTool;
import com.icss.newretail.model.AppletsDTO;
import com.icss.newretail.model.ResponseBase;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.file.FileService;
import com.icss.newretail.util.OBSUtil;
import com.icss.newretail.util.ToolUtil;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service("FileService")
@Slf4j
@Data
public class FileServiceImpl implements FileService {

    @Value("${obs.endPoint}")
    private String endPoint;// 存储点

    @Value("${obs.bucketName}")
    private String bucketName;// 文件桶

    @Value("${servicecomb.credentials.accessKey}")
    private String ak;// accessKey

    @Value("${servicecomb.credentials.secretKey}")
    private String sk;// secretKey

    //工行交互服务根地址
    private static String icbcUrl = "http://118.121.196.48:8002/icbc_wly/api";

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 上传文件
     *
     * @param file
     * @param fileProject
     * @param fileExt
     * @param compross
     * @return
     */
    @Override
    @Transactional
    public ResponseResult<String> uploadFile(MultipartFile file, String fileProject, String fileExt, String compross) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        try {
            // 获取新文件名 gif|jpg|jpeg|png|GIF|JPG|PNG
            // List<String> imgArr = new ArrayList<String>();
            // imgArr.add("PNG");
            // imgArr.add("JPG");
            // imgArr.add("gif");
            // imgArr.add("GIF");
            // imgArr.add("png");
            // imgArr.add("jpeg");
            // imgArr.add("jpg");

            String oldFileName = file.getOriginalFilename();
            String newFileName = OBSUtil.getNewFileName(oldFileName);// 新文件名
            // 根据文件类型对文件大小进行限制
            // String fileType =
            // oldFileName.substring(oldFileName.lastIndexOf(".") + 1,
            // oldFileName.length());
            // long fileSize = file.getSize();
            // int picSize = 1;
            // int wordSize = 500;
            // double size = Double.valueOf(fileSize) / 1024 / 1024;
            // if (imgArr.contains(fileType)) {
            // if (size > picSize) {
            // responseResult.setCode(0);
            // responseResult.setMessage("图片不能大于1M");
            // responseResult.setResult(null);
            // throw new MaxUploadSizeExceededException(picSize);
            // }
            // } else {
            // if (size > wordSize) {
            // responseResult.setCode(0);
            // responseResult.setMessage("文件不能大于500M");
            // responseResult.setResult(null);
            // throw new MaxUploadSizeExceededException(wordSize);
            // }
            // }

            if (!StringUtils.isEmpty(fileExt)) {
                newFileName = OBSUtil.getNewFileName(fileExt);// 新文件名
            }
            // 获取保存路径
            DynamicStringProperty myprop = DynamicPropertyFactory.getInstance()
                    .getStringProperty("servicecomb.uploads.directory", "");
            String uploadDir = myprop.getValue() + "/" + OBSUtil.getUploadDir();
            // 文件写入本地
            File targetFile = new File("/home/file/" + uploadDir + newFileName);
            file.transferTo(targetFile);
            // 上传obs
            String fileUrl = putObject(newFileName, targetFile);
            responseResult.setCode(1);
            responseResult.setMessage("上传成功");
            responseResult.setResult(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            responseResult.setCode(0);
        }
        return responseResult;
    }

    @Override
    public ResponseResult uploadFileByTourService(MultipartFile file) {
        ResponseResult responseResult = new ResponseResult();
        try {
            String oldFileName = file.getOriginalFilename();
            String newFileName = OBSUtil.getNewFileName(oldFileName);// 新文件名
            // 获取保存路径
            DynamicStringProperty myprop = DynamicPropertyFactory.getInstance()
                    .getStringProperty("servicecomb.uploads.directory", "");
            String uploadDir = myprop.getValue() + "/" + OBSUtil.getUploadDir();
            // 文件写入本地
            File targetFile = new File("/home/file/" + uploadDir + newFileName);
            file.transferTo(targetFile);
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
                PutObjectResult putObjectResult = obsClient.putObject(bucketName, OBSUtil.getUploadDir() + newFileName, targetFile);
                Map<String, Object> map = new HashMap<>();
                map.put("objectKey", putObjectResult.getObjectKey());
                map.put("objectUrl", putObjectResult.getObjectUrl());
                responseResult.setResult(map);

                responseResult.setCode(ResponseBase.SUCCESS);
                responseResult.setMessage("上传成功");
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
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            responseResult.setCode(ResponseBase.FAILED);
            responseResult.setMessage(e.getMessage());
        }
        return responseResult;
    }

    private String putObject(String objectKey, File file) throws IOException, ObsException {
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

    /**
     * 生成二维码图片，上传到文件服务器，并返回图片路径
     *
     * @param fileExt
     * @param content
     * @return
     */
    @Override
    @Transactional
    public ResponseResult<String> createQrcode(String fileExt, String content) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        try {
            // 生成二维码图片
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 195, 195, hints);
            // 获取新文件名
            String newFileName = OBSUtil.getNewFileName("." + fileExt);// 新文件名
            // 获取保存路径
            DynamicStringProperty myprop = DynamicPropertyFactory.getInstance()
                    .getStringProperty("servicecomb.uploads.directory", "");
            String uploadDir = myprop.getValue() + "/" + OBSUtil.getUploadDir();
            // 文件写入本地
            File targetFile = new File(uploadDir + newFileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            MatrixToImageWriter.writeToPath(matrix, fileExt, targetFile.toPath());
            // 上传obs
            String fileUrl = putObject(newFileName, targetFile);
            responseResult.setCode(1);
            responseResult.setResult(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            responseResult.setCode(0);
        }
        return responseResult;
    }

    /**
     * 文件上传
     *
     * @param file
     * @param fileName
     * @return ResponseResult
     */
    @Override
    public ResponseResult<String> fileUploadIcbc(MultipartFile file) {
        ResponseResult<String> resp = new ResponseResult<String>();
        String submitUrl = icbcUrl + "/file/upload";
        try {
            String fileName = file.getOriginalFilename();
            log.info("-工行文件-" + fileName + "--上传-1-submitUrl--" + submitUrl);
            String result = HttpRequest.post(submitUrl)
                    .header("Content-Type", "multipart/form-data;boundary=\"boundary\"")
                    .form("fileContent", file.getBytes(), fileName)
                    .execute().body();
            log.info("-工行文件-" + fileName + "--上传-2-result--" + result);
            JSONObject jsonResult = JSONObject.parseObject(result);
            if ("0".equals(jsonResult.getString("code"))) {
                JSONArray jsonList = jsonResult.getJSONArray("data");
                JSONObject jsonDate = jsonList.getJSONObject(0);
                String fileKey = jsonDate.getString("fileKey");
                log.info("-工行文件-" + fileName + "--上传-3-fileKey--" + fileKey);
                resp.setCode(1);
                resp.setMessage("文件上传成功");
                resp.setResult(fileKey);
            } else {
                resp.setCode(0);
                resp.setMessage("文件上传异常：" + jsonResult.getString("msg"));
            }
        } catch (Exception e) {
            resp.setCode(0);
            resp.setMessage("文件上传异常：" + e.getMessage());
            log.error("文件上传异常---" + e.getMessage());
        }
        return resp;
    }

    @Override
    public ResponseResult<String> uploadPic(MultipartFile file, String appId, String secret) {
        ResponseResult<String> result = new ResponseResult<String>();
        try {
            WxMaService wxMaService = WxMaJavaTool.getWxMaService(appId, secret, jedisPool, redissonClient);
            String accessToken = wxMaService.getAccessToken();
            String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken;
            String results = uploadFiled(url, file);
            System.out.println("获取的信息为" + results);
            if (ToolUtil.notEmpty(results)) {
                result.setCode(1);
                result.setMessage("获取成功");
                result.setResult(results);
            } else {
                result.setCode(0);
                result.setMessage("获取失败");
            }
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("调用微信接口鉴别图片异常:" + ex.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult<String> uploadWord( AppletsDTO appletsDTO) {
        ResponseResult<String> result = new ResponseResult<String>();
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            WxMaService wxMaService = WxMaJavaTool.getWxMaService(appletsDTO.getAppId(), appletsDTO.getSecret(), jedisPool, redissonClient);
            String accessToken = wxMaService.getAccessToken();

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/json;charset=UTF-8");
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("content", appletsDTO.getContent());
            request.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.create("application/json", "utf-8")));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String results = EntityUtils.toString(httpEntity, "UTF-8");// 转成string

            System.out.println("获取的信息为" + results);
            if (ToolUtil.notEmpty(results)) {
                result.setCode(1);
                result.setMessage("获取成功");
                result.setResult(results);
            } else {
                result.setCode(0);
                result.setMessage("获取失败");
            }
        } catch (Exception ex) {
            result.setCode(0);
            result.setMessage("调用微信接口鉴别词汇异常:" + ex.getMessage());
        }
        return result;
    }

    /**
     * 上传二进制文件
     *
     * @param graphurl 接口地址
     * @param file     图片文件
     * @return
     */
    public static String uploadFiled(String graphurl, MultipartFile file) {
        String line = null;//接口返回的结果
        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL(graphurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"image\";filename=\""
                    + "https://api.weixin.qq.com" + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 读取文件数据
            out.write(file.getBytes());
            // 最后添加换行
            out.write(newLine.getBytes());

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY
                    + boundaryPrefix + newLine).getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
        }
        return line;
    }

    @Override
    public ResponseResult<byte[]> getObjFileStr(String objectKey) {
        return new ResponseResult(OBSUtil.getFileStr(objectKey));
    }

}
