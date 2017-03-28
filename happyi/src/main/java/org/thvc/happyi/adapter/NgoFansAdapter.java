package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.FansBean;
import org.thvc.happyi.fragment.NgoFansFragment;
import org.thvc.happyi.fragment.RefreshFragment;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by 84948 on 2015/12/31.
 */
public class NgoFansAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FansBean.DataEntity.ListEntity> list;
    private NgoFansFragment.MyHandler handler;
    private ViewHolder v;

    public NgoFansAdapter(Context context, ArrayList<FansBean.DataEntity.ListEntity> list, NgoFansFragment.MyHandler handler) {
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            v = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_fans, null);
            v.tv_content = (TextView) view.findViewById(R.id.tv_content);
            v.iv_collect = (ImageView) view.findViewById(R.id.iv_collect);
            v.iv_has_collect = (ImageView) view.findViewById(R.id.iv_has_collect);
            v.nickName = (TextView) view.findViewById(R.id.tv_fans_name);
            v.fansAvatar = (CircleImageView) view.findViewById(R.id.iv_fans_avatar);
            v.tv_fans_number= (TextView) view.findViewById(R.id.tv_fans_number);
            v.tv_party_number= (TextView) view.findViewById(R.id.tv_party_number);
            view.setTag(v);
        } else {
            v = (ViewHolder) view.getTag();
        }
        ImgUtils.setCircleImageView(v.fansAvatar, list.get(position).getHeadpic());
        v.nickName.setText(list.get(position).getNickname());
        v.tv_content.setText(list.get(position).getContent());
        if (list.get(position).getMembertype() != null) {
            if (list.get(position).getMembertype().equals("2")) {
                v.iv_collect.setVisibility(View.GONE);
                v.iv_has_collect.setVisibility(View.GONE);
            } else if (list.get(position).getMembertype().equals("3") || list.get(position).getMembertype().equals("4")) {
                if (list.get(position).getIsCollected() == 1) {
                    v.iv_has_collect.setVisibility(View.VISIBLE);
                    v.iv_collect.setVisibility(View.GONE);
                } else {
                    v.iv_has_collect.setVisibility(View.GONE);
                    v.iv_collect.setVisibility(View.VISIBLE);
                }
                v.iv_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        v.iv_has_collect.setVisibility(View.VISIBLE);
                        v.iv_collect.setVisibility(View.GONE);
                        int memberType = Integer.parseInt(list.get(position).getMembertype()) - 1;
                        collect("2", HappyiApplication.getInstance().getSolevar(context), list.get(position).getUserid(), memberType + "");
                    }
                });
                v.iv_has_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        v.iv_has_collect.setVisibility(View.GONE);
                        v.iv_collect.setVisibility(View.VISIBLE);
                        //下方的memberType需要减一是因为NGO=3,FUND=4，但是加粉丝的接口中，NGO为2，FUND为3
                        int memberType = Integer.parseInt(list.get(position).getMembertype()) - 1;
                        collect("1", HappyiApplication.getInstance().getSolevar(context), list.get(position).getUserid(), memberType + "");
                    }
                });
            }
        } else {

        }
        return view;
    }

    class ViewHolder {
        CircleImageView fansAvatar;
        TextView nickName, tv_content,tv_fans_number,tv_party_number;
        ImageView iv_collect, iv_has_collect;
    }

    public void collect(String status, String userid, String dataid, String type) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("status", status);
        params.addQueryStringParameter("dataid", dataid);
        params.addQueryStringParameter("type", type);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CollectBean collectBean = ParseUtils.parseCollectBean(result);
                    if (collectBean.getStatus() == 1) {
                        Toast.makeText(context, collectBean.getData(), Toast.LENGTH_SHORT).show();
                        list.clear();
                        notifyDataSetChanged();
                        handler.sendEmptyMessage(RefreshFragment.LOAD);
                    } else {
                        Toast.makeText(context, collectBean.getInfo(), Toast.LENGTH_SHORT).show();
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
