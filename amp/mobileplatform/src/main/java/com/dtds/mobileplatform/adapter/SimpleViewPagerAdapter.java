package com.dtds.mobileplatform.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*****************************************
 * @description: [代码复用]简单通用View级别的ViewPager适配器
 * @author:lixiaohui
 * @date: 2017/8/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class SimpleViewPagerAdapter extends PagerAdapter {

    private List<View> mViews;

    public SimpleViewPagerAdapter(List<View> view) {
        this.mViews = view;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(view);
        return view;

    }
}