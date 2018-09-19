package com.dtds.common.abs;

import android.content.Context;

import com.dtds.common.biz.advert.vo.AdvertVO;
import com.dtds.common.biz.webank.print.OrderPrintable;

import java.util.Map;

/*****************************************
 * @description:业务公共模块的抽象
 * @author:lixiaohui
 * @date: 2017/10/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public interface BizCommonInterface {

    /**
     * 创建对话框
     *
     * @param ctx
     * @return
     */
    LoadingDialogInterface newLoadingDialog(Context ctx);

    /**
     * 登录超时方法回调
     *
     * @param ctx
     */
    void onLoginTimeout(Context ctx);

    /**
     * 【调试用】是否显示日志
     *
     * @return
     */
    boolean isShowLog();

    /**
     * 【调试用】配置环境操作
     *
     * @param ctx
     */
    void configureEnvironment(Context ctx);

    /**
     * HTTP公共的查询参数
     *
     * @return
     */
    Map<String, String> getHttpCommonQueryParams();

    /**
     * HTTP公共的请求头参数
     *
     * @return
     */
    Map<String, String> getHttpCommonHeaderParams();

    /**
     * 广告点击后的处理逻辑
     *
     * @param ctx
     * @param advertVO
     */
    void onAdvertClick(Context ctx, AdvertVO advertVO);

    /**
     * 获得应用上下文
     *
     * @return
     */
    Context getApplicationContext();

    /**
     * 打印订单信息
     *
     * @param type
     * @return
     */
    OrderPrintable getOrderPrinter(Object type);
}
