package com.dtds.common.http;

import com.dtds.common.http.ex.UnexpectedDataException;

import rx.functions.Func1;

/*****************************************
 * @description:通用返回处理
 * @author:lixiaohui
 * @date: 2017/9/19
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class CommRespFunc1<T> implements Func1<CommRespVO<T>, T> {

    @Override
    public T call(CommRespVO<T> baseBean) {
        if (!baseBean.isSuccess()) {
            throw new UnexpectedDataException(baseBean.getMsg() + "###" + baseBean.getStatus());
        }
        return baseBean.getData();
    }
}
