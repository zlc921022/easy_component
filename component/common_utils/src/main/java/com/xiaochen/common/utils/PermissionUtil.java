package com.xiaochen.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 权限获取检查util
 */
public class PermissionUtil {

    private PermissionUtil() {
    }

    /**
     * 是否获取了权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean isGetPermission(@NonNull Context context, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isGet = true;
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    isGet = false;
                    break;
                }
            }
            return isGet;
        }
        return true;
    }
}
