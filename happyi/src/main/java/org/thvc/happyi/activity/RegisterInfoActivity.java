package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.RegisterInfoAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.RegisterInformationBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/25.
 * 我的活动报名信息页面
 * 修改：huangxinqi
 * 修改内容：补充点击活动名称进入活动详情页
 */
public class RegisterInfoActivity extends BaseSwipeBackActivity {
    private TextView tv_party_name;
    private TextView tv_deadline;
    private TextView tv_Notbeginning;
    private TextView tv_has_good;
    private TextView tv_location;
    private ListView lv_pay_list;
    private RelativeLayout rlPartyDetail;
    private List<RegisterInformationBean.DataEntity.ListEntity> list;
    private List<RegisterInformationBean.DataEntity.ListEntity> lists = new ArrayList<>();
    private String title;
    private String actbegin;
    private String dataid;
    private String isdel;
    private String userid;
    private String hasGood;
    private String location;
    private RegisterInfoAdapter registerInfoAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1000) {
                registerInfoAdapter.notifyDataSetChanged();
                RegisterInfoActivity.this.onResume();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        title = getIntent().getStringExtra("title");
        actbegin = getIntent().getStringExtra("actbegin");
        dataid = getIntent().getStringExtra("dataid");
        isdel = getIntent().getStringExtra("isdel");
        location = getIntent().getStringExtra("location");
        hasGood = getIntent().getStringExtra("hasGood");
        init();
    }

    public void init() {
        tv_has_good = (TextView) findViewById(R.id.tv_has_good);
        tv_location = (TextView) this.findViewById(R.id.tv_location);
        tv_party_name = (TextView) this.findViewById(R.id.tv_party_name);
        tv_deadline = (TextView) this.findViewById(R.id.tv_deadline);
        tv_Notbeginning = (TextView) this.findViewById(R.id.tv_Notbeginning);
        lv_pay_list = (ListView) this.findViewById(R.id.lv_pay_list);
        rlPartyDetail = (RelativeLayout) this.findViewById(R.id.rl_party_detail);
        rlPartyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterInfoActivity.this, PartyNativeDetailActivity.class);
                intent.putExtra("id", dataid);
                startActivity(intent);
            }
        });
        tv_has_good.setText(hasGood + "人喜欢");
        tv_location.setText(location);
        tv_party_name.setText(title);
        tv_deadline.setText(getStringTime(actbegin));//注意！此处已经改成活动结束时间 2016.04.01修改
        if (isdel.equals("3")) {
            tv_Notbeginning.setText("认证中");
        } else if (isdel.equals("4")) {
            tv_Notbeginning.setText("认领中");
        } else if (isdel.equals("5")) {
            tv_Notbeginning.setText("认证确认中");
        } else if (isdel.equals("6")) {
            tv_Notbeginning.setText("报名中");
        } else if (isdel.equals("7")) {
            tv_Notbeginning.setText("进行中");
        } else if (isdel.equals("8")) {
            tv_Notbeginning.setText("已结束");
        } else {
            tv_Notbeginning.setVisibility(View.VISIBLE);
        }

       /* if (isdel.equals("4")) {
            tv_Notbeginning.setText("未开始");
        } else {
            tv_Notbeginning.setVisibility(View.VISIBLE);
        }*/
        registerInfoAdapter = new RegisterInfoAdapter(handler, this, lists, title, dataid, isdel);
        lv_pay_list.setAdapter(registerInfoAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lists.clear();
        getRegisterInformationList();
    }

    public void getRegisterInformationList() {
        userid = HappyiApplication.getInstance().getUserid(RegisterInfoActivity.this);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("dataid", dataid);
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.MY_PARTY_JOIN_PEOPLE + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RegisterInformationBean registerInformationBean = ParseUtils.parseRegisterInformationBean(result);
                    if (registerInformationBean.getStatus() == 1) {
                        list = registerInformationBean.getData().getList();
                        if (list != null && list.size() != 0) {
                            lists.addAll(list);
                            registerInfoAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(RegisterInfoActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}
