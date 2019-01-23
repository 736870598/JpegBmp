package com.medivh.libjepgturbo.jepgcompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * 压缩图片
 */
public class ImageCompress{

    public static boolean saveBitmap(String bitmapPath, String savePath) {
       return saveBitmap(bitmapPath, savePath, 25);
    }

    public static boolean saveBitmap(String bitmapPath, String savePath,  int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath);
        boolean result = false;
        if (bitmap != null){
            result = saveBitmap(bitmap, savePath, quality,true);
            bitmap.recycle();
        }
        return result;
    }

    public static boolean saveBitmap(Bitmap bit, String savePath) {
        return saveBitmap(bit, savePath, 25,true);
    }

    public static boolean saveBitmap(Bitmap bit, String savePath, int quality) {
        return saveBitmap(bit, savePath, quality, true);
    }

    public static boolean saveBitmap(Bitmap bit, String savePath, int quality,  boolean optimize) {
        if (bit == null){
            return false;
        }
        if (quality <= 0 || quality > 100){
            quality = 30;
        }
        String result = CompressCore.saveBitmap(bit, quality, savePath, optimize);
        return TextUtils.isEmpty(result) || result.equals("1");
    }

}