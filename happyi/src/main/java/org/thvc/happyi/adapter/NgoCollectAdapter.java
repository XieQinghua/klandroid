package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.NGOCollectBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * 关注的ngo的列表适配器
 * Created by huangxinqi
 * on 2015/11/26-11:00.
 * 备注：取消关注功能没有实现
 * 修改：huangxinqi
 * on 2015.12.11
 * 备注：完成取消该关注
 */
public class NgoCollectAdapter extends BaseAdapter {
    private ArrayList<NGOCollectBean.DataEntity.ListEntity> dataList;
    private Context context;

    public NgoCollectAdapter(ArrayList<NGOCollectBean.DataEntity.ListEntity> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_collect_ngo, null);
            vh.civNGOHead = (CircleImageView) view.findViewById(R.id.iv_ngo_img);
            vh.tvFans = (TextView) view.findViewById(R.id.tv_ngo_fans);
            vh.tvPartys = (TextView) view.findViewById(R.id.tv_ngo_partys);
            vh.tvNGOName = (TextView) view.findViewById(R.id.tv_ngo_name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        ImgUtils.setNGOHeadImage(vh.civNGOHead, dataList.get(i).getHeadpic());
        vh.tvFans.setText(dataList.get(i).getCollect());
        vh.tvPartys.setText(dataList.get(i).getPartycount());
        vh.tvNGOName.setText(dataList.get(i).getNickname());
        return view;
    }

    class ViewHolder {
        CircleImageView civNGOHead;
        TextView tvFans, tvPartys, tvNGOName;
    }

    public void collectNgo(String status, String userid, String dataid, String type) {
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
