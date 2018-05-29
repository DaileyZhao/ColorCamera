package com.zcm.ui.skin;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.zcm.ui.skin.base.BaseSkinActivity;


/**
 * Created by zhy on 15/9/22.
 */
public class ResourceManager {
    private static final String DEFTYPE_DRAWABLE = "drawable";
    private static final String DEFTYPE_COLOR = "color";
    private static final String DEFTYPE_MIPMAP = "mipmap";
    private Resources mResources;
    private String mPluginPackageName;
    private Context mContext;

    private LruCache<String, Integer> resNameIdCache;

    public ResourceManager(Context context, Resources res, String pluginPackageName) {
        mResources = res;
        mContext = context;
        if (TextUtils.isEmpty(pluginPackageName)) {
            mPluginPackageName = context.getPackageName();
        } else {
            mPluginPackageName = pluginPackageName;
        }
        resNameIdCache = new LruCache<>(30);
    }

    private static Bitmap decodeBitmap(Resources resources, int id, Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        Bitmap result = null;
        try {
            result = BitmapFactory.decodeResource(resources, id, options);
        } catch (Throwable e) {
        }
        return result;
    }

    public Bitmap getBitmapByName(Context context, String name, int defaultResId, Bitmap.Config config) {
        Resources defaultResources = mContext.getResources();
        String defaultPkgName = mContext.getPackageName();
        // check context suffix
        String suffix = null;
        while (context instanceof ContextWrapper && !(context instanceof BaseSkinActivity) ) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (context != null && context instanceof BaseSkinActivity) {
            suffix = ((BaseSkinActivity) context).getSkinResSuffix();
        }
        // if context has suffix, append it to name, then try to load resource with new name
        if (!TextUtils.isEmpty(suffix)) {
            String nameWithSuffix = name + suffix;
            Integer resId = resNameIdCache.get(nameWithSuffix);
            if (resId != null) {
                return decodeBitmap(defaultResources, resId, config);
            }
            resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_DRAWABLE, defaultPkgName);
            if (resId == 0)
                resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_COLOR, defaultPkgName);
            if (resId == 0)
                resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_MIPMAP, defaultPkgName);
            if (resId != 0) {
                resNameIdCache.put(nameWithSuffix, resId);
                return decodeBitmap(defaultResources, resId, config);
            }

        }

        // if context hasn't suffix, or if no resource match name with suffix, just look for
        // resource that matches name without suffix..
        if (defaultResources == mResources) {
            return decodeBitmap(defaultResources, defaultResId, config);
        }
        Integer resId = resNameIdCache.get(name);
        if (resId != null) {
            return decodeBitmap(mResources, resId, config);
        }
        resId = mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName);
        if (resId == 0)
            resId = mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName);
        if (resId == 0)
            resId = mResources.getIdentifier(name, DEFTYPE_MIPMAP, mPluginPackageName);
        if (resId == 0) {
            return decodeBitmap(defaultResources, defaultResId, config);
        }
        resNameIdCache.put(name, resId);
        return decodeBitmap(mResources, resId, config);
    }

    public Drawable getDefaultDrawableByName(String name) {
        int id = mContext.getResources().getIdentifier(name,DEFTYPE_DRAWABLE,mContext.getPackageName());
        if(id > 0) {
            return getDrawableByName(mContext, name, id);
        }else{
            return null;
        }
    }

    public Drawable getDrawableByName(Context context, String name, int defaultResId) {
        DrawableInfo drawableInfo = getDrawableInfoByName(context, name, defaultResId);
        return drawableInfo.getDrawable();
    }

    public DrawableInfo getDrawableInfoByName(Context context, String name, int defaultResId) {

        Resources defaultResources = mContext.getResources();
        String defaultPkgName = mContext.getPackageName();

        // check context suffix
        String suffix = null;
        while (context instanceof ContextWrapper && !(context instanceof BaseSkinActivity) ) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (context != null && context instanceof BaseSkinActivity) {
            suffix = ((BaseSkinActivity) context).getSkinResSuffix();
        }
        // if context has suffix, append it to name, then try to load resource with new name
        if (!TextUtils.isEmpty(suffix)) {
            String nameWithSuffix = name + suffix;
            Integer resId = resNameIdCache.get(nameWithSuffix);
            try {
                if (resId != null) {
                    return DrawableInfo.ofSkin(defaultResources.getDrawable(resId));
                }
                resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_DRAWABLE, defaultPkgName);
                if (resId == 0)
                    resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_COLOR, defaultPkgName);
                if (resId == 0)
                    resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_MIPMAP, defaultPkgName);
                if (resId != 0) {
                    resNameIdCache.put(nameWithSuffix, resId);
                    return DrawableInfo.ofSkin(defaultResources.getDrawable(resId));
                }
            } catch (Resources.NotFoundException e) {
                return DrawableInfo.ofDefault(defaultResources.getDrawable(defaultResId));
            }

        }

        // if context hasn't suffix, or if no resource match name with suffix, just look for
        // resource that matches name without suffix..
        if (defaultResources == mResources) {
            return DrawableInfo.ofDefault(mResources.getDrawable(defaultResId));
        }
        Integer resId = resNameIdCache.get(name);
        try {
            if (resId != null) {
                return DrawableInfo.ofSkin(mResources.getDrawable(resId));
            }
            resId = mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName);
            if (resId == 0)
                resId = mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName);
            if (resId == 0)
                resId = mResources.getIdentifier(name, DEFTYPE_MIPMAP, mPluginPackageName);
            if (resId == 0) {
                return DrawableInfo.ofDefault(defaultResources.getDrawable(defaultResId));
            }
            resNameIdCache.put(name, resId);
            return DrawableInfo.ofSkin(mResources.getDrawable(resId));
        } catch (Resources.NotFoundException e) {
            return DrawableInfo.ofDefault(defaultResources.getDrawable(defaultResId));
        }
    }

    public int getColor(Context context, String name, int defaultResId) {
        Resources defaultResources = mContext.getResources();
        String defaultPkgName = mContext.getPackageName();
        // check context suffix
        String suffix = null;
        while (context instanceof ContextWrapper && !(context instanceof BaseSkinActivity) ) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (context != null && context instanceof BaseSkinActivity) {
            suffix = ((BaseSkinActivity) context).getSkinResSuffix();
        }
        // if context has suffix, append it to name, then try to load resource with new name
        if (!TextUtils.isEmpty(suffix)) {
            String nameWithSuffix = name + suffix;
            Integer resId = resNameIdCache.get(nameWithSuffix);
            try {
                if (resId != null) {
                    return defaultResources.getColor(resId);
                }
                resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_COLOR, defaultPkgName);
                if (resId != 0) {
                    resNameIdCache.put(name, resId);
                    return defaultResources.getColor(resId);
                }
            } catch (Resources.NotFoundException e) {
                return defaultResources.getColor(defaultResId);
            }
        }

        // if context hasn't suffix, or if no resource math name with suffix, just look for
        // resource that matches name without suffix..
        if (defaultResources == mResources) {
            return mResources.getColor(defaultResId);
        }
        Integer resId = resNameIdCache.get(name);
        try {
            if (resId != null) {
                return mResources.getColor(resId);
            }
            resId = mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName);
            if (resId == 0) {
                return defaultResources.getColor(defaultResId);
            }
            resNameIdCache.put(name, resId);
            return mResources.getColor(resId);
        } catch (Resources.NotFoundException e) {
            return defaultResources.getColor(defaultResId);
        }

    }

    public ColorStateList getColorStateList(Context context, String name, int defaultResId) {
        Resources defaultResources = mContext.getResources();
        String defaultPkgName = mContext.getPackageName();
        // check context suffix
        String suffix = null;
        while (context instanceof ContextWrapper && !(context instanceof BaseSkinActivity) ) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (context != null && context instanceof BaseSkinActivity) {
            suffix = ((BaseSkinActivity) context).getSkinResSuffix();
        }
        // if context has suffix, append it to name, then try to load resource with new name
        if (!TextUtils.isEmpty(suffix)) {
            String nameWithSuffix = name + suffix;
            Integer resId = resNameIdCache.get(name);
            try {
                if (resId != null) {
                    return defaultResources.getColorStateList(resId);
                }
                resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_COLOR, defaultPkgName);
                if (resId == 0) {
                    resId = defaultResources.getIdentifier(nameWithSuffix, DEFTYPE_DRAWABLE, defaultPkgName);
                }
                if (resId != 0) {
                    resNameIdCache.put(nameWithSuffix, resId);
                    return defaultResources.getColorStateList(resId);
                }
            } catch (Resources.NotFoundException e) {
                return defaultResources.getColorStateList(defaultResId);
            }
        }

        // if context hasn't suffix, or if no resource math name with suffix, just look for
        // resource that matches name without suffix..
        if (defaultResources == mResources) {
            return mResources.getColorStateList(defaultResId);
        }
        Integer resId = resNameIdCache.get(name);
        try {
            if (resId != null) {
                return mResources.getColorStateList(resId);
            }
            resId = mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName);
            if (resId == 0) {
                resId = mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName);
            }
            if (resId == 0) {
                return defaultResources.getColorStateList(defaultResId);
            }
            resNameIdCache.put(name, resId);
            return mResources.getColorStateList(resId);
        } catch (Resources.NotFoundException e) {
            return defaultResources.getColorStateList(defaultResId);
        }

    }

    public Resources getDefaultResources() {
        return mContext.getResources();
    }

    public static class DrawableInfo {
        private Drawable defaultDrawable;
        private Drawable skinDrawable;

        private DrawableInfo(Drawable defaultDrawable, Drawable skinDrawable) {
            this.defaultDrawable = defaultDrawable;
            this.skinDrawable = skinDrawable;
        }

        public static DrawableInfo ofSkin(Drawable skinDrawable) {
            return new DrawableInfo(null, skinDrawable);
        }

        public static DrawableInfo ofDefault(Drawable defaultDrawable) {
            return new DrawableInfo(defaultDrawable, null);
        }

        public boolean isSkin() {
            return skinDrawable != null;
        }

        public Drawable getDrawable() {
            return skinDrawable != null ? skinDrawable : defaultDrawable;
        }

    }

    public String getPluginPackageName(){
        return mPluginPackageName;
    }
}
