package com.zcm.ui.skin;

import android.view.View;

/**
 * Create by zcm on 2018/4/13 上午11:49
 */
public abstract class BaseAttr {

    public String attrName;
    public int attrValue;
    public String entryName;
    public String entryType;

    public abstract void apply(View view);
}
