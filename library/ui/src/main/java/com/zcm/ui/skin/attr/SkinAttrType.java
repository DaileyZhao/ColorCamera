package com.zcm.ui.skin.attr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zcm.ui.BuildConfig;
import com.zcm.ui.skin.BaseSkinManager;
import com.zcm.ui.skin.ResourceManager;
import com.zcm.ui.skin.utils.BaseSkinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 15/9/28.
 */
public abstract class SkinAttrType {
    public static final String SRC_NORMAL_FILTER = "srcNormalFilter";
    public static final String SRC_PRESS_FILTER = "srcPressFilter";
    public static final String SRC_DISABLE_FILTER = "srcDisableFilter";

    public static final String DRAWABLE_TOP_NORMAL_FILTER = "drawableTopNormalFilter";
    public static final String DRAWABLE_TOP_PRESS_FILTER = "drawableTopPressFilter";
    public static final String DRAWABLE_TOP_DISABLE_FILTER = "drawableTopDisableFilter";

    public static final String DRAWABLE_BOTTOM_NORMAL_FILTER = "drawableBottomNormalFilter";
    public static final String DRAWABLE_BOTTOM_PRESS_FILTER = "drawableBottomPressFilter";
    public static final String DRAWABLE_BOTTOM_DISABLE_FILTER = "drawableBottomDisableFilter";

    public static final String DRAWABLE_LEFT_NORMAL_FILTER = "drawableLeftNormalFilter";
    public static final String DRAWABLE_LEFT_PRESS_FILTER = "drawableLeftPressFilter";
    public static final String DRAWABLE_LEFT_DISABLE_FILTER = "drawableLeftDisableFilter";

    public static final String DRAWABLE_RIGHT_NORMAL_FILTER = "drawableRightNormalFilter";
    public static final String DRAWABLE_RIGHT_PRESS_FILTER = "drawableRightPressFilter";
    public static final String DRAWABLE_RIGHT_DISABLE_FILTER = "drawableRightDisableFilter";

    public static final String BACKGROUND_NORMAL_FILTER = "backgroundNormalFilter";
    public static final String BACKGROUND_PRESS_FILTER = "backgroundPressFilter";
    public static final String BACKGROUND_DISABLE_FILTER = "backgroundDisableFilter";

    public static final String TEXT_COLOR_NORMAL_FILTER = "textColorNormalFilter";
    public static final String TEXT_COLOR_PRESS_FILTER = "textColorPressFilter";
    public static final String TEXT_COLOR_DISABLE_FILTER = "textColorDisableFilter";


    static SkinAttrType BACKGROUD = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            try {
                Drawable drawable;
//                if (BaseSkinUtils.useSkin(view)) {
                    drawable = getAndApplyColorFilter(resName, resId, view,
                            BACKGROUND_NORMAL_FILTER, BACKGROUND_NORMAL_FILTER, BACKGROUND_DISABLE_FILTER);
//                } else {
//                    drawable = getDefResources().getDrawable(resId);
//                }
                view.setBackgroundDrawable(drawable);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    };
    static SkinAttrType TEXT_COLOR = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            ColorStateList colorlist;
            if (BaseSkinUtils.useSkin(view)) {
                colorlist = getResourceManager().getColorStateList(view.getContext(), resName, resId);
            } else {
                colorlist = getDefResources().getColorStateList(resId);
            }
            ((TextView) view).setTextColor(colorlist);
        }
    };
    static SkinAttrType TEXT_COLOR_FILTER = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                Integer normalColor = SkinAttr.getFilterColor(view, TEXT_COLOR_NORMAL_FILTER, BaseSkinUtils.useSkin(view));
                if (normalColor == null) {
                    if (BuildConfig.DEBUG) {
                        throw new AssertionError();
                    } else {
                        normalColor = 0;
                    }
                }
                Integer pressColor = SkinAttr.getFilterColor(view, TEXT_COLOR_PRESS_FILTER, BaseSkinUtils.useSkin(view));
                Integer disableColor = SkinAttr.getFilterColor(view, TEXT_COLOR_DISABLE_FILTER, BaseSkinUtils.useSkin(view));
                ((TextView) view).setTextColor(SkinAttr.getColorStateList(normalColor, pressColor, disableColor));
            }
        }
    };
    static SkinAttrType SRC = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof ImageView) {
                try {
                    Drawable drawable;
//                    if (BaseSkinUtils.useSkin(view)) {
                        drawable = getAndApplyColorFilter(resName, resId, view,
                                SRC_NORMAL_FILTER, SRC_PRESS_FILTER, SRC_DISABLE_FILTER);
//                    } else {
//                        drawable = getDefResources().getDrawable(resId);
//                    }
                    ((ImageView) view).setImageDrawable(drawable);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static SkinAttrType DRAWABLE_TOP = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                try {
                    Drawable drawable;
//                    if (BaseSkinUtils.useSkin(view)) {
                        drawable = getAndApplyColorFilter(resName, resId, view,
                                DRAWABLE_TOP_NORMAL_FILTER, DRAWABLE_TOP_PRESS_FILTER, DRAWABLE_TOP_DISABLE_FILTER);
//                    } else {
//                        drawable = getDefResources().getDrawable(resId);
//                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    TextView v = (TextView)view;
                    Drawable[] drawables = v.getCompoundDrawables();
                    drawables[1] = drawable;
                    ((TextView) view).setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static SkinAttrType DRAWABLE_BOTTOM = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                try {
                    Drawable drawable;
//                    if (BaseSkinUtils.useSkin(view)) {
                        drawable = getAndApplyColorFilter(resName, resId, view,
                                DRAWABLE_BOTTOM_NORMAL_FILTER, DRAWABLE_BOTTOM_PRESS_FILTER, DRAWABLE_BOTTOM_DISABLE_FILTER);
//                    } else {
//                        drawable = getDefResources().getDrawable(resId);
//                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    TextView v = (TextView)view;
                    Drawable[] drawables = v.getCompoundDrawables();
                    drawables[3] = drawable;
                    ((TextView) view).setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static SkinAttrType DRAWABLE_LEFT = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                try {
                    Drawable drawable;
//                    if (BaseSkinUtils.useSkin(view)) {
                        drawable = getAndApplyColorFilter(resName, resId, view,
                                DRAWABLE_LEFT_NORMAL_FILTER, DRAWABLE_LEFT_PRESS_FILTER, DRAWABLE_LEFT_DISABLE_FILTER);
//                    } else {
//                        drawable = getDefResources().getDrawable(resId);
//                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    TextView v = (TextView)view;
                    Drawable[] drawables = v.getCompoundDrawables();
                    drawables[0] = drawable;
                    ((TextView) view).setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static SkinAttrType DRAWABLE_RIGHT = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                try {
                    Drawable drawable;
//                    if (BaseSkinUtils.useSkin(view)) {
                        drawable = getAndApplyColorFilter(resName, resId, view,
                                DRAWABLE_RIGHT_NORMAL_FILTER, DRAWABLE_RIGHT_PRESS_FILTER, DRAWABLE_RIGHT_DISABLE_FILTER);
//                    } else {
//                        drawable = getDefResources().getDrawable(resId);
//                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    TextView v = (TextView)view;
                    Drawable[] drawables = v.getCompoundDrawables();
                    drawables[2] = drawable;
                    ((TextView) view).setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    };
    static SkinAttrType TEXT_COLOR_HINT = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof TextView) {
                try {
                    ColorStateList colorStateList;
                    if (BaseSkinUtils.useSkin(view)) {
                        colorStateList = getResourceManager().getColorStateList(view.getContext(), resName, resId);
                    } else {
                        colorStateList = getDefResources().getColorStateList(resId);
                    }
                    ((TextView) view).setHintTextColor(colorStateList);
                } catch (Throwable e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    static SkinAttrType DIVIDER = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof ListView) {
                Drawable drawable;
                if (BaseSkinUtils.useSkin(view)) {
                    drawable = getResourceManager().getDrawableByName(view.getContext(), resName, resId);
                } else {
                    drawable = getDefResources().getDrawable(resId);
                }
                if (drawable.getIntrinsicHeight() <= 0) { // ColorDrawable 的默认高度为-1
                    int dividerHeight = ((ListView)view).getDividerHeight();
                    ((ListView)view).setDivider(drawable);
                    ((ListView)view).setDividerHeight(dividerHeight);
                } else {
                    ((ListView)view).setDivider(drawable);
                }
            }
        }
    };

    static SkinAttrType CHILD_DIVIDER = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            if (view instanceof ExpandableListView) {
                Drawable drawable;
                if (BaseSkinUtils.useSkin(view)) {
                    drawable = getResourceManager().getDrawableByName(view.getContext(), resName, resId);
                } else {
                    drawable = getDefResources().getDrawable(resId);
                }
                ((ExpandableListView)view).setChildDivider(drawable);
            }
        }
    };

    static SkinAttrType INDETERMINATE_DRAWABLE = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            Drawable drawable;
            if (BaseSkinUtils.useSkin(view)) {
                drawable = getResourceManager().getDrawableByName(view.getContext(), resName, resId);
            } else {
                drawable = getDefResources().getDrawable(resId);
            }
            ((ProgressBar) view).setIndeterminateDrawable(drawable);
        }
    };

    static SkinAttrType PROGRESS_DRAWABLE = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            Drawable drawable;
            if (BaseSkinUtils.useSkin(view)) {
                drawable = getResourceManager().getDrawableByName(view.getContext(), resName, resId);
            } else {
                drawable = getDefResources().getDrawable(resId);
            }
            ((SeekBar) view).setProgressDrawable(drawable);
        }
    };

    static SkinAttrType THUMB = new SkinAttrType() {
        @Override
        public void apply(View view, String resName, int resId) {
            Drawable drawable;
            if (BaseSkinUtils.useSkin(view)) {
                drawable = getResourceManager().getDrawableByName(view.getContext(), resName, resId);
            } else {
                drawable = getDefResources().getDrawable(resId);
            }
            ((SeekBar) view).setThumb(drawable);
        }
    };

    public static Map<String, SkinAttrType> SKIN_ATTR_TYPE_MAP = new HashMap<>();

    static {
        BACKGROUD.attrType = "background";
        TEXT_COLOR.attrType = "textColor";
        TEXT_COLOR_HINT.attrType = "textColorHint";
        SRC.attrType = "src";
        DRAWABLE_LEFT.attrType = "drawableLeft";
        DRAWABLE_RIGHT.attrType = "drawableRight";
        DRAWABLE_TOP.attrType = "drawableTop";
        DRAWABLE_BOTTOM.attrType = "drawableBottom";
        DIVIDER.attrType = "divider";
        CHILD_DIVIDER.attrType = "childDivider";
        INDETERMINATE_DRAWABLE.attrType = "indeterminateDrawable";
        PROGRESS_DRAWABLE.attrType = "progressDrawable";
        THUMB.attrType = "thumb";
        TEXT_COLOR_FILTER.attrType = "textColorNormalFilter";

        SKIN_ATTR_TYPE_MAP.put(BACKGROUD.attrType, BACKGROUD);
        SKIN_ATTR_TYPE_MAP.put(TEXT_COLOR.attrType, TEXT_COLOR);
        SKIN_ATTR_TYPE_MAP.put(TEXT_COLOR_HINT.attrType, TEXT_COLOR_HINT);
        SKIN_ATTR_TYPE_MAP.put(SRC.attrType, SRC);
        SKIN_ATTR_TYPE_MAP.put(DRAWABLE_LEFT.attrType, DRAWABLE_LEFT);
        SKIN_ATTR_TYPE_MAP.put(DRAWABLE_RIGHT.attrType, DRAWABLE_RIGHT);
        SKIN_ATTR_TYPE_MAP.put(DRAWABLE_TOP.attrType, DRAWABLE_TOP);
        SKIN_ATTR_TYPE_MAP.put(DRAWABLE_BOTTOM.attrType, DRAWABLE_BOTTOM);
        SKIN_ATTR_TYPE_MAP.put(DIVIDER.attrType, DIVIDER);
        SKIN_ATTR_TYPE_MAP.put(CHILD_DIVIDER.attrType, CHILD_DIVIDER);
        SKIN_ATTR_TYPE_MAP.put(INDETERMINATE_DRAWABLE.attrType, INDETERMINATE_DRAWABLE);
        SKIN_ATTR_TYPE_MAP.put(PROGRESS_DRAWABLE.attrType, PROGRESS_DRAWABLE);
        SKIN_ATTR_TYPE_MAP.put(THUMB.attrType, THUMB);
        SKIN_ATTR_TYPE_MAP.put(TEXT_COLOR_FILTER.attrType, TEXT_COLOR_FILTER);
    }

    public static void addAttrType(SkinAttrType type) {
        if (type != null) {
            SKIN_ATTR_TYPE_MAP.put(type.getAttrType(), type);
        }
    }

    // -- 以上用于检查xml中定义的background, textColor, src，若资源以skin_开头，则为生成的View创建监听器，在换肤时自动更新样式 --

    private static List<String> SKIN_ATTR_FILTER_LIST = new ArrayList<>();

    static {
        SKIN_ATTR_FILTER_LIST.add(SRC_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(SRC_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(SRC_DISABLE_FILTER);

        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_TOP_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_TOP_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_TOP_DISABLE_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_BOTTOM_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_BOTTOM_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_BOTTOM_DISABLE_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_LEFT_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_LEFT_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_LEFT_DISABLE_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_RIGHT_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_RIGHT_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(DRAWABLE_RIGHT_DISABLE_FILTER);

        SKIN_ATTR_FILTER_LIST.add(BACKGROUND_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(BACKGROUND_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(BACKGROUND_DISABLE_FILTER);

        SKIN_ATTR_FILTER_LIST.add(TEXT_COLOR_NORMAL_FILTER);
        SKIN_ATTR_FILTER_LIST.add(TEXT_COLOR_PRESS_FILTER);
        SKIN_ATTR_FILTER_LIST.add(TEXT_COLOR_DISABLE_FILTER);
    }

    String attrType;

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String resName, int resId);

    public static Map<String, SkinAttrType> getSkinAttrTypeMap() {
        return SKIN_ATTR_TYPE_MAP;
    }

    public static List<String> getSkinAttrFilterList() {
        return SKIN_ATTR_FILTER_LIST;
    }

    static ResourceManager getResourceManager() {
        return BaseSkinManager.getInstance().getSkinResourceManager().getResourceManager();
    }

    static Resources getDefResources() {
        return BaseSkinManager.getInstance().getSkinResourceManager().getDefResources();
    }

    static Drawable getAndApplyColorFilter(String resName, int resId, View view, String normalColor, String pressColor, String disableColor) {
        boolean useSkin = BaseSkinUtils.useSkin(view);
        ResourceManager.DrawableInfo drawableInfo;
        Drawable drawable;
        if (useSkin) {
            drawableInfo = getResourceManager().getDrawableInfoByName(view.getContext(), resName, resId);
            drawable = drawableInfo.getDrawable();
        } else {
            drawableInfo = ResourceManager.DrawableInfo.ofDefault(getDefResources().getDrawable(resId));
            drawable = drawableInfo.getDrawable();
        }
        if (!drawableInfo.isSkin() && resName.startsWith(SkinAttr.ATTR_TINT_PREFIX)) {
            // 没有找到皮肤，检查文件名，并应用color filter
            drawable = SkinAttr.applyDrawableFilter(resName, drawable, resId,
                    SkinAttr.getFilterColor(view, normalColor, useSkin),
                    SkinAttr.getFilterColor(view, pressColor, useSkin),
                    SkinAttr.getFilterColor(view, disableColor, useSkin));
        }
        return drawable;
    }
}
