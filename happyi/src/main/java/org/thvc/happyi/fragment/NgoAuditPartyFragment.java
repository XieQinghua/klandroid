package org.thvc.happyi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.NgoMyPartyAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.NGOPartyBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * NGO待审核的活动
 * Created by Administrator on 2015/11/21.
 * 修改：huagnxinqi on 2015/12/01
 * NGO审核中的活动
 */
public class NgoAuditPartyFragment extends BaseFragment1 implements XListView.IXListViewListener {
    private ArrayList<NGOPartyBean.DataEntity.ListEntity> list = new ArrayList<>();//全局list
    private ArrayList<NGOPartyBean.DataEntity.ListEntity> listEntities;//每次请求服务器返回的list
    private XListView lvAuditParty;
    private NgoMyPartyAdapter adapter;
    private int pageNum = 20;
    private int p = 1, pages;
    private TextView tv_empty;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_my_party, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvAuditParty = (XListView) getView().findViewById(R.id.lv_ngo_party);
        lvAuditParty.setPullLoadEnable(true);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        getDataList();
        adapter = new NgoMyPartyAdapter(getActivity(), list);
        lvAuditParty.setAdapter(adapter);
        lvAuditParty.setXListViewListener(this);
        mHandler = new Handler();
    }


    public void getDataList() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
//        params.addQueryStringParameter("where[isdel]", "2");//待审核，此处传参形式不同
        params.addQueryStringParameter("isdel", "2");//待审核，此处传参形式不同
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTYMYLIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NGOPartyBean ngoPartyBean = ParseUtils.parseNGOPartyBean(result);
                    if (ngoPartyBean.getStatus() == 1) {
                        pages = ngoPartyBean.getData().getMaxPage();
                        listEntities = (ArrayList<NGOPartyBean.DataEntity.ListEntity>) ngoPartyBean.getData().getList();
                        if (listEntities != null && listEntities.size() != 0) {
                            for (NGOPartyBean.DataEntity.ListEntity entity : listEntities) {
                                if (entity.getIsdel().equals("2")) {
                                    //listEntities.remove(entity);
                                    list.add(entity);
                                }
                            }
                            //list.addAll(listEntities);
                            adapter.notifyDataSetChanged();
                            lvAuditParty.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);

                        } else {
                            lvAuditParty.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    } else {
                        Toast.makeText(getActivity(), ngoPartyBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                e.getExceptionCode();
                Toast.makeText(getActivity(), "请求失败" + s + e.getExceptionCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onLoad() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        lvAuditParty.stopRefresh();
        lvAuditParty.stopLoadMore();
        lvAuditParty.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                list.clear();
                getDataList();
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
                    getDataList();
                }
                onLoad();
            }
        }, 1000);
    }

}
