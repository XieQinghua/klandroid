package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.fragment.PartyDoneFragment;
import org.thvc.happyi.fragment.PartyInFragment;
import org.thvc.happyi.fragment.PartyNoStartFragment;
import org.thvc.happyi.view.PullToRefreshView;

/**
 * 项目名称：klandroid
 * 类描述：个人我的活动页面
 * 创建人：谢庆华.
 * 创建时间：2015/11/24 10:16
 * 修改人：Administrator
 * 修改时间：2015/11/24 10:16
 * 修改备注：
 */
public class OrdinaryMypartyActivity extends BaseSwipeBackActivity implements View.OnClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    private PartyInFragment partyInFragment;
    private PartyDoneFragment partyDoneFragment;
    private PartyNoStartFragment partyNoStartFragment;

    private TextView tvPartyNoStart, tvPartyIn, tvPartyDone;
    private TextView line1, line2, line3;

    private LinearLayout tabPartyIn;
    private LinearLayout tabPartyDone;
    private LinearLayout tabPartyNoStart;

    private ScrollView sv_myParty;
    private PullToRefreshView pullToRefreshView;
    private FragmentManager fragmentManager;

    private int tabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordinary_my_party);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        init();
        setSelectedTab(0);
        sv_myParty.smoothScrollTo(0, 0);
    }

    private void init() {
        tabPartyDone = (LinearLayout) this.findViewById(R.id.tab_party_done);
        tabPartyIn = (LinearLayout) findViewById(R.id.tab_party_in);
        tabPartyNoStart = (LinearLayout) findViewById(R.id.tab_party_nostart);

        tabPartyNoStart.setOnClickListener(this);
        tabPartyDone.setOnClickListener(this);
        tabPartyIn.setOnClickListener(this);


        tvPartyNoStart = (TextView) findViewById(R.id.tv_party_nostart);
        tvPartyIn = (TextView) findViewById(R.id.tv_party_in);
        tvPartyDone = (TextView) findViewById(R.id.tv_party_done);
        line1 = (TextView) findViewById(R.id.line1);
        line2 = (TextView) findViewById(R.id.line2);
        line3 = (TextView) findViewById(R.id.line3);

        sv_myParty = (ScrollView) findViewById(R.id.sv_myparty);

        pullToRefreshView = (PullToRefreshView) findViewById(R.id.refresh_myparty);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);

        fragmentManager = getSupportFragmentManager();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_party_nostart:
                setSelectedTab(0);
                break;
            case R.id.tab_party_in:
                setSelectedTab(1);
                break;

            case R.id.tab_party_done:
                setSelectedTab(2);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void resetTab() {
        tvPartyDone.setTextColor(getResources().getColor(R.color.party_text_color));
        tvPartyIn.setTextColor(getResources().getColor(R.color.party_text_color));
        tvPartyNoStart.setTextColor(getResources().getColor(R.color.party_text_color));
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
                tvPartyNoStart.setTextColor(getResources().getColor(R.color.orangered));
                line1.setVisibility(View.VISIBLE);
                if (partyNoStartFragment == null) {
                    partyNoStartFragment = new PartyNoStartFragment();
                    fragmentTransaction.add(R.id.fl_party_list, partyNoStartFragment);
                } else {
                    fragmentTransaction.show(partyNoStartFragment);
                }
                break;
            case 1:
                tvPartyIn.setTextColor(getResources().getColor(R.color.orangered));
                line2.setVisibility(View.VISIBLE);
                if (partyInFragment == null) {
                    partyInFragment = new PartyInFragment();
                    fragmentTransaction.add(R.id.fl_party_list, partyInFragment);
                } else {
                    fragmentTransaction.show(partyInFragment);
                }
                break;
            case 2:
                tvPartyDone.setTextColor(getResources().getColor(R.color.orangered));
                line3.setVisibility(View.VISIBLE);
                if (partyDoneFragment == null) {
                    partyDoneFragment = new PartyDoneFragment();
                    fragmentTransaction.add(R.id.fl_party_list, partyDoneFragment);
                } else {
                    fragmentTransaction.show(partyDoneFragment);
                }
                break;
        }
        fragmentTransaction.commit();
        sv_myParty.smoothScrollTo(0, 0);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (partyDoneFragment != null) {
            fragmentTransaction.hide(partyDoneFragment);
        }
        if (partyInFragment != null) {
            fragmentTransaction.hide(partyInFragment);
        }
        if (partyNoStartFragment != null) {
            fragmentTransaction.hide(partyNoStartFragment);
        }
    }

    /**
     * 上拉加载
     *
     * @param view
     */
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        switch (tabIndex) {
            case 0:
                partyNoStartFragment.loadDataList();
                pullToRefreshView.onFooterRefreshComplete();
                break;
            case 1:
                partyInFragment.loadDataList();
                pullToRefreshView.onFooterRefreshComplete();
                break;
            case 2:
                partyDoneFragment.loadDataList();
                pullToRefreshView.onFooterRefreshComplete();
                break;
        }
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
                        partyNoStartFragment.refreshDataList();
                        pullToRefreshView.onHeaderRefreshComplete();
                        break;
                    case 1:
                        partyInFragment.refreshDataList();
                        pullToRefreshView.onHeaderRefreshComplete();
                        break;
                    case 2:
                        partyDoneFragment.refreshDataList();
                        pullToRefreshView.onHeaderRefreshComplete();
                        break;
                }
                pullToRefreshView.onHeaderRefreshComplete();
                sv_myParty.smoothScrollTo(0, 0);
            }
        }, 500);

    }
}
