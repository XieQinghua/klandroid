package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.PlaceBean;

/**
 * Created by huangxinqi on 2016/1/22.
 */
public class WalkingRouteAdapter extends RouteAdapter {
    private WalkingRouteResult result;
    private Context context;
    private WalkingRouteLine routeLine;
    private PlaceBean placeBean;
    private ViewHolder vh;

    public WalkingRouteAdapter(Context context, WalkingRouteResult result, PlaceBean placeBean) {
        this.context = context;
        this.placeBean = placeBean;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        routeLine = result.getRouteLines().get(position);
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_route_walk, null);
            vh.tv_distance_walk = (TextView) convertView.findViewById(R.id.tv_distance_walk);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.rl_walk = (RelativeLayout) convertView.findViewById(R.id.rl_walk);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_title.setText(getTitle(routeLine));
        vh.tv_time.setText(timeFormat(routeLine.getDuration()));
        vh.tv_distance_walk.setText(distanceFormat(routeLine.getDistance()));
        //点击事件在fragment中的listview.setOnItemClick中处理
       /* vh.rl_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RouteResultActivity.class);
                intent.putExtra("place",placeBean);
                intent.putExtra("index",position);
                intent.putExtra("type", DestinationActivity.WALK);
                intent.putExtra("title",getTitle(routeLine));
                intent.putExtra("duration",routeLine.getDuration());
                intent.putExtra("distance",distanceFormat(routeLine.getDistance()));
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    class ViewHolder {
        TextView tv_time, tv_distance_walk, tv_title;
        RelativeLayout rl_walk;
    }

    private String getTitle(WalkingRouteLine routeLine) {
        String title = "";
        WalkingRouteLine.WalkingStep step = routeLine.getAllStep().get(0);
        title = step.getEntranceInstructions() + step.getExitInstructions();
        return title;
    }
}
