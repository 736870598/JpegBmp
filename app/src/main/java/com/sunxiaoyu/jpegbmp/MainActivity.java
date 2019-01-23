package com.sunxiaoyu.jpegbmp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.medivh.libjepgturbo.jepgcompress.ImageCompress;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textview);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            new MyTask().execute(uri);
        }
    }

    private class MyTask extends AsyncTask<Uri, String, Boolean>{

        private String savePath;
        private String info;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            info = "";
            tv.setText("开始处理。。。");
        }

        @Override
        protected Boolean doInBackground(Uri... params) {
            savePath =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/hfmBmp";
            File file = new File(savePath);
            if (!file.exists()){
                file.mkdirs();
            }
            savePath = savePath + "/" + System.currentTimeMillis() + ".jpg";

            String bitmapPath =PathUtils.getPath(MainActivity.this, params[0]);
            info += "原图位置：" + bitmapPath + "\n";
            info += "原图大小：" + new File(bitmapPath).length() + "\n";
            Bitmap oldBitmap = BitmapFactory.decodeFile(bitmapPath);
            info += "原图宽高：" + oldBitmap.getWidth() + "*" + oldBitmap.getHeight() + "\n";
            info += "\n开始压缩。。。\n\n";
            long startTemp = System.currentTimeMillis();

            boolean result = ImageCompress.saveBitmap(oldBitmap, savePath);

            info += "压缩完成，状态："+result+ "  , 耗时: " + (System.currentTimeMillis() - startTemp) + "\n\n";
            oldBitmap.recycle();
            if (result){
                info += "压缩后位置：" + (savePath) + "\n";
                info += "压缩后大小："+ new File(savePath).length() + "\n";
                Bitmap newBitmap = BitmapFactory.decodeFile(savePath);
                info += "压缩后宽高：" + newBitmap.getWidth() + "*" + newBitmap.getHeight() + "\n";
                newBitmap.recycle();
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            tv.setText(info);
        }
    }

}
