package com.ubit.wallet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.dialog.MyDialogFragment;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.Constants;

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
                final UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                if (TextUtils.isEmpty(myInfo.getPhone())) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BUNDLE_EXTRA, 0);
                    gotoPager(BindPhoneFragment.class, bundle);
                } else {
                    showChangeMobileDialog();
                }
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

    private void showChangeMobileDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_change_phone_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView,
                        R.id.tvCancel,
                        R.id.tvChange);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvChange:
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.BUNDLE_EXTRA, 1);
                        gotoPager(BindPhoneFragment.class, bundle);
                        break;
                }
            }
        });
        dialogFragment.show(getActivity().getSupportFragmentManager(), "MyDialogFragment");
    }
}
