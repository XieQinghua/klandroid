package org.thvc.happyi.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.MessageAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.MessageListBean;
import org.thvc.happyi.bean.SetUpToReadBean;
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
 * Created by Administrator on 2015/11/13.
 * 消息页面
 */
public class MessageActivity extends BaseSwipeBackActivity implements XListView.IXListViewListener {
    private XListView message_list;
    private TextView message_empty;
    private List<MessageListBean.DataEntity.ListEntity> listEntities;
    private List<MessageListBean.DataEntity.ListEntity> list = new ArrayList<>();
    private String userid;
    private int p = 1, page = 10, pages;
    private MessageAdapter messageAdapter;
    private Button btn_setread;
    private Handler mHandler;
    String lastUpdateTime;//最后更新时间

    private static final String LOAD = "1";//加载数据
    private static final String PULL = "2";//上拉加载数据
    private static final String DROPDOWN = "3";//下拉加载数据


    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    list.clear();
                    getMessageList(LOAD);//获取NGO列表数据
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message_list = (XListView) this.findViewById(R.id.message_list);
        message_list.setPullLoadEnable(true);
        message_empty = (TextView) this.findViewById(R.id.message_empty);

        btn_setread = (Button) this.findViewById(R.id.btn_setread);
        btn_setread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWholeRead();
            }
        });

        userid = HappyiApplication.getInstance().getUserid(MessageActivity.this);
        getMessageList(LOAD);
        messageAdapter = new MessageAdapter(MessageActivity.this, list, handler);
        message_list.setAdapter(messageAdapter);
        message_list.setXListViewListener(this);
        mHandler = new Handler();
    }


    public void getMessageList(final String str) {
        if (str.equals(LOAD)) {
            showDialog(LOADING_DIALOG);
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.NOTICE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    MessageListBean messageListBean = ParseUtils.parseMessageListBean(result);
                    pages = messageListBean.getData().getMaxPage();
                    if (messageListBean.getStatus() == 1) {
                        if (messageListBean.getData().getList() != null && messageListBean.getData().getList().size() != 0) {
                            listEntities = messageListBean.getData().getList();
                            list.addAll(listEntities);
                            messageAdapter.notifyDataSetChanged();
                            message_list.setVisibility(View.VISIBLE);
                            message_empty.setVisibility(View.GONE);
                        } else {
                            message_list.setVisibility(View.GONE);
                            message_empty.setVisibility(View.VISIBLE);
                            message_empty.setText("暂无消息");
                        }
                        removeDialog();
                    }
                } else {
                    Toast.makeText(MessageActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });


    }


    public void setWholeRead() {
        HttpUtils httpUtils = new HttpUtils();
        final MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.READ_NOTICE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    SetUpToReadBean setUpToReadBean = ParseUtils.parSetUpToReadBean(result);
                    if (setUpToReadBean.getStatus() == 1) {
                        // 更新数据
                        list.clear();
                        getMessageList(LOAD);
                    }
                } else {
                    Toast.makeText(MessageActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
        message_list.stopRefresh();
        message_list.stopLoadMore();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a hh:mm");
        Date date = new Date(System.currentTimeMillis());
        lastUpdateTime = simpleDateFormat.format(date) + "";
        message_list.setRefreshTime(lastUpdateTime);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                p = 1;
                list.clear();
                getMessageList(DROPDOWN);
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
                    getMessageList(PULL);
                }
                onLoad();
            }
        }, 1000);
    }
}
