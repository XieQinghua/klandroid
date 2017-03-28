package org.thvc.happyi.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.RefundAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.ARefundBean;
import org.thvc.happyi.bean.RefundHistoryBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CustomDialog;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 颜松梁
 * Created by Administrator on 2015/12/1.
 * 退款列表页面
 */
public class RefundFragment extends BaseFragment implements XListView.IXListViewListener {

    private XListView refund_list;
    private String userid;
    private ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities;
    private int p = 1, page = 10, pages;
    private TextView tv_empty;

    public static final int LOADING_DIALOG = 0;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //先得到一个布局
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_refund, null);
        refund_list = (XListView) view.findViewById(R.id.refund_list);

        refund_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showAlertDialog(listEntities.get(position - 1).getReason(), listEntities.get(position - 1).getId());
//                Intent intent = new Intent(getActivity(), ARefundActivity.class);
//                intent.putExtra("head", listEntities.get(position - 1).getHead());
//                intent.putExtra("title", listEntities.get(position - 1).getTitle());
//                intent.putExtra("payfee", listEntities.get(position - 1).getPayfee());
//                intent.putExtra("reason", listEntities.get(position - 1).getReason());
//                intent.putExtra("nickname", listEntities.get(position - 1).getRealname());
//                intent.putExtra("enrollend", listEntities.get(position - 1).getEnrollend());
//                intent.putExtra("id", listEntities.get(position - 1).getId());
//                startActivity(intent);

            }
        });
        refund_list.setPullLoadEnable(true);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        refund_list.setXListViewListener(this);
        mHandler = new Handler();
        userid = HappyiApplication.getInstance().getUserid(getActivity());
        getRfundList("1");
        return view;
    }


    public void getRfundList(String str) {
        getActivity().showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("isdel", "2");
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
                            RefundAdapter refundAdapter = new RefundAdapter(getActivity(), listEntities);
                            refund_list.setAdapter(refundAdapter);
                            refund_list.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            refund_list.setVisibility(View.GONE);
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
                listEntities.clear();
                getRfundList("2");
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
                    getRfundList("2");
                }
                onLoad();
            }
        }, 1000);
    }


    public void showAlertDialog(String str, final String id) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("因" + str + "不能参加本次活动");
        builder.setTitle("退款原因");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int which) {
                HttpUtils httpUtils = new HttpUtils();
                MyRequestParams params = new MyRequestParams();//定义访问服务器参数
                params.addQueryStringParameter("id", id);
                params.addQueryStringParameter("isdel", "3");
                String url = params.myRequestParams(params);
                httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CONFIRM_REFUND + url, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        if (new JsonValidator().validate(result)) {
                            ARefundBean aRefundBean = ParseUtils.parseARefundBean(result);
                            if (aRefundBean.getStatus() == 1) {
                                Toast.makeText(getActivity(), aRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if (aRefundBean.getStatus() == -1) {
                                Toast.makeText(getActivity(), aRefundBean.getInfo() + "，请不要重新退款", Toast.LENGTH_SHORT).show();
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
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }

}
