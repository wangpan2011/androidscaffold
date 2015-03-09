package com.pppark.support.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;

import com.pppark.framework.logging.Log;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class PhoneUtil {
    
    private static String mPeerId = null;
    private static String mImei = null;
    private static boolean mCustomPlayerDeviceSupported = false;
    private static boolean mCustomPlayerDeviceSupportedConsumed = false;

    public static String getPeerid(final Context context) {
        if (null == mPeerId && null != context) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (null != wm && null != wm.getConnectionInfo()) {
                String mac = wm.getConnectionInfo().getMacAddress();
                mac += "004V";
                mPeerId = mac.replaceAll(":", "");
                mPeerId = mPeerId.replaceAll(",", "");
                mPeerId = mPeerId.replaceAll("[.]", "");
                mPeerId = mPeerId.toUpperCase();
            }
        }
        return mPeerId;
    }
    
    private static String mUnicomPeerId = null;

    public static String getUnicomPeerId(Context context) {
        if (null == mUnicomPeerId) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            mUnicomPeerId = tm.getDeviceId();
            if (null == mUnicomPeerId || mUnicomPeerId.length() < 2) {
                mUnicomPeerId = Secure.getString(
                        context.getContentResolver(),
                        Secure.ANDROID_ID); // *** use for tablets
            }
        }
        return mUnicomPeerId;
    }

    public static String getImeiId(final Context context) {
        if (null == mImei && null != context) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                mImei = tm.getDeviceId();
            }
            if (null == mImei) {
                mImei = "000000000000000";
            }
        }
        return mImei;
    }

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return verName;

    }

    public static byte[] getMD5Byte(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return messageDigest.digest();
    }
    public static String getMD5Str(String str){
        byte[] byteArray = getMD5Byte(str);
        if (byteArray == null) {
            return "";
        }
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString().toLowerCase();
    }

    // 压缩
    public static byte[] compress(byte[] str) throws IOException {
        if (str == null || str.length == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str);
        gzip.flush();
        gzip.close();
        return out.toByteArray();
    }

    /**
     * 数据解压缩
     * 
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os)
            throws IOException {
        int BUFFER = 1024;
        GZIPInputStream gis = new GZIPInputStream(is);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }

    /**
     * 数据解压缩
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String decompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 解压缩
        decompress(bais, baos);
        String s = baos.toString("utf-8");
        baos.flush();
        baos.close();
        bais.close();

        return s;
    }

    public static byte[] encryptAES(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static byte[] decryptAES(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    
    
    
    private static final long BASE_B = 1L;
    private static final long BASE_KB = 1024L;
    private static final long BASE_MB = 1024L * 1024L;
    private static final long BASE_GB = 1024L * 1024L * 1024L;
    private static final long BASE_TB = 1024L * 1024L * 1024L * 1024L;
    // private static final long BASE_PB = 1024L * 1024L * 1024L * 1024L* 1024L;
    private static final String UNIT_BIT = "B";
    private static final String UNIT_KB = "K";
    private static final String UNIT_MB = "M";
    private static final String UNIT_GB = "G";
    private static final String UNIT_TB = "T";
    private static final String UNIT_PB = "P";
    public static String convertFileSize(long file_size, int precision) {
        long int_part = 0;
        double fileSize = file_size;
        double floatSize = 0L;
        long temp = file_size;
        int i = 0;
        double base = 1;
        String baseUnit = "M";
        String fileSizeStr = null;
        int indexMid = 0;

        while (temp / 1024 > 0) {
            int_part = temp / 1024;
            temp = int_part;
            i++;
        }
        switch (i) {
        case 0:
            // B
            base = BASE_B;
            floatSize = fileSize / base;
            baseUnit = UNIT_BIT;
            break;

        case 1:
            // KB
            base = BASE_KB;
            floatSize = fileSize / base;
            baseUnit = UNIT_KB;
            break;

        case 2:
            // MB
            base = BASE_MB;
            floatSize = fileSize / base;
            baseUnit = UNIT_MB;
            break;

        case 3:
            // GB
            base = BASE_GB;
            floatSize = fileSize / base;
            baseUnit = UNIT_GB;
            break;

        case 4:
            // TB
            // base = BASE_TB;
            floatSize = (fileSize / BASE_GB) / BASE_KB;
            baseUnit = UNIT_TB;
            break;
        case 5:
            // PB
            // base = BASE_PB;
            floatSize = (fileSize / BASE_GB) / BASE_MB;
            baseUnit = UNIT_PB;
            break;
        default:
            break;
        }

        // Log.d("filesize", "fileSize="+fileSize+",base="+base);

        fileSizeStr = Double.toString(floatSize);
        if (precision == 0) {
            indexMid = fileSizeStr.indexOf('.');
            if (-1 == indexMid) {
                return fileSizeStr + baseUnit;
            }
            return fileSizeStr.substring(0, indexMid) + baseUnit;
        }

        try {
            BigDecimal b = new BigDecimal(Double.toString(floatSize));
            BigDecimal one = new BigDecimal("1");
            double dou = b.divide(one, precision, BigDecimal.ROUND_HALF_UP).doubleValue();
            return dou + baseUnit;
        } catch (Exception e) {
            indexMid = fileSizeStr.indexOf('.');
            if (-1 == indexMid) {
                // �ַ���û��������ַ�
                return fileSizeStr + baseUnit;
            }
            // Util.log(TAG, fileSizeStr);
            // Util.log(TAG, ""+indexMid + "  "+precision);
            if (fileSizeStr.length() <= indexMid + precision + 1) {
                return fileSizeStr + baseUnit;
            }
            if (indexMid < 3) {
                indexMid += 1;
            }
            if (indexMid + precision < 6) {
                indexMid = indexMid + precision;
            }
            return fileSizeStr.substring(0, indexMid) + baseUnit;
        }
    }

    // 更新:设备是否支持内部播放架构,通过调用isCustomPlayerDeviceSupported()方法来获取
    public static boolean updateCustomPlayerDeviceSupported() {

        boolean versionSupported = false;
        boolean cpuAbiArmV7aSupported = false;
        boolean cpuAbiX86Supported = false;
        boolean cpuFreqSupported = false;
        boolean neonSupported = false;

        // 判断系统版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            versionSupported = true;
            // 判断CPU_ABI
            if (Build.CPU_ABI.toLowerCase().equals("armeabi-v7a")) {
                cpuAbiArmV7aSupported = true;
                // 判断NENO
                File fileCpuInfo = new File("/proc/cpuinfo");
                if (fileCpuInfo.exists()) {
                    StringWriter writer = new StringWriter();
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(fileCpuInfo);
                        IOUtils.copy(in, writer);
                        String info = writer.toString();
                        neonSupported = info.toLowerCase().contains("neon");
                    } catch (IOException e) {
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
                // 判断CPU_FREQ
                File fileCpuInfoFreq = new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
                if (fileCpuInfoFreq.exists()) {
                    StringWriter writer = new StringWriter();
                    FileInputStream in = null;
                    try {
                        in = new FileInputStream(fileCpuInfoFreq);
                        IOUtils.copy(in, writer);
                        String info = writer.toString();
                        int freq = Integer.parseInt(info.toLowerCase().trim());
                        freq += 999;
                        freq /= 1000;
                        if (freq >= 800)
                            cpuFreqSupported = true;
                    } catch (IOException e) {
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            } else if (Build.CPU_ABI.toLowerCase().equals("x86")) {
                cpuAbiX86Supported = isX86Supported();
            }
        }

        Log.d("updateCustomPlayerDeviceSupported versionSupported = " + versionSupported);
        Log.d("updateCustomPlayerDeviceSupported cpuAbiArmV7aSupported = " + cpuAbiArmV7aSupported);
        Log.d("updateCustomPlayerDeviceSupported cpuAbiX86Supported = " + cpuAbiX86Supported);
        Log.d("updateCustomPlayerDeviceSupported neonSupported = " + neonSupported);
        Log.d("updateCustomPlayerDeviceSupported cpuFreqSupported = " + cpuFreqSupported);
        mCustomPlayerDeviceSupported = (versionSupported && ((cpuAbiArmV7aSupported && neonSupported) || cpuAbiX86Supported));
        return mCustomPlayerDeviceSupported;

    }
    
    // 设备是否支持内部播放架构
    public static boolean isCustomPlayerDeviceSupported(){
        if(!mCustomPlayerDeviceSupportedConsumed){
            mCustomPlayerDeviceSupportedConsumed = true;
            updateCustomPlayerDeviceSupported();
        }
        return mCustomPlayerDeviceSupported;
    }
    
    // 发布包中是否有对X86的支持
    public static boolean isX86Supported() {
        
        boolean supported = false;
        ZipInputStream zip = null;
        try {
            zip = new ZipInputStream(new FileInputStream(SystemUtil.getPackageSourceDir()));
            ZipEntry entry = null;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("lib/x86/")) {
                    supported = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return supported;
        
    }
    
    
    public static String getGBSize(long fileSize) {
        double floatSize = 0L;
        String result = null;
        floatSize = (double) fileSize / BASE_GB;
        if (floatSize - 1024 > 0) {
            floatSize = floatSize / 1024l;
            result = new java.text.DecimalFormat("0.00").format(floatSize) + "T";
        } else {
            result = new java.text.DecimalFormat("0.0").format(floatSize) + "G";
        }

        return result/* Double.toString(floatSize) */;
    }

    public static String getMBSize(long fileSize) {
        double floatSize = 0L;
        String result = null;
        floatSize = (double) fileSize / BASE_MB;
        if (floatSize - 1024 > 0) {
            floatSize = floatSize / 1024l;
            result = new java.text.DecimalFormat("0.00").format(floatSize) + "G";
        } else {
            result = new java.text.DecimalFormat("0.0").format(floatSize) + "MB";
        }

        return result/* Double.toString(floatSize) */;
    }
    
}
