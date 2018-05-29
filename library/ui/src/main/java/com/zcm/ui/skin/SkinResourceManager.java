package com.zcm.ui.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import com.zcm.ui.skin.callback.ISkinChangedListener;
import com.zcm.ui.skin.callback.ISkinChangingCallback;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 15/9/22.
 */
class SkinResourceManager implements ISkinResourceManager{
    private Context mContext;
    private Resources mResources;
    private ResourceManager mResourceManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<WeakReference<ISkinChangedListener>> mSkinChangedListeners = new ArrayList<WeakReference<ISkinChangedListener>>();

    public SkinResourceManager(Context context) {
        mContext = context;
        mResourceManager = new ResourceManager(mContext, mContext.getResources(), null);
    }


    @Override
    public void resetSkin() {
        mResourceManager = new ResourceManager(mContext, mContext.getResources(), null);
        notifyChangedListeners();
    }

    /**
     * 根据suffix选择插件内某套皮肤，默认为""
     *
     * @param skinPluginPath 皮肤apk全路径
     * @param pkgName        皮肤apk包名
     * @param callback       皮肤加载事件回调
     */
    @Override
    public void changeSkin(final String skinPluginPath, final String pkgName, ISkinChangingCallback callback) {
        if (callback == null) {
            callback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        }

        if (TextUtils.isEmpty(skinPluginPath) || TextUtils.isEmpty(pkgName)) {
            throw new IllegalArgumentException("skinPluginPath or skinPkgName can not be empty!");
        }

        final ISkinChangingCallback finalCallBack = callback;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                finalCallBack.onStart();
            }
        });

        try {
            loadPlugin(skinPluginPath, pkgName);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalCallBack.onComplete();
                    notifyChangedListeners();
                }
            });
        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    finalCallBack.onError(e);
                }
            });

        }
    }

    @Override
    public void addChangedListener(ISkinChangedListener listener) {
        synchronized (mSkinChangedListeners) {
            mSkinChangedListeners.add(new WeakReference<ISkinChangedListener>(listener));
        }
    }

    @Override
    public void removeChangedListener(ISkinChangedListener listener) {
        synchronized (mSkinChangedListeners) {
            for (WeakReference<ISkinChangedListener> reference : mSkinChangedListeners) {
                if (reference.get() == listener) {
                    mSkinChangedListeners.remove(reference);
                    break;
                }
            }
        }
    }

    void notifyChangedListeners() {
        synchronized (mSkinChangedListeners) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                innerNotify();
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        innerNotify();
                    }
                });
            }
        }
    }

    void innerNotify() {
        synchronized (mSkinChangedListeners) {
            List<WeakReference<ISkinChangedListener>> removes = new ArrayList<WeakReference<ISkinChangedListener>>();
            for (WeakReference<ISkinChangedListener> listener : mSkinChangedListeners) {
                if (listener.get() != null) {
                    listener.get().onSkinChanged();
                } else {
                    removes.add(listener);
                }
            }
            mSkinChangedListeners.removeAll(removes);
        }
    }

    public ResourceManager getResourceManager() {
        return mResourceManager;
    }

    private void loadPlugin(String skinPath, String skinPkgName) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPath);
        Resources superRes = mContext.getResources();
        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourceManager = new ResourceManager(mContext, mResources, skinPkgName);
    }

    @Override
    public Resources getDefResources() {
        return mContext.getResources();
    }

    public boolean isUseDefaultSkin(){
        String plugPkg = getResourceManager().getPluginPackageName();
        return TextUtils.isEmpty(plugPkg) || plugPkg.equals(mContext.getPackageName());
    }
}
