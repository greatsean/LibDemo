package com.dtds.common.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dtds.common.abs.BizCommonBridge;
import com.dtds.common.http.ex.UnexpectedDataException;
import com.dtds.common.ui.AppBaseActivity;
import com.dtds.mobileplatform.util.LogUtil;
import com.dtds.mobileplatform.util.NetworkUtil;
import com.dtds.mobileplatform.util.UiHelper;
import com.dtds.mobileplatform.widget.ev.IEmptyView;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;

/*****************************************
 * @description:公共的网络请求处理订阅者
 * 执行流程：
 * 成功：onStart→(onNext→)onFinish(false)→onSuccess→(onCompleted→)onLast(false);
 * 失败：onStart→onError→onFinish(true)→onLast(true);
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public abstract class CommHttpSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "CommHttpSubscriber";
    private SoftReference<Context> mActRef;
    private boolean mIgnoreNullData;//是否忽略null data
    private boolean mShowLoading = true;
    private IEmptyView mEmptyView;//空视图
    private String status;
    private String msg;

    /**
     * 加载框、toast、是否允许null值的 可配置的构造方法
     *
     * @param baseAct
     * @param ignoreNullData 是否忽略null data
     * @param isShowLoading  是否显示请求框
     */
    public CommHttpSubscriber(Context baseAct, boolean ignoreNullData, boolean isShowLoading, IEmptyView emptyView) {
        mActRef = new SoftReference<Context>(baseAct);
        mIgnoreNullData = ignoreNullData;
        mShowLoading = isShowLoading;
        mEmptyView = emptyView;
    }

    public CommHttpSubscriber(Context baseAct, boolean ignoreNullData, boolean isShowLoading) {
        this(baseAct, ignoreNullData, isShowLoading, null);
    }

    /**
     * 加载框、toast、是否允许null值的 可配置的构造方法
     *
     * @param baseAct
     * @param ignoreNullData 是否忽略null data
     */
    public CommHttpSubscriber(Context baseAct, boolean ignoreNullData) {
        this(baseAct, ignoreNullData, true);
    }

    /**
     * 有loading框，异常有toast提示的请求
     */
    public CommHttpSubscriber(Context baseAct) {
        this(baseAct, false);//返回null值认为请求不成功
    }

    /**
     * 是否任务Null data也是一种成功处理
     *
     * @param ignoreNullData
     */
    public CommHttpSubscriber(boolean ignoreNullData) {
        this(null, ignoreNullData);
    }

    /**
     * 后台静默请求，异常没有loading框，没有toast提示
     */
    public CommHttpSubscriber() {
        this(null, false);
    }

    @Override
    public void onStart() {
        Context context = mActRef.get();
        if (context instanceof AppBaseActivity && mShowLoading) {
            AppBaseActivity baseAct = (AppBaseActivity) context;
            baseAct.showLoadingDialog();
        }
        if (mEmptyView != null) {//刚开始加载隐藏
            mEmptyView.hiddenEmptyView();
        }
        super.onStart();
    }

    @Override
    public void onCompleted() {
        onLast(false);
    }

    @Override
    public void onError(Throwable e) {//统一处理异常情况
        onFinish(true);
        doCommonError(e, mActRef.get());
        onLast(true);
    }

    protected void doCommonError(Throwable e, Context baseAct) {
        String message = e.getMessage();
        if (e instanceof IOException) {
            if (!NetworkUtil.checkNet(BizCommonBridge.findNativeImplement().getApplicationContext())) {//手动检查下是否有网络更准确
                //无网络可能抛java.net.ConnectException: Failed to connect to /172.16.1.127:8060或者UnknownHostException 找不到主机，必定是无网络
                this.status = "";
                this.msg = "抱歉,当前没有网络支持!";

            } else if (e instanceof SocketTimeoutException) {
                this.status = "";
                this.msg = "请求超时,请检查当前网络环境.";

            } else {
                doOtherException(e, message);
            }
        } else if (e instanceof HttpException) {
            HttpException e2 = (HttpException) e;
            int code = e2.code();
            switch (code) {
                case 404://页面找不到，功能未上线或者已经下线
                    this.status = "";
                    this.msg = "页面找不到,请稍后重试.";
                    break;
                default://其他错误
                    this.status = "";
                    this.msg = "请求异常，请稍后重试.";
                    break;
            }
            Log.w(TAG, "onError: http exception:", e);
        } else if (e instanceof UnexpectedDataException) {
            //HTTP 200 正常返回，但是服务端返回异常数据
            String[] split = message.split("###", 2);
            String respMsg = split[0];
            String status = split.length > 1 ? split[1] : "";

            this.status = status;
            this.msg = respMsg;
            Log.w(TAG, "onError: Unexpected Data Exception:", e);
        } else {
            doOtherException(e, message);
        }

        if (baseAct != null) {//弹出toast
            if (isLoginTimeout(status)) {//token过期，重新登录
                UiHelper.toast(baseAct, "登录过期，请重新登录");//直接提示
                BizCommonBridge.findNativeImplement().onLoginTimeout(baseAct);
            } else {
                UiHelper.toast(baseAct, this.msg);//直接提示
            }
        }

        if (mEmptyView != null) {//显示错误信息在界面上
            mEmptyView.setEmptyReason(IEmptyView.EMPTY_REASON_CODE_LOAD_ERROR, msg);
            mEmptyView.showEmptyView();
        }
    }

    private void doOtherException(Throwable e, String message) {
        if (e instanceof Exception) {
            LogUtil.w(TAG, "doOtherException: 其他异常", (Exception) e);
        }
        if (TextUtils.isEmpty(message)) {
            this.msg = "未知异常，请尝试退出重启APP";
        } else {//有错误信息
            message = getJsonErrorMsg(e, message);
            this.msg = message;
        }
    }

    /**
     * 根据状态码判断是否超时
     *
     * @param statusCode
     * @return
     */
    protected boolean isLoginTimeout(String statusCode) {
        return "401".equals(statusCode) || "403".equals(statusCode);
    }

    /**
     * 获取json里面的错误
     *
     * @param e
     * @param message
     * @return
     */
    private String getJsonErrorMsg(Throwable e, String message) {
        if (e instanceof com.alibaba.fastjson.JSONException) {//处理那种后台返回data数据和前端处理的data数据类型不一致的情况,比如下单商品没库存的时候，下单接口返回的data是个列表数据，而正常的是普通对象数据
            //提取真正的错误信息
            String errorMsg = null;
            try {
                int startIndex = message.indexOf(", json :");
                int endIndex = message.lastIndexOf(", fieldName data");
                if (startIndex != -1) {
                    String json = message.substring(startIndex + 8, endIndex);
                    org.json.JSONObject jsonObject = new org.json.JSONObject(json);
                    errorMsg = jsonObject.getString("msg");
                }
            } catch (Exception je) {
                Log.w(TAG, "onError: Unexpected json Data:" + message, je);
            }
            if (!TextUtils.isEmpty(errorMsg)) {//不为空，使用这个错误信息
                message = errorMsg;
            }
        }
        return message;
    }


    @Override
    public void onNext(T t) {
        if (!mIgnoreNullData && t == null) {//不允许null值作为成功的状态
            onError(new RuntimeException("无数据，请稍后重试"));
        } else {
            onFinish(false);
            onSuccess(t);
        }
    }

    /**
     * 当前订阅结束
     *
     * @param isError 是否以错误结束
     */
    public void onFinish(boolean isError) {
        Context context = mActRef.get();
        if (context instanceof AppBaseActivity && mShowLoading) {
            AppBaseActivity baseAct = (AppBaseActivity) context;
            baseAct.dismissLoadingDialog();
        }
    }

    /**
     * 成功返回
     *
     * @param respObj
     */
    public abstract void onSuccess(T respObj);


    /**
     * 最后执行
     *
     * @param isError
     */
    public void onLast(boolean isError) {

    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 获得空视图
     *
     * @return
     */
    public IEmptyView getEmptyView() {
        return mEmptyView;
    }
}
