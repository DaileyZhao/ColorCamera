package com.zcm.library.base;

import android.content.Context;
import android.content.res.Configuration;

public class EmptyAppLifecycleCallbacks implements ApplicationLifecycleCallbacks{
    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }
}
