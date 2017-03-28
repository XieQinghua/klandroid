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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.NgoPartyDetailActivity;
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
 * Created by Administrator on 2015/11/21.
 * 修改：huagnxinqi on 2015/12/01
 * NGO通过的活动
 */
public class NgoPassPartyFragment extends BaseFragment1 implements XListView.IXListViewListener {
    private ArrayList<NGOPartyBean.DataEntity.ListEntity> list = new ArrayList<>();
    private ArrayList<NGOPartyBean.DataEntity.ListEntity> listEntities;

    private XListView lvPassParty;
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
        lvPassParty = (XListView) getView().findViewById(R.id.lv_ngo_party);
        lvPassParty.setPullLoadEnable(true);
        lvPassParty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("partyId", list.get(position - 1).getId());
                intent.setClass(getActivity(), NgoPartyDetailActivity.class);
                startActivity(intent);
            }
        });
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);

        getDataLists();

        lvPassParty.setXListViewListener(this);
        mHandler = new Handler();
    }

    public void getDataLists() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(getActivity()));//！！getSolevar
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        //params.addQueryStringParameter("isdel", "3");//已通过活动，此处传参形式不同
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.PARTYMYLIST + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
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
                                if (entity.getIsdel().equals("3") || entity.getIsdel().equals("4") || entity.getIsdel().equals("5") || entity.getIsdel().equals("6") || entity.getIsdel().equals("7") || entity.getIsdel().equals("8")) {
                                    list.add(entity);
                                }
                            }
                            //list.addAll(listEntities);
                            adapter = new NgoMyPartyAdapter(getActivity(), list);
                            lvPassParty.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            lvPassParty.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            lvPassParty.setVisibility(View.GONE);
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
        lvPassParty.stopRefresh();
        lvPassParty.stopLoadMore();
        lvPassParty.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                list.clear();
                getDataLists();
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
                    getDataLists();
                }
                onLoad();
            }
        }, 1000);
    }
}
