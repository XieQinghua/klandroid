package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;

import org.thvc.happyi.R;

/**
 * Created by huangxinqi on 2016/1/22.
 */
public class TransitRouteAdapter extends RouteAdapter {
    private Context context;
    private TransitRouteResult result;
    private ViewHolder vh;
    private TransitRouteLine routeLine;

    public TransitRouteAdapter(Context context, TransitRouteResult result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.getRouteLines().size();
    }

    @Override
    public Object getItem(int position) {
        return result.getRouteLines().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        routeLine = result.getRouteLines().get(position);
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_route_transit, null);
            vh.tv_distance_transit = (TextView) convertView.findViewById(R.id.tv_distance_transit);
            vh.tv_transit_name = (TextView) convertView.findViewById(R.id.tv_transit_name);
            vh.tv_transit_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_transit_time.setText(timeFormat(routeLine.getDuration()) + "|");
        vh.tv_transit_name.setText(getTitle(routeLine));
        vh.tv_distance_transit.setText(distanceFormat(routeLine.getDistance()));
        return convertView;
    }

    class ViewHolder {
        TextView tv_transit_name, tv_transit_time, tv_distance_transit;
    }

    private String getTitle(TransitRouteLine routeLine) {
        String title = "";
        int total = 0;
        if (routeLine.getAllStep().size() > 0) {
            for (TransitRouteLine.TransitStep step : routeLine.getAllStep()) {
                if (step.getVehicleInfo() != null) {
                    if (step.getVehicleInfo().getTitle() != null && step.getVehicleInfo().getPassStationNum() != 0) {
                        title += step.getVehicleInfo().getTitle() + "→";
                        total += step.getVehicleInfo().getPassStationNum();
                    }
                }
            }
        }
        return title.substring(0, title.length() - 1) + "  " + total + "站";
    }
}
