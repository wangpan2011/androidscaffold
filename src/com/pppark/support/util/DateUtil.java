package com.pppark.support.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.pppark.framework.logging.Log;

import android.annotation.SuppressLint;


/**
 * 日期处理类
 * 
 * @Package com.xunlei.video.support.util
 * @ClassName: DateUtil
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-4-28 下午1:38:27
 */
public class DateUtil {
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return format.format(new Date());
    }

    public static String getMillTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return format.format(new Date());
    }

    public static String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date());
    }

    public static String getShortDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        return format.format(new Date());
    }

    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            Log.e(e);
            return null;
        }
    }

    public static String formatDateStr(String dateStr, String currentFormat, String targetFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(targetFormat, Locale.getDefault());
        return sdf.format(str2Date(dateStr, currentFormat));
    }

    public static String formatDate(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String format_time = sdf.format(new Date(time / 1000));
        return format_time;
    }
    
    /**
     * 
     * 获取两个时间的间隔天数
     * @Title: daysBetween
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     * @return int
     * @date 2014年5月20日 上午11:51:32
     */
    @SuppressLint("SimpleDateFormat")
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
