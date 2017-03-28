package org.thvc.happyi.utils.onekeyshare;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import org.thvc.happyi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 完成的主要功能：分享控件
 * Created by huangxinqi
 * on 2015/11/15-01-43.
 */
public class ShareDialog {
    private AlertDialog alertDialog;
    private GridView gridView;
    private ImageView cancelButton;
    private SimpleAdapter ImageAdapter;
    private int[] images = {R.drawable.icon_qq, R.drawable.icon_weibo, R.drawable.icon_wechat, R.drawable.icon_momment, R.drawable.icon_qzone};

    /**
     * 设置gridView的布局图片，并设置adapter
     *
     * @param context
     */
    public ShareDialog(Context context) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.dialog_share);
        gridView = (GridView) window.findViewById(R.id.share_gridView);
        cancelButton = (ImageView) window.findViewById(R.id.im_close);
        List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < images.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", images[i]);
            maps.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, maps, R.layout.gridview_item, new String[]{"image"}, new int[]{R.id.img});
        gridView.setAdapter(simpleAdapter);
    }

    /**
     * 关闭对话框操作
     */
    public void dismiss() {
        alertDialog.dismiss();
    }

    /**
     * 监听按gridView上的item时的动作
     *
     * @param listener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);
    }

    /**
     * 监听cancel按钮的动作
     *
     * @param listener
     */
    public void setCancelListener(View.OnClickListener listener) {
        cancelButton.setOnClickListener(listener);
    }
}
