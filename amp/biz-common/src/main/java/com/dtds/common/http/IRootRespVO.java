package com.dtds.common.http;


/*****************************************
 * @description:通用根节点返回VO接口类
 * @author:lixiaohui
 * @date: 2017/10/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public interface IRootRespVO<T> {

    public String getMsg();

    public String getStatus();

    public T getData();

    public boolean isSuccess();

}
