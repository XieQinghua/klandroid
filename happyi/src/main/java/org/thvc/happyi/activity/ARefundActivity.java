package org.thvc.happyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.ARefundBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 颜松梁
 * Created by Administrator on 2015/12/7.
 * 确定退款页面
 */
public class ARefundActivity extends BaseSwipeBackActivity {
    private CircleImageView im_user_img;
    private TextView iv_user_name;
    private TextView tv_Refund_amount;
    private TextView tv_party_title;
    private TextView tv_party_time;
    private TextView tv_reason;
    private Button btn_Confirmation_refund;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arefund);

        id = getIntent().getStringExtra("id");

        im_user_img = (CircleImageView) this.findViewById(R.id.im_user_img);
        iv_user_name = (TextView) this.findViewById(R.id.iv_user_name);
        tv_Refund_amount = (TextView) this.findViewById(R.id.tv_Refund_amount);
        tv_party_title = (TextView) this.findViewById(R.id.tv_party_title);
        tv_party_time = (TextView) this.findViewById(R.id.tv_party_time);
        tv_reason = (TextView) this.findViewById(R.id.tv_reason);
        btn_Confirmation_refund = (Button) this.findViewById(R.id.btn_Confirmation_refund);
        btn_Confirmation_refund.setOnClickListener(new MyOnClickListener());

        ImgUtils.setCircleImage(im_user_img, getIntent().getStringExtra("head"));
        iv_user_name.setText(getIntent().getStringExtra("nickname"));
        tv_Refund_amount.setText("退款金额：" + getIntent().getStringExtra("payfee"));
        tv_party_title.setText(getIntent().getStringExtra("title"));
        tv_party_time.setText("活动报名截止时间：" + getStringTime(getIntent().getStringExtra("enrollend")));
        tv_reason.setText("退款原因：" + getIntent().getStringExtra("reason"));
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }


    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            HttpUtils httpUtils = new HttpUtils();
            MyRequestParams params = new MyRequestParams();//定义访问服务器参数
            params.addQueryStringParameter("id", id);
            params.addQueryStringParameter("isdel", "3");
            String url = params.myRequestParams(params);
            httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CONFIRM_REFUND + url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    if (new JsonValidator().validate(result)) {
                        ARefundBean aRefundBean = ParseUtils.parseARefundBean(result);
                        if (aRefundBean.getStatus() == 1) {
                            Toast.makeText(ARefundActivity.this, aRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                        } else if (aRefundBean.getStatus() == -1) {
                            Toast.makeText(ARefundActivity.this, aRefundBean.getInfo() + "，请不要重新退款", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ARefundActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

                }
            });
        }
    }

}
