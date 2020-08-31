package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;

public class SetPayPasswordFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_pay_password;
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
        int id=v.getId();
        switch (id){
            case R.id.tvOk:
                break;
        }
    }
}
