package com.dtds.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtds.mobileplatform.ui.PlatformBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/*****************************************
 * @description:
 * @author:lixiaohui
 * @date: 2017/9/23
 * @company:深圳动态网络科技有限公司
 *****************************************/
public abstract class AppBaseFragment extends PlatformBaseFragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        initView(inflater, container, savedInstanceState, view);
        return view;
    }

    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getLayoutId();

    /**
     * 添加订阅对象
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        if (getActivity() instanceof AppBaseActivity) {
            ((AppBaseActivity) getActivity()).addSubscription(subscription);
            return;
        }
    }

    @Override
    public void onDetach() {
        if (getActivity() instanceof AppBaseActivity) {//Fragment与Activity解除依附关系时，解除订阅
            ((AppBaseActivity) getActivity()).onUnsubscribe();
        }
        super.onDetach();
    }
}
