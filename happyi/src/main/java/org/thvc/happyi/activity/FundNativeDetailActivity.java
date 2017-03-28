package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.FundNativeDetailBean;
import org.thvc.happyi.fragment.BaseFragment1;
import org.thvc.happyi.fragment.FundNativeHomeFragment;
import org.thvc.happyi.fragment.FundNativePartyFragment;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

/**
 * Created by Administrator on 2016/3/21.
 * 原生基金会详情页面
 */
public class FundNativeDetailActivity extends BaseSwipeBackActivity {
    private CircleImageView iv_fund_img;
    private TextView tv_fund_fans, tv_collect, tv_fund_name;

    private LinearLayout relative_homepage, relative_pary;
    private TextView tv_homepage, tv_pary;
    private TextView view_homepage, view_pary;
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private BaseFragment1 bf;

    private RelativeLayout rl_layout_title;

    private String id;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_native_details);
        userid = HappyiApplication.getInstance().getUserid(FundNativeDetailActivity.this);

        //创建一个fragment管理器
        fragmentManager = getFragmentManager();
        rl_layout_title = (RelativeLayout) findViewById(R.id.rl_layout_title);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = rl_layout_title.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;
        rl_layout_title.setLayoutParams(para);

        iv_fund_img = (CircleImageView) findViewById(R.id.iv_fund_img);
        tv_fund_fans = (TextView) findViewById(R.id.tv_fund_fans);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_fund_name = (TextView) findViewById(R.id.tv_fund_name);

        tv_collect = (TextView) findViewById(R.id.tv_collect);
        relative_homepage = (LinearLayout) this.findViewById(R.id.relative_homepage);
        relative_pary = (LinearLayout) this.findViewById(R.id.relative_pary);
        tv_homepage = (TextView) this.findViewById(R.id.tv_homepage);
        tv_pary = (TextView) this.findViewById(R.id.tv_pary);
        view_homepage = (TextView) this.findViewById(R.id.view_homepage);
        view_pary = (TextView) this.findViewById(R.id.view_pary);
        relative_homepage.setOnClickListener(new MyOnClickListener());
        relative_pary.setOnClickListener(new MyOnClickListener());

        id = getIntent().getStringExtra("id");
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userid.equals("")) {
                    String status = "";
                    String collect = tv_collect.getText().toString().trim();
                    if (collect.equals("已关注")) {
                        status = "1";
                    } else if (collect.equals("关注")) {
                        status = "2";
                    }
                    collectNgo(status);
                } else {
                    startActivity(new Intent(FundNativeDetailActivity.this, LoginActivity.class));
                }
            }
        });

        GetcollectFoun();

        if (!(bf instanceof FundNativeHomeFragment)) {
            bf = new FundNativeHomeFragment();
            replaceFragment(bf);
        }
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relative_homepage:
                    tv_homepage.setTextColor(getResources().getColor(R.color.orangered));
                    view_homepage.setVisibility(View.VISIBLE);
                    tv_pary.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_pary.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof FundNativeHomeFragment)) {
                        bf = new FundNativeHomeFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.relative_pary:
                    tv_pary.setTextColor(getResources().getColor(R.color.orangered));
                    view_pary.setVisibility(View.VISIBLE);
                    tv_homepage.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_homepage.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof FundNativePartyFragment)) {
                        bf = new FundNativePartyFragment();
                        replaceFragment(bf);
                    }
                    break;
            }
        }
    }

    private void collectNgo(final String status) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("status", status);//TODO 此处要做详情页面如果显示“已关注”按键考虑，暂定写死只做2添加关注操作
        params.addQueryStringParameter("dataid", id);
        params.addQueryStringParameter("type", 3 + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CollectBean collectBean = ParseUtils.parseCollectBean(result);
                    if (collectBean.getStatus() == 1) {
                        if (status.equals("1")) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_oranged_bg));
                            tv_collect.setText("关注");
                        } else if (status.equals("2")) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_gray_bg));
                            tv_collect.setText("已关注");
                        }
                    }
                } else {
                    Toast.makeText(FundNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });

    }


    public void GetcollectFoun() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", id);
        if (HappyiApplication.getInstance().getUserid(FundNativeDetailActivity.this) != null) {
            params.addQueryStringParameter("loginuser", HappyiApplication.getInstance().getUserid(FundNativeDetailActivity.this));
        }
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FIND_FUND + url, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FundNativeDetailBean fundNativeDetailBean = ParseUtils.parseFundNativeDetailBean(result);
                    if (fundNativeDetailBean.getStatus() == 1) {
                        ImgUtils.setHeadImage(iv_fund_img, fundNativeDetailBean.getData().getHeadpic());
                        tv_fund_fans.setText(fundNativeDetailBean.getData().getCollect());
                        if (fundNativeDetailBean.getData().getHascollect() == 1) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_gray_bg));
                            tv_collect.setText("已关注");
                        } else if (fundNativeDetailBean.getData().getHascollect() == 0) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_oranged_bg));
                            tv_collect.setText("关注");
                        }
                        tv_fund_name.setText(fundNativeDetailBean.getData().getNickname());
                    }
                } else {
                    Toast.makeText(FundNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
            }
        });
    }

    /**
     * 替换一个Fragment
     *
     * @param activeFragment
     */
    private void replaceFragment(BaseFragment1 activeFragment) {
        // 通过管理器开启一个事物
        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.fund_frame, activeFragment);// 替换
        tran.commit(); // 提交事物
    }

}
