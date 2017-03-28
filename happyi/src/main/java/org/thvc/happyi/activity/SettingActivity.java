package org.thvc.happyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.Release.DataDao;
import org.thvc.happyi.Release.FounDataDao;
import org.thvc.happyi.Release.NgoDataDao;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.view.CustomDialog;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/12.
 * 系统设置页面
 */
public class SettingActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private TextView tv_feedback, tv_change_pwd, tv_happyi_introduce, tv_version, tv_SignOut;
    private String system, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        tv_feedback = (TextView) findViewById(R.id.tv_feedback);
        tv_change_pwd = (TextView) findViewById(R.id.tv_change_pwd);
        tv_happyi_introduce = (TextView) findViewById(R.id.tv_happyi_introduce);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_SignOut = (TextView) findViewById(R.id.tv_SignOut);

        tv_feedback.setOnClickListener(this);
        tv_change_pwd.setOnClickListener(this);
        tv_version.setText(getVersion());
        tv_happyi_introduce.setOnClickListener(this);
        tv_SignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback:
                startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));//跳转到意见反馈页面
                break;
            case R.id.tv_change_pwd:
                startActivity(new Intent(SettingActivity.this, ChangePwdActivity.class));//跳转到修改密码页面
                break;
            case R.id.tv_happyi_introduce:
                startActivity(new Intent(SettingActivity.this, HappyiIntroduceActivity.class));//跳转到App介绍页面
                break;
            case R.id.tv_SignOut:
                showAlertDialog();//注销登陆
                break;

        }
    }

    public void showAlertDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("是否继续注销登录操作?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //清空SharedPreferences用户数据
                HappyiApplication.getInstance().clearsaveParam(SettingActivity.this);
                //清空DB
                system = HappyiApplication.getInstance().getSystem(SettingActivity.this);
                userid = HappyiApplication.getInstance().getUserid(SettingActivity.this);
                if (system.equals("2")) {
                    DataDao dataDao = new DataDao(SettingActivity.this);
                    dataDao.delete(userid);
                } else if (system.equals("3")) {
                    NgoDataDao ngoDataDao = new NgoDataDao(SettingActivity.this);
                    ngoDataDao.delete(userid);
                } else if (system.equals("4")) {
                    FounDataDao founDataDao = new FounDataDao(SettingActivity.this);
                    founDataDao.delete(userid);
                }

                SharedPreferences sp = getSharedPreferences("loginparam", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("system");
                editor.remove("userid");
                editor.commit();
                //设置自动登录标识为false
                HappyiApplication.getInstance().setAutologin(SettingActivity.this, false);
                //清空Activity
                finishAll();
                //跳转登录界面
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });

        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }

    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion() {
        String st = getResources().getString(R.string.Version_number_is_wrong);
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return st;
        }
    }
}
