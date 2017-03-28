package org.thvc.happyi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import org.thvc.happyi.R;
import org.thvc.happyi.base.BaseSwipeBackActivity;
import org.thvc.happyi.bean.PlaceBean;
import org.thvc.happyi.fragment.DriveFragment;
import org.thvc.happyi.fragment.TransitFragment;
import org.thvc.happyi.fragment.WalkFragment;

/**
 * 路径选择的activity
 */
public class RouteChooseActivity extends BaseSwipeBackActivity implements View.OnClickListener, OnGetRoutePlanResultListener {
    private String type;
    private String city;
    private DriveFragment driveFragment;
    private WalkFragment walkFragment;
    private TransitFragment transitFragment;
    private PlanNode myPlanNode;
    private PlanNode destinationPlanNode;
    private LatLng myPosition;
    private LatLng destination;
    private WalkingRouteResult walkingRouteResult;
    private DrivingRouteResult drivingRouteResult;
    private TransitRouteResult transitRouteResult;
    private TextView tv_destination_name;

    public WalkingRouteResult getWalkingRouteResult() {
        return walkingRouteResult;
    }

    public DrivingRouteResult getDrivingRouteResult() {
        return drivingRouteResult;
    }

    public TransitRouteResult getTransitRouteResult() {
        return transitRouteResult;
    }

    private RoutePlanSearch routePlanSearch;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String destinationName;
    private LinearLayout ll_walk, ll_drive, ll_transit;
    private ImageView iv_walk, iv_drive, iv_transit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_route_choose);
        initialize();
        searchRoute(type);
        selectBtn(type);
    }

    /**
     * 初始化
     */
    private void initialize() {
        city = getIntent().getStringExtra("city");
        type = getIntent().getStringExtra("type");
        destinationName = getIntent().getStringExtra("destinationName");
        //设置默认坐标
        myPosition = new LatLng(getIntent().getDoubleExtra("latitude", 28.219703), getIntent().getDoubleExtra("longitude", 112.898404));
        destination = new LatLng(getIntent().getDoubleExtra("destinationLatitude", 28.219703), getIntent().getDoubleExtra("destinationLongitude", 112.898404));
        myPlanNode = PlanNode.withLocation(myPosition);
        destinationPlanNode = PlanNode.withLocation(destination);
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        ll_drive = (LinearLayout) findViewById(R.id.ll_drive);
        ll_transit = (LinearLayout) findViewById(R.id.ll_transit);
        ll_walk = (LinearLayout) findViewById(R.id.ll_walk);
        iv_drive = (ImageView) findViewById(R.id.iv_drive);
        iv_walk = (ImageView) findViewById(R.id.iv_walk);
        iv_transit = (ImageView) findViewById(R.id.iv_transit);
        ll_drive.setOnClickListener(this);
        ll_walk.setOnClickListener(this);
        ll_transit.setOnClickListener(this);
        tv_destination_name = (TextView) findViewById(R.id.tv_destination_name);
        tv_destination_name.setText(destinationName);
    }

    /**
     * 选择路径
     *
     * @param type
     */
    private void searchRoute(String type) {
        switch (type) {
            case DestinationActivity.WALK:
                routePlanSearch.walkingSearch((new WalkingRoutePlanOption())
                        .from(myPlanNode)
                        .to(destinationPlanNode));
                break;
            case DestinationActivity.DRIVE:
                routePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(myPlanNode)
                        .to(destinationPlanNode));
                break;
            case DestinationActivity.TRANSIT:
                routePlanSearch.transitSearch((new TransitRoutePlanOption())
                        .from(myPlanNode)
                        .city(city)
                        .to(destinationPlanNode));
        }
    }

    /**
     * 将按钮变成灰色
     */
    private void resetBtn() {
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
                iv_drive.setImageDrawable(getResources().getDrawable(R.drawable.icon_drive_selected));
                break;
            case DestinationActivity.WALK:
                iv_walk.setImageDrawable(getResources().getDrawable(R.drawable.icon_walk_selected));
                break;
            case DestinationActivity.TRANSIT:
                iv_transit.setImageDrawable(getResources().getDrawable(R.drawable.icon_bus_selected));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_drive:
                selectBtn(DestinationActivity.DRIVE);
                searchRoute(DestinationActivity.DRIVE);
                break;
            case R.id.ll_walk:
                selectBtn(DestinationActivity.WALK);
                searchRoute(DestinationActivity.WALK);
                break;
            case R.id.ll_transit:
                selectBtn(DestinationActivity.TRANSIT);
                searchRoute(DestinationActivity.TRANSIT);
                break;
        }
    }

    private void chooseTab(String type) {
        Bundle bundle = new Bundle();
        PlaceBean placeBean = new PlaceBean()
                .setCityName("长沙")
                .setMyLat(myPosition.latitude)
                .setMyLon(myPosition.longitude)
                .setDesLat(destination.latitude)
                .setDesLon(destination.longitude);
        bundle.putSerializable("place", placeBean);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (type) {
            case DestinationActivity.WALK:
                if (walkFragment == null) {
                    walkFragment = new WalkFragment();
                    walkFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_route_plan, walkFragment);
                } else {
                    fragmentTransaction.show(walkFragment);
                }
                break;
            case DestinationActivity.DRIVE:
                if (driveFragment == null) {
                    driveFragment = new DriveFragment();
                    driveFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_route_plan, driveFragment);
                } else {
                    fragmentTransaction.show(driveFragment);
                }
                break;
            case DestinationActivity.TRANSIT:
                if (transitFragment == null) {
                    transitFragment = new TransitFragment();
                    transitFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_route_plan, transitFragment);
                } else {
                    fragmentTransaction.show(transitFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (walkFragment != null) {
            fragmentTransaction.hide(walkFragment);
        }
        if (driveFragment != null) {
            fragmentTransaction.hide(driveFragment);
        }
        if (transitFragment != null) {
            fragmentTransaction.hide(transitFragment);
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteChooseActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            walkingRouteResult = result;
            chooseTab(DestinationActivity.WALK);
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteChooseActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            transitRouteResult = result;
            chooseTab(DestinationActivity.TRANSIT);
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(RouteChooseActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            drivingRouteResult = result;
            chooseTab(DestinationActivity.DRIVE);
        }
    }
}
