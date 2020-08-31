package com.ubit.wallet.fragment;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.activity.MainActivity;
import com.ubit.wallet.bean.PhoneCodeBean;
import com.ubit.wallet.bean.PicCodeResultBean;
import com.ubit.wallet.bean.UserBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.OnHttpErrorListener;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.BitmapUtil;
import com.ubit.wallet.utils.MD5Utils;

public class LoginFragment extends BaseFragment {

    private int mLoginType = 0;  //0是手机号登录，1是邮箱登录

    private String mSid;

    private String mPhoneCode = "86";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvLogin, R.id.tvForgetPassword, R.id.tvChange, R.id.tvPhoneCode);
        ((RadioGroup) view.findViewById(R.id.rGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMobile) {
                    mLoginType = 0;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    setViewVisible(R.id.llPhone);
                    setViewGone(R.id.tvEmail, R.id.etEmail);
                } else {
                    mLoginType = 1;
                    ((RadioButton) fv(R.id.rbMobile)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    ((RadioButton) fv(R.id.rbEmail)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 29);
                    setViewGone(R.id.llPhone);
                    setViewVisible(R.id.tvEmail, R.id.etEmail);
                }
            }
        });
        getCaptcha();
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
            case R.id.tvRight:
                gotoPager(RegisterFirstFragment.class);
                break;
            case R.id.tvLogin:
                if (TextUtils.isEmpty(mSid)) {
                    getCaptcha();
                    return;
                }
                String name;
                if (mLoginType == 0) {
                    name = getTextById(R.id.etMobile);
                    if (TextUtils.isEmpty(name)) {
                        showToast(R.string.app_please_input_mobile);
                        return;
                    }
                } else {
                    name = getTextById(R.id.etEmail);
                    if (TextUtils.isEmpty(name)) {
                        showToast(R.string.app_please_input_email);
                        return;
                    }
                }
                String password = getTextById(R.id.etPassword);
                if (TextUtils.isEmpty(password)) {
                    showToast(R.string.app_please_input_password);
                    return;
                }
                String code = getTextById(R.id.etPicVerCode);
                if (TextUtils.isEmpty(code)) {
                    showToast(R.string.app_please_input_pic_ver_code);
                    return;
                }
                HttpMethods.getInstance().login(name, MD5Utils.encryptMD5(password), code, mSid, mPhoneCode,
                        new HttpObserver(new SubscriberOnNextListener<UserBean>() {
                            @Override
                            public void onNext(UserBean bean, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                DataManager.getInstance().saveMyInfo(bean);
                                gotoPager(MainActivity.class);
                                ((BaseActivity) getActivity()).finishAllActivity();
                            }
                        }, getActivity(), new OnHttpErrorListener() {
                            @Override
                            public void onConnectError(Throwable e) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                showToast(R.string.app_net_work_error);
                                getCaptcha();
                            }

                            @Override
                            public void onServerError(int errorCode, String errorMsg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
                                if (!TextUtils.isEmpty(errorMsg)) {
                                    ((BaseActivity) getActivity()).errorCodeDo(errorCode, errorMsg);
                                }
                                getCaptcha();
                            }
                        }));
                break;
            case R.id.tvForgetPassword:
                gotoPager(ForgetPasswordFragment.class);
                break;
            case R.id.tvChange:
                getCaptcha();
                break;
        }
    }

    private void getCaptcha() {
        HttpMethods.getInstance().captcha(new HttpObserver(new SubscriberOnNextListener<PicCodeResultBean>() {
            @Override
            public void onNext(PicCodeResultBean bean, String msg) {
                if (getActivity() == null || getView() == null) {
                    return;
                }
                if (bean != null) {
                    Bitmap bitmap = BitmapUtil.base64ToBitmap(bean.getCode());
                    ((ImageView) fv(R.id.ivCaptcha)).setImageBitmap(bitmap);
                    mSid = bean.getSid();
                }
            }
        }, getActivity(), (BaseActivity) getActivity()));
    }
}
