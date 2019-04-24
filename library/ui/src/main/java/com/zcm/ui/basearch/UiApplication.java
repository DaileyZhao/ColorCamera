package com.zcm.ui.basearch;

import com.zcm.library.base.BaseApplication;
import com.zcm.ui.changeskin.SkinManager;

public class UiApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
