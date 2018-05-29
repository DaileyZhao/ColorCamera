package com.zcm.ui.basearch;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.zcm.ui.R;
import com.zcm.ui.permission.PermissionsActivity;
import com.zcm.ui.skin.base.BaseSkinActivity;

/**
 * Created by zcm on 2018/1/3.
 * 基类Activity，添加皮肤管理
 * 添加动态权限申请
 */

public abstract class BaseActivity extends BaseSkinActivity {
    protected final String TAG=this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 设置状态栏沉浸式
     */
    protected void setTranslucentStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 运行时权限权限申请函数
     * @param requestCode
     * @param permissions
     */
    protected void requiredPermission(int requestCode, String... permissions){
        PermissionsActivity.startActivityForResult(this,requestCode,permissions);
    }
}
