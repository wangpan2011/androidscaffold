package com.pppark.framework.view;

import android.content.Context;
import android.widget.Checkable;

/**
 * 支持多选模式的BaseHolderView
 * @Package com.xunlei.video.framework
 * @ClassName: BaseMultiChoiceHolderView
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date May 20, 2014 12:30:23 AM
 */
public abstract class BaseMultiChoiceHolderView extends BaseHolderView implements Checkable{
    
    private boolean mChecked;

    public BaseMultiChoiceHolderView(Context context, int layoutResId) {
        super(context, layoutResId);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isChecked() {
        // TODO Auto-generated method stub
        return mChecked;
    }
    
    @Override
    public void setChecked(boolean checked) {
        // TODO Auto-generated method stub
        if (checked != mChecked) {
            mChecked = checked;
            onCheckStateChange(mChecked);
        }
    }

    @Override
    public void toggle() {
        // TODO Auto-generated method stub
        setChecked(!isChecked());
    }
    
    /**
     * check状态发生变化
     * @Title: onCheckStateChange
     * @param isChecked
     * @return void
     * @date May 20, 2014 12:45:21 AM
     */
    abstract protected void onCheckStateChange(boolean isChecked);
    
    /**
     * 多选模式发生变化
     * @Title: onCheckModeChange
     * @param isOpenMode
     * @return void
     * @date May 20, 2014 12:45:21 AM
     */
    abstract protected void onCheckModeChange(boolean isOpenMode);

}
