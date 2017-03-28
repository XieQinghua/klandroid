package org.thvc.happyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.FundNativeDetailActivity;
import org.thvc.happyi.adapter.FoundationAdapter;
import org.thvc.happyi.bean.Foundationlistbean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Administrator on 2016/3/21.
 */
public class FoundationListFragment extends BaseFragment implements XListView.IXListViewListener {

    private EditText et_search;
    private ImageView iv_empty;

    private XListView Foundation_list;
    private int p = 1, page =10, pages;
    private ArrayList<Foundationlistbean.DataEntity.ListEntity>list ;
    private ArrayList<Foundationlistbean.DataEntity.ListEntity>lists = new ArrayList<>() ;
    private FoundationAdapter foundationAdapter;
    private Handler mHandler;
    private TextView tv_empty;
    private String lastUpdateTime;//最后更新时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Foundation_list = (XListView) getView().findViewById(R.id.ngo_list);
        Foundation_list.setPullLoadEnable(true);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);
        foundationAdapter = new FoundationAdapter(getActivity(),lists);
        Foundation_list.setAdapter(foundationAdapter);
        Foundation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), FundNativeDetailActivity.class).putExtra("id", list.get(position - 1).getSolevar()
                ));
            }
        });
        Foundation_list.setXListViewListener(this);
        mHandler = new Handler();

        et_search = (EditText) getView().findViewById(R.id.et_search);
        iv_empty = (ImageView) getView().findViewById(R.id.iv_empty);
        iv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空et_search
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GetFoundationList(et_search.getText().toString().trim());
                iv_empty.setVisibility(View.VISIBLE);
                iv_empty.setClickable(true);
                if (et_search.getText().toString().trim().equals("")) {
                    iv_empty.setVisibility(View.INVISIBLE);
                    if (lists != null && lists.size() != 0) {
                        lists.clear();
                        foundationAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        GetFoundationList(et_search.getText().toString().trim());
    }

    public void GetFoundationList(String search) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("p", p + "");
        if (!search.equals("")){
            lists.clear();
            params.addQueryStringParameter("name", search);
        }
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FUND_INDEX + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    Foundationlistbean foundationlistbean = ParseUtils.parseFoundationlistbean(result);
                    pages = foundationlistbean.getData().getMaxPage();
                    if (foundationlistbean.getStatus() == 1) {
                        list = (ArrayList<Foundationlistbean.DataEntity.ListEntity>) foundationlistbean.getData().getList();
                        if (list != null) {
                            lists.addAll(list);
                            foundationAdapter.notifyDataSetChanged();
                            Foundation_list.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        }else {
                            Foundation_list.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            tv_empty.setText("抱歉，该项无数据");
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }


    private void onLoad() {
        Foundation_list.stopRefresh();
        Foundation_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        Foundation_list.setRefreshTime(lastUpdateTime);
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lists.clear();
                p = 1;
                GetFoundationList(et_search.getText().toString().trim());
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((pages - p) != 0) {
                    p += 1;
                    GetFoundationList(et_search.getText().toString().trim());
                }
                onLoad();
            }
        }, 2000);
    }
}
