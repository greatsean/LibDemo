package com.dtds.mobileplatform.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.dtds.mobileplatform.BuildConfig;


/*****************************************
 * @description:日志处理类
 * @author:lixiaohui
 * @date: 2017/10/25
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class LogUtil {

    private static String DEFAULT_TAG = "dtdsApp";
    private static String sGlobalTag = DEFAULT_TAG;
    private static boolean sShowLog = BuildConfig.DEBUG;

    /**
     * 初始化
     *
     * @param isShowLog
     */
    public static void init(boolean isShowLog) {
        init(isShowLog, null);
    }

    /**
     * 初始化日志管理类
     *
     * @param isShowLog
     * @param tag
     */
    public static void init(boolean isShowLog, @Nullable String tag) {
        sShowLog = isShowLog;
        sGlobalTag = TextUtils.isEmpty(tag) ? DEFAULT_TAG : tag;
    }

    /**
     * 调试级别输出,使用默认tag
     *
     * @param msg
     */
    public static void d(String msg) {
        d(sGlobalTag, msg);
    }

    /**
     * 调试级别输出
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (sShowLog) {
            Log.d(tag, msg);
        }
    }

    /**
     * 信息级别输出,使用默认tag
     *
     * @param msg
     */
    public static void i(String msg) {
        i(sGlobalTag, msg);
    }

    /**
     * 信息级别输出
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (sShowLog) {
            Log.i(tag, msg);
        }
    }

    /**
     * 警告级别输出,使用默认tag
     *
     * @param msg
     */
    public static void w(String msg) {
        w(sGlobalTag, msg);
    }

    /**
     * 警告级别输出
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (sShowLog) {
            Log.w(tag, msg);
        }
    }

    /**
     * 警告级别输出
     *
     * @param tag
     * @param msg
     * @param e
     */
    public static void w(String tag, String msg, Exception e) {
        if (sShowLog) {
            Log.w(tag, msg, e);
        }
    }

    /**
     * error错误级别输出,使用默认tag
     *
     * @param msg
     */
    public static void e(String msg) {
        e(sGlobalTag, msg);
    }

    /**
     * error错误级别输出
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (sShowLog) {
            Log.e(tag, msg);
        }
    }

}
