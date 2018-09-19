package com.dtds.mobileplatform.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

/*****************************************
 * @description:android系统相关工具类
 * @author:lixiaohui
 * @date: 2017/9/6
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class AndroidUtil {

    private static final String TAG = "AndroidUtil";

    /**
     * 获取App版本号，如果获取失败返回-1，不会报错
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        if (context == null) {
            return -1;
        }
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * APP版本号
     *
     * @param context
     * @param packageName
     * @return
     */
    public static int getAppVersionCode(Context context, String packageName) {
        if (context == null) {
            return -1;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.w(TAG, e.getMessage(), e);
            return -1;
        }
    }

    /**
     * 获取App版本名称，如果获取失败返回null，不会报错
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.w(TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获得android设备的MAC地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String mac = "";
        if (context != null) {
            try {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    mac = info.getMacAddress();
                } else {
                    LogUtil.w(TAG, "WifiInfo is null,MAC address failed.");
                }
            } catch (Exception e) {
                LogUtil.w(TAG, e.getMessage(), e);
            }
        } else {
            LogUtil.w(TAG, "Context is null,MAC address failed.");
        }
        return mac;
    }

    /**
     * [获取序列号信息]
     *
     * @return 当前的序列号
     */
    public static String getSerialNumber() {
        return android.os.Build.SERIAL;
    }


    ///////////////////////////////////////////////以下方法来自第三方未被验证过的///////////////////////////////////////////////////////////////////

    /**
     * [获取IMEI信息]
     *
     * @return 当前的IMEI
     */
    public static String getIMEI(Context context) {
        String deviceId = "";
        if (context != null) {
            try {
                TelephonyManager systemService = (TelephonyManager) context.getSystemService(
                        Context.TELEPHONY_SERVICE);
                deviceId = systemService.getDeviceId();
            } catch (Exception e) {
                LogUtil.w(TAG, e.getMessage(), e);
            }
        } else {
            LogUtil.w(TAG, "Context is null,deviceId got failed.");
        }
        return deviceId;
    }

    /**
     * [获取ANDROID_ID信息]
     *
     * @return 当前的ANDROID_ID
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        if (context != null) {
            try {
                ContentResolver contentResolver = context.getContentResolver();
                androidId = Settings.Secure.getString(contentResolver,
                        Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                LogUtil.w(TAG, e.getMessage(), e);
            }
        } else {
            LogUtil.w(TAG, "Context is null,ANDROID_ID got failed.");
        }
        return androidId;
    }

    /**
     * [获取SIM序列号信息]
     *
     * @return 当前的SIM序列号
     */
    public static String getSimSerialNumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断指定App是否安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
