package org.thvc.happyi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.ExpressionAdapter;
import org.thvc.happyi.adapter.ExpressionPagerAdapter;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.utils.SmileUtils;
import org.thvc.happyi.view.ExpandGridView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/8.
 * 评论弹窗，Activity实现
 * 添加了表情富文本功能
 */
public class CommentActivity extends BaseActivity {

    private LinearLayout ll_face_container, js_reple_layout;
    private TextView btn_expression;
    private TextView btn_keyboard;
    private EditText et_sendmessage;
    private ViewPager vPager;
    private List<String> reslist;
    private InputMethodManager imm;
    private Button btn_send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        //1.得到InputMethodManager对象
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        init();
    }

    public void init() {
        js_reple_layout = (LinearLayout) this.findViewById(R.id.js_reple_layout);
        btn_expression = (TextView) this.findViewById(R.id.btn_expression);
        btn_keyboard = (TextView) this.findViewById(R.id.btn_keyboard);
        et_sendmessage = (EditText) this.findViewById(R.id.et_sendmessage);
        vPager = (ViewPager) this.findViewById(R.id.vPager);
        btn_send = (Button) this.findViewById(R.id.btn_send);
        ll_face_container = (LinearLayout) this.findViewById(R.id.ll_face_container);
        btn_expression.setOnClickListener(new MyOnClickListener());
        btn_keyboard.setOnClickListener(new MyOnClickListener());
        et_sendmessage.setOnClickListener(new MyOnClickListener());
        btn_send.setOnClickListener(new MyOnClickListener());

        js_reple_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        // 表情list
        reslist = getExpressionRes(105);
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        View gv3 = getGridChildView(3);
        View gv4 = getGridChildView(4);
        View gv5 = getGridChildView(5);
        View gv6 = getGridChildView(6);
        views.add(gv1);
        views.add(gv2);
        views.add(gv3);
        views.add(gv4);
        views.add(gv5);
        views.add(gv6);
        vPager.setAdapter(new ExpressionPagerAdapter(views));
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_expression:
                    btn_expression.setVisibility(View.GONE);
                    btn_keyboard.setVisibility(View.VISIBLE);
                    ll_face_container.setVisibility(View.VISIBLE);
                    //2.调用hideSoftInputFromWindow方法隐藏软键盘
                    imm.hideSoftInputFromWindow(et_sendmessage.getWindowToken(), 0); //强制隐藏键盘
                    break;
                case R.id.btn_keyboard:
                    btn_keyboard.setVisibility(View.GONE);
                    btn_expression.setVisibility(View.VISIBLE);
                    ll_face_container.setVisibility(View.GONE);
                    //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
                    imm.showSoftInput(et_sendmessage, InputMethodManager.SHOW_FORCED);
                    break;
                case R.id.et_sendmessage:
                    et_sendmessage.setSelection(et_sendmessage.getText().length());//光标显示在末尾
                    btn_keyboard.setVisibility(View.GONE);
                    btn_expression.setVisibility(View.VISIBLE);
                    ll_face_container.setVisibility(View.GONE);
                    //1.得到InputMethodManager对象
                    //2.调用showSoftInput方法显示软键盘，其中view为聚焦的view组件
                    imm.showSoftInput(et_sendmessage, InputMethodManager.SHOW_FORCED);
                    break;
                case R.id.btn_send:
                    String sendmessage = et_sendmessage.getText().toString().trim();
                    if (sendmessage.equals("")) {
                        Toast.makeText(CommentActivity.this, "您没有输入数据！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("sendmessage", sendmessage);
                    setResult(1, intent);
                    finish();
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                    break;
            }
        }
    }

    /**
     * 获取表情的gridview的子view
     *
     * @param i
     * @return
     */
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.grid_view);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, 40));
        } else if (i == 3) {
            list.addAll(reslist.subList(40, 60));
        } else if (i == 4) {
            list.addAll(reslist.subList(60, 80));
        } else if (i == 5) {
            list.addAll(reslist.subList(80, 100));
        } else if (i == 6) {
            list.addAll(reslist.subList(100, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this, 1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (filename != "delete_expression") { // 不是删除键，显示表情
                        // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                        //特别注意这里是通过反射来获取表情，所以类名不能出错
                        Class clz = Class.forName("org.thvc.happyi.utils.SmileUtils");
                        Field field = clz.getField(filename);
                        et_sendmessage.append(SmileUtils.getSmiledText(CommentActivity.this, (String) field.get(null)));
                    } else { // 删除文字或者表情
                        if (!TextUtils.isEmpty(et_sendmessage.getText())) {
                            int selectionStart = et_sendmessage.getSelectionStart();// 获取光标的位置
                            if (selectionStart > 0) {
                                String body = et_sendmessage.getText().toString();
                                String tempStr = body.substring(0, selectionStart);
                                int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                if (i != -1) {
                                    CharSequence cs = tempStr.substring(i, selectionStart);
                                    if (SmileUtils.containsKey(cs.toString()))
                                        et_sendmessage.getEditableText().delete(i, selectionStart);
                                    else
                                        et_sendmessage.getEditableText().delete(selectionStart - 1,
                                                selectionStart);
                                } else {
                                    et_sendmessage.getEditableText().delete(selectionStart - 1, selectionStart);
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                }

            }
        });
        return view;
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "e_" + x;

            reslist.add(filename);

        }
        return reslist;

    }
}
