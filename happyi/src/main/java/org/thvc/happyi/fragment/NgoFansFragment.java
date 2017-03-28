package org.thvc.happyi.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.NgoFansAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.FansBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NgoFansFragment extends RefreshFragment implements XListView.IXListViewListener {

    private NgoFansAdapter adapter;
    private ArrayList<FansBean.DataEntity.ListEntity> list;
    private XListView lvFans;
    private MyHandler mHandler;
    private TextView tv_empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ngo_fans, null);
        initialize(view);
        firstLoad(10, LOAD);
        return view;
    }

    @Override
    public void getDataList(final int pageIndex, final int type) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", HappyiApplication.getInstance().getSolevar(getActivity()));
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        if (HappyiApplication.getInstance().getUserid(getActivity()) != null) {
            params.addQueryStringParameter("loginuser", HappyiApplication.getInstance().getUserid(getActivity()));
        }
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.FANS + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FansBean fansBean = ParseUtils.parseFansBean(result);
                    if (fansBean.getStatus() == 1) {
                        ArrayList<FansBean.DataEntity.ListEntity> receiveList = (ArrayList<FansBean.DataEntity.ListEntity>) fansBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            switch (type) {
                                case LOAD:
                                    list = receiveList;
                                    adapter = new NgoFansAdapter(getActivity(), list, mHandler);
                                    lvFans.setAdapter(adapter);
                                    break;
                                case LOADMORE:
                                    list.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                case REFRESH:
                                    list = receiveList;
                                    adapter = new NgoFansAdapter(getActivity(), list, mHandler);
                                    lvFans.setAdapter(adapter);
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多粉丝了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                lvFans.setVisibility(View.GONE);
                                tv_empty.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "抱歉，你还没有粉丝", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), fansBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.e(s);
            }
        });
    }

    private void initialize(View view) {
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        lvFans = (XListView) view.findViewById(R.id.lv_fans);
        lvFans.setPullLoadEnable(true);
        lvFans.setXListViewListener(this);
        mHandler = new MyHandler();
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessage(REFRESH);
    }

    @Override
    public void onLoadMore() {
        mHandler.sendEmptyMessage(LOADMORE);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADMORE:
                    loadDataList();
                    lvFans.stopLoadMore();
                    break;
                case REFRESH:
                    refreshDataList();
                    lvFans.stopRefresh();
                    break;
                case LOAD:
                    onResume();
                    refreshDataList();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
