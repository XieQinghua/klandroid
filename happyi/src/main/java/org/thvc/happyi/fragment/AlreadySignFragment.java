package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.SignAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.SignBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * 已签到页面
 * Created by Administrator on 2016/3/24.
 */
public class AlreadySignFragment extends BaseFragment {

    private ListView alreadysign_list;
    private String partyId;
    private ArrayList<SignBean.DataBean.ListBean> list;
    private TextView tv_empty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_already_sign, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alreadysign_list = (ListView) getView().findViewById(R.id.alreadysign_list);
        partyId = getActivity().getIntent().getStringExtra("partyId");
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        getDataLists("1");
    }

    public void getDataLists(final String scan) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        String sss = HappyiApplication.getInstance().getUserid(getActivity());
        params.addQueryStringParameter("dataid", partyId);
        params.addQueryStringParameter("userid", sss);
        params.addQueryStringParameter("scan", scan);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_JOIN_Party_PEOPLE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    SignBean signBean = ParseUtils.parseSignBean(result);
                    if (signBean.getStatus() == 1) {
                        if (list != null && list.size() != 0) {
                            list = (ArrayList<SignBean.DataBean.ListBean>) signBean.getData().getList();
                            SignAdapter adapter = new SignAdapter(list, getActivity());
                            alreadysign_list.setAdapter(adapter);
                        } else {
                            tv_empty.setVisibility(View.VISIBLE);
                            alreadysign_list.setVisibility(View.GONE);
                            tv_empty.setText("抱歉，还没有人来签到");
                        }
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
