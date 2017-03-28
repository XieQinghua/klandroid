package org.thvc.happyi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.utils.guide.MaterialShowcaseView;

/**
 * create by huangxinqi
 * 作用：测试新手指引
 */

public class NewHandActivity extends AppCompatActivity {
    private Button button_show;
    private Button buttton_reset;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hand);
        button_show = (Button) findViewById(R.id.show_guide);
        textView = (TextView) findViewById(R.id.guide_target);
        buttton_reset = (Button) findViewById(R.id.guide_reset);
        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"fuck",Toast.LENGTH_SHORT).show();
                new MaterialShowcaseView.Builder(NewHandActivity.this)
                        .setTarget(textView)
                        .setDismissText("我知道了")
                        .setContentText("这是新手指引的内容")
                        .setDelay(1000) // optional but starting animations immediately in onCreate can make them choppy
                        .singleUse("fuck") // provide a unique ID used to ensure it is only shown once
                        .setMaskColour(R.color.gray_line)
                        .show();
            }
        });
        buttton_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialShowcaseView.resetSingleUse(NewHandActivity.this, "fuck");
            }
        });
    }
}
