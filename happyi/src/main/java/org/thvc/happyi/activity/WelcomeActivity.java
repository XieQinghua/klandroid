package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.utils.SharedPreferencesUtil;

/**
 * 项目名称：klandroid
 * 类描述：程序的欢迎界面，此Acticity用于判断用户是否第一次登录程序
 * 创建人：谢庆华.
 * 创建时间：2015/11/9 13:52
 * 修改人：Administrator
 * 修改时间：2015/11/9 13:52
 * 修改备注：
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean b = (boolean) SharedPreferencesUtil.getData(HappyiApplication.getInstance().getContext(), "is_first", true);
        Intent intent;
        if (b) {
            // 第一次进入应用，进入引导页面
            intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            SharedPreferencesUtil.saveData(HappyiApplication.getInstance().getContext(), "is_first", false);
            finish();
        } else {
//            Log.e("MD5", MD5.getSign(this, this.getPackageName()));//获取应用的MD5签名
            // 不是第一次进入，进入应用程序的开屏页面
            intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }
}