package com.entertainment.project.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.entertainment.project.R;

/**
 * Created by Sick on 2016/12/9.
 */
public class AnimationLayout extends View {
    private Bitmap bitmap;
    private Paint mBitPaint;

    private int width, height;

    RotateAnimation animation;

    public AnimationLayout(Context context) {
        super(context);
        init(context);
    }


    public AnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_logo);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);

        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAnimationListener != null) {
                    onAnimationListener.onClick(v);
                }
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, (width - bitmap.getWidth()) / 2, (height - bitmap.getHeight())/2, mBitPaint);


    }

    public void setAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;

    }


    OnAnimationListener onAnimationListener;

    public interface OnAnimationListener {
        void onClick(View v);
    }

    public void runAnimation() {

        setAnimation(animation);
        animation.start();

    }

    public void stopAnimation() {
        animation.cancel();
    }
}
