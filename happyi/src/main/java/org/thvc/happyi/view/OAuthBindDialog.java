package org.thvc.happyi.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.thvc.happyi.R;

/**
 * Created by huangxinqi
 * on 2015/12/4-19:02.
 */
public class OAuthBindDialog {
    private AlertDialog alertDialog;
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etVerifyCode;
    private Button btnConfirm;
    private TextView tvGetCode;
    private RelativeLayout rlVerifyCode;
    private ImageView ivPassword;
    private ImageView ivClose;
    private ImageView ivPhone;
    private CheckBox checkAgreement;
    private TextView userAgreement;

    public OAuthBindDialog(final Context context) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_oauth_bind);
        etPhoneNumber = (EditText) window.findViewById(R.id.et_phone);
        etPassword = (EditText) window.findViewById(R.id.et_password);
        btnConfirm = (Button) window.findViewById(R.id.btn_confirm);
        etVerifyCode = (EditText) window.findViewById(R.id.et_verify_code);
        tvGetCode = (TextView) window.findViewById(R.id.tv_get_code);
        ivClose = (ImageView) window.findViewById(R.id.im_close);
        rlVerifyCode = (RelativeLayout) window.findViewById(R.id.rl_verify_code);
        ivPhone = (ImageView) window.findViewById(R.id.iv_phone);
        ivPassword = (ImageView) window.findViewById(R.id.iv_password);
        checkAgreement = (CheckBox) window.findViewById(R.id.check_agreement);
        userAgreement = (TextView) window.findViewById(R.id.tv_user_agreement);
    }

    public CheckBox getCheckAgreement() {
        return checkAgreement;
    }

    public void setCheckAgreement(CheckBox checkAgreement) {
        this.checkAgreement = checkAgreement;
    }

    public TextView getUserAgreement() {
        return userAgreement;
    }

    public void setUserAgreement(TextView userAgreement) {
        this.userAgreement = userAgreement;
    }

    public ImageView getIvPassword() {
        return ivPassword;
    }

    public void setIvPassword(ImageView ivPassword) {
        this.ivPassword = ivPassword;
    }

    public ImageView getIvPhone() {
        return ivPhone;
    }

    public void setIvPhone(ImageView ivPhone) {
        this.ivPhone = ivPhone;
    }

    public RelativeLayout getRlVerifyCode() {
        return rlVerifyCode;
    }

    public void setRlVerifyCode(RelativeLayout rlVerifyCode) {
        this.rlVerifyCode = rlVerifyCode;
    }

    public ImageView getIvClose() {
        return ivClose;
    }

    public void setIvClose(ImageView ivClose) {
        this.ivClose = ivClose;
    }


    public TextView getTvGetCode() {
        return tvGetCode;
    }

    public void setTvGetCode(TextView tvGetCode) {
        this.tvGetCode = tvGetCode;
    }

    public EditText getEtVerifyCode() {
        return etVerifyCode;
    }

    public void setEtVerifyCode(EditText etVerifyCode) {
        this.etVerifyCode = etVerifyCode;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public EditText getEtPhoneNumber() {
        return etPhoneNumber;
    }

    public void setEtPhoneNumber(EditText etPhoneNumber) {
        this.etPhoneNumber = etPhoneNumber;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    public void setEtPassword(EditText etPassword) {
        this.etPassword = etPassword;
    }

    public Button getBtnConfirm() {
        return btnConfirm;
    }

    public void setBtnConfirm(Button btnConfirm) {
        this.btnConfirm = btnConfirm;
    }

}
