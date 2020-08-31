package com.ubit.wallet.fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.CountDownUtil;
import com.ubit.wallet.utils.MD5Utils;

import java.util.HashMap;

public class ForgetPasswordFragment extends BaseFragment {

    private int mGetVerCodeType = 1;  //1用手机号获取验证码，2用邮箱获取验证码

    private String mPhoneCode = "86";

    private String mSid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_password;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvGetCode, R.id.tvOk, R.id.tvPhoneCode);
        ((RadioGroup) view.findViewById(R.id.rGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMobile) {
                    mGetVerCodeType = 1;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    setViewVisible(R.id.llPhone);
                    setViewGone(R.id.tvEmail, R.id.etEmail);
                } else {
                    mGetVerCodeType = 2;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    setViewGone(R.id.llPhone);
                    setViewVisible(R.id.tvEmail, R.id.etEmail);
                }
            }
        });
    }

    @Override
    public void updateUIText() {

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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvPhoneCode:
                gotoPager(SelectCountryCodeFragment.class);
                break;
            case R.id.tvGetCode:
                if (mGetVerCodeType == 1) {
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
                } else {
                    String email = getTextById(R.id.etEmail);
                    if (TextUtils.isEmpty(email)) {
                        showToast(R.string.app_please_input_email);
                        return;
                    }
                    HttpMethods.getInstance().get_email_code(email, new HttpObserver(new SubscriberOnNextListener<HashMap<String, String>>() {
                        @Override
                        public void onNext(HashMap<String, String> map, String msg) {
                            if (getActivity() == null || getView() == null) {
                                return;
                            }
                            if (map != null && map.containsKey("sid")) {
                                mSid = map.get("sid");
                            }
                            startCountDown();
                        }
                    }, getActivity(), (BaseActivity) getActivity()));
                }
                break;
            case R.id.tvRight:
                goBack();
                break;
            case R.id.tvOk:
                String account;
                if (mGetVerCodeType == 1) {
                    account = getTextById(R.id.etMobile);
                    if (TextUtils.isEmpty(account)) {
                        showToast(R.string.app_please_input_mobile);
                        return;
                    }
                } else {
                    account = getTextById(R.id.etEmail);
                    if (TextUtils.isEmpty(account)) {
                        showToast(R.string.app_please_input_email);
                        return;
                    }
                }
                String verCode = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(verCode)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                String password1 = getTextById(R.id.etNewPassword);
                if (TextUtils.isEmpty(password1)) {
                    showToast(R.string.app_please_input_new_password);
                    return;
                }
                String password2 = getTextById(R.id.etNewCheckPassword);
                if (TextUtils.isEmpty(password2)) {
                    showToast(R.string.app_please_input_check_new_password);
                    return;
                }
                if (!password1.equals(password2)) {
                    showToast(R.string.app_password_not_equal_check_password);
                    return;
                }
                String password = MD5Utils.encryptMD5(password1);
                HttpMethods.getInstance().find_pwd(account, String.valueOf(mGetVerCodeType), mPhoneCode, verCode, mSid, password, password,
                        new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                showToast(R.string.app_reset_password_success);
                                goBack();
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
