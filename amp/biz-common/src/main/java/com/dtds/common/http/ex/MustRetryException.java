package com.dtds.common.http.ex;

/*****************************************
 * @description:必须重试的异常
 * @author:lixiaohui
 * @date: 2017/12/5
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class MustRetryException extends RuntimeException {

    public MustRetryException(String msg) {
        super(msg);
    }

    public MustRetryException() {
        super("必须重试的异常");
    }
}
