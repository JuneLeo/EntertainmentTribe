package com.entertainment.project.view;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2015/6/10.
 * PullView
 * describe 有弹性的ScrollView 实现下拉弹回和上拉弹回
 */
public class PullView extends ScrollView {
    private static final String TAG = "PullView ";
    // 移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
// 目的是达到一个延迟的效果
    private static final float MOVE_FACTOR = 0.5f;
    // 阻尼系数
    private static final float OFFSET_RADIO = 3.5f;
    // 松开手指后, 界面回到正常位置需要的动画时间
    private static final int ANIM_TIME = 300;
    // ScrollView的子View， 也是ScrollView的唯一一个子View
    private View contentView;
    // 手指按下时的Y值, 用于在移动时计算移动距离
// 如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startY;
    // 用于记录正常的布局位置
    private Rect originalRect = new Rect();
    // 手指按下时记录是否可以继续下拉
    private boolean canPullDown = false;
    // 手指按下时记录是否可以继续上拉
    private boolean canPullUp = false;
    // 在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;

    private boolean isHover = false; //是否悬停

    public PullView(Context context) {
        super(context);
        init();
    }
    public PullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        this.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (contentView == null)
            return;
// ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
        originalRect.set(contentView.getLeft(), contentView.getTop(), contentView.getRight(), contentView.getBottom());
        for(int i = 0;i < noMoveViews.size(); i++){
            final View view = noMoveViews.get(i);
            view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Rect rect = new Rect();
                    rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    noMoveRects.add(rect);
                    view.removeOnLayoutChangeListener(this);
                }
            });

        }
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
// 手指是否移动到了当前ScrollView控件之外
        boolean isTouchOutOfScrollView = ev.getY() >= this.getHeight() || ev.getY() <= 0;
        if (isTouchOutOfScrollView) { // 如果移动到了当前ScrollView控件之外
            if (isMoved) // 如果当前contentView已经被移动, 首先把布局移到原位置, 然后消费点这个事件
                boundBack();
            return true;
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
// 判断是否可以上拉和下拉
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
// 记录按下时的Y值
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                float nowY2 = ev.getY();
                int deltaY2 = (int) (nowY2 - startY);
                if(isHover){
                    if(!(canPullUp && deltaY2 < 0)){
                        if(isMoved){
                           boundBack();
                        }
                    }
                }else{
                    boundBack();
                }

                if(isAnimation) {
                    for (int i = 0; i < alphaAnimationViews.size(); i++) {
                        Log.d("view alpha" + alphaAnimationViews.get(i).getAlpha(), "view visible" + alphaAnimationViews.get(i).getVisibility());
                        if (alphaAnimationViews.get(i) != null && alphaAnimationViews.get(i).getVisibility() == VISIBLE) {
                            showVisAnimation(alphaAnimationViews.get(i));
                            //views.get(i).startAnimation(getVisAnimation());
                            Log.d("visAnimation","显示动画");
                        }
                    }
                }
                isAnimation = false;

                isKeyBoard = false;

                break;
            case MotionEvent.ACTION_MOVE:
// 在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();
                    break;
                }
// 计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);
// 是否应该移动布局
                boolean shouldMove = (canPullDown && deltaY > 0) // 可以下拉， 并且手指向下移动
                        || (canPullUp && deltaY < 0) // 可以上拉， 并且手指向上移动
                        || (canPullUp && canPullDown); // 既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                if (shouldMove) {
// 计算偏移量
                    int offset = (int) (deltaY / OFFSET_RADIO);
// 随着手指的移动而移动布局
                    contentView.layout(originalRect.left, originalRect.top + offset, originalRect.right, originalRect.bottom + offset);
                    for(int i = 0;i < noMoveViews.size();i++){
                        if(noMoveViews.get(i) != null) {
                            noMoveViews.get(i).layout(noMoveRects.get(i).left,noMoveRects.get(i).top  + offset,noMoveRects.get(i).right,noMoveRects.get(i).bottom + offset);
                            //noMoveViews.get(i).scrollTo(noMoveRects.get(i).left,noMoveRects.get(i).top  - offset);
                        }
                    }
                    isMoved = true; // 记录移动了布局
                }


                if(!isAnimation) {
                    for (int i = 0; i < alphaAnimationViews.size(); i++) {
                        Log.d("view alpha" + alphaAnimationViews.get(i).getAlpha(), "view visible" + alphaAnimationViews.get(i).getVisibility());
                        if (alphaAnimationViews.get(i) != null && alphaAnimationViews.get(i).getAlpha() == 1 && alphaAnimationViews.get(i).getVisibility() == VISIBLE) {
                            //views.get(i).startAnimation(getGonAnimation());
                            showGonAnimation(alphaAnimationViews.get(i));
                            Log.d("gonAnimation", "隐藏动画");
                        }
                    }
                }
                isAnimation = true;

                if(!isKeyBoard && Math.abs(deltaY) > 10){
                    InputMethodManager imm = (InputMethodManager)
                            getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(PullView.this.getWindowToken(), 0);

                    isKeyBoard = true;
                }

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isAnimation = false;
    private boolean isKeyBoard = false;
    private float startScroll;
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                startScroll = ev.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                if(isAnimation) {
//                    for (int i = 0; i < alphaAnimationViews.size(); i++) {
//                        Log.d("view alpha" + alphaAnimationViews.get(i).getAlpha(),"view visible" + alphaAnimationViews.get(i).getVisibility());
//                        if (alphaAnimationViews.get(i) != null && alphaAnimationViews.get(i).getVisibility() == VISIBLE) {
//                            showVisAnimation(alphaAnimationViews.get(i));
//                            //views.get(i).startAnimation(getVisAnimation());
//                            Log.d("visAnimation","显示动画");
//                        }
//                    }
//                }
//                isAnimation = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if(!isAnimation) {
//                    for (int i = 0; i < alphaAnimationViews.size(); i++) {
//                        Log.d("view alpha" + alphaAnimationViews.get(i).getAlpha(), "view visible" + alphaAnimationViews.get(i).getVisibility());
//                        if (alphaAnimationViews.get(i) != null && alphaAnimationViews.get(i).getAlpha() == 1 && alphaAnimationViews.get(i).getVisibility() == VISIBLE) {
//                            //views.get(i).startAnimation(getGonAnimation());
//                            showGonAnimation(alphaAnimationViews.get(i));
//                            Log.d("gonAnimation", "隐藏动画");
//                        }
//                    }
//                }
//                isAnimation = true;
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    /**
     * 将内容布局移动到原位置 可以在UP事件中调用, 也可以在其他需要的地方调用, 如手指移动到当前ScrollView外时
     */
    private void boundBack() {
        if (!isMoved)
            return; // 如果没有移动布局， 则跳过执行
// 开启动画
        TranslateAnimation anim = new TranslateAnimation(0, 0, contentView.getTop(), originalRect.top);
        anim.setDuration(ANIM_TIME);
        contentView.startAnimation(anim);
// 设置回到正常的布局位置
        contentView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
        for(int i = 0;i < noMoveViews.size();i++){
            if(noMoveViews.get(i) != null) {
                noMoveViews.get(i).startAnimation(anim);
                //noMoveViews.get(i).scrollTo(noMoveRects.get(i).left,noMoveRects.get(i).top);
                noMoveViews.get(i).layout(noMoveRects.get(i).left,noMoveRects.get(i).top ,noMoveRects.get(i).right,noMoveRects.get(i).bottom );
            }
        }
// 将标志位设回false
        canPullDown = false;
        canPullUp = false;
        isMoved = false;
    }
    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0 || contentView.getHeight() < getHeight() + getScrollY();
    }
    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return contentView.getHeight() <= getHeight() + getScrollY();
    }


    private List<View> alphaAnimationViews = new ArrayList<View>();
    private List<View> noMoveViews = new ArrayList<View>();
    private List<Rect> noMoveRects = new ArrayList<Rect>();

    private int alphaAnimationTime = 200;

    public void setAlphaAnimationViews(View view) {
        this.alphaAnimationViews.add(view);
    }
    public void setNoMoveViews(View view) {
        this.noMoveViews.add(view);
        bringChildToFront(view);
    }

    Animation animationVis = new AlphaAnimation(0.0f, 1.0f);
    Animation animationGon = new AlphaAnimation(1.0f, 0.0f);

    public Animation getVisAnimation(){
        animationVis.setDuration(alphaAnimationTime);
        animationVis.setFillAfter(true);
        return animationVis;
    }
    public void showVisAnimation(View view){
        ObjectAnimator.ofFloat(view, "alpha", 0.0F, 1.0F)//
                .setDuration(alphaAnimationTime)//
                .start();
    }

    public Animation getGonAnimation(){
        animationGon.setDuration(alphaAnimationTime);
        animationGon.setFillAfter(true);
        return animationGon;
    }
    public void showGonAnimation(View view){
        ObjectAnimator.ofFloat(view, "alpha", 1.0F, 0.0F)//
                .setDuration(alphaAnimationTime)//
                .start();
    }

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
    }
}
