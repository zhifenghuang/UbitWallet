package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;

public class SafeCenterFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_safe_center;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.llMobile, R.id.llEmail, R.id.llLoginPassword, R.id.llPayPassword);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llMobile:
                break;
            case R.id.llEmail:
                gotoPager(BindEmailFragment.class);
                break;
            case R.id.llLoginPassword:
                gotoPager(SetLoginPasswordFragment.class);
                break;
            case R.id.llPayPassword:
                gotoPager(SetPayPasswordFragment.class);
                break;
        }
    }
}
