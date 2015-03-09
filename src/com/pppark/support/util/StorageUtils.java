package com.pppark.support.util;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * 存储相关
 * @Package com.xunlei.video.support.util
 * @ClassName: StorageUtils
 * @author likai
 * @mail likai@xunlei.com
 * @date 2014-5-28 下午2:38:05
 */
public class StorageUtils {
    public static long getSDCardAvailaleSize() {
        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
}
