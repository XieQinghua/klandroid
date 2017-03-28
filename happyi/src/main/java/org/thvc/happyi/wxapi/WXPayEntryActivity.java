package org.thvc.happyi.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.OrdinaryMypartyActivity;
import org.thvc.happyi.activity.PaymentActivity;
import org.thvc.happyi.bean.MoneyJoinNoityBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.Constants;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";

    public static final String WECHAT_APP_ID = "wx3cbe9547aab6bcb4";

    private IWXAPI api;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 支付类型
     */
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == 0) {//支付完成

            Participationnotice();

        } else {//错误或者取消
            Toast.makeText(WXPayEntryActivity.this, "微信付款失败", Toast.LENGTH_SHORT).show();
            finish();
        }

//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.app_tip);
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            builder.show();
//        }
    }


    /**
     * 支付成功 发送参与活动通知
     */
    public void Participationnotice() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("payno", PaymentActivity.payno);
        params.addQueryStringParameter("payerid", "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.JOIN_NOITY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MoneyJoinNoityBean moneyJoinNoityBean = ParseUtils.parseMoneyJoinNoityBean(result);
                    if (moneyJoinNoityBean.getStatus() == 1) {
                        Toast.makeText(WXPayEntryActivity.this, "微信付款成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WXPayEntryActivity.this, OrdinaryMypartyActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(WXPayEntryActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
            }
        });
    }

}