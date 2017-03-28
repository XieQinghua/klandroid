package org.thvc.happyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseActivity;

import java.util.List;

/**
 * create by huangxinqi
 * on 2016/1/21
 * 显示目的地于百度地图
 */
public class DestinationActivity extends BaseActivity implements View.OnClickListener {
    public static final String WALK = "walk";
    public static final String DRIVE = "drive";
    public static final String TRANSIT = "transit";
    private String type;
    private BaiduMap mBaiduMap;
    private MapView mapView;
    //默认的目的地坐标
    private double destinationLatitude = 28.170333;
    private double destinationLongitude = 112.938789;
    private String destinationName = "";
    private String city = "长沙";
    //我的坐标
    private double myLongitude;
    private double myLatitude;

    //定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private ImageView iv_back;
    private TextView tv_destination_name;
    private LinearLayout ll_transit;
    private LinearLayout ll_drive;
    private LinearLayout ll_walk;
    private TextView tv_transit;
    private TextView tv_drive;
    private TextView tv_walk;
    private ImageView iv_transit;
    private ImageView iv_drive;
    private ImageView iv_walk;
    //如果是第一次定位，在我的坐标添加覆盖物，同时使地图包含我的坐标以及目的地的坐标
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_destination);
        destinationLatitude = getIntent().getDoubleExtra("latitude", 28.170333);
        destinationLongitude = getIntent().getDoubleExtra("longitude", 112.938789);
        destinationName = getIntent().getStringExtra("destinationName");
        city = getIntent().getStringExtra("city");

        initializeView();
        initializeMap();
        configLocation();
        initMyLocation();
        mLocationClient.start();
    }

    private void initializeView() {
        ll_drive = (LinearLayout) findViewById(R.id.ll_drive);
        ll_transit = (LinearLayout) findViewById(R.id.ll_transit);
        ll_walk = (LinearLayout) findViewById(R.id.ll_walk);
        ll_drive.setOnClickListener(this);
        ll_transit.setOnClickListener(this);
        ll_walk.setOnClickListener(this);
        tv_drive = (TextView) findViewById(R.id.tv_drive);
        tv_walk = (TextView) findViewById(R.id.tv_walk);
        tv_transit = (TextView) findViewById(R.id.tv_transit);
        iv_drive = (ImageView) findViewById(R.id.iv_drive);
        iv_walk = (ImageView) findViewById(R.id.iv_walk);
        iv_transit = (ImageView) findViewById(R.id.iv_transit);
        tv_destination_name = (TextView) findViewById(R.id.tv_destination_name);
        tv_destination_name.setText(destinationName);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化地图
     */
    private void initializeMap() {
        mapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    /**
     * 定位初始化
     */
    private void initMyLocation() {
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(100)
                .direction(90.0f)
                .latitude(destinationLatitude)
                .longitude(destinationLongitude).build();
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationEnabled(true);
        LatLng ll = new LatLng(destinationLatitude, destinationLongitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 18);//设置缩放比例
        mBaiduMap.animateMapStatus(u);

        //给目的地构建Marker图标
        BitmapDescriptor destinationBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions destinationBitmapOverlayOptions = new MarkerOptions()
                .position(new LatLng(destinationLatitude, destinationLongitude))
                .icon(destinationBitmapDescriptor);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(destinationBitmapOverlayOptions);
        mBaiduMap.setMyLocationEnabled(false); //当不需要定位图层时关闭定位图层
    }

    /**
     * 定位配置
     */
    private void configLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 选择出行方式
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DestinationActivity.this, RouteChooseActivity.class);
        switch (v.getId()) {
            case R.id.ll_transit:
                type = TRANSIT;
                intent.putExtra("type", TRANSIT);
                break;
            case R.id.ll_drive:
                type = DRIVE;
                intent.putExtra("type", DRIVE);
                break;
            case R.id.ll_walk:
                type = WALK;
                intent.putExtra("type", WALK);
                break;
        }
        selectBtn(type);
        intent.putExtra("destinationName", destinationName);
        intent.putExtra("type", type);
        intent.putExtra("city", city);
        intent.putExtra("latitude", myLatitude);
        intent.putExtra("longitude", myLongitude);
        intent.putExtra("destinationLatitude", destinationLatitude);
        intent.putExtra("destinationLongitude", destinationLongitude);
        startActivity(intent);
    }

    /**
     * 重置按钮
     */
    private void resetBtn() {
        tv_walk.setTextColor(getResources().getColor(R.color.happyi_content_color));
        tv_drive.setTextColor(getResources().getColor(R.color.happyi_content_color));
        tv_transit.setTextColor(getResources().getColor(R.color.happyi_content_color));
        iv_walk.setImageDrawable(getResources().getDrawable(R.drawable.icon_walk));
        iv_transit.setImageDrawable(getResources().getDrawable(R.drawable.icon_bus));
        iv_drive.setImageDrawable(getResources().getDrawable(R.drawable.icon_drive));
    }

    /**
     * 选择按钮
     *
     * @param type 选择的类型
     */
    private void selectBtn(String type) {
        resetBtn();
        switch (type) {
            case DestinationActivity.DRIVE:
                tv_drive.setTextColor(getResources().getColor(R.color.orangered));
                iv_drive.setImageDrawable(getResources().getDrawable(R.drawable.icon_drive_selected));
                break;
            case DestinationActivity.WALK:
                tv_walk.setTextColor(getResources().getColor(R.color.orangered));
                iv_walk.setImageDrawable(getResources().getDrawable(R.drawable.icon_walk_selected));
                break;
            case DestinationActivity.TRANSIT:
                tv_transit.setTextColor(getResources().getColor(R.color.orangered));
                iv_transit.setImageDrawable(getResources().getDrawable(R.drawable.icon_bus_selected));
                break;
        }
    }

    /**
     * 注册位置监听器，在onReceiveLocation方法中执行获得定位后的操作
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            myLatitude = location.getLatitude();
            myLongitude = location.getLongitude();
            if (isFirstLocate) {
                //给我的位置设置标识
                BitmapDescriptor myBitmapDescriptor = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_st);
                OverlayOptions myOverlayOptions = new MarkerOptions()
                        .position(new LatLng(myLatitude, myLongitude))
                        .icon(myBitmapDescriptor);
                mBaiduMap.addOverlay(myOverlayOptions);
                //下面是使地图的大小，包含LatLngBounds.Builder include的坐标
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(myLatitude, myLongitude));
                builder.include(new LatLng(destinationLatitude, destinationLongitude));
                mBaiduMap.setMapStatus(MapStatusUpdateFactory
                        .newLatLngBounds(builder.build()));
                isFirstLocate = false;
            }

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        // activity 销毁时同时销毁地图控件
        mapView.onDestroy();
        mLocationClient.stop();
        super.onDestroy();
    }
}
