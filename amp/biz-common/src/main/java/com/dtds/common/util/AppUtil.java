package com.dtds.common.util;

import android.content.Context;

import com.dtds.mobileplatform.util.AndroidUtil;

/*****************************************
 * @description:动态App相关的辅助工具类
 * @author:lixiaohui
 * @date: 2018/1/18
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class AppUtil {

    private AppUtil() {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * [获取序列号信息]
     *
     * @return 当前的序列号
     */
    public static String getDeviceID(Context context) {
        return AndroidUtil.getSerialNumber().trim().replace(" ", "")+"_"+AndroidUtil.getMacAddress(context) ;
    }

}
