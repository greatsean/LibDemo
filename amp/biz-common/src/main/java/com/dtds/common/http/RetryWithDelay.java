package com.dtds.common.http;

import com.dtds.common.http.ex.MustRetryException;
import com.dtds.mobileplatform.util.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/*****************************************
 * @description:延迟重试
 * @author:lixiaohui
 * @date: 2017/12/5
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class RetryWithDelay implements
        Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof MustRetryException && (++retryCount <= maxRetries)) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            LogUtil.i("RetryWithDelay", "get error, it will try after " + retryDelayMillis
                                    + " millisecond, retry count " + retryCount);
                            return Observable.timer(retryDelayMillis,
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}