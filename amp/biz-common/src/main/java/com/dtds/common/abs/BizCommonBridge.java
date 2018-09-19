package com.dtds.common.abs;

import com.dtds.mobileplatform.util.LogUtil;

/*****************************************
 * @description:业务公共模块与本地实现桥梁
 * @author:lixiaohui
 * @date: 2017/10/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class BizCommonBridge {
    private static final String TAG = "BizCommonBridge";
    private static BizCommonInterface sAppInstance;

    public static BizCommonInterface findNativeImplement() {
        if (sAppInstance != null) {
            return sAppInstance;
        }
        sAppInstance = newNativeImplement("com.dtds.common.BizCommonImplement");//查找
        //依然没找到直接报错
        if (sAppInstance == null) {
            throw new RuntimeException("必须编写com.dtds.common.BizCommonImplement.java类");
        }
        if (!(sAppInstance instanceof BizCommonInterface)) {
            throw new RuntimeException("com.dtds.common.BizCommonImplement.java类必须实现com.dtds.common.util.BizCommonInterface 接口");
        }
        return sAppInstance;
    }

    private static BizCommonInterface newNativeImplement(String clzName) {
        BizCommonInterface app = null;
        try {
            Class<?> aClass = Class.forName(clzName);
            app = (BizCommonInterface) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            LogUtil.w(TAG, e.getMessage());
        } catch (InstantiationException e) {
            LogUtil.w(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtil.w(TAG, e.getMessage());
        }
        return app;
    }
}
