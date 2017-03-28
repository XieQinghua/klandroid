package org.thvc.happyi.fragment;

import android.content.Intent;
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
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.adapter.FundPartyAdapter;
import org.thvc.happyi.bean.ClaimPartyBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/23.
 */
public class FundNativePartyFragment extends BaseFragment1 implements XListView.IXListViewListener {

    private XListView lv_fund_party;
    private TextView tv_empty;
    private String id;
    private Handler mHandler;

    String lastUpdateTime;//最后更新时间
    private ArrayList<ClaimPartyBean.DataEntity.ListEntity> list;
    private ArrayList<ClaimPartyBean.DataEntity.ListEntity> lists = new ArrayList<>();
    private int p = 1, page = 6, pages;
    private FundPartyAdapter fundPartyAdapter;

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

        lv_fund_party = (XListView) getView().findViewById(R.id.lv_ngo_party);
        tv_empty = (TextView)getView().findViewById(R.id.tv_empty);
        lv_fund_party.setPullLoadEnable(true);
        fundPartyAdapter = new FundPartyAdapter(getActivity(),lists);
        lv_fund_party.setAdapter(fundPartyAdapter);
        lv_fund_party.setXListViewListener(this);
        lv_fund_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", lists.get(position-1).getId());
                startActivity(intent);
            }
        });
        mHandler = new Handler();
        GetFundParty();
    }

    public void GetFundParty(){
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", id);
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CLAIMLIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    ClaimPartyBean claimPartyBean = ParseUtils.parseClaimPartyBean(result);
                    if (claimPartyBean.getStatus() == 1) {
                        pages = claimPartyBean.getData().getMaxPage();
                        list = (ArrayList<ClaimPartyBean.DataEntity.ListEntity>) claimPartyBean.getData().getList();
                        if (list != null) {
                            lists.addAll(list);
                            fundPartyAdapter.notifyDataSetChanged();
                            lv_fund_party.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            lv_fund_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    private void onLoad() {
        lv_fund_party.stopRefresh();
        lv_fund_party.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lv_fund_party.setRefreshTime(lastUpdateTime);
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                lists.clear();
                GetFundParty();
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
                    GetFundParty();
                }
                onLoad();
            }
        }, 2000);
    }
}
