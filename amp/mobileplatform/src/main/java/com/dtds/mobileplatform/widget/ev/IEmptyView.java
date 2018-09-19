package com.dtds.mobileplatform.widget.ev;

/*****************************************
 * @description:空视图抽象接口
 * @author:lixiaohui
 * @date: 2018/8/10
 * @company:深圳动态网络科技有限公司
 *****************************************/
public interface IEmptyView {
    //空视图原因
    int EMPTY_REASON_CODE_DEFAULT = 0;//默认
    int EMPTY_REASON_CODE_NO_DATA = 1;//无数据
    int EMPTY_REASON_CODE_LOAD_ERROR = 2;//加载失败

    /**
     * 获得布局ID
     *
     * @return
     */
    int getLayoutId();

    /**
     * 设置空视图原因
     *
     * @param emptyReasonCode
     */
    void setEmptyReason(int emptyReasonCode);

    /**
     * 空视图原因
     *
     * @param emptyReasonCode
     * @param reasonMainStr
     */
    void setEmptyReason(int emptyReasonCode, String reasonMainStr);

    /**
     * 空视图原因
     * @param emptyReasonCode
     * @param reasonMainStr 主要文字描述
     * @param reasonSubStr 子信息描述
     */
    void setEmptyReason(int emptyReasonCode, String reasonMainStr, String reasonSubStr);

    /**
     * 显示空视图
     */
    void showEmptyView();

    /**
     * 隐藏空视图
     */
    void hiddenEmptyView();

    /**
     * 隐藏空视图
     */
    void setEmptyActionListener(EmptyActionListener listener);

}
