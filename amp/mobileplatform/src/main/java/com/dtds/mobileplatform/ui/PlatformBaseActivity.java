package com.dtds.mobileplatform.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dtds.mobileplatform.util.ActivityMgr;
import com.dtds.mobileplatform.util.LogUtil;

/*****************************************
 * @description: 所有activity基类
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public abstract class PlatformBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("ACTIVITY_MANAGER", this.getClass().getName() + ".onCreate");
        ActivityMgr.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        LogUtil.d("ACTIVITY_MANAGER", this.getClass().getName() + ".onDestroy");
        ActivityMgr.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 在activity中显示加载对话框
     */
    public abstract void showLoadingDialog();

    /**
     * 隐藏加载框
     */
    public abstract void dismissLoadingDialog();
}
