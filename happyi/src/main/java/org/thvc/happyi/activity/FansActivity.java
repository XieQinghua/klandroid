package org.thvc.happyi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.FansAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.FansBean;
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
 * Created by Administrator on 2015/11/13.
 * 粉丝页面
 * 修改记录：添加了下拉刷新上拉加载
 * update by huangxinqi
 * on 2015.11.21 17:30
 * update by huangxinqi
 * on 2015.11.30 17:30
 */
public class FansActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {
    private FansAdapter adapter;
    private ArrayList<FansBean.DataEntity.ListEntity> list = new ArrayList<>();
    private ArrayList<FansBean.DataEntity.ListEntity> receiveList;
    private XListView lvFans;
    private int p = 1, page = 10, pages;
    private TextView tv_empty;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间


    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    list.clear();
                    getDataLists("1");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        init();
    }

    private void init() {
        lvFans = (XListView) findViewById(R.id.lv_fans);
        lvFans.setPullLoadEnable(true);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        adapter = new FansAdapter(FansActivity.this, list, handler);
        lvFans.setAdapter(adapter);
        lvFans.setXListViewListener(this);
        mHandler = new Handler();
        getDataLists("1");
    }


    public void getDataLists(String str) {
        if (str.equals("1")) {
            showDialog(LOADING_DIALOG);
        }
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", HappyiApplication.getInstance().getSolevar(FansActivity.this));
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        HttpUtils httpUtils = new HttpUtils();
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FANS + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FansBean fansBean = ParseUtils.parseFansBean(result);
                    if (fansBean.getStatus() == 1) {
                        pages = fansBean.getData().getMaxPage();
                        receiveList = (ArrayList<FansBean.DataEntity.ListEntity>) fansBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            list.addAll(receiveList);
                            adapter.notifyDataSetChanged();
                            tv_empty.setVisibility(View.GONE);
                            lvFans.setVisibility(View.VISIBLE);
                        } else {
                            lvFans.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，你还没有粉丝");
                        }
                        removeDialog();
                    } else {
                        Toast.makeText(FansActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FansActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    private void onLoad() {
        lvFans.stopRefresh();
        lvFans.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lvFans.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                list.clear();
                getDataLists("2");
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
                    getDataLists("2");
                }
                onLoad();
            }
        }, 1000);
    }
}
