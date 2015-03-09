package com.pppark.support.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.pppark.framework.logging.Log;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class NetUtil {

    private static boolean hasNet = false;

    /**
     * 是否连接到网络
     * 
     * @Title: isNetworkConnected
     * @return boolean
     * @date May 16, 2014 6:38:57 PM
     */
    public static boolean isNetworkConnected() {
        return hasNet;
    }

    public static void setHasNet(boolean hasNet) {
        NetUtil.hasNet = hasNet;
        Log.d("[ NetUtil ] Net status changed, hasNet = {}", hasNet);
    }

    // --------------手机网络部分------------------------------------------------
    // 当前无网络
    public static final int NETWORKINFO_INVALID = 0;
    // wap网络状态
    public static final int NETWORKINFO_MOBILE_WAP = 1;
    // net网络
    public static final int NETWORKINFO_MOBILE_NET = 2;
    // wifi网络
    public static final int NETWORKINFO_WIFI = 3;

    /** 验证当前是否有网络 */
    public static boolean checkNetWrokAvailable(Context context) {
        if (context != null) {
            NetworkInfo nwInfo = getNetWorkInfo(context);
            if (nwInfo != null) {
                return true;
            }
        }
        return false;
    }

    /** 启动设置网络连接界面 */
    public static boolean startNewWorkConnection(Context context) {
        if (checkNetWrokAvailable(context)) {
            return true;
        }
        else {// 假如没有可用网络
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
    }

    /** 判断当前网络是否是wifi连接网络 */
    public static boolean isWifiNetWork(Context context) {
        if (getNetWorkType(context) == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        else {
            return false;
        }
    }

    /** 判断是否是wap网络 */
    public static boolean isWapNetWork(Context context) {
        Log.i("UtilUnicom", "PhoneWrap.IsWapNetWork. check IsWapNetWork:" + getCurrentApn(context));
        if (getCurrentApn(context) == NETWORKINFO_MOBILE_WAP) {
            TelephonyManager telmanager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int typ = telmanager.getNetworkType();
            String type = "";
            if (typ == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "EDGE"; // 2.75G
            }
            if (typ == TelephonyManager.NETWORK_TYPE_GPRS) {
                type = "GPRS"; // 2G
            }
            if (typ == TelephonyManager.NETWORK_TYPE_UMTS) {
                type = "UTMS"; // 3G
            }
            if (typ == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                type = "UNKNOWN";
            }
            if (type == "UTMS") {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /** 判断是否是Net网络 */
    public static boolean isNetNetwork(Context context) {
        if (getCurrentApn(context) == NETWORKINFO_MOBILE_NET) {
            return true;
        }
        else {
            return false;
        }
    }

    // 是否为联通网络
    public static boolean isUnicom(Context context) {
        NetworkInfo netWorkInfo = getNetWorkInfo(context);
        if (netWorkInfo != null) {
            String tStr = netWorkInfo.getExtraInfo();
            if (netWorkInfo.getState() == NetworkInfo.State.CONNECTED
                    && tStr != null
                    && (tStr.toLowerCase().contains("3gnet") || tStr.toLowerCase().contains("uninet"))) {
                return true;
            }
        }
        return false;
    }

    /** 得到当前网络的APN */
    private static int getCurrentApn(Context context) {
        NetworkInfo netWorkInfo = getNetWorkInfo(context);
        if (netWorkInfo != null) {
            // cmwap, uniwap, cmnet, 3gnet
            // TODO 还没有考虑电信的卡是否区分wap和net
            if (netWorkInfo.getState() == NetworkInfo.State.CONNECTED
                    && netWorkInfo.getExtraInfo() != null
                    && netWorkInfo.getExtraInfo().toLowerCase().endsWith("wap")) {
                return NETWORKINFO_MOBILE_WAP;
            }
            else {
                return NETWORKINFO_MOBILE_NET;
            }
        }
        else {
            return NETWORKINFO_INVALID;
        }
    }

    /** 判断当前网络是否是手机Gprs连接网络 */
    public static boolean isMobileNetWrok(Context context) {
        if (getNetWorkType(context) == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        else {
            return false;
        }
    }

    /** 得到网络类型，可以判断是wifi，还是mobile连接 */
    private static int getNetWorkType(Context context) {
        NetworkInfo nw = getNetWorkInfo(context);
        if (nw != null) {
            return nw.getType();
        }
        else {
            return -1;
        }
    }

    /** 得到网络信息 */
    public static NetworkInfo getNetWorkInfo(Context context) {
        ConnectivityManager connectMgr = getConnectManager(context);
        if (connectMgr == null) {
            return null;
        }
        else {
            return connectMgr.getActiveNetworkInfo();
        }
    }

    /** 得到网络管理对象 */
    private static ConnectivityManager getConnectManager(Context context) {
        ConnectivityManager connectMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectMgr;
    }

    // ----------手机IMEI-----------------------------------------
    /** 手机IMEI */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /** 手机sim卡号 */
    public static String getSIM(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getSimSerialNumber();
        return imei;
    }

    // 获取ip地址
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

}
