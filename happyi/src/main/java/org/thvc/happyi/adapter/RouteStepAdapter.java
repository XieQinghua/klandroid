package org.thvc.happyi.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.DestinationActivity;

/**
 * Created by huangxinqi on 2016/1/23.
 * 路径规划的每一个步骤
 */
public class RouteStepAdapter extends BaseAdapter {
    private Context context;
    private RouteLine routeLine;
    private TextView tv_step;
    private String type;

    public RouteStepAdapter(Context context, RouteLine routeLine) {
        this.context = context;
        this.routeLine = routeLine;
    }

    @Override
    public int getCount() {
        return routeLine.getAllStep().size();
    }

    @Override
    public Object getItem(int position) {
        return routeLine.getAllStep().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_route_step, null);
            tv_step = (TextView) convertView.findViewById(R.id.tv_step);
            convertView.setTag(tv_step);
        } else {
            tv_step = (TextView) convertView.getTag();
        }
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = routeLine.getAllStep().get(position);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            type = DestinationActivity.DRIVE;
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions() + "   行驶" + distanceFormat(((DrivingRouteLine.DrivingStep) step).getDistance());
        } else if (step instanceof WalkingRouteLine.WalkingStep) {
            type = DestinationActivity.WALK;
            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
        } else if (step instanceof TransitRouteLine.TransitStep) {
            type = DestinationActivity.TRANSIT;
            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
        }

        if (nodeLocation != null && nodeTitle != null) {
            tv_step.setText(nodeTitle);
            tv_step.setCompoundDrawablePadding(20);
            Drawable iconStep = null;
            switch (type) {
                case DestinationActivity.WALK:
                    iconStep = context.getResources().getDrawable(R.drawable.icon_step_walk);
                    break;
                case DestinationActivity.TRANSIT:
                    iconStep = context.getResources().getDrawable(R.drawable.icon_step_transit);
                    break;
                case DestinationActivity.DRIVE:
                    iconStep = context.getResources().getDrawable(R.drawable.icon_step_drive);
                    break;
            }
            iconStep.setBounds(0, 0, iconStep.getMinimumWidth(), iconStep.getMinimumHeight());
            tv_step.setCompoundDrawables(iconStep, null, null, null);
        }
        return convertView;
    }

    private String distanceFormat(int meter) {
        String result;
        if (meter < 1000) {
            result = meter + "米";
        } else {
            result = Math.rint(meter / 100) / 10 + "千米";
        }
        return result;
    }
}
