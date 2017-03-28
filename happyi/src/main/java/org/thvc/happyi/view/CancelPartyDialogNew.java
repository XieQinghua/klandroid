package org.thvc.happyi.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.RefundOptionActivity;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.ApplyRefundBean;
import org.thvc.happyi.bean.RefundInfoBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;

/**
 * Created by huangxinqi on 2016/3/25.
 */
public class CancelPartyDialogNew {
    private AlertDialog dialog;
    private ImageView iv_reason_time;
    private ImageView iv_reason_body;
    private ImageView iv_reason_other;
    private RelativeLayout rl_reason_time;
    private RelativeLayout rl_reason_body;
    private RelativeLayout rl_reason_ohter;
    private Button btn_Confirm;
    private String isPay;
    private String joinId;
    private Context context;
    private String reason;
    private EditText et_reason;
    private static final int OTHER = 100;
    private static final int TIME = 200;
    private static final int BODY = 300;
    private int reasonType = TIME;
    private double money;

    public CancelPartyDialogNew(final CancelPartyListener myListener, final Context context, String title, final String dataId, final String time, final String isPay, final String joinId, final String payFee) {
        this.context = context;
        this.joinId = joinId;
        this.isPay = isPay;
        this.money = Double.parseDouble(payFee);
        dialog = new android.app.AlertDialog.Builder(context).create();
        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_cancel_party_new);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initializeView(window);
        rl_reason_ohter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                iv_reason_other.setImageResource(R.drawable.btn_selected);
                reasonType = OTHER;
                et_reason.setEnabled(true);
            }
        });
        rl_reason_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                iv_reason_time.setImageResource(R.drawable.btn_selected);
                reasonType = TIME;
            }
        });
        rl_reason_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                iv_reason_body.setImageResource(R.drawable.btn_selected);
                reasonType = BODY;
            }
        });
        btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (reasonType) {
                    case TIME:
                        reason = "时间问题";
                        break;
                    case BODY:
                        reason = "身体不适";
                        break;
                    case OTHER:
                        reason = et_reason.getText().toString().trim();
                        break;
                }
                RefundInfoBean refundInfoBean = new RefundInfoBean();
                HttpUtils httpUtils = new HttpUtils();
                if (isPay.equals("2") || money == 0.0) {
                    MyRequestParams params = new MyRequestParams();
                    params.addQueryStringParameter("userid", HappyiApplication.getInstance().getSolevar(context));
                    params.addQueryStringParameter("dataid", dataId);
                    params.addQueryStringParameter("reason", reason);
                    params.addQueryStringParameter("joinid", joinId);
                    String url = params.myRequestParams(params);
                    httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.APPLY_REFUND + url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            if (new JsonValidator().validate(responseInfo.result)) {
                                ApplyRefundBean applyRefundBean = new Gson().fromJson(responseInfo.result, ApplyRefundBean.class);
                                if (applyRefundBean.getStatus() == 1) {
                                    Toast.makeText(context, "取消活动成功" + applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                                    myListener.onCancel();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "取消活动失败" + applyRefundBean.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            e.printStackTrace();
                        }
                    });
                } else if (isPay.equals("1") && money > 0) {
                    refundInfoBean.setDataid(dataId).setJoinid(joinId).setUserid(HappyiApplication.getInstance().getSolevar(context)).setReason(reason);
                    context.startActivity(new Intent(context, RefundOptionActivity.class).putExtra("info", refundInfoBean));
                    dialog.dismiss();
                }
            }
        });
    }

    private void initializeView(Window window) {
        rl_reason_body = (RelativeLayout) window.findViewById(R.id.rl_reason_body);
        rl_reason_ohter = (RelativeLayout) window.findViewById(R.id.rl_reason_other);
        rl_reason_time = (RelativeLayout) window.findViewById(R.id.rl_reason_time);
        et_reason = (EditText) window.findViewById(R.id.et_reason);
        et_reason.setEnabled(false);
        iv_reason_body = (ImageView) window.findViewById(R.id.iv_reason_body);
        iv_reason_time = (ImageView) window.findViewById(R.id.iv_reason_time);
        iv_reason_other = (ImageView) window.findViewById(R.id.iv_reason_other);
        btn_Confirm = (Button) window.findViewById(R.id.btn_confirm);
    }

    private void reset() {
        et_reason.setEnabled(false);
        iv_reason_body.setImageResource(R.drawable.btn_unselected);
        iv_reason_other.setImageResource(R.drawable.btn_unselected);
        iv_reason_time.setImageResource(R.drawable.btn_unselected);
    }

    public interface CancelPartyListener {
        void onCancel();
    }
}
