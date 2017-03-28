package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.NgoDetailbean;
import org.thvc.happyi.fragment.BaseFragment1;
import org.thvc.happyi.fragment.NgoHomeFragment;
import org.thvc.happyi.fragment.NgoPartyFragment;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CircleImageView;

/**
 * 项目名称：klandroid
 * 类描述：原生NGO详情
 * 创建人：谢庆华.
 * 创建时间：2016/1/20 11:21
 * 修改人：Administrator
 * 修改时间：2016/1/20 11:21
 * 修改备注：
 */
public class NgoNativeDetailActivity extends BaseSwipeBackActivity {
    private static final String TAG = "NgoNative";
    private CircleImageView iv_ngo_img;
    private TextView tv_ngo_fans, tv_collect, tv_ngo_name, tv_ngo_intro, tv_ngo_party;
    private ImageView im_image;
    private RelativeLayout rl_ngo_party;
    private String id;
    private ListView lv_ngo_partys;
    private String userid;

    private LinearLayout relative_homepage, relative_pary;
    private TextView tv_homepage, tv_pary;
    private TextView view_homepage, view_pary;
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private BaseFragment1 bf;
    public static String intros = null;

    private RelativeLayout rl_layout_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_native_details);
        userid = HappyiApplication.getInstance().getUserid(NgoNativeDetailActivity.this);

        //创建一个fragment管理器
        fragmentManager = getFragmentManager();
        rl_layout_title = (RelativeLayout) findViewById(R.id.rl_layout_title);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = rl_layout_title.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;
        rl_layout_title.setLayoutParams(para);

        relative_homepage = (LinearLayout) this.findViewById(R.id.relative_ngo_home);
        relative_pary = (LinearLayout) this.findViewById(R.id.relative_ngo_party);
        tv_homepage = (TextView) this.findViewById(R.id.tv_ngo_home);
        tv_pary = (TextView) this.findViewById(R.id.tv_ngo_party);
        view_homepage = (TextView) this.findViewById(R.id.view_ngo_home);
        view_pary = (TextView) this.findViewById(R.id.view_ngo_party);

        relative_homepage.setOnClickListener(new MyOnClickListener());
        relative_pary.setOnClickListener(new MyOnClickListener());


        iv_ngo_img = (CircleImageView) findViewById(R.id.iv_ngo_img);
        tv_ngo_fans = (TextView) findViewById(R.id.tv_ngo_fans);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        tv_ngo_name = (TextView) findViewById(R.id.tv_ngo_name);
//        tv_ngo_intro = (TextView) findViewById(R.id.tv_ngo_intro);
//        tv_ngo_party = (TextView) findViewById(R.id.tv_ngo_party);
//        rl_ngo_party = (RelativeLayout) findViewById(R.id.rl_ngo_party);
        im_image = (ImageView) findViewById(R.id.im_image);
//        lv_ngo_partys = (ListView) findViewById(R.id.lv_ngo_partys);
//        ngoPartyListAdapter = new NgoPartyListAdapter(lists, this);
//        lv_ngo_partys.setAdapter(ngoPartyListAdapter);
//        rl_ngo_party.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userid.equals("")) {
                    String status = "";
                    String collect = tv_collect.getText().toString().trim();
                    if (collect.equals("已关注")) {
                        status = "1";
                    } else if (collect.equals("关注")) {
                        status = "2";
                    }
                    collectNgo(status);
                } else {
                    startActivity(new Intent(NgoNativeDetailActivity.this, LoginActivity.class));
                }
            }
        });
//        lv_ngo_partys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(NgoNativeDetailActivity.this, PartyNativeDetailActivity.class);
//                intent.putExtra("id", lists.get(position).getId());
//                startActivity(intent);
//            }
//        });
        getData();
        if (!(bf instanceof NgoHomeFragment)) {
            bf = new NgoHomeFragment();
            replaceFragment(bf);
        }

//        getNgoParty();
    }


    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            bf = (BaseFragment1) fragmentManager
                    .findFragmentById(R.id.ngo_frame);
            switch (v.getId()) {
                case R.id.relative_ngo_home:
                    tv_homepage.setTextColor(getResources().getColor(R.color.orangered));
                    view_homepage.setVisibility(View.VISIBLE);
                    tv_pary.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_pary.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof NgoHomeFragment)) {
                        bf = new NgoHomeFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.relative_ngo_party:
                    tv_pary.setTextColor(getResources().getColor(R.color.orangered));
                    view_pary.setVisibility(View.VISIBLE);
                    tv_homepage.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_homepage.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof NgoPartyFragment)) {
                        bf = new NgoPartyFragment();
                        replaceFragment(bf);
                    }
                    break;

            }
        }
    }


    private void collectNgo(final String status) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("status", status);//TODO 此处要做详情页面如果显示“已关注”按键考虑，暂定写死只做2添加关注操作
        params.addQueryStringParameter("dataid", id);
        params.addQueryStringParameter("type", 2 + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CollectBean collectBean = ParseUtils.parseCollectBean(result);
                    if (collectBean.getStatus() == 1) {
                        if (status.equals("1")) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_oranged_bg));
                            tv_collect.setText("关注");
                        } else if (status.equals("2")) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_gray_bg));
                            tv_collect.setText("已关注");
                        }
//                        Toast.makeText(NgoNativeDetailActivity.this, collectBean.getData(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NgoNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });

    }

    private void getData() {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", id);
        if (HappyiApplication.getInstance().getUserid(NgoNativeDetailActivity.this) != null) {
            params.addQueryStringParameter("loginuser", HappyiApplication.getInstance().getUserid(NgoNativeDetailActivity.this));
        }
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.FIND_NGO + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    NgoDetailbean ngoDetailbean = ParseUtils.parseNgoDetailbean(result);
                    if (ngoDetailbean.getStatus() == 1) {
                        ImgUtils.setHeadImage(iv_ngo_img, ngoDetailbean.getData().getHeadpic());
                        tv_ngo_fans.setText(ngoDetailbean.getData().getCollect());
                        if (ngoDetailbean.getData().getHascollect() == 1) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_gray_bg));
                            tv_collect.setText("已关注");
                        } else if (ngoDetailbean.getData().getHascollect() == 2) {
                            tv_collect.setBackground(getResources().getDrawable(R.drawable.btn_oranged_bg));
                            tv_collect.setText("关注");
                        }
                        tv_ngo_name.setText(ngoDetailbean.getData().getNickname());
//                        tv_ngo_intro.setText(ngoDetailbean.getData().getContent());
                        intros = ngoDetailbean.getData().getContent();
                    }
                } else {
                    Toast.makeText(NgoNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                Log.e(TAG, s);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rl_ngo_party:
//                SharedPreferences setting = getSharedPreferences("SHARE_APP_TAG", 0);
//                Boolean user_first = setting.getBoolean("SHARE_APP_TAG", true);
//                if (user_first) {//第一次
//                    setting.edit().putBoolean("SHARE_APP_TAG", false).commit();
//                    lv_ngo_partys.setVisibility(View.VISIBLE);
//                    im_image.setImageResource(R.drawable.arrows_down);
//                } else {
//                    setting.edit().putBoolean("SHARE_APP_TAG", true).commit();
//                    lv_ngo_partys.setVisibility(View.GONE);
//                    im_image.setImageResource(R.drawable.arrows_up);
//                }
//                break;
//        }
//    }

//    public void getNgoParty() {
//        HttpUtils httpUtils = new HttpUtils();
//        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
//        params.addQueryStringParameter("ngoid", id);
//        String url = params.myRequestParams(params);
//        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.NGO_PARTY_LIST + url, new RequestCallBack<String>() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                if (result != null) {
//                    NgoPartyListBean ngoPartyListBean = ParseUtils.parseNgoPartyListBean(result);
//                    if (ngoPartyListBean.getStatus() == 1) {
//                        list = (ArrayList<NgoPartyListBean.DataEntity.ListEntity>) ngoPartyListBean.getData().getList();
//                        if (list != null && list.size() != 0) {
//                            lists.addAll(list);
//                            ngoPartyListAdapter.notifyDataSetChanged();
//                        } else {
//                            tv_ngo_party.setText("他没有发布相关的活动");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
//                Log.e(TAG, s);
//            }
//        });
//
//    }

    /**
     * 替换一个Fragment
     *
     * @param activeFragment
     */
    private void replaceFragment(BaseFragment1 activeFragment) {
        // 通过管理器开启一个事物
        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.ngo_frame, activeFragment);// 替换
        tran.commit(); // 提交事物
    }

}
