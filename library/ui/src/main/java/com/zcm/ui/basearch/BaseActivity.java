package com.zcm.ui.basearch;

import com.zcm.ui.permission.PermissionsActivity;
import com.zcm.ui.skin.base.BaseSkinActivity;

/**
 * Created by zcm on 2018/1/3.
 * 基类Activity，添加皮肤管理
 * 添加动态权限申请
 */

public abstract class BaseActivity extends BaseSkinActivity {
    /**
     * 运行时权限权限申请函数
     * @param requestCode
     * @param permissions
     */
    protected void requiredPermission(int requestCode, String... permissions){
        PermissionsActivity.startActivityForResult(this,requestCode,permissions);
    }
}
