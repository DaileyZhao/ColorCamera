package com.zcm.ui.skin;

import android.view.View;

import java.util.List;

/**
 * Create by zcm on 2018/4/13 上午11:36
 */
public class SkinView {

    public View view;
    public List<BaseAttr> viewAttrs;

    public void apply() {
        if (view != null && viewAttrs != null){
            for (BaseAttr attr: viewAttrs){
                attr.apply(view);
            }
        }
    }
}
