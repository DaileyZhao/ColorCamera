package com.zcm.ui.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Create by zcm on 2018/4/13 下午3:53
 */
public class SkinManager {
    private static final Object mClock = new Object();
    private static SkinManager mInstance;

    private Context mContext;
    private Resources mResources;
    private String mSkinPkgName;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if(null == mInstance) {
            synchronized (mClock) {
                if(null == mInstance) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        enableContext(context);
        mContext = context.getApplicationContext();
    }

    public void loadSkin(String skinPath) {
        loadSkin(skinPath, null);
    }

    public void loadSkin(final String skinPath, final ILoadListener listener) {
        enableContext(mContext);
        if(TextUtils.isEmpty(skinPath)) {
            return;
        }
        new AsyncTask<String, Void, Resources>() {
            @Override
            protected void onPreExecute() {
                if(null != listener) {
                    listener.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                if(null != params && params.length == 1) {
                    String skinPath = params[0];
                    File file = new File(skinPath);
                    if(null != file && file.exists()) {
                        PackageManager packageManager = mContext.getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinPath, 1);
                        if(null != packageInfo) {
                            mSkinPkgName = packageInfo.packageName;
                        }
                        return getResources(mContext, skinPath);
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(Resources result) {
                if(null != result) {
                    mResources = result;
                    if(null != listener) {
                        listener.onSuccess();
                    }
                } else {
                    if(null != listener) {
                        listener.onFailure();
                    }
                }
            }
        }.execute(skinPath);
    }

    public Resources getResources(Context context, String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, apkPath);

            Resources r = context.getResources();
            Resources skinResources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());
            return skinResources;
        } catch (Exception e) {
        }
        return null;
    }

    public void restoreDefaultSkin() {
        if(null != mResources) {
            mResources = null;
            mSkinPkgName = null;
        }
    }

    public int getColor(int id) {
        enableContext(mContext);
        Resources originResources = mContext.getResources();
        int originColor = originResources.getColor(id);
        if(null == mResources || TextUtils.isEmpty(mSkinPkgName)) {
            return originColor;
        }
        String entryName = mResources.getResourceEntryName(id);
        int resourceId = mResources.getIdentifier(entryName, "color", mSkinPkgName);
        try {
            return mResources.getColor(resourceId);
        } catch (Exception e) {
        }
        return originColor;
    }

    public Drawable getDrawable(int id) {
        enableContext(mContext);
        Resources originResources = mContext.getResources();
        Drawable originDrawable = originResources.getDrawable(id);
        if(null == mResources || TextUtils.isEmpty(mSkinPkgName)) {
            return originDrawable;
        }
        String entryName = mResources.getResourceEntryName(id);
        int resourceId = mResources.getIdentifier(entryName, "drawable", mSkinPkgName);
        try {
            return mResources.getDrawable(resourceId);
        } catch (Exception e) {
        }
        return originDrawable;
    }

    private void enableContext(Context context) {
        if(null == context) {
            throw new NullPointerException();
        }
    }

}
