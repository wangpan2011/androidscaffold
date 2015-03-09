package com.pppark.framework;

import java.util.Observable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

import com.pppark.R;
import com.pppark.framework.data.DataTask;
import com.pppark.framework.data.DataTask.DataTaskListener;
import com.pppark.framework.data.IUIDataTask;
import com.pppark.framework.logging.Log;
import com.pppark.support.util.ToastUtil;
import com.pppark.support.util.ViewUtil;


/**
 * Fragment基类<br>
 * 
 * @Package com.xunlei.video.framework
 * @ClassName: BaseFragment
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 18, 2014 12:00:12 AM
 */
public abstract class BaseFragment extends Fragment implements IUI, IUIDataTask {

    /**
     * 销毁时通知DataTask cancel的观察者
     */
    protected Observable lifeObservable = new Observable();

    /**
     * * 做了4件事:<br>
     * 1、生成rootView<br>
     * 2、初始化Views<br>
     * 3、调用initViewProperty<br>
     * 4、调用initData
     * 
     * @Title: onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @param layoutResId
     * @return View
     * @date Apr 18, 2014 11:24:57 AM
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layoutResId) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(layoutResId, container, false);
        ButterKnife.inject(this, rootView);

        initViewProperty();
        initData();

        ViewUtil.setActionBarTabBarHeight((BaseActivity)getActivity());
        return rootView;
    }

    @Override
    public DataTask newDataTask(DataTaskListener dataTaskListener) {
        // TODO Auto-generated method stub
        return new DataTask(dataTaskListener, lifeObservable);
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        lifeObservable.notifyObservers();
        super.onDestroyView();
    }

    public void replaceFragment(Class<?> fregmentClass, Bundle arguments) {
        Log.d("replace fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    protected void openFragment(Fragment fromFragment, Class<?> fregmentClass, Bundle arguments) {
        Log.d("open fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        transaction.hide(fromFragment);
        transaction.add(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void replaceFragment(Fragment fromFragment, Class<?> fregmentClass, int contentId, Bundle arguments) {
        Log.d("open fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        transaction.replace(contentId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void openFragment(Fragment fromFragment, Class<?> fregmentClass, int contentId, Bundle arguments) {
        Log.d("open fragment. class={}", fregmentClass.getName());

        Fragment fragment = Fragment.instantiate(getActivity(), fregmentClass.getName(), arguments);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        transaction.hide(fromFragment);
        transaction.add(contentId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void setTitle(int resId) {
        getActivity().setTitle(resId);
    }

    protected void setTitle(CharSequence title) {
        getActivity().setTitle(title);
    }

    protected void showToast(String text) {
        if (getActivity() != null) 
            ToastUtil.showToast(getActivity(), text);
    }

    protected void showToast(int resId) {
        ToastUtil.showToast(getActivity(), resId);
    }

    /**
     * 接收返回键按下事件
     * 
     * @Title: onBackKeyDown
     * @return boolean false:back键事件未处理，向下传递。 true：消费掉该事件。
     * @date 2014-3-10 上午11:15:33
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 设置禁止finish activity手势，用于存在viewpager等手势冲突的activity
     * 
     * @Title: setForbidFinishActivityGesture
     * @param paramBoolean
     * @return void
     * @date 2014-5-20 下午4:44:14
     */
    protected void setForbidFinishActivityGesture(boolean paramBoolean) {
        if (!(getActivity() instanceof BaseActivity))
            return;
        ((BaseActivity) getActivity()).setForbidFinishActivityGesture(paramBoolean);
    }

    /**
     * 设置禁止启动Activity动画
     * 
     * @Title: setForbidStartActivityAnimation
     * @param paramBoolean
     * @return void
     * @date 2014-5-20 下午4:44:26
     */
    public void setForbidStartActivityAnimation(boolean paramBoolean) {
        if (!(getActivity() instanceof BaseActivity))
            return;
        ((BaseActivity) getActivity()).setForbidStartActivityAnimation(paramBoolean);
    }

    /**
     * 刷新menu
     * 
     * @Title: supportInvalidateOptionsMenu
     * @return void
     * @date 2014-6-5 上午11:37:17
     */
    public void supportInvalidateOptionsMenu() {
        if (!(getActivity() instanceof BaseActivity))
            return;
        ((BaseActivity) getActivity()).supportInvalidateOptionsMenu();
    }

    protected void setShowAsAction(MenuItem item, int actionEnum){
        MenuItemCompat.setShowAsAction(item, actionEnum);
    }
}
