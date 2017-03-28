package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.PartyTypeAdapter;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.PartyTypeBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PartyTypeActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {

    private TextView tv_title, tv_empty;
    private ImageView iv_search;

    private XListView lv_party;

    private String title;
    private String id;
    private Handler mHandler;
    private int p = 1, page = 6, maxPage;

    String lastUpdateTime;//最后更新时间

    private PartyTypeAdapter partyTypeAdapter;

    private ArrayList<PartyTypeBean.DataBean.ListBean> list;
    private ArrayList<PartyTypeBean.DataBean.ListBean> lists = new ArrayList<>();

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
                startActivity(new Intent(PartyTypeActivity.this, SearchActivity.class));
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

        String title = getIntent().getStringExtra("title");
        partyTypeAdapter = new PartyTypeAdapter(PartyTypeActivity.this, lists);
        lv_party.setAdapter(partyTypeAdapter);
        lv_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PartyTypeActivity.this, PartyNativeDetailActivity.class);
                intent.putExtra("id", lists.get(position - 1).getId());
                startActivity(intent);
            }
        });

        if (title.equals("热门活动")) {
            GetHotPartyList();
        } else if (title.equals("精品活动")) {
            GetRecPartyList();
        } else if (title.equals("最新活动")) {
            GetNewPartyList();
        }

    }


    public void GetHotPartyList() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_INDEX_HOTLIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    PartyTypeBean partyTypeBean = ParseUtils.parsePartyTypeBean(result);
                    if (partyTypeBean.getStatus() == 1 && partyTypeBean.getData() != null) {
                        maxPage = partyTypeBean.getData().getMaxPage();
                        list = (ArrayList<PartyTypeBean.DataBean.ListBean>) partyTypeBean.getData().getList();
                        if (list != null && list.size() != 0) {
                            lists.addAll(list);
                            partyTypeAdapter.notifyDataSetChanged();
                        } else {
                            lv_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }
                } else {
                    lv_party.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.VISIBLE);
                    tv_empty.setText("抱歉，该项无数据");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    public void GetRecPartyList() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_INDEX_RECLIST + url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                            PartyTypeBean partyTypeBean = ParseUtils.parsePartyTypeBean(result);
                            if (partyTypeBean.getStatus() == 1 && partyTypeBean.getData() != null) {
                                maxPage = partyTypeBean.getData().getMaxPage();
                                list = (ArrayList<PartyTypeBean.DataBean.ListBean>) partyTypeBean.getData().getList();
                                if (list != null && list.size() != 0) {
                                    lists.addAll(list);
                                    partyTypeAdapter.notifyDataSetChanged();
                                } else {
                                    lv_party.setVisibility(View.GONE);
                                    tv_empty.setVisibility(View.VISIBLE);
                                    tv_empty.setText("抱歉，该项无数据");
                                }
                            }
                        } else {
                            lv_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        LogUtils.i(s);
                    }
                }
        );
    }


    public void GetNewPartyList() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_INDEX_NEWLIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    PartyTypeBean partyTypeBean = ParseUtils.parsePartyTypeBean(result);
                    if (partyTypeBean.getStatus() == 1 && partyTypeBean.getData() != null) {
                        maxPage = partyTypeBean.getData().getMaxPage();
                        list = (ArrayList<PartyTypeBean.DataBean.ListBean>) partyTypeBean.getData().getList();
                        if (list != null && list.size() != 0) {
                            lists.addAll(list);
                            partyTypeAdapter.notifyDataSetChanged();
                        } else {
                            lv_party.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }
                } else {
                    lv_party.setVisibility(View.GONE);
                    tv_empty.setVisibility(View.VISIBLE);
                    tv_empty.setText("抱歉，该项无数据");
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
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
                if (title.equals("热门活动")) {
                    GetHotPartyList();
                } else if (title.equals("精品活动")) {
                    GetRecPartyList();
                } else if (title.equals("最新活动")) {
                    GetNewPartyList();
                }
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
                    if (title.equals("热门活动")) {
                        GetHotPartyList();
                    } else if (title.equals("精品活动")) {
                        GetRecPartyList();
                    } else if (title.equals("最新活动")) {
                        GetNewPartyList();
                    }
                }
                onLoad();
            }
        }, 1000);
    }
}
