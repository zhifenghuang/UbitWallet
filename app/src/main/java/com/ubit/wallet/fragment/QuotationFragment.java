package com.ubit.wallet.fragment;

import android.view.View;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ubit.wallet.R;
import com.ubit.wallet.adapter.XFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuotationFragment extends BaseFragment {

    private List<BaseFragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quotation;
    }

    @Override
    protected void onViewCreated(View view) {
        TabLayout tab_layout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        mFragments = new ArrayList<>();
        mFragments.add(new QuotationRecordFragment());
        mFragments.add(new TradeFragment());
        mFragments.add(new TradeFragment());
        String[] titles = new String[]{getString(R.string.app_trade_record),
                getString(R.string.app_simple_intro),
                getString(R.string.app_history_data)};

        FragmentPagerAdapter adapter = new XFragmentAdapter(getChildFragmentManager(), mFragments, titles);
        viewPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewPager);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }
}
