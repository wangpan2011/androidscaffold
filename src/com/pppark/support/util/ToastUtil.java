package com.pppark.support.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * ToastUtil
 * @Package com.xunlei.video.business.util
 * @ClassName: ToastUtil
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-4-21 下午5:31:34
 */
public class ToastUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void showToast(Context ctx, int resId) {
        String text = ctx.getText(resId).toString();
        showToast(ctx, text);
    }

    public static void showToast(Context ctx, int resId, int gravity) {
        String text = ctx.getText(resId).toString();
        showToast(ctx, text, gravity);
    }

    public static void showToast(Context ctx, int resId, int gravity, int offsetX, int offsetY) {
        String text = ctx.getText(resId).toString();
        showToast(ctx, text, gravity, offsetX, offsetY);
    }

    public static void showToast(Context ctx, String message, int gravity) {
        showToast(ctx, message, gravity, 0, 0);
    }
    
    public static void showToast(final Context ctx, final String message) {
        sHandler.post(new Runnable(){

            @Override
            public void run() {
                Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
                toast.show();
            }
            
        });
    }

    public static void showToast(final Context ctx, final String message, final int gravity, final int offsetX, final int offsetY) {
        sHandler.post(new Runnable(){

            @Override
            public void run() {
                Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
                toast.setGravity(gravity, offsetX, offsetY);
                toast.show();
            }
            
        });
    }
}
