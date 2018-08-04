package com.zcm.ui.basearch;

import android.app.Application;
import android.view.Choreographer;

import com.zcm.library.uimonitor.FPSFrameCallback;
import com.zcm.library.uimonitor.LogMonitor;

/**
 * Create by zcm on 2018/5/16 上午11:24
 */
public class BaseApplication extends Application{
    static Application CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT=BaseApplication.this;
        LogMonitor.getInstance().writeMonitor();
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
    }
    public static Application getAPPcontext(){
        if (CONTEXT==null){
            throw new NullPointerException("Application hasn`t initialize！");
        }
        return CONTEXT;
    }
}
