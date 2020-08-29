package com.ubit.wallet.fragment;

import android.util.TypedValue;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ubit.wallet.R;
import com.ubit.wallet.utils.Constants;
import com.ubit.wallet.utils.CountDownUtil;

import org.greenrobot.eventbus.EventBus;

public class ForgetPasswordFragment extends BaseFragment {

    private int mGetVerCodeType = 0;  //0用手机号获取验证码，1用邮箱获取验证码

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forget_password;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvGetCode, R.id.tvOk);
        ((RadioGroup) view.findViewById(R.id.rGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMobile) {
                    mGetVerCodeType = 0;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    setViewVisible(R.id.llPhone);
                    setViewGone(R.id.tvEmail, R.id.etEmail);
                } else {
                    mGetVerCodeType = 1;
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
                new CountDownUtil(fv(R.id.tvGetCode), fv(R.id.etVerCode))
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
                break;
            case R.id.tvRight:
                goBack();
                break;
            case R.id.tvOk:
                break;
        }
    }
}
