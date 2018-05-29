package com.zcm.ui.basearch;

import android.app.Application;

/**
 * Create by zcm on 2018/5/16 上午11:24
 */
public class BaseApplication extends Application{
    static Application CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT=BaseApplication.this;
    }
    public static Application getAPPcontext(){
        if (CONTEXT==null){
            throw new NullPointerException("Application hasn`t initialize！");
        }
        return CONTEXT;
    }
}
