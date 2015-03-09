package com.pppark.framework.cache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.Uri;

/**
 * 重写ImageLoader createConnection方法，突破防盗链
 * @Package com.xunlei.video.framework.cache
 * @ClassName: BaseImageDownloader
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 25, 2014 10:27:45 PM
 */
public class BaseImageDownloader extends com.nostra13.universalimageloader.core.download.BaseImageDownloader {
    
    public BaseImageDownloader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        if(url.contains("xlpan.kanimg.com") || url.contains("media.geilijiasu.com")){
            conn.setRequestProperty("Referer", "http://pad.i.vod.xunlei.com");
        }
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }
}