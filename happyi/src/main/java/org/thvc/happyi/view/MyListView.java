package org.thvc.happyi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 项目名称：klandroid
 * 类描述：屏蔽掉ListView的滚动，解决与ScrollView的冲突
 * 创建人：谢庆华.
 * 创建时间：2015/11/12 16:35
 * 修改人：Administrator
 * 修改时间：2015/11/12 16:35
 * 修改备注：
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //该自定义控件只是重写了ListView的onMeasure方法，使其不会出现滚动条
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
