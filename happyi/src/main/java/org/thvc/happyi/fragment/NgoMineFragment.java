package org.thvc.happyi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.Release.NgoDataDao;
import org.thvc.happyi.Release.NgoDataText;
import org.thvc.happyi.Zxing.MipcaActivityCapture;
import org.thvc.happyi.activity.AuthManageActivity;
import org.thvc.happyi.activity.MessageActivity;
import org.thvc.happyi.activity.NgoFansActivity;
import org.thvc.happyi.activity.NgoCollectActivity;
import org.thvc.happyi.activity.NgoDataActivity;
import org.thvc.happyi.activity.NgoMyPartyActivity;
import org.thvc.happyi.activity.RefundActivity;
import org.thvc.happyi.activity.SettingActivity;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.NgoDetailbean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

/**
 * 项目名称：klandroid
 * 类描述：我的页面
 * 创建人：颜松梁.
 * 创建时间：2015/11/12
 * 修改人：Administrator
 * 修改时间：2015/11/12
 * 修改备注：
 */
public class NgoMineFragment extends Fragment {
    private View view;
    private RelativeLayout ngo_personal_data;//个人信息
    private CircleImageView im_user_img;//头像
    private TextView iv_user_name;//昵称
    private TextView tv_ngo_fans;

    private TextView tv_MyParty;//我的活动按钮
    private TextView tv_MyFollow;//我的关注
    private TextView tv_Auth;//认证管理
    private TextView tv_Refund;//退款
    private TextView tv_Set;//设置
    private TextView tv_Saoyisao;//扫一扫

    private ImageView iv_Message;//消息
    private TextView tv_message;//未读消息红点

    private NgoDataText ngoDataText;
    private String userid;

    public static final int LOADING_DIALOG = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ngo_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid = HappyiApplication.getInstance().getUserid(getActivity());
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        ngo_personal_data = (RelativeLayout) getView().findViewById(R.id.ngo_personal_data);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = ngo_personal_data.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;
        ngo_personal_data.setLayoutParams(para);

        im_user_img = (CircleImageView) getView().findViewById(R.id.im_user_img);
        tv_ngo_fans = (TextView) getView().findViewById(R.id.tv_ngo_fans);
        tv_MyParty = (TextView) getView().findViewById(R.id.tv_MyParty);
        tv_MyFollow = (TextView) getView().findViewById(R.id.tv_MyFollow);
        tv_Auth = (TextView) getView().findViewById(R.id.tv_Auth);
        tv_Refund = (TextView) getView().findViewById(R.id.tv_Refund);
        tv_Set = (TextView) getView().findViewById(R.id.tv_Set);
        tv_Saoyisao = (TextView) getView().findViewById(R.id.tv_Saoyisao);
        iv_user_name = (TextView) getView().findViewById(R.id.iv_user_name);
        tv_message = (TextView) getView().findViewById(R.id.tv_message);
        iv_Message = (ImageView) getView().findViewById(R.id.iv_Message);


        tv_MyParty.setOnClickListener(new MyOnClickListener());
        tv_MyFollow.setOnClickListener(new MyOnClickListener());
        tv_Auth.setOnClickListener(new MyOnClickListener());
        tv_Refund.setOnClickListener(new MyOnClickListener());
        tv_Set.setOnClickListener(new MyOnClickListener());
        tv_Saoyisao.setOnClickListener(new MyOnClickListener());
        tv_ngo_fans.setOnClickListener(new MyOnClickListener());
        iv_Message.setOnClickListener(new MyOnClickListener());
        im_user_img.setOnClickListener(new MyOnClickListener());
        String solevar = HappyiApplication.getInstance().getSolevar(getActivity());
        GetFansShuLiang();
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 查询数据库
         */
        NgoDataDao ngoDataDao = new NgoDataDao(getActivity());
        ngoDataText = ngoDataDao.select(userid);
        iv_user_name.setText(ngoDataText.getNickname());
        ImgUtils.setHeadImage(im_user_img, ngoDataText.getHeadpic());
    }

    public void GetFansShuLiang() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FIND_NGO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NgoDetailbean ngoDetailbean = ParseUtils.parseNgoDetailbean(result);
                    if (ngoDetailbean.getStatus() == 1) {
                        tv_ngo_fans.setText("粉丝  " + ngoDetailbean.getData().getCollect());
                        if (ngoDetailbean.getData().getMessagecount().equals("0")) {
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

    /**
     * 点击跳转操作
     */
    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_user_img:
                    startActivity(new Intent(getActivity(), NgoDataActivity.class));//跳转到NGO基本资料页面
                    break;
                case R.id.iv_Message:
                    startActivity(new Intent(getActivity(), MessageActivity.class));//跳转到消息页面
                    break;
                case R.id.tv_ngo_fans:
                    startActivity(new Intent(getActivity(), NgoFansActivity.class));//跳转到粉丝页面
                    break;
                case R.id.tv_MyParty:
                    startActivity(new Intent(getActivity(), NgoMyPartyActivity.class));//跳转到我的活动页面
                    break;
                case R.id.tv_MyFollow:
                    startActivity(new Intent(getActivity(), NgoCollectActivity.class));//跳转到我的关注页面
                    break;
                case R.id.tv_Auth:
                    startActivity(new Intent(getActivity(), AuthManageActivity.class));//跳转到认证管理页面
                    break;
                case R.id.tv_Refund:
                    startActivity(new Intent(getActivity(), RefundActivity.class));//跳转到退款操作页面
                    break;
                case R.id.tv_Set:
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    break;
                case R.id.tv_Saoyisao:
                    startActivity(new Intent(getActivity(), MipcaActivityCapture.class));
                    break;
            }
        }
    }
}
