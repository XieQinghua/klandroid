package org.thvc.happyi.utils.guide.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.thvc.happyi.utils.guide.target.Target;


/**
 * 没有形状，即不出现围住指引目标的框
 */
public class NoShape implements Shape {

    @Override
    public void updateTarget(Target target) {
        // do nothing
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y, int padding) {
        // do nothing
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
