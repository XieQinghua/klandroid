package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.fragment.BaseFragment;
import org.thvc.happyi.fragment.RefundFragment;
import org.thvc.happyi.fragment.RefundHistoryFragment;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/21.
 * 退款操作页面
 * 修改：huangxinqi
 * 修改内容：原本的fragment一部分为app.supportv4.fragment，一部分为app.fragment 现在统一为v4
 */
public class RefundActivity extends BaseSwipeBackActivity {
    private LinearLayout relativ_layout_tuikuan;//退款控件
    private LinearLayout relativ_layout_jilu;//退款记录控件

    private TextView tv_tuikuan;
    private TextView tv_jilu;
    private TextView line1, line2;

    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private BaseFragment bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refun);
        //创建一个fragment管理器
        fragmentManager = getSupportFragmentManager();
        init();

    }

    public void init() {
        relativ_layout_tuikuan = (LinearLayout) this.findViewById(R.id.relativ_layout_tuikuan);
        relativ_layout_jilu = (LinearLayout) this.findViewById(R.id.relativ_layout_jilu);

        tv_tuikuan = (TextView) this.findViewById(R.id.tv_tuikuan);
        tv_jilu = (TextView) this.findViewById(R.id.tv_jilu);

        line1 = (TextView) this.findViewById(R.id.line1);
        line2 = (TextView) this.findViewById(R.id.line2);

        relativ_layout_tuikuan.setOnClickListener(new MyOnClickListener());
        relativ_layout_jilu.setOnClickListener(new MyOnClickListener());

        if (!(bf instanceof RefundFragment)) {
            bf = new RefundFragment();
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
            // Fragment的一个管理器
            bf = (BaseFragment) fragmentManager
                    .findFragmentById(R.id.frame_layout_main); // ImageFragment
            switch (v.getId()) {
                case R.id.relativ_layout_tuikuan:
                    tv_tuikuan.setTextColor(RefundActivity.this.getResources().getColor(R.color.orangered));
                    tv_jilu.setTextColor(RefundActivity.this.getResources().getColor(R.color.party_text_color));
                    line1.setVisibility(View.VISIBLE);
                    line2.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof RefundFragment)) {
                        bf = new RefundFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.relativ_layout_jilu:
                    line1.setVisibility(View.INVISIBLE);
                    line2.setVisibility(View.VISIBLE);
                    tv_jilu.setTextColor(RefundActivity.this.getResources().getColor(R.color.orangered));
                    tv_tuikuan.setTextColor(RefundActivity.this.getResources().getColor(R.color.party_text_color));
                    if (!(bf instanceof RefundHistoryFragment)) {
                        bf = new RefundHistoryFragment();
                        replaceFragment(bf);
                    }
                    break;
            }

        }

    }

    /**
     * 替换一个Fragment
     *
     * @param bf
     */
    private void replaceFragment(BaseFragment bf) {
        // 通过管理器开启一个事物
        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.frame_layout_main, bf);// 替换
        tran.commit(); // 提交事物
    }

}
