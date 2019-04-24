package com.zcm.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PermissionUtil {
    private static final String TAG="PermissionUtil";

    public static void requestDrawOverlays(Context context) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
        if (!(context instanceof Activity)){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (intent.resolveActivity(context.getPackageManager())!=null){
            context.startActivity(intent);
        }else {
            Log.e(TAG, "No activity to handle intent" );
        }
    }

}
