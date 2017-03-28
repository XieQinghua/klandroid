package org.thvc.happyi.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import org.thvc.happyi.adapter.PartyPeopleAdapter;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.JoinPeopleBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * 项目名称：klandroid
 * 类描述：
 * 创建人：谢庆华.
 * 创建时间：2016/3/30 16:08
 * 修改人：Administrator
 * 修改时间：2016/3/30 16:08
 * 修改备注：
 */
public class MoreJoinPeopleActivity extends BaseSwipeBackActivity {
    private ArrayList<JoinPeopleBean.DataEntity.ListEntity> list;
    private PartyPeopleAdapter adapter;
    private ListView lv_party_people;
    private TextView tv_empty;
    private String partyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_joinpeople);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        partyId = getIntent().getStringExtra("id");
        lv_party_people = (ListView) findViewById(R.id.lv_party_people);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", partyId);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_JOIN_PEOPLE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    JoinPeopleBean joinPeopleBean = ParseUtils.parseJoinPeopleBean(result);
                    if (joinPeopleBean.getStatus() == 1) {
                        if (joinPeopleBean.getData().getList() != null && joinPeopleBean.getData().getList().size() != 0) {
                            list = (ArrayList<JoinPeopleBean.DataEntity.ListEntity>) joinPeopleBean.getData().getList();
                            adapter = new PartyPeopleAdapter(MoreJoinPeopleActivity.this, list);
                            lv_party_people.setAdapter(adapter);
                        } else {
                            tv_empty.setText("该活动还没有人员报名");
                            lv_party_people.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(MoreJoinPeopleActivity.this, joinPeopleBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MoreJoinPeopleActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.e(s);
            }
        });
    }
}
