package org.thvc.happyi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import org.thvc.happyi.R;
import org.thvc.happyi.adapter.RouteStepAdapter;
import org.thvc.happyi.bean.PlaceBean;
import org.thvc.happyi.utils.map.DrivingRouteOverlay;
import org.thvc.happyi.utils.map.OverlayManager;
import org.thvc.happyi.utils.map.TransitRouteOverlay;
import org.thvc.happyi.utils.map.WalkingRouteOverlay;

/**
 * 查询结果显示
 */
public class RouteResultActivity extends AppCompatActivity implements OnGetRoutePlanResultListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private PlaceBean placeBean;
    private String type;
    private int index;
    private RouteLine route = null;
    private OverlayManager routeOverlay = null;
    private TextView popupText = null;//泡泡view
    private RouteStepAdapter adapter;
    RoutePlanSearch mSearch = null;
    private ListView lvStep;
    private ImageView iv_back;
    private String duration, distance, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_route_result);
        placeBean = (PlaceBean) getIntent().getSerializableExtra("place");
        type = getIntent().getStringExtra("type");
        index = getIntent().getIntExtra("index", 0);
        distance = getIntent().getStringExtra("distance");
        duration = getIntent().getStringExtra("duration");
        title = getIntent().getStringExtra("title");
        initializeView();
        initializeMap();
        routePlan();
    }

    private void initializeView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvStep = (ListView) findViewById(R.id.lv_step);
        lvStep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (route != null && route.getAllStep().size() > 0) {
                    LatLng nodeLocation = null;
                    String nodeTitle = null;
                    Object step = route.getAllStep().get(position);
                    if (step instanceof DrivingRouteLine.DrivingStep) {
                        nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
                        nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
                    } else if (step instanceof WalkingRouteLine.WalkingStep) {
                        nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
                        nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
                    } else if (step instanceof TransitRouteLine.TransitStep) {
                        nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
                        nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
                    }

                    if (nodeLocation == null || nodeTitle == null) {
                        return;
                    }
                    //移动节点至中心
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
                    // show popup
                    popupText = new TextView(RouteResultActivity.this);
                    popupText.setBackgroundResource(R.drawable.popup);
                    popupText.setTextColor(getResources().getColor(R.color.happyi_title_color));
                    int margin = (int) getResources().getDimension(R.dimen.dp);
                    popupText.setPadding(5 * margin, 5 * margin, 5 * margin, 10 * margin);
                    popupText.setGravity(Gravity.CENTER);
                    popupText.setTextSize(12f);
                    popupText.setEms(10);
                    popupText.setMaxLines(1);//最大行数1
                    popupText.setEllipsize(TextUtils.TruncateAt.END);//超出范围显示省略号
                    popupText.setText(nodeTitle);
                    mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
                }
            }
        });
    }

    private void initializeMap() {
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置比例尺
        mBaiduMap.setMapStatus(msu);

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    /**
     * 路径规划
     */
    private void routePlan() {
        //reset 地图
        route = null;
        mBaiduMap.clear();
        PlanNode stNode = PlanNode.withLocation(new LatLng(placeBean.getMyLat(), placeBean.getMyLon()));
        PlanNode enNode = PlanNode.withLocation(new LatLng(placeBean.getDesLat(), placeBean.getDesLon()));
        if (type.equals(DestinationActivity.DRIVE)) {
            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        } else if (type.equals(DestinationActivity.TRANSIT)) {
            mSearch.transitSearch((new TransitRoutePlanOption())
                    .from(stNode)
                    .city(placeBean.getCityName())
                    .to(enNode));
        } else if (type.equals(DestinationActivity.WALK)) {
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        }
    }


    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(index);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(index));
            overlay.addToMap();
            overlay.zoomToSpan();
            adapter = new RouteStepAdapter(getApplicationContext(), route);
            lvStep.setAdapter(adapter);
        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(index);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(index));
            overlay.addToMap();
            overlay.zoomToSpan();
            adapter = new RouteStepAdapter(getApplicationContext(), route);
            lvStep.setAdapter(adapter);
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteResultActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            route = result.getRouteLines().get(index);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(index));
            overlay.addToMap();
            overlay.zoomToSpan();

            adapter = new RouteStepAdapter(getApplicationContext(), route);
            lvStep.setAdapter(adapter);
        }
    }

    //定制RouteOverly,描绘轨迹
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {

            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }
}
