package org.thvc.happyi.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.fragment.AlreadyCertifiedFragment;
import org.thvc.happyi.fragment.BaseFragment1;
import org.thvc.happyi.fragment.RecommendClaimFragment;

/**
 * 项目名称：klandroid
 * 类描述：认证管理页面
 * 创建人：谢庆华.
 * 创建时间：2016/3/24 17:07
 * 修改人：Administrator
 * 修改时间：2016/3/24 17:07
 * 修改备注：
 */
public class AuthManageActivity extends BaseSwipeBackActivity {

    private LinearLayout relative_recommend, relative_Already_certified;
    private TextView tv_recommend, tv_Already_certified;
    private View view_Already_certified, view_recommend;

    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private BaseFragment1 bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_manage);
        //创建一个fragment管理器
        fragmentManager = getFragmentManager();
        init();
        if (!(bf instanceof RecommendClaimFragment)) {
            bf = new RecommendClaimFragment();
            replaceFragment(bf);
        }

    }

    public void init() {
        relative_recommend = (LinearLayout) findViewById(R.id.relative_recommend);
        relative_Already_certified = (LinearLayout) findViewById(R.id.relative_Already_certified);
        tv_recommend = (TextView) findViewById(R.id.tv_recommend);
        tv_Already_certified = (TextView) findViewById(R.id.tv_Already_certified);
        view_Already_certified = findViewById(R.id.view_Already_certified);
        view_recommend = findViewById(R.id.view_recommend);

        relative_recommend.setOnClickListener(new MyOnClickListener());
        relative_Already_certified.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relative_recommend:
                    tv_recommend.setTextColor(getResources().getColor(R.color.orangered));
                    view_recommend.setVisibility(View.VISIBLE);
                    tv_Already_certified.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_Already_certified.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof RecommendClaimFragment)) {
                        bf = new RecommendClaimFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.relative_Already_certified:
                    tv_Already_certified.setTextColor(getResources().getColor(R.color.orangered));
                    view_Already_certified.setVisibility(View.VISIBLE);
                    tv_recommend.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_recommend.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof AlreadyCertifiedFragment)) {
                        bf = new AlreadyCertifiedFragment();
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
        tran.replace(R.id.ngo_recommend, activeFragment);// 替换
        tran.commit(); // 提交事物
    }
}
