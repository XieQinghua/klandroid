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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.Release.DataDao;
import org.thvc.happyi.Release.DataText;
import org.thvc.happyi.Zxing.MipcaActivityCapture;
import org.thvc.happyi.activity.MessageActivity;
import org.thvc.happyi.activity.MyCollectActivity;
import org.thvc.happyi.activity.OrdinaryMypartyActivity;
import org.thvc.happyi.activity.PersonalDataActivity;
import org.thvc.happyi.activity.RefundRecordActivity;
import org.thvc.happyi.activity.SettingActivity;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.InformationBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/17.
 * 普通用户个人中心
 */
public class OrdinaryMineFragment extends Fragment {
    private View view;
    private RelativeLayout personal_data;
    private CircleImageView im_ord_img;//头像

    private TextView iv_user_name;
    private TextView iv_user_content;

    private ImageView iv_Message;
    private TextView tv_message;//未读消息红点
    private String userid;

    private TextView tv_Activit;//我的活动
    private TextView tv_saoyisao;//扫一扫
    private TextView tv_Follow;//我的关注
    private TextView tv_Set;//设置
    private TextView tv_refund;//退款记录
    private DataText dataText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ordinary_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userid = HappyiApplication.getInstance().getUserid(getActivity());

        personal_data = (RelativeLayout) getView().findViewById(R.id.personal_data);//个人资料
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = personal_data.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;
        personal_data.setLayoutParams(para);
        tv_refund = (TextView) getView().findViewById(R.id.tv_refund);
        iv_user_name = (TextView) getView().findViewById(R.id.iv_user_name);
        iv_user_content = (TextView) getView().findViewById(R.id.iv_user_content);

        tv_message = (TextView) getView().findViewById(R.id.tv_message);

        im_ord_img = (CircleImageView) getView().findViewById(R.id.im_ord_img);//头像

        tv_Activit = (TextView) getView().findViewById(R.id.tv_Activit);//我的活动
        tv_saoyisao = (TextView) getView().findViewById(R.id.tv_saoyisao);//扫一扫
        tv_Follow = (TextView) getView().findViewById(R.id.tv_Follow);//我的关注
        iv_Message = (ImageView) getView().findViewById(R.id.iv_Message);//我的消息
        tv_Set = (TextView) getView().findViewById(R.id.tv_Set);//设置

        //设置点击事件
        tv_Activit.setOnClickListener(new MyOnClickListener());
        tv_saoyisao.setOnClickListener(new MyOnClickListener());
        tv_Follow.setOnClickListener(new MyOnClickListener());
        iv_Message.setOnClickListener(new MyOnClickListener());
        tv_Set.setOnClickListener(new MyOnClickListener());
        im_ord_img.setOnClickListener(new MyOnClickListener());
        tv_refund.setOnClickListener(new MyOnClickListener());
    }


    @Override
    public void onResume() {
        super.onResume();
        /**
         * 查询数据库
         */
        DataDao dataDao = new DataDao(getActivity());
        dataText = dataDao.select(userid);
        iv_user_name.setText(dataText.getNickname());
        iv_user_content.setText(dataText.getContent());
        ImgUtils.setHeadImage(im_ord_img, dataText.getHeadpic());
        Getinformation();
    }

    /**
     * 点击事件
     */
    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_ord_img:
                    startActivity(new Intent(getActivity(), PersonalDataActivity.class));//跳转到个人信息页面
                    break;
                case R.id.tv_Activit://我的活动点击事件
                    startActivity(new Intent(getActivity(), OrdinaryMypartyActivity.class));//跳转到我的活动页面
                    break;
//                case R.id.tv_myCoupon:
//                    startActivity(new Intent(getActivity(), MyTicketActivity.class).putExtra("Ticket", "MyTicket"));//跳转到我的活动劵页面
//                    break;
                case R.id.tv_saoyisao:
                    startActivity(new Intent(getActivity(), MipcaActivityCapture.class));//扫一扫
                    break;
                case R.id.tv_Follow://我的关注点击事件
                    startActivity(new Intent(getActivity(), MyCollectActivity.class));//跳转到我的关注页面
                    break;
                case R.id.iv_Message://我的消息点击事件
                    startActivity(new Intent(getActivity(), MessageActivity.class));//跳转到消息页面
                    break;
                case R.id.tv_refund:
                    startActivity(new Intent(getActivity(), RefundRecordActivity.class));//跳转到退款记录页面
                    break;
                case R.id.tv_Set://设置点击事件
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    break;
            }

        }
    }

    public void Getinformation() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FIND_ORDINARY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    InformationBean informationBean = ParseUtils.parseInformationBean(result);
                    if (informationBean.getData().getMessagecount().equals("0")) {
                        tv_message.setVisibility(View.GONE);
                    } else {
                        tv_message.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
