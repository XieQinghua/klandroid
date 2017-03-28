package org.thvc.happyi.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.PartyDetailBean;
import org.thvc.happyi.fragment.CommentFragment;
import org.thvc.happyi.fragment.FansFragment;
import org.thvc.happyi.fragment.JoinPeopleFragment;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/23.
 * NGO详情页面（此页面已经放弃使用）
 */
public class NgoPartyDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener, PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    private FansFragment fansFragment;
    private CommentFragment commentFragment;
    private JoinPeopleFragment joinPeopleFragment;

    //切换tab
    private LinearLayout tabJoinPeople;
    private LinearLayout tabFans;
    private LinearLayout tabComment;

    private TextView ivJoinPeople;
    private TextView ivComment;
    private TextView ivFans;

    private TextView tvJoinPeople;
    private TextView tvFans;
    private TextView tvComment;

    private ScrollView svMyPartyDetail;
    private PullToRefreshView pullToRefreshView;
    private FragmentManager fragmentManager;

    private int tabIndex = 0;

    private ImageView iv_party_subject;//活动主题图片
    private TextView tv_party_label1;//活动标签
    private TextView tv_party_label2;
    private TextView tv_party_label3;
    private TextView tv_party_name;
    private TextView tv_address;//地址
    private TextView tv_deadline;//截止时间
    private TextView tv_party_money;//活动金额
    private TextView tv_money;//配比金额
    private TextView tv_number;//喜欢人数

    private PartyDetailBean partyDetailBean;
    private String id;//活动id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_party_detail);
        init();
        setTabSelected(0);

    }

    private void init() {
        tabComment = (LinearLayout) findViewById(R.id.tab_comment);
        tabFans = (LinearLayout) findViewById(R.id.tab_fans);
        tabJoinPeople = (LinearLayout) findViewById(R.id.tab_party_join_people);

        ivComment = (TextView) findViewById(R.id.iv_comment);
        ivFans = (TextView) findViewById(R.id.iv_fans);
        ivJoinPeople = (TextView) findViewById(R.id.iv_party_join_people);

        tvJoinPeople = (TextView) findViewById(R.id.tv_join_people);
        tvComment = (TextView) findViewById(R.id.tv_comment);
        tvFans = (TextView) findViewById(R.id.tv_fans);

        tabFans.setOnClickListener(this);
        tabJoinPeople.setOnClickListener(this);
        tabComment.setOnClickListener(this);

        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh_view);
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);

        svMyPartyDetail = (ScrollView) findViewById(R.id.sv_ngo_party_detail);
        fragmentManager = getSupportFragmentManager();

        iv_party_subject = (ImageView) this.findViewById(R.id.iv_party_subject);
        tv_party_label1 = (TextView) this.findViewById(R.id.tv_party_label1);
        tv_party_label2 = (TextView) this.findViewById(R.id.tv_party_label2);
        tv_party_label3 = (TextView) this.findViewById(R.id.tv_party_label3);

        tv_party_name = (TextView) this.findViewById(R.id.tv_party_name);
        tv_address = (TextView) this.findViewById(R.id.tv_address);
        tv_deadline = (TextView) this.findViewById(R.id.tv_deadline);
        tv_party_money = (TextView) this.findViewById(R.id.tv_party_money);
        tv_money = (TextView) this.findViewById(R.id.tv_money);
        tv_number = (TextView) this.findViewById(R.id.tv_number);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = iv_party_subject.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;//活动主题图片长高设置为屏幕宽度2/3
        iv_party_subject.setLayoutParams(para);


        HttpUtils httpUtils = new HttpUtils();
        id = getIntent().getStringExtra("partyId");
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("id", id);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.PARTY_DETAIL + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    partyDetailBean = ParseUtils.parsePartyDetailBean(result);
                    fillPartyDetail(partyDetailBean);
                } else {
                    Toast.makeText(NgoPartyDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    /**
     * 给活动的详情填充数据
     *
     * @param partyDetailBean
     */
    private void fillPartyDetail(PartyDetailBean partyDetailBean) {
        if (partyDetailBean.getData().getSubject() != null) {
            ImgUtils.setRectangleImage(iv_party_subject, partyDetailBean.getData().getSubject().get(0));//TODO 此处主题图片为多图数组
        }
        if (partyDetailBean.getData().getStatuscn() != null && partyDetailBean.getData().getStatuscn().size() != 0) {
            for (int i = 0; i < partyDetailBean.getData().getStatuscn().size(); i++) {
                if (partyDetailBean.getData().getStatuscn().size() == 3) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.VISIBLE);
                    tv_party_label3.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        tv_party_label1.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        tv_party_label2.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 2) {
                        tv_party_label3.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    }
                } else if (partyDetailBean.getData().getStatuscn().size() == 2) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.VISIBLE);
                    tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        tv_party_label1.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        tv_party_label2.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    }
                } else if (partyDetailBean.getData().getStatuscn().size() == 1) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.GONE);
                    tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        tv_party_label1.setText(partyDetailBean.getData().getStatuscn().get(i).getTitle());
                    }
                }
            }
        }
        tv_address.setText(partyDetailBean.getData().getDetail());
        tv_party_name.setText(partyDetailBean.getData().getTitle());
        tv_deadline.setText(getStringTime(partyDetailBean.getData().getEnrollend()));
        double money = Double.parseDouble(partyDetailBean.getData().getPrefee()) - Double.parseDouble(partyDetailBean.getData().getGetpre());
        tv_party_money.setText("￥" + (int) money);
        if (partyDetailBean.getData().getIsget().equals("2")) {
            tv_money.setText("已认证");
        } else if (partyDetailBean.getData().getIsget().equals("3")) {
            tv_money.setText("已认证");
        } else {
            tv_money.setText("未认证");
        }
        tv_number.setText("·  " + partyDetailBean.getData().getCollect() + "人喜欢");
    }

    private void reset() {
        tvFans.setTextColor(getResources().getColor(R.color.happyi_content_color));
        tvJoinPeople.setTextColor(getResources().getColor(R.color.happyi_content_color));
        tvComment.setTextColor(getResources().getColor(R.color.happyi_content_color));

        tabComment.setBackgroundColor(getResources().getColor(R.color.white));
        tabJoinPeople.setBackgroundColor(getResources().getColor(R.color.white));
        tabFans.setBackgroundColor(getResources().getColor(R.color.white));

    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (commentFragment != null) {
            fragmentTransaction.hide(commentFragment);
        }
        if (fansFragment != null) {
            fragmentTransaction.hide(fansFragment);
        }
        if (joinPeopleFragment != null) {
            fragmentTransaction.hide(joinPeopleFragment);
        }
    }

    public void setTabSelected(int tabIndex) {
        this.tabIndex = tabIndex;
        reset();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (tabIndex) {
            case 0:
                tvJoinPeople.setTextColor(getResources().getColor(R.color.orangered));
                ivJoinPeople.setVisibility(View.VISIBLE);
                ivComment.setVisibility(View.INVISIBLE);
                ivFans.setVisibility(View.INVISIBLE);
                if (joinPeopleFragment == null) {
                    joinPeopleFragment = new JoinPeopleFragment();
                    fragmentTransaction.add(R.id.fl_party_detail, joinPeopleFragment);
                } else {
                    fragmentTransaction.show(joinPeopleFragment);
                }
                break;
            case 1:
                tvComment.setTextColor(getResources().getColor(R.color.orangered));
                ivComment.setVisibility(View.VISIBLE);
                ivJoinPeople.setVisibility(View.INVISIBLE);
                ivFans.setVisibility(View.INVISIBLE);
                if (commentFragment == null) {
                    commentFragment = new CommentFragment();
                    fragmentTransaction.add(R.id.fl_party_detail, commentFragment);
                } else {
                    fragmentTransaction.show(commentFragment);
                }
                break;
            case 2:
                tvFans.setTextColor(getResources().getColor(R.color.orangered));
                ivFans.setVisibility(View.VISIBLE);
                ivComment.setVisibility(View.INVISIBLE);
                ivJoinPeople.setVisibility(View.INVISIBLE);
                if (fansFragment == null) {
                    fansFragment = new FansFragment();
                    fragmentTransaction.add(R.id.fl_party_detail, fansFragment);
                } else {
                    fragmentTransaction.show(fansFragment);
                }
                break;
        }
        fragmentTransaction.commit();
        svMyPartyDetail.smoothScrollTo(0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tab_party_join_people:
                setTabSelected(0);
                break;
            case R.id.tab_comment:
                setTabSelected(1);
                break;
            case R.id.tab_fans:
                setTabSelected(2);
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case 0:
//                        joinPeopleFragment.loadDataList();
                        break;
                    case 1:
                        commentFragment.loadDataList();
                        break;
                    case 2:
                        fansFragment.loadDataList();
                        break;
                }
                pullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case 0:
//                        joinPeopleFragment.refreshDataList();
                        break;
                    case 1:
                        commentFragment.refreshDataList();
                        break;
                    case 2:
                        fansFragment.refreshDataList();
                        break;
                }

                pullToRefreshView.onHeaderRefreshComplete();
                svMyPartyDetail.smoothScrollTo(0, 0);
            }
        }, 1000);
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");//精确到分钟
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}
