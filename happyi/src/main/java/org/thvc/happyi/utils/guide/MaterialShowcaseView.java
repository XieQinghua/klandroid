package org.thvc.happyi.utils.guide;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.utils.guide.shape.CircleShape;
import org.thvc.happyi.utils.guide.shape.NoShape;
import org.thvc.happyi.utils.guide.shape.RectangleShape;
import org.thvc.happyi.utils.guide.shape.Shape;
import org.thvc.happyi.utils.guide.target.Target;
import org.thvc.happyi.utils.guide.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;


/**
 * create by huangxinqi
 * 当只有一个步骤时用这个类
 */
public class MaterialShowcaseView extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    private int mOldHeight;
    private int mOldWidth;
    private Bitmap mBitmap;// = new WeakReference<>(null);
    private Canvas mCanvas;
    private Paint mEraser;
    private Target mTarget;
    private Shape mShape;
    private int mXPosition;
    private int mYPosition;
    private boolean mWasDismissed = false;
    private int mShapePadding = ShowcaseConfig.DEFAULT_SHAPE_PADDING;

    private View mContentBox;
    private TextView mContentTextView;
    private TextView mDismissButton;
    private int mGravity;
    private int mContentBottomMargin;
    private int mContentTopMargin;
    private boolean mDismissOnTouch = false;
    private boolean mShouldRender = false; // flag to decide when we should actually render
    private int mMaskColour;
    private AnimationFactory mAnimationFactory;
    private boolean mShouldAnimate = true;
    private long mFadeDurationInMillis = ShowcaseConfig.DEFAULT_FADE_TIME;
    private Handler mHandler;
    private long mDelayInMillis = ShowcaseConfig.DEFAULT_DELAY;
    private int mBottomMargin = 0;
    private boolean mSingleUse = false; // should display only once
    private PrefsManager mPrefsManager; // used to store state doe single use mode
    List<IShowcaseListener> mListeners; // external listeners who want to observe when we show and dismiss
    private UpdateOnGlobalLayout mLayoutListener;
    private IDetachedListener mDetachedListener;

    public MaterialShowcaseView(Context context) {
        super(context);
        init(context);
    }

    public MaterialShowcaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaterialShowcaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialShowcaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        setWillNotDraw(false);

        // create our animation factory
        mAnimationFactory = new AnimationFactory();

        mListeners = new ArrayList<>();

        // make sure we add a global layout listener so we can adapt to changes
        mLayoutListener = new UpdateOnGlobalLayout();
        getViewTreeObserver().addOnGlobalLayoutListener(mLayoutListener);

        // consume touch events
        setOnTouchListener(this);

        mMaskColour = Color.parseColor(ShowcaseConfig.DEFAULT_MASK_COLOUR);
        setVisibility(INVISIBLE);


        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.showcase_content, this, true);
        mContentBox = contentView.findViewById(R.id.content_box);
        mContentTextView = (TextView) contentView.findViewById(R.id.tv_content);
        mDismissButton = (TextView) contentView.findViewById(R.id.tv_dismiss);
        mDismissButton.setOnClickListener(this);
    }


    /**
     * 画一层透明的图层，再擦除掉要进行新手指引的地方
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mShouldRender) return;

        // 获得当前维度
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // 如果需要的话，首先要构建一个新的画布，即先通过或新的维度
        if (mBitmap == null || mCanvas == null || mOldHeight != height || mOldWidth != width) {

            if (mBitmap != null) mBitmap.recycle();

            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


            mCanvas = new Canvas(mBitmap);
        }

        // 保存旧的维度
        mOldWidth = width;
        mOldHeight = height;

        // 清除画布
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // 画背景
        mCanvas.drawColor(mMaskColour);

        // 将要（新手）指引的的目标处图层白色
        if (mEraser == null) {
            mEraser = new Paint();
            mEraser.setColor(0xFFFFFFFF);
            mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
        }

        // 画要擦除的形状
        mShape.draw(mCanvas, mEraser, mXPosition, mYPosition, mShapePadding);

        // 在canvas上绘制出这个图形
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();


        if (!mWasDismissed && mSingleUse && mPrefsManager != null) {
            mPrefsManager.resetShowcase();
        }


        notifyOnDismissed();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mDismissOnTouch) {
            hide();
        }
        return true;
    }


    private void notifyOnDisplayed() {
        for (IShowcaseListener listener : mListeners) {
            listener.onShowcaseDisplayed(this);
        }
    }

    private void notifyOnDismissed() {
        if (mListeners != null) {
            for (IShowcaseListener listener : mListeners) {
                listener.onShowcaseDismissed(this);
            }

            mListeners.clear();
            mListeners = null;
        }

        /**
         * 内部监听器，用于监听新手指引显示的顺序
         */
        if (mDetachedListener != null) {
            mDetachedListener.onShowcaseDetached(this, mWasDismissed);
        }
    }

    /**
     * 让点击过的按钮消失
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        hide();
    }

    /**
     * 设置新手指引的目标,target要包含位置和大小的参数
     *
     * @param target
     */
    public void setTarget(Target target) {
        mTarget = target;

        // update dismiss btn_oranged_bg state
        updateDismissButton();

        if (mTarget != null) {

            /**
             * 当sdk版本大于loliPop版本时，避免画在标题栏上
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBottomMargin = getSoftButtonsBarSizePort((Activity) getContext());
                LayoutParams contentLP = (LayoutParams) getLayoutParams();

                if (contentLP != null && contentLP.bottomMargin != mBottomMargin)
                    contentLP.bottomMargin = mBottomMargin;
            }

            // 获得目标的位置
            Point targetPoint = mTarget.getPoint();
            Rect targetBounds = mTarget.getBounds();
            setPosition(targetPoint);

            // 计算提示应该出现在目标的上方还是下方
            int height = getMeasuredHeight();
            int midPoint = height / 2;
            int yPos = targetPoint.y;

            int radius = Math.max(targetBounds.height(), targetBounds.width()) / 2;
            if (mShape != null) {
                mShape.updateTarget(mTarget);
                radius = mShape.getHeight() / 2;
            }

            if (yPos > midPoint) {
                // target is in lower half of screen, we'll sit above it
                mContentTopMargin = 0;
                mContentBottomMargin = (height - yPos) + radius + mShapePadding;
                mGravity = Gravity.BOTTOM;
            } else {
                // target is in upper half of screen, we'll sit below it
                mContentTopMargin = yPos + radius + mShapePadding;
                mContentBottomMargin = 0;
                mGravity = Gravity.TOP;
            }
        }

        applyLayoutParams();
    }

    /**
     * 获得布局的参数
     */
    private void applyLayoutParams() {

        if (mContentBox != null && mContentBox.getLayoutParams() != null) {
            LayoutParams contentLP = (LayoutParams) mContentBox.getLayoutParams();

            boolean layoutParamsChanged = false;

            if (contentLP.bottomMargin != mContentBottomMargin) {
                contentLP.bottomMargin = mContentBottomMargin;
                layoutParamsChanged = true;
            }

            if (contentLP.topMargin != mContentTopMargin) {
                contentLP.topMargin = mContentTopMargin;
                layoutParamsChanged = true;
            }

            if (contentLP.gravity != mGravity) {
                contentLP.gravity = mGravity;
                layoutParamsChanged = true;
            }

            /**
             * Only apply the layout params if we've actually changed them, otherwise we'll get stuck in a layout loop
             */
            if (layoutParamsChanged)
                mContentBox.setLayoutParams(contentLP);
        }
    }

    /**
     * SETTERS 设置位置
     */

    void setPosition(Point point) {
        setPosition(point.x, point.y);
    }

    void setPosition(int x, int y) {
        mXPosition = x;
        mYPosition = y;
    }

    /**
     * 设置新手指引的内容
     *
     * @param contentText
     */
    private void setContentText(CharSequence contentText) {
        if (mContentTextView != null) {
            mContentTextView.setText(contentText);
        }
    }

    /**
     * 设置点击后让新手指引消失的按钮上的字
     *
     * @param dismissText
     */
    private void setDismissText(CharSequence dismissText) {
        if (mDismissButton != null) {
            mDismissButton.setText(dismissText);

            updateDismissButton();
        }
    }

    /**
     * 设置新手提示的内容的颜色
     *
     * @param textColour
     */
    private void setContentTextColor(int textColour) {
        if (mContentTextView != null) {
            mContentTextView.setTextColor(textColour);
        }
    }

    /**
     * 设置点击后让新手指引消失的按钮上的字的颜色
     *
     * @param textColour
     */
    private void setDismissTextColor(int textColour) {
        if (mDismissButton != null) {
            mDismissButton.setTextColor(textColour);
        }
    }

    /**
     * 设置指引的形状（留白处）的内边距
     *
     * @param padding
     */
    private void setShapePadding(int padding) {
        mShapePadding = padding;
    }

    /**
     * 设置 是否已经点击了让新手指引消失的按钮
     *
     * @param dismissOnTouch
     */
    private void setDismissOnTouch(boolean dismissOnTouch) {
        mDismissOnTouch = dismissOnTouch;
    }

    private void setShouldRender(boolean shouldRender) {
        mShouldRender = shouldRender;
    }

    /**
     * 设置新手指引的背景颜色
     *
     * @param maskColour
     */
    private void setMaskColour(int maskColour) {
        mMaskColour = maskColour;
    }

    private void setDelay(long delayInMillis) {
        mDelayInMillis = delayInMillis;
    }

    private void setFadeDuration(long fadeDurationInMillis) {
        mFadeDurationInMillis = fadeDurationInMillis;
    }

    /**
     * 添加监听器
     *
     * @param showcaseListener
     */
    public void addShowcaseListener(IShowcaseListener showcaseListener) {
        mListeners.add(showcaseListener);
    }

    /**
     * 更改监听器状态
     *
     * @param showcaseListener
     */
    public void removeShowcaseListener(MaterialShowcaseSequence showcaseListener) {
        if (mListeners.contains(showcaseListener)) {
            mListeners.remove(showcaseListener);
        }
    }

    /**
     * 更改监听器状态
     *
     * @param detachedListener
     */
    void setDetachedListener(IDetachedListener detachedListener) {
        mDetachedListener = detachedListener;
    }

    public void setShape(Shape mShape) {
        this.mShape = mShape;
    }

    /**
     * Set properties based on a config object
     *
     * @param config
     */
    public void setConfig(ShowcaseConfig config) {
        setDelay(config.getDelay());
        setFadeDuration(config.getFadeDuration());
        setContentTextColor(config.getContentTextColor());
        setDismissTextColor(config.getDismissTextColor());
        setMaskColour(config.getMaskColor());
        setShape(config.getShape());
        setShapePadding(config.getShapePadding());
    }

    /**
     * 更新点击后让新手指引消失的按钮的状态
     */
    private void updateDismissButton() {
        // hide or show btn_oranged_bg
        if (mDismissButton != null) {
            if (TextUtils.isEmpty(mDismissButton.getText())) {
                mDismissButton.setVisibility(GONE);
            } else {
                mDismissButton.setVisibility(VISIBLE);
            }
        }
    }

    public boolean hasFired() {
        return mPrefsManager.hasFired();
    }

    private class UpdateOnGlobalLayout implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            setTarget(mTarget);
        }
    }

    public static class Builder {
        private static final int CIRCLE_SHAPE = 0;
        private static final int RECTANGLE_SHAPE = 1;
        private static final int NO_SHAPE = 2;

        private boolean fullWidth = false;
        private int shapeType = CIRCLE_SHAPE;

        final MaterialShowcaseView showcaseView;

        private final Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;

            showcaseView = new MaterialShowcaseView(activity);
        }


        public Builder setTarget(View target) {
            showcaseView.setTarget(new ViewTarget(target));
            return this;
        }


        public Builder setDismissText(int resId) {
            return setDismissText(activity.getString(resId));
        }

        public Builder setDismissText(CharSequence dismissText) {
            showcaseView.setDismissText(dismissText);
            return this;
        }


        public Builder setContentText(int resId) {
            return setContentText(activity.getString(resId));
        }

        public Builder setContentText(CharSequence text) {
            showcaseView.setContentText(text);
            return this;
        }


        public Builder setDismissOnTouch(boolean dismissOnTouch) {
            showcaseView.setDismissOnTouch(dismissOnTouch);
            return this;
        }

        public Builder setMaskColour(int maskColour) {
            showcaseView.setMaskColour(maskColour);
            return this;
        }

        public Builder setContentTextColor(int textColour) {
            showcaseView.setContentTextColor(textColour);
            return this;
        }

        public Builder setDismissTextColor(int textColour) {
            showcaseView.setDismissTextColor(textColour);
            return this;
        }

        public Builder setDelay(int delayInMillis) {
            showcaseView.setDelay(delayInMillis);
            return this;
        }

        public Builder setFadeDuration(int fadeDurationInMillis) {
            showcaseView.setFadeDuration(fadeDurationInMillis);
            return this;
        }

        public Builder setListener(IShowcaseListener listener) {
            showcaseView.addShowcaseListener(listener);
            return this;
        }

        public Builder singleUse(String showcaseID) {
            showcaseView.singleUse(showcaseID);
            return this;
        }

        public Builder setShape(Shape shape) {
            showcaseView.setShape(shape);
            return this;
        }

        public Builder withCircleShape() {
            shapeType = CIRCLE_SHAPE;
            return this;
        }

        public Builder withoutShape() {
            shapeType = NO_SHAPE;
            return this;
        }

        public Builder setShapePadding(int padding) {
            showcaseView.setShapePadding(padding);
            return this;
        }

        public Builder withRectangleShape() {
            return withRectangleShape(false);
        }

        public Builder withRectangleShape(boolean fullWidth) {
            this.shapeType = RECTANGLE_SHAPE;
            this.fullWidth = fullWidth;
            return this;
        }

        public MaterialShowcaseView build() {
            if (showcaseView.mShape == null) {
                switch (shapeType) {
                    case RECTANGLE_SHAPE: {
                        showcaseView.setShape(new RectangleShape(showcaseView.mTarget.getBounds(), fullWidth));
                        break;
                    }
                    case CIRCLE_SHAPE: {
                        showcaseView.setShape(new CircleShape(showcaseView.mTarget));
                        break;
                    }
                    case NO_SHAPE: {
                        showcaseView.setShape(new NoShape());
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unsupported shape type: " + shapeType);
                }
            }

            return showcaseView;
        }

        public MaterialShowcaseView show() {
            build().show(activity);
            return showcaseView;
        }

    }

    private void singleUse(String showcaseID) {
        mSingleUse = true;
        mPrefsManager = new PrefsManager(getContext(), showcaseID);
    }

    public void removeFromWindow() {
        if (getParent() != null && getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }

        mEraser = null;
        mAnimationFactory = null;
        mCanvas = null;
        mHandler = null;

        getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutListener);
        mLayoutListener = null;

        if (mPrefsManager != null)
            mPrefsManager.close();

        mPrefsManager = null;


    }


    /**
     * 显示新手指导
     *
     * @param activity
     * @return
     */
    public boolean show(final Activity activity) {

        /**
         * if we're in single use mode and have already shot our bolt then do nothing
         */
        if (mSingleUse) {
            if (mPrefsManager.hasFired()) {
                return false;
            } else {
                mPrefsManager.setFired();
            }
        }

        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);

        setShouldRender(true);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mShouldAnimate) {
                    fadeIn();
                } else {
                    setVisibility(VISIBLE);
                    notifyOnDisplayed();
                }
            }
        }, mDelayInMillis);

        updateDismissButton();

        return true;
    }


    public void hide() {

        /**
         * This flag is used to indicate to onDetachedFromWindow that the showcase view was dismissed purposefully (by the user or programmatically)
         */
        mWasDismissed = true;

        if (mShouldAnimate) {
            fadeOut();
        } else {
            removeFromWindow();
        }
    }

    /**
     * 透明度加深
     */
    public void fadeIn() {
        setVisibility(INVISIBLE);

        mAnimationFactory.fadeInView(this, mFadeDurationInMillis,
                new IAnimationFactory.AnimationStartListener() {
                    @Override
                    public void onAnimationStart() {
                        setVisibility(View.VISIBLE);
                        notifyOnDisplayed();
                    }
                }
        );
    }

    /**
     * 透明度降低
     */
    public void fadeOut() {

        mAnimationFactory.fadeOutView(this, mFadeDurationInMillis, new IAnimationFactory.AnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                setVisibility(INVISIBLE);
                removeFromWindow();
            }
        });
    }

    /**
     * 重置sharedprefrence的对应的值，指引将再次显示
     */
    public void resetSingleUse() {
        if (mSingleUse && mPrefsManager != null) mPrefsManager.resetShowcase();
    }

    /**
     * 重置sharedprefrence的对应的值，指引将再次显示
     *
     * @param context
     * @param showcaseID
     */
    public static void resetSingleUse(Context context, String showcaseID) {
        PrefsManager.resetShowcase(context, showcaseID);
    }

    /**
     * 重置所有新手指引
     *
     * @param context
     */
    public static void resetAll(Context context) {
        PrefsManager.resetAll(context);
    }

    public static int getSoftButtonsBarSizePort(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

}
