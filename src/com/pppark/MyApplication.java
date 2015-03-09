package com.pppark;

import com.pppark.support.manager.CrashHandler;
import com.pppark.support.util.NetUtil;

import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.SubscriberExceptionEvent;

public class MyApplication extends Application {

    public static Context context;
    private static boolean IS_INITED = false;
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init();
        MyApplication.context = getApplicationContext();

        EventBus.getDefault().configureLogSubscriberExceptions(false);// eventbus不打印subscriber异常日志
        if (BuildConfig.DEBUG) {// 监听eventbus回调subscriber的异常
            EventBus.getDefault().register(this, SubscriberExceptionEvent.class);
        }
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 启动后需要初始化的类，先统一写在init方法中，便于以后移植
     * 
     * @Title: init
     * @return void
     * @date 2014-4-23 下午3:30:43
     */
    public void init() {
        if (IS_INITED)
            return;
        IS_INITED = true;

        registNetListener();
        initDatabase();
    }

    private void initDatabase() {
        Sprinkles sprinkles = Sprinkles.init(this, "padkankan.db", 0);

        // database version 0
        Migration version0 = new Migration();
        sprinkles.addMigration(version0);

    }

    private void registNetListener() {
        NetUtil.setHasNet(NetUtil.checkNetWrokAvailable(context));
        BroadcastReceiver internetChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                boolean isConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                NetUtil.setHasNet(!isConnected);
            }
        };
        registerReceiver(internetChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * 将eventbus subscriber的异常交由默认的异常处理器
     */
    public void onEvent(SubscriberExceptionEvent errorEvent) {
        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), errorEvent.throwable);
    }

}
