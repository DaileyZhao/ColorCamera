package com.zcm.library.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Choreographer;

import com.bytedance.boost_multidex.BoostMultiDex;
import com.zcm.library.net.NetManager;
import com.zcm.library.uimonitor.FPSFrameCallback;
import com.zcm.library.uimonitor.LogMonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zcm on 2018/5/16 上午11:24
 */
public class BaseApplication extends Application {
    List<ApplicationLifecycleCallbacks> appLifecycleCallbacks = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        BoostMultiDex.install(base);
        PackageManager packageManager = getPackageManager();
        ApplicationInfo appInfo = null;
        Class<?> lifecycleClazz;
        ApplicationLifecycleCallbacks callbacks = null;
        try {
            appInfo = packageManager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            String moduleName = bundle.getString("Module_Application");
            lifecycleClazz = Class.forName(moduleName);
            callbacks = (ApplicationLifecycleCallbacks) lifecycleClazz.newInstance();
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        registerAppLifecycleCallbacks(callbacks);
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.attachBaseContext(base);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogMonitor.getInstance().writeMonitor();
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
        NetManager.getInstance().init();
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.onCreate();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.onLowMemory();
            }
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.onTrimMemory(level);
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.onTerminate();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (appLifecycleCallbacks.size() > 0) {
            for (ApplicationLifecycleCallbacks appLifecycleCallbacks : appLifecycleCallbacks) {
                appLifecycleCallbacks.onConfigurationChanged(newConfig);
            }
        }
    }

    public void registerAppLifecycleCallbacks(ApplicationLifecycleCallbacks callback) {
        synchronized (appLifecycleCallbacks) {
            appLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterAppLifecycleCallbacks(ApplicationLifecycleCallbacks callback) {
        synchronized (appLifecycleCallbacks) {
            appLifecycleCallbacks.remove(callback);
        }
    }

}
