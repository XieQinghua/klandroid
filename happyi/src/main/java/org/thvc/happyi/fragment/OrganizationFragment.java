package org.thvc.happyi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.thvc.happyi.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public class OrganizationFragment extends Fragment {

    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction tran;
    private BaseFragment bf;
    private LinearLayout relative_ngo, relative_Foundation;
    private TextView tv_ngo, tv_Foundation;
    private View view_ngo, view_Foundation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建一个fragment管理器
        fragmentManager = getFragmentManager();
        init();
    }

    public void init() {
        relative_ngo = (LinearLayout) getView().findViewById(R.id.relative_ngo);
        relative_Foundation = (LinearLayout) getView().findViewById(R.id.relative_Foundation);
        tv_ngo = (TextView) getView().findViewById(R.id.tv_ngo);
        tv_Foundation = (TextView) getView().findViewById(R.id.tv_Foundation);
        view_ngo = (View) getView().findViewById(R.id.view_ngo);
        view_Foundation = (View) getView().findViewById(R.id.view_Foundation);

        relative_ngo.setOnClickListener(new MyOnClickListener());
        relative_Foundation.setOnClickListener(new MyOnClickListener());
        if (!(bf instanceof NgoListFragment)) {
            bf = new NgoListFragment();
            replaceFragment(bf);
        }

    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relative_ngo:
                    tv_ngo.setTextColor(getResources().getColor(R.color.orangered));
                    view_ngo.setVisibility(View.VISIBLE);
                    tv_Foundation.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_Foundation.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof NgoListFragment)) {
                        bf = new NgoListFragment();
                        replaceFragment(bf);
                    }
                    break;
                case R.id.relative_Foundation:
                    tv_Foundation.setTextColor(getResources().getColor(R.color.orangered));
                    view_Foundation.setVisibility(View.VISIBLE);
                    tv_ngo.setTextColor(getResources().getColor(R.color.happyi_content_color));
                    view_ngo.setVisibility(View.INVISIBLE);
                    if (!(bf instanceof FoundationListFragment)) {
                        bf = new FoundationListFragment();
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
    private void replaceFragment(BaseFragment activeFragment) {
        // 通过管理器开启一个事物
        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.ngo_frame, activeFragment);// 替换
        tran.commit(); // 提交事物
    }

}
