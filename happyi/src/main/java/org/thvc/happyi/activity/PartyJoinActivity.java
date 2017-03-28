package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import org.thvc.happyi.bean.PartyRegistrationBean;
import org.thvc.happyi.bean.TokenBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.Bimp;
import org.thvc.happyi.utils.FileUtils;
import org.thvc.happyi.utils.IsIDCard;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/27.
 * 活动报名页面
 */
public class PartyJoinActivity extends BaseSwipeBackActivity {
    private static final String TAG = "PartyJoin";
    private EditText et_realname, et_job, et_age, et_tel, et_identity;
    private LinearLayout ll_person_join, ll_team_join;
    private TextView tv_sex;
    private Button btn_Next_step;
    private String userid;//用户编号
    private String type;//报名类型
    private String dataid;//活动编号
    private String isjoin;//是否参加活动
    private String payfee;//活动费用
    private String realname;//姓名
    private String job;//职业
    private String tel;//电话
    private RelativeLayout rl_identity_info;//身份证信息布局
    private RelativeLayout rl_pay_info;//费用信息布局
    private RelativeLayout rl_pic_info;//照片布局信息
    private ImageView iv_pic;
    private TextView tv_actual_money, tv_original_money, tv_save_money;
    private int sex;
    private String age;
    private String getpre;
    private String title;
    private String fundname;
    private String idcard;//身份证号码
    private String iden_info;//身份证验证信息
    private String safe;
    private boolean needPic;

    private String imageName;
    private Uri imageUri;
    private String token = null;
    private UploadManager uploadManager;
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public String filePath = null;
    private String key;
    private String headpic = null;

    private DataDao dataDao;
    private DataText dataText;

    private PartyRegistrationBean parsePartyRegistrationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_join);
        init();
        userid = HappyiApplication.getInstance().getUserid(this);
        type = getIntent().getStringExtra("type");
        dataid = getIntent().getStringExtra("actid");
        payfee = getIntent().getStringExtra("prefee");
        getpre = getIntent().getStringExtra("getpre");
        fundname = getIntent().getStringExtra("fundname");
        title = getIntent().getStringExtra("title");
        safe = getIntent().getStringExtra("safe");
        needPic = getIntent().getBooleanExtra("needpic", false);
        if (safe.equals("1")) {
            rl_identity_info.setVisibility(View.VISIBLE);
        }
        if (needPic) {
            rl_pic_info.setVisibility(View.VISIBLE);
            iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PopupWindows(PartyJoinActivity.this, v);
//                    Toast.makeText(PartyJoinActivity.this, "需要上传一张照片", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btn_Next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinParty();
            }
        });
        /**
         * 查询数据库
         */
        dataDao = new DataDao(PartyJoinActivity.this);
        dataText = dataDao.select(userid);
        realname = dataText.getRealname();
        initData();
    }


    private void init() {
        rl_pay_info = (RelativeLayout) findViewById(R.id.rl_pay_info);
        tv_actual_money = (TextView) findViewById(R.id.tv_actual_money);
        tv_original_money = (TextView) findViewById(R.id.tv_original_money);
        tv_original_money.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        tv_save_money = (TextView) findViewById(R.id.tv_save_money);
        btn_Next_step = (Button) this.findViewById(R.id.btn_Next_step);

        ll_person_join = (LinearLayout) findViewById(R.id.ll_person_join);
        ll_team_join = (LinearLayout) findViewById(R.id.ll_team_join);

        et_realname = (EditText) findViewById(R.id.et_realname);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_age = (EditText) findViewById(R.id.et_age);
        et_job = (EditText) findViewById(R.id.et_job);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        final String[] items = {"男", "女"};
        final int[] index = {0};
        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择性别对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(PartyJoinActivity.this);
                builder.setSingleChoiceItems(items, index[0], new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        index[0] = which;
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_sex.setText(items[index[0]]);
                    }
                });
                builder.show();
            }
        });
        rl_identity_info = (RelativeLayout) findViewById(R.id.rl_identity_info);
        rl_pic_info = (RelativeLayout) findViewById(R.id.rl_pic_info);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        et_identity = (EditText) findViewById(R.id.et_identity);
    }

    private void initData() {
        if (type.equals("person")) {
            ll_person_join.setVisibility(View.VISIBLE);
        } else if (type.equals("family")) {
            //家庭报名布局页面
        } else if (type.equals("team")) {
            ll_team_join.setVisibility(View.VISIBLE);
        }
        double cost = Double.parseDouble(payfee) - Double.parseDouble(getpre);
        tv_actual_money.setText("￥" + (int) cost);//人均费用，计算得出，取整数值
        tv_original_money.setText("原价" + payfee + "元/人");
        tv_original_money.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        tv_save_money.setText(fundname + "为你节省了" + getpre + "元");
        if (realname != null) {
            et_realname.setText(dataText.getRealname());//此处为真实姓名
        } else {
            et_realname.setText("");//此处为真实姓名
        }
        et_realname.setSelection(et_realname.getText().length());//光标位于文字后面
        if (dataText.getMobile() != null) {
            et_tel.setText(dataText.getMobile());
        }
        if (dataText.getAge() != null) {
            et_age.setText(dataText.getAge());
        }

        if (dataText.getSex() != null && dataText.getSex().equals("1")) {
            tv_sex.setText("男");
        } else {
            tv_sex.setText("女");
        }
        if (dataText.getJob() != null) {
            et_job.setText(dataText.getJob());
        }
        if (safe.equals("1")) {
            et_identity.setText(dataText.getIdcard());
        }
    }

    /**
     * 报名活动
     */
    private void joinParty() {
        realname = et_realname.getText().toString().trim();
        tel = et_tel.getText().toString().trim();
        age = et_age.getText().toString().trim();

        if (tv_sex.getText().toString().trim().equals("男")) {
            sex = 1;
        } else if (tv_sex.getText().toString().trim().equals("女")) {
            sex = 2;
        } else {
            Toast.makeText(PartyJoinActivity.this, "性别输入不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        job = et_job.getText().toString().trim();
        idcard = et_identity.getText().toString().trim();

        //判断身份证号码正确性，有效：返回"" 无效：返回String信息
        try {
            iden_info = IsIDCard.IDCardValidate(idcard);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (realname.equals("")) {
            Toast.makeText(PartyJoinActivity.this, R.string.enter_name, Toast.LENGTH_SHORT).show();
            return;
        }
        if (tel.equals("")) {
            Toast.makeText(PartyJoinActivity.this, R.string.enter_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(tel)) {
            Toast.makeText(PartyJoinActivity.this, R.string.phone_number_error, Toast.LENGTH_SHORT).show();
            return;
        }
//        if (age.equals("")) {
//            Toast.makeText(PartyJoinActivity.this, R.string.enter_age, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (job.equals("")) {
//            Toast.makeText(PartyJoinActivity.this, R.string.enter_job, Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (safe.equals("1") && idcard.equals("")) {//先判断身份证输入框是否为空
            Toast.makeText(PartyJoinActivity.this, R.string.enter_identity_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (safe.equals("1") && !iden_info.equals("")) {
            Toast.makeText(PartyJoinActivity.this, iden_info, Toast.LENGTH_SHORT).show();
            return;
        }
        if (needPic && filePath == null) {
            Toast.makeText(PartyJoinActivity.this, R.string.enter_pic, Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("dataid", dataid);
        params.addQueryStringParameter("payfee", payfee + "");
        params.addQueryStringParameter("realname", realname);
        //params.addQueryStringParameter("job", job);
        params.addQueryStringParameter("tel", tel);
        params.addQueryStringParameter("sex", sex + "");
        //params.addQueryStringParameter("age", age);
        if (!idcard.equals("") || idcard != null) {
            params.addQueryStringParameter("idcard", idcard);
        }
        if (needPic && filePath != null) {
            params.addQueryStringParameter("headpic", "headpic/" + imageName);
        }
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.JOIN_PARTY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    parsePartyRegistrationBean = ParseUtils.parsePartyRegistrationBean(result);
                    if (parsePartyRegistrationBean.getStatus() == 1) {
                        //TODO 头像上传七牛
                        if (filePath != null) {
                            uploadQiNiu();//已经做异步处理
                            removeDialog();
                        } else {
                            String joined = String.valueOf(parsePartyRegistrationBean.getData());
                            double cost = Double.parseDouble(payfee) - Double.parseDouble(getpre);
                            if (cost > 0) {
                                Intent intent = new Intent(PartyJoinActivity.this, PaymentActivity.class);
                                intent.putExtra("payment", "yici");
                                //intent.putExtra("prefee", String.valueOf(payfee));
                                //intent.putExtra("getpre", getpre);
                                intent.putExtra("joined", joined);
                                //intent.putExtra("fundname", fundname);
                                //intent.putExtra("title", title);
                                intent.putExtra("dataId", dataid);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PartyJoinActivity.this, "报名成功，该活动报名费为0元，无需支付", Toast.LENGTH_SHORT).show();
                            }
                            removeDialog();
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(PartyJoinActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
                    iv_pic.setImageBitmap(bitmap);
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
        key = "headpic/" + imageName;
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
                Log.e(TAG, key);
                Log.e(TAG, token);
                Log.e(TAG, filePath);
                uploadManager.put(filePath, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String s, com.qiniu.android.http.ResponseInfo responseInfo, JSONObject jsonObject) {
                        if (responseInfo.statusCode == 200) {
                            headpic = key;
//                            Toast.makeText(PartyJoinActivity.this, "上传成功" + headpic, Toast.LENGTH_SHORT).show();
                            //TODO 此处要调接口修改服务器字段
                            //上传成功，做跳转页面操作
                            String joined = String.valueOf(parsePartyRegistrationBean.getData());
                            double cost = Double.parseDouble(payfee) - Double.parseDouble(getpre);
                            if (cost > 0) {
                                Intent intent = new Intent(PartyJoinActivity.this, PaymentActivity.class);
                                intent.putExtra("payment", "yici");
                                //intent.putExtra("prefee", String.valueOf(payfee));
                                //intent.putExtra("getpre", getpre);
                                intent.putExtra("joined", joined);
                                //intent.putExtra("fundname", fundname);
                                //intent.putExtra("title", title);
                                intent.putExtra("dataId", dataid);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PartyJoinActivity.this, "报名成功，该活动报名费为0元，无需支付", Toast.LENGTH_SHORT).show();
                            }
                            removeDialog();
                            finish();
                        } else {
                            Toast.makeText(PartyJoinActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
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
            //16位的字母+数字随机数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//精确到秒
            String timestamp = sdf.format(new Date(Long.valueOf(System.currentTimeMillis())));//14位的时间戳
            String chars = "abcdefghijklmnopqrstuvwxyz";
            imageName = "" + chars.charAt((int) (Math.random() * 26)) + chars.charAt((int) (Math.random() * 26)) + timestamp;
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");
            }
            filePath = FileUtils.SDPATH + imageName;
            imageUri = Uri.parse("file:///sdcard/formats/" + imageName);
            final Intent intent = new Intent("com.android.camera.action.CROP");

            // 照片URL地址
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 2);
            intent.putExtra("outputX", 720);
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
