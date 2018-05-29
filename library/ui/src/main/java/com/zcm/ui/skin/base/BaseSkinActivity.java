package com.zcm.ui.skin.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;

import com.zcm.ui.BuildConfig;
import com.zcm.ui.R;
import com.zcm.ui.skin.BaseSkinManager;
import com.zcm.ui.skin.attr.IApply;
import com.zcm.ui.skin.attr.SkinAttrSupport;
import com.zcm.ui.skin.attr.SkinView;
import com.zcm.ui.skin.callback.ISkinChangedListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/9/22.
 */
public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();
    private final Object[] mConstructorArgs = new Object[2];
    private Map<View, SkinView> mSkinViews = new HashMap<View, SkinView>();

    private boolean useSkin = false;

    /**
     * 必须在任何一个View被创建之前调用该方法，通常在onCreate中调用就可以
     * @param useSkin
     */
    protected void setUseSkin(boolean useSkin) {
        this.useSkin = useSkin;
    }

    public boolean isUseSkin() {
        return useSkin;
    }

    public String getSkinResSuffix() {
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View fragmentView = super.onCreateView(name, context, attrs);
        if (fragmentView != null) {
            return fragmentView;
        }
        View view = createViewFromTag(context, name, attrs);
        if (view == null) {
            return null;
        }
        List<IApply> skinAttrList = SkinAttrSupport.getSkinAttrs(attrs, context, view);
        if (skinAttrList.isEmpty()) {
            view.setTag(R.id.view_use_skin, useSkin);
            return view;
        }
        injectSkin(view, skinAttrList);
        return view;
    }

    private SkinView getSkinView(View view) {
        SkinView skinView = mSkinViews.get(view);
        if (skinView == null) {
            skinView = new SkinView(view, new ArrayList<IApply>());
            mSkinViews.put(view, skinView);
        }
        return skinView;
    }

    public void injectSkin(View view, List<IApply> skinAttrList) {
        view.setTag(R.id.view_use_skin, useSkin);
        if (skinAttrList.size() != 0) {
            getSkinView(view).addApplies(skinAttrList);
            for (IApply attr : skinAttrList) {
                try {
                    attr.apply(view, /*SkinUtils.useSkin(view)*/useSkin);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void injectSkin(View view, final IApply skinAttr) {
        view.setTag(R.id.view_use_skin, useSkin);
        if (skinAttr != null) {
            getSkinView(view).addApply(skinAttr);
            try {
                skinAttr.apply(view, /*SkinUtils.useSkin(view)*/useSkin);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除掉皮肤样式，清除后该view不受皮肤切换影响
     * @param view
     */
    public void clearSkin(View view) {
        if (view != null) {
            SkinView v = getSkinView(view);
            if (v != null) {
                view.setTag(R.id.view_use_skin, false);
                v.apply();
            }
        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf('.')) {
                View view;
                if (name.equals("View")) {
                    view = createView(context, name, "android.view.");
                } else {
                    view = createView(context, name, "android.widget.");
                }
                return view;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseSkinManager.getInstance().getSkinResourceManager().addChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseSkinManager.getInstance().getSkinResourceManager().removeChangedListener(this);
        mSkinViews.clear();
    }

    protected final void removeSkinView(View view) {
        mSkinViews.remove(view);
    }

    @Override
    public void onSkinChanged() {
        /**
         * 目前不清楚为什么会有这个问题，先暂时转化为List再遍历 cylee
         * java.util.ConcurrentModificationException
         at java.util.HashMap$HashIterator.nextEntry(HashMap.java:806)
         at java.util.HashMap$ValueIterator.next(HashMap.java:838)
         at com.baidu.homework.common.skin.base.BaseSkinActivity.onSkinChanged(Unknown Source)
         at com.baidu.homework.common.skin.SkinResourceManager.innerNotify(Unknown Source)
         at com.baidu.homework.common.skin.SkinResourceManager.notifyChangedListeners(Unknown Source)
         */
        if (mSkinViews != null && mSkinViews.values() != null) {
            List<SkinView> skinViews = new ArrayList<SkinView>(mSkinViews.values());
            int count = skinViews.size();
            for (int i = 0; i < count; i++) {
                try {
                    SkinView view = skinViews.get(i);
                    // 单个view皮肤应用失败直接忽略
                    view.apply();
                } catch (Throwable e) {
                    e.printStackTrace();
                    if (BuildConfig.DEBUG) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
