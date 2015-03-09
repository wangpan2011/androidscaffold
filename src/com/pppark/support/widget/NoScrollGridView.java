package com.pppark.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不能滚动的GridView,用于ScrollView+GridView
 * 
 * @Package com.xunlei.video.support.widget
 * @ClassName: NoScrollGridView
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-4-24 下午3:50:53
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        this(context,null);
    }
    public NoScrollGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
