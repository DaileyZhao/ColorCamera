package com.zcm.ui.basearch;

import android.content.Context;
import android.util.Log;

import com.zcm.library.base.EmptyAppLifecycleCallbacks;
import com.zcm.ui.changeskin.SkinManager;

public class UiApplication extends EmptyAppLifecycleCallbacks {
    Context context;
    private static final String TAG = "UiApplication";

    @Override
    public void attachBaseContext(Context base) {
        context = base;
        Log.e(TAG, "attachBaseContext has executed  "+context.getClass().getName() );
    }

    @Override
    public void onCreate() {
        SkinManager.getInstance().init(context);
        Log.e(TAG, "onCreate has executed ");
    }
}
