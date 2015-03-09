package com.pppark.support.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.internal.widget.ScrollingTabContainerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.pppark.R;
import com.pppark.framework.BaseActivity;


/**
 * 处理跟view有关的工具类
 * 
 * @Package com.xunlei.video.support.util
 * @ClassName: ViewUtil
 * @author mayinquan
 * @mail mayinquan@xunlei.com
 * @date 2014-4-24 下午2:04:00
 */
public class ViewUtil {
    public static void hideSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void showSoftKeyboard(View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
    }
    
    /**
     * 取得一个activity的根view
     * @Title: getRootView
     * @param activity
     * @return View
     * @date 2013-3-1 下午1:06:51
     */
    public static View getRootView(Activity activity){
        return ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
    }
    
    /**
     * 获取activity中的所有view
     * @Title: getAllView
     * @param window
     * @return List<View>
     * @date 2013-3-1 下午1:04:15
     */
    public static List<View> getAllView(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return getAllChildView(view);
    }
    
    /**
     * 获取一个view的所有子view
     * @Title: getAllChildView
     * @param view
     * @return List<View>
     * @date 2013-3-1 下午1:05:08
     */
    public static List<View> getAllChildView(View view) {
        // TODO Auto-generated method stub
        List<View> allChildView = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View viewChild = vg.getChildAt(i);
                allChildView.add(viewChild);
                allChildView.addAll(getAllChildView(viewChild));
            }
        }
        return allChildView;
    }
    
    /**
     * 获得com.android.internal.R$id中的变量值
     * @Title: getAndroidInternalID
     * @param context
     * @param fieldName
     * @return
     * @return int
     * @date Jun 16, 2014 5:32:46 PM
     */
    public static int getAndroidInternalID(Context context, String fieldName) {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName("com.android.internal.R$id");
            Object object = clazz.newInstance();
            Field field = clazz.getField(fieldName);
            return Integer.parseInt(field.get(object).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * 设置actionbar tabbar高度，背景色等
     * @Title: setActionBarTabBarHeight
     * @param activity
     * @return void
     * @date Jun 17, 2014 10:43:53 AM
     */
    public static void setActionBarTabBarHeight(BaseActivity activity){
        try {
            if (activity.getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS) {
                int tabBarHeight = activity.getResources().getDimensionPixelSize(R.dimen.actionbar_tabbar_height);
                try{//support v7
                    View actionBar = ((ViewGroup) activity.findViewById(android.support.v7.appcompat.R.id.action_bar_container)).getChildAt(0);
                    actionBar.setBackgroundResource(R.drawable.abc_ab_solid_dark_holo);
                    
                    View actionBarTabBar = ((ViewGroup) activity.findViewById(android.support.v7.appcompat.R.id.action_bar_container)).getChildAt(2);
                    ScrollingTabContainerView tabBar = (ScrollingTabContainerView)actionBarTabBar;
                    tabBar.setContentHeight(tabBarHeight);
                }catch(Exception e){//非support v7 反射
                    try {
                        View actionBarTabBar = ((ViewGroup) activity.findViewById(ViewUtil
                                .getAndroidInternalID(activity, "action_bar_container"))).getChildAt(2);
                        @SuppressWarnings("rawtypes")
                        Class clazz = Class.forName("com.android.internal.widget.ScrollingTabContainerView");
                        @SuppressWarnings("unchecked")
                        Method m = clazz.getMethod("setContentHeight", int.class);
                        m.invoke(actionBarTabBar, tabBarHeight);
                    } catch (Exception e1) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
