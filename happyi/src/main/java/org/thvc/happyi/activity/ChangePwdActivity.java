package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.ChangePwdBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;


/**
 * 项目名称：klandroid
 * 类描述：
 * 创建人：谢庆华.
 * 创建时间：2015/11/21 15:51
 * 修改人：Administrator
 * 修改时间：2015/11/21 15:51
 * 修改备注：
 */
public class ChangePwdActivity extends BaseSwipeBackActivity {
    private final static String TAG = "ChangePwdActivity";
    private EditText et_old_pwd, et_new_pwd, et_again_new_pwd;
    private String password, newpwd, again_newpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        init();
    }

    private void init() {
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_again_new_pwd = (EditText) findViewById(R.id.et_again_new_pwd);
    }

    public void submit(View v) {
        password = et_old_pwd.getText().toString().trim();
        newpwd = et_new_pwd.getText().toString().trim();
        again_newpwd = et_again_new_pwd.getText().toString().trim();
//        if (password.equals(HappyiApplication.getInstance().getPassword(this).toString())) {
        if (newpwd.length() < 6 || again_newpwd.length() < 6) {
            Toast.makeText(ChangePwdActivity.this, "请输入6-16位新密码", Toast.LENGTH_SHORT).show();
        } else {
            if (!newpwd.equals(again_newpwd)) {
                Toast.makeText(ChangePwdActivity.this, R.string.two_inconsistent, Toast.LENGTH_SHORT).show();
            } else {
                confirmSubmit();
            }
        }
//        } else {
//            Toast.makeText(ChangePwdActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
//        }
    }

    private void confirmSubmit() {
        showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(this));
        params.addQueryStringParameter("password", password);
        params.addQueryStringParameter("newpwd", newpwd);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CHANGE_PASSWORD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e(TAG, result);
                if (new JsonValidator().validate(result)) {
                    ChangePwdBean changePwdBean = ParseUtils.parseChangePwdBean(result);
                    if (changePwdBean.getStatus() == 1) {
                        Toast.makeText(ChangePwdActivity.this, changePwdBean.getInfo(), Toast.LENGTH_SHORT).show();
                        //直接执行注销登录操作不提示弹出框
                        //清空用户数据
                        HappyiApplication.getInstance().clearsaveParam(ChangePwdActivity.this);
                        //设置自动登录标识为false
                        HappyiApplication.getInstance().setAutologin(ChangePwdActivity.this, false);
                        //清空Activity
                        finishAll();
                        //跳转登录界面
                        startActivity(new Intent(ChangePwdActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(ChangePwdActivity.this, changePwdBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                    removeDialog(LOADING_DIALOG);
                } else {
                    Toast.makeText(ChangePwdActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    removeDialog(LOADING_DIALOG);
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }
}
