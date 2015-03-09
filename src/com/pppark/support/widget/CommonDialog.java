package com.pppark.support.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;

import com.pppark.R;


/**
 * 通用dialog
 * 
 * @Package com.xunlei.video.support.widget
 * @ClassName: CommonDialog
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-6-6 下午2:42:11
 */
public class CommonDialog extends Dialog implements View.OnClickListener {

    private View rootView;

    public View messageRootView;
    public TextView titleTv;
    public TextView messageTv;
    public FrameLayout contentRootView;
    public Button positiveBtn;
    public Button negativeBtn;
    public Button neutralBtn;

    private CharSequence mTitleText;
    private CharSequence mMessageText;
    private CharSequence mPositiveBtnText;
    private CharSequence mNegativeBtnText;
    private CharSequence mNeutralBtnText;
    private View mContentView;

    private OnClickListener mPositiveListener;
    private OnClickListener mNegativeListener;
    private OnClickListener mNeutralListener;

    private int messageTvGravity = Gravity.CENTER;

    private DialogInterface.OnClickListener mDefaultClickListener = new OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    public CommonDialog(Context context) {
        this(context, 0);
    }

    public CommonDialog(Context context, int theme) {
        super(context, R.style.common_dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.common_dialog_layout, null);
        setContentView(rootView);
        initView(rootView);
        initData();
    }

    private void initView(View root) {
        titleTv = ButterKnife.findById(root, R.id.common_dialog_title_tv);
        messageTv = ButterKnife.findById(root, R.id.common_dialog_message_tv);
        messageRootView = ButterKnife.findById(root, R.id.common_dialog_content_scrollview);

        contentRootView = ButterKnife.findById(root, R.id.common_dialog_content_flayout);

        positiveBtn = ButterKnife.findById(root, R.id.common_dialog_btn_ok);
        negativeBtn = ButterKnife.findById(root, R.id.common_dialog_btn_cancel);
        neutralBtn = ButterKnife.findById(root, R.id.common_dialog_btn_middle);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        neutralBtn.setOnClickListener(this);
    }

    private void initData() {
        if (TextUtils.isEmpty(mTitleText)) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(mTitleText);
        }

        messageTv.setGravity(messageTvGravity);
        if (TextUtils.isEmpty(mMessageText)) {
            messageTv.setVisibility(View.GONE);
            messageRootView.setVisibility(View.GONE);
        } else {
            messageTv.setVisibility(View.VISIBLE);
            messageTv.setText(mMessageText);
            messageRootView.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mPositiveBtnText)) {
            positiveBtn.setVisibility(View.GONE);
        } else {
            positiveBtn.setVisibility(View.VISIBLE);
            positiveBtn.setText(mPositiveBtnText);
        }

        if (TextUtils.isEmpty(mNegativeBtnText)) {
            negativeBtn.setVisibility(View.GONE);
        } else {
            negativeBtn.setVisibility(View.VISIBLE);
            negativeBtn.setText(mNegativeBtnText);
        }

        if (TextUtils.isEmpty(mNeutralBtnText)) {
            neutralBtn.setVisibility(View.GONE);
        } else {
            neutralBtn.setVisibility(View.VISIBLE);
            neutralBtn.setText(mNeutralBtnText);
        }

        if (null != mContentView) {
            contentRootView.removeAllViews();
            contentRootView.addView(mContentView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.common_dialog_btn_ok:
            if (null != mPositiveListener)
                mPositiveListener.onClick(this, BUTTON_POSITIVE);
            mDefaultClickListener.onClick(this, BUTTON_POSITIVE);
            break;
        case R.id.common_dialog_btn_cancel:
            if (null != mNegativeListener)
                mNegativeListener.onClick(this, BUTTON_NEGATIVE);
            mDefaultClickListener.onClick(this, BUTTON_NEGATIVE);
            break;
        case R.id.common_dialog_btn_middle:
            if (null != mNeutralListener)
                mNeutralListener.onClick(this, BUTTON_NEUTRAL);
            mDefaultClickListener.onClick(this, BUTTON_NEUTRAL);
            break;
        default:
            break;
        }
    }

    public void setTitle(CharSequence titleText) {
        this.mTitleText = titleText;
    }

    public void setMessage(CharSequence messageText) {
        this.mMessageText = messageText;
    }

    public void setPositiveBtnText(CharSequence positiveBtnText) {
        this.mPositiveBtnText = positiveBtnText;
    }

    public void setNegativeBtnText(CharSequence negativeBtnText) {
        this.mNegativeBtnText = negativeBtnText;
    }

    public void setNeutralBtnText(CharSequence neutralBtnText) {
        this.mNeutralBtnText = neutralBtnText;
    }

    public void setCommonView(View view) {
        this.mContentView = view;
    }

    public void setPositiveListener(OnClickListener positiveListener) {
        this.mPositiveListener = positiveListener;
    }

    public void setNegativeListener(OnClickListener negativeListener) {
        this.mNegativeListener = negativeListener;
    }

    public void setNeutralListener(OnClickListener neutralListener) {
        this.mNeutralListener = neutralListener;
    }

    public Button getButton(int whichButton) {
        switch (whichButton) {
        case BUTTON_NEGATIVE:
            return this.negativeBtn;
        case BUTTON_NEUTRAL:
            return this.neutralBtn;
        case BUTTON_POSITIVE:
            return this.positiveBtn;
        }
        return this.positiveBtn;
    }

    public void setMessageGravity(int gravity) {
        this.messageTvGravity = gravity;
    }

    public static class Builder {

        private CommonDialog mCurrentDialog;
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
            mCurrentDialog = new CommonDialog(mContext);
        }

        public Builder setTitle(int titleId) {
            mCurrentDialog.setTitle(mContext.getText(titleId));
            return this;
        }

        public Builder setTitle(CharSequence title) {
            mCurrentDialog.setTitle(title);
            return this;
        }

        public Builder setMessage(int messageId) {
            if (messageId != 0)
                mCurrentDialog.setMessage(mContext.getText(messageId));
            return this;
        }

        public Builder setMessage(CharSequence message) {
            mCurrentDialog.setMessage(message);
            return this;
        }

        public Builder setPositiveButton(int textId, OnClickListener listener) {
            mCurrentDialog.setPositiveBtnText(mContext.getText(textId));
            mCurrentDialog.setPositiveListener(listener);
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            mCurrentDialog.setPositiveBtnText(text);
            mCurrentDialog.setPositiveListener(listener);
            return this;
        }

        public Builder setNegativeButton(int textId, OnClickListener listener) {
            mCurrentDialog.setNegativeBtnText(mContext.getText(textId));
            mCurrentDialog.setNegativeListener(listener);
            return this;
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            mCurrentDialog.setNegativeBtnText(text);
            mCurrentDialog.setNegativeListener(listener);
            return this;
        }

        public Builder setNeutralButton(int textId, OnClickListener listener) {
            mCurrentDialog.setNeutralBtnText(mContext.getText(textId));
            mCurrentDialog.setNeutralListener(listener);
            return this;
        }

        public Builder setNeutralButton(CharSequence text, OnClickListener listener) {
            mCurrentDialog.setNeutralBtnText(text);
            mCurrentDialog.setNeutralListener(listener);
            return this;
        }

        public Builder setView(View view) {
            mCurrentDialog.setCommonView(view);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCurrentDialog.setCancelable(cancelable);
            return this;
        }

        public Builder setMessageGravity(int gravity) {
            mCurrentDialog.setMessageGravity(gravity);
            return this;
        }

        public CommonDialog create() {
            return mCurrentDialog;
        }
    }

}
