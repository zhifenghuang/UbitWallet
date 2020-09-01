package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.bean.UserInfoBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.CountDownUtil;

public class BindPhoneFragment extends BaseFragment {


    private String mPhoneCode = "86";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_phone;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvGetCode, R.id.tvOk, R.id.tvPhoneCode);
    }


    @Override
    public void onResume() {
        super.onResume();
        Object object = DataManager.getInstance().getObject();
        if (object instanceof PhoneCodeBean.CodeBean) {
            mPhoneCode = String.valueOf(((PhoneCodeBean.CodeBean) object).getCode());
            setText(R.id.tvPhoneCode, "+" + mPhoneCode);
        }
        DataManager.getInstance().setObject(null);
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvPhoneCode:
                gotoPager(SelectCountryCodeFragment.class);
                break;
            case R.id.tvGetCode:
                String phone = getTextById(R.id.etMobile);
                if (TextUtils.isEmpty(phone)) {
                    showToast(R.string.app_please_input_mobile);
                    return;
                }
                HttpMethods.getInstance().get_mobile_code(phone, mPhoneCode, new HttpObserver(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o, String msg) {
                        if (getActivity() == null || getView() == null) {
                            return;
                        }
                        startCountDown();
                    }
                }, getActivity(), (BaseActivity) getActivity()));
                break;
            case R.id.tvOk:
                final String newPhone = getTextById(R.id.etMobile);
                if (TextUtils.isEmpty(newPhone)) {
                    showToast(R.string.app_please_input_mobile);
                    return;
                }
                String code = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(code)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                final UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                HttpMethods.getInstance().bind_phone(
                        DataManager.getInstance().getToken(),
                        newPhone, mPhoneCode, code, new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                myInfo.setPhone(newPhone);
                                myInfo.setPhone_code(mPhoneCode);
                                DataManager.getInstance().saveUserInfo(myInfo);
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                getActivity().finish();
                                showToast(R.string.app_bind_phone_success);
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }

    private void startCountDown() {
        new CountDownUtil(fv(R.id.tvGetCode))
                .setCountDownMillis(60_000L)//倒计时60000ms
                .setHintAfterText("s")
                .setCountDownHint(getString(R.string.app_message_resend))
                .setCountDownColor(R.color.color_00_cf_7c, R.color.color_b2_b2_b2)
                .setCountDownBackgroundColor(R.drawable.shape_stroke_00cf7c_12, R.drawable.shape_stroke_b2b2b2_12)
                .setOnTimeListener(new CountDownUtil.OnTimeFinishListener() {
                    @Override
                    public void onTimeFinish() {
                    }
                })
                .start();
    }
}
