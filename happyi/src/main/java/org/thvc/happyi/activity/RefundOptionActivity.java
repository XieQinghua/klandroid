package org.thvc.happyi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.ApplyRefundBean;
import org.thvc.happyi.bean.RefundInfoBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;

/**
 * 个人用户选择退款方式，支付宝或者银行卡
 * Created by huangxinqi
 * on 2015/12/12-00:22.
 */
public class RefundOptionActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private EditText etBankName;
    private EditText etAccount;
    private EditText etName;
    private EditText etBankAccount;
    private EditText etOwnerName;
    private RelativeLayout rlAliPay;
    private RelativeLayout rlBank;
    private Button btnSubmit;
    private boolean isAliPayCheck = true;
    private ImageButton ibAliPay;
    private ImageButton ibBank;
    private RefundInfoBean refundInfoBean;
    private static final int checkAliPay = 1000;
    private static final int checkBank = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case checkAliPay:
                    etAccount.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.VISIBLE);
                    etBankAccount.setVisibility(View.GONE);
                    etBankName.setVisibility(View.GONE);
                    etOwnerName.setVisibility(View.GONE);
                    break;
                case checkBank:
                    etBankAccount.setVisibility(View.VISIBLE);
                    etOwnerName.setVisibility(View.VISIBLE);
                    etBankName.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.GONE);
                    etAccount.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_option);
        initializedView();
        refundInfoBean = (RefundInfoBean) getIntent().getSerializableExtra("info");
    }

    private void initializedView() {
        ibBank = (ImageButton) findViewById(R.id.rb_bank);
        ibAliPay = (ImageButton) findViewById(R.id.rb_ali_pay);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        etOwnerName = (EditText) findViewById(R.id.et_owner_name);
        etBankAccount = (EditText) findViewById(R.id.et_bank_account);
        etAccount = (EditText) findViewById(R.id.et_account);
        etName = (EditText) findViewById(R.id.et_name);
        etBankName = (EditText) findViewById(R.id.et_bank_name);
        rlAliPay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rlBank = (RelativeLayout) findViewById(R.id.rl_bank);
        btnSubmit.setOnClickListener(this);
        rlAliPay.setOnClickListener(this);
        rlBank.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alipay:
                isAliPayCheck = true;
                ibBank.setImageResource(R.drawable.btn_unselected);
                ibAliPay.setImageResource(R.drawable.btn_selected);
                handler.sendEmptyMessage(checkAliPay);
                break;
            case R.id.rl_bank:
                isAliPayCheck = false;
                ibBank.setImageResource(R.drawable.btn_selected);
                ibAliPay.setImageResource(R.drawable.btn_unselected);
                handler.sendEmptyMessage(checkBank);
                break;
            case R.id.btn_submit:
                if (isAliPayCheck) {
                    if (!(TextUtils.isEmpty(etAccount.getText().toString().trim()) && TextUtils.isEmpty(etName.getText().toString().trim()))) {
                        refundByAliPay();
                    } else {
                        Toast.makeText(RefundOptionActivity.this, "账户或姓名不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!(TextUtils.isEmpty(etBankAccount.getText().toString().trim()) && TextUtils.isEmpty(etBankName.getText().toString().trim()) && TextUtils.isEmpty(etOwnerName.getText().toString().trim()))) {
                        refundByBank();
                    } else {
                        Toast.makeText(RefundOptionActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void refundByBank() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", refundInfoBean.getUserid());
        params.addQueryStringParameter("dataid", refundInfoBean.getDataid());
        params.addQueryStringParameter("joinid", refundInfoBean.getJoinid());
        params.addQueryStringParameter("reason", refundInfoBean.getReason());
        params.addQueryStringParameter("cardno", etBankAccount.getText().toString().trim());
        params.addQueryStringParameter("realname", etOwnerName.getText().toString().trim());
        params.addQueryStringParameter("bank", etBankName.getText().toString().trim());
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APPLY_REFUND + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (responseInfo.result != null) {
                    ApplyRefundBean applyRefundBean = new Gson().fromJson(responseInfo.result, ApplyRefundBean.class);
                    if (applyRefundBean.getStatus() == 1) {
                        Toast.makeText(RefundOptionActivity.this, "退款申请成功" + applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                        RefundOptionActivity.this.finish();
                    } else {
                        Toast.makeText(RefundOptionActivity.this, applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    private void refundByAliPay() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", refundInfoBean.getUserid());
        params.addQueryStringParameter("dataid", refundInfoBean.getDataid());
        params.addQueryStringParameter("joinid", refundInfoBean.getJoinid());
        params.addQueryStringParameter("reason", refundInfoBean.getReason());
        params.addQueryStringParameter("alipay", etAccount.getText().toString().trim());
        params.addQueryStringParameter("realname", etName.getText().toString().trim());
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APPLY_REFUND + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    ApplyRefundBean applyRefundBean = new Gson().fromJson(responseInfo.result, ApplyRefundBean.class);
                    if (applyRefundBean.getStatus() == 1) {
                        Toast.makeText(RefundOptionActivity.this, "退款申请成功" + applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                        RefundOptionActivity.this.finish();
                    } else {
                        Toast.makeText(RefundOptionActivity.this, applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RefundOptionActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
