package com.zcm.ui.basearch;

import android.support.v7.app.AppCompatActivity;

import com.zcm.ui.permission.PermissionsActivity;

/**
 * Created by zcm on 2018/1/3.
 * 添加动态权限申请
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected void requiredPermission(int requestCode, String... permissions){
        PermissionsActivity.startActivityForResult(this,requestCode,permissions);
    }
}
