package com.dtds.mobileplatform.widget.ev;

import android.content.Context;

/*****************************************
 * @description:空数据加载监听
 * @author:lixiaohui
 * @date: 2018/8/10
 * @company:深圳动态网络科技有限公司
 *****************************************/
public interface EmptyActionListener {

    void onEmptyViewRetry(Object params);

    void onEmptyViewNetworkSet(Context ctx);

}
