package com.zcm.ui.skin.attr;

import android.view.View;


import com.zcm.ui.BuildConfig;
import com.zcm.ui.skin.utils.BaseSkinUtils;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhy on 15/9/22.
 */
public class SkinView {
    View view;
    List<IApply> attrs;

    public SkinView(View view, List<IApply> skinAttrs) {
        this.view = view;
        this.attrs = skinAttrs;
    }

    public void addApply(IApply apply) {
        attrs.add(apply);
    }

    public void addApplies(Collection<? extends IApply> applies) {
        attrs.addAll(applies);
    }

    public void apply() {
        if (view == null) return;
        for (IApply attr : attrs) {
            try {
                attr.apply(view, BaseSkinUtils.useSkin(view));
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }
}
