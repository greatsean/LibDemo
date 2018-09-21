package com.dtds.common.http;


/*****************************************
 * @description:通用返回VO类
 * @author:lixiaohui
 * @date: 2017/10/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class CommRespVO<T> implements IRootRespVO<T> {

    private String code;
    private String msg;
    private String status;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return "200".equals(getStatus());
    }

    @Override
    public String toString() {
        return "CommRespVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
