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
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.NgoNativeDetailActivity;
import org.thvc.happyi.adapter.NgoIntroAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.NgoListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.XList.XListView;
import org.thvc.happyi.view.XList.XListView.IXListViewListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 项目名称：klandroid
 * 类描述：NGO列表页面
 * 创建人：谢庆华.
 * 创建时间：2015/11/6 16:03
 * 修改人：huangxinqi
 * 修改时间：2015/12/3 12:43
 * 修改备注：添加上拉加载下拉刷新
 */
public class NgoListFragment extends BaseFragment implements IXListViewListener {
    private static final String TAG = "NgoListFragment";
    public static final int LOADING_DIALOG = 0;
    private EditText et_search;
    private ImageView iv_empty;

    private XListView ngo_list;
    private String userid;
    private int p = 1, page = 10, pages;
    private ArrayList<NgoListBean.DataEntity.ListEntity> listEntities;
    private ArrayList<NgoListBean.DataEntity.ListEntity> list = new ArrayList<>();
    private TextView tv_empty;
    private NgoIntroAdapter ngoIntroAdapter;

    private Handler mHandler;
    private String lastUpdateTime;//最后更新时间

    private static final String LOAD = "1";//加载数据
    private static final String PULL = "2";//上拉加载数据
    private static final String DROPDOWN = "3";//下拉加载数据


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_list, container, false);
        return view;
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    list.clear();
                    GetNgoList(LOAD, et_search.getText().toString().trim());//获取NGO列表数据
                    break;
            }
        }
    };


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userid = HappyiApplication.getInstance().getUserid(getActivity());
        ngo_list = (XListView) getView().findViewById(R.id.ngo_list);
        ngo_list.setPullLoadEnable(true);
        tv_empty = (TextView) getView().findViewById(R.id.tv_empty);

        ngoIntroAdapter = new NgoIntroAdapter(list, getActivity(), handler);
        ngo_list.setAdapter(ngoIntroAdapter);
        ngo_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), NgoNativeDetailActivity.class).putExtra("id", list.get(position - 1).getSolevar()));
            }
        });
        ngo_list.setXListViewListener(this);
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
                GetNgoList(LOAD, et_search.getText().toString().trim());
                iv_empty.setVisibility(View.VISIBLE);
                iv_empty.setClickable(true);
                if (et_search.getText().toString().trim().equals("")) {
                    iv_empty.setVisibility(View.INVISIBLE);
                    if (list != null && list.size() != 0) {
                        list.clear();
                        ngoIntroAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GetNgoList(LOAD, et_search.getText().toString().trim());//获取NGO列表数据
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        GetNgoList(LOAD, et_search.getText().toString().trim());
    }

    public void GetNgoList(final String str, String search) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        if (!search.equals("")) {
            list.clear();
            params.addQueryStringParameter("name", search);
        }
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.NGO_LIST + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NgoListBean ngoListBean = ParseUtils.parseNgoListBean(result);
                    pages = ngoListBean.getData().getMaxPage();
                    if (str.equals(DROPDOWN)) {
                        list.clear();
                    }
                    if (ngoListBean.getStatus() == 1) {
                        listEntities = (ArrayList<NgoListBean.DataEntity.ListEntity>) ngoListBean.getData().getList();
                        if (listEntities != null) {
                            list.addAll(listEntities);
                            ngoIntroAdapter.notifyDataSetChanged();
                            ngo_list.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            ngo_list.setVisibility(View.GONE);
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
                LogUtils.i(s);
            }
        });
    }


    private void onLoad() {
        ngo_list.stopRefresh();
        ngo_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        ngo_list.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                p = 1;
                GetNgoList(DROPDOWN, et_search.getText().toString().trim());
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
                    GetNgoList(PULL, et_search.getText().toString().trim());
                }
                onLoad();
            }
        }, 2000);
    }

}
