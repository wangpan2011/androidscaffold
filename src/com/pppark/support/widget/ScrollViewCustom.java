package com.pppark.support.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ScrollViewCustom extends HorizontalScrollView {
    private int mInitPosition;
    private int mNewCheck = 100;
    private int mChildWidth = 0;

    private OnScrollStopListner mOnScrollstopListner;

    private Runnable mScrollerTask = new Runnable() {
        @Override
        public void run() {
            int newPosition = getScrollX();
            if (mInitPosition - newPosition == 0) {
                if (mOnScrollstopListner == null) {
                    return;
                }

                mOnScrollstopListner.onScrollStoped();
                Rect outRect = new Rect();
                getDrawingRect(outRect);

                if (getScrollX() == 0) {
                    mOnScrollstopListner.onScrollToLeftEdge();
                } else if (mChildWidth + getPaddingLeft() + getPaddingRight() == outRect.right) {
                    mOnScrollstopListner.onScrollToRightEdge();
                } else {
                    mOnScrollstopListner.onScrollToMiddle();
                }
            } else {
                mInitPosition = getScrollX();
                postDelayed(mScrollerTask, mNewCheck);
            }
        }
    };

    public ScrollViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startScrollerTask() {
        if (!isFull()) {
            return;
        }

        mInitPosition = getScrollX();
        postDelayed(mScrollerTask, mNewCheck);
        // checkTotalWidth();
    }

    private void checkTotalWidth() {
        if (mChildWidth > 0) {
            return;
        }

        for (int i = 0; i < getChildCount(); i++) {
            mChildWidth += getChildAt(i).getWidth();
        }
    }

    /**
     * 判断子控件是否填充满ScrollView
     * 
     * @return
     */
    public boolean isFull() {
        checkTotalWidth();

        boolean isFull = true;
        Rect outRect = new Rect();
        getDrawingRect(outRect);
        if (mChildWidth + getPaddingLeft() + getPaddingRight() <= outRect.right) {
            isFull = false;
        }

        return isFull;
    }

    public void setOnScrollStopListner(OnScrollStopListner listner) {
        mOnScrollstopListner = listner;
    }

    public interface OnScrollStopListner {
        /**
         * scroll have stoped
         */
        void onScrollStoped();

        /**
         * scroll have stoped, and is at left edge
         */
        void onScrollToLeftEdge();

        /**
         * scroll have stoped, and is at right edge
         */
        void onScrollToRightEdge();

        /**
         * scroll have stoped, and is at middle
         */
        void onScrollToMiddle();
    }
}
