package org.thvc.happyi.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseActivity;
import org.thvc.happyi.bean.PartyContentDetailBean;
import org.thvc.happyi.http.HappyiApi;
import org.thvc.happyi.utils.JsonValidator;
import org.thvc.happyi.utils.MyRequestParams;
import org.thvc.happyi.utils.ParseUtils;

/**
 * 项目名称：klandroid
 * 类描述：活动详图文详情
 * 创建人：谢庆华.
 * 创建时间：2016/1/4 8:58
 * 修改人：Administrator
 * 修改时间：2016/1/4 8:58
 * 修改备注：
 */
public class PartyContentDetailActivity extends BaseActivity {
    private WebView web_detalis;
    private TextView tv_content;
    private String id;
    private MapView mapView;
    private BaiduMap baiduMap;
    private TextView tv_destinationName;
    private String destinationName;
    private String latitude;
    private String longitude;
    private String city;
    private LatLng location;
    private Marker marker;
    private String phone;
    private boolean hasLocation = false;//是否有经纬度
    private LinearLayout ll_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_party_text_details);
        web_detalis = (WebView) findViewById(R.id.web_detalis);
        tv_content = (TextView) findViewById(R.id.tv_content);
        mapView = (MapView) findViewById(R.id.map);
        //mapView=new MapView(this);
        tv_destinationName = (TextView) findViewById(R.id.tv_destination_name);
        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        id = getIntent().getStringExtra("id");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        city = getIntent().getStringExtra("city");
        phone = getIntent().getStringExtra("phone");
        destinationName = getIntent().getStringExtra("destinationName");
        tv_destinationName.setText("地址：" + destinationName);
        if (longitude == null || latitude == null || longitude.equals("") || latitude.equals("")) {
            mapView.setVisibility(View.GONE);
            tv_destinationName.setText("暂无地址详情");
        } else {
            hasLocation = true;
            location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        }
        showDialog(LOADING_DIALOG);//开启进度Dialog
        HttpUtils httpUtils = new HttpUtils();
        MyRequestParams params = new MyRequestParams();
        params.addQueryStringParameter("id", id);
        String url = params.myRequestParams(params);
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("id", id);
        httpUtils.send(HttpRequest.HttpMethod.POST, HappyiApi.CONTENT_DETAIL + url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (new JsonValidator().validate(result)) {
                    PartyContentDetailBean parsePartyContentDetailBean = ParseUtils.parsePartyContentDetailBean(result);
                    if (parsePartyContentDetailBean.getStatus() == 1) {
                        String customHtml = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'>" +
                                "<head runat='server'>" +
                                "<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>" +
                                "<title>图文详情</title>" +
                                "<script src='http://www.happiyi.com/Public/Js/jquery-1.8.3.min.js' type='text/javascript'></script>" +
                                "<!-- 全局Css样式开始 -->" +
                                "<link rel='stylesheet' type='text/Css' href='http://www.happiyi.com/Template/Home/Thvc/Assets/Css/reset.css'>" +
                                "<link rel='stylesheet' type='text/Css' href='http://www.happiyi.com/Template/Home/Thvc/Assets/Css/common.css'>" +
                                "<link rel='stylesheet' type='text/Css' href='http://www.happiyi.com/Template/Home/Thvc/Assets/Css/theme.css'>" +
                                "<!-- 全局Css样式结束 -->" +
                                "<meta http-equiv='Cache-Control' content='must-revalidate,no-cache'>" +
                                "<meta id='viewport'   name='viewport' content='width=device-width, maximum-scale=2,minimum-scale=1,initial-scale=1.000000'>" +
                                "<meta name='MobileOptimized' content='240'>" +
                                "<style>body{color: #3c3c3c; font-family:SourceHanSansCN-Light; font-size: 14px;}" +
                                "img{width:95%; display:block; margin:0 auto;  }</style>" +
                                "</head><body>" +
                                "<script type='text/javascript'>$(document).ready(function() {  $('#map').click(function (){shows();});}); function shows(){   window.location='/map'; }</script>" +
                                "<div  class='content' style='width:95%;display:block;margin:10px auto; '  >  <div  class='.article_left' >" + parsePartyContentDetailBean.getData().getContent() + "</div > </div></body></html>";

                        web_detalis.loadDataWithBaseURL(null, customHtml, "text/html", "gb2312", null);
                        if (hasLocation) {
                            initMap();
                        }
                        removeDialog();

                    } else {
                        Toast.makeText(PartyContentDetailActivity.this, parsePartyContentDetailBean.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PartyContentDetailActivity.this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initMap() {
        //LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,800);
        //layoutParams.setMargins(10,10,10,100);
        //mapView.setLayoutParams(layoutParams);
        //ll_map.addView(mapView);
        baiduMap = mapView.getMap();
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(location, 15));
        MarkerOptions markerOptions = new MarkerOptions().position(location).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.icon_en))
                .zIndex(9);
        marker = (Marker) (baiduMap.addOverlay(markerOptions));
    }
    /*@Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mapView.onPause();
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mapView.onResume();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mapView.onDestroy();
    }
}

