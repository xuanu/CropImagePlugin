package com.qimon.view.cropview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qimon.view.cropview.R;
import com.qimon.view.cropview.view.ClipImageLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 裁剪图片
 * Created by 郑治玄 on 2016/6/2.
 *
 * @author zeffect  zeffect
 */
public class CropImageActivity extends Activity {
    /**
     * 传入图片路径使用的key
     **/
    public static final String Key_Key = "key";
    //
    /**
     * 控件
     **/
    private TextView back_btn, ok_btn;
    /**
     * 控件
     **/
    private ClipImageLayout mCliImage;
    /**
     * 上下亠
     **/
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropview_activity_crop_image);
        mContext = this;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        String filePath = getIntent().getStringExtra(Key_Key);
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(mContext, R.string.cropview_file_dont_exist, Toast.LENGTH_SHORT).show();
            finish();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(mContext, R.string.cropview_file_dont_exist, Toast.LENGTH_SHORT).show();
            finish();
        }
        back_btn = (TextView) findViewById(R.id.aci_back_btn);
        ok_btn = (TextView) findViewById(R.id.aci_ok_btn);
        mCliImage = (ClipImageLayout) findViewById(R.id.aci_crop_cil);
        //加载图片不缓存，不然下次加载会是重复图片
        mCliImage.getZoomImageView().setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        //
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCancel();
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 保存图片
     */
    private void saveFile() {
        Bitmap bitmap = mCliImage.clip();
        try {
            File file = new File(getExternalCacheDir(), "cache_photo" + System.currentTimeMillis() + ".jgp");
            if (!file.exists()) {
                file.createNewFile();
            }
            saveBitmap4Bitmap(file, bitmap, 100);
            backOk(file);
        } catch (IOException e) {
            backCancel();
        }
    }

    /**
     * 按百分百保存Bitmap图片。
     *
     * @param file   文件路径加文件名
     * @param bitmap 待保存的bitmap图片。
     * @param score  压缩比（1<=score<=100），值越大，图片质量越高。
     * @throws IOException
     */
    public static void saveBitmap4Bitmap(File file, Bitmap bitmap, int score) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        // out.write(bitmap2Bytes(bitmap));
        bitmap.compress(Bitmap.CompressFormat.JPEG, score, out);
        out.flush();
        out.close();
    }

    /**
     * 返回成功
     *
     * @param file 文件
     */
    private void backOk(File file) {
        setResult(RESULT_OK, new Intent().putExtra(Key_Key, file.getAbsolutePath()));
        finish();
    }

    @Override
    public void onBackPressed() {
        backCancel();
    }

    /**
     * 返回取消
     */
    private void backCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
