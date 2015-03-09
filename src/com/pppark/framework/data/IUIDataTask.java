package com.pppark.framework.data;

import com.pppark.framework.data.DataTask.DataTaskListener;

/**
 * 需在UI界面中实现的DataTask接口，实现与UI生命周期绑定
 * @Package com.xunlei.video.framework.data
 * @ClassName: IUIDataTask
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 18, 2014 9:55:00 AM
 */
public interface IUIDataTask {

    DataTask newDataTask(DataTaskListener dataTaskListener);
    
}
