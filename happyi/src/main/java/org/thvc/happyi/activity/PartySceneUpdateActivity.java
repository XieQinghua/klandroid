package org.thvc.happyi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;
import org.thvc.happyi.R;
import org.thvc.happyi.Release.PartySceneImage;
import org.thvc.happyi.Release.PartySceneImageDao;
import org.thvc.happyi.Release.PartySceneText;
import org.thvc.happyi.Release.PartySceneTextDao;
import org.thvc.happyi.adapter.ImagePublishAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.PartyAndNewBean;
import org.thvc.happyi.bean.TokenBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.CustomConstants;
import org.thvc.happyi.utils.FileUtils;
import org.thvc.happyi.utils.ImageItem;
import org.thvc.happyi.utils.IntentConstants;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/12/11.
 * 活动现场更新页面
 */
public class PartySceneUpdateActivity extends BaseSwipeBackActivity {

    private EditText et_track_text;
    private TextView tv_submit;


    private static final int TAKE_PICTURE = 1;

    private String path = "";
    private List<Bitmap> bmp = new ArrayList<Bitmap>();

    private UploadManager uploadManager;
    private String token = null;
    private String userid;
    private String dataid;
    private HttpUtils httpUtils;
    private String content;

    private GridView mGridView;
    private ImagePublishAdapter mAdapter;
    public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
    private String keys;
    private String footprintid;
    private ImageView im_back;
    private String tracktext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partyscene_update);
        userid = HappyiApplication.getInstance().getUserid(this);
        dataid = getIntent().getStringExtra("dataid");
        tracktext = getIntent().getStringExtra("tracktext");
        initData();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
    }

    protected void onPause() {
        super.onPause();
        saveTempToPref();
    }

    private void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveTempToPref();
    }

    private void saveTempToPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = JSON.toJSONString(mDataList);
        sp.edit().putString(CustomConstants.PREF_TEMP_IMAGES, prefStr).commit();
    }

    private void getTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
        if (!TextUtils.isEmpty(prefStr)) {
            List<ImageItem> tempImages = JSON.parseArray(prefStr,
                    ImageItem.class);
            mDataList = tempImages;
        }
    }


    @SuppressWarnings("unchecked")
    private void initData() {
        getTempFromPref();
        List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
                .getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
        if (incomingDataList != null) {
            mDataList.addAll(incomingDataList);
        }
    }

    private void removeTempFromPref() {
        SharedPreferences sp = getSharedPreferences(
                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
    }

    public void init() {
        et_track_text = (EditText) this.findViewById(R.id.et_track_text);
        if (et_track_text.getText().toString().trim().equals("")) {
            et_track_text.setText(tracktext);
        }
        tv_submit = (TextView) this.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new MyOnClickListener());
        im_back = (ImageView) this.findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mDataList.clear();
            }
        });
        mGridView = (GridView) findViewById(R.id.gv_gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImagePublishAdapter(this, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == getDataSize()) {
                    new PopupWindows(PartySceneUpdateActivity.this, mGridView);
                } else {
                    Intent intent = new Intent(PartySceneUpdateActivity.this,
                            ImageZoomActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,
                            (Serializable) mDataList);
                    intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, position);
                    startActivity(intent);
                }
            }
        });
    }


    private int getDataSize() {
        return mDataList == null ? 0 : mDataList.size();
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_submit:
                    content = et_track_text.getText().toString().trim();
                    if (content.equals("")) {
                        Toast.makeText(PartySceneUpdateActivity.this, "请编辑文字内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mDataList.size() <= 0) {
                        Toast.makeText(PartySceneUpdateActivity.this, "请选需要发布的图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    removeTempFromPref();
                    footprintid = String.valueOf(getTaskId() + System.currentTimeMillis());
                    String getstatus = String.valueOf(0);
                    String pathlist = "";

                    PartySceneText partySceneText = new PartySceneText(userid, footprintid, pathlist, getstatus);
                    PartySceneTextDao partySceneTextDao = new PartySceneTextDao(PartySceneUpdateActivity.this);
                    partySceneTextDao.textInsert(partySceneText);

                    for (int i = 0; i < mDataList.size(); i++) {
                        String sourcePath = mDataList.get(i).sourcePath.toString(); //+ String.valueOf(System.currentTimeMillis());
                        keys = String.valueOf("Party/" + System.currentTimeMillis()) + sourcePath.substring(sourcePath.lastIndexOf("/") + 1);

                        SimpleDateFormat formatters = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                        Date curDates = new Date(System.currentTimeMillis());//获取当前时间
                        String strDates = formatters.format(curDates);

                        PartySceneImage partySceneImage = new PartySceneImage(userid, footprintid, sourcePath, keys, getstatus, strDates);
                        PartySceneImageDao partySceneImageDao = new PartySceneImageDao(PartySceneUpdateActivity.this);
                        partySceneImageDao.imageInsert(partySceneImage);
                    }
                    uploadQiNiu();
                    break;
            }

        }
    }


    /**
     * 图片上传七牛方法
     */
    public void uploadQiNiu() {
        showDialog(LOADING_DIALOG);
        PartySceneImageDao partySceneImageDao = new PartySceneImageDao(PartySceneUpdateActivity.this);
        PartySceneImage partySceneImage = partySceneImageDao.select(userid, footprintid);
        if (partySceneImage != null) {
            final String status = partySceneImage.getStatus().toString();
            final String key = partySceneImage.getPathlist().toString();
            final String filePath = partySceneImage.getFilePath().toString();
            if (status.equals("0") && !status.equals("3")) {
                uploadManager = new UploadManager();
                HttpUtils httpUtils = new HttpUtils();
                MyRequestParams params = new MyRequestParams();
                params.addQueryStringParameter("key", key);
                String url = params.myRequestParams(params);
                httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CREATE_TOKEN + url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        if (result != null) {
                            TokenBean tokenBean = ParseUtils.parseTokenBean(result);
                            token = tokenBean.getData().getUpToken();
                            uploadManager.put(filePath, key, token, new UpCompletionHandler() {
                                @Override
                                public void complete(String s, com.qiniu.android.http.ResponseInfo responseInfo, JSONObject jsonObject) {
                                    if (responseInfo.statusCode == 200) {
                                        PartySceneTextDao partySceneTextDao = new PartySceneTextDao(PartySceneUpdateActivity.this);
                                        PartySceneText partySceneText = partySceneTextDao.select(userid, footprintid);
                                        String pathlist = partySceneText.getPathlist();

                                        String filekeys;
                                        filekeys = "";
                                        filekeys += pathlist + key + ",";

                                        partySceneTextDao.textUpdate(userid, footprintid, filekeys);

                                        String statuss = String.valueOf(3);
                                        PartySceneImageDao partySceneImageDao = new PartySceneImageDao(PartySceneUpdateActivity.this);
                                        partySceneImageDao.imageUpdate(userid, filePath, statuss);
                                        uploadQiNiu();
                                    }
                                }
                            }, null);

                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
            } else if (status.equals("3")) {
                Toast.makeText(PartySceneUpdateActivity.this, "完成上传", Toast.LENGTH_SHORT).show();
            }
        } else {
            PartyAndNew();
        }

    }

    /**
     * 上传活动现场数据
     */
    public void PartyAndNew() {
        PartySceneTextDao partySceneTextDao = new PartySceneTextDao(PartySceneUpdateActivity.this);
        PartySceneText partySceneText = partySceneTextDao.select(userid, footprintid);
        String pic = partySceneText.getPathlist();

        httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("dataid", dataid);
        params.addQueryStringParameter("content", content);
        params.addQueryStringParameter("pic", pic);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CONTENT_ADD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    PartyAndNewBean partyAndNewBean = ParseUtils.parsePartyAndNewBean(result);
                    if (partyAndNewBean.getStatus() == 1) {
                        Toast.makeText(PartySceneUpdateActivity.this, "活动更新成功", Toast.LENGTH_SHORT).show();
                        mDataList.clear();
                        PartySceneUpdateActivity.this.finish();
                        removeDialog(0);
                    }
                } else {
                    Toast.makeText(PartySceneUpdateActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popup_choose_pic, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            //修改高度显示，解决被手机底部虚拟键挡住的问题
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //实例化一个ColorDrawable颜色为半透明
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.ll_popup).getTop();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            update();
            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);//拍照按钮
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);//选取照片按钮
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);//取消按钮
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PartySceneUpdateActivity.this, ImageBucketChooseActivity.class);
                    intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize());
                    intent.putExtra("dataid", dataid);
                    intent.putExtra("tracktext", et_track_text.getText().toString().trim());
                    startActivity(intent);
                    dismiss();
                    PartySceneUpdateActivity.this.finish();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private int getAvailableSize() {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE
                        && resultCode == -1 && !TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        FileUtils.deleteDir(FileUtils.SDPATH);
        FileUtils.deleteDir(FileUtils.SDPATH1);
        // 清理图片缓存
        for (int i = 0; i < bmp.size(); i++) {
            bmp.get(i).recycle();
        }
        bmp.clear();
        super.onDestroy();
    }

    /**
     * 监听放回按钮
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //假如按的回退键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            mDataList.clear();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
