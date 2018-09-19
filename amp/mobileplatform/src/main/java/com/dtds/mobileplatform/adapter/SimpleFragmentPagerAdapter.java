package com.dtds.mobileplatform.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*****************************************
 * @description: [代码复用]简单通用Fragment级别的ViewPager适配器
 * @author:lixiaohui
 * @date: 2017/8/26
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}