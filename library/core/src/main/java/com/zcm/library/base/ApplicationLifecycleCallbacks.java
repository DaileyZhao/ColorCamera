package com.zcm.library.base;

import android.content.Context;
import android.content.res.Configuration;

public interface ApplicationLifecycleCallbacks {

    void attachBaseContext(Context base);

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

    void onConfigurationChanged(Configuration newConfig);
}
