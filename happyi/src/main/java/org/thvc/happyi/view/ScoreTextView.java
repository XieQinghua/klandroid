package org.thvc.happyi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/23.
 */
public class ScoreTextView extends TextView {
    public ScoreTextView(Context context) {
        super(context);
    }

    public ScoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(315, getMeasuredWidth()/3, getMeasuredHeight()/3);
        super.onDraw(canvas);
    }
}
