package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.FansAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.FansBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * NGO的活动-》详情-》粉丝
 * Created by huangxinqi
 * on 2015/11/27-13:08.
 */
public class FansFragment extends RefreshFragment {
    private FansAdapter adapter;
    private ArrayList<FansBean.DataEntity.ListEntity> list;
    private ListView lvFans;
    private String partyId;


    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH:
                    list.clear();
                    refreshDataList();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fans, null);
        partyId = getActivity().getIntent().getStringExtra("partyId");
        lvFans = (ListView) view.findViewById(R.id.lv_fans);
        firstLoad(pageNum, LOAD);
        return view;
    }

    @Override
    public void getDataList(final int pageIndex, final int type) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", partyId);
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(getActivity()));
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FANS + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    FansBean fansBean = ParseUtils.parseFansBean(result);
                    if (fansBean.getStatus() == 1) {
                        ArrayList<FansBean.DataEntity.ListEntity> receiveList = (ArrayList<FansBean.DataEntity.ListEntity>) fansBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            switch (type) {
                                case LOAD:
                                    list = receiveList;
                                    adapter = new FansAdapter(getActivity(), list, handler);
                                    lvFans.setAdapter(adapter);
                                    break;
                                case LOADMORE:
                                    list.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                case REFRESH:
                                    list = receiveList;
                                    adapter = new FansAdapter(getActivity(), list, handler);
                                    lvFans.setAdapter(adapter);
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多粉丝了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                Toast.makeText(getActivity(), "抱歉，你还没有粉丝", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
