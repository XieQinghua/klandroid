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
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.base.BaseSwipeBackActivity;

/**
 * 项目名称：klandroid
 * 类描述：活动详情页面
 * 创建人：谢庆华.
 * 创建时间：2015/11/12 16:57
 * 修改人：Administrator
 * 修改时间：2015/11/12 16:57
 * 修改备注：
 */
public class PartyWebDetailsActivity extends BaseSwipeBackActivity {

    private static final String TAG = "PartyDetails";
    private WebView party_webview;
    private TextView party_error;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_details);
        init();
    }

    public void init() {
        party_error = (TextView) this.findViewById(R.id.party_error);
        party_webview = (WebView) findViewById(R.id.party_webview);
        url = getIntent().getStringExtra("url");
        //设置字符集编码
        party_webview.getSettings().setDefaultTextEncodingName("UTF-8");
        //开启JavaScript支持
        party_webview.getSettings().setJavaScriptEnabled(true);
        //传递一个Java对象，同时给他命名，这个对象可以在js中调用这个对象的方法
        //party_webview.addJavascriptInterface(new Myjavascript(this, handler), "MyJavaScript");
        //String url = "http://www.happiyi.com/Android/party_detail.html?id=" + id + "&userid=" + userid + "&usertype=" + usertype;

        //监听webwiew的下载进度
        party_webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        party_webview.loadUrl(url);

        party_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                party_error.setVisibility(View.VISIBLE);
                party_error.setText(getString(R.string.web_error));
                party_webview.setVisibility(View.GONE);
            }
        });
    }
}

