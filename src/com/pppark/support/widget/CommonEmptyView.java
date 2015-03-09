package com.pppark.support.widget;

import com.pppark.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 通用的 EmptyView
 * 
 * @Package com.xunlei.video.support.widget
 * @ClassName: CommonEmptyView
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-5-13 下午3:09:21
 */
public class CommonEmptyView extends LinearLayout {
    private String topText, bottomText;
    private Drawable emptyNoticeBg;
    private boolean showProgressBar = true;
    private boolean showRefreshBtn = false;
    @InjectView(R.id.common_empty_view_noticeview)
    View noticeView;
    @InjectView(R.id.common_empty_view_loading_pb)
    ProgressBar pb;
    @InjectView(R.id.common_empty_view_top_tv)
    TextView topTextView;
    @InjectView(R.id.common_empty_view_bottom_tv)
    TextView bottomTextView;
    @InjectView(R.id.common_empty_view_imageview)
    ImageView iv;

    public CommonEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonEmptyView);
        this.topText = ta.getString(R.styleable.CommonEmptyView_topText);
        this.bottomText = ta.getString(R.styleable.CommonEmptyView_bottomText);
        this.emptyNoticeBg = ta.getDrawable(R.styleable.CommonEmptyView_emptyNoticeBg);
        this.showProgressBar = ta.getBoolean(R.styleable.CommonEmptyView_showProgressBar, true);
        this.showRefreshBtn = ta.getBoolean(R.styleable.CommonEmptyView_showRefreshBtn, false);
        ta.recycle();
        initView(context);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        inflate(context, R.layout.common_empty_view, this);
        ButterKnife.inject(this);

        pb.setIndeterminateDrawable(getResources().getDrawable(R.anim.common_progress_drawable_anim));

        iv.setBackgroundDrawable(emptyNoticeBg);
        
        setTopText(topText);
        setBottomText(bottomText);
        if (showProgressBar) {
            switchToLoading();
        } else {
            switchToLoaded();
        }
    }

    public void showProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }

    public void showNoticeView() {
        noticeView.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        pb.setVisibility(View.GONE);
    }

    public void hideNoticeView() {
        noticeView.setVisibility(View.GONE);
    }

    /**
     * 隐藏progressbar，显示提示信息
     * 
     * @Title: switchToLoaded
     * @return void
     * @date 2013-12-27 下午2:54:08
     */
    public void switchToLoaded() {
        hideProgressBar();
        showNoticeView();
    }

    /**
     * 显示progressBar，隐藏提示信息
     * 
     * @Title: switchToLoading
     * @return void
     * @date 2013-12-27 下午2:53:23
     */
    public void switchToLoading() {
        showProgressBar();
        hideNoticeView();
    }

    /**
     * 隐藏progressbar，显示提示信息，显示刷新按钮
     * 
     * @Title: switchToRefresh
     * @return void
     * @date 2014-5-13 下午4:08:24
     */
    public void switchToRefresh() {
        hideProgressBar();
        showNoticeView();
    }

    /**
     * 隐藏CommonEmptyView
     * 
     * @Title: hide
     * @return void
     * @date 2014-5-13 下午3:30:35
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * 显示CommonEmptyView
     * 
     * @Title: show
     * @return void
     * @date 2014-5-13 下午3:30:45
     */
    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void setTopText(String topText) {
        topTextView.setText(topText);
    }

    public void setTopText(int topTextId) {
        topTextView.setText(topTextId);
    }

    public void setBottomText(String bottomText) {
        bottomTextView.setText(bottomText);
    }

    public void setBottomText(int bottomTextId) {
        bottomTextView.setText(bottomTextId);
    }

    public void setNoticeImg(int imageId) {
        iv.setBackgroundResource(imageId);
    }

    public void setRefreshBtnOnClickListener(OnClickListener listener) {
        noticeView.setOnClickListener(listener);
    }

    public boolean isShowing() {
        return getVisibility() == View.VISIBLE;
    }
}
