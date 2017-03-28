package org.thvc.happyi.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import org.thvc.happyi.adapter.JoinPeopleAdapter;
import org.thvc.happyi.adapter.PartyDetailCommentAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.AddCommentBean;
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.CommentPermissionsBean;
import org.thvc.happyi.bean.GetInFoBean;
import org.thvc.happyi.bean.PartyClaimBean;
import org.thvc.happyi.bean.PartyDetailappBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.utils.onekeyshare.OneKeyShareBuilder;
import org.thvc.happyi.view.CircleImageView;
import org.thvc.happyi.view.ElasticityScrollView;
import org.thvc.happyi.view.SetMoneyDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.thvc.happyi.R.color.happyi_secondary_content;
import static org.thvc.happyi.R.color.orangered;

/**
 * 项目名称：klandroid
 * 类描述：原生活动详情
 * 创建人：谢庆华.
 * 创建时间：2016/1/4 8:58
 * 修改人：Administrator
 * 修改时间：2016/1/4 8:58
 * 修改备注：
 */
public class PartyNativeDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = "PartyNative";

    private String id;
    private String userid;
    private String usertype;
    private HttpUtils httpUtils;
    private String solevar;

    private PartyDetailappBean partyDetailappBean;

    private ElasticityScrollView sv_content;
    private RelativeLayout rl_layout_title;
    private RelativeLayout rl_layout_bottom;
    private int title_height;

    private ImageView iv_share, iv_collect;//分享，关注


    private View CustomView;
    private AlertDialog dialog;

    private RelativeLayout rl_ngo, rl_claim_fund, rl_fund;
    private CircleImageView iv_ngo_img, iv_fund_img, iv_claim_fund_img;
    private TextView tv_party_name, tv_ngo_name, tv_foundation_name, iv_claim_fund_name,
            tv_party_label1, tv_party_label2, tv_party_label3, tv_address,
            tv_money1, tv_content, tv_deadline, tv_limit_number, tv_consult_phone;

    private ViewPager vp_party_scene;
    private TextView tv_enrollend_time;//截止时间
    private ArrayList<String> picList;
    private int currentItem = 0; // 当前图片的索引号
    private LinearLayout ll_point;//切换点布局
    private List<View> points; // 图片标题正文的那些点

    private TextView tv_more_joinpeople;
    private GridView gv_joinpeople;//活动参与人员
    private JoinPeopleAdapter joinPeopleAdapter;//报名人员Adapter
    private TextView tv_empty_comment;
    private ListView lv_comment;
    private TextView tv_comment;
    private PartyDetailCommentAdapter commentAdapter;
    private Button bt_details;
    private String latitude;
    private String longitude;
    private TextView tv_actual_money, tv_original_money, tv_per_money;
    private Button btn_join;
    private static final String PERSON_JOIN = "person";
    private static final String FAMILY_JOIN = "family";
    private static final String TEAM_JOIN = "team";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_native_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//解决华为手机虚拟键遮挡底部报名按钮问题
//        FontHelper.applyFont(PartyNativeDetailActivity.this, findViewById(R.id.layout_party_detail), "fonts/SourceHanSansK-Light.ttf");
        init();
    }

    private void init() {
        id = getIntent().getStringExtra("id");//活动id
        userid = HappyiApplication.getInstance().getUserid(PartyNativeDetailActivity.this);
        solevar = HappyiApplication.getInstance().getSolevar(PartyNativeDetailActivity.this);
        usertype = HappyiApplication.getInstance().getSystem(this);

        rl_layout_title = (RelativeLayout) findViewById(R.id.rl_layout_title);
        rl_layout_bottom = (RelativeLayout) findViewById(R.id.rl_layout_bottom);
        title_height = (int) getResources().getDimension(R.dimen.height_top_bar);
        sv_content = (ElasticityScrollView) findViewById(R.id.sv_content);

        vp_party_scene = (ViewPager) findViewById(R.id.vp_party_scene);
        tv_enrollend_time = (TextView) findViewById(R.id.tv_enrollend_time);

        rl_ngo = (RelativeLayout) findViewById(R.id.rl_ngo);
        rl_claim_fund = (RelativeLayout) findViewById(R.id.rl_claim_fund);
        rl_fund = (RelativeLayout) findViewById(R.id.rl_fund);
        iv_ngo_img = (CircleImageView) findViewById(R.id.iv_ngo_img);
        iv_fund_img = (CircleImageView) findViewById(R.id.iv_fund_img);
        iv_claim_fund_img = (CircleImageView) findViewById(R.id.iv_claim_fund_img);

        //设置主题图，NGO头像长高参数
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final ViewGroup.LayoutParams para = vp_party_scene.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;//活动主题图片长高设置为屏幕宽度1/1
        vp_party_scene.setLayoutParams(para);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);

        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_collect = (ImageView) findViewById(R.id.iv_collect);

        tv_party_name = (TextView) findViewById(R.id.tv_party_name);
        tv_ngo_name = (TextView) findViewById(R.id.tv_ngo_name);

        tv_party_label1 = (TextView) findViewById(R.id.tv_party_label1);
        tv_party_label2 = (TextView) findViewById(R.id.tv_party_label2);
        tv_party_label3 = (TextView) findViewById(R.id.tv_party_label3);
        tv_content = (TextView) findViewById(R.id.tv_content);
        bt_details = (Button) findViewById(R.id.bt_details);
        bt_details.setOnClickListener(this);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address.setOnClickListener(this);

        tv_foundation_name = (TextView) findViewById(R.id.tv_foundation_name);
        iv_claim_fund_name = (TextView) findViewById(R.id.iv_claim_fund_name);
        tv_money1 = (TextView) findViewById(R.id.tv_money1);

        tv_deadline = (TextView) findViewById(R.id.tv_deadline);
        tv_limit_number = (TextView) findViewById(R.id.tv_limit_number);
        tv_consult_phone = (TextView) findViewById(R.id.tv_consult_phone);

        tv_more_joinpeople = (TextView) findViewById(R.id.tv_more_joinpeople);
        tv_more_joinpeople.setOnClickListener(this);
        gv_joinpeople = (GridView) findViewById(R.id.gv_joinpeople);

        tv_empty_comment = (TextView) findViewById(R.id.tv_empty_comment);
        lv_comment = (ListView) findViewById(R.id.lv_comment);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        tv_comment.setOnClickListener(this);

        tv_actual_money = (TextView) findViewById(R.id.tv_actual_money);
        tv_original_money = (TextView) findViewById(R.id.tv_original_money);
        tv_original_money.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        tv_per_money = (TextView) findViewById(R.id.tv_per_money);

        btn_join = (Button) findViewById(R.id.btn_join);//我要报名按钮
        btn_join.setOnClickListener(this);
        //获取活动详情数据
        getPartyDate();
    }

    /**
     * 获取活动数据
     */
    private void getPartyDate() {
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("id", id);
        if (!userid.equals("")) {
            params.addQueryStringParameter("userid", userid);
        }
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_DETAIL + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    partyDetailappBean = ParseUtils.parsePartyDetailappBean(result);
                    if (partyDetailappBean.getStatus() == 1) {
                        initDate(partyDetailappBean);
                        removeDialog();
                    } else {
                        Toast.makeText(PartyNativeDetailActivity.this, partyDetailappBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
     * 适配活动数据
     *
     * @param partyDetailappBean
     */
    private void initDate(final PartyDetailappBean partyDetailappBean) {
        //设置主题图片图集
        if (partyDetailappBean.getData().getSubject() != null && partyDetailappBean.getData().getSubject().size() != 0) {
            picList = (ArrayList<String>) partyDetailappBean.getData().getSubject();
            points = new ArrayList<View>();
            for (int i = 0; i < picList.size(); i++) {
                ImageView view = new ImageView(PartyNativeDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            int margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                layoutParams.setMargins(5, 5, 5, 5);
                view.setLayoutParams(layoutParams);
                if (i == 0) {
                    view.setBackgroundResource(R.drawable.party_point_select);
                } else {
                    view.setBackgroundResource(R.drawable.party_point_normal);
                }

                ll_point.addView(view);
                points.add(view);
            }
            vp_party_scene.setAdapter(new MyPagerAdapter());
            vp_party_scene.setOnPageChangeListener(new MyPageChangeListener());
        }

        if (partyDetailappBean.getData().getIsdel().equals("8")) {
            tv_enrollend_time.setVisibility(View.VISIBLE);
            tv_enrollend_time.setText(R.string.party_end);
            //报名按键变“活动已结束”按键
            btn_join.setText(R.string.party_already_end);
            btn_join.setBackgroundColor(getResources().getColor(happyi_secondary_content));
            btn_join.setClickable(false);
            btn_join.setVisibility(View.VISIBLE);
        } else if (partyDetailappBean.getData().getIsdel().equals("7")) {
            tv_enrollend_time.setVisibility(View.VISIBLE);
            tv_enrollend_time.setText(R.string.party_process);
            btn_join.setText(R.string.party_in);
            btn_join.setBackgroundColor(getResources().getColor(happyi_secondary_content));
            btn_join.setClickable(false);
            btn_join.setVisibility(View.VISIBLE);
        } else if (partyDetailappBean.getData().getIsdel().equals("6")) {
            //报名按键变“我要报名”按键
            btn_join.setClickable(true);
            btn_join.setVisibility(View.VISIBLE);

            //计算截止时间
            long countTime = Long.valueOf(partyDetailappBean.getData().getActbegin()) * 1000L - System.currentTimeMillis();
            long days = countTime / (1000 * 60 * 60 * 24);
            long hours = (countTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            tv_enrollend_time.setVisibility(View.VISIBLE);
            if (countTime > 0) {
                if (days == 0 && hours < 1) {
                    tv_enrollend_time.setText("活动即将开始");
                }
                {
                    tv_enrollend_time.setText("活动开始倒计时：" + days + "天" + hours + "小时");
                }
            } else {
                tv_enrollend_time.setText(R.string.party_process);
            }
        } else if (partyDetailappBean.getData().getIsdel().equals("5")) {
            tv_enrollend_time.setVisibility(View.GONE);
            //认领确认中改成去让利
            btn_join.setText("去让利");
            btn_join.setBackgroundColor(getResources().getColor(orangered));
            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetMoneyDialog setMoneyDialog = new SetMoneyDialog(PartyNativeDetailActivity.this, Integer.parseInt(partyDetailappBean.getData().getId()));
                }
            });
            btn_join.setVisibility(View.VISIBLE);
        } else if (partyDetailappBean.getData().getIsdel().equals("4")) {
            //认领确认中改成去让利
            btn_join.setText("去支持");
            btn_join.setBackgroundColor(getResources().getColor(orangered));
            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog(partyDetailappBean.getData().getId());
                }
            });
            btn_join.setVisibility(View.VISIBLE);

        }

        ImgUtils.setHeadImage(iv_ngo_img, partyDetailappBean.getData().getUser().getHeadpic());
        if (partyDetailappBean.getData().getFund() != null) {
            ImgUtils.setHeadImage(iv_fund_img, partyDetailappBean.getData().getFund().getHeadpic());
        } else {
            rl_fund.setVisibility(View.GONE);
        }
        if (partyDetailappBean.getData().getCheck() != null) {
            ImgUtils.setHeadImage(iv_claim_fund_img, partyDetailappBean.getData().getCheck().getHeadpic());
        } else {
            rl_claim_fund.setVisibility(View.GONE);
        }

        rl_ngo.setOnClickListener(this);
        rl_claim_fund.setOnClickListener(this);
        rl_fund.setOnClickListener(this);

        iv_share.setOnClickListener(this);
        if (partyDetailappBean.getData().getHascollect() != null && partyDetailappBean.getData().getHascollect().equals("1")) {
            iv_collect.setImageDrawable(getResources().getDrawable(R.drawable.icon_collect_pressed));
        }
        iv_collect.setOnClickListener(this);

        tv_party_name.setText(partyDetailappBean.getData().getTitle());
        tv_ngo_name.setText(partyDetailappBean.getData().getUser().getNickname());
        //填充活动标签数据
        if (partyDetailappBean.getData().getStatuscn() != null && partyDetailappBean.getData().getStatuscn().size() != 0) {
            for (int i = 0; i < partyDetailappBean.getData().getStatuscn().size(); i++) {
                if (partyDetailappBean.getData().getStatuscn().size() == 3) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.VISIBLE);
                    tv_party_label3.setVisibility(View.VISIBLE);

                    if (i == 0) {
                        tv_party_label1.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        tv_party_label2.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 2) {
                        tv_party_label3.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    }
                } else if (partyDetailappBean.getData().getStatuscn().size() == 2) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.VISIBLE);
                    tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        tv_party_label1.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        tv_party_label2.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    }
                } else if (partyDetailappBean.getData().getStatuscn().size() == 1) {
                    tv_party_label1.setVisibility(View.VISIBLE);
                    tv_party_label2.setVisibility(View.GONE);
                    tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        tv_party_label1.setText(partyDetailappBean.getData().getStatuscn().get(i).getTitle());
                    }
                }
            }
        }

        tv_content.setText(partyDetailappBean.getData().getDescription());
        tv_address.setText(partyDetailappBean.getData().getAddr());

        if (partyDetailappBean.getData().getFund() != null) {
            tv_foundation_name.setText(partyDetailappBean.getData().getFund().getNickname());
        } else {
            tv_foundation_name.setVisibility(View.GONE);
        }
        if (partyDetailappBean.getData().getCheck() != null) {
            iv_claim_fund_name.setText(partyDetailappBean.getData().getCheck().getNickname());
        } else {
            iv_claim_fund_name.setVisibility(View.GONE);
        }

        tv_deadline.setText("活动开始时间：" + getStringTime(partyDetailappBean.getData().getActbegin()));

        tv_limit_number.setText("活动报名限额：" + partyDetailappBean.getData().getTotalpep() + "人");
        tv_consult_phone.setText("咨询电话：" + partyDetailappBean.getData().getActtel() + "(" + partyDetailappBean.getData().getActcontact() + ")");
        tv_consult_phone.setOnClickListener(this);

        //适配报名人员数据
        if (partyDetailappBean.getData().getJoinpeople() != null && partyDetailappBean.getData().getJoinpeople().size() != 0) {
            ArrayList<PartyDetailappBean.DataEntity.JoinpeopleEntity> list
                    = (ArrayList<PartyDetailappBean.DataEntity.JoinpeopleEntity>) partyDetailappBean.getData().getJoinpeople();
            joinPeopleAdapter = new JoinPeopleAdapter(PartyNativeDetailActivity.this, list, partyDetailappBean.getData().getJoinpep());
            gv_joinpeople.setAdapter(joinPeopleAdapter);
        } else {
            joinPeopleAdapter = new JoinPeopleAdapter(PartyNativeDetailActivity.this, null, null);
            gv_joinpeople.setAdapter(joinPeopleAdapter);
        }
        //适配活动评论数据
        if (partyDetailappBean.getData().getComment() != null && partyDetailappBean.getData().getComment().size() != 0) {
            ArrayList<PartyDetailappBean.DataEntity.CommentEntity> list
                    = (ArrayList<PartyDetailappBean.DataEntity.CommentEntity>) partyDetailappBean.getData().getComment();
            commentAdapter = new PartyDetailCommentAdapter(PartyNativeDetailActivity.this, list);
            lv_comment.setAdapter(commentAdapter);
        } else {
            tv_empty_comment.setVisibility(View.VISIBLE);
        }

        tv_original_money.setText("原费用: " + partyDetailappBean.getData().getPrefee() + "元");//原费用

        if (partyDetailappBean.getData().getIsget().equals("2")) {
            tv_per_money.setVisibility(View.VISIBLE);
            tv_per_money.setText(partyDetailappBean.getData().getFund().getNickname() + "为你省" + partyDetailappBean.getData().getGetpre() + "元");
        } else if (partyDetailappBean.getData().getIsget().equals("3")) {
            tv_per_money.setVisibility(View.GONE);
        } else {
            tv_per_money.setVisibility(View.GONE);
        }
        double renjunMoney = Double.parseDouble(partyDetailappBean.getData().getPrefee()) - Double.parseDouble(partyDetailappBean.getData().getGetpre());
        tv_actual_money.setText("￥" + (int) renjunMoney);//人均费用，计算得出，取整数值

        //显示页面
        rl_layout_title.setVisibility(View.VISIBLE);
        rl_layout_bottom.setVisibility(View.VISIBLE);
        sv_content.setVisibility(View.VISIBLE);
    }


    private String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//精确到天
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        String city = partyDetailappBean.getData().getCity();
        latitude = partyDetailappBean.getData().getLat();
        longitude = partyDetailappBean.getData().getLng();
        String destinationName = partyDetailappBean.getData().getAddr();
        switch (v.getId()) {
            /**分享*/
            case R.id.iv_share:
                if (!userid.equals("")) {
                    share();
                } else {
                    startActivity(new Intent(PartyNativeDetailActivity.this, LoginActivity.class));
                }
                break;
            /**关注活动*/
            case R.id.iv_collect:
                if (!userid.equals("")) {
                    if (partyDetailappBean.getData().getHascollect() != null && partyDetailappBean.getData().getHascollect().equals("1")) {
                        collectParty("1");//取消关注
                    } else {
                        collectParty("2");//添加关注
                    }


                } else {
                    startActivity(new Intent(PartyNativeDetailActivity.this, LoginActivity.class));
                }
                break;
            /**查看地图*/
            case R.id.tv_address:
                intent = new Intent(PartyNativeDetailActivity.this, DestinationActivity.class);

                if (city != null && destinationName != null) {
                    intent.putExtra("city", city);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("destinationName", destinationName);
                    startActivity(intent);
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, "暂无详细地址坐标", Toast.LENGTH_SHORT).show();
                }
                break;
            /**查看活动图文详情*/
            case R.id.bt_details:
                intent = new Intent(PartyNativeDetailActivity.this, PartyContentDetailActivity.class);
//                if (city != null && latitude != 0 && longitude != 0 & destinationName != null) {
                intent.putExtra("city", city);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("destinationName", destinationName);
                intent.putExtra("id", id);
                intent.putExtra("phone", partyDetailappBean.getData().getActtel());
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open, 0);
//                } else {
//                    Toast.makeText(PartyNativeDetailActivity.this, "暂无详细地址坐标", Toast.LENGTH_SHORT).show();
//                }


                break;
            /**跳转Ngo详情页面*/
            case R.id.rl_ngo:
                startActivity(new Intent(PartyNativeDetailActivity.this, NgoNativeDetailActivity.class)
                        .putExtra("id", partyDetailappBean.getData().getUser().getSolevar()));
                break;
            /**跳转到认证NGO页面*/
            case R.id.rl_claim_fund:
                startActivity(new Intent(PartyNativeDetailActivity.this, NgoNativeDetailActivity.class)
                        .putExtra("id", partyDetailappBean.getData().getCheck().getSolevar()));
                break;
            /**跳转到基金会页面*/
            case R.id.rl_fund:
                startActivity(new Intent(PartyNativeDetailActivity.this, FundNativeDetailActivity.class)
                        .putExtra("id", partyDetailappBean.getData().getFund().getSolevar()));
                break;
            /**查看更多好友*/
            case R.id.tv_more_joinpeople:
                startActivity(new Intent(PartyNativeDetailActivity.this, MoreJoinPeopleActivity.class).putExtra("id", id));
                break;
            /**评论*/
            case R.id.tv_comment:
                if (!userid.equals("")) {
                    /**评价*/
                    comment();
                } else {
                    startActivity(new Intent(PartyNativeDetailActivity.this, LoginActivity.class));
                }
                break;
            /**报名*/
            case R.id.btn_join:
                if (!userid.equals("")) {
                    if (usertype.equals("3") || usertype.equals("4")) { //判断用户类型是否能报名
                        Toast.makeText(PartyNativeDetailActivity.this, "非普通用户不可以报名参加活动", Toast.LENGTH_SHORT).show();
                    } else {
                        /**弹出报名方式选择窗口*/
                        new PopupWindows(PartyNativeDetailActivity.this, v);
                    }
                } else {
                    startActivity(new Intent(PartyNativeDetailActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_consult_phone:
                //拨打电话
                intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("tel:" + partyDetailappBean.getData().getActtel()));
                startActivity(intent);
                break;
        }
    }

    public class PopupWindows extends PopupWindow {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public PopupWindows(Context mContext, View parent) {
            View view = View.inflate(mContext, R.layout.item_popup_join, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            //修改高度显示，解决被手机底部虚拟键挡住的问题
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //实例化一个ColorDrawable颜色为半透明
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.ll_popup).getTop();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            update();
            Button bt1 = (Button) view.findViewById(R.id.bt_join_person);//个人报名
            Button bt2 = (Button) view.findViewById(R.id.bt_join_family);//家庭报名
            Button bt3 = (Button) view.findViewById(R.id.bt_join_group);//团体报名
            Button bt4 = (Button) view.findViewById(R.id.item_popupwindows_cancel);//取消按钮
            if ((partyDetailappBean.getData().getType().contains("1"))) {
                bt1.setBackground(getResources().getDrawable(R.drawable.btn_oval_sharp_selector));
                bt1.setVisibility(View.VISIBLE);
            }
            if ((partyDetailappBean.getData().getType().contains("2"))) {
                bt2.setVisibility(View.VISIBLE);
            }
            if ((partyDetailappBean.getData().getType().contains("3"))) {
                bt3.setVisibility(View.VISIBLE);
            }
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //个人报名
                    joinParty(PERSON_JOIN);
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //家庭报名
                    //joinParty(FAMILY_JOIN);
                    Intent intent = new Intent(PartyNativeDetailActivity.this, FamilyEnrollActivity.class);
                    intent.putExtra("dataId", id);
                    startActivity(intent);
                    dismiss();

                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //团体报名
                    joinParty(TEAM_JOIN);
                    dismiss();

                }
            });
            bt4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    /**
     * 分享
     */
    private void share() {
        OneKeyShareBuilder oneKeyShareBuilder = new OneKeyShareBuilder();
        String text = partyDetailappBean.getData().getDescription().replaceAll("<br/>", "\n");
        if (text.length() > 120) {
            text = text.substring(0, 120) + "···";
        }
        oneKeyShareBuilder.setText(text)
                .setTitleUrl("http://www.happiyi.com/Party/detail.html?id=" + id)
                .setTitle(partyDetailappBean.getData().getTitle())
                .setContext(PartyNativeDetailActivity.this)
                .setImageUrl(HappyiApi.QINIU + partyDetailappBean.getData().getSubject().get(0))
                .setUrl("http://www.happiyi.com/Party/detail.html?id=" + id)
                .showShareDialog();
    }

    /**
     * 关注活动
     */
    private void collectParty(final String status) {
        httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("status", status);//操作[1取消关注|2添加关注]
        params.addQueryStringParameter("dataid", id);
        params.addQueryStringParameter("type", 1 + "");
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COLLECT_ADD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CollectBean collectBean = ParseUtils.parseCollectBean(result);
                    if (collectBean.getStatus() == 1) {
//                        Toast.makeText(PartyNativeDetailActivity.this, collectBean.getData(), Toast.LENGTH_SHORT).show();
                        if (status.equals("1")) {
                            iv_collect.setImageDrawable(getResources().getDrawable(R.drawable.icon_collect_normal));
                            partyDetailappBean.getData().setHascollect("2");
                        } else if (status.equals("2")) {
                            iv_collect.setImageDrawable(getResources().getDrawable(R.drawable.icon_collect_pressed));
                            partyDetailappBean.getData().setHascollect("1");
                        }
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
     * 报名
     */
    private void joinParty(String type) {
        Intent intent = new Intent(PartyNativeDetailActivity.this, PartyJoinActivity.class);//跳转到报名页面
        intent.putExtra("actid", id);
        intent.putExtra("type", type);//报名类型
        intent.putExtra("prefee", partyDetailappBean.getData().getPrefee());
        intent.putExtra("title", partyDetailappBean.getData().getTitle());
        intent.putExtra("getpre", partyDetailappBean.getData().getGetpre());
        intent.putExtra("needpic", partyDetailappBean.getData().getBpfield().contains("7"));
        if (partyDetailappBean.getData().getFund() != null) {
            intent.putExtra("fundname", partyDetailappBean.getData().getFund().getNickname());
        } else {
            intent.putExtra("fundname", "");
        }
        intent.putExtra("safe", partyDetailappBean.getData().getSafe());
        startActivity(intent);
    }


    /**
     * 评论
     */
    private void comment() {
        if (!partyDetailappBean.getData().getIsdel().equals("8")) {
            Toast.makeText(PartyNativeDetailActivity.this, "活动未结束，暂不能评价", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("userid", userid);//!!此处传参数用户编号(solevar)
        params.addQueryStringParameter("dataid", id);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COMMENT_CHECK + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    CommentPermissionsBean commentPermissionsBean = ParseUtils.parseCommentPermissionsBean(result);
                    if (commentPermissionsBean.getStatus() == 1) {
                        //做评论操作
                        forResult();
                    } else {
                        //提示没有评价权限
                        Toast.makeText(PartyNativeDetailActivity.this, commentPermissionsBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.i(s);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String sendmessage = data.getStringExtra("sendmessage");
            addComment(sendmessage);
        }
    }

    public void forResult() {
        Intent intent = new Intent(PartyNativeDetailActivity.this, CommentActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 添加评论
     *
     * @param str
     */
    public void addComment(String str) {
        httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", solevar);
        params.addQueryStringParameter("dataid", id);
        params.addQueryStringParameter("content", str);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.COMMENT_ADD + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    AddCommentBean addCommentBean = ParseUtils.parseAddCommentBean(result);
                    if (addCommentBean.getStatus() == 1) {
                        Toast.makeText(PartyNativeDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PartyNativeDetailActivity.this, addCommentBean.getData(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    /**
     * 自定义pageradapter  适配viewpager
     */
    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picList.size();
        }

        /**
         * 根据指定的下标 创建viewpager中展示的item  返回当前page中的view对象
         * 第一个参数表示 当前管理page的视图组
         * 第二个参数表示 指定下标
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            //适配器加载网络图片
            ImgUtils.setRectangleImage(imageView, picList.get(position));
//            ImageView imageView2 = new ImageView(container.getContext());
//            imageView2.setBackground(getDrawable(R.drawable.img_default_cover));//加模糊效果
            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        /**
         * 根据指定的下标移除视图组中的view对象
         * 第一个参数表示 视图组对象
         * 第二个参数表示 当前移除的视图的下标
         * 第三个参数表示 instantiateItem 返回的object对象
         */
        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        /**
         * 表示判断viewpager中展示的view对象与instantiateItem对象是否时同一个对象
         */
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
            points.get(oldPosition).setBackgroundResource(R.drawable.party_point_normal);
            points.get(position).setBackgroundResource(R.drawable.party_point_select);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }


    /**
     * 认领活动
     */
    public void ClaimParty(String dataid) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        params.addQueryStringParameter("dataid", dataid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.GET_PARTY + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    PartyClaimBean partyClaimBean = ParseUtils.parsePartyClaimBean(result);
                    if (partyClaimBean.getStatus() == 1) {
                        Toast.makeText(PartyNativeDetailActivity.this, "支持成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                LogUtils.i(s);
            }


        });
    }


    public void getinfo(final TextView tv_ordinary, final TextView tv_new) {
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();//定义访问服务器参数
        params.addQueryStringParameter("userid", userid);
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.FUND_GETINFO + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    GetInFoBean getInFoBean = ParseUtils.parseGetInFoBean(result);
                    if (getInFoBean.getStatus() == 1) {
                        tv_ordinary.setText("支持比例:" + getInFoBean.getData().getOcount() + "%" + "最低金额:" + getInFoBean.getData().getOmin() + "元" + "最高金额:" + getInFoBean.getData().getOmax() + "元");
                        tv_new.setText("支持比例:" + getInFoBean.getData().getNcount() + "%" + "最低金额:" + getInFoBean.getData().getNmin() + "元" + "最高金额:" + getInFoBean.getData().getNmax() + "元");
                    }
                } else {
                    Toast.makeText(PartyNativeDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

            }
        });
    }

    public void Dialog(final String dataid) {
        AlertDialog.Builder builder = myBuilder(PartyNativeDetailActivity.this);
        dialog = builder.show();
        //点击屏幕外侧，dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_ordinary = (TextView) CustomView.findViewById(R.id.tv_ordinary);
        TextView tv_new = (TextView) CustomView.findViewById(R.id.tv_new);
        Button positiveButton = (Button) CustomView.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) CustomView.findViewById(R.id.negativeButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaimParty(dataid);
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        getinfo(tv_ordinary, tv_new);
    }

    protected AlertDialog.Builder myBuilder(PartyNativeDetailActivity dialogWindows) {
        final LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogWindows);
        CustomView = inflater.inflate(R.layout.support_dialog, null);
        return builder.setView(CustomView);
    }

}

