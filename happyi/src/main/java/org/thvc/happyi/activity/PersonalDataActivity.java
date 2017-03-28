package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
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
import org.thvc.happyi.Release.DataDao;
import org.thvc.happyi.Release.DataText;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/17.
 * 个人资料页面
 */
public class PersonalDataActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private final static String TAG = "PersonalData";
    private TextView tv_edit, tv_commit;
    private CircleImageView im_user_img;
    private EditText et_nickname, et_realname, et_email, et_mobile, et_job, et_content, et_idcard;
    private TextView tv_sex, tv_birthday;

    private String imageName;
    private Uri imageUri;
    private String token = null;
    private UploadManager uploadManager;
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public String filePath = null;
    private String key;

    private String userid;
    private String headpic;

    private DataDao dataDao;
    private DataText dataTextOld;

    private String nickname;
    private String realname;
    private int sex;
    private String birthday;
    private String age;
    private String email;
    private String job;
    private String content;
    private String idcard;
    private String iden_info;//身份证验证信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        init();
        userid = HappyiApplication.getInstance().getUserid(this);
        /**
         * 查询数据库
         */
        dataDao = new DataDao(PersonalDataActivity.this);
        DataText dataText = dataDao.select(userid);

        headpic = dataText.getHeadpic();
        ImgUtils.setHeadImage(im_user_img, headpic);
        et_nickname.setText(dataText.getNickname());
        et_realname.setText(dataText.getRealname());
        if (dataText.getSex().equals("1")) {
            tv_sex.setText("男");
        } else {
            tv_sex.setText("女");
        }
        tv_birthday.setText(dataText.getBirthday());
        et_email.setText(dataText.getEmail());
        et_mobile.setText(HappyiApplication.getInstance().getUsername(this));//这里取Username，预防用户直接第三方登录去不到值
        et_job.setText(dataText.getJob());
        et_content.setText(dataText.getContent());
        et_idcard.setText(dataText.getIdcard());
    }

    /**
     * 初始化
     */
    public void init() {
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(this);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        im_user_img = (CircleImageView) this.findViewById(R.id.im_user_img);

        et_nickname = (EditText) this.findViewById(R.id.et_nickname);
        et_realname = (EditText) this.findViewById(R.id.et_realname);
        tv_sex = (TextView) this.findViewById(R.id.tv_sex);
        tv_birthday = (TextView) this.findViewById(R.id.tv_birthday);
        et_email = (EditText) this.findViewById(R.id.et_email);
        et_mobile = (EditText) this.findViewById(R.id.et_mobile);
        et_job = (EditText) this.findViewById(R.id.et_job);
        et_content = (EditText) this.findViewById(R.id.et_content);
        et_idcard = (EditText) this.findViewById(R.id.et_idcard);
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
                new PopupWindows(PersonalDataActivity.this, v);
                break;
            case R.id.tv_sex:
                showDialog(tv_sex);
                break;
            case R.id.tv_birthday:
                showDialog(tv_birthday);
                break;
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
        //资料文本框可编辑
        et_nickname.setEnabled(true);
        et_nickname.setSelection(et_nickname.getText().length());//光标显示在末尾
        et_nickname.addTextChangedListener(textWatcher);//监听文本变化

        et_realname.setEnabled(true);
        et_realname.addTextChangedListener(textWatcher);

        et_email.setEnabled(true);
        et_email.addTextChangedListener(textWatcher);

//        et_mobile.setEnabled(true);
//        et_mobile.addTextChangedListener(textWatcher);//联系电话为用户登录账号，不可更改

        et_job.setEnabled(true);
        et_job.addTextChangedListener(textWatcher);

        et_content.setEnabled(true);
        et_content.addTextChangedListener(textWatcher);

        et_idcard.setEnabled(true);
        et_idcard.addTextChangedListener(textWatcher);

        tv_sex.setOnClickListener(this);
        tv_sex.addTextChangedListener(textWatcher);
        tv_birthday.setOnClickListener(this);
        tv_birthday.addTextChangedListener(textWatcher);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Toast.makeText(PersonalDataActivity.this, "文字改变了", Toast.LENGTH_SHORT).show();
            //"完成"高亮
            tv_commit.setClickable(true);
            tv_commit.setTextColor(getResources().getColor(R.color.black));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    /**
     * 弹出对话框，弹出性别选择框和日期选择对话框
     *
     * @param view
     */
    String[] items = {"男", "女"};
    int index = 0;

    public void showDialog(final View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        switch (view.getId()) {
            case R.id.tv_sex:
                //选择性别对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setSingleChoiceItems(items, index, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index = which;
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_sex.setText(items[index]);
                    }
                });
                builder.show();
                break;
            case R.id.tv_birthday:
                //日期选择对话框
                DatePickerDialog dpd = new DatePickerDialog(PersonalDataActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_birthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;
        }
    }

    /**
     * 完成按钮
     */
    public void commit() {
//        Toast.makeText(PersonalDataActivity.this, "点击了完成，可以开始上传操作了", Toast.LENGTH_SHORT).show();

        nickname = et_nickname.getText().toString().trim();
        realname = et_realname.getText().toString().trim();
        if (tv_sex.getText().toString().trim().equals("男")) {
            sex = 1;
        } else {
            sex = 2;
        }
        birthday = tv_birthday.getText().toString().trim();
        email = et_email.getText().toString().trim();
        job = et_job.getText().toString().trim();
        content = et_content.getText().toString().trim();
        idcard = et_idcard.getText().toString().trim();
        //验证昵称
        if (nickname.length() < 2 || IsIDCard.isConSpeCharacters(nickname)) {
            Toast.makeText(PersonalDataActivity.this, R.string.enter_correct_nickname, Toast.LENGTH_SHORT).show();
            return;
        }
        //验收邮箱格式正确性
        if (!email.equals("") && !IsIDCard.checkEmail(email)) {
            Toast.makeText(PersonalDataActivity.this, R.string.email_error, Toast.LENGTH_SHORT).show();
            return;
        }

        //判断身份证号码正确性
        try {
            iden_info = IsIDCard.IDCardValidate(idcard);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!idcard.equals("") && !iden_info.equals("")) {
            Toast.makeText(PersonalDataActivity.this, iden_info, Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("nickname", nickname);
        params.addQueryStringParameter("realname", realname);
        params.addQueryStringParameter("sex", sex + "");
        params.addQueryStringParameter("birthday", birthday);
        params.addQueryStringParameter("email", email);
        params.addQueryStringParameter("job", job);
        params.addQueryStringParameter("content", content);
        params.addQueryStringParameter("idcard", idcard);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.ORDINARY_EDIT + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    EditBean editBean = ParseUtils.parseEditBean(result);
                    if (editBean.getStatus() == 1) {
                        dataTextOld = dataDao.select(userid);
                        //头像上传七牛
                        if (filePath != null) {
                            uploadQiNiu();//已经做异步处理
                            Log.e("headpic", headpic);
                            Toast.makeText(PersonalDataActivity.this, getResources().getString(R.string.change_success), Toast.LENGTH_SHORT).show();
                            removeDialog();
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                            int year = Integer.parseInt(sdf.format(System.currentTimeMillis()));
                            age = (year - Integer.parseInt(birthday.substring(0, 4))) + "";
                            DataText dataText = new DataText(userid, dataTextOld.getSystem(), dataTextOld.getUsername(), dataTextOld.getMobile(),
                                    nickname, age, birthday, sex + "", content, headpic, realname, email, job, idcard);
                            dataDao.update(userid, dataText);
                            //缓存sqlite成功则"编辑"按钮出现，"完成"隐藏，文本不可点
                            tv_edit.setVisibility(View.VISIBLE);
                            tv_commit.setVisibility(View.GONE);

                            et_nickname.setEnabled(false);
                            et_realname.setEnabled(false);
                            et_email.setEnabled(false);
                            et_mobile.setEnabled(false);
                            et_job.setEnabled(false);
                            et_content.setEnabled(false);
                            et_idcard.setEnabled(false);
                            tv_sex.setClickable(false);
                            tv_birthday.setClickable(false);
                            Toast.makeText(PersonalDataActivity.this, getResources().getString(R.string.change_success), Toast.LENGTH_SHORT).show();
                            removeDialog();
                        }
                    } else {
                        Toast.makeText(PersonalDataActivity.this, editBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PersonalDataActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    TokenBean tokenBean = ParseUtils.parseTokenBean(result);
                    token = tokenBean.getData().getUpToken();
                    Log.e(TAG, key);
                    Log.e(TAG, token);
                    Log.e(TAG, filePath);
                    uploadManager.put(filePath, key, token, new UpCompletionHandler() {
                        @Override
                        public void complete(String s, com.qiniu.android.http.ResponseInfo responseInfo, JSONObject jsonObject) {
                            Log.e("上传状态码", responseInfo.statusCode + "");
                            if (responseInfo.statusCode == 200) {
                                Log.e(TAG, "上传头像成功");
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
                                        LogUtils.i(s);
                                    }
                                });
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                                int year = Integer.parseInt(sdf.format(System.currentTimeMillis()));
                                age = (year - Integer.parseInt(birthday.substring(0, 4))) + "";
                                DataText dataText = new DataText(userid, dataTextOld.getSystem(), dataTextOld.getUsername(), dataTextOld.getMobile(),
                                        nickname, age, birthday, sex + "", content, headpic, realname, email, job, idcard);
                                dataDao.update(userid, dataText);
                                //缓存sqlite成功则"编辑"按钮出现，"完成"隐藏，文本不可点
                                tv_edit.setVisibility(View.VISIBLE);
                                tv_commit.setVisibility(View.GONE);

                                et_nickname.setEnabled(false);
                                et_realname.setEnabled(false);
                                et_email.setEnabled(false);
                                et_mobile.setEnabled(false);
                                et_job.setEnabled(false);
                                et_content.setEnabled(false);
                                et_idcard.setEnabled(false);
                                tv_sex.setClickable(false);
                                tv_birthday.setClickable(false);
                            }
                        }
                    }, null);
                } else {
                    Toast.makeText(PersonalDataActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                }
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
