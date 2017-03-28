package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.RegisterInformationBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * NGO的活动-》详情-》报名人员
 * Created by huangxinqi
 * on 2015/11/27-13:09.
 */
public class JoinPeopleFragment extends BaseFragment {
    private ArrayList<RegisterInformationBean.DataEntity.ListEntity> list;

    private String partyId;
    private RadioButton rb_sign_in, rb_not_sign_in;


    private AlreadySignFragment alreadysign;
    private NoSignFragment nosign;

    private FragmentManager fragmentManager;
    private int tabIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_people, null);
        fragmentManager = getActivity().getSupportFragmentManager();
        partyId = getActivity().getIntent().getStringExtra("partyId");
        rb_sign_in = (RadioButton) view.findViewById(R.id.rb_sign_in);
        rb_not_sign_in = (RadioButton) view.findViewById(R.id.rb_not_sign_in);
        rb_sign_in.setOnClickListener(new MyOnClickListener());
        rb_not_sign_in.setOnClickListener(new MyOnClickListener());
        setTabSelected(0);
        return view;
    }



    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rb_sign_in:
                    setTabSelected(0);
                    break;
                case R.id.rb_not_sign_in:
                    setTabSelected(1);
                    break;
            }

        }
    }



    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (alreadysign != null) {
            fragmentTransaction.hide(alreadysign);
        }
        if (nosign != null) {
            fragmentTransaction.hide(nosign);
        }
    }


    public void setTabSelected(int tabIndex) {
        this.tabIndex = tabIndex;
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (tabIndex) {
            case 0:
                if (alreadysign == null) {
                    alreadysign = new AlreadySignFragment();
                    fragmentTransaction.add(R.id.sign_frame, alreadysign);
                } else {
                    fragmentTransaction.show(alreadysign);
                }
                break;
            case 1:
                if (nosign == null) {
                    nosign = new NoSignFragment();
                    fragmentTransaction.add(R.id.sign_frame, nosign);
                } else {
                    fragmentTransaction.show(nosign);
                }
                break;

        }
        fragmentTransaction.commit();
    }




    public void getDataList(final String scan) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
        params.addQueryStringParameter("dataid", partyId);
        params.addQueryStringParameter("scan", scan);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_JOIN_PEOPLE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RegisterInformationBean registerInformationBean = ParseUtils.parseRegisterInformationBean(result);
                    if (registerInformationBean.getStatus() == 1) {
                        ArrayList<RegisterInformationBean.DataEntity.ListEntity> receiveList = (ArrayList<RegisterInformationBean.DataEntity.ListEntity>) registerInformationBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            list = receiveList;
//                            adapter = new JoinPeopleAdapter(getActivity(), list, scan);
//                            lvJoinPeople.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
