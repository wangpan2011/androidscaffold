package com.pppark.support.util;

import com.pppark.MyApplication;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SystemUtil {

    private static Context mContext = MyApplication.context;
    private static DisplayMetrics mDm;

    // 获取包的版本名称
    public static String getPackageVersion() {
        String verName = "";
        try {
            verName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }

    // 获取系统简略信息
    public static String getSimpleSystemInfo() {
        String sdk_ver;
        sdk_ver = android.os.Build.VERSION.SDK;
        sdk_ver += "_";
        sdk_ver += android.os.Build.MANUFACTURER;
        sdk_ver += "_";
        sdk_ver += android.os.Build.MODEL;
        return sdk_ver;
    }

    // 获取IMEI号
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (null == imei) {
            imei = "000000000000000";
        }
        return imei;
    }

    public static DisplayMetrics getScreenSize() {
        if (null == mDm) {
            mDm = new DisplayMetrics();
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(mDm);
        }
        return mDm;
    }

    // 获取包源代码路径
    public static String getPackageSourceDir() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).applicationInfo.sourceDir;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
