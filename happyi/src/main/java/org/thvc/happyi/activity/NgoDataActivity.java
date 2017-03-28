package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import org.thvc.happyi.Release.NgoDataDao;
import org.thvc.happyi.Release.NgoDataText;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.EditBean;
import org.thvc.happyi.bean.TokenBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.Bimp;
import org.thvc.happyi.utils.FileUtils;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.IsIDCard;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/18.
 * NGO个人资料页面
 */
public class NgoDataActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private final static String TAG = "NgoDataActivity";
    private TextView tv_edit, tv_commit;
    private CircleImageView im_user_img;
    private EditText et_username, et_nickname, et_address, et_realname, et_orgcontact, et_orgemail, et_orgtel;

    private String imageName;
    private Uri imageUri;
    private String token = null;
    private UploadManager uploadManager;
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public String filePath = null;
    private String key;

    private String userid;
    private String headpic;

    private NgoDataDao ngoDataDao;
    private NgoDataText ngoDataTextOld;

    private String nickname;
    private String realname;
    private String address;
    private String orgcontact;
    private String orgtel;
    private String orgemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_data);
        init();
        userid = HappyiApplication.getInstance().getUserid(this);

        /**
         * 查询数据库
         */
        ngoDataDao = new NgoDataDao(NgoDataActivity.this);
        NgoDataText bgoDataText = ngoDataDao.select(userid);

        headpic = bgoDataText.getHeadpic();
        ImgUtils.setHeadImage(im_user_img, headpic);
        et_username.setText(bgoDataText.getUsername());//用户名
        et_nickname.setText(bgoDataText.getNickname());//机构名称
        et_address.setText(bgoDataText.getAddress());//地址
        et_realname.setText(bgoDataText.getRealname());//主管单位
        et_orgcontact.setText(bgoDataText.getOrgcontact());//负责人姓名
        et_orgemail.setText(bgoDataText.getOrgemail());//负责人邮箱
        et_orgtel.setText(bgoDataText.getOrgtel());//负责人电话
    }

    public void init() {
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(this);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        im_user_img = (CircleImageView) this.findViewById(R.id.im_user_img);

        et_username = (EditText) this.findViewById(R.id.et_username);//用户名
        et_nickname = (EditText) this.findViewById(R.id.et_nickname);//机构名称
        et_address = (EditText) this.findViewById(R.id.et_address);//地址
        et_realname = (EditText) this.findViewById(R.id.et_realname);//主管单位
        et_orgcontact = (EditText) this.findViewById(R.id.et_orgcontact);//联系人姓名
        et_orgemail = (EditText) this.findViewById(R.id.et_orgemail);//联系人邮箱
        et_orgtel = (EditText) this.findViewById(R.id.et_orgtel);//联系人电话

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                edit();
                break;
            case R.id.tv_commit:
                commit();
                break;
            case R.id.im_user_img:
                new PopupWindows(NgoDataActivity.this, v);
                break;
            case R.id.et_username:
                Toast.makeText(NgoDataActivity.this, "抱歉，您没有权限修改此项!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.et_address:
                Toast.makeText(NgoDataActivity.this, "抱歉，您没有权限修改此项!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.et_realname:
                Toast.makeText(NgoDataActivity.this, "抱歉，您没有权限修改此项!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 编辑资料
     */
    private void edit() {
        //"编辑"变浅色"完成"
        tv_edit.setVisibility(View.GONE);
        tv_commit.setVisibility(View.VISIBLE);
        tv_commit.setClickable(false);

        tv_edit.setTextColor(getResources().getColor(R.color.party_backgrounds));

        im_user_img.setOnClickListener(this);

        et_username.setOnClickListener(this);//用户名、机构名称、地址不能修改，不能编辑
        et_address.setOnClickListener(this);
        et_nickname.setOnClickListener(this);

        et_realname.setEnabled(true);
        et_realname.setSelection(et_realname.getText().length());//资料文本框可编辑，光标显示在末尾
        et_realname.addTextChangedListener(textWatcher);

        et_orgcontact.setEnabled(true);
        et_orgcontact.addTextChangedListener(textWatcher);

        et_orgemail.setEnabled(true);
        et_orgemail.addTextChangedListener(textWatcher);

        et_orgtel.setEnabled(true);
        et_orgtel.addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Toast.makeText(NgoDataActivity.this, "文字改变了", Toast.LENGTH_SHORT).show();
            //"完成"高亮
            tv_commit.setClickable(true);
            tv_commit.setTextColor(getResources().getColor(R.color.black));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * 完成按钮
     */
    public void commit() {
//        Toast.makeText(NgoDataActivity.this, "点击了完成，可以开始上传操作了", Toast.LENGTH_SHORT).show();
        nickname = et_nickname.getText().toString().trim();
        realname = et_realname.getText().toString().trim();
        address = et_address.getText().toString().trim();
        orgcontact = et_orgcontact.getText().toString().trim();
        orgtel = et_orgtel.getText().toString().trim();
        orgemail = et_orgemail.getText().toString().trim();
        //验收邮箱格式正确性
        if (!IsIDCard.checkEmail(orgemail)) {
            Toast.makeText(NgoDataActivity.this, R.string.email_error, Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(orgtel)) {
            Toast.makeText(NgoDataActivity.this, R.string.phone_number_error, Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("nickname", nickname);
        params.addQueryStringParameter("realname", realname);
        params.addQueryStringParameter("address", address);
        params.addQueryStringParameter("orgcontact", orgcontact);
        params.addQueryStringParameter("orgtel", orgtel);
        params.addQueryStringParameter("orgemail", orgemail);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.NGO_EDIT + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    EditBean editBean = ParseUtils.parseEditBean(result);
                    if (editBean.getStatus() == 1) {

                        ngoDataTextOld = ngoDataDao.select(userid);

                        //头像上传七牛
                        if (filePath != null) {
                            uploadQiNiu();//已经做异步处理
                            Log.e("headpic", headpic);
                            Toast.makeText(NgoDataActivity.this, getResources().getString(R.string.change_success), Toast.LENGTH_SHORT).show();
                            removeDialog();
                        } else {
                            NgoDataText ngoDataText = new NgoDataText(userid, ngoDataTextOld.getUsername(), realname, nickname, address, headpic, orgcontact, orgtel, orgemail);
                            ngoDataDao.update(userid, ngoDataText);
                            //缓存sqlite成功则"编辑"按钮出现，"完成"隐藏，文本不可点
                            tv_edit.setVisibility(View.VISIBLE);
                            tv_commit.setVisibility(View.GONE);

                            et_realname.setEnabled(false);
                            et_orgcontact.setEnabled(false);
                            et_orgemail.setEnabled(false);
                            et_orgtel.setEnabled(false);

                            Toast.makeText(NgoDataActivity.this, getResources().getString(R.string.change_success), Toast.LENGTH_SHORT).show();
                            removeDialog();
                        }
                    } else {
                        Toast.makeText(NgoDataActivity.this, editBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NgoDataActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

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
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(
                            // 相册
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                    dismiss();

                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private static final int TAKE_PICTURE = 1;
    private static final int RESULT_LOAD_IMAGE = 2;
    private static final int CUT_PHOTO_REQUEST_CODE = 3;
    private String path = "";
    private Uri photoUri;

    public void photo() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = android.os.Environment
                    .getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // 有sd卡，是否有myImage文件夹
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有headImg文件
                file = new File(sdcardPathDir + System.currentTimeMillis() + ".JPG");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == -1) {// 拍照
                    startPhotoZoom(photoUri);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK && null != data) {// 相册返回
                    Uri uri = data.getData();
                    if (uri != null) {
                        startPhotoZoom(uri);
                    }
                }
                break;
            case CUT_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {// 裁剪返回
                    Bitmap bitmap = Bimp.getLoacalBitmap(filePath);
                    im_user_img.setImageBitmap(bitmap);
                    //"完成"高亮，可点击
                    tv_commit.setClickable(true);
                    tv_commit.setTextColor(getResources().getColor(R.color.black));

                }
                break;
        }
    }


    /**
     * 图片上传七牛方法
     */
    private void uploadQiNiu() {
        uploadManager = new UploadManager();
        HttpUtils httpUtils = new HttpUtils();
        key = "Member/" + imageName;
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("key", key);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CREATE_TOKEN + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //访问获得token成功
                String result = responseInfo.result;
                TokenBean tokenBean = ParseUtils.parseTokenBean(result);
                token = tokenBean.getData().getUpToken();
                Log.e("修改头像的key", key);
                Log.e("修改头像的token", token);
                Log.e("修改头像的filePath", filePath);
                uploadManager.put(filePath, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String s, com.qiniu.android.http.ResponseInfo responseInfo, JSONObject jsonObject) {
                        Log.e("上传状态码", responseInfo.statusCode + "");
                        if (responseInfo.statusCode == 200) {
                            Log.e(TAG, "修改头像成功");
                            headpic = key;

                            //修改个人头像服务器字段
                            HttpUtils httpUtils = new HttpUtils();
                            MyRequestParams params = new MyRequestParams();
                            params.addQueryStringParameter("userid", userid);
                            params.addQueryStringParameter("headpic", headpic);
                            String url = params.myRequestParams(params);
                            httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CHANGE_HEADPIC + url, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result = responseInfo.result;
                                    Log.e(TAG, result);
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {

                                }
                            });
                            NgoDataText ngoDataText = new NgoDataText(userid, ngoDataTextOld.getUsername(), realname, nickname, address, headpic, orgcontact, orgtel, orgemail);
                            ngoDataDao.update(userid, ngoDataText);
                            //缓存sqlite成功则"编辑"按钮出现，"完成"隐藏，文本不可点
                            tv_edit.setVisibility(View.VISIBLE);
                            tv_commit.setVisibility(View.GONE);

                            et_realname.setEnabled(false);
                            et_orgcontact.setEnabled(false);
                            et_orgemail.setEnabled(false);
                            et_orgtel.setEnabled(false);
                        }
                    }
                }, null);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    private void startPhotoZoom(Uri uri) {
        try {
            imageName = userid + ".jpg";
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");
            }
            filePath = FileUtils.SDPATH + imageName;
            imageUri = Uri.parse("file:///sdcard/formats/" + imageName);
            final Intent intent = new Intent("com.android.camera.action.CROP");

            // 照片URL地址
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 输出格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
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

}
