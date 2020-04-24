package com.xiaochen.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * @author zlc
 * email : zlc921022@163.com
 * desc : 网络工具类
 */
@SuppressLint("MissingPermission")
public class NetWorkUtil {

    private NetWorkUtil() {
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context application context
     * @return true/false
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectManager == null) {
                return false;
            }
            NetworkInfo[] networkInfos = connectManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (NetworkInfo networkInfo : networkInfos) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查网络是否连接
     *
     * @param context
     */
    public static boolean isAvailableNetwork(Context context) {
        if (context == null) {
            return false;
        }
        if (!isNetworkAvailable(context)) {
            ToastUtil.showLongToast(context, "请检查网络是否连接");
            return false;
        }
        return true;
    }

    /**
     * 启动wifi设置页面
     *
     * @param context 全局上下文
     */
    public static void startNetSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开网络设置界面
     *
     * @param activity
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent();
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断wifi是否连接
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectManager == null) {
                return false;
            }
            NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo != null && networkInfo.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断wifi是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        if (context == null) {
            return false;
        }
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    /**
     * 打开或者关闭wifi
     *
     * @param context
     * @param enabled
     */
    public static void setWifiEnabled(Context context, boolean enabled) {
        if (context == null) {
            return;
        }
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return;
        }
        if (enabled) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } else {
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    /**
     * 判断移动网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isMobileConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectManager == null) {
                return false;
            }
            NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return networkInfo != null && networkInfo.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断移动数据是否打开
     */
    public static boolean isDataEnabled(Context context) {
        if (context == null) {
            return false;
        }
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method declaredMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            return declaredMethod != null && (boolean) declaredMethod.invoke(tm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开或关闭移动数据
     */
    public static void setDataEnabled(Context context, boolean enabled) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method setMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            setMobileDataEnabledMethod.invoke(tm, enabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前网络是否是4G网络
     *
     * @param context
     * @return
     */
    public static boolean is4G(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                return networkInfo.getType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
