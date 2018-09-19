package com.dtds.common.util;

import com.dtds.common.ui.AppBaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/*****************************************
 * @description:任务工具类
 * @author:lixiaohui
 * @date: 2018/8/9
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class TaskUtil {

    public static void runSimpleTask(final Runnable taskRun, final Runnable callbackRun, final AppBaseActivity baseAct, final boolean showLoading) {
        if (baseAct == null || taskRun == null) {
            return;
        }
        if (showLoading) {
            baseAct.showLoadingDialog();
        }
        Subscription subscribe = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                taskRun.run();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (showLoading) {
                    baseAct.dismissLoadingDialog();
                }
                if (callbackRun != null) {
                    callbackRun.run();
                }
            }
        });
        baseAct.addSubscription(subscribe);
    }
}
