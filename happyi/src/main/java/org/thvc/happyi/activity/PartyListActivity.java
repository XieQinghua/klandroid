package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.PartyListAdapter;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.PartyListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 项目名称：klandroid
 * 类描述：活动列表页面
 * 创建人：谢庆华.
 * 创建时间：2015/11/12 10:22
 * 修改人：huagnxiqi
 * 修改时间：2015/11/20 16:52
 * 修改备注：
 */
public class PartyListActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {
    private static final String TAG = "PartyList";
    private TextView tv_title, tv_empty;
    private ImageView iv_search;
    private PartyListAdapter partyListAdapter;
    private ArrayList<PartyListBean.DataBean.ListBean> list;
    private ArrayList<PartyListBean.DataBean.ListBean> lists = new ArrayList<>();
    private XListView lv_party;

    private String title;
    private String id;
    private Handler mHandler;
    private int p = 1, page = 6, maxPage;

    String lastUpdateTime;//最后更新时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);
        init();
    }


    private void init() {
        title = getIntent().getStringExtra("title");
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PartyListActivity.this, SearchActivity.class));
            }
        });
        id = getIntent().getStringExtra("typeid");
        tv_title = (TextView) findViewById(R.id.tv_title_center);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_title.setText(title);
        lv_party = (XListView) findViewById(R.id.lv_party);
        lv_party.setPullLoadEnable(true);
        lv_party.setXListViewListener(this);
        mHandler = new Handler();

//        getPartyData();//获取NGO列表数据

        partyListAdapter = new PartyListAdapter(PartyListActivity.this, lists);
        lv_party.setAdapter(partyListAdapter);
        lv_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PartyListActivity.this, PartyNativeDetailActivity.class);
                intent.putExtra("id", lists.get(position - 1).getId());
                startActivity(intent);
            }
        });
        getPartyData(id);

    }

    private void getPartyData(String str) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        if (str.equals("")) {
        } else {
            params.addQueryStringParameter("status", str);
        }
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_SEARCH + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    PartyListBean partyListBean = ParseUtils.parsePartyListBean(result);
                    if (partyListBean.getStatus() == 1 && partyListBean.getData() != null) {
                        maxPage = partyListBean.getData().getMaxPage();
                        if (partyListBean.getData().getList() != null && partyListBean.getData().getList().size() != 0) {
                            list = (ArrayList<PartyListBean.DataBean.ListBean>) partyListBean.getData().getList();
                            lists.addAll(list);
                            partyListAdapter.notifyDataSetChanged();
                        } else {
                            lv_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }
                } else {
                    Toast.makeText(PartyListActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    private void onLoad() {
        lv_party.stopRefresh();
        lv_party.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lv_party.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lists.clear();
                p = 1;
                getPartyData(id);
                onLoad();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((maxPage - p) != 0) {
                    p += 1;
                    getPartyData(id);
                }
                onLoad();
            }
        }, 1000);
    }
}
