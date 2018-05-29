package com.zcm.ui.skin;


import com.zcm.ui.basearch.BaseApplication;

/**
 * Created by cylee on 2017/8/16.
 */

public class BaseSkinManager {
    ISkinResourceManager mSkinResourceManager;
    private static BaseSkinManager mInstance;

    private BaseSkinManager() {
        mSkinResourceManager = new SkinResourceManager(BaseApplication.getAPPcontext());
    }

    public static BaseSkinManager getInstance() {
        if (mInstance == null) {
            synchronized (BaseSkinManager.class) {
                mInstance = new BaseSkinManager();
            }
        }
        return mInstance;
    }

    public ISkinResourceManager getSkinResourceManager() {
        return mSkinResourceManager;
    }
}
