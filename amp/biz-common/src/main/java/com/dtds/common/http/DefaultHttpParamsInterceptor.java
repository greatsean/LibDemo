package com.dtds.common.http;

import android.text.TextUtils;

import com.dtds.common.abs.BizCommonBridge;
import com.dtds.common.abs.BizCommonInterface;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*****************************************
 * @description:通用参数拦截器
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public final class DefaultHttpParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        BizCommonInterface nativeImplement = BizCommonBridge.findNativeImplement();

        HttpUrl.Builder urlBuilder = original.url().newBuilder();
        //追加公共的请求体参数
        Map<String, String> httpCommonQueryParams = nativeImplement.getHttpCommonQueryParams();
        if (null != httpCommonQueryParams) {
            Set<Map.Entry<String, String>> entries = httpCommonQueryParams.entrySet();
            for (Map.Entry<String, String> entry : entries) {//遍历所有键值对
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    urlBuilder.addQueryParameter(key, TextUtils.isEmpty(value) ? "" : value);
                }
            }
        }
        //构建HttpUrl对象
        HttpUrl url = urlBuilder.build();

        //添加公共请求头参数
        Request.Builder reqBuilder = original.newBuilder();
        Map<String, String> httpCommonHeaderParams = nativeImplement.getHttpCommonHeaderParams();
        if (null != httpCommonHeaderParams) {
            Set<Map.Entry<String, String>> entries = httpCommonHeaderParams.entrySet();
            for (Map.Entry<String, String> entry : entries) {//遍历所有键值对
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    reqBuilder.addHeader(key, TextUtils.isEmpty(value) ? "" : value);
                }
            }
        }
        //构建Request对象
        Request request = reqBuilder
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Connection", "close")
//                .addHeader("token", Networks.getInstance().token)
                .method(original.method(), original.body())
                .url(url)
                .build();
        return chain.proceed(request);
    }
}
