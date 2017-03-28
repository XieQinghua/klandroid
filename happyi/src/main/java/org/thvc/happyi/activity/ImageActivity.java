package org.thvc.happyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.IntentConstants;
import org.thvc.happyi.view.ImgerView.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/25.
 */
public class ImageActivity extends BaseActivity {
    private ArrayList<String> picList;
    private TextView tv_count;
    private ViewPager myViewPager;
    private int currentPosition;
    private TextView tv_save;
    private String myJpgPath;
    private int page = 0;
    private int window_width, window_height;// 控件宽度
    private int state_height;// 状态栏的高度
    private ViewTreeObserver viewTreeObserver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        picList = getIntent().getStringArrayListExtra(IntentConstants.EXTRA_IMAGE_LIST);
        currentPosition = getIntent().getIntExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, 0);
        tv_count = (TextView) this.findViewById(R.id.tv_count);
        tv_save = (TextView) this.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new MyOnClickListener());
        myViewPager = (ViewPager) this.findViewById(R.id.vp_party_scene);
        if (picList != null && picList.size() != 0) {
            tv_count.setText("第" + (currentPosition + 1) + "张/共" + picList.size() + "张");
            myViewPager.setAdapter(new MyPagerAdapter());
            myViewPager.setCurrentItem(currentPosition);
            myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    tv_count.setText("第" + (position + 1) + "张/共" + picList.size() + "张");
                    currentPosition = position;
                    myJpgPath = picList.get(position) + ".png";
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(ImageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

            }
        }

    };

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(ImageActivity.this, "正在保存", Toast.LENGTH_SHORT).show();
            myJpgPath = picList.get(page);
            myJpgPath = myJpgPath.substring(myJpgPath.indexOf("/"));
            new Thread() {
                @Override
                public void run() {
                    String urlPath = HappyiApi.QINIU + picList.get(page);
                    Bitmap tmpBitmap = null;
                    try {
                        InputStream is = new java.net.URL(urlPath).openStream();
                        tmpBitmap = BitmapFactory.decodeStream(is);
                        saveMyBitmap(tmpBitmap);
                        is.close();
                        handler.sendEmptyMessage(1);
                        Log.e("saveMyBitmap", "保存成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }


    /**
     * 保存图片到SD卡
     *
     * @param mBitmap
     * @return void
     * @throws IOException
     * @since v 1.0
     */
    public void saveMyBitmap(Bitmap mBitmap)
            throws IOException {
        File tmp = new File("/sdcard/happyi");
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        File f = new File("/sdcard/happyi", myJpgPath);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(tmp);
            intent.setData(uri);
            sendBroadcast(intent);//这个广播的目的就是更新图库
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义pageradapter  适配viewpager
     */
    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        /**
         * 根据指定的下标 创建viewpager中展示的item  返回当前page中的view对象
         * 第一个参数表示 当前管理page的视图组
         * 第二个参数表示 指定下标
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ImgUtils.setRectangleImageRaw(photoView, picList.get(position));
            ((ViewPager) container).addView(photoView);
            return photoView;
        }

        /**
         * 根据指定的下标移除视图组中的view对象
         * 第一个参数表示 视图组对象
         * 第二个参数表示 当前移除的视图的下标
         * 第三个参数表示 instantiateItem 返回的object对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //根据指定的下标获得当前移除的view对象 然后在视图组中移除
            ((ViewPager) container).removeView((View) object);
        }

        /**
         * 表示判断viewpager中展示的view对象与instantiateItem对象是否时同一个对象
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
