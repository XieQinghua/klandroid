package org.thvc.happyi.fragment;

import android.annotation.TargetApi;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.thvc.happyi.activity.FundNativeDetailActivity;
import org.thvc.happyi.activity.NgoNativeDetailActivity;
import org.thvc.happyi.activity.PartyListActivity;
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.activity.PartyTypeActivity;
import org.thvc.happyi.activity.PartyWebDetailsActivity;
import org.thvc.happyi.activity.SearchActivity;
import org.thvc.happyi.adapter.NewGridListAdapter;
import org.thvc.happyi.adapter.PartyLabelAdapter;
import org.thvc.happyi.adapter.PartyRecommendAdapter;
import org.thvc.happyi.adapter.RecGridListAdapter;
import org.thvc.happyi.adapter.RecListAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.HomeIndexBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.CustomScrollView;
import org.thvc.happyi.view.PullToRefreshView;
import org.thvc.happyi.view.ScrollChangedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：klandroid
 * 类描述：首页
 * 创建人：谢庆华.
 * 创建时间：2015/11/6 16:03
 * 修改人：huagnxinqi
 * 修改时间：2015/11/21 15:08
 * 修改备注：增加了下拉刷新，上拉加载尚未完成
 */
public class HomeFragment extends Fragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private ScheduledExecutorService scheduledExecutorService;
    private ImageView iv_search;

    private FrameLayout fl_banner;
    private ViewGroup.LayoutParams para;
    private ViewPager viewPager;

    private List<HomeIndexBean.DataEntity.MarketEntity> market;//轮播图list数据

    private TextView bannerTitle;
    private List<View> dots; // 图片标题正文的那些点
    private LinearLayout v_dot;//轮播图切换点布局
    private int currentItem = 0; // 当前图片的索引号
    private int p = 1, page = 6, maxPage;
    public static final int LOADING_DIALOG = 0;

    private ArrayList<String> idList;
    private ArrayList<String> names;


    private PartyRecommendAdapter partyRecommendAdapter;
//    private ArrayList<HomePartBean.DataBean.ListBean>list;
//    private ArrayList<HomePartBean.DataBean.ListBean>lists = new ArrayList<>();


    private HttpUtils httpUtils;
    private PullToRefreshView mPullToRefreshView;
    private CustomScrollView sv_home;
    private RelativeLayout rl_layout;

    private static final String LOAD = "1";//加载数据
    private static final String PULL = "2";//上拉加载数据
    private static final String DROPDOWN = "3";//下拉加载数据

    private ViewPager mViewPager;    //下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)

    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private HorizontalScrollView mHideHorizontalScrollView;//隐藏的水平滚动控件
    private LinearLayout layout1, layout2;
    private ImageView mImageView1, mImageView2;

    //热门活动
    private PartyLabelAdapter partyLabelAdapter;
    private GridView gv_hot_label;
    private ListView lv_hot_party;
    private ImageView iv_hot_subject;
    private ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity> Hotlist;
    private ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity> HotlistGrid = new ArrayList<>();
    private ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity> HotlistList = new ArrayList<>();

    //精品活动
    private GridView gv_rec_label;
    private ListView lv_rec_party;
    private ImageView iv_rec_subject;
    private RecGridListAdapter recGridListAdapter;
    private RecListAdapter recListAdapter;
    private ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity> Reclist;
    private ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity> ReclistGrid = new ArrayList<>();
    private ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity> ReclistList = new ArrayList<>();

    //最新活动
    private GridView gv_new_label;
    private ImageView iv_new_subject;
    private NewGridListAdapter newGridListAdapter;
    private ArrayList<HomeIndexBean.DataEntity.NewEntity.NewDataEntity> Newlist;
    private ArrayList<HomeIndexBean.DataEntity.NewEntity.NewDataEntity> Newlists = new ArrayList<>();

    LocalActivityManager manager = null;

    private RadioGroup myRadioGroup;
    private RadioGroup myHideRadioGroup;

    private int _id = 1000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = new LocalActivityManager(getActivity(), true);
        manager.dispatchCreate(savedInstanceState);

        iv_search = (ImageView) getView().findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);


        /**轮播图*/
        fl_banner = (FrameLayout) getView().findViewById(R.id.fl_banner);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        para = fl_banner.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;//轮播图高设置为屏幕宽度2/3
        fl_banner.setLayoutParams(para);
        bannerTitle = (TextView) getView().findViewById(R.id.tv_banner_title);
        v_dot = (LinearLayout) getView().findViewById(R.id.v_dot);
        viewPager = (ViewPager) getView().findViewById(R.id.vp);
        viewPager.setOnTouchListener(new View.OnTouchListener() {//解决viewPager与PullToRefreshView冲突
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPullToRefreshView.requestDisallowInterceptTouchEvent(true);
                } else {
                    mPullToRefreshView.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        /**活动标签*/
        layout1 = (LinearLayout) getView().findViewById(R.id.lay1);
        mImageView1 = (ImageView) getView().findViewById(R.id.img1);
        layout2 = (LinearLayout) getView().findViewById(R.id.lay2);
        mImageView2 = (ImageView) getView().findViewById(R.id.img2);
//        mImageView2.setLayoutParams(new LinearLayout.LayoutParams(itemWidth + radio.getPaddingLeft() + radio.getPaddingRight(),
//                2 * (int) getResources().getDimension(R.dimen.dp)));
        mHorizontalScrollView = (HorizontalScrollView) getView().findViewById(R.id.horizontalScrollView1);
        mHideHorizontalScrollView = (HorizontalScrollView) getView().findViewById(R.id.horizontalScrollView2);
        sv_home = (CustomScrollView) getView().findViewById(R.id.sv_home);
        //监听ScrollView下滑高度
        sv_home.setOnScrollChangedListener(new ScrollChangedListener() {
            @Override
            public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
                int height = 2 * para.height;
                if (t < height) {
                    mHideHorizontalScrollView.setVisibility(View.GONE);
                    mHorizontalScrollView.setVisibility(View.VISIBLE);
                } else {
                    mHideHorizontalScrollView.setVisibility(View.VISIBLE);
                    mHorizontalScrollView.setVisibility(View.GONE);
                }
            }
        });
        myRadioGroup = new RadioGroup(getActivity());
        myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(myRadioGroup);
        myHideRadioGroup = new RadioGroup(getActivity());
        myHideRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        myHideRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout2.addView(myHideRadioGroup);

        /**热门活动**/
        iv_hot_subject = (ImageView) getView().findViewById(R.id.iv_hot_subject);
        iv_hot_subject.setOnClickListener(new MyOnClickListener());
        WindowManager hot = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        para = iv_hot_subject.getLayoutParams();
        para.width = hot.getDefaultDisplay().getWidth();
        para.height = hot.getDefaultDisplay().getWidth() * 1 / 3;
        int margin = (int) getActivity().getResources().getDimension(R.dimen.dp);
        iv_hot_subject.setPadding(5 * margin, 10 * margin, 5 * margin, 0);
        iv_hot_subject.setLayoutParams(para);
        gv_hot_label = (GridView) getView().findViewById(R.id.gv_hot_label);
        partyLabelAdapter = new PartyLabelAdapter(HotlistGrid, getActivity());
        gv_hot_label.setAdapter(partyLabelAdapter);
        gv_hot_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", HotlistGrid.get(position).getId());
                startActivity(intent);
            }
        });

        lv_hot_party = (ListView) getView().findViewById(R.id.lv_hot_party);
        partyRecommendAdapter = new PartyRecommendAdapter(getActivity(), HotlistList);
        lv_hot_party.setAdapter(partyRecommendAdapter);
        lv_hot_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", HotlistList.get(position).getId());
                startActivity(intent);
            }
        });


        /***精品活动***/
        iv_rec_subject = (ImageView) getView().findViewById(R.id.iv_rec_subject);
        iv_rec_subject.setOnClickListener(new MyOnClickListener());
        WindowManager rec = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        para = iv_rec_subject.getLayoutParams();
        para.width = rec.getDefaultDisplay().getWidth();
        para.height = rec.getDefaultDisplay().getWidth() * 1 / 3;
        iv_rec_subject.setPadding(5 * margin, 10 * margin, 5 * margin, 0);
        iv_rec_subject.setLayoutParams(para);
        gv_rec_label = (GridView) getView().findViewById(R.id.gv_rec_label);
        recGridListAdapter = new RecGridListAdapter(getActivity(), ReclistGrid);
        gv_rec_label.setAdapter(recGridListAdapter);
        gv_rec_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", ReclistGrid.get(position).getId());
                startActivity(intent);
            }
        });

        lv_rec_party = (ListView) getView().findViewById(R.id.lv_rec_party);
        recListAdapter = new RecListAdapter(getActivity(), ReclistList);
        lv_rec_party.setAdapter(recListAdapter);
        lv_rec_party.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", ReclistList.get(position).getId());
                startActivity(intent);
            }
        });

        /***最新活动****/
        iv_new_subject = (ImageView) getView().findViewById(R.id.iv_new_subject);
        iv_new_subject.setOnClickListener(new MyOnClickListener());
        WindowManager news = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        para = iv_new_subject.getLayoutParams();
        para.width = news.getDefaultDisplay().getWidth();
        para.height = news.getDefaultDisplay().getWidth() * 1 / 3;
        iv_new_subject.setPadding(5 * margin, 10 * margin, 5 * margin, 0);
        iv_new_subject.setLayoutParams(para);
        gv_new_label = (GridView) getView().findViewById(R.id.gv_new_label);
        newGridListAdapter = new NewGridListAdapter(getActivity(), Newlists);
        gv_new_label.setAdapter(newGridListAdapter);
        gv_new_label.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                intent.putExtra("id", Newlists.get(position).getId());
                startActivity(intent);
            }
        });

        getHomeData(LOAD);
    }


    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_hot_subject:
                    startActivity(new Intent(getActivity(), PartyTypeActivity.class).putExtra("title", "热门活动"));
                    break;
                case R.id.iv_rec_subject:
                    startActivity(new Intent(getActivity(), PartyTypeActivity.class).putExtra("title", "精品活动"));
                    break;
                case R.id.iv_new_subject:
                    startActivity(new Intent(getActivity(), PartyTypeActivity.class).putExtra("title", "最新活动"));
                    break;

            }

        }
    }


    /**
     * 换行切换任务
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % market.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };


    /**
     * 移除ViewPager上的切换点
     */
    private void removeView() {
        //获取linearlayout子view的个数
        int count = v_dot.getChildCount();
        try {
            for (int i = 0; i < count; i++) {
                v_dot.removeViewAt(count - dots.size());//此行代码刷新是可能报NullPointerException
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 每五秒钟切换一次轮播图图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 0, 5, TimeUnit.SECONDS);
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        // 当页面不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    public void getHomeData(final String str) {
        if (str.equals(LOAD)) {
            getActivity().showDialog(LOADING_DIALOG);
        }
        httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("p", p + "");
        params.addQueryStringParameter("pageNum", page + "");
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getUserid(getActivity()));
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_INDEX_INDEXAPP + url, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {//用于校验一个字符串是否是合法的JSON格式
                    HomeIndexBean homeIndexBean = ParseUtils.parseHomeIndexBean(result);
                    if (homeIndexBean.getStatus() == 1 && homeIndexBean.getData() != null) {
                        /**适配轮播图数据*/
                        if (homeIndexBean.getData().getMarket() != null && homeIndexBean.getData().getMarket().size() != 0) {
                            market = new ArrayList<HomeIndexBean.DataEntity.MarketEntity>();
                            dots = new ArrayList<View>();
                            for (int i = 0; i < homeIndexBean.getData().getMarket().size(); i++) {
                                market.add(homeIndexBean.getData().getMarket().get(i));
                                ImageView view = new ImageView(getActivity());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                int margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                                layoutParams.setMargins(margin, 0, 0, 0);
                                view.setLayoutParams(layoutParams);
                                view.setBackgroundResource(R.drawable.dot_normal);
                                v_dot.addView(view);
                                dots.add(view);
                            }
                            viewPager.setAdapter(new MyAdapter());
                            // 设置一个监听器，当ViewPager中的页面改变时调用
                            viewPager.setOnPageChangeListener(new MyPageChangeListener());
                            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                            // 每五秒钟切换一次轮播图图片显示
                            scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 0, 5, TimeUnit.SECONDS);
                            getActivity().removeDialog(0);
                        }
                        viewPager.setFocusable(true);
                        viewPager.setFocusableInTouchMode(true);
                        viewPager.requestFocus();

                        /**活动标签数据*/
                        if (homeIndexBean.getData().getType() != null && homeIndexBean.getData().getType().size() != 0) {
                            names = new ArrayList<String>();
                            idList = new ArrayList<String>();
                            names.add("推荐活动");
                            for (int i = 0; i < 9; i++) {
                                names.add(homeIndexBean.getData().getType().get(i).getTitle());
                                idList.add(homeIndexBean.getData().getType().get(i).getId() + "");
                            }

                            for (int i = 0; i < names.size(); i++) {
                                RadioButton radio = new RadioButton(getActivity());
                                radio.setButtonDrawable(android.R.color.transparent);
                                radio.setBackground(getResources().getDrawable(android.R.color.transparent));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                        LayoutParams.MATCH_PARENT, Gravity.CENTER);
                                radio.setLayoutParams(params);
                                radio.setPadding(30, LayoutParams.MATCH_PARENT, 30, LayoutParams.MATCH_PARENT);
                                radio.setGravity(Gravity.CENTER);
                                radio.setId(_id + i);
                                radio.setText(names.get(i));
                                radio.setTextColor(getResources().getColor(R.color.happyi_title_color));
                                radio.setTextSize(14);
                                radio.setTag(names);
                                if (i == 0) {
                                    radio.setChecked(true);
                                    int itemWidth = (int) radio.getPaint().measureText(names.get(i));
                                    mImageView1.setLayoutParams(new LinearLayout.LayoutParams(itemWidth + radio.getPaddingLeft() + radio.getPaddingRight(),
                                            2 * (int) getResources().getDimension(R.dimen.dp)));
                                }
                                myRadioGroup.addView(radio);
                            }
                            myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                                    int radioButtonId = group.getCheckedRadioButtonId();
                                    //根据ID获取RadioButton的实例
                                    RadioButton rb = (RadioButton) getView().findViewById(radioButtonId);
                                    AnimationSet animationSet = new AnimationSet(true);
                                    TranslateAnimation translateAnimation;
                                    translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                                    animationSet.addAnimation(translateAnimation);
                                    animationSet.setFillBefore(true);
                                    animationSet.setFillAfter(true);
                                    animationSet.setDuration(200);
                                    String title = rb.getText().toString().trim();

                                    if (title.equals("推荐活动")) {
                                    } else if (title.equals("亲子活动")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "1").putExtra("title", "亲子活动"));
                                    } else if (title.equals("明星公益")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "2").putExtra("title", "明星公益"));
                                    } else if (title.equals("青年社交")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "3").putExtra("title", "青年社交"));
                                    } else if (title.equals("乡村体验")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "4").putExtra("title", "乡村体验"));
                                    } else if (title.equals("自然环保")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "5").putExtra("title", "自然环保"));
                                    } else if (title.equals("健康运动")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "6").putExtra("title", "健康运动"));
                                    } else if (title.equals("慈善捐助")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "7").putExtra("title", "慈善捐助"));
                                    } else if (title.equals("爱心助学")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "8").putExtra("title", "爱心助学"));
                                    } else if (title.equals("文化艺术")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "9").putExtra("title", "文化艺术"));
                                    }
                                    //开始上面蓝色横条图片的动画切换
                                    mImageView1.startAnimation(animationSet);
                                    //更新当前蓝色横条距离左边的距离
                                    mCurrentCheckedRadioLeft = rb.getLeft();

                                    mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);
                                    mHideHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);

                                    mImageView1.setLayoutParams(new LinearLayout.LayoutParams(rb.getRight() - rb.getLeft(),
                                            2 * (int) getResources().getDimension(R.dimen.dp)));
                                }
                            });
                            for (int i = 0; i < names.size(); i++) {
                                RadioButton radio = new RadioButton(getActivity());
                                radio.setButtonDrawable(android.R.color.transparent);
                                radio.setBackground(getResources().getDrawable(android.R.color.transparent));
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                        LayoutParams.MATCH_PARENT, Gravity.CENTER);
                                radio.setLayoutParams(params);
                                radio.setPadding(30, LayoutParams.MATCH_PARENT, 30, LayoutParams.MATCH_PARENT);
                                radio.setGravity(Gravity.CENTER);
                                radio.setId(_id + i);
                                radio.setText(names.get(i));
                                radio.setTextColor(getResources().getColor(R.color.happyi_title_color));
                                radio.setTextSize(14);
                                radio.setTag(names);
                                if (i == 0) {
                                    radio.setChecked(true);
                                    int itemWidth = (int) radio.getPaint().measureText(names.get(i));
                                    mImageView2.setLayoutParams(new LinearLayout.LayoutParams(itemWidth + radio.getPaddingLeft() + radio.getPaddingRight(),
                                            2 * (int) getResources().getDimension(R.dimen.dp)));
                                }
                                myHideRadioGroup.addView(radio);
                            }
                            myHideRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                                    int radioButtonId = group.getCheckedRadioButtonId();
                                    //根据ID获取RadioButton的实例
                                    RadioButton rb = (RadioButton) getView().findViewById(radioButtonId);
                                    AnimationSet animationSet = new AnimationSet(true);
                                    TranslateAnimation translateAnimation;
                                    translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                                    animationSet.addAnimation(translateAnimation);
                                    animationSet.setFillBefore(true);
                                    animationSet.setFillAfter(true);
                                    animationSet.setDuration(200);
                                    String title = rb.getText().toString().trim();

                                    if (title.equals("推荐活动")) {
                                    } else if (title.equals("亲子活动")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "1").putExtra("title", "亲子活动"));
                                    } else if (title.equals("明星公益")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "2").putExtra("title", "明星公益"));
                                    } else if (title.equals("青年社交")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "3").putExtra("title", "青年社交"));
                                    } else if (title.equals("乡村体验")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "4").putExtra("title", "乡村体验"));
                                    } else if (title.equals("自然环保")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "5").putExtra("title", "自然环保"));
                                    } else if (title.equals("健康运动")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "6").putExtra("title", "健康运动"));
                                    } else if (title.equals("慈善捐助")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "7").putExtra("title", "慈善捐助"));
                                    } else if (title.equals("爱心助学")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "8").putExtra("title", "爱心助学"));
                                    } else if (title.equals("文化艺术")) {
                                        startActivity(new Intent(getActivity(), PartyListActivity.class).putExtra("typeid", "9").putExtra("title", "文化艺术"));
                                    }
                                    //开始上面蓝色横条图片的动画切换
                                    mImageView2.startAnimation(animationSet);
                                    //更新当前蓝色横条距离左边的距离
                                    mCurrentCheckedRadioLeft = rb.getLeft();

                                    mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);
                                    mHideHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);

                                    mImageView2.setLayoutParams(new LinearLayout.LayoutParams(rb.getRight() - rb.getLeft(),
                                            2 * (int) getResources().getDimension(R.dimen.dp)));
                                }
                            });

                        }
                        /***热门活动***/
                        if (homeIndexBean.getData().getHot().getData() != null && homeIndexBean.getData().getHot().getData().size() != 0) {
                            ImgUtils.setRectangleImage(iv_hot_subject, homeIndexBean.getData().getHot().getPic());
                            Hotlist = (ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity>) homeIndexBean.getData().getHot().getData();
                            for (int i = 0; i < Hotlist.size(); i++) {
                                if (i <= 3) {
                                    HotlistGrid.add(Hotlist.get(i));
                                } else {
                                    HotlistList.add(Hotlist.get(i));
                                }
                            }
                            partyLabelAdapter.notifyDataSetChanged();
                            partyRecommendAdapter.notifyDataSetChanged();
                        }
                        /***精品活动****/
                        if (homeIndexBean.getData().getRec().getData() != null && homeIndexBean.getData().getRec().getData().size() != 0) {
                            ImgUtils.setRectangleImage(iv_rec_subject, homeIndexBean.getData().getRec().getPic());
                            Reclist = (ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity>) homeIndexBean.getData().getRec().getData();
                            for (int i = 0; i < Reclist.size(); i++) {
                                if (i <= 3) {
                                    ReclistGrid.add(Reclist.get(i));
                                } else {
                                    ReclistList.add(Reclist.get(i));
                                }
                            }
                            recGridListAdapter.notifyDataSetChanged();
                            recListAdapter.notifyDataSetChanged();
                        }

                        /*****最新活动******/
                        if (homeIndexBean.getData().getNewX().getData() != null && homeIndexBean.getData().getNewX().getData().size() != 0) {
                            ImgUtils.setRectangleImage(iv_new_subject, homeIndexBean.getData().getNewX().getPic());
                            Newlist = (ArrayList<HomeIndexBean.DataEntity.NewEntity.NewDataEntity>) homeIndexBean.getData().getNewX().getData();
                            Newlists.addAll(Newlist);
                            newGridListAdapter.notifyDataSetChanged();
                        }

                        //显示页面
                        mPullToRefreshView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getActivity(), homeIndexBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
//                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFooterRefresh(final PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((maxPage - p) != 0) {
                    p += 1;
//                    getHomeData(PULL);
                }
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                lists.clear();
                p = 1;
//                getHomeData(DROPDOWN);
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);

    }


    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author xieqinghua
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            bannerTitle.setText(market.get(position).getTitle());
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    /**
     * 填充ViewPager页面的适配器
     *
     * @author xieqinghua
     */
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return market.size();
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //适配器加载网络图片
            ImgUtils.setRectangleImage(imageView, market.get(position).getPic());
            ((ViewPager) container).addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    if (market.get(position).getCateid() == 1) {
                        intent = new Intent(getActivity(), PartyNativeDetailActivity.class);
                        intent.putExtra("id", market.get(position).getColumnid());
                        startActivity(intent);
                    } else if (market.get(position).getCateid() == 2) {
                        intent = new Intent(getActivity(), NgoNativeDetailActivity.class);
                        intent.putExtra("id", market.get(position).getColumnid());
                        startActivity(intent);
                    } else if (market.get(position).getCateid() == 3) {
                        intent = new Intent(getActivity(), FundNativeDetailActivity.class);
                        intent.putExtra("id", market.get(position).getColumnid());
                        startActivity(intent);
                    } else if (market.get(position).getCateid() == 4) {
                        //TODO 跳转H5页面
                        intent = new Intent(getActivity(), PartyWebDetailsActivity.class);
                        intent.putExtra("url", market.get(position).getUrl());
                        startActivity(intent);
                    }
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }


}
