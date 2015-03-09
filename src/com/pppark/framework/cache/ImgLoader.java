package com.pppark.framework.cache;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pppark.MyApplication;

/**
 * 全局ImageLoader管理
 * @Package com.xunlei.video.framework.cache
 * @ClassName: ImgLoader
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date May 16, 2014 12:29:12 AM
 */
public class ImgLoader {
    
    private static ImageLoader imageLoader;
    private static DisplayImageOptions baseDisplayImageOptions;
    
    /**
     * 获得全局ImgLoader
     * @Title: getImageLoader
     * @return ImageLoader
     * @date May 16, 2014 12:27:42 AM
     */
    public static ImageLoader getImageLoader() {
        if (imageLoader != null) {
            return imageLoader;
        }
        
        initBaseDisplayImageOptions();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyApplication.context)
                .imageDownloader(new BaseImageDownloader(MyApplication.context))
                .defaultDisplayImageOptions(baseDisplayImageOptions)
                .memoryCacheSizePercentage(15)
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        return imageLoader;
    }

    private static void initBaseDisplayImageOptions(){
        if(baseDisplayImageOptions == null){
            baseDisplayImageOptions = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .considerExifParams(false)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();
        }
    }
    
    /**
     * 获得一个全局的DisplayImageOptions.Builder
     * @Title: getBaseDisplayImageOptionsBuilder
     * @return DisplayImageOptions.Builder
     * @date May 16, 2014 12:28:27 AM
     */
    public static DisplayImageOptions.Builder getBaseDisplayImageOptionsBuilder(){
        initBaseDisplayImageOptions();
        return new DisplayImageOptions.Builder().cloneFrom(baseDisplayImageOptions);
    }

}
