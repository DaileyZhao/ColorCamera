package com.zcm.ui.skin.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.zcm.ui.R;
import com.zcm.ui.skin.BaseSkinManager;
import com.zcm.ui.skin.ResourceManager;
import com.zcm.ui.skin.attr.IApply;
import com.zcm.ui.skin.base.BaseSkinActivity;


/**
 * Created by cylee on 2017/8/16.
 */

public class BaseSkinUtils {
    public static String getFeSkinId() {
        return "skin-gray";
    }

    public static boolean useSkin(View view) {
        if (view != null) {
            Object tag = view.getTag(R.id.view_use_skin);
            if (tag != null) {
                return Boolean.valueOf(tag.toString());
            } else {
                tag = view.getTag();
                if (tag != null) {
                    return !TextUtils.equals("no_skin", tag.toString());
                }
            }
        }
        return false;
    }

    public static Drawable loadDrawable(Context context, int id, boolean useSkin) {
        return loadDrawable(context, id, -1, useSkin);
    }

    public static Drawable loadDrawable(Context context, int id, int filterColorId, boolean useSkin) {
        String resName = context.getResources().getResourceEntryName(id);
        Drawable drawable = null;
        try {
            if (useSkin) {
                ResourceManager resourceManager = BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager();
                drawable = resourceManager.getDrawableByName(context, resName, id);
                if (drawable != null && filterColorId >= 0) {
                    int filterColor = getColor(context, filterColorId, true);
                    drawable = drawable.mutate(); // 此处处理android对于load同一id的drawable返回同一实例的优化问题
                    drawable.setColorFilter(new PorterDuffColorFilter(filterColor, PorterDuff.Mode.SRC_IN));
                }
            } else {
                drawable = context.getResources().getDrawable(id);
            }
        } catch (Throwable e) {
            drawable = context.getResources().getDrawable(id);
        }
        return drawable;
    }

    public static int getColor(final Context context, final int id, boolean useSkin) {
        if (useSkin) {
            String resName = context.getResources().getResourceEntryName(id);
            return  BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager().getColor(context, resName, id);
        }
        return context.getResources().getColor(id);
    }

    public static void setBackgroundColor(final BaseSkinActivity activity, final View view, final int id) {
        if (activity != null) {
            activity.injectSkin(view, new IApply() {
                @Override
                public void apply(View view, boolean useSkin) {
                    int color = getColor(activity, id, useSkin);
                    view.setBackgroundColor(color);
                }
            });
        }
    }

    public static void setImageSrc(final BaseSkinActivity activity, final ImageView v, final int id) {
        setImageSrc(activity, v, id, -1);
    }

    public static void setImageSrc(final BaseSkinActivity activity, final ImageView v, final int id, final int filterColorId) {
        if (activity != null) {
            activity.injectSkin(v, new IApply() {
                @Override
                public void apply(View view, boolean useSkin) {
                    Drawable drawable = loadDrawable(activity, id, filterColorId, useSkin);
                    if (drawable != null) {
                        v.setImageDrawable(drawable);
                    }
                }
            });
        }
    }
}
