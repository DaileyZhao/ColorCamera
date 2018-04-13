package com.zcm.ui.skin.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zcm.ui.skin.BaseAttr;
import com.zcm.ui.skin.SkinView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zcm on 2018/4/13 上午11:56
 * 皮肤BaseActivity
 */
public class BaseSkinActivity extends AppCompatActivity{
    protected final String TAG = this.getClass().getSimpleName();

    private static final String DEFAULT_SCHEMA_NAME = "http://schemas.android.com/apk/res-auto";
    private static final String DEFAULT_ATTR_NAME = "enable";

    private List<SkinView> mSkinViews = new ArrayList<SkinView>();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //这里把需要换肤的View缓存下来
        View view=null;
        boolean skinEnable=attrs.getAttributeBooleanValue(DEFAULT_SCHEMA_NAME,DEFAULT_ATTR_NAME,false);
        if (skinEnable){
            view=createView(name, context, attrs);
            if (view!=null){
                parseAttrs(name,context,attrs,view);
            }
        }
        return view;
    }

    public final View createView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if(-1 == name.indexOf('.')) {
            if("View".equalsIgnoreCase(name)) {
                view = createView(name, context, attrs, "android.view.");
            }
            if(null == view) {
                view = createView(name, context, attrs, "android.widget.");
            }
            if(null == view) {
                view = createView(name, context, attrs, "android.webkit.");
            }
        } else {
            view = createView(name, context, attrs, null);
        }
        return view;
    }

    View createView(String name, Context context, AttributeSet attrs, String prefix) {
        View view = null;
        try {
            view = LayoutInflater.from(context).createView(name, prefix, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void parseAttrs(String name, Context context, AttributeSet attrs, View view) {
        int attrCount = attrs.getAttributeCount();
        final Resources temp = context.getResources();
        List<BaseAttr> viewAttrs = new ArrayList<BaseAttr>();
         for(int i = 0; i < attrCount; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if(isSupportedAttr(attrName)) {
                if(attrValue.startsWith("@")) {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = temp.getResourceEntryName(id);
                    String entryType = temp.getResourceTypeName(id);
                    Log.e(TAG, "parseAttrs: "+entryName+"\t"+entryType );
                    BaseAttr viewAttr = createAttr(attrName, attrValue, id, entryName, entryType);
                    if(null != viewAttr) {
                        viewAttrs.add(viewAttr);
                    }
                }
            }
        }

        if(viewAttrs.size() > 0) {
            SkinView skinView = new SkinView();
            skinView.view = view;
            skinView.viewAttrs = viewAttrs;
            mSkinViews.add(skinView);
        }
    }

    // attrName:textColor   attrValue:2130968576   entryName:common_bg_color   entryType:color
    private BaseAttr createAttr(String attrName, String attrValue, int id, String entryName, String entryType) {
        BaseAttr viewAttr = null;
        if("background".equalsIgnoreCase(attrName)) {
//            viewAttr = new BackgroundAttr();
        } else if("textColor".equalsIgnoreCase(attrName)) {
//            viewAttr = new TextColorAttr();
        }
        if(null != viewAttr) {
            viewAttr.attrName = attrName;
            viewAttr.attrValue = id;
            viewAttr.entryName = entryName;
            viewAttr.entryType = entryType;
        }
        return viewAttr;
    }

    private boolean isSupportedAttr(String attrName) {
        if("background".equalsIgnoreCase(attrName)) {
            return true;
        } else if("textColor".equalsIgnoreCase(attrName)) {
            return true;
        }
        return false;
    }

    public void applaySkin() {
        if(null != mSkinViews) {
            for(SkinView skinView : mSkinViews) {
                if(null != skinView.view) {
                    skinView.apply();
                }
            }
        }
    }
}
