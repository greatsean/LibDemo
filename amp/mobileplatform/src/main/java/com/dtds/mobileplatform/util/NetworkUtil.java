package com.dtds.mobileplatform.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*****************************************
 * @description:网络工具
 * @author:lixiaohui
 * @date: 2017/11/22
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class NetworkUtil {
    /**
     * 判断网络是否连接
     */
    public static boolean checkNet(Context context) {
        if (context==null){
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();// 网络是否连接
    }

    /**
     * 判断是否为wifi联网
     */
    public static boolean isWiFi(Context cxt) {
        if (!checkNet(cxt)){
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE
        NetworkInfo.State state = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        return NetworkInfo.State.CONNECTED == state;
    }

}
