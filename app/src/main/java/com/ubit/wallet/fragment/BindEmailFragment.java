package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;

public class BindEmailFragment extends BaseFragment {


    private String mSid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_email;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvOk);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvOk:
                gotoPager(SelectCountryCodeFragment.class);
                break;
        }
    }
}
