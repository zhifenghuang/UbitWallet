package com.ubit.wallet.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ubit.wallet.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

public class XFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public XFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

