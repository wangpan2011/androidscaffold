package com.pppark.support.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;
import android.view.View;

import com.pppark.R;
import com.pppark.support.widget.CommonDialog;

/**
 * 创建dialog的工具类，创建统一，便于修改样式
 * 
 * @Package com.xunlei.video.support.util
 * @ClassName: DialogUtil
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-4-24 下午2:22:26
 */
public class DialogUtil {
    public static ProgressDialog createProgressDialog(Context context, int titleResId, int msgResId,
            OnCancelListener cancelListener) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(titleResId);
        mProgressDialog.setMessage(context.getString(msgResId));
        if (null != cancelListener)
            mProgressDialog
                    .setOnCancelListener(cancelListener);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(
                R.anim.common_progress_drawable_anim));
        return mProgressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context, String title, String msg,
            OnCancelListener cancelListener) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        if (null != cancelListener)
            mProgressDialog
                    .setOnCancelListener(cancelListener);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(
                R.anim.common_progress_drawable_anim));
        return mProgressDialog;
    }

    public static CommonDialog createAlertDialog(Context context, int titleResId, int msgResId,
            int negativeBtnResId, OnClickListener negativeBtnListener, int positiveBtnResId,
            OnClickListener positiveBtnListener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setTitle(titleResId).setMessage(msgResId).setCancelable(true);
        if (negativeBtnListener != null) {
            builder.setNegativeButton(negativeBtnResId, negativeBtnListener);

        }
        if (positiveBtnListener != null) {
            builder.setPositiveButton(positiveBtnResId, positiveBtnListener);
        }
        CommonDialog dialog = null;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static CommonDialog createAlertDialog(Context context, int titleResId, int msgResId,
            int negativeBtnResId, OnClickListener negativeBtnListener, int positiveBtnResId,
            OnClickListener positiveBtnListener, int neutralBtnResId,
            OnClickListener neutralBtnListener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setTitle(titleResId).setMessage(msgResId).setCancelable(true);
        if (negativeBtnListener != null) {
            builder.setNegativeButton(negativeBtnResId, negativeBtnListener);
        }
        if (positiveBtnListener != null) {
            builder.setPositiveButton(positiveBtnResId, positiveBtnListener);
        }
        if (neutralBtnListener != null) {
            builder.setNeutralButton(neutralBtnResId, neutralBtnListener);
        }
        CommonDialog dialog = null;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    public static CommonDialog createAlertDialog(Context context, String title, CharSequence msg,
            String negativeBtnText, OnClickListener negativeBtnListener, String positiveBtnText,
            OnClickListener positiveBtnListener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setCancelable(true);
        // negative btn一般是取消，可以没有clickListener
        if (!TextUtils.isEmpty(negativeBtnText)) {
            builder.setNegativeButton(negativeBtnText, negativeBtnListener);

        }
        if (positiveBtnListener != null) {
            builder.setPositiveButton(positiveBtnText, positiveBtnListener);
        }
        CommonDialog dialog = null;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static CommonDialog createCustomViewDialog(Context context, int titleResId,
            int negativeBtnTextResId, OnClickListener negativeBtnListener, int positiveBtnTextResId,
            OnClickListener positiveBtnListener, View view) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setTitle(titleResId).setCancelable(true);
        if (negativeBtnListener != null) {
            builder.setNegativeButton(negativeBtnTextResId, negativeBtnListener);
        }
        if (positiveBtnListener != null) {
            builder.setPositiveButton(positiveBtnTextResId, positiveBtnListener);
        }
        if (view != null) {
            builder.setView(view);
        }
        CommonDialog dialog = null;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static CommonDialog createCustomViewDialog(Context context, String title,
            String negativeBtnText, OnClickListener negativeBtnListener, String positiveBtnText,
            OnClickListener positiveBtnListener, View view) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setTitle(title).setCancelable(true);
        if (negativeBtnListener != null) {
            builder.setNegativeButton(negativeBtnText, negativeBtnListener);
        }
        if (positiveBtnListener != null) {
            builder.setPositiveButton(positiveBtnText, positiveBtnListener);
        }
        if (view != null) {
            builder.setView(view);
        }
        CommonDialog dialog = null;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static ProgressDialog createProgressDialog1(Context context, String title, String msg,
            OnDismissListener dismissListener) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(title))
            mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(msg);
        if (null != dismissListener)
            mProgressDialog
                    .setOnDismissListener(dismissListener);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(
                R.anim.common_progress_drawable_anim));
        return mProgressDialog;
    }
}
