package com.icss.newretail.api.file;

import com.icss.newretail.model.AppletsDTO;
import com.icss.newretail.model.ResponseResult;
import com.icss.newretail.service.file.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestSchema(schemaId = "file")
@RequestMapping(path = "/v1/file")
public class FileApi {
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     *
     * @param loginRequest
     * @return
     */
    @PostMapping(path = "uploadFile")
    @ApiOperation(value = "上传文件")
    public ResponseResult<String> uploadFile(MultipartFile file,
                                             @RequestParam(required = false, defaultValue = "") String fileProject,
                                             @RequestParam(required = false, defaultValue = "") String fileExt,
                                             @RequestParam(required = false, defaultValue = "false") String compross) {
        return fileService.uploadFile(file, fileProject, fileExt, compross);
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping(path = "uploadFileByTour")
    @ApiOperation(value = "上传文件")
    public ResponseResult uploadFileByTourService(MultipartFile file) {
        return fileService.uploadFileByTourService(file);
    }

    /**
     * 根据对象名获取文件流
     *
     * @param objectKey
     * @return
     */
    @PostMapping(path = "getObjFileStr")
    @ApiOperation(value = "根据对象名获取文件流")
    public ResponseResult<byte[]> getObjFileStr(@RequestParam(required = true, defaultValue = "") String objectKey) {
        return fileService.getObjFileStr(objectKey);
    }

    /**
     * 生成二维码图片，上传到文件服务器，并返回图片路径
     *
     * @param fileExt
     * @param content
     * @return
     */
    @GetMapping(path = "createQrcode")
    @ApiOperation(value = "上传文件")
    public ResponseResult<String> createQrcode(
            @ApiParam(name = "fileExt", value = "文件后缀", required = true) String fileExt,
            @ApiParam(name = "content", value = "生成二维码的内容", required = true) String content) {
        return fileService.createQrcode(fileExt, content);
    }

    /**
     * 文件上传工行
     *
     * @param file
     * @param fileName
     * @return ResponseResult
     */
    @PostMapping(path = "fileUploadIcbc")
    @ApiOperation(value = "工行上传文件")
    public ResponseResult<String> fileUploadIcbc(MultipartFile file) {
        return fileService.fileUploadIcbc(file);
    }


    /**
     * 上传图片鉴别
     *
     * @return
     */
    @PostMapping(path = "uploadPic")
    @ApiOperation(value = "上传图片鉴别")
    public ResponseResult<String> uploadPic(MultipartFile file,@RequestParam String appId,@RequestParam String secret) {
        return fileService.uploadPic(file,appId,secret);
    }

    /**
     * 敏感词汇鉴别
     *
     * @return
     */
    @PostMapping(path = "uploadWord")
    @ApiOperation(value = "敏感词汇鉴别")
    public ResponseResult<String> uploadWord(@RequestBody AppletsDTO  appletsDTO) {
        return fileService.uploadWord(appletsDTO);
    }

}
