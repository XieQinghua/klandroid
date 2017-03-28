package org.thvc.happyi.activity;

import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.GetInFoBean;
import org.thvc.happyi.bean.SetInFoBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

/**
 * 项目名称：klandroid
 * 类描述：账户管理
 * 创建人：谢庆华.
 * 创建时间：2016/03/29 10:25
 * 修改人：
 * 修改时间：2016/03/29 10:25
 * 修改备注：
 */
public class AccountManageActivity extends BaseSwipeBackActivity {
    private EditText ed_claim1;//普通用户认领比例
    private EditText ed_zuidi1;//普通用户最低金额
    private EditText ed_zuigao1;//普通用户最高金额
    private EditText ed_claim;//新用户认领比例
    private EditText ed_zuidi;//新用户最低金额
    private EditText ed_zuigao;//新用户最高金额
    private TextView tv_account_set;//设定
    private TextView tv_account_modify;//修改

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        userid = HappyiApplication.getInstance().getUserid(this);
        init();
        getInfo();
    }

    public void getInfo() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FUND_GETINFO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    GetInFoBean getInFoBean = ParseUtils.parseGetInFoBean(result);
                    if (getInFoBean.getStatus() == 1) {
                        ed_claim1.setText(getInFoBean.getData().getOcount());
                        ed_zuidi1.setText(getInFoBean.getData().getOmin());
                        ed_zuigao1.setText(getInFoBean.getData().getOmax());

                        ed_claim.setText(getInFoBean.getData().getNcount());
                        ed_zuidi.setText(getInFoBean.getData().getNmin());
                        ed_zuigao.setText(getInFoBean.getData().getNmax());
                    }
                } else {
                    Toast.makeText(AccountManageActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    public void init() {
        ed_claim1 = (EditText) findViewById(R.id.ed_claim1);
        ed_zuidi1 = (EditText) findViewById(R.id.ed_zuidi1);
        ed_zuigao1 = (EditText) findViewById(R.id.ed_zuigao1);
        ed_claim = (EditText) findViewById(R.id.ed_claim);
        ed_zuidi = (EditText) findViewById(R.id.ed_zuidi);
        ed_zuigao = (EditText) findViewById(R.id.ed_zuigao);
        tv_account_set = (TextView) findViewById(R.id.tv_account_set);
        tv_account_modify = (TextView) findViewById(R.id.tv_account_modify);

        ed_claim1.setEnabled(false);
        ed_zuidi1.setEnabled(false);
        ed_zuigao1.setEnabled(false);
        ed_claim.setEnabled(false);
        ed_zuidi.setEnabled(false);
        ed_zuigao.setEnabled(false);

        tv_account_set.setOnClickListener(new MyOnClickListener());
        tv_account_modify.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_account_set:
                    setinfo();
                    break;
                case R.id.tv_account_modify:
                    tv_account_modify.setVisibility(View.GONE);
                    tv_account_set.setVisibility(View.VISIBLE);
                    ed_claim1.setEnabled(true);
                    ed_zuidi1.setEnabled(true);
                    ed_zuigao1.setEnabled(true);
                    ed_claim.setEnabled(true);
                    ed_zuidi.setEnabled(true);
                    ed_zuigao.setEnabled(true);
                    Selection.setSelection(ed_claim1.getText(), ed_claim1.getText().length());
                    Selection.setSelection(ed_zuidi1.getText(), ed_zuidi1.getText().length());
                    Selection.setSelection(ed_zuigao1.getText(), ed_zuigao1.getText().length());
                    Selection.setSelection(ed_claim.getText(), ed_claim.getText().length());
                    Selection.setSelection(ed_zuidi.getText(), ed_zuidi.getText().length());
                    Selection.setSelection(ed_zuigao.getText(), ed_zuigao.getText().length());
                    break;
            }
        }
    }


    public void setinfo() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("ncount", ed_claim.getText().toString().trim());
        params.addQueryStringParameter("nmin", ed_zuidi.getText().toString().trim());
        params.addQueryStringParameter("nmax", ed_zuigao.getText().toString().trim());
        params.addQueryStringParameter("ocount", ed_claim1.getText().toString().trim());
        params.addQueryStringParameter("omin", ed_zuidi1.getText().toString().trim());
        params.addQueryStringParameter("omax", ed_zuigao1.getText().toString().trim());
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FUND_SETINFO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    SetInFoBean setInFoBean = ParseUtils.parseSetInFoBean(result);
                    if (setInFoBean.getStatus() == 1) {
                        ed_claim1.setEnabled(false);
                        ed_zuidi1.setEnabled(false);
                        ed_zuigao1.setEnabled(false);
                        ed_claim.setEnabled(false);
                        ed_zuidi.setEnabled(false);
                        ed_zuigao.setEnabled(false);
                        tv_account_set.setVisibility(View.GONE);
                        tv_account_modify.setVisibility(View.VISIBLE);
                        Toast.makeText(AccountManageActivity.this, setInFoBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AccountManageActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

            }
        });


    }
}
