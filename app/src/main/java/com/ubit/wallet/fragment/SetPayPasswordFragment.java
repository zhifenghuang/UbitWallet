package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.MD5Utils;

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
        int id = v.getId();
        switch (id) {
            case R.id.tvOk:
                String password1 = getTextById(R.id.etPayPassword);
                if (TextUtils.isEmpty(password1)) {
                    showToast(R.string.app_please_input_trade_password);
                    return;
                }
                String password2 = getTextById(R.id.etNewCheckPassword);
                if (TextUtils.isEmpty(password2)) {
                    showToast(R.string.app_please_input_sure_trade_password);
                    return;
                }
                if (!password1.equals(password2)) {
                    showToast(R.string.app_pay_password_not_equal_check_password);
                    return;
                }
                HttpMethods.getInstance().modify_pay_password(
                        DataManager.getInstance().getToken(),
                        MD5Utils.encryptMD5(password1), MD5Utils.encryptMD5(password2), new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                                int hasPsw = myInfo.getHasPayPsw();
                                myInfo.setHasPayPsw(1);
                                DataManager.getInstance().saveUserInfo(myInfo);
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                getActivity().finish();
                                showToast(hasPsw == 1 ? R.string.app_modify_password_success : R.string.app_set_password_success);
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }
}
