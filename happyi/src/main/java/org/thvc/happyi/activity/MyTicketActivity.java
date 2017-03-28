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
import org.thvc.happyi.adapter.MyTicketAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.MyTicketBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/29.
 * 我的活动券页面
 */
public class MyTicketActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {
    private XListView myCoupon_list;
    private TextView message_empty;
    private String userid;
    private int p = 1, pages;
    private ArrayList<MyTicketBean.DataEntity.ListEntity> list;
    private ArrayList<MyTicketBean.DataEntity.ListEntity> lists = new ArrayList<>();
    private MyTicketAdapter myTicketAdapter;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间
    private String Ticket;
    private String joinid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        userid = HappyiApplication.getInstance().getUserid(MyTicketActivity.this);
        myCoupon_list = (XListView) this.findViewById(R.id.myCoupon_list);
        myCoupon_list.setPullLoadEnable(true);
        message_empty = (TextView) this.findViewById(R.id.message_empty);

        Ticket = getIntent().getStringExtra("Ticket");
        joinid = getIntent().getStringExtra("joinid");
        myTicketAdapter = new MyTicketAdapter(lists, this);
        myCoupon_list.setAdapter(myTicketAdapter);
        myCoupon_list.setXListViewListener(this);
        mHandler = new Handler();
        getMyTicket();
    }

    public void getMyTicket() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        if (Ticket.equals("party")) {
            params.addQueryStringParameter("joinid", joinid);
        }
        params.addQueryStringParameter("scan", "0");
        params.addQueryStringParameter("p", p + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.MYTICKET + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MyTicketBean myTicketBean = ParseUtils.parseMyTicketBean(result);
                    if (myTicketBean.getStatus() == 1) {
                        pages = myTicketBean.getData().getMaxPage();
                        list = (ArrayList<MyTicketBean.DataEntity.ListEntity>) myTicketBean.getData().getList();
                        if (list != null && list.size() != 0) {
                            lists.addAll(list);
                            myTicketAdapter.notifyDataSetChanged();
                            myCoupon_list.setVisibility(View.VISIBLE);
                            message_empty.setVisibility(View.GONE);
                        } else {
                            myCoupon_list.setVisibility(View.GONE);
                            message_empty.setVisibility(View.VISIBLE);
                            message_empty.setText("暂无消息");
                        }
                    }
                } else {
                    Toast.makeText(MyTicketActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
        myCoupon_list.stopRefresh();
        myCoupon_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        myCoupon_list.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                lists.clear();
                getMyTicket();
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
                    getMyTicket();
                }
                onLoad();
            }
        }, 1000);
    }

}
