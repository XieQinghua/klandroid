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

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.NgoNativeDetailActivity;
import org.thvc.happyi.adapter.NgoCollectAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.CollectAddBean;
import org.thvc.happyi.bean.NGOCollectBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CustomDialog;

import java.util.ArrayList;

/**
 * 关注的NGO（个人和NGO用户共用）
 */
public class CollectNgoFragment extends RefreshFragment {
    //每一个fragment都继承RefreshFragment，然后声明一个适配器，一个listview和一个适配器
    private ArrayList<NGOCollectBean.DataEntity.ListEntity> ngoList;
    private NgoCollectAdapter adapter;
    private ListView lvNGOCollect;
    private TextView tv_empty;
    public static final int LOADING_DIALOG = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局，初始化控件
        View view = inflater.inflate(R.layout.fragment_collect_ngo, null);
        lvNGOCollect = (ListView) view.findViewById(R.id.lv_collect_ngo);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        //点击进入NGO详情
        lvNGOCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), NgoNativeDetailActivity.class)
                        .putExtra("id", ngoList.get(position).getSolevar()));
            }
        });
        //长按取消关注
        lvNGOCollect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //弹出提示框
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("是否继续取消关注该机构?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        //取消关注操作
                        HttpUtils httpUtils = new HttpUtils();
                        MyRequestParams params = new MyRequestParams();
                        params.addQueryStringParameter("status", 1 + "");//1取消关注
                        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
                        params.addQueryStringParameter("dataid", ngoList.get(position).getSolevar());
                        params.addQueryStringParameter("type", 2 + "");
                        String url = params.myRequestParams(params);
                        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                if (new JsonValidator().validate(result)) {
                                    CollectAddBean collectAddBean = ParseUtils.parseCollectAddBean(result);
                                    if (collectAddBean.getStatus() == 1) {
                                        Toast.makeText(getActivity(), collectAddBean.getInfo(), Toast.LENGTH_SHORT).show();
                                        ngoList.remove(position);
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //父类的方法，第一次加载数据
        firstLoad(pageNum, LOAD);
    }

    /**
     * 向服务器请求数据
     *
     * @param pageIndex 页码
     * @param type      类型：下拉刷新，第一次加载数据，加载更多数据
     */
    @Override
    public void getDataList(final int pageIndex, final int type) {
        getActivity().showDialog(LOADING_DIALOG);
        HttpUtils httpUtils = new HttpUtils();
        String userid = HappyiApplication.getInstance().getUserid(getActivity());
        //设置参数
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("p", pageIndex + "");
        params.addQueryStringParameter("pageNum", pageNum + "");
        String url = params.myRequestParams(params);
        //发送请求
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_NGO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    //个人感觉ParseUtil使用静态工厂方法不合适，因为方法里只有一句话，而且在项目中只调用一次，因此采用下面的写法了..orz
                    NGOCollectBean ngoCollectBean = new Gson().fromJson(result, NGOCollectBean.class);
                    if (ngoCollectBean.getStatus() == 1) {
                        ArrayList<NGOCollectBean.DataEntity.ListEntity> receiveList = (ArrayList<NGOCollectBean.DataEntity.ListEntity>) ngoCollectBean.getData().getList();
                        if (receiveList != null && receiveList.size() != 0) {
                            lvNGOCollect.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                            //根据类型来选择加载的数据内容
                            switch (type) {
                                //第一次进入时夹加载数据
                                case LOAD:
                                    ngoList = receiveList;
                                    adapter = new NgoCollectAdapter(ngoList, getActivity());
                                    lvNGOCollect.setAdapter(adapter);
                                    break;
                                //加载更多数据
                                case LOADMORE:
                                    ngoList.addAll(receiveList);
                                    adapter.notifyDataSetChanged();
                                    loadSuccess(receiveList.size());
                                    break;
                                //刷新数据
                                case REFRESH:
                                    ngoList.clear();
                                    ngoList = receiveList;
                                    adapter = new NgoCollectAdapter(ngoList, getActivity());
                                    lvNGOCollect.setAdapter(adapter);
                                    refreshSuccess();
                            }
                        } else if (receiveList == null || receiveList.size() == 0) {
                            if (pageIndex > 1) {
                                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            } else if (pageIndex == 1) {
                                tv_empty.setVisibility(View.VISIBLE);
                                lvNGOCollect.setVisibility(View.GONE);
                                tv_empty.setText("抱歉，您还没有关注的机构");
                            }

                        }
                        getActivity().removeDialog(0);

                    } else {
                        Toast.makeText(getActivity(), ngoCollectBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
