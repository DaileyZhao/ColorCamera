package com.zcm.library.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.view.Choreographer;

import com.zcm.library.net.NetManager;
import com.zcm.library.uimonitor.FPSFrameCallback;
import com.zcm.library.uimonitor.LogMonitor;

/**
 * Create by zcm on 2018/5/16 上午11:24
 */
public class BaseApplication extends Application{
    static Application CONTEXT;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT=BaseApplication.this;
        LogMonitor.getInstance().writeMonitor();
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
        NetManager.getInstance().init();
    }
    public static Application getApplication(){
        if (CONTEXT==null){
            throw new NullPointerException("Application hasn`t initialize！");
        }
        return CONTEXT;
    }
}
