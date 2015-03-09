package com.pppark.framework.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.pppark.framework.data.BasePo;

/**
 * HolderViewAdapter
 * 
 * @Package com.xunlei.video.framework
 * @ClassName: HolderViewAdapter
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 16, 2014 3:33:28 PM
 */
public class HolderViewAdapter extends android.widget.BaseAdapter {

    private Context context;
    private Class<? extends BaseHolderView>[] holderViews;
    protected List<? extends BasePo> data;

    public HolderViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * HolderViewAdapter
     * @Title: HolderViewAdapter
     * @param context
     * @param data
     * @param holderViews，如多视图类型，需重写getItemViewType方法
     * @date Apr 25, 2014 5:54:14 PM
     */
    public HolderViewAdapter(Context context, List<? extends BasePo> data,
            Class<? extends BaseHolderView>... holderViews) {
        this.context = context;
        this.holderViews = holderViews;
        this.data = data;
    }

    public void setHolderViews(Class<? extends BaseHolderView>... holderViews) {
        this.holderViews = holderViews;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setHolderViews(Class<? extends BaseHolderView> holderView) {
        Class[] views = new Class[1];
        views[0] = holderView;
        this.holderViews = views;
    }

    public void setData(List<? extends BasePo> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data != null ? data.size() : 0;
    }

    @Override
    public BasePo getItem(int position) {
        // TODO Auto-generated method stub
        return data != null && data.size() > position ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return holderViews != null ? holderViews.length : 1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        BaseHolderView holderView = null;
        if (convertView != null &&
                convertView.getClass().getName().equals(this.holderViews[getItemViewType(position)].getName())) {
            
            holderView = (BaseHolderView) convertView;
        } else {

            try {
                holderView = (BaseHolderView) this.holderViews[getItemViewType(position)].getConstructor(Context.class)
                        .newInstance(this.context);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if(getItem(position) != null)
            holderView.bindData(getItem(position), position);

        return holderView;
    }

}
