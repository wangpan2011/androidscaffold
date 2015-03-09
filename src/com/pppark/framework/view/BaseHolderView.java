package com.pppark.framework.view;

import android.content.Context;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pppark.framework.cache.ImgLoader;
import com.pppark.framework.data.BasePo;

/**
 * 替代ViewHolder，优化效率的同时解放getView方法
 * 
 * @Package com.xunlei.video.framework
 * @ClassName: BaseHolderView
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 16, 2014 2:56:17 PM
 */
public abstract class BaseHolderView extends RelativeLayout {
    
    private Context mContext;

    /**
     * 做了几件事：<br>
     * 1、inflate<br>
     * 2、injectView
     * @Title: BaseHolderView
     * @param context
     * @param layoutResId item布局ID
     * @date Apr 25, 2014 4:18:10 PM
     */
    public BaseHolderView(Context context, int layoutResId) {
        super(context);
        
        this.mContext = context;
        
        inflate(this.mContext, layoutResId, this);
        
        ButterKnife.inject(this);
    }

    /**
     * 获得全局统一ImageLoader
     * 
     * @Title: getImageLoader
     * @return ImageLoader
     * @date Apr 16, 2014 3:46:19 PM
     */
    protected ImageLoader getImageLoader() {
        return ImgLoader.getImageLoader();
    }

    /**
     * 绑定数据对象，在此之前已通过bufferknife初始化控件
     * 
     * @Title: bindData
     * @param po
     * @return void
     * @date Apr 16, 2014 2:55:46 PM
     */
    abstract protected void bindData(BasePo po, int position);
}
