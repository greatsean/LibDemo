package com.dtds.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dtds.common.abs.BizCommonBridge;


/*****************************************
 * @description: 个人配置工具类
 * @author:lixiaohui
 * @date: 2017/8/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class PreferencesUtil {
    private static final String SPFILENAME = "app_sp";
    private static SharedPreferences sPreference;

    public static void init(Context context) {
        sPreference = context.getSharedPreferences(SPFILENAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getPreference() {
        if (sPreference == null) {
            init(BizCommonBridge.findNativeImplement().getApplicationContext());
        }
        return sPreference;
    }

    /**
     * @param key   关键字
     * @param value 值
     */
    public static boolean put(String key, boolean value) {
        //添加保存数据
        return getPreference().edit().putBoolean(key, value).commit();
    }

    public static boolean put(String key, String value) {
        //添加保存数据
        return getPreference().edit().putString(key, value).commit();
    }

    public static boolean put(String key, int value) {
        //添加保存数据
        return getPreference().edit().putInt(key, value).commit();
    }

    public static boolean put(String key, long value) {
        //添加保存数据
        return getPreference().edit().putLong(key, value).commit();
    }

    public static boolean put(String key, float value) {
        //添加保存数据
        return getPreference().edit().putFloat(key, value).commit();
    }

    /**
     * 清除指定配置
     * @param key
     * @return
     */
    public static boolean clear(String key) {
        return getPreference().edit().remove(key).commit();
    }

    /**
     * 清除所有配置
     */
    public static void clearAll() {
        getPreference().edit().clear().commit();
    }


    public static boolean getBoolean(String key, boolean defValue) {
        return getPreference().getBoolean(key, defValue);
    }

    /**
     * 获得字段
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return getPreference().getString(key, defValue);
    }

    public static long getLong(String key, Long defValue) {
        return getPreference().getLong(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return getPreference().getInt(key, defValue);
    }
}
