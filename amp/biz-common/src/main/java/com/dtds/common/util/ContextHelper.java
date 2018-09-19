package com.dtds.common.util;

import android.content.Context;

import com.dtds.common.abs.BizCommonBridge;
import com.dtds.common.abs.BizCommonInterface;

/*****************************************
 * @description:上下文助手
 * @author:lixiaohui
 * @date: 2018/1/9
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class ContextHelper {
    private boolean isNullCtx;
    private Context mContext;

    public ContextHelper(Context context) {
        mContext = context;
    }

    public boolean isNullContext() {
        return isNullCtx;
    }

    public Context getContext() {
        return mContext;
    }

    public ContextHelper safelyHelper() {
        if (mContext == null) {
            BizCommonInterface nativeImplement = BizCommonBridge.findNativeImplement();
            if (nativeImplement != null) {
                mContext = nativeImplement.getApplicationContext();
            } else {
                isNullCtx = true;
                return this;
            }
        }
        isNullCtx = false;
        return this;
    }
}