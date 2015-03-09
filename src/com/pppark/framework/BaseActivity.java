package com.pppark.framework;

import java.util.Observable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import butterknife.ButterKnife;

import com.pppark.R;
import com.pppark.framework.data.DataTask;
import com.pppark.framework.data.DataTask.DataTaskListener;
import com.pppark.framework.data.IUIDataTask;
import com.pppark.framework.logging.Log;
import com.pppark.support.util.ToastUtil;
import com.pppark.support.util.ViewUtil;

/**
 * Activity、FragmentActivity基类<br>
 * 
 * @Package com.xunlei.video.framework
 * @ClassName: BaseActivity
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 17, 2014 11:56:43 PM
 */
public abstract class BaseActivity extends ActionBarActivity implements IUI, IUIDataTask {

    /**
     * 销毁时通知DataTask cancel的观察者
     */
    protected Observable lifeObservable = new Observable();
    private boolean forbidStartActivityAnimation = false;
    private boolean forbidFinishActivityGesture = false;

    /**
     * 做了4件事:<br>
     * 1、setContentView<br>
     * 2、初始化Views<br>
     * 3、调用initViewProperty<br>
     * 4、调用initData
     * 
     * @Title: onCreate
     * @param savedInstanceState
     * @param layoutResId
     * @return void
     * @date Apr 18, 2014 11:23:00 AM
     */
    protected void onCreate(Bundle savedInstanceState, int layoutResId) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
        }
        setContentView(layoutResId);

        ButterKnife.inject(this);
        initViewProperty();
        initData();
        
        ViewUtil.setActionBarTabBarHeight(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 设置禁止启动Activity动画
     * 
     * @Title: setForbidStartActivityAnimation
     * @param forbidStartActivityAnimation
     * @return void
     * @date May 14, 2014 11:14:18 AM
     */
    public void setForbidStartActivityAnimation(boolean forbidStartActivityAnimation) {
        this.forbidStartActivityAnimation = forbidStartActivityAnimation;
    }

    /**
     * 设置禁止finish activity手势，用于存在viewpager等手势冲突的activity
     * 
     * @Title: setForbidFinishActivityGesture
     * @param forbidFinishActivityGesture
     * @return void
     * @date May 14, 2014 11:48:27 AM
     */
    public void setForbidFinishActivityGesture(boolean forbidFinishActivityGesture) {
        this.forbidFinishActivityGesture = forbidFinishActivityGesture;
    }

    @Override
    public void startActivity(Intent intent) {
        // TODO Auto-generated method stub
        super.startActivity(intent);
        if (!this.forbidStartActivityAnimation) {
            overridePendingTransition(R.anim.right_in, R.anim.zoom_out);
            return;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        // TODO Auto-generated method stub
        super.startActivityForResult(intent, requestCode);
        if (!this.forbidStartActivityAnimation) {
            overridePendingTransition(R.anim.right_in, R.anim.zoom_out);
            return;
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (!this.forbidStartActivityAnimation) {
            overridePendingTransition(R.anim.zoom_in, R.anim.right_out);
        }
    }

    private int startX = 0;
    private int startY = 0;

    /*
     * (non-Javadoc) 手势finish
     * 
     * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        if (this.forbidFinishActivityGesture) {
            return super.dispatchTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) event.getX();
            startY = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() - startX > 100 && Math.abs(event.getY() - startY) < 200) {
                onBackPressed();
                return true;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        BaseFragment outer = (BaseFragment) fm.findFragmentById(R.id.content_frame);
        if (outer != null) {
            if (outer.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        lifeObservable.notifyObservers();
        super.onDestroy();
    }

    @Override
    public DataTask newDataTask(DataTaskListener dataTaskListener) {
        // TODO Auto-generated method stub
        return new DataTask(dataTaskListener, lifeObservable);
    }

    protected void setContentFragment(Class<? extends BaseFragment> fragmentClass) {
        Bundle arguments = null;
        if (getIntent() != null) {
            arguments = getIntent().getExtras();
        }
        setContentFragment(fragmentClass, arguments);
    }

    protected void setContentFragment(Class<? extends BaseFragment> fragmentClass, Bundle arguments) {
        Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), arguments);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, fragment);
        t.commit();
    }

    protected void setContentFragment(Class<? extends BaseFragment> fragmentClass, Bundle arguments, int contentId) {
        Log.d("set content fragment. class={}", fragmentClass.getName());

        Fragment fragment = Fragment.instantiate(this, fragmentClass.getName(), arguments);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(contentId, fragment);
        t.commit();
    }

    protected void setContentFragment(String fragmentClassName, Bundle arguments) {
        Log.d("set content fragment. class={}", fragmentClassName);

        Fragment fragment = Fragment.instantiate(this, fragmentClassName, arguments);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, fragment);
        t.commit();
    }

    protected void showToast(String text) {
        ToastUtil.showToast(this, text);
    }

    protected void showToast(int resId) {
        ToastUtil.showToast(this, resId);
    }

}
