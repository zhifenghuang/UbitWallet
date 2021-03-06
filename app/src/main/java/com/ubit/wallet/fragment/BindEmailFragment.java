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
import com.ubit.wallet.utils.CountDownUtil;

import java.util.HashMap;

public class BindEmailFragment extends BaseFragment {


    private String mSid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bind_email;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvGetCode, R.id.tvOk);
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvGetCode:
                String email = getTextById(R.id.etEmail);
                if (TextUtils.isEmpty(email)) {
                    showToast(R.string.app_please_input_bind_email);
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
                break;
            case R.id.tvOk:
                final String newEmail = getTextById(R.id.etEmail);
                if (TextUtils.isEmpty(newEmail)) {
                    showToast(R.string.app_please_input_bind_email);
                    return;
                }
                String code = getTextById(R.id.etVerCode);
                if (TextUtils.isEmpty(code)) {
                    showToast(R.string.app_please_input_ver_code);
                    return;
                }
                final UserInfoBean.Info myInfo = DataManager.getInstance().getUserInfo();
                HttpMethods.getInstance().bind_email(
                        DataManager.getInstance().getToken(),
                        newEmail, mSid, code, new HttpObserver(new SubscriberOnNextListener() {
                            @Override
                            public void onNext(Object o, String msg) {
                                myInfo.setEmail(newEmail);
                                DataManager.getInstance().saveUserInfo(myInfo);
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                getActivity().finish();
                                showToast(R.string.app_bind_email_success);
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
