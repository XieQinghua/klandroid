package org.thvc.happyi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;

/**
 * 颜松梁
 * Created by Administrator on 2015/11/23.
 * App介绍页面
 */
public class HappyiIntroduceActivity extends BaseSwipeBackActivity {
    private WebView web_info;
    private TextView tv_info_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happyi_info);
        web_info = (WebView) findViewById(R.id.web_info);
        tv_info_error = (TextView) findViewById(R.id.tv_info_error);
        //设置字符集编码
        web_info.getSettings().setDefaultTextEncodingName("UTF-8");
        //开启JavaScript支持
        web_info.getSettings().setJavaScriptEnabled(true);

        String url = "http://www.happiyi.com/wechat.php/Artonce/intro.html?system=android";
        //监听webwiew的下载进度
        web_info.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        web_info.loadUrl(url);

        web_info.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                tv_info_error.setVisibility(View.VISIBLE);
                tv_info_error.setText(getString(R.string.web_error));
                web_info.setVisibility(View.GONE);
            }
        });
    }
}
