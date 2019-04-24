package com.zcm.ui.basearch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zcm.ui.R;
import com.zcm.ui.changeskin.base.BaseSkinActivity;
import com.zcm.ui.permission.PermissionsActivity;
import com.zcm.ui.systembar.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zcm on 2018/1/3.
 * 基类Activity，添加皮肤管理
 * 添加动态权限申请
 */

public abstract class BaseActivity extends BaseSkinActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mThisActivity = this;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
        if (isSubscribedEvent()) {
            registerEventBus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(mThisActivity);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder = ButterKnife.bind(mThisActivity);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        unbinder = ButterKnife.bind(mThisActivity);
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return false;
    }

    protected boolean isSubscribedEvent() {
        return false;
    }

    /**
     * 获取主题色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(mThisActivity)) {
            EventBus.getDefault().register(mThisActivity);
        }
    }

    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(mThisActivity)) {
            EventBus.getDefault().unregister(mThisActivity);
        }
    }

    protected void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }

    protected void postStickyEvent(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 设置状态栏沉浸式
     */
    protected void setTranslucentStatus() {
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
     *
     * @param requestCode
     * @param permissions
     */
    protected void requiredPermission(int requestCode, String... permissions) {
        PermissionsActivity.startActivityForResult(this, requestCode, permissions);
    }
}
