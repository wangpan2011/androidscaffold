package com.pppark.support.util;

/**
 * 文件大小格式化
 * @Package com.xunlei.video.support.util
 * @ClassName: FileSizeFormater
 * @author likai
 * @mail likai@xunlei.com
 * @date 2014-5-14 上午10:51:46
 */
public class FileSizeFormater {

    private static final int BASE_B = 1;
    private static final int BASE_KB = BASE_B * 1024;
    private static final int BASE_MB = BASE_KB * 1024;
    private static final int BASE_GB = BASE_MB * 1024;

    private static final String UNIT_BIT = "B";
    private static final String UNIT_KB = "KB";
    private static final String UNIT_MB = "M";
    private static final String UNIT_GB = "G";
    
    /**
     * 默认保留两位小数
     * @Title: formatSize
     * @param size
     * @return
     * @return String
     * @date 2014-5-14 上午10:52:10
     */
    public static String formatSize(long size) {
        return formatSize(size, 2);
    }
    
    /**
     * 自定义精确度
     * @Title: formatSize
     * @param size
     * @param precision
     * @return
     * @return String
     * @date 2014-5-14 上午10:52:32
     */
    public static String formatSize(long size, int precision) {
        String formater = "%."+precision+"f";
        if (size < BASE_KB) {
            return String.format(formater, (float) size) + UNIT_BIT;
        } else if (size < BASE_MB) {
            return String.format(formater, (float) size / BASE_KB) + UNIT_KB;
        } else if (size < BASE_GB) {
            return String.format(formater, (float) size / BASE_MB) + UNIT_MB;
        } else {
            return String.format(formater, (float) size / BASE_GB) + UNIT_GB;
        }
    }
}
