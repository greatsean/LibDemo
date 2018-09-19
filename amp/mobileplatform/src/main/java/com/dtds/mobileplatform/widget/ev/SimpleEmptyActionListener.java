package com.dtds.mobileplatform.widget.ev;

import android.content.Context;

/*****************************************
 * @description:空数据加载监听简单实现类
 * @author:lixiaohui
 * @date: 2018/8/10
 * @company:深圳动态网络科技有限公司
 *****************************************/
public abstract class SimpleEmptyActionListener implements EmptyActionListener {

    @Override
    public void onEmptyViewNetworkSet(Context ctx) {
        //no op
    }
}
