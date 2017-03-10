package com.entertainment.project.modules.alipay.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.entertainment.project.common.utils.ScreenUtils;

/**
 * Created by Sick on 2016/11/2.
 */
public class AlipayItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = AlipayItemDecoration.class.getSimpleName();
    private Rect rect;
    private Paint paint;
    private Paint paintBackGround;
    private Context context;

    private boolean isFirstType2 = true;
    private int index2 = 0;

    private boolean isFirstType4 = true;
    private int index4 = 0;
    private int type2Num = 0;

    public AlipayItemDecoration(Context context) {
        rect = new Rect();
        paint = new Paint();
        paintBackGround = new Paint();
        paint.setColor(Color.parseColor("#e9efed"));
        paintBackGround.setColor(Color.parseColor("#e9efed"));
        this.context = context;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {


        for (int a = 0; a < parent.getChildCount(); a++) {   //获取的是显示出来的item数量 并且划过第一以后，下一个将作为第0个
            android.util.Log.d(TAG, "onDraw: " + a);
            final View child = parent.getChildAt(a);   //可见的那个view
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int i = parent.getChildLayoutPosition(parent.getChildAt(a));   //可见的view对应的adapter上的position位置
            //下面缺少优化，
            //问题已经解决 TODO 可以考虑通过  不同类型的布局 type 来优化，将会更优雅，存在问题是判断对应type的具体位置，而造成无法判断是取余后的位置，当前的写死的
            int type = parent.getAdapter().getItemViewType(i);
            switch (type) {
                case 1:
                    final int left = child.getLeft() - params.leftMargin + ScreenUtils.dip2px(context, 15);
                    final int right = child.getRight() + params.rightMargin - ScreenUtils.dip2px(context, 15);
                    final int top = child.getBottom() + params.bottomMargin;
                    final int bottom = top + ScreenUtils.dip2px(context, 1);
                    c.drawRect(left, top, right, bottom, paint);
                    break;
                case 2:

                    i = i - index2;

                    if (i % 3 == 0 || i % 3 == 1) {
                        final int tops2 = child.getTop() - params.topMargin + ScreenUtils.dip2px(context, 8);
                        final int bottoms2 = child.getBottom() + params.bottomMargin - ScreenUtils.dip2px(context, 8);
                        final int lefts2 = child.getRight() + params.rightMargin;
                        final int rights2 = lefts2 + ScreenUtils.dip2px(context, 1);
                        c.drawRect(lefts2, tops2, rights2, bottoms2, paint);
                    }

                    if (type2Num > 3) {
                        if (type2Num%3==0){
                            if (i==type2Num-1||i==type2Num-2||i==type2Num-3){
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 15);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }else{
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 1);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }
                        }else if (type2Num%3==1){
                            if (i==type2Num-1){
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 15);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }else{
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 1);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }
                        }else if (type2Num%3==2){
                            if (i==type2Num-1||i==type2Num-2){
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 15);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }else{
                                final int left2 = child.getLeft() - params.leftMargin;
                                final int right2 = child.getRight() + params.rightMargin;
                                final int top2 = child.getBottom() + params.bottomMargin;
                                final int bottom2 = top2 + ScreenUtils.dip2px(context, 1);
                                c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                            }
                        }
                    } else {
                        final int left2 = child.getLeft() - params.leftMargin;
                        final int right2 = child.getRight() + params.rightMargin;
                        final int top2 = child.getBottom() + params.bottomMargin;
                        final int bottom2 = top2 + ScreenUtils.dip2px(context, 15);
                        c.drawRect(left2, top2, right2, bottom2, paintBackGround);
                    }


                    break;
                case 3:
                    final int left3 = child.getLeft() - params.leftMargin;
                    final int right3 = child.getRight() + params.rightMargin;
                    final int top3 = child.getBottom() + params.bottomMargin;
                    final int bottom3 = top3 + ScreenUtils.dip2px(context, 1);
                    c.drawRect(left3, top3, right3, bottom3, paint);
                    break;
                case 4:

                    i = i - index4;
                    if (i % 4 == 0) {
                        final int top_4_1 = child.getTop() - params.topMargin + ScreenUtils.dip2px(context, 12);
                        final int bottom_4_1 = child.getBottom() + params.bottomMargin - ScreenUtils.dip2px(context, 12);
                        final int left_4_1 = child.getRight() + params.rightMargin;
                        final int right_4_1 = left_4_1 + ScreenUtils.dip2px(context, 1);
                        c.drawRect(left_4_1, top_4_1, right_4_1, bottom_4_1, paint);

                        final int left_4_s = child.getLeft() - params.leftMargin;
                        final int right_4_s = child.getRight() + params.rightMargin;
                        final int top_4_s = child.getBottom() + params.bottomMargin;
                        final int bottom_4_s = top_4_s + ScreenUtils.dip2px(context, 1);
                        c.drawRect(left_4_s, top_4_s, right_4_s, bottom_4_s, paintBackGround);
                    }
                    if (i % 4 == 1) {
                        final int left_4_2 = child.getLeft() - params.leftMargin;
                        final int right_4_2 = child.getRight() + params.rightMargin;
                        final int top_4_2 = child.getBottom() + params.bottomMargin;
                        final int bottom_4_2 = top_4_2 + ScreenUtils.dip2px(context, 1);
                        c.drawRect(left_4_2, top_4_2, right_4_2, bottom_4_2, paintBackGround);
                    }
                    if (i % 4 == 2) {
                        final int top_4_3 = child.getTop() - params.topMargin + ScreenUtils.dip2px(context, 12);
                        final int bottom_4_3 = child.getBottom() + params.bottomMargin - ScreenUtils.dip2px(context, 12);
                        final int left_4_3 = child.getRight() + params.rightMargin;
                        final int right_4_3 = left_4_3 + ScreenUtils.dip2px(context, 1);
                        c.drawRect(left_4_3, top_4_3, right_4_3, bottom_4_3, paint);

                        final int left_4_s = child.getLeft() - params.leftMargin;
                        final int right_4_s = child.getRight() + params.rightMargin;
                        final int top_4_s = child.getBottom() + params.bottomMargin;
                        final int bottom_4_s = top_4_s + ScreenUtils.dip2px(context, 15);
                        c.drawRect(left_4_s, top_4_s, right_4_s, bottom_4_s, paintBackGround);
                    }
                    if (i % 4 == 3) {
                        final int left_4_4 = child.getLeft() - params.leftMargin;
                        final int right_4_4 = child.getRight() + params.rightMargin;
                        final int top_4_4 = child.getBottom() + params.bottomMargin;
                        final int bottom_4_4 = top_4_4 + ScreenUtils.dip2px(context, 15);
                        c.drawRect(left_4_4, top_4_4, right_4_4, bottom_4_4, paintBackGround);
                    }

                    break;
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildLayoutPosition(view);

        int type = parent.getAdapter().getItemViewType(itemPosition);
        switch (type) {
            case 1:
                outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                break;
            case 2:
                if (isFirstType2) {
                    index2 = itemPosition;
                    isFirstType2 = false;
                }
                itemPosition = itemPosition - index2;
                if (type2Num > 3) {
                    if (type2Num%3==0){
                        if (itemPosition==type2Num-1||itemPosition==type2Num-2||itemPosition==type2Num-3){
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 15));
                        }else{
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                        }
                    }else if (type2Num%3==1){
                        if (itemPosition==type2Num-1){
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 15));
                        }else{
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                        }
                    }else if (type2Num%3==2){
                        if (itemPosition==type2Num-1||itemPosition==type2Num-2){
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 15));
                        }else{
                            outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                        }
                    }
                } else {
                    outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 15));
                }
                break;
            case 3:
                outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                break;
            case 4:
                if (isFirstType4) {
                    index4 = itemPosition;
                    isFirstType4 = false;
                }
                itemPosition = itemPosition - index4;
                if (itemPosition % 4 == 0 || itemPosition % 4 == 1) {
                    outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 1));
                }
                if (itemPosition % 4 == 2 || itemPosition % 4 == 3) {
                    outRect.set(0, 0, 0, ScreenUtils.dip2px(context, 15));
                }
                break;
        }
    }

    public void setDat(int type2Num) {
        this.type2Num = type2Num;
    }
}
