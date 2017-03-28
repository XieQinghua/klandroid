package org.thvc.happyi.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.MessageListBean;
import org.thvc.happyi.bean.SetUpToReadBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/13.
 * 消息适配器
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageListBean.DataEntity.ListEntity> listEntities = new ArrayList<>();
    private Handler handler;

    public MessageAdapter(Context context, List<MessageListBean.DataEntity.ListEntity> listEntities, Handler handler) {
        this.context = context;
        this.listEntities = listEntities;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return listEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return listEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = null;
        if (convertView == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
            vh.relativ_layout = (RelativeLayout) view.findViewById(R.id.relativ_layout);
            vh.tv_message = (TextView) view.findViewById(R.id.tv_message);
            vh.tv_time = (TextView) view.findViewById(R.id.tv_time);
            vh.tv_unread = (TextView) view.findViewById(R.id.tv_unread);
            view.setTag(vh);
        } else {
            view = convertView; // 直接复用已经不可见的视图
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        if (listEntities.get(position).getStatus().equals("2")) {
            vh.tv_unread.setText("未读");
            vh.tv_unread.setTextColor(context.getResources().getColor(R.color.orangered));
        } else if (listEntities.get(position).getStatus().equals("1")) {
            vh.tv_unread.setText("已读");
            vh.tv_unread.setTextColor(context.getResources().getColor(R.color.party_text_color));
        }
        vh.relativ_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUpToRead(listEntities.get(position).getId(), vh);
            }
        });
        vh.tv_message.setText(listEntities.get(position).getRemark());
        vh.tv_time.setText(getStringTime(listEntities.get(position).getAddtime()));


        return view;
    }

    class ViewHolder {
        TextView tv_message, tv_time, tv_unread;
        RelativeLayout relativ_layout;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }

    public void SetUpToRead(String id, final ViewHolder vh) {
        HttpUtils httpUtils = new HttpUtils();
        final MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(context));
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.READ_NOTICE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    SetUpToReadBean setUpToReadBean = ParseUtils.parSetUpToReadBean(result);
                    if (setUpToReadBean.getStatus() == 1) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                Message msg = Message.obtain();
                                msg.what = 1;
                                handler.sendMessage(msg);//传3给主线程做关注活动操作
                            }
                        }.start();
                        vh.tv_unread.setTextColor(context.getResources().getColor(R.color.party_text_color));
                        vh.tv_unread.setText("已读");
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
