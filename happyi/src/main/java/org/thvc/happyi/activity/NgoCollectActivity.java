package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.fragment.CollectFundFragment;
import org.thvc.happyi.fragment.CollectNgoFragment;
import org.thvc.happyi.fragment.NgoCollectPartyFragment;
import org.thvc.happyi.view.PullToRefreshView;

/**
 * 项目名称：klandroid
 * 类描述：Ngo我的关注页面
 * 创建人：谢庆华.
 * 创建时间：2015/12/22 17:45
 * 修改人：Administrator
 * 修改时间：2015/12/22 17:45
 * 修改备注：个人用户我的关注和NGO我的关注区别在于关注活动列表不同
 */
public class NgoCollectActivity extends BaseSwipeBackActivity implements View.OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private CollectFundFragment fundFragment;
    private CollectNgoFragment ngoFragment;
    private NgoCollectPartyFragment partyFragment;

    private TextView tvParty, tvNgo, tvFoun;
    private TextView line1, line2, line3;
    private LinearLayout tabFund, tabParty, tabNGO;

    private ScrollView sv_mycollect;
    private PullToRefreshView pullToRefreshView;
    private FragmentManager fragmentManager;

    private int tabIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        initView();
        setSelectedTab(0);
    }

    private void initView() {

        tabFund = (LinearLayout) findViewById(R.id.tab_fund);
        tabParty = (LinearLayout) findViewById(R.id.tab_party);
        tabNGO = (LinearLayout) findViewById(R.id.tab_ngo);

        tabFund.setOnClickListener(this);
        tabParty.setOnClickListener(this);
        tabNGO.setOnClickListener(this);

        tvParty = (TextView) findViewById(R.id.tv_party);
        tvNgo = (TextView) findViewById(R.id.tv_ngo);
        tvFoun = (TextView) findViewById(R.id.tv_foun);
        line1 = (TextView) findViewById(R.id.line1);
        line2 = (TextView) findViewById(R.id.line2);
        line3 = (TextView) findViewById(R.id.line3);

        sv_mycollect = (ScrollView) findViewById(R.id.sv_mycollect);

        pullToRefreshView = (PullToRefreshView) findViewById(R.id.refresh_mycollect);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        fragmentManager = getSupportFragmentManager();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_party:
                setSelectedTab(0);
                break;
            case R.id.tab_ngo:
                setSelectedTab(1);
                break;
            case R.id.tab_fund:
                setSelectedTab(2);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void resetTab() {
        tvParty.setTextColor(getResources().getColor(R.color.party_text_color));
        tvNgo.setTextColor(getResources().getColor(R.color.party_text_color));
        tvFoun.setTextColor(getResources().getColor(R.color.party_text_color));
        line1.setVisibility(View.INVISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setSelectedTab(int tabIndex) {
        this.tabIndex = tabIndex;
        resetTab();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (tabIndex) {
            case 0:
                tvParty.setTextColor(getResources().getColor(R.color.orangered));
                line1.setVisibility(View.VISIBLE);
                if (partyFragment == null) {
                    partyFragment = new NgoCollectPartyFragment();
                    fragmentTransaction.add(R.id.fl_collect_list, partyFragment);
                } else {
                    fragmentTransaction.show(partyFragment);
                }
                break;
            case 1:
                tvNgo.setTextColor(getResources().getColor(R.color.orangered));
                line2.setVisibility(View.VISIBLE);
                if (ngoFragment == null) {
                    ngoFragment = new CollectNgoFragment();
                    fragmentTransaction.add(R.id.fl_collect_list, ngoFragment);
                } else {
                    fragmentTransaction.show(ngoFragment);
                }
                break;
            case 2:
                tvFoun.setTextColor(getResources().getColor(R.color.orangered));
                line3.setVisibility(View.VISIBLE);
                if (fundFragment == null) {
                    fundFragment = new CollectFundFragment();
                    fragmentTransaction.add(R.id.fl_collect_list, fundFragment);
                } else {
                    fragmentTransaction.show(fundFragment);
                }
                break;
        }
        fragmentTransaction.commit();
        //将焦点移到顶部
        sv_mycollect.smoothScrollTo(0, 0);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (fundFragment != null) {
            fragmentTransaction.hide(fundFragment);
        }
        if (ngoFragment != null) {
            fragmentTransaction.hide(ngoFragment);
        }
        if (partyFragment != null) {
            fragmentTransaction.hide(partyFragment);
        }
    }

    /**
     * 上拉加载
     *
     * @param view
     */
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case 0:
                        partyFragment.loadDataList();
                        break;
                    case 1:
                        ngoFragment.loadDataList();
                        break;
                    case 2:
                        fundFragment.loadDataList();
                        break;
                }
                pullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    /**
     * 下拉刷新
     *
     * @param view
     */
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case 0:
                        partyFragment.refreshDataList();
                        break;
                    case 1:
                        ngoFragment.refreshDataList();
                        break;
                    case 2:
                        fundFragment.refreshDataList();
                        break;
                }
                pullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1500);

    }
}
