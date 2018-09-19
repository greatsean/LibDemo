package com.dtds.mobileplatform.util;

import android.view.View;

/*****************************************
 * @description:防止两次以上的View重复点击
 * @author:lixiaohui
 * @date: 2017/11/17
 * @company:深圳动态网络科技有限公司
 *****************************************/
public abstract class OnNoDoubleClickListener implements View.OnClickListener {
    private int mThrottleFirstTime = 1000;
    private long mLastClickTime = 0L;

    public OnNoDoubleClickListener() {
    }

    public OnNoDoubleClickListener(int throttleFirstTime) {
        this.mThrottleFirstTime = throttleFirstTime;
    }

    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.mLastClickTime > (long) this.mThrottleFirstTime) {
            this.mLastClickTime = currentTime;
            this.onNoDoubleClick(v);
        }

    }

    public abstract void onNoDoubleClick(View v);
}