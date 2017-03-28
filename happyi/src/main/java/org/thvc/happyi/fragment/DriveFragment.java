package org.thvc.happyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.mapapi.search.route.DrivingRouteResult;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.DestinationActivity;
import org.thvc.happyi.activity.RouteChooseActivity;
import org.thvc.happyi.activity.RouteResultActivity;
import org.thvc.happyi.adapter.DriveRouteAdapter;
import org.thvc.happyi.bean.PlaceBean;

/**
 * Created by huangxinqi on 2016/1/21.
 */
public class DriveFragment extends Fragment {
    private DrivingRouteResult drivingRouteResult;
    private ListView listView;
    private DriveRouteAdapter adapter;
    private PlaceBean placeBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, null);
        drivingRouteResult = ((RouteChooseActivity) getActivity()).getDrivingRouteResult();
        adapter = new DriveRouteAdapter(getActivity(), drivingRouteResult);
        listView = (ListView) view.findViewById(R.id.lv_route);
        placeBean = (PlaceBean) getArguments().getSerializable("place");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RouteResultActivity.class);
                intent.putExtra("place", placeBean);
                intent.putExtra("index", position);
                intent.putExtra("type", DestinationActivity.DRIVE);
                startActivity(intent);
            }
        });
        return view;
    }
}
