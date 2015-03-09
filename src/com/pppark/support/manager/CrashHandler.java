package com.pppark.support.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.pppark.MyApplication;
import com.pppark.framework.logging.Logger;
import com.pppark.support.util.NetUtil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;


public class CrashHandler implements UncaughtExceptionHandler {
    private static final Logger LOG = Logger.getLogger(CrashHandler.class);

    public static final String LOG_PATH = "/CLOUDPLAY/LOG_CRASH/";

    private DateFormat formatter = new SimpleDateFormat("yyMMdd-HHmmss", Locale.getDefault());
    private DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.getDefault());

    private static CrashHandler crashHandler = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler; 
    private Map<String, String> mDeviceCrashInfo = new LinkedHashMap<String, String>();
    private static final String VERSION_NAME = "versionName"; 
    private static final String VERSION_CODE = "versionCode"; 
    private static final String VERSION_SDK_INT = "version_sdk_int"; 
    private static final String VERSION_RELEASE = "version_release"; 
    private static final String NET_TYPE = "net_type"; 
    private static final String NET_INFO = "net_info"; 

    private CrashHandler() {}

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }
    
    public void init(){
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); 
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        
        collectCrashDeviceInfo(MyApplication.context);
        saveCrashInfo2File(ex);
        
        if(mDefaultHandler != null){
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private void saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        Date date = new Date();
        String time = formatter1.format(date);
        sb.append("#Video Crash Log\n");
        sb.append("#");
        sb.append(time);
        sb.append("\n");

        String result = writer.toString();
        sb.append(result);
        
        sb.append("\n------------Device info------------\n");
        for (Map.Entry<String, String> entry : mDeviceCrashInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        try {
            String fileName = "crash-" + formatter.format(date) + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + LOG_PATH;
                (new File(path)).mkdirs();

                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            LOG.e("an error occured while writing file...", e);
        }
    }
    
    public void collectCrashDeviceInfo(Context ctx) { 
        try { 
            PackageManager pm = ctx.getPackageManager(); 
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 
                    PackageManager.GET_ACTIVITIES); 
            if (pi != null) { 
                mDeviceCrashInfo.put(VERSION_NAME, 
                        pi.versionName == null ? "not set" : pi.versionName); 
                mDeviceCrashInfo.put(VERSION_CODE, ""+pi.versionCode); 
                mDeviceCrashInfo.put(VERSION_RELEASE, ""+android.os.Build.VERSION.RELEASE);
                mDeviceCrashInfo.put(VERSION_SDK_INT, ""+android.os.Build.VERSION.SDK_INT);
                if(NetUtil.isWifiNetWork(ctx)){
                    mDeviceCrashInfo.put(NET_TYPE, "WIFI");
                }else if(NetUtil.isMobileNetWrok(ctx)){
                    ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    mDeviceCrashInfo.put(NET_TYPE, "GPRS");
                    mDeviceCrashInfo.put(NET_INFO, netInfo.toString());
                }
            } 
        } catch (NameNotFoundException e) { 
        } 
        //使用反射来收集设备信息.在Build类中包含各种设备信息, 
        //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息 
        //具体信息请参考后面的截图 
        Field[] fields = Build.class.getDeclaredFields(); 
        for (Field field : fields) { 
            try { 
                field.setAccessible(true); 
                mDeviceCrashInfo.put(field.getName(), ""+field.get(null)); 
            } catch (Exception e) { 
            } 
        } 
    }
}
