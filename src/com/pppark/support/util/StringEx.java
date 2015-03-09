package com.pppark.support.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

public class StringEx {

    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Check the string if it is null or whitespace. String will be trim() if it contains useless whitespace.
     * 
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static String Empty = "";

    public static String insertWhitespace(String src) {
        String newStr = src;
        if (!isNullOrEmpty(src)) {
            if (src.length() == 1) {
                newStr = " " + src + " ";
            } else if (src.length() == 2) {
                newStr = src.substring(0, 1) + " " + src.substring(1);
            }
        }
        return newStr;
    }

    public static String fromHtml(String source) {
        String convertedText = source;// by default;
        if (!StringEx.isNullOrEmpty(source)) {
            try {
                CharSequence cs = Html.fromHtml(source);
                if (cs != null) {
                    convertedText = cs.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertedText;
    }

    /**
     * 获取格式化播放时长字符串[HH:mm:ss]
     * 
     * @param mCurSec
     *            时长(单位：秒)
     * @return HH:mm:ss
     */
    public static String getPlayTimeFormat(int mCurSec) {
        String formattedTime = "00:00:00";
        if (mCurSec == 0) {
            return formattedTime;

        }
        int hour = mCurSec / 60 / 60;
        int leftSec = mCurSec % (60 * 60);
        int min = leftSec / 60;
        int sec = leftSec % 60;
        String strHour = String.format("%d", hour);
        String strMin = String.format("%d", min);
        String strSec = String.format("%d", sec);

        if (hour < 10) {
            strHour = "0" + hour;
        }
        if (min < 10) {
            strMin = "0" + min;
        }
        if (sec < 10) {
            strSec = "0" + sec;
        }
        formattedTime = strHour + ":" + strMin + ":" + strSec;
        return formattedTime;
    }

    public static List<String> split(String input, String divider, boolean removeEmptyItem) throws Exception {
        if (null == input) {
            throw new Exception("Invalid parameter: input is NULL.");
        }
        List<String> resultItems = new ArrayList<String>();
        String[] rawItems = input.split(divider);
        for (int i = 0; i < rawItems.length; i++) {
            if (!removeEmptyItem || !"".equals(rawItems[i].trim())) {
                resultItems.add(rawItems[i]);
            }
        }

        return resultItems;
    }

    public static String toTimeString(Date date) {
        return date != null ? timeFormat.format(date) : "";
    }

    public static String toDateTimeString(Date date) {
        return date != null ? dateTimeFormat.format(date) : "";
    }

    public static String join(String[] strs) {
        String delimiter = " ";

        return join(strs, delimiter);
    }

    public static String join(String[] strs, String delimiter) {
        StringBuilder sBuilder = null;
        if (strs != null) {
            sBuilder = new StringBuilder();
            for (int i = 0; i < strs.length; i++) {
                sBuilder.append(strs[i]).append(delimiter);
            }
            if (sBuilder.length() > 0) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            return sBuilder.toString();
        }

        return sBuilder == null ? null : sBuilder.toString();
    }

    /**
     * md5加密
     * 
     * @Title md5
     * @param string
     * @return String
     * @date 2013-11-20 上午10:21:01
     */
    public static String md5(String string) {
        byte[] hash = new byte[0];

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 去掉字符串中的空格 制表符
     * 
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
