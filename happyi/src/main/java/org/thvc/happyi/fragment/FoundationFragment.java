package org.thvc.happyi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.Release.FounDataDao;
import org.thvc.happyi.Release.FounDataText;
import org.thvc.happyi.activity.AccountManageActivity;
import org.thvc.happyi.activity.ClaimPartyActivity;
import org.thvc.happyi.activity.FundDataActivity;
import org.thvc.happyi.activity.MessageActivity;
import org.thvc.happyi.activity.NgoFansActivity;
import org.thvc.happyi.activity.NoClaimPartyActivity;
import org.thvc.happyi.activity.SettingActivity;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.FoundationBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/23.
 * 基金会管理中心
 */
public class FoundationFragment extends Fragment {
    private View view;
    private RelativeLayout rl_fund;
    private CircleImageView im_user_img;//头像
    private TextView iv_user_name;//昵称
    private TextView tv_fund_fans;//粉丝
    private TextView tv_Claim;
    private TextView tv_Claimed;
    private TextView tv_Set;
    private TextView tv_message;
    private TextView tv_account_management;
    public static final int LOADING_DIALOG = 0;


    private FounDataText founDataText;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fund_mine, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid = HappyiApplication.getInstance().getUserid(getActivity());
        init();
    }

    private void init() {
        rl_fund = (RelativeLayout) getView().findViewById(R.id.rl_fund);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = rl_fund.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;//活动主题图片长高设置为屏幕宽度2/3
        rl_fund.setLayoutParams(para);
        im_user_img = (CircleImageView) getView().findViewById(R.id.im_user_img);
        iv_user_name = (TextView) getView().findViewById(R.id.iv_user_name);
        tv_fund_fans = (TextView) getView().findViewById(R.id.tv_fund_fans);
        tv_Claim = (TextView) getView().findViewById(R.id.tv_Claim);
        tv_Claimed = (TextView) getView().findViewById(R.id.tv_Claimed);
        tv_Set = (TextView) getView().findViewById(R.id.tv_Set);
        tv_message = (TextView) getView().findViewById(R.id.tv_message);
        tv_account_management = (TextView) getView().findViewById(R.id.tv_account_management);
        tv_account_management.setOnClickListener(new MyOnClickListener());

        im_user_img.setOnClickListener(new MyOnClickListener());
        tv_fund_fans.setOnClickListener(new MyOnClickListener());
        tv_message.setOnClickListener(new MyOnClickListener());
        tv_Claim.setOnClickListener(new MyOnClickListener());
        tv_Claimed.setOnClickListener(new MyOnClickListener());
        tv_Set.setOnClickListener(new MyOnClickListener());
        userid = HappyiApplication.getInstance().getUserid(getActivity());
        getFoudation();
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 查询数据库
         */
        FounDataDao founDataDao = new FounDataDao(getActivity());
        founDataText = founDataDao.select(userid);
        iv_user_name.setText(founDataText.getNickname());
        ImgUtils.setHeadImage(im_user_img, founDataText.getHeadpic());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_user_img:
                    startActivity(new Intent(getActivity(), FundDataActivity.class));//跳转到基金会个人中心页面
                    break;
                case R.id.tv_fund_fans:
                    startActivity(new Intent(getActivity(), NgoFansActivity.class));//跳转到粉丝页面
                    break;
                case R.id.tv_message:
                    startActivity(new Intent(getActivity(), MessageActivity.class));//跳转到消息页面
                    break;
                case R.id.tv_Claim:
                    startActivity(new Intent(getActivity(), NoClaimPartyActivity.class));//跳转到待认领活动页面
                    break;
                case R.id.tv_Claimed:
                    startActivity(new Intent(getActivity(), ClaimPartyActivity.class));//跳转到已认领活动页面
                    break;
                case R.id.tv_account_management:
                    startActivity(new Intent(getActivity(), AccountManageActivity.class));//跳转到账户管理页面
                    break;
                case R.id.tv_Set:
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    break;
            }
        }
    }


    public void getFoudation() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FIND_FUND + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FoundationBean foundationBean = ParseUtils.parseFoundationBean(result);
                    if (foundationBean.getStatus() == 1) {
                        iv_user_name.setText(foundationBean.getData().getNickname());
                        tv_fund_fans.setText("粉丝  " + foundationBean.getData().getCollect());
                        if (foundationBean.getData().getMessagecount().equals("0")) {
                            tv_message.setVisibility(View.GONE);
                        } else {
                            tv_message.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                LogUtils.e(s);
            }

        });

    }

}
