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
import org.thvc.happyi.bean.FundCollectListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * 关注的基金会列表
 */
public class FundCollectAdapter extends BaseAdapter {
    private ArrayList<FundCollectListBean.DataEntity.ListEntity> dataList;
    private Context context;

    public FundCollectAdapter(ArrayList<FundCollectListBean.DataEntity.ListEntity> dataList, Context context) {
        this.context = context;
        this.dataList = dataList;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_collect_fund, null);
            vh.iv_fund_img = (CircleImageView) view.findViewById(R.id.iv_fund_img);
            vh.tv_fund_name = (TextView) view.findViewById(R.id.tv_fund_name);
            vh.tv_fund_fans = (TextView) view.findViewById(R.id.tv_fund_fans);
            vh.tv_fund_partys = (TextView) view.findViewById(R.id.tv_fund_partys);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        ImgUtils.setHeadImage(vh.iv_fund_img, dataList.get(i).getHeadpic());
        vh.tv_fund_name.setText(dataList.get(i).getNickname());
        vh.tv_fund_fans.setText(dataList.get(i).getCollect());
        vh.tv_fund_partys.setText("100");//TODO 暂时没有数据返回
        return view;
    }

    class ViewHolder {
        CircleImageView iv_fund_img;
        TextView tv_fund_name, tv_fund_fans, tv_fund_partys;
    }

    public void collectFoun(String status, String userid, String dataid, String type) {
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
