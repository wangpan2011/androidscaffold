package com.pppark.support.util;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.nostra13.universalimageloader.utils.L;
/**
 * 文件相关的工具类
 * @author Administrator
 *
 */
public class FileUtil {
	private final String TAG = FileUtil.class.getSimpleName();
	private final static String DIR_CACHE = "app_data";//接口数据缓存
	
	/**
     * 得到pic路径
     * 1、有sdcard返回Android/data/com..../app_data目录
     * 2、无sdcard返回data/data/com.../app_data目录
     * 
     **/
    public static File getDirCache(Context pContext){
        if(isSDCardWritable()){
            return pContext.getExternalFilesDir(DIR_CACHE);
        }else{
            return getDirInner(pContext, DIR_CACHE);
        }
    }
    //每次退出都清空缓存
    public static void clearCache(Context pContext){
        //删除内部cache
        deleteDirInner(pContext, DIR_CACHE);
        //删除sdcard上的cache
        deleteDirCache(pContext);
    }
	
	
	
	
	
	/**
	 * sdcard是否被移除
	 * 
	 * @Title isExternalStorageRemovable
	 * @return boolean
	 * @date 2013-12-6 下午4:33:00
	 */
	@TargetApi(9)
    private static boolean isExternalStorageRemovable() {
        if (hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }
	
	private static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }
	
	/**
	 * 判断sd卡读写能力
	 * @return
	 */
	private static boolean isSDCardWritable(){
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !isExternalStorageRemovable()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据dir名字 得到文件
	 * 存在 返回
	 * 不存在 创建
	 * 
	 * 
	 * @Title getDir
	 * @param pDir
	 * @return File
	 * @date 2013-12-6 下午4:52:02
	 */
	private static File getDirInner(Context pContext, String pDir){
		File tFile = null;
		String tRoot = pContext.getFilesDir().getParent();
		if(tRoot!=null && !"".equals(tRoot)){
		    String tDir = tRoot + File.separator + pDir;
			if(createDir(tDir)){
				tFile = new File(tDir);
			}else{
			}
		}else{
		}
		return tFile;
	}
	/**
	 * 根据文件夹名字 创建文件夹
	 * @param dir
	 * @return
	 */
	private static boolean createDir(String pDir) {
		boolean result = false;
		if(pDir!=null && !"".equals(pDir)){
			File folder = new File(pDir);
			if (!folder.exists()) {
				if (folder.mkdirs()) {
					result = true;
				} else {
					result = false;
				}
			}else{
				result = true;
			}
		}
		return result;
	}
	
	private static void deleteDirInner(Context pContext, String pDir){
        String tRoot = pContext.getFilesDir().getParent() + File.separator + pDir;
        deleteDir(new File(tRoot));
    }
    private static void deleteDirCache(Context pContext){
        if(isSDCardWritable()){
            File tDir = pContext.getExternalFilesDir(DIR_CACHE);
            deleteDir(tDir);
        }
    }
    private static void deleteDir(File pDir){
        if(pDir.exists()){
            String[] files = pDir.list();
            if(files!=null && files.length>0){
                File file;
                for(int i=0; i<files.length; i++){
                    file = new File(pDir, files[i]);
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
        }
    }
}
	
