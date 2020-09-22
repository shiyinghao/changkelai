package com.icss.newretail.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * ydt
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的
 */
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

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

    public static Map<String, String> writeToFile(String text)
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
        fileUrl = OBSUtil.putObject(name, outputFile);
        map.put("url", fileUrl);
        outputFile.delete();
        return map;
    }

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
}
