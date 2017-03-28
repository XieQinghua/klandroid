package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.ClaimPartyAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
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
 * 颜松梁
 * Created by Administrator on 2015/11/23.
 * 基金会已认领活动页面
 */
public class ClaimPartyActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {
    private String solevar;
    private XListView lv_claim_party;
    private ArrayList<ClaimPartyBean.DataEntity.ListEntity> list;
    private ArrayList<ClaimPartyBean.DataEntity.ListEntity> lists = new ArrayList<>();
    private int p = 1, page = 6, pages;
    private TextView tv_empty;
    private Handler mHandler;
    private ClaimPartyAdapter claimPartyAdapter;
    String lastUpdateTime;//最后更新时间

    private static final String LOAD = "1";//加载数据
    private static final String PULL = "2";//上拉加载数据
    private static final String DROPDOWN = "3";//下拉加载数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_claim);
        solevar = HappyiApplication.getInstance().getSolevar(ClaimPartyActivity.this);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        lv_claim_party = (XListView) this.findViewById(R.id.lv_claim_party);
        lv_claim_party.setPullLoadEnable(true);
        lv_claim_party.setXListViewListener(this);
        mHandler = new Handler();

        getClaimedPartyList(LOAD);

        claimPartyAdapter = new ClaimPartyAdapter(lists, ClaimPartyActivity.this);
        lv_claim_party.setAdapter(claimPartyAdapter);

        lv_claim_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClaimPartyActivity.this, PartyNativeDetailActivity.class);
                intent.putExtra("id", list.get(position - 1).getId());
                startActivity(intent);
            }
        });
    }

    public void getClaimedPartyList(String str) {
        if (str.equals(LOAD)) {
            showDialog(LOADING_DIALOG);
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", solevar);
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
                            claimPartyAdapter.notifyDataSetChanged();
                            lv_claim_party.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            lv_claim_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                        removeDialog();
                    }
                } else {
                    Toast.makeText(ClaimPartyActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

            }


        });
    }

    private void onLoad() {
        lv_claim_party.stopRefresh();
        lv_claim_party.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lv_claim_party.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                lists.clear();
                getClaimedPartyList(DROPDOWN);
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
                    getClaimedPartyList(PULL);
                }
                onLoad();
            }
        }, 2000);
    }

}
