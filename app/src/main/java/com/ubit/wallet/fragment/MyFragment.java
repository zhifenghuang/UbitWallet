package com.ubit.wallet.fragment;

import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.manager.DataManager;

public class MyFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.llSafeCenter, R.id.llLogout, R.id.ivAvatar);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivAvatar:
                gotoPager(EditUserInfoFragment.class);
                break;
            case R.id.llSafeCenter:
                gotoPager(SafeCenterFragment.class);
                break;
            case R.id.llLogout:
                DataManager.getInstance().loginOut();
                ((BaseActivity) getActivity()).finishAllActivity();
                gotoPager(LoginFragment.class);
                break;
        }
    }
}
