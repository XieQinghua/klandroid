package org.thvc.happyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.RegisterInfoActivity;
import org.thvc.happyi.adapter.PartyAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.MyPartyBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.utils.Utility;

import java.util.ArrayList;

/**
 * 未开始的活动
 * Created by huangxinqi
 * on 2015/11/23-16-47.
 */
public class PartyNoStartFragment extends RefreshFragment {
    private PartyAdapter adapter;
    private ArrayList<MyPartyBean.DataEntity.ListEntity> list;
    private ListView lvParty;
    private TextView tv_empty;
    public static final int LOADING_DIALOG = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_nostart, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvParty = (ListView) getView().findViewById(R.id.lv_party_nostart);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        lvParty.setOnItemClickListener(new MyOnItemClickListener());
        firstLoad(pageNum, LOAD);
    }

    @Override
    public void getDataList(final int pageIndex, final int type) {
        getActivity().showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
        params.addQueryStringParameter("isdel", "6");
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.JOIN_LIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MyPartyBean myPartyBean = ParseUtils.parseMyPartyBean(result);
                    if (myPartyBean.getStatus() == 1) {
                        ArrayList<MyPartyBean.DataEntity.ListEntity> receiveList = (ArrayList<MyPartyBean.DataEntity.ListEntity>) myPartyBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            lvParty.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                            switch (type) {
                                case LOAD:
                                    list = receiveList;
                                    adapter = new PartyAdapter(getActivity(), list);
                                    lvParty.setAdapter(adapter);
                                    Utility.setListViewHeightBasedOnChildren(lvParty);
                                    break;
                                case LOADMORE:
                                    list.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                case REFRESH:
                                    list.clear();
                                    list = receiveList;
                                    adapter = new PartyAdapter(getActivity(), list);
                                    lvParty.setAdapter(adapter);
                                    Utility.setListViewHeightBasedOnChildren(lvParty);
                                    refreshSuccess();
                            }

                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多活动了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                tv_empty.setVisibility(View.VISIBLE);
                                lvParty.setVisibility(View.GONE);
                                tv_empty.setText("抱歉，你还没有未开始的活动");
                            }
                        }
                        getActivity().removeDialog(0);

                    } else {
                        Toast.makeText(getActivity(), myPartyBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), RegisterInfoActivity.class);
            intent.putExtra("dataid", list.get(position).getId());
            intent.putExtra("title", list.get(position).getTitle());
            intent.putExtra("actbegin", list.get(position).getEnrollend());//活动结束时间
            intent.putExtra("isdel", list.get(position).getIsdel());
//            intent.putExtra("subject", list.get(position).getSubject());
//            intent.putExtra("head", list.get(position).getHead());
            intent.putExtra("location", list.get(position).getCity());
            intent.putExtra("hasGood", list.get(position).getCollect());
            startActivity(intent);
        }
    }
}
