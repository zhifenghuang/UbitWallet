package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.utils.Constants;

import org.greenrobot.eventbus.EventBus;

public class RegisterSecondFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_second;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvOk);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRight:
                EventBus.getDefault().post(Constants.FINISH_ACTIVITY);
                goBack();
                break;
            case R.id.tvOk:
                break;
        }
    }
}
