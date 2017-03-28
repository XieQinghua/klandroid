package org.thvc.happyi.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.MoneyJoinNoityBean;
import org.thvc.happyi.bean.MoneyJoinPaynoBean;
import org.thvc.happyi.bean.PartyDetailappBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.Constants;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MD5;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.utils.PayResult;
import org.thvc.happyi.utils.SignUtils;
import org.thvc.happyi.utils.Util;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 颜松梁
 * Created by Administrator on 2015/12/1.
 * 付款页面
 */
public class PaymentActivity extends BaseSwipeBackActivity {
    private static final String TAG = "Pay";
    private String userid;//用户编号
    private ImageButton im_wechatpay;
    private ImageButton im_alipay;
    private RelativeLayout rlWechat;
    private RelativeLayout rlAliPay;
    private TextView tv_total;
    private TextView tv_Foundation;
    private TextView tv_FoundationMoney;
    private TextView tv_xuzhifu;
    private TextView tv_title;
    private String isPaymentmethod;
    private Button btn_Submit;
    private String title;
    public static String payno;
    private String subject;
    private String body;
    private String price;
    private String familyGgetPre;//家庭报名配比金额
    private String originalMoney;//家庭报名的原价
    private String prefee;
    private String getpre;
    private String fundName;
    private String dataid;
    private PartyDetailappBean partyDetailappBean;
    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    TextView show;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;


    // 商户PID
    public static final String PARTNER = "2088911923491539";
    // 商户收款账号
    public static final String SELLER = "happiyi01@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICXgIBAAKBgQDB+yYrrQzHNgtDZtaf2hFIXYlyjnY/oLsCV2kOOC7C39X5FxhzjmGYDv9C4EfgGG4uot5qkFc4gsmvioKRWhDX0PDZV879jwqu/lxMwJ08OTqd8F7mNNwW+9F3far3RJ6ywLnfuD4AE2ZnYj05e/DQ2XF2sF96aCigGpCndPSGuQIDAQABAoGAIe9W0SM37g3FUKhH+bUDev5qEjFb/GuY66LxDPzFKXBRFX911plc92YAP+nOiSSc+emiMVBJAejXca8wgk8YTOp02xriWurFkxlAo8rlzCqAmLa8ZCfMTazL1Wjvv1Aom9e9jRo0YJJzZ03/UoJwEugKfXS0mzMiI/pcoV7EV4ECQQD9J1QIczk+cLpV0uZJ63J3pXZmQ/khCUGsb034BRCYWsZJDr0IJaTmX9VjLQNjUvu77Q9x+XBgp3Xf4gz+kbVJAkEAxCl/9PYKiULPJlfaANoih2YlvukXL3TXNLBrjbSLD1FX4jZ+Q4tD6b/cQMkPc0fnSdTA7yyttJcLcuouwvr18QJBANMGwLcWjeZuA/ZdCxkditsP9AXFrdwLIlLCqeURG8dVDCuEfLFY6AAebPvyXvuiBjSepZxlyXbYwZryjdB71/kCQQCPoFcpE7E5OSqVR4O+VfksLDV86Avq2mDsGjRVyBh9ebCgWAfxCpoKH0l0QgLHBKGrZFRzisIQ1Ps0A5WDe97xAkEAo7zlG1MoK+/59+tSymTSKjm3ChemfwPvy7TtUtLxd0waG8auQVFOU0jzlJt/BZgYnGGlHq9C2yiIkXDbZUEpFA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private String joined;//报名号
    private String payment;


    private IWXAPI api;
    private ProgressDialog dialog;
    private String packageValue;


    private RelativeLayout relativ_layout_payment;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PaymentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Participationnotice();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PaymentActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PaymentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(PaymentActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_new);
        dataid = getIntent().getStringExtra("dataId");
        joined = getIntent().getStringExtra("joined");
        payment = getIntent().getStringExtra("payment");
        originalMoney = getIntent().getStringExtra("originalPrice");
        familyGgetPre = getIntent().getStringExtra("getPre");
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(Constants.APP_ID);

        tv_total = (TextView) this.findViewById(R.id.tv_total);
        tv_Foundation = (TextView) this.findViewById(R.id.tv_Foundation);
        tv_FoundationMoney = (TextView) this.findViewById(R.id.tv_FoundationMoney);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_xuzhifu = (TextView) this.findViewById(R.id.tv_xuzhifu);
        relativ_layout_payment = (RelativeLayout) this.findViewById(R.id.relativ_layout_payment);

        relativ_layout_payment.setVisibility(View.VISIBLE);

        im_wechatpay = (ImageButton) this.findViewById(R.id.im_wechatpay);
        im_alipay = (ImageButton) this.findViewById(R.id.im_alipay);
        btn_Submit = (Button) this.findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new MyOnClickListener());
        userid = HappyiApplication.getInstance().getUserid(this);
        rlAliPay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) findViewById(R.id.rl_wechat);
        rlWechat.setOnClickListener(new MyOnClickListener());
        rlAliPay.setOnClickListener(new MyOnClickListener());
        isPaymentmethod = "Wechat";

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        getPartyData();
    }

    /**
     * 计算价格
     */
    private void caculatePrice() {
        int prefees = (int) Float.parseFloat(prefee);
        int getpres = (int) Float.parseFloat(getpre);

        String str = String.valueOf(prefees - getpres);
        String strc = String.valueOf(prefees);
        String strcs = String.valueOf(getpres);

        tv_xuzhifu.setText("￥" + str);
        //tv_title.setText(title);
        tv_Foundation.setText(fundName + "为您节省了");
        tv_total.setText("原价" + strc + "元/人");
        tv_total.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tv_FoundationMoney.setText(strcs + "元");
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_wechat:
                    im_wechatpay.setImageResource(R.drawable.btn_selected);
                    im_alipay.setImageResource(R.drawable.btn_unselected);
                    isPaymentmethod = "Wechat";
                    break;
                case R.id.rl_alipay:
                    im_alipay.setImageResource(R.drawable.btn_selected);
                    im_wechatpay.setImageResource(R.drawable.btn_unselected);
                    isPaymentmethod = "Alipay";
                    break;
                case R.id.btn_Submit:
                    MoneyjoinPayno();
                    break;
            }
        }
    }

    private void getPartyData() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("id", dataid);
        if (!userid.equals("")) {
            params.addQueryStringParameter("userid", userid);
        }
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_DETAIL + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    partyDetailappBean = ParseUtils.parsePartyDetailappBean(result);
                    if (partyDetailappBean.getStatus() == 1) {
                        if (partyDetailappBean.getData().getFund() != null) {
                            fundName = partyDetailappBean.getData().getFund().getNickname();
                        } else {
                            fundName = "芒果V基金";//TODO 此处在没有返回基金会时写死
                        }
                        //家庭报名和个人报名价格不一样
                        if (payment.equals("family")) {
                            getpre = familyGgetPre;
                            prefee = originalMoney;
                        } else {
                            getpre = partyDetailappBean.getData().getGetpre();
                            prefee = partyDetailappBean.getData().getPrefee();
                        }
                        caculatePrice();
                    } else {
                        Toast.makeText(PaymentActivity.this, partyDetailappBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

    private void Submit() {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    finish();
                                }
                            }).show();
            return;
        }
        // 订单
        String orderInfo = getOrderInfo(subject, body, price, payno);
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PaymentActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String payno) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + payno + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额  price
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://www.happiyi.com/index.php/Order/paynotify/payno/" + payno
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"http://www.happiyi.com/index.php/Order/paysuccess/payno/" + payno + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    public void MoneyjoinPayno() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("joinid", joined);
        params.addQueryStringParameter("payment", isPaymentmethod);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.JOIN_PAY_NO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MoneyJoinPaynoBean moneyJoinPaynoBean = ParseUtils.parseMoneyJoinPaynoBean(result);
                    if (moneyJoinPaynoBean.getStatus() == 1) {
                        payno = moneyJoinPaynoBean.getData().getPayno();
                        subject = moneyJoinPaynoBean.getData().getData().getParty().getTitle();
                        body = moneyJoinPaynoBean.getData().getData().getParty().getDescription();
                        price = moneyJoinPaynoBean.getData().getData().getPayfee();

                        if (isPaymentmethod.equals("Alipay")) {
                            Submit();
                        } else if (isPaymentmethod.equals("Wechat")) {
                            /**一、APP支付生成预支付订单*/
                            GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
                            getPrepayId.execute();

                        }
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion1", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion2", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        Log.e("orion3", sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(PaymentActivity.this, getString(R.string.app_tip),
                    getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//            show.setText(sb.toString());
            resultunifiedorder = result;

            /**二、生成APP微信支付参数*/
            genPayReq();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            Log.e("orion4", entity);
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("orion5", content);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion6", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取预支付订单号:
     * prepay_id（服务器完成）！！！
     * 注意：如果服务端开发文档跟客户端demo里的参数不一样，以demo里的参数为准，
     * 否则服务器传过来的参数无法调起微信支付！！！
     */
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", subject));
            /**这里用的是mach_id,跟sign签名时参数名不同，一定要注意*/
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://www.happiyi.com/Wxpay/wxnotify"));//接收微信支付成功通知的链接：http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php
            packageParams.add(new BasicNameValuePair("out_trade_no", payno));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "196.168.1.1"));
            packageParams.add(new BasicNameValuePair("total_fee", (int) (100 * Double.parseDouble(price)) + ""));//订单总金额,单位为分,不能带小数点,int类型
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);

            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            //解决body传中文报签名错误的问题，生成的xml请求参数转为字节数组后，用“ISO8859-1”编码格式进行编码为字符
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    private void genPayReq() {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
//        show.setText(sb.toString());
        Log.e("orion7", signParams.toString());

        /**三、调起微信支付*/
        sendPayReq();
    }

    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }


    /**
     * 支付成功 发送参与活动通知
     */
    public void Participationnotice() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("payno", payno);
        params.addQueryStringParameter("payerid", "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.JOIN_NOITY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MoneyJoinNoityBean moneyJoinNoityBean = ParseUtils.parseMoneyJoinNoityBean(result);
                    if (moneyJoinNoityBean.getStatus() == 1) {
                        startActivity(new Intent(PaymentActivity.this, OrdinaryMypartyActivity.class));
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
