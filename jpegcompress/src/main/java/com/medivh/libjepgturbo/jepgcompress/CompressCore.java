package com.medivh.libjepgturbo.jepgcompress;

import android.graphics.Bitmap;

/**
 * 图片压缩
 * @author medivh
 * @version V1.0.0
 */

public class CompressCore {

    /**
     * 调用native方法
     * @param bit bitmap
     * @param quality 压缩率
     * @param fileName 文件名
     * @param optimize 是否启用哈希曼算法
     * @return result 1表示成功 其它表示失败
     */
    public static String saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        return compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
    }

    /**
     * 调用底层 bitherlibjni.c中的方法
     * @param bit bitmap
     * @param w 宽
     * @param h 高
     * @param quality 压缩率
     * @param fileNameBytes 文件名大小
     * @param optimize 是否启用哈希曼算法压缩
     * @return result 1表示成功 其它表示失败
     */
    private static native String compressBitmap(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes,
                                                boolean optimize);
    //加载lib下两个so文件
    static {
        try {
            System.loadLibrary("jpeg");
            System.loadLibrary("bitherjni");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
