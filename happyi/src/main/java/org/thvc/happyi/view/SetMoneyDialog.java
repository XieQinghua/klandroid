package org.thvc.happyi.view;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import org.thvc.happyi.bean.NGOSetMoneyBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;

/**
 * NGO设置让利的对话框
 * Created by huangxinqi on 2016/3/30.
 */
public class SetMoneyDialog {
    private Context context;
    private AlertDialog alertDialog;
    private int dataId;
    private EditText et_set_money;
    private EditText et_content;
    private Button btn_cancel;
    private Button btn_set;
    private int money;
    private String content;

    /**
     * dataId为活动编号
     *
     * @param context
     * @param dataId
     */
    public SetMoneyDialog(final Context context, int dataId) {
        this.dataId = dataId;
        this.context = context;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_set_money);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        et_content = (EditText) window.findViewById(R.id.et_content);
        et_set_money = (EditText) window.findViewById(R.id.et_set_money);
        btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
        btn_set = (Button) window.findViewById(R.id.btn_set);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_set_money.getText().toString())) {
                    Toast.makeText(context, "请输入让利百分比", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(et_set_money.getText().toString())) {
                    Toast.makeText(context, "请输入正确参数", Toast.LENGTH_SHORT).show();
                    return;
                }
                money = Integer.parseInt(et_set_money.getText().toString());
                if (money >= 0 && money <= 100) {
                    content = et_content.getText().toString();
                    setMoney();
                } else {
                    Toast.makeText(context, "让利范围1-100%", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 给接口发送请求
     */
    private void setMoney() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(context));
        params.addQueryStringParameter("id", dataId + "");
        params.addQueryStringParameter("count", money + "");
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.NGO_SET_MONEY + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NGOSetMoneyBean ngoSetMoneyBean = new Gson().fromJson(result, NGOSetMoneyBean.class);
                    if (ngoSetMoneyBean.getStatus() == 1) {
                        Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "设置失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT).show();
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
