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
import org.thvc.happyi.adapter.RefundRecordAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.RefundRecordBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 颜松梁
 * Created by Administrator on 2015/12/21.
 * 普通用户退款记录页面
 */
public class RefundRecordActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {

    private XListView refund_list;
    private int p = 1, page = 10, pages;
    private String userid;
    private TextView tv_empty;
    private List<RefundRecordBean.DataEntity.ListEntity> list;
    private List<RefundRecordBean.DataEntity.ListEntity> lists = new ArrayList<>();
    private RefundRecordAdapter refundRecordAdapter;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间

    private static final String LOAD = "1";//加载数据
    private static final String PULL = "2";//上拉加载数据
    private static final String DROPDOWN = "3";//下拉加载数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refundrecord);

        userid = HappyiApplication.getInstance().getUserid(this);
        refund_list = (XListView) this.findViewById(R.id.refund_list);
        refund_list.setPullLoadEnable(true);
        refundRecordAdapter = new RefundRecordAdapter(this, lists);
        refund_list.setAdapter(refundRecordAdapter);
        refund_list.setXListViewListener(this);
        mHandler = new Handler();
        tv_empty = (TextView) this.findViewById(R.id.tv_empty);

        GetRefundRecordList(LOAD);//获取NGO列表数据
    }

    public void GetRefundRecordList(String str) {
        if (str.equals(LOAD)) {
            showDialog(LOADING_DIALOG);
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.MY_REFUND_INDEX + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RefundRecordBean refundRecordBean = ParseUtils.parseRefundRecordBean(result);
                    pages = refundRecordBean.getData().getMaxPage();
                    if (refundRecordBean.getStatus() == 1) {
                        list = refundRecordBean.getData().getList();
                        if (list != null) {
                            lists.addAll(list);
                            refundRecordAdapter.notifyDataSetChanged();
                            tv_empty.setVisibility(View.GONE);
                            refund_list.setVisibility(View.VISIBLE);
                        } else {
                            refund_list.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                        removeDialog();
                    } else {
                        Toast.makeText(RefundRecordActivity.this, refundRecordBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RefundRecordActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
        refund_list.stopRefresh();
        refund_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        refund_list.setRefreshTime(lastUpdateTime);
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                lists.clear();
                GetRefundRecordList(DROPDOWN);
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
                    GetRefundRecordList(PULL);
                }
                onLoad();
            }
        }, 1000);
    }
}
