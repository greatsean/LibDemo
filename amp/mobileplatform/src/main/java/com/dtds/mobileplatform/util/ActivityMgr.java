package com.dtds.mobileplatform.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/*****************************************
 * @description:activity管理类，集中管理当前应用所有activity
 * @author:lixiaohui
 * @date: 2017/10/25
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class ActivityMgr {
    private static final String TAG = "ActivityMgr";
    private List<Activity> mActivitiesList = new ArrayList<>();
    private static ActivityMgr ourInstance = new ActivityMgr();

    public static ActivityMgr getInstance() {
        return ourInstance;
    }

    private ActivityMgr() {
    }

    public void finishAllActivity() {
        if (null == mActivitiesList) {
            return;
        }

        for (Activity activity : mActivitiesList) {
            if (null != activity && !activity.isFinishing()) {
                activity.finish();
            }
        }

        mActivitiesList.clear();
    }

    public List<Activity> getAllActivity() {
        return mActivitiesList;
    }

    public void addActivity(Activity activity) {
        mActivitiesList.add(activity);
    }

    /**
     * add by lixiaohui 2017/1/13
     * 移除指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mActivitiesList.remove(activity);
    }


    /**
     * 判断当前上下文是否能操作对话框
     *
     * @param context
     * @return
     */
    public static boolean canDoDialog(Context context) {
        return !activityIsFinish(context);
    }

    /**
     * 窗口是否已经结束
     *
     * @param ctx
     * @return
     */
    public static boolean activityIsFinish(Context ctx) {
        boolean isFinish = false;
        if (ctx == null) {
            isFinish = true;
        }
        if (ctx instanceof Activity) {
            Activity act = (Activity) ctx;
            isFinish = act.isFinishing();
        }
        return isFinish;
    }

    public static void startActivity(Context ctx, Class<?> clz) {
        if (ctx == null) {
            return;
        }
        ctx.startActivity(new Intent(ctx, clz));
    }
}
