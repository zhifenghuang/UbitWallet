package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;

public class UpdateNickFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_nick;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRight:
                break;
        }
    }
}
