package org.thvc.happyi.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.mob.tools.utils.UIHandler;

import org.thvc.happyi.R;
import org.thvc.happyi.Release.DataDao;
import org.thvc.happyi.Release.DataText;
import org.thvc.happyi.Release.FounDataDao;
import org.thvc.happyi.Release.FounDataText;
import org.thvc.happyi.Release.NgoDataDao;
import org.thvc.happyi.Release.NgoDataText;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.bean.FoundationBean;
import org.thvc.happyi.bean.LoginBean;
import org.thvc.happyi.bean.NgoLoginBean;
import org.thvc.happyi.bean.OAuthBean;
import org.thvc.happyi.bean.OAuthBindBean;
import org.thvc.happyi.bean.OAuthMobileBean;
import org.thvc.happyi.bean.OauthLoginBean;
import org.thvc.happyi.bean.RegisterCodeBean;
import org.thvc.happyi.bean.UserRegisterBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.CommonUtils;
import org.thvc.happyi.utils.ExampleUtil;
import org.thvc.happyi.utils.IsIDCard;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.OAuthBindDialog;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * 颜松梁
 * Created by Administrator on 2015/11/9.
 * 登陆页面
 * 修改 huangxinqi
 * 添加三方登陆
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, PlatformActionListener {
    private final static String TAG = "LoginActivity";

    private ImageView im_Account;//账号图片
    private ImageView im_Password;//密码图片
    private EditText edit_Account;//账号输入域
    private EditText edit_Password;//密码输入域

    private CheckBox check_Remember_Passwords;//记住密码开关

    private TextView tv_Forget_Password;//忘记密码按钮
    private TextView tv_Registered;//注册按钮
    private TextView tv_Third_Party_Landing;//第三方登陆按钮

    private Button btn_Landing;//登陆按钮

    private View CustomView;//注册页面view
    private AlertDialog dialog;//注册对话框

    private String currentUsername;
    private String currentPassword;

    private Platform currentPlatform;

    private TimeCount time;
    private TextView tv_get_code;

    private boolean autologin = false;// 自动登录的标识

    private HashMap<String, Object> userInfo;//三方登陆授权成功之后获得的用户信息
    //注册和忘记密码
    private ImageView im_NewPasswords;
    private LinearLayout linear_layout_CheckBox;
    private LinearLayout linear_layout_NewPasswords;
    private EditText edit_Phone_Number;
    private EditText edit_Verification_Code;
    private EditText edit_Passwords;
    private EditText edit_NewPasswords;
    private CheckBox check_agreement;//爱周末用户协议开关
    private TextView tv_user_agreement;//爱周末用户协议入口
    private ImageButton ibQQLogin;
    private ImageButton ibWeiboLogin;
    private ImageButton ibWechatLogin;
    private String username;
    private String code;
    private String passwords;
    private String newpasswords;
    private String btnText = "";
    private String returnType;//这个是三方登陆oauth的返回值1.	返回y说明该手机号码尚未被注册2.	返回x说明该手机号码已经注册账号未绑定第三方账号
    private String loginType;//三方登陆方式
    private String openId;//三方登陆id
    //极光推送
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    //下面是授权过程的状态
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**初始化第三方登录*/
        ShareSDK.initSDK(this);

        setContentView(R.layout.activity_login);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
        Init();
        // 判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            // 设置默认是记录密码状态
            edit_Account.setText(sp.getString("USER_NAME", ""));
            edit_Password.setText(sp.getString("PASSWORD", ""));
            check_Remember_Passwords.setChecked(true);
        } else {
            edit_Account.setText(sp.getString("USER_NAME", ""));
            check_Remember_Passwords.setChecked(false);
        }
        registerMessageReceiver();
    }

    public void Init() {
        // 获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        im_Account = (ImageView) this.findViewById(R.id.im_Account);
        im_Password = (ImageView) this.findViewById(R.id.im_Password);

        edit_Account = (EditText) this.findViewById(R.id.edit_Account);
        edit_Password = (EditText) this.findViewById(R.id.edit_Password);

        ibQQLogin = (ImageButton) findViewById(R.id.ib_qq_login);
        ibWechatLogin = (ImageButton) findViewById(R.id.ib_wechat_login);
        ibWeiboLogin = (ImageButton) findViewById(R.id.ib_weibo_login);
        ibQQLogin.setOnClickListener(this);
        ibWechatLogin.setOnClickListener(this);
        ibWeiboLogin.setOnClickListener(this);

        check_Remember_Passwords = (CheckBox) this.findViewById(R.id.check_Remember_Passwords);

        // 监听记住密码多选框按钮事件
        check_Remember_Passwords.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (check_Remember_Passwords.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        tv_Forget_Password = (TextView) this.findViewById(R.id.tv_Forget_Password);
        tv_Registered = (TextView) this.findViewById(R.id.tv_Registered);
        tv_Third_Party_Landing = (TextView) this.findViewById(R.id.tv_Third_Party_Landing);

        btn_Landing = (Button) this.findViewById(R.id.btn_Landing);
        edit_Account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_Account.setImageResource(R.drawable.im_account1);
                } else {
                    im_Account.setImageResource(R.drawable.im_account2);
                }
            }
        });
        edit_Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_Password.setImageResource(R.drawable.im_password1);
                } else {
                    im_Password.setImageResource(R.drawable.im_password2);
                }
            }
        });
        // 如果用户名改变，清空密码
        edit_Account.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_Password.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_Forget_Password.setOnClickListener(this);
        tv_Registered.setOnClickListener(this);
        tv_Third_Party_Landing.setOnClickListener(this);
        btn_Landing.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("oauth", MODE_PRIVATE);
        editor = sharedPreferences.edit();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Forget_Password:
                btnText = "ForgetPassword";
                alertDialog();
                break;
            case R.id.tv_Registered:
                btnText = "Registered";
                alertDialog();
                break;
            case R.id.tv_Third_Party_Landing:
                break;
            case R.id.btn_Landing:
                Landing();
                break;
            case R.id.ib_qq_login:
                authorize(new QQ(LoginActivity.this));
                break;
            case R.id.ib_wechat_login:
                authorize(new Wechat(LoginActivity.this));
                break;
            case R.id.ib_weibo_login:
                authorize(new SinaWeibo(LoginActivity.this));
                break;
        }

    }

    /**
     * 登陆
     */
    public void Landing() {
        currentUsername = edit_Account.getText().toString().trim();
        currentPassword = edit_Password.getText().toString().trim();

        /**
         * 判断用户 名或者密码是否有为空
         */
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        //判断手机号码正确性
        if (!IsIDCard.isValidMobileNo(currentUsername)) {
            Toast.makeText(LoginActivity.this, R.string.phone_number_error, Toast.LENGTH_SHORT).show();
            removeDialog();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        showDialog(LOADING_DIALOG);
        numberLogin();
    }


    /**
     * 手机号码登录
     */
//     加密请求
//        MyRequestParams params = new MyRequestParams();
//        params.addQueryStringParameter("username", currentUsername);
//        params.addQueryStringParameter("password", currentPassword);
//        String str = params.myRequestParams(params);
//        String url = HappyiApi.LANDING+str;
//        String result = params.getHttpResult(url);
    public void numberLogin() {
        if (!CommonUtils.isNetWorkConnected(this)) {
            //判断网络是否可用
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("password", currentPassword);
        params.addQueryStringParameter("username", currentUsername);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.LOGIN + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    LoginBean loginBean = ParseUtils.parseLoginBean(result);
                    //得到用户的相关信息,并且将用户信息保存
                    if (loginBean.getStatus() == 1) {
                        autologin = true;
                        String system = loginBean.getData().getSystem();
                        String userid = loginBean.getData().getUserid();
                        String nickname = loginBean.getData().getNickname();
                        String solevar = loginBean.getData().getSolevar();
                        //保存用户数据到应用SharedPreferences
                        HappyiApplication.getInstance().saveParam(LoginActivity.this,
                                autologin,
                                system,
                                currentUsername,
                                currentPassword,
                                userid,
                                nickname,
                                solevar,
                                result);
                        //存入db
                        if (system.equals("2")) {
                            getPersonaJson(result);
                        } else if (system.equals("3")) {
                            getNgoJson(result);
                        } else if (system.equals("4")) {
                            getFoundationjson(result);
                        }
                        //登录到主界面
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        removeDialog();
                        if (check_Remember_Passwords.isChecked()) {
                            // 记住用户名、密码、
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_NAME", currentUsername);
                            editor.putString("PASSWORD", currentPassword);
                            editor.commit();
                        }
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, loginBean.getInfo(), Toast.LENGTH_SHORT).show();
                        removeDialog();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });

    }

    /**
     * 解析普通用户的json数据
     *
     * @param result
     */
    public void getPersonaJson(String result) {
        LoginBean loginBean = ParseUtils.parseLoginBean(result);
        String userid = loginBean.getData().getUserid();//用户ID
        String system = loginBean.getData().getSystem();
        String username = loginBean.getData().getUsername();
        String mobile = loginBean.getData().getMobile();
        String nickname = loginBean.getData().getNickname();
        String age = loginBean.getData().getAge() + "";
        String birthday = loginBean.getData().getBirthday();
        String sex = loginBean.getData().getSex();
        String content = loginBean.getData().getContent();
        String headpic = loginBean.getData().getHeadpic();
        String realname = loginBean.getData().getRealname();
        String email = loginBean.getData().getEmail();
        String job = loginBean.getData().getJob();
        String idcard = loginBean.getData().getIdcard();
        DataText dataText = new DataText(userid, system, username, mobile, nickname, age, birthday, sex, content, headpic, realname, email, job, idcard);
        DataDao dataDao = new DataDao(LoginActivity.this);
        dataDao.insert(dataText);
    }

    /**
     * 解析NGO的json数据
     *
     * @param result
     */
    public void getNgoJson(String result) {
        NgoLoginBean ngoLoginBean = ParseUtils.parseNgoLoginBean(result);
        String userid = ngoLoginBean.getData().getUserid();//用户ID
        String username = ngoLoginBean.getData().getUsername();//用户名
        String realname = ngoLoginBean.getData().getRealname();//主管单位
        String nickname = ngoLoginBean.getData().getNickname();//机构名称
        String address = ngoLoginBean.getData().getAddress();//地址
        String headpic = ngoLoginBean.getData().getHeadpic();//头像
        String orgcontact = ngoLoginBean.getData().getInfo().getOrgcontact();//负责人
        String orgtel = ngoLoginBean.getData().getInfo().getOrgtel();//负责人电话
        String orgemail = ngoLoginBean.getData().getInfo().getOrgemail();//负责人邮箱
        NgoDataText dataText = new NgoDataText(userid, username, realname, nickname, address, headpic, orgcontact, orgtel, orgemail);
        NgoDataDao ngoDataDao = new NgoDataDao(LoginActivity.this);
        ngoDataDao.insert(dataText);
    }


    /**
     * 解析基金会的json数据
     *
     * @param result
     */
    public void getFoundationjson(String result) {
        FoundationBean foundationBean = ParseUtils.parseFoundationBean(result);
        String userid = foundationBean.getData().getUserid();//用户ID
        String headpic = foundationBean.getData().getHeadpic();//头像
        String nickname = foundationBean.getData().getNickname();//机构名称
        String address = foundationBean.getData().getAddress();//地址
        String orgcontact = foundationBean.getData().getInfo().getOrgcontact();//负责人
        String orgemail = foundationBean.getData().getInfo().getOrgemail();//负责人邮箱
        String orgtel = foundationBean.getData().getInfo().getOrgtel();//负责人电话
        FounDataText founDataText = new FounDataText(userid, headpic, nickname, address, orgcontact, orgemail, orgtel);
        FounDataDao founDataDao = new FounDataDao(LoginActivity.this);
        founDataDao.insert(founDataText);
    }

    /**
     * 弹出注册和忘记密码对话框
     */
    public void alertDialog() {
        time = new TimeCount(60000, 1000);
        AlertDialog.Builder builder = myBuilder(LoginActivity.this);
        dialog = builder.show();
        //点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        ImageView im_close = (ImageView) CustomView.findViewById(R.id.im_close);//关闭注册页面
        im_close.setOnClickListener(new MyOnClickListener());
        final ImageView im_Phone_Number = (ImageView) CustomView.findViewById(R.id.im_Phone_Number);//账号图标
        final ImageView im_Verification_Code = (ImageView) CustomView.findViewById(R.id.im_Verification_Code);//输入验证码图标
        final ImageView im_passwords = (ImageView) CustomView.findViewById(R.id.im_passwords);//密码图标
        im_NewPasswords = (ImageView) CustomView.findViewById(R.id.im_NewPasswords);

        check_agreement = (CheckBox) CustomView.findViewById(R.id.check_agreement);
        tv_user_agreement = (TextView) CustomView.findViewById(R.id.tv_user_agreement);
        tv_user_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserAgreementActivity.class));
            }
        });
        edit_Phone_Number = (EditText) CustomView.findViewById(R.id.edit_Phone_Number);//注册账号输入域
        edit_Verification_Code = (EditText) CustomView.findViewById(R.id.edit_Verification_Code);//输入验证码
        edit_Passwords = (EditText) CustomView.findViewById(R.id.edit_Passwords);//注册密码输入域
        edit_NewPasswords = (EditText) CustomView.findViewById(R.id.edit_NewPasswords);//忘记密码再次输入域


        linear_layout_CheckBox = (LinearLayout) CustomView.findViewById(R.id.linear_layout_CheckBox);
        linear_layout_NewPasswords = (LinearLayout) CustomView.findViewById(R.id.linear_layout_NewPasswords);
        tv_get_code = (TextView) CustomView.findViewById(R.id.tv_get_code);//获取验证码按钮
        tv_get_code.setOnClickListener(new MyOnClickListener());


        Button btn_Registereds = (Button) CustomView.findViewById(R.id.btn_Registereds);//注册和忘记密码按钮
        btn_Registereds.setOnClickListener(new MyOnClickListener());

        if (btnText.equals("ForgetPassword")) {
            linear_layout_NewPasswords.setVisibility(View.VISIBLE);
            linear_layout_CheckBox.setVisibility(View.GONE);
            btn_Registereds.setText("确定");
        } else if (btnText.equals("Registered")) {
            linear_layout_NewPasswords.setVisibility(View.GONE);
            linear_layout_CheckBox.setVisibility(View.VISIBLE);
            btn_Registereds.equals("注册");
        }
        edit_Phone_Number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_Phone_Number.setImageResource(R.drawable.im_mobile_phone);
                } else {
                    im_Phone_Number.setImageResource(R.drawable.im_mobile_phone1);
                }
            }
        });
        edit_Verification_Code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_Verification_Code.setImageResource(R.drawable.im_verification_code);
                } else {
                    im_Verification_Code.setImageResource(R.drawable.im_verification_code1);
                }
            }
        });
        edit_Passwords.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_passwords.setImageResource(R.drawable.im_password1);
                } else {
                    im_passwords.setImageResource(R.drawable.im_password2);
                }
            }
        });
        edit_NewPasswords.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    im_NewPasswords.setImageResource(R.drawable.im_password1);
                } else {
                    im_NewPasswords.setImageResource(R.drawable.im_password2);
                }
            }
        });
    }


    protected AlertDialog.Builder myBuilder(LoginActivity dialogWindows) {
        final LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogWindows);
        CustomView = inflater.inflate(R.layout.dialog_registered, null);
        return builder.setView(CustomView);
    }

    /**
     * 请求授权
     *
     * @param plat
     */
    private void authorize(Platform plat) {
        currentPlatform = plat;
        plat.removeAccount();
        ShareSDK.removeCookieOnAuthorize(true);
        openId = plat.getDb().getUserId();
        if (plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                /*Message message=new Message();
                message.what=MSG_USERID_FOUND;
                message.obj=userId;
                UIHandler.sendMessage(message,this);*/
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                thirdPartyLogin(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    /**
     * 三方授权登陆
     *
     * @param plat
     * @param userId
     * @param userInfo
     */
    private void thirdPartyLogin(String plat, String userId, HashMap<String, Object> userInfo) {
        this.userInfo = userInfo;
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MSG_USERID_FOUND: {
                //Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();

            }
            break;
            case MSG_LOGIN: {
                MyRequestParams paramsOauth = new MyRequestParams();
                paramsOauth.addQueryStringParameter("field", "openid");
                paramsOauth.addQueryStringParameter("value", currentPlatform.getDb().getUserId());
                switch (currentPlatform.getName()) {
                    case "QQ":
                        loginType = "qq";
                        break;
                    case "SinaWeibo":
                        loginType = "sina";
                        break;
                    case "Wechat":
                        loginType = "id";
                        break;
                }
                paramsOauth.addQueryStringParameter("type", loginType);
                HttpUtils httpUtils = new HttpUtils();
                String url = paramsOauth.myRequestParams(paramsOauth);
                httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.OAUTH + url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (new JsonValidator().validate(responseInfo.result)) {
                            OAuthBean oAuthBean = new Gson().fromJson(responseInfo.result, OAuthBean.class);
                            if (oAuthBean.getStatus() == 1) {
                                OAuthBean.DataEntity dataEntity = oAuthBean.getData();
                                autologin = true;
                                editor.putString("loginType", loginType);
                                editor.putString("openId", openId);
                                editor.commit();
                                //HappyiApplication.getInstance().saveLoginType(LoginActivity.this,loginType);
                                //HappyiApplication.getInstance().saveOpenId(LoginActivity.this,openId);
                                String system = dataEntity.getSystem();
                                String userid = dataEntity.getUserid();
                                String nickname = dataEntity.getNickname();
                                String solevar = dataEntity.getSolevar();
                                String username = dataEntity.getUsername();
                                //currentPassword=dataEntity.get
                                //保存用户数据到应用SharedPreferences
                                HappyiApplication.getInstance().saveParam(LoginActivity.this,
                                        autologin,
                                        system,
                                        username,
                                        currentPassword,
                                        userid,
                                        nickname,
                                        solevar,
                                        responseInfo.result);
                                //存入db
                                if (system.equals("2")) {
                                    getPersonaJson(responseInfo.result);
                                } else if (system.equals("3")) {
                                    getNgoJson(responseInfo.result);
                                } else if (system.equals("4")) {
                                    getFoundationjson(responseInfo.result);
                                }
                                //登录到主界面
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                removeDialog();
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, oAuthBean.getInfo(), Toast.LENGTH_SHORT).show();
                                if (oAuthBean.getInfo().equals("请先绑定账号！")) {
                                    showOAuthBindDialog();
                                }
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e(TAG, s);
                    }
                });
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    /**
     * 判断该手机号码是否已经被注册或绑定
     *
     * @param phone
     */
    private void checkIsRegisterOrBind(String phone, final OAuthBindDialog dialog) {
        //1.返回y说明该手机号码尚未被注册 2.	返回x说明该手机号码已经注册账号未绑定第三方账号
        MyRequestParams paramsOauth = new MyRequestParams();
        paramsOauth.addQueryStringParameter("field", "mobile");
        paramsOauth.addQueryStringParameter("value", phone);
        switch (currentPlatform.getName()) {
            case "QQ":
                loginType = "qq";
                break;
            case "SinaWeibo":
                loginType = "sina";
                break;
            case "Wechat":
                loginType = "id";
                break;
        }
        paramsOauth.addBodyParameter("type", loginType);
        HttpUtils httpUtils = new HttpUtils();
        String url = paramsOauth.myRequestParams(paramsOauth);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.OAUTH + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    OAuthMobileBean oAuthMobileBean = new Gson().fromJson(responseInfo.result, OAuthMobileBean.class);
                    if (oAuthMobileBean.getStatus() == 1) {
                        if (oAuthMobileBean.getData().equals("x")) {
                            returnType = "x";
                            dialog.getEtPassword().setHint("该手机号码已注册，请输入密码");
                        } else if (oAuthMobileBean.getData().equals("y")) {
                            returnType = "y";
                            dialog.getRlVerifyCode().setVisibility(View.VISIBLE);
                            dialog.getEtPassword().setHint("请设置密码");
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, oAuthMobileBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 显示绑定电话号码的对话框
     */
    private void showOAuthBindDialog() {
        time = new TimeCount(60000, 1000);
        final OAuthBindDialog dialog = new OAuthBindDialog(LoginActivity.this);
        tv_get_code = dialog.getTvGetCode();
        dialog.getIvClose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getAlertDialog().dismiss();
            }
        });
        dialog.getEtPhoneNumber().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkIsRegisterOrBind(dialog.getEtPhoneNumber().getText().toString().trim(), dialog);
                    dialog.getIvPhone().setImageResource(R.drawable.im_mobile_phone1);
                } else {
                    dialog.getIvPhone().setImageResource(R.drawable.im_mobile_phone);
                }
            }
        });
        dialog.getEtPassword().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getIvPassword().setImageResource(R.drawable.im_password1);
                } else {
                    dialog.getIvPassword().setImageResource(R.drawable.im_password2);
                }
            }
        });
        dialog.getTvGetCode().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = dialog.getEtPhoneNumber().getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LoginActivity.this, R.string.enter_phone_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                time.start();
                thirdLoginVerifyCode(phone);
            }
        });
        dialog.getUserAgreement().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserAgreementActivity.class));
            }
        });

        dialog.getBtnConfirm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = dialog.getEtPhoneNumber().getText().toString().trim();
                String password = dialog.getEtPassword().getText().toString().trim();
                String verifyCode = dialog.getEtVerifyCode().getText().toString().trim();
                if (!dialog.getCheckAgreement().isChecked()) {
                    Toast.makeText(LoginActivity.this, R.string.not_choose_agreement, Toast.LENGTH_SHORT).show();
                    return;
                }
                oAuthBind(phone, password, verifyCode);
            }
        });
    }

    /**
     * 三方登陆获取验证码
     *
     * @param phone
     */
    private void thirdLoginVerifyCode(String phone) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("mobile", phone);
        if (returnType.equals("x")) {
            params.addQueryStringParameter("exist", 1 + "");
        } else if (returnType.equals("y")) {
            params.addQueryStringParameter("exist", 0 + "");
        }
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.SEND_CODE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RegisterCodeBean registerCodeBean = ParseUtils.parseRegisterCodeBean(result);
                    if (registerCodeBean.getStatus() == 1) {
                        Log.e(TAG, "获取验证码成功");
                    } else {
                        Toast.makeText(LoginActivity.this, registerCodeBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 三方登陆绑定
     *
     * @param phone
     * @param password
     */
    private void oAuthBind(final String phone, String password, String verifyCode) {
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("openid", currentPlatform.getDb().getUserId());
        params.addQueryStringParameter("username", phone);
        params.addQueryStringParameter("password", password);
        params.addQueryStringParameter("code", verifyCode);
        switch (currentPlatform.getName()) {
            case "QQ":
                loginType = "qq";
                break;
            case "SinaWeibo":
                loginType = "sina";
                break;
            case "Wechat":
                loginType = "id";
                break;
        }
        params.addQueryStringParameter("type", loginType);
        HttpUtils httpUtils = new HttpUtils();
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.OAUTH_BIND + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    OAuthBindBean oAuthBindBean = new Gson().fromJson(responseInfo.result, OAuthBindBean.class);
                    if (oAuthBindBean.getStatus() == 1) {
                        oAuthLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, oAuthBindBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 自动登录
     */
    private void oAuthLogin() {
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("openid", currentPlatform.getDb().getUserId());
        params.addQueryStringParameter("ip", getLocalIpAddress());
        switch (currentPlatform.getName()) {
            case "QQ":
                loginType = "qq";
                break;
            case "SinaWeibo":
                loginType = "sina";
                break;
            case "Wechat":
                loginType = "id";
                break;
        }
        params.addQueryStringParameter("type", loginType);
        HttpUtils httpUtils = new HttpUtils();
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.OAUTH_LOGIN + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    OauthLoginBean oauthLoginBean = new Gson().fromJson(responseInfo.result, OauthLoginBean.class);
                    if (oauthLoginBean.getStatus() == 1) {
                        OauthLoginBean.DataEntity dataEntity = oauthLoginBean.getData();
                        autologin = true;
                        editor.putString("loginType", loginType);
                        editor.putString("openId", openId);
                        editor.commit();
                        //HappyiApplication.getInstance().saveLoginType(LoginActivity.this,loginType);
                        //HaapyiApplication.getInstance().saveOpenId(LoginActivity.this,openId);
                        String system = dataEntity.getSystem();
                        String userid = dataEntity.getUserid();
                        String nickname = dataEntity.getNickname();
                        String solevar = dataEntity.getSolevar();
                        String username = dataEntity.getUsername();
                        //保存用户数据到应用SharedPreferences
                        HappyiApplication.getInstance().saveParam(LoginActivity.this,
                                autologin,
                                system,
                                username,
                                "",
                                userid,
                                nickname,
                                solevar,
                                responseInfo.result);
                        //存入db
                        if (system.equals("2")) {
                            getPersonaJson(responseInfo.result);
                        } else if (system.equals("3")) {
                            getNgoJson(responseInfo.result);
                        } else if (system.equals("4")) {
                            getFoundationjson(responseInfo.result);
                        }
                        //登录到主界面
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        removeDialog();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, oauthLoginBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            thirdPartyLogin(platform.getName(), platform.getDb().getUserId(), hashMap);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if (i == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }


    /**
     * 注册页面的点击事件
     */
    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_close:
                    dialog.dismiss();
                    break;
                case R.id.tv_get_code:
                    username = edit_Phone_Number.getText().toString().trim();
                    if (TextUtils.isEmpty(username)) {
                        Toast.makeText(LoginActivity.this, R.string.enter_phone_number, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //判断手机号码正确性
                    if (!IsIDCard.isValidMobileNo(username)) {
                        Toast.makeText(LoginActivity.this, R.string.phone_number_error, Toast.LENGTH_SHORT).show();
                        removeDialog();
                        return;
                    }
                    if (btnText.equals("ForgetPassword")) {
                        registercode(username, 1);
                    } else if (btnText.equals("Registered")) {
                        registercode(username, 0);
                    }

                    break;
                case R.id.btn_Registereds:
                    username = edit_Phone_Number.getText().toString().trim();
                    code = edit_Verification_Code.getText().toString().trim();
                    passwords = edit_Passwords.getText().toString().trim();
                    newpasswords = edit_NewPasswords.getText().toString().trim();
                    if (btnText.equals("ForgetPassword")) {
                        forgetPassword(username, code, passwords, newpasswords);
                    } else if (btnText.equals("Registered")) {
                        registered(username, code, passwords);
                    }
                    break;
            }

        }
    }


    /**
     * 注册
     */
    private void registered(String username, String code, String passwords) {
        if (!CommonUtils.isNetWorkConnected(this)) {
            //判断网络是否可用
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * 判断用户 名或者密码是否有为空
         */
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, R.string.enter_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, R.string.verification_code_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        if (code.length() < 6) {
            Toast.makeText(this, R.string.verification_code_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            Toast.makeText(this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!check_agreement.isChecked()) {
            Toast.makeText(LoginActivity.this, R.string.not_choose_agreement, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", passwords);
        params.addQueryStringParameter("code", code);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.ORDINARY_REG + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    UserRegisterBean userRegisterBean = ParseUtils.parseUserRegisterBean(result);
                    if (userRegisterBean.getStatus() == 1) {
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, userRegisterBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 忘记密码
     *
     * @param username     注册时的手机号码
     * @param code         验证码
     * @param passwords    第一次设置新密码
     * @param newpasswords 新密码
     */
    private void forgetPassword(String username, String code, String passwords, String newpasswords) {
        if (!CommonUtils.isNetWorkConnected(this)) {
            //判断网络是否可用
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 判断用户 名或者密码是否有为空
         */
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, R.string.enter_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, R.string.verification_code_hint, Toast.LENGTH_SHORT).show();
            return;
        }
        if (code.length() < 6) {
            Toast.makeText(this, R.string.verification_code_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            Toast.makeText(this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwords.equals(newpasswords)) {
            Toast.makeText(this, R.string.two_inconsistent, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", newpasswords);
        params.addQueryStringParameter("code", code);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FORGET_PASSWORD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    Toast.makeText(LoginActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 获取验证码
     */
    private void registercode(String username, int exist) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("mobile", username);
        params.addQueryStringParameter("exist", exist + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.SEND_CODE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RegisterCodeBean registerCodeBean = ParseUtils.parseRegisterCodeBean(result);
                    if (registerCodeBean.getStatus() == 1) {
                        Log.e(TAG, "获取验证码成功");
                        time.start();//此号码能获取验证码才开启倒计时
                    } else {
                        Toast.makeText(LoginActivity.this, registerCodeBean.getInfo(), Toast.LENGTH_SHORT).show();
                        tv_get_code.setText("获取验证码");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 极光推送
     */
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_get_code.setClickable(false);
            tv_get_code.setText("验证码（" + millisUntilFinished / 1000 + "）");
        }

        @Override
        public void onFinish() {
            tv_get_code.setText("重新获取验证码");
            tv_get_code.setClickable(true);
        }
    }


    /**
     * 主页监听手机返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));//跳转到退款操作页面
            finish();
        }
        return false;
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("ifo", ex.toString());
        }
        return "";
    }

}
