package com.dtds.common.http.ex;

/*****************************************
 * @description:非预期数据异常
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class UnexpectedDataException extends RuntimeException {

    public UnexpectedDataException(String msg) {
        super(msg);
    }

    public UnexpectedDataException() {
        super("非预期数据异常");
    }

}
