package com.dtds.common.ui;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.dtds.common.abs.BizCommonBridge;
import com.dtds.common.abs.LoadingDialogInterface;
import com.dtds.mobileplatform.ui.PlatformBaseActivity;
import com.dtds.mobileplatform.util.ActivityMgr;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/*****************************************
 * @description:本APP定制的activity
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class AppBaseActivity extends PlatformBaseActivity {

    private LoadingDialogInterface mLoadingDialog;//数据加载框
    private int mCallLoadingCount = 0;//调用显示加载框的次数

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }


    /**
     * 添加订阅
     */
    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 取消所有订阅，以避免内存泄露
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }

    /**
     * 显示加载对话框（调用此方法将使用默认加载框样式）
     */
    @Override
    public void showLoadingDialog() {
        if (ActivityMgr.activityIsFinish(this)) {
            return;
        }

        //        mCallLoadingCount++;//调用一次累计一次
        if (mLoadingDialog == null) {
            mLoadingDialog = BizCommonBridge.findNativeImplement().newLoadingDialog(this);
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void dismissLoadingDialog() {//销毁前如果还有加载框未关闭调用关闭，该方法可连续调用多次
        if (ActivityMgr.activityIsFinish(this)) {
            return;
        }

        //        mCallLoadingCount--;//调用一次计减一次
        if (mCallLoadingCount == 0 && mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
