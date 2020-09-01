package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.MD5Utils;

public class SetLoginPasswordFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_login_password;
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
                String oldPsw = getTextById(R.id.etOldPassword);
                if (TextUtils.isEmpty(oldPsw)) {
                    showToast(R.string.app_please_input_old_password);
                    return;
                }
                String password1 = getTextById(R.id.etNewPassword);
                if (TextUtils.isEmpty(password1)) {
                    showToast(R.string.app_please_input_new_login_password);
                    return;
                }
                String password2 = getTextById(R.id.etNewCheckPassword);
                if (TextUtils.isEmpty(password2)) {
                    showToast(R.string.app_please_input_new_login_password_again);
                    return;
                }
                if (!password1.equals(password2)) {
                    showToast(R.string.app_new_password_not_equal_check_password);
                    return;
                }
                HttpMethods.getInstance().modify_login_password(
                        DataManager.getInstance().getToken(),
                        MD5Utils.encryptMD5(oldPsw), MD5Utils.encryptMD5(password1), MD5Utils.encryptMD5(password2), new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                getActivity().finish();
                                showToast(R.string.app_modify_password_success);
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }
}
