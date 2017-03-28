package org.thvc.happyi.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.MemberAdapter;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.bean.FamilyEnrollBean;
import org.thvc.happyi.bean.PartyDetailappBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.IsIDCard;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;
import org.thvc.happyi.view.MyListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangxinqi on 2016/4/7.
 * 家庭报名
 */
public class FamilyEnrollActivity extends BaseActivity {
    private TextView tv_actual_money, tv_original_money, tv_save_money, tv_next;
    private double actualMoney;//现价
    private String originalMoney;//原价
    private String getPre;//基金会为您省的钱
    private String fundName;//基金会名字
    private String dataId;//活动id
    private LinearLayout ll_type;
    private int typeNum;//套餐个数
    private ScrollView sv_family_enroll;
    private TextView tv_empty;
    private MyListView lv_member;
    private PartyDetailappBean partyDetailappBean;
    private MemberAdapter memberAdapter;
    private RadioGroup radioGroup;
    private static int idBean = 12345;//radioButton的起始id
    private int checkedType;//被选中的套餐
    private int memberNum;//套餐的人数
    private String specId;//套餐编号
    private boolean isSafe = false;//是否要输身份证
    private ArrayList<Member> members;
    private int data;//这个是报名号，支付接口的一个参数,因为接口返回的字段名叫data...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_enroll);
        dataId = getIntent().getStringExtra("dataId");
        members = new ArrayList<Member>();
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_type = (LinearLayout) findViewById(R.id.ll_type);
        sv_family_enroll = (ScrollView) findViewById(R.id.sv_family_enroll);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        lv_member = (MyListView) findViewById(R.id.lv_member);
        ll_type = (LinearLayout) findViewById(R.id.ll_type);
        tv_actual_money = (TextView) findViewById(R.id.tv_actual_money);
        tv_original_money = (TextView) findViewById(R.id.tv_original_money);
        tv_save_money = (TextView) findViewById(R.id.tv_save_money);
        getPartyDate();
    }

    /**
     * 点击下一步执行的方法
     */
    private void next() {
        for (int i = 0; i < lv_member.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) lv_member.getChildAt(i);
            EditText et_phone = (EditText) linearLayout.findViewById(R.id.et_phone);
            EditText et_name = (EditText) linearLayout.findViewById(R.id.et_name);
//            EditText et_job = (EditText) linearLayout.findViewById(R.id.et_job);
            EditText et_idCard = (EditText) linearLayout.findViewById(R.id.et_idCard);
            if (TextUtils.isEmpty(et_name.getText().toString())) {
                Toast.makeText(this, "请输入成员" + (i + 1) + "姓名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isChineseOnly(et_name.getText().toString())) {
                Toast.makeText(this, "请正确填写成员" + (i + 1) + "中文名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_phone.getText().toString())) {
                Toast.makeText(this, "请输入成员" + (i + 1) + "手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
//            if (TextUtils.isEmpty(et_job.getText().toString())) {
//                Toast.makeText(this, "请输入职业", Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (!isValidMobileNo(et_phone.getText().toString())) {
                Toast.makeText(this, "请正确填写成员" + (i + 1) + "手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isSafe) {
                if (TextUtils.isEmpty(et_idCard.getText().toString())) {
                    Toast.makeText(this, "请填写成员" + (i + 1) + "身份证", Toast.LENGTH_SHORT).show();
                    return;
                }
                String idCardInfo = "";
                try {
                    idCardInfo = IsIDCard.IDCardValidate(et_idCard.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!idCardInfo.equals("")) {
                    Toast.makeText(this, "成员" + (i + 1) + idCardInfo, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            RadioGroup radioGroup = (RadioGroup) linearLayout.findViewById(R.id.rg_gender);
            Member member = new Member();
//            member.setJob(et_job.getText().toString());
            member.setName(et_name.getText().toString());
            member.setPhone(et_phone.getText().toString());
            if (isSafe) {
                member.setIdCard(et_idCard.getText().toString());
            }
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_male) {
                member.setGender("1");
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_male) {
                member.setGender("2");
            }
            members.add(member);
        }
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("dataid", dataId);
        for (int i = 0; i < members.size(); i++) {
            params.addQueryStringParameter("people[" + i + "][realname]", members.get(i).getName());
//            params.addQueryStringParameter("people[" + i + "][job]", members.get(i).getJob());
            params.addQueryStringParameter("people[" + i + "][sex]", members.get(i).getGender());
            params.addQueryStringParameter("people[" + i + "][tel]", members.get(i).getPhone());
            if (isSafe) {
                params.addQueryStringParameter("people[" + i + "][idcard]", members.get(i).getIdCard());
            }
        }
        params.addQueryStringParameter("specid", specId);
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(FamilyEnrollActivity.this));
        String url = params.myRequestParams(params);
        String latestUrl = HappyiApi.JOIN_FAMILY + url;
        httpUtils.send(HttpRequest.HttpMethod.POST, latestUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                if (new JsonValidator().validate(responseInfo.result)) {
                    FamilyEnrollBean familyEnrollBean = new Gson().fromJson(responseInfo.result, FamilyEnrollBean.class);
                    if (familyEnrollBean.getStatus() == 1) {
                        data = familyEnrollBean.getData();
                        pay();
                    }
                } else {
                    Toast.makeText(FamilyEnrollActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
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
     * 去支付
     */
    private void pay() {
        if (actualMoney > 0) {
            Intent intent = new Intent(FamilyEnrollActivity.this, PaymentActivity.class);
            intent.putExtra("payment", "family");
            intent.putExtra("joined", data + "");
            intent.putExtra("dataId", dataId);
            intent.putExtra("originalPrice", originalMoney);
            intent.putExtra("getPre", getPre);
            startActivity(intent);
        } else {
            Toast.makeText(this, "参加该活动的费用为0元，无需支付，报名成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取活动详情
     */
    private void getPartyDate() {
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("id", dataId);
        params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(FamilyEnrollActivity.this));
        String url = params.myRequestParams(params);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APP_DETAIL + url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    partyDetailappBean = ParseUtils.parsePartyDetailappBean(result);
                    if (partyDetailappBean.getStatus() == 1) {
                        if (partyDetailappBean.getData().getSpec() != null) {
                            typeNum = partyDetailappBean.getData().getSpec().size();
                            originalMoney = partyDetailappBean.getData().getSpec().get(0).getPrice();

                            getPre = partyDetailappBean.getData().getGetpre();
                            fundName = partyDetailappBean.getData().getFund().getNickname();
                            if (partyDetailappBean.getData().getSafe().equals("1")) {
                                isSafe = true;
                            }
                            actualMoney = Double.parseDouble(originalMoney) - Double.parseDouble(getPre);
                            tv_original_money.setText("原费用" + originalMoney + "元");
                            tv_original_money.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
                            tv_actual_money.setText("￥" + (int) actualMoney);//取整数值
                            tv_save_money.setText(fundName + "为你节省了" + getPre + "元");
                            memberNum = Integer.parseInt(partyDetailappBean.getData().getSpec().get(0).getNum());
                            memberAdapter = new MemberAdapter(FamilyEnrollActivity.this, memberNum, isSafe);
                            lv_member.setAdapter(memberAdapter);

                            for (int i = 0; i < typeNum; i++) {
                                RadioButton radioButton = new RadioButton(FamilyEnrollActivity.this);
                                if (i == 0) {
                                    radioButton.setChecked(true);
                                }
                                radioButton.setPadding(15, 0, 15, 0);
//                                radioButton.setText("套餐" + (i + 1));
                                radioButton.setText(partyDetailappBean.getData().getSpec().get(i).getTitle());//套餐标题
                                radioButton.setId(i + idBean);
                                radioButton.setButtonDrawable(R.drawable.type_selector);
                                radioGroup.addView(radioButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            }
                            ll_type.addView(radioGroup);
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    radioGroup.check(checkedId);
                                    checkedType = checkedId - idBean;
                                    specId = partyDetailappBean.getData().getSpec().get(checkedType).getId();
                                    changePrice(checkedType);
                                }
                            });
                            tv_next.setFocusable(true);
                            tv_next.setFocusableInTouchMode(true);
                            tv_next.requestFocus();
                            removeDialog();
                        } else {
                            sv_family_enroll.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            removeDialog();
                        }
                    } else {
                        Toast.makeText(FamilyEnrollActivity.this, partyDetailappBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FamilyEnrollActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 更新价格，需要传入套餐的索引
     *
     * @param index
     */
    private void changePrice(int index) {
        memberNum = Integer.parseInt(partyDetailappBean.getData().getSpec().get(index).getNum());
        originalMoney = partyDetailappBean.getData().getSpec().get(index).getPrice();
        getPre = partyDetailappBean.getData().getGetpre();
        fundName = partyDetailappBean.getData().getFund().getNickname();
        actualMoney = Double.parseDouble(originalMoney) - Double.parseDouble(getPre);
        tv_original_money.setText("原价" + originalMoney + "元");
        tv_original_money.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        tv_actual_money.setText("￥" + (int) actualMoney);//取整数值
        tv_save_money.setText(fundName + "为您省了" + getPre + "元");
//        memberAdapter = new MemberAdapter(FamilyEnrollActivity.this, memberNum, isSafe);
//        lv_member.setAdapter(memberAdapter);
    }

    /**
     * 内部类 报名成员Bean
     */
    class Member {
        String name;
        String phone;
        String gender;
        String job;
        String idCard;

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }
    }

    /**
     * 验证手机号码
     *
     * @param mobileNo
     * @return
     */
    public static boolean isValidMobileNo(String mobileNo) {
        boolean flag = false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0678])|(18[0-9]))\\d{8}$");
        Matcher match = p.matcher(mobileNo);
        if (mobileNo != null) {
            flag = match.matches();
        }
        return flag;
    }

    /**
     * 是否只包含中文
     *
     * @param name
     * @return
     */
    private static boolean isChineseOnly(String name) {
        if (name.replaceAll("[\u4e00-\u9fa5]*", "").length() == 0) {
            //不包含特殊字符
            return true;
        }
        return false;
    }
}
