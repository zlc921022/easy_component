package com.xiaochen.common.widget.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.core.content.ContextCompat;

import com.xiaochen.common.widget.R;


/**
 * @author zlc
 * @created 28/06/2018
 * @desc 开关按钮自定义
 */
@SuppressWarnings("all")
public class SwitchToggleView extends View implements View.OnClickListener {

    private Paint mTogglePaint;
    private int mToggleOpenColor;
    private int mToggleCloseColor;
    private int mToggleColor;
    private float mToggleRoundRadius;
    private Paint mBackGroundPaint;
    private RectF mBgRectF;
    //x方向偏移量
    private float mDistanceX = 0f;
    //开关有没有移动
    private boolean mIsToggleMove = false;
    //动画相关
    private float mEndX;
    private float mStartX;
    private float mDownX;
    private float mDownY;
    //动画执行时间
    private static final long DURATION = 500;
    //开关打开状态
    private boolean mToggleOpen;
    /**
     * 控件宽高
     */
    private int mViewWidth;
    private int mViewHeight;
    /**
     * 圆的半径
     */
    private int mCircleRadius;
    /**
     * x方向偏移量
     */
    private float mOffsetX;
    private int mDx1;

    private int mSwitchState;
    private final static int OPENED = 0;
    private final static int GOING_TO_OPEN = 1;
    private final static int GOING_TO_CLOSE = 2;
    private final static int CLOSED = 3;

    public SwitchToggleView(Context context) {
        this(context, null);
    }

    public SwitchToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchToggleView);
        mToggleOpenColor = typedArray.getColor(R.styleable.SwitchToggleView_toggle_openColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        mToggleCloseColor = typedArray.getColor(R.styleable.SwitchToggleView_toggle_closeColor,
                ContextCompat.getColor(context, R.color.colorGraySecondary));
        mToggleColor = typedArray.getColor(R.styleable.SwitchToggleView_toggle_color,
                ContextCompat.getColor(context, R.color.colorWhite));
        mToggleRoundRadius = typedArray.getDimension(R.styleable.SwitchToggleView_toggle_round_radius,
                dp2px(R.dimen.dp_30));
        this.mToggleOpen = typedArray.getBoolean(R.styleable.SwitchToggleView_toggle_open, false);
        //不要忘记回收
        typedArray.recycle();
    }

    private void initPaint() {

        //初始化开关画笔
        mTogglePaint = new Paint();
        mTogglePaint.setAntiAlias(true);
        mTogglePaint.setStyle(Paint.Style.FILL);
        mTogglePaint.setStrokeWidth(getDimens(R.dimen.dp_1));
        mTogglePaint.setColor(mToggleColor);

        //初始化背景画笔
        mBackGroundPaint = new Paint();
        mBackGroundPaint.setAntiAlias(true);
        mBackGroundPaint.setStyle(Paint.Style.FILL);
        mBackGroundPaint.setStrokeWidth(getDimens(R.dimen.dp_1));

        mBgRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //wrap_content时默认指定一个宽高
        int width = getDimens(R.dimen.dp_52);
        int height = getDimens(R.dimen.dp_26);
        int measureWidth = (widthMode == MeasureSpec.AT_MOST) ? width : widthSize;
        int measureHeight = (heightMode == MeasureSpec.AT_MOST) ? height : heightSize;

        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mDx1 = getDimens(R.dimen.dp_1);
        mToggleRoundRadius = mViewHeight * 1.0f / 2;
        mCircleRadius = mViewHeight / 2 - mDx1;
        mSwitchState = mToggleOpen ? OPENED : CLOSED;
        if (mToggleOpen) {
            mOffsetX = mViewWidth - mCircleRadius - mDx1;
        } else {
            mOffsetX = mDx1 + mCircleRadius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSwitchToggle(canvas);
    }

    private void drawSwitchToggle(Canvas canvas) {
        //画背景圆角矩形
        if (mSwitchState == OPENED || mSwitchState == GOING_TO_OPEN) {
            mBackGroundPaint.setColor(mToggleOpenColor);
        } else {
            mBackGroundPaint.setColor(mToggleCloseColor);
        }
        mBgRectF.set(0, 0, mViewWidth, mViewHeight);
        canvas.drawRoundRect(mBgRectF, mToggleRoundRadius, mToggleRoundRadius, mBackGroundPaint);

        //画圆形开关按钮
        float radius = mCircleRadius;
        float cx, cy = mViewHeight * 1.0f / 2;
        if (mSwitchState == OPENED) {
            cx = mViewWidth - radius - mDx1;
        } else if (mSwitchState == CLOSED) {
            cx = radius + mDx1;
        } else if (mSwitchState == GOING_TO_OPEN) {
            cx = Math.min(mOffsetX, mViewWidth - radius - mDx1);
        } else {
            cx = Math.max(mOffsetX, radius + mDx1);
        }
        canvas.drawCircle(cx, cy, radius, mTogglePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mIsToggleMove = false;
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                mDistanceX = mDownX - moveX;
                float distanceY = moveY - mDownY;
                mIsToggleMove = Math.abs(mDistanceX) > Math.abs(distanceY)
                        && Math.abs(mDistanceX) >= mViewWidth * 1.0f / 2;
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if (mIsToggleMove) {
                    switchToggle();
                    setToggleAnimation();
                    return true;
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    /**
     * 开关切换
     */
    private void switchToggle() {
        if (mDistanceX > 0) {
            //从右往左 --> 关闭
            mOffsetX = mViewWidth - mCircleRadius;
            mStartX = mOffsetX;
            mEndX = mDx1 + mCircleRadius;
            mSwitchState = GOING_TO_CLOSE;
        } else {
            //从左往右 --> 打开
            mOffsetX = mDx1 + mCircleRadius;
            mStartX = mOffsetX;
            mEndX = mViewWidth - mCircleRadius - mDx1;
            mSwitchState = GOING_TO_OPEN;
        }
    }

    @Override
    public void onClick(View view) {
        Log.e("startX", mDownX + "");
        //点击开关同一侧 不做处理
        boolean isOpen = mDownX > mViewWidth * 1.0f / 2 && mSwitchState == OPENED;
        boolean isClosed = mDownX < mViewWidth * 1.0f / 2 && mSwitchState == CLOSED;
        if (isOpen || isClosed) {
            return;
        }
        mDistanceX = mOffsetX - mDownX;
        switchToggle();
        setToggleAnimation();
    }

    //给按钮添加动画
    public void setToggleAnimation() {
        ValueAnimator toggleAnimator = ValueAnimator.ofFloat(mStartX, mEndX);
        toggleAnimator.setDuration(DURATION);
        toggleAnimator.setInterpolator(new AccelerateInterpolator());
        toggleAnimator.addUpdateListener(animation -> {
            mOffsetX = (Float) animation.getAnimatedValue();
            invalidate();
        });
        toggleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mSwitchState == GOING_TO_CLOSE) {
                    mSwitchState = CLOSED;
                } else if (mSwitchState == GOING_TO_OPEN) {
                    mSwitchState = OPENED;
                }
                if (mOnToggleListener != null) {
                    mToggleOpen = mOffsetX >= mViewWidth * 1.0f / 2;
                    mOnToggleListener.onSwitchClick(SwitchToggleView.this, mToggleOpen);
                }
            }
        });
        toggleAnimator.start();
    }

    public void setToggleCloseColor(int toggleCloseColor) {
        mToggleCloseColor = toggleCloseColor;
        postInvalidate();
    }

    public void setToggleOpenColor(int toggleOpenColor) {
        mToggleOpenColor = toggleOpenColor;
        postInvalidate();
    }

    public void setToggleColor(int toggleColor) {
        mToggleColor = toggleColor;
        postInvalidate();
    }

    public void setToggleRoundRadius(float toggleRoundRadius) {
        mToggleRoundRadius = toggleRoundRadius;
        postInvalidate();
    }

    public int dp2px(int resId) {
        return getDimens(resId);
    }

    /**
     * 获取开关打开状态
     *
     * @return
     */
    public boolean isToggleOpen() {
        return mToggleOpen;
    }

    /**
     * 设置开关打开状态
     *
     * @param toggleOpen
     */
    public void setToggleOpen(boolean toggleOpen) {
        this.mToggleOpen = toggleOpen;
        if (toggleOpen) {
            mOffsetX = mViewWidth - mCircleRadius - mDx1;
        } else {
            mOffsetX = mDx1 + mCircleRadius;
        }
        invalidate();
    }

    public interface OnToggleSwitchListener {
        void onSwitchClick(SwitchToggleView view, boolean isOpen);
    }

    private OnToggleSwitchListener mOnToggleListener;

    public void setOnToggleListener(OnToggleSwitchListener onToggleListener) {
        mOnToggleListener = onToggleListener;
    }

    public int getDimens(int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }
}
