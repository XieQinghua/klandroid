package org.thvc.happyi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.bean.LoginBean;
import org.thvc.happyi.bean.OauthLoginBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.CommonUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 项目名称：klandroid
 * 类描述：开屏页
 * 创建人：谢庆华.
 * 创建时间：2015/11/9 14:13
 * 修改人：Administrator
 * 修改时间：2015/11/9 14:13
 * 修改备注：
 * update by huangxinqi
 * 修改该内容： 添加三方登陆的自动登录
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private RelativeLayout rootLayout;
    private Boolean autologin = false;//自动登录标识
    private String username;
    private String password;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置淡进淡出动画
//        rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
//        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
//        animation.setDuration(1000);
//        rootLayout.startAnimation(animation);
        autologin = HappyiApplication.getInstance().getAutologin(this);
        username = HappyiApplication.getInstance().getUsername(this);
        password = HappyiApplication.getInstance().getPassword(this);
        sharedPreferences = getSharedPreferences("oauth", MODE_PRIVATE);
    }

    /**
     * 账号密码自动登录
     */
    private void autoLogin() {
        if (!CommonUtils.isNetWorkConnected(this)) {
            //判断网络是否可用
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("username", username);
        params.addBodyParameter("password", password);
        String url = params.myRequestParams(params);
//        Log.e(TAG, "账号：" + username + "密码：" + password);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.LOGIN + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    LoginBean loginBean = ParseUtils.parseLoginBean(result);
                    //得到用户的相关信息,并且将用户信息保存
                    if (loginBean.getStatus() == 1) {
                        //登录到主界面
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
    protected void onStart() {
        super.onStart();
//        Log.e(TAG, "自动登录标识为" + autologin);
        if (autologin) {
            if (!sharedPreferences.getString("openId", "").equals("")) {
                oauthLogin();
            } else {
                autoLogin();
            }
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * 三方登录
     */
    private void oauthLogin() {
        String openId = sharedPreferences.getString("openId", "");
        String loginType = sharedPreferences.getString("loginType", "");
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("type", loginType);
        params.addQueryStringParameter("openid", openId);
        params.addQueryStringParameter("ip", getLocalIpAddress());
        HttpUtils httpUtils = new HttpUtils();
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.OAUTH_LOGIN + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    OauthLoginBean oauthLoginBean = new Gson().fromJson(responseInfo.result, OauthLoginBean.class);
                    if (oauthLoginBean.getStatus() == 1) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
}
