package com.zcm.ui.skin.attr;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.StateSet;
import android.view.View;

import com.zcm.ui.BuildConfig;
import com.zcm.ui.R;
import com.zcm.ui.skin.BaseSkinManager;
import com.zcm.ui.skin.ColorFilteredStateDrawable;
import com.zcm.ui.skin.ResourceManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhy on 15/9/22.
 */
public class SkinAttr implements IApply{
    public static final String ATTR_PREFIX = "skin_"; // 皮肤资源前缀
    public static final String ATTR_TINT_PREFIX = ATTR_PREFIX + "tint_"; // 可以应用tint color的资源前缀
    public static final String ATTR_DEFAULT_PREFIX = ATTR_PREFIX + "default_";
    private String resName;
    private SkinAttrType attrType;
    private int resId;

    private static Method getStateDrawableIndex;
    private static Method getStateDrawable;

    private static final int[] STATE_NORMAL = StateSet.WILD_CARD;
    private static final int[] STATE_PRESSED = new int[]{android.R.attr.state_pressed};
    private static final int[] STATE_SELECTED = new int[]{android.R.attr.state_selected};
    private static final int[] STATE_DISABLED = new int[]{-android.R.attr.state_enabled};


    protected SkinAttr() {}

    public SkinAttr(SkinAttrType attrType, String resName) {
        this.resName = resName;
        this.attrType = attrType;
        if (BuildConfig.DEBUG) {
            if (!resName.startsWith(ATTR_PREFIX)) {
                throw new AssertionError("illegal resource name: " + resName + ", skin resource must begin with " + ATTR_PREFIX);
            }
        }
    }

    public SkinAttr(int resId, Context context, SkinAttrType attrType) {
        this.resName = context.getResources().getResourceEntryName(resId);
        this.attrType = attrType;
        this.resId = resId;
        if (BuildConfig.DEBUG) {
            if (!resName.startsWith(ATTR_PREFIX)) {
                throw new AssertionError("illegal resource name: " + resName + ", skin resource must begin with " + ATTR_PREFIX);
            }
        }
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public void apply(View view, boolean useSkin) {
        attrType.apply(view, resName, resId);
    }

    public static void bindFilterColor(View view, String filterName, String colorResName, int id) {
        if (view != null && !TextUtils.isEmpty(filterName)) {
            Map<String, NameIdPair> map = (Map<String, NameIdPair>) view.getTag(R.id.view_skin_attr_extra_info);
            if (map == null) {
                map = new HashMap<>();
                view.setTag(R.id.view_skin_attr_extra_info, map);
            }
            map.put(filterName, new NameIdPair(colorResName, id));
        }
    }

    public static boolean hasFilterColor(View view, String filterName) {
        if (view != null && !TextUtils.isEmpty(filterName)) {
            Map<String, NameIdPair> map = (Map<String, NameIdPair>) view.getTag(R.id.view_skin_attr_extra_info);
            return map != null && map.containsKey(filterName);
        } else {
            return false;
        }
    }

    public static Integer getFilterColor(View view, String filterName, boolean useSkin) {
        if (view != null && !TextUtils.isEmpty(filterName) && hasFilterColor(view, filterName)) {
            Map<String, NameIdPair> map = (Map<String, NameIdPair>) view.getTag(R.id.view_skin_attr_extra_info);
            NameIdPair nameIdPair = map.get(filterName);
            if (useSkin) {
                return BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager().getColor(view.getContext(), nameIdPair.name, nameIdPair.id);
            } else {
                return BaseSkinManager.getInstance().getSkinResourceManager().getDefResources().getColor(nameIdPair.id);
            }
        } else {
            return null;
        }
    }

    public static ColorStateList getColorStateList(int normalColor, Integer pressColor, Integer disableColor) {
        if (pressColor == null && disableColor == null) {
            // 仅提供normal颜色
            return new ColorStateList(new int[][]{STATE_NORMAL}, new int[]{normalColor});
        } else if (pressColor == null) {
            // 仅提供normal和disable
            return new ColorStateList(new int[][]{STATE_DISABLED, STATE_NORMAL}, new int[]{disableColor, normalColor});
        } else if (disableColor == null) {
            // 仅提供normal和press
            return new ColorStateList(new int[][]{STATE_PRESSED, STATE_SELECTED, STATE_NORMAL}, new int[]{pressColor, pressColor, normalColor});
        } else {
            // 所有颜色均提供
            return new ColorStateList(new int[][]{STATE_DISABLED, STATE_PRESSED, STATE_SELECTED, STATE_NORMAL}, new int[]{disableColor, pressColor, pressColor, normalColor});
        }
    }

    public static Drawable applyDrawableFilter(String drawableName, Drawable defaultDrawable, int defaultDrawableId,
                                               Integer normalColor, Integer pressColor, Integer disableColor) {
        if (!drawableName.startsWith(ATTR_TINT_PREFIX)) {
            return defaultDrawable;
        }
        //检查是否有默认资源
        boolean isDefaultSkin =BaseSkinManager.getInstance().getSkinResourceManager().isUseDefaultSkin();
        if(isDefaultSkin){
            String replaceName = drawableName.replace(SkinAttr.ATTR_TINT_PREFIX,SkinAttr.ATTR_DEFAULT_PREFIX);
            Drawable drawable = BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager().getDefaultDrawableByName(replaceName);
            if(null != drawable){
                return drawable;
            }
        }
        Drawable normalStateDrawable;
        Drawable pressedStateDrawable;
        Drawable selectedStateDrawable;
        Drawable disabledStateDrawable;

        if (defaultDrawable instanceof DrawableContainer) {
            if (defaultDrawable instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) defaultDrawable;

                normalStateDrawable = getStateDrawable(stateListDrawable, STATE_NORMAL);
                pressedStateDrawable = getStateDrawable(stateListDrawable, STATE_PRESSED);
                selectedStateDrawable = getStateDrawable(stateListDrawable, STATE_SELECTED);
                disabledStateDrawable = getStateDrawable(stateListDrawable, STATE_DISABLED);

//                if (normalStateDrawable == null || pressedStateDrawable == null || selectedStateDrawable == null) {
//                    return defaultDrawable;
//                }
            } else {
                throw new AssertionError("drawable: " + drawableName + " need to be filtered and mustn't be DrawableContainer(only StateListDrawable is supported)");
            }
        } else {
            ResourceManager resourceManager = BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager();
            normalStateDrawable = resourceManager.getDefaultResources().getDrawable(defaultDrawableId);
            pressedStateDrawable = resourceManager.getDefaultResources().getDrawable(defaultDrawableId);
            disabledStateDrawable = resourceManager.getDefaultResources().getDrawable(defaultDrawableId);
            selectedStateDrawable = pressedStateDrawable;
        }

        if (Build.VERSION.SDK_INT < 21){//由于StateListDrawable的ColorFilter在Android5.0以下无效，此处兼容5.0以下
            ColorFilteredStateDrawable stateListDrawable = new ColorFilteredStateDrawable();
            if (defaultDrawable == null || normalColor == null) {
                throw new AssertionError("selector must have a default drawable or colorFilter");
            }
            if (pressColor != null) {
                stateListDrawable.addState(STATE_SELECTED, defaultDrawable,pressColor);
            }
            if (pressColor != null) {
                stateListDrawable.addState(STATE_PRESSED, defaultDrawable,pressColor);
            }
            if (disableColor != null) {
                stateListDrawable.addState(STATE_DISABLED, defaultDrawable,disableColor);
            }

            stateListDrawable.addState(STATE_NORMAL,defaultDrawable,normalColor);
            stateListDrawable.setBounds(0, 0, defaultDrawable.getIntrinsicWidth(), defaultDrawable.getIntrinsicHeight());

            return stateListDrawable;
        }else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            if (normalStateDrawable == null || normalColor == null) {
                throw new AssertionError("selector must have a default drawable or colorFilter");
            }

            if (pressColor != null && selectedStateDrawable != null) {
                selectedStateDrawable.mutate().setColorFilter(new PorterDuffColorFilter(pressColor, PorterDuff.Mode.SRC_IN));
                stateListDrawable.addState(STATE_SELECTED, selectedStateDrawable);
            }
            if (pressColor != null && pressedStateDrawable != null) {
                pressedStateDrawable.mutate().setColorFilter(new PorterDuffColorFilter(pressColor, PorterDuff.Mode.SRC_IN));
                stateListDrawable.addState(STATE_PRESSED, pressedStateDrawable);
            }
            if (disableColor != null && disabledStateDrawable != null) {
                disabledStateDrawable.mutate().setColorFilter(new PorterDuffColorFilter(disableColor, PorterDuff.Mode.SRC_IN));
                stateListDrawable.addState(STATE_DISABLED, disabledStateDrawable);
            }

            normalStateDrawable.mutate().setColorFilter(new PorterDuffColorFilter(normalColor, PorterDuff.Mode.SRC_IN));
            stateListDrawable.addState(STATE_NORMAL, normalStateDrawable);
            stateListDrawable.setBounds(0, 0, normalStateDrawable.getIntrinsicWidth(), normalStateDrawable.getIntrinsicHeight());

            return stateListDrawable;

        }


    }

    private static Drawable getStateDrawable(StateListDrawable stateListDrawable, int[] state) {
        try {
            if (getStateDrawable == null || getStateDrawableIndex == null) {
                getStateDrawable = StateListDrawable.class.getMethod("getStateDrawable", int.class);
                getStateDrawableIndex = StateListDrawable.class.getMethod("getStateDrawableIndex", int[].class);
            }
            int index = (int) getStateDrawableIndex.invoke(stateListDrawable, state);
            if (index == -1) {
                return null;
            }
            Drawable drawable = (Drawable) getStateDrawable.invoke(stateListDrawable, index);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
