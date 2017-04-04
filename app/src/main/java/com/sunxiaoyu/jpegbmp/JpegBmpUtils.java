package com.sunxiaoyu.jpegbmp;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/3/3/003.
 */

public class JpegBmpUtils {

    /**
     * 压缩图片
     * @param bmp           图片
     * @param savePath      保持路径
     * @param quality       压缩比例（0-100）
     * @return   处理结果
     */
    public static String compressBmp(Bitmap bmp, String savePath, int quality){
        return nativeCompressBmp(bmp, bmp.getWidth(), bmp.getHeight(), quality, savePath);
    }



    static {
        System.loadLibrary("native-lib");
    }

    public static native String nativeCompressBmp(Bitmap bmp, int w, int h, int quality, String path);




}
