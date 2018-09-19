/*
 * 文件名：BasicFragment.java
 * 版权：Copyright 2000-2100 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述：订单可视
 * 修改人：  zWX317775
 * 修改时间：2016年3月9日
 * 修改内容：新增 
 */
package com.dtds.mobileplatform.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/*****************************************
 * @Author:lixiaohui
 * @Description: fragment基类
 * @Date: 2017/8/25
 * @Company:深圳动态网络科技有限公司
 *****************************************/
public abstract class PlatformBaseFragment extends Fragment {

    /**
     * 是否首次加载过了
     */
    private boolean isFirstLoad = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isLazyLoadData()) {
            loadData();
        }
    }

    /**
     * 是否懒(调用loadData方法)加载数据
     *
     * @return
     */
    public boolean isLazyLoadData() {
        return false;
    }

    public void loadData() {
        //do nothing
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoadData() && isFirstLoad && isVisibleToUser) {//如果是懒加载且未加载过
            isFirstLoad = false;
            loadData();
        }
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof PlatformBaseActivity) {
            ((PlatformBaseActivity) activity).showLoadingDialog();
        }
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoadingDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof PlatformBaseActivity) {
            ((PlatformBaseActivity) activity).dismissLoadingDialog();
        }
    }


}