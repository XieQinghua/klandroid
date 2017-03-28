package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.RefundHistoryAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.RefundHistoryBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Administrator on 2015/12/18.
 */
public class RefundHistoryFragment extends BaseFragment implements XListView.IXListViewListener {
    public static final int LOADING_DIALOG = 0;
    private XListView refundhistory_list;
    private String userid;
    private ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities;
    private int p = 1, page = 10, pages;
    private TextView tv_empty;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_refundhistory, null);
        refundhistory_list = (XListView) view.findViewById(R.id.refundhistory_list);
        userid = HappyiApplication.getInstance().getUserid(getActivity());
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        refundhistory_list.setXListViewListener(this);
        mHandler = new Handler();
        getRefundHistory("1");
        return view;
    }


    public void getRefundHistory(String str) {
        if (str.equals("1")) {
            getActivity().showDialog(LOADING_DIALOG);
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("isdel", "1");
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.REFUND_INDEX + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    RefundHistoryBean refundListBean = ParseUtils.parseRefundHistoryBean(result);
                    if (refundListBean.getStatus() == 1) {
                        pages = refundListBean.getData().getMaxPage();
                        if (refundListBean.getData().getList() != null && refundListBean.getData().getList().size() != 0) {
                            listEntities = (ArrayList<RefundHistoryBean.DataEntity.ListEntity>) refundListBean.getData().getList();
                            RefundHistoryAdapter refundHistoryAdapter = new RefundHistoryAdapter(getActivity(), listEntities);
                            refundhistory_list.setAdapter(refundHistoryAdapter);
                            refundhistory_list.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            refundhistory_list.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("暂无数据");
                        }
                        getActivity().removeDialog(0);
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
        refundhistory_list.stopRefresh();
        refundhistory_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        refundhistory_list.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                listEntities.clear();
                getRefundHistory("2");
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
                    getRefundHistory("2");
                }
                onLoad();
            }
        }, 1000);
    }
}
