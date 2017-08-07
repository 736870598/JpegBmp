package com.sunxiaoyu.jpegbmp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageview);
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
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                new MyTask().execute(bitmap);


//                String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1111.jpg";
//
//                String result = JpegBmpUtils.compressBmp(bitmap, savePath, 20);
//
//                if("".equals(result)){
//                    bitmap = BitmapFactory.decodeFile(savePath);
//                    iv.setImageBitmap(bitmap);
//                    tv.setText("\n压缩成功，图片保存在：" + savePath);
//                }else{
//                    tv.setText("\n结果：" + result);
//                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyTask extends AsyncTask<Bitmap, String, String>{

        private ProgressDialog pd;
        private String savePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("保存图片中，请稍等...");
            pd.show();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            savePath =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/hfmBmp";
            File file = new File(savePath);
            if (!file.exists()){
                file.mkdirs();
            }
            savePath = savePath + "/" + System.currentTimeMillis() + ".jpg";

            Bitmap bitmap = params[0];
            Log.v("hfmBmp---", "count: " + getBitmapSize(bitmap));


            long startTemp = System.currentTimeMillis();
            String result = JpegBmpUtils.compressBmp(params[0], savePath, 20);
            long elapsedTemp = System.currentTimeMillis() - startTemp;
            Log.v("hfmBmp---", "压缩图片消耗时长：" + elapsedTemp + " , 保存路径：" + savePath);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd != null && pd.isShowing()){
                pd.dismiss();
            }

            if ("".equals(result)){
                tv.setText("\n压缩成功，图片保存在：" + savePath);
                iv.setImageBitmap( BitmapFactory.decodeFile(savePath));
            }else{
                tv.setText("\n结果：" + result);
            }
        }
    }



    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

}
