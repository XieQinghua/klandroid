package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.fragment.BaseFragment1;
import org.thvc.happyi.fragment.NgoAuditPartyFragment;
import org.thvc.happyi.fragment.NgoPassPartyFragment;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/16.
 * 我的活动页面
 * 修改：huangxinqi on 2015/12/1
 * 完成下拉刷新上拉加载，对接口
 */
public class NgoMyPartyActivity extends BaseSwipeBackActivity {
    private TextView tv_Through, tv_Auditing;
    private TextView line1, line2;
    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private BaseFragment1 bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_party);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //创建一个fragment管理器
        fragmentManager = getFragmentManager();
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        tv_Through = (TextView) this.findViewById(R.id.tv_Through);
        tv_Auditing = (TextView) this.findViewById(R.id.tv_Auditing);
        line1 = (TextView) this.findViewById(R.id.line1);
        line2 = (TextView) this.findViewById(R.id.line2);
        tv_Through.setOnClickListener(new MyOnClickListener());
        tv_Auditing.setOnClickListener(new MyOnClickListener());

        if (!(bf instanceof NgoPassPartyFragment)) {
            bf = new NgoPassPartyFragment();
            replaceFragment(bf);
        }
    }

    /**
     * 点击事件
     */
    class MyOnClickListener implements View.OnClickListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            bf = (BaseFragment1) fragmentManager.findFragmentById(R.id.frame_layout_main);
            switch (v.getId()) {
                case R.id.tv_Through:
                    tv_Through.setTextColor(NgoMyPartyActivity.this.getResources().getColor(R.color.orangered));
                    line1.setVisibility(View.VISIBLE);
                    line2.setVisibility(View.INVISIBLE);
                    tv_Auditing.setTextColor(NgoMyPartyActivity.this.getResources().getColor(R.color.party_text_color));
                    if (!(bf instanceof NgoPassPartyFragment)) {
                        bf = new NgoPassPartyFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.tv_Auditing:
                    tv_Auditing.setTextColor(NgoMyPartyActivity.this.getResources().getColor(R.color.orangered));
                    line1.setVisibility(View.INVISIBLE);
                    line2.setVisibility(View.VISIBLE);
                    tv_Through.setTextColor(NgoMyPartyActivity.this.getResources().getColor(R.color.party_text_color));
                    if (!(bf instanceof NgoAuditPartyFragment)) {
                        bf = new NgoAuditPartyFragment();
                        replaceFragment(bf);
                    }
                    break;
            }
        }
    }

    /**
     * 替换一个Fragment
     *
     * @param activeFragment
     */
    private void replaceFragment(BaseFragment1 activeFragment) {
        // 通过管理器开启一个事物
        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.frame_layout_main, activeFragment);// 替换
        tran.commit(); // 提交事物
    }
}
