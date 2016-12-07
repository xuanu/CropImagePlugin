package plugins.zeffect.cn.cropimageplugin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qimon.view.cropview.activity.CropImageActivity;

import static android.app.Activity.RESULT_CANCELED;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.showimage);
        Button tempButton = (Button) findViewById(R.id.button);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });
    }

    /**
     * 从本地相册?取图片作为头像
     */
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentFromGallery, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 100:
            case 101:
                String filePath2 = getGalleryPath(MainActivity.this, intent.getData());
                startActivityForResult(new Intent(this, CropImageActivity.class).putExtra(CropImageActivity.Key_Key, filePath2), 102);
                break;
            case 102:
                mImageView.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra(CropImageActivity.Key_Key)));
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 获取图片真实路径，通用，但选择图片只能用Intent.ACTION_PICK
     *
     * @param pUri
     * @return
     */
    public static String getGalleryPath(Context pContext, Uri pUri) {
        String localPath = "";
        Cursor cr = pContext.getContentResolver().query(pUri,
                new String[]{MediaStore.Images.Media.DATA}, null,
                null, null);
        if (cr == null) {
            return localPath;
        }
        if (cr.moveToFirst()) {
            localPath = cr.getString(cr
                    .getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cr.close();
        return localPath;
    }
}
