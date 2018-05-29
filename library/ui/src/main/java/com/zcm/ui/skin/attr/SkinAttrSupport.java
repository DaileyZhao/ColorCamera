package com.zcm.ui.skin.attr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 15/9/23.
 */
public class SkinAttrSupport {
    public static List<IApply> getSkinAttrs(AttributeSet attrs, Context context, View view) {
        List<IApply> skinAttrs = new ArrayList<IApply>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            try {
                String attrName = attrs.getAttributeName(i);
                String attrValue = attrs.getAttributeValue(i);
                SkinAttrType attrType = getSupprotAttrType(attrName);

                boolean isFilterName = verifyFilterName(attrName);
                boolean isAttrType = attrType != null;
                if (isAttrType || isFilterName) {
                    // 找到了需要被皮肤系统响应的属性
                    if (attrValue.startsWith("@")) {
                        int id = Integer.parseInt(attrValue.substring(1));
                        if (id == 0) continue;
                        String entryName = context.getResources().getResourceEntryName(id);

                        if (isAttrType) {
                            if (entryName.startsWith(SkinAttr.ATTR_PREFIX)) {
                                // attr value name starts with 'skin_'
                                SkinAttr skinAttr = new SkinAttr(attrType, entryName);
                                skinAttr.setResId(id);
                                skinAttrs.add(skinAttr);
                            }
                        }

                        if (isFilterName) {
                            SkinAttr.bindFilterColor(view, attrName, entryName, id);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return skinAttrs;
    }

    private static SkinAttrType getSupprotAttrType(String attrName) {
        if (attrName != null) {
            return SkinAttrType.getSkinAttrTypeMap().get(attrName);
        }
        return null;
    }

    private static boolean verifyFilterName(String attrName) {
        if (attrName != null) {
            return SkinAttrType.getSkinAttrFilterList().contains(attrName);
        }
        return false;
    }
}
