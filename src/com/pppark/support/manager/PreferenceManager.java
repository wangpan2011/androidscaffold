package com.pppark.support.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.pppark.MyApplication;

/**
 * 全局SharedPreference管理
 * 
 * @Package com.xunlei.video.support.util
 * @ClassName: PreferenceManager
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 21, 2014 11:27:19 PM
 */
public class PreferenceManager {

    private static final SharedPreferences SHARED_PREFS = MyApplication.context.getSharedPreferences("xl_share_sp",
            Context.MODE_PRIVATE);

    public static String getString(String key,
            final String defaultValue) {
        return SHARED_PREFS.getString(key, defaultValue);
    }

    public static void setString(final String key,
            final String value) {
        SHARED_PREFS.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(final String key,
            final boolean defaultValue) {
        return SHARED_PREFS.getBoolean(key, defaultValue);
    }

    public static void setBoolean(final String key,
            final boolean value) {
        SHARED_PREFS.edit().putBoolean(key, value).commit();
    }

    public static void setInt(final String key,
            final int value) {
        SHARED_PREFS.edit().putInt(key, value).commit();
    }

    public static int getInt(final String key,
            final int defaultValue) {
        return SHARED_PREFS.getInt(key, defaultValue);
    }

    public static void setFloat(final String key,
            final float value) {
        SHARED_PREFS.edit().putFloat(key, value).commit();
    }

    public static float getFloat(final String key,
            final float defaultValue) {
        return SHARED_PREFS.getFloat(key, defaultValue);
    }

    public static void setLong(final String key,
            final long value) {
        SHARED_PREFS.edit().putLong(key, value).commit();
    }

    public static long getLong(final String key,
            final long defaultValue) {
        return SHARED_PREFS.getLong(key, defaultValue);
    }
}
