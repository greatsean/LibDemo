package com.dtds.mobileplatform.util;


/*****************************************
 * @description:过程（开始和结束）的回调接口
 * @author:lixiaohui
 * @date: 2017/10/25
 * @company:深圳动态网络科技有限公司
 *****************************************/
public interface ProcessCallback<T> {
    void onMethodStart(T... args);

    void onMethodEnd(T... args);

}
