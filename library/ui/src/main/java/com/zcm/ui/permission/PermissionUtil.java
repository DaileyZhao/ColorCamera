package com.zcm.ui.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Create by zcm on 2018/8/16 上午11:28
 */
public class PermissionUtil {
    /**
     * 判断是否缺少权限  targetSdkVersion>=23时调用
     * @param context
     * @param permission
     * @return
     */
    public static boolean lacksPermission(Context context,String permission){
        return ContextCompat.checkSelfPermission(context, permission)==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 判断缺少权限组  targetSdkVersion>=23时调用
     */
    public static boolean lacksPermissions(Context context,String... permissions){
        for (String permission:permissions){
            if (lacksPermission(context,permission)){
                return true;
            }
        }
        return false;
    }
}
