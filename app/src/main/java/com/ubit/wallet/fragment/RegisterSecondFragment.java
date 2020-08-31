package com.ubit.wallet.fragment;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.activity.MainActivity;
import com.ubit.wallet.bean.PicCodeResultBean;
import com.ubit.wallet.bean.UserBean;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.OnHttpErrorListener;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.utils.BitmapUtil;
import com.ubit.wallet.utils.Constants;
import com.ubit.wallet.utils.MD5Utils;

import org.greenrobot.eventbus.EventBus;

public class RegisterSecondFragment extends BaseFragment {

    private String mUserId;

    private String mSid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register_second;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvRight, R.id.tvChange, R.id.tvOk);
        mUserId = getArguments().getString(Constants.BUNDLE_EXTRA, "");
        getCaptcha();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvRight:
                EventBus.getDefault().post(Constants.FINISH_ACTIVITY);
                goBack();
                break;
            case R.id.tvChange:
                getCaptcha();
                break;
            case R.id.tvOk:
                if (TextUtils.isEmpty(mSid)) {
                    getCaptcha();
                    return;
                }
                String password1 = getTextById(R.id.etPassword);
                if (TextUtils.isEmpty(password1)) {
                    showToast(R.string.app_please_input_password);
                    return;
                }
                String password2 = getTextById(R.id.etCheckPassword);
                if (TextUtils.isEmpty(password2)) {
                    showToast(R.string.app_please_input_check_password);
                    return;
                }
                if (!password1.equals(password2)) {
                    showToast(R.string.app_password_not_equal_check_password);
                    return;
                }
                String inviteCode = getTextById(R.id.etInviteCode);
                String picVerCode = getTextById(R.id.etPicVerCode);
                if (TextUtils.isEmpty(picVerCode)) {
                    showToast(R.string.app_please_input_pic_ver_code);
                    return;
                }
                String password = MD5Utils.encryptMD5(password1);
                HttpMethods.getInstance().register2(mUserId, password, password, inviteCode, picVerCode, mSid,
                        new HttpObserver(new SubscriberOnNextListener<UserBean>() {
                            @Override
                            public void onNext(UserBean bean, String msg) {
                                if (getActivity() == null || getView() == null) {
                                    return;
                                }
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
        }
    }

    private void getCaptcha() {
        HttpMethods.getInstance().captcha(new HttpObserver(new SubscriberOnNextListener<PicCodeResultBean>() {
            @Override
            public void onNext(PicCodeResultBean bean, String msg) {
                if (getActivity() == null) {
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
