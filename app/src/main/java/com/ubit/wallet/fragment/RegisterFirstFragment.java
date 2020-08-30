package com.ubit.wallet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.utils.Constants;
import com.ubit.wallet.utils.CountDownUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class RegisterFirstFragment extends BaseFragment {

    private int mRegisterType = 1;  //1是手机号注册，2是邮箱注册

    private String mPhoneCode = "86";

    private String mSid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_first;
    }

    @Override
    protected void onViewCreated(View view) {
        EventBus.getDefault().register(this);
        setViewsOnClickListener(R.id.tvRight, R.id.tvGetCode, R.id.tvNext);

        ((RadioGroup) view.findViewById(R.id.rGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMobile) {
                    mRegisterType = 1;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    setViewVisible(R.id.llPhone);
                    setViewGone(R.id.tvEmail, R.id.etEmail);
                } else {
                    mRegisterType = 2;
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvGetCode:
                if (mRegisterType == 1) {
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
            case R.id.tvNext:
                String account;
                if (mRegisterType == 1) {
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
                HttpMethods.getInstance().register1(String.valueOf(mRegisterType), account, verCode, mSid == null ? "" : mSid, mPhoneCode,
                        new HttpObserver(new SubscriberOnNextListener<HashMap<String, String>>() {
                            @Override
                            public void onNext(HashMap<String, String> map, String msg) {
                                if (getActivity() == null || getView() == null || map == null || !map.containsKey("user_id")) {
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.BUNDLE_EXTRA, map.get("user_id"));
                                gotoPager(RegisterSecondFragment.class, bundle);
                            }
                        }, getActivity(), (BaseActivity) getActivity()));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (str.equals(Constants.FINISH_ACTIVITY)) {
            goBack();
        }
    }


    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void startCountDown() {
        new CountDownUtil(fv(R.id.tvGetCode))
                .setCountDownMillis(60_000L)//倒计时60000ms
                .setHintAfterText("s")
                .setCountDownHint(getString(R.string.app_message_resend))
                .setCountDownColor(R.color.color_00_cf_7c, R.color.color_b2_b2_b2)

                .setCountDownBackgroundColor(R.drawable.shape_stroke_00_cf7c_12, R.drawable.shape_stroke_b2b2b2_12)
                .setOnTimeListener(new CountDownUtil.OnTimeFinishListener() {
                    @Override
                    public void onTimeFinish() {
                    }
                })
                .start();
    }
}
