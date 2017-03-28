package org.thvc.happyi.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import org.thvc.happyi.R;

/**
 * Created by huangxinqi
 * on 2015/12/2-17:33.
 */
public class JoinPeopleDialog {
    private TextView tvName;
    private TextView tvGender;
    private TextView tvPhone;
    private TextView tvJob;
    private ImageButton ibCall;
    private Context context;
    private ImageButton ibDismiss;
    private AlertDialog alertDialog;

    public JoinPeopleDialog(final Context context, String name, String gender, String job, final String telephoneNumber) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.dialog_tel);
        initializeView(window);
        tvPhone.setText("电话:   " + telephoneNumber);
        tvName.setText("姓名:   " + name);
        tvJob.setText("职业:   " + job);
        tvGender.setText("性别:   " + gender);
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("tel:" + telephoneNumber));
                context.startActivity(intent);
            }
        });
        ibDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void dismiss() {
        alertDialog.dismiss();
    }

    private void initializeView(Window window) {
        tvGender = (TextView) window.findViewById(R.id.tv_gender);
        tvJob = (TextView) window.findViewById(R.id.tv_job);
        tvName = (TextView) window.findViewById(R.id.tv_name);
        tvPhone = (TextView) window.findViewById(R.id.tv_phone);
        ibCall = (ImageButton) window.findViewById(R.id.ib_call);
        ibDismiss = (ImageButton) window.findViewById(R.id.ib_dismiss);
    }

}
