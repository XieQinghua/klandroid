package org.thvc.happyi.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.adapter.NgoPartyAdapter;
import org.thvc.happyi.bean.NgoPartyListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * ngo活动页面
 * Created by Administrator on 2016/3/16.
 */
public class NgoPartyFragment extends BaseFragment1 implements XListView.IXListViewListener {

    private XListView lv_ngo_party;
    private TextView tv_empty;
    private String id;
    private Handler mHandler;

    private NgoPartyAdapter ngoPartyListAdapter;
    private ArrayList<NgoPartyListBean.DataEntity.ListEntity> list;
    private ArrayList<NgoPartyListBean.DataEntity.ListEntity> lists = new ArrayList<>();
    String lastUpdateTime;//最后更新时间

    private int p = 1, pages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_party, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        id = getActivity().getIntent().getStringExtra("id");
        lv_ngo_party = (XListView) getView().findViewById(R.id.lv_ngo_party);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        lv_ngo_party.setPullLoadEnable(true);
        ngoPartyListAdapter = new NgoPartyAdapter(lists, getActivity());
        lv_ngo_party.setAdapter(ngoPartyListAdapter);
        lv_ngo_party.setXListViewListener(this);
        lv_ngo_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", lists.get(position - 1).getId());
                startActivity(intent);
            }
        });
        mHandler = new Handler();
        getNgoParty();
    }


    public void getNgoParty() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", id);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTYMYLIST + url, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NgoPartyListBean ngoPartyListBean = ParseUtils.parseNgoPartyListBean(result);
                    if (ngoPartyListBean.getStatus() == 1) {
                        pages = ngoPartyListBean.getData().getMaxPage();
                        list = (ArrayList<NgoPartyListBean.DataEntity.ListEntity>) ngoPartyListBean.getData().getList();
                        if (list != null && list.size() != 0) {
                            lists.addAll(list);
                            ngoPartyListAdapter.notifyDataSetChanged();
                        } else {
                            tv_empty.setVisibility(View.VISIBLE);
                            lv_ngo_party.setVisibility(View.GONE);
                            tv_empty.setText("抱歉，这个机构没有发布活动");
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
            }
        });
    }

    private void onLoad() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lv_ngo_party.stopRefresh();
        lv_ngo_party.stopLoadMore();
        lv_ngo_party.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                lists.clear();
                getNgoParty();
                onLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((pages - p) != 0) {
                    p += 1;
                    getNgoParty();
                }
                onLoad();
            }
        }, 1000);
    }
}
