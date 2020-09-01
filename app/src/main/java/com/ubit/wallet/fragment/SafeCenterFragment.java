package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.manager.DataManager;

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
        final UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
        setText(R.id.tvPhone, TextUtils.isEmpty(myInfo.getPhone()) ? getString(R.string.app_not_setting) : "+" + myInfo.getPhone_code() + myInfo.getPhone());
        setText(R.id.tvEmail, TextUtils.isEmpty(myInfo.getEmail()) ? getString(R.string.app_not_setting) : myInfo.getEmail());
        setText(R.id.tvPayPswState, myInfo.getHasPayPsw() == 1 ? getString(R.string.app_modify) : getString(R.string.app_not_setting));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llMobile:
                gotoPager(BindPhoneFragment.class);
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
