package com.icss.newretail.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.management.OperationsException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yanghu
 * @date : Created in 2020/8/5 10:42
 * @description : 导出工具类
 * @modified By :
 * @version: : TODO
 */

public class ExportUtil {

    /**
     *
     * @param list  数据模板
     * @param codeNames   字段名
     * @param strName     字段名对应的导出表头
     * @param fileName  文件名
     * @return
     */
    public static ResponseEntity<byte[]> poiExportDate(List<?> list, String[] codeNames, String[] strName, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream();
        //指定以流的形式下载文件
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //指定文件名
        try {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = ExcelUtil.getBigWriter();
        // 定义单元格
        writer.setColumnWidth(-1, 25);
        for (int i = 0; i < codeNames.length; i++) {
            writer.addHeaderAlias(codeNames[i], strName[i]);
        }
        writer.renameSheet("StoreReviewProfile");
        // 一次性写出内容，使用默认样式
        writer.write(list, true);
        writer.flush(byteOutPutStream);
        writer.close();
        return new ResponseEntity<>(byteOutPutStream.toByteArray(), headers, HttpStatus.OK);
    }

//    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    private static List<List<Object>> lineList = new ArrayList<>();

    /**
     * excel 导出工具类
     *
     * @param response
     * @param fileName    文件名
     * @param projects    对象集合
     * @param columnNames 导出的excel中的列名
     * @param keys        对应的是对象中的字段名字
     * @throws IOException
     */
    public static void export(HttpServletResponse response, String fileName, List<?> projects, String[] columnNames, String[] keys) throws IOException {

        ExcelWriter bigWriter = ExcelUtil.getBigWriter();

        for (int i = 0; i < columnNames.length; i++) {
            bigWriter.addHeaderAlias(columnNames[i], keys[i]);
            bigWriter.setColumnWidth(i, 20);
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        bigWriter.write(projects, true);
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        bigWriter.flush(out, true);
        // 关闭writer，释放内存
        bigWriter.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * excel导入工具类
     *
     * @param file       文件
     * @param columNames 列对应的字段名
     * @return 返回数据集合
     * @throws OperationsException
     * @throws IOException
     */
    public static List<Map<String, Object>> leading(MultipartFile file, String[] columNames) throws OperationsException, IOException {
        String fileName = file.getOriginalFilename();
        // 上传文件为空
        if (StringUtils.isEmpty(fileName)) {
            throw new OperationsException("没有导入文件");
        }
        //上传文件大小为1000条数据
        if (file.getSize() > 1024 * 1024 * 10) {
//            logger.error("upload | 上传失败: 文件大小超过10M，文件大小为：{}", file.getSize());
            throw new OperationsException("上传失败: 文件大小不能超过10M!");
        }
        // 上传文件名格式不正确
        if (fileName.lastIndexOf(".") != -1 && !".xlsx".equals(fileName.substring(fileName.lastIndexOf(".")))) {
            throw new OperationsException("文件名格式不正确, 请使用后缀名为.XLSX的文件");
        }
        InputStream inputStream = file.getInputStream();
        //读取数据
        ExcelUtil.read07BySax(inputStream, 0, createRowHandler());
        //去除excel中的第一行数据
        lineList.remove(0);
        //将数据封装到list<Map>中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (null != lineList.get(i)) {
                Map<String, Object> hashMap = new HashMap<>();
                for (int j = 0; j < columNames.length; j++) {
                    Object property = lineList.get(i).get(j);
                    hashMap.put(columNames[j], property);
                }
                dataList.add(hashMap);
            } else {
                break;
            }
        }
        inputStream.close();
        return dataList;
    }

    /**
     * 通过实现handle方法编写我们要对每行数据的操作方式
     */
    private static RowHandler createRowHandler() {
        //清空一下集合中的数据
        lineList.removeAll(lineList);
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List rowlist) {
                //将读取到的每一行数据放入到list集合中
                JSONArray jsonObject = new JSONArray(rowlist);
                lineList.add(jsonObject.toList(Object.class));
            }
        };
    }

    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public static List readExcelUtil(File file) {
        InputStream is =null;
        try {
            // 创建输入流，读取Excel
            is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        innerList.add(cellinfo);
                    }
                    outerList.add(i, innerList);
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}