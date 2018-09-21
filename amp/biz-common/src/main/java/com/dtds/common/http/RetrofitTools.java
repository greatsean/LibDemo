package com.dtds.common.http;

import android.support.annotation.NonNull;

import com.dtds.common.ui.AppBaseActivity;
import com.dtds.common.abs.BizCommonBridge;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*****************************************
 * @description:Retrofit工具类
 * @author:lixiaohui
 * @date: 2017/9/19
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class RetrofitTools {

    /**
     * 使用默认的拦截器
     * @param basePath
     * @param clz
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> T getApiService(String basePath, Class<T> clz) {
        boolean isShowLog = BizCommonBridge.findNativeImplement().isShowLog();
        //日志输出拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isShowLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)//网络请求连接超时时间30秒
                .retryOnConnectionFailure(false)		// 取消重连
                .readTimeout(30, TimeUnit.SECONDS)		// 读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)		// 写入超时时间
                .addInterceptor(logging)//添加日志输出
                .addInterceptor(new DefaultHttpParamsInterceptor());//添加日志输出

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(basePath)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(clz);
    }

    /**
     * 创建api服务
     *
     * @param basePath
     * @param clz
     * @param interceptors
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> T getApiService(String basePath, Class<T> clz, Interceptor[] interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS);//网络请求连接超时时间30秒
        if (interceptors != null) {//如果有设置其他拦截器,循环添加
            for (int i = 0; i < interceptors.length; i++) {
                builder.addInterceptor(interceptors[i]);//添加公共参数的截器
            }
        }
        //生成客户端对象
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(basePath)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(clz);
    }

    /**
     * 创建网络请求订阅
     *
     * @param act
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Subscription createNetworkSubscription(AppBaseActivity act, Observable<IRootRespVO<T>> observable, CommHttpSubscriber<T> subscriber) {
        Subscription subscription = createNetworkSubscription(observable, subscriber);
        act.addSubscription(subscription);
        return subscription;
    }

    /**
     * 创建一个通用的网络请求的订阅
     *
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public static <T> Subscription createNetworkSubscription(Observable<IRootRespVO<T>> observable, CommHttpSubscriber<T> subscriber) {
        Subscription subscription = observable.map(new CommRespFunc1<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        return subscription;
    }

}
