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
import org.thvc.happyi.application.HappyiApplication;
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
 * 推荐认领页面
 * Created by Administrator on 2016/3/28.
 */
public class RecommendClaimFragment extends BaseFragment1 implements XListView.IXListViewListener {

    private XListView party_recommended_list;
    private TextView tv_empty;

    private NgoPartyAdapter ngoPartyListAdapter;
    private ArrayList<NgoPartyListBean.DataEntity.ListEntity> list;
    private ArrayList<NgoPartyListBean.DataEntity.ListEntity> lists = new ArrayList<>();

    private Handler mHandler;

    String lastUpdateTime;//最后更新时间
    private int p = 1, page = 10, pages;
    private String userid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_claim, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userid = HappyiApplication.getInstance().getUserid(getActivity());
        party_recommended_list = (XListView) getView().findViewById(R.id.party_recommended_list);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        ngoPartyListAdapter = new NgoPartyAdapter(lists, getActivity());
        party_recommended_list.setAdapter(ngoPartyListAdapter);
        party_recommended_list.setPullLoadEnable(true);
        party_recommended_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", lists.get(position - 1).getId());
                startActivity(intent);
            }
        });
        getNgoParty();
        mHandler = new Handler();
    }

    public void getNgoParty() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.NOSET_LIST + url, new RequestCallBack<String>() {
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
                            party_recommended_list.setVisibility(View.GONE);
                            tv_empty.setText(R.string.no_auth_party);
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
        party_recommended_list.stopRefresh();
        party_recommended_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        party_recommended_list.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lists.clear();
                p = 1;
                getNgoParty();
                onLoad();
            }
        }, 2000);
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
        }, 2000);
    }
}
