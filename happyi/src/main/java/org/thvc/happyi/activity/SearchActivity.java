package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.SearchListAdapter;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.SearchListBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

import java.util.ArrayList;

/**
 * 项目名称：klandroid
 * 类描述：主页搜索跳转页面
 * 创建人：谢庆华.
 * 创建时间：2015/12/3 11:37
 * 修改人：Administrator
 * 修改时间：2015/12/3 11:37
 * 修改备注：
 */
public class SearchActivity extends BaseSwipeBackActivity {
    private EditText et_search;
    private ImageView iv_empty;
    private ArrayList<SearchListBean.DataEntity> searchlist;
    //    private ArrayList<SearchListBean.DataEntity> searchlists = new ArrayList<>();
    private ListView lv_party_search;
    private SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        et_search = (EditText) findViewById(R.id.et_search);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);

        iv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空et_search
                et_search.setText("");
            }
        });
        lv_party_search = (ListView) findViewById(R.id.lv_party_search);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSearchList(et_search.getText().toString().trim());
                iv_empty.setVisibility(View.VISIBLE);
                iv_empty.setClickable(true);
                if (et_search.getText().toString().trim().equals("")) {
                    iv_empty.setVisibility(View.INVISIBLE);
                    if (searchlist != null && searchlist.size() != 0) {
                        searchlist.clear();
                        searchListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getSearchList(final String titles) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("title", titles);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FIND_TITLE + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    SearchListBean searchListBean = ParseUtils.parseSearchListBean(result);
                    if (searchListBean.getStatus() == 1) {
                        searchlist = (ArrayList<SearchListBean.DataEntity>) searchListBean.getData();
                        if (searchlist != null && searchlist.size() != 0) {
                            searchListAdapter = new SearchListAdapter(searchlist, SearchActivity.this, titles);
                            lv_party_search.setAdapter(searchListAdapter);
                            searchListAdapter.notifyDataSetChanged();
                            lv_party_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(SearchActivity.this, PartyNativeDetailActivity.class);
                                    intent.putExtra("id", searchlist.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(SearchActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
