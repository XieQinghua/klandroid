package org.thvc.happyi.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import org.thvc.happyi.bean.FeedackBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/23.
 * 意见反馈页面
 */
public class FeedbackActivity extends BaseSwipeBackActivity {
    private String nickname;
    private String mobile;
    private String content;
    private String userid;
    private EditText ed_feedbacks;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        nickname = HappyiApplication.getInstance().getNickname(FeedbackActivity.this);
        mobile = HappyiApplication.getInstance().getUsername(FeedbackActivity.this);
        userid = HappyiApplication.getInstance().getUserid(FeedbackActivity.this);
        ed_feedbacks = (EditText) this.findViewById(R.id.ed_feedbacks);
        btn_submit = (Button) this.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = ed_feedbacks.getText().toString().trim();
                if (content.equals("")) {
                    Toast.makeText(FeedbackActivity.this, getString(R.string.enter_idea), Toast.LENGTH_SHORT).show();
                    return;
                }
                submitFeedback();
            }
        });
    }

    public void submitFeedback() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("content", content);
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("source", 2 + "");//安卓端
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.ADDFEEDBACK + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FeedackBean feedackBean = ParseUtils.parseFeedackBean(result);
                    if (feedackBean.getStatus() == 1) {
                        Toast.makeText(FeedbackActivity.this, getString(R.string.thanks_idea), Toast.LENGTH_SHORT).show();
                        ed_feedbacks.setText("");
                        finish();
                    }
                } else {
                    Toast.makeText(FeedbackActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
