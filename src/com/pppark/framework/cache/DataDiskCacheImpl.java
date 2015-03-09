package com.pppark.framework.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.text.TextUtils;

import com.pppark.MyApplication;
import com.pppark.support.manager.PreferenceManager;
import com.pppark.support.util.FileUtil;
import com.pppark.support.util.PhoneUtil;

public class DataDiskCacheImpl extends ADataDiskCache {
    private final static String SHAREPREFERENCE_LASTMODIFIED = "lastModified";
    private String mKey;
    
    public DataDiskCacheImpl(String pUrl) {
        super(pUrl);
    }

    private final static String ENCRYPT_STR = "xunleiyingyin";

    @Override
    String getDataKey() {
        if(TextUtils.isEmpty(mKey)){
            long userid=0;
            //需要获取userId 暂时以0处理
//            if (UserManager.getInstance().isLogin()){
//                userid = UserManager.getInstance().getUser().userID;
//            }
                
            mKey = PhoneUtil.getMD5Str(Long.toString(userid) + mUrl);
        }
        return mKey;
    }

    @Override
    public void saveToCache(String pData) {
        File pDir = FileUtil.getDirCache(MyApplication.context);
        if(pDir!=null){
            try {
                FileOutputStream fos = new FileOutputStream(new File(pDir, getDataKey()));
                byte[] asedata = null;
                byte[] md5byte = null;
                try {
                    byte[] compressdata = PhoneUtil.compress(pData.getBytes("utf-8"));
                    md5byte = PhoneUtil.getMD5Byte(ENCRYPT_STR);
                    asedata = PhoneUtil.encryptAES(md5byte, compressdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fos.write(asedata);
                fos.flush();
                fos.close();
                setLoaded();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFromCache() {
        String decompressData = null;
        File pDir = FileUtil.getDirCache(MyApplication.context);
        if(pDir!=null){
            FileInputStream fis=null;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            try {
                fis = new FileInputStream(new File(pDir, getDataKey()));
                while ((len = fis.read(buff, 0, 1024)) > 0) {
                    swapStream.write(buff, 0, len);
                }
                byte[] getserverdata = swapStream.toByteArray();    
                byte[] md5byte = PhoneUtil.getMD5Byte(ENCRYPT_STR);
                byte[] decryptdata = PhoneUtil.decryptAES(md5byte,getserverdata);
                decompressData = PhoneUtil.decompress(decryptdata);
                swapStream.flush();
                swapStream.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decompressData;
    }

    @Override
    public String getLastModified() {
        //从sharedpreferences中取，默认w为0
        return PreferenceManager.getString(SHAREPREFERENCE_LASTMODIFIED, "0");
    }

    @Override
    public void saveLastModified(String pLastModified) {
        //保存到sharedpreferences
        PreferenceManager.setString(SHAREPREFERENCE_LASTMODIFIED, pLastModified);
    }

    @Override
    public boolean isLoaded() {
        return  mArrFlags.get(getDataKey())!=null && mArrFlags.get(getDataKey());
    }

    @Override
    public void setLoaded() {
        mArrFlags.put(getDataKey(), true);
    }

}
