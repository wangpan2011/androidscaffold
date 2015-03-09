package com.pppark.framework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pppark.R;

/**
 * 公用FragmentActivity
 * 
 * @Package com.xunlei.video.framework
 * @ClassName: FragmentActivity
 * @author Beethoven
 * @mail zhanghuitao@xunlei.com
 * @date Apr 22, 2014 12:27:16 AM
 */
public class SharedFragmentActivity extends BaseActivity {

    public static final String INTENT_FRAGMENT_NAME = "intent_fragment_name";
    
    /**
     * 启动一个fragment
     * 
     * @Title: startFragmentActivity
     * @param fragmentClass
     * @param extras
     * @return void
     * @date Apr 22, 2014 12:27:37 AM
     */
    public static void startFragmentActivity(Context context, Class<? extends BaseFragment> fragmentClass, Bundle extras) {
        Intent intent = new Intent(context, SharedFragmentActivity.class);
        intent.putExtra(INTENT_FRAGMENT_NAME, fragmentClass);
        if (null != extras)
            intent.putExtras(extras);
        context.startActivity(intent);
    }
    
    public static void startFragmentActivityNewTask(Context context, Class<? extends BaseFragment> fragmentClass, Bundle extras) {
        Intent intent = new Intent(context, SharedFragmentActivity.class);
        intent.putExtra(INTENT_FRAGMENT_NAME, fragmentClass);
        if (null != extras)
            intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    /**
     * 启动一个用于回调信息的fragment
     * @Title: startFragmentActivityForResult
     * @param activity
     * @param fragmentClass
     * @param requestCode
     * @param extras
     * @return void
     * @date 2014-4-30 下午1:29:28
     */
    public static void startFragmentActivityForResult(BaseFragment fragment, Class<? extends BaseFragment> fragmentClass, int requestCode, Bundle extras) {
        Intent intent = new Intent(fragment.getActivity(), SharedFragmentActivity.class);
        intent.putExtra(INTENT_FRAGMENT_NAME, fragmentClass);
        if (null != extras)
            intent.putExtras(extras);
        fragment.startActivityForResult(intent, requestCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState, R.layout.content_frame);

        Class<? extends BaseFragment> fragmentClass = (Class<? extends BaseFragment>)getIntent().getSerializableExtra(INTENT_FRAGMENT_NAME);
        if (fragmentClass != null) {
            setContentFragment(fragmentClass, getIntent().getExtras());
        }
    }
    
    @Override
    protected void onStart() {
        try {
            super.onStart();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }
    
    @Override
    protected void onStop() {
        try {
            super.onStop();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }
    
    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }
    
    @Override
    public void initViewProperty() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        
    }
    
}
