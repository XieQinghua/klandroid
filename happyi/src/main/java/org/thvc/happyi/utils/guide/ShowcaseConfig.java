package org.thvc.happyi.utils.guide;

import android.graphics.Color;

import org.thvc.happyi.utils.guide.shape.CircleShape;
import org.thvc.happyi.utils.guide.shape.Shape;

/**
 * create by huagnxinqi
 * 这里是新手指引的设置和默认设置
 */
public class ShowcaseConfig {

    public static final String DEFAULT_MASK_COLOUR = "#dd335075";//默认颜色
    public static final long DEFAULT_FADE_TIME = 300;//默认显示淡入淡出的时间
    public static final long DEFAULT_DELAY = 0;//默认延迟
    public static final Shape DEFAULT_SHAPE = new CircleShape();//默认目标形状
    public static final int DEFAULT_SHAPE_PADDING = 10;//默认外边距

    private long mDelay = DEFAULT_DELAY;
    private int mMaskColour;
    private int mContentTextColor;
    private int mDismissTextColor;
    private long mFadeDuration = DEFAULT_FADE_TIME;
    private Shape mShape = DEFAULT_SHAPE;
    private int mShapePadding = DEFAULT_SHAPE_PADDING;

    public ShowcaseConfig() {
        mMaskColour = Color.parseColor(ShowcaseConfig.DEFAULT_MASK_COLOUR);//背景颜色
        mContentTextColor = Color.parseColor("#ffffff");//提示文字颜色
        mDismissTextColor = Color.parseColor("#ffffff");//点击后使新手指引消失的字的颜色
    }

    public long getDelay() {
        return mDelay;
    }

    public void setDelay(long delay) {
        this.mDelay = delay;
    }

    public int getMaskColor() {
        return mMaskColour;
    }

    public void setMaskColor(int maskColor) {
        mMaskColour = maskColor;
    }

    public int getContentTextColor() {
        return mContentTextColor;
    }

    public void setContentTextColor(int mContentTextColor) {
        this.mContentTextColor = mContentTextColor;
    }

    public int getDismissTextColor() {
        return mDismissTextColor;
    }

    public void setDismissTextColor(int dismissTextColor) {
        this.mDismissTextColor = dismissTextColor;
    }

    public long getFadeDuration() {
        return mFadeDuration;
    }

    public void setFadeDuration(long fadeDuration) {
        this.mFadeDuration = fadeDuration;
    }

    public Shape getShape() {
        return mShape;
    }

    public void setShape(Shape shape) {
        this.mShape = shape;
    }

    public void setShapePadding(int padding) {
        this.mShapePadding = padding;
    }

    public int getShapePadding() {
        return mShapePadding;
    }
}
