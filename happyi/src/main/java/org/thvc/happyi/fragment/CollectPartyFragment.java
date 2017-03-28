package org.thvc.happyi.fragment;

import android.content.DialogInterface;
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
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.adapter.PartyCollectAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.CollectAddBean;
import org.thvc.happyi.bean.PartyCollectBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.utils.Utility;
import org.thvc.happyi.view.CustomDialog;

import java.util.ArrayList;

/**
 * 关注的活动
 * Created by huangxinqi
 * on 2015/11/24-10:47.
 */
public class CollectPartyFragment extends RefreshFragment {
    private ListView lvCollectParty;
    private ArrayList<PartyCollectBean.DataEntity.ListEntity> dataList;
    private PartyCollectAdapter adapter;
    private TextView tv_empty;
    public static final int LOADING_DIALOG = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect_party, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvCollectParty = (ListView) getView().findViewById(R.id.lv_collect_party);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);

        lvCollectParty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", dataList.get(position).getPartyid());
                startActivity(intent);
            }
        });
        //长按取消关注
        lvCollectParty.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出提示框
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("是否继续取消关注该活动?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        //取消关注操作
                        HttpUtils httpUtils = new HttpUtils();
                        MyRequestParams params = new MyRequestParams();
                        params.addQueryStringParameter("status", 1 + "");//1取消关注
                        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
                        params.addQueryStringParameter("dataid", dataList.get(position).getPartyid());
                        params.addQueryStringParameter("type", 1 + "");
                        String url = params.myRequestParams(params);
                        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                if (new JsonValidator().validate(result)) {
                                    CollectAddBean collectAddBean = ParseUtils.parseCollectAddBean(result);
                                    if (collectAddBean.getStatus() == 1) {
                                        Toast.makeText(getActivity(), collectAddBean.getInfo(), Toast.LENGTH_SHORT).show();
                                        dataList.remove(position);
                                        adapter.notifyDataSetChanged();
                                        dialog.dismiss();
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
                });

                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
        //获取数据
        getDataList(pageIndex, LOAD);
    }

    @Override
    public void getDataList(final int pageIndex, final int type) {
        getActivity().showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        String userid = HappyiApplication.getInstance().getUserid(getActivity());
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_PARTY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    PartyCollectBean partyCollectBean = ParseUtils.parsePartyCollectBaen(result);
                    if (partyCollectBean.getStatus() == 1) {
                        ArrayList<PartyCollectBean.DataEntity.ListEntity> receiveList = (ArrayList<PartyCollectBean.DataEntity.ListEntity>) partyCollectBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            lvCollectParty.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                            switch (type) {
                                case LOAD:
                                    dataList = receiveList;
                                    adapter = new PartyCollectAdapter(getActivity(), dataList);
                                    lvCollectParty.setAdapter(adapter);
                                    Utility.setListViewHeightBasedOnChildren(lvCollectParty);
                                    break;
                                case LOADMORE:
                                    dataList.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                case REFRESH:
                                    dataList = receiveList;
                                    adapter.notifyDataSetChanged();
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                tv_empty.setVisibility(View.VISIBLE);
                                lvCollectParty.setVisibility(View.GONE);
                                tv_empty.setText("抱歉，您还没有关注的活动");
                            }
                        }
                        getActivity().removeDialog(0);
                    } else {
                        Toast.makeText(getActivity(), partyCollectBean.getInfo(), Toast.LENGTH_SHORT).show();
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
