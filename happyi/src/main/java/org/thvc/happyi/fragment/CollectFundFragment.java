package org.thvc.happyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.FundNativeDetailActivity;
import org.thvc.happyi.adapter.FundCollectAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.FundCollectListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * 关注的基金会列表（个人和NGO用户共用）
 */
public class CollectFundFragment extends RefreshFragment {
    private ArrayList<FundCollectListBean.DataEntity.ListEntity> list;
    private FundCollectAdapter adapter;
    private ListView lvFundCollect;
    public static final int LOADING_DIALOG = 0;

    private TextView tv_empty;
    //页码
    private int pageIndex = 1;

    //获取数据的类型：LOAD：第一次进入时加载，REFRESH:下拉刷新，LOADMORE:上拉加载
    private final static int LOAD = 1000;
    private final static int REFRESH = 2000;
    private final static int LOADMORE = 3000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect_fund, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvFundCollect = (ListView) getView().findViewById(R.id.lv_collect_fund);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        //点击进入基金会详情
        lvFundCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FundNativeDetailActivity.class);
                intent.putExtra("id", list.get(position).getSolevar());
                startActivity(intent);
            }
        });
        //获取数据
        getDataList(pageIndex, LOAD);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取关注基金会列表
     *
     * @param pageIndex 页码
     * @param type      类型（第一次加载，下拉刷新，上拉加载更多）
     */
    @Override
    public void getDataList(final int pageIndex, final int type) {
        getActivity().showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_FUND + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    FundCollectListBean fundCollectListBean = ParseUtils.parseFundCollectListBean(result);
                    if (fundCollectListBean.getStatus() == 1) {
                        ArrayList<FundCollectListBean.DataEntity.ListEntity> receiveList = (ArrayList<FundCollectListBean.DataEntity.ListEntity>) fundCollectListBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            lvFundCollect.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                            switch (type) {
                                case LOAD:
                                    list = receiveList;
                                    adapter = new FundCollectAdapter(list, getActivity());
                                    lvFundCollect.setAdapter(adapter);
                                    break;
                                case LOADMORE:
                                    list.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                case REFRESH:
                                    list.clear();
                                    list = receiveList;
                                    adapter = new FundCollectAdapter(list, getActivity());
                                    lvFundCollect.setAdapter(adapter);
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多基金会了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                tv_empty.setVisibility(View.VISIBLE);
                                lvFundCollect.setVisibility(View.GONE);
                                tv_empty.setText("抱歉，您还没有关注的基金会");
                            }
                        }
                        getActivity().removeDialog(0);

                    } else {
                        Toast.makeText(getActivity(), fundCollectListBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }
}
