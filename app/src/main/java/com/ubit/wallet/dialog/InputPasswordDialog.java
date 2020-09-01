package com.ubit.wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ubit.wallet.R;
import com.ubit.wallet.activity.BaseActivity;
import com.ubit.wallet.http.HttpMethods;
import com.ubit.wallet.http.HttpObserver;
import com.ubit.wallet.http.SubscriberOnNextListener;
import com.ubit.wallet.manager.DataManager;
import com.ubit.wallet.utils.MD5Utils;

public class InputPasswordDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private EditText etPassword;

    private OnInputPasswordListener mOnInputPasswordListener;

    public InputPasswordDialog(Context context) {
        super(context, R.style.LoadingDialog);
        this.mContext = context;
        setContentView(R.layout.layout_input_password_dialog);

        etPassword = findViewById(R.id.etPassword);

        Window view = getWindow();
        WindowManager.LayoutParams lp = view.getAttributes();
        lp.width = ((BaseActivity) context).getDisplaymetrics().widthPixels; // 设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        view.setGravity(Gravity.BOTTOM);

        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.tvOk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideSoftInput();
        int id = v.getId();
        if (id == R.id.tvOk) {
            next();
        } else if (id == R.id.ivClose) {
            dismiss();
        }
    }

    private void next() {
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ((BaseActivity) mContext).showToast(R.string.app_please_input_trade_password);
            return;
        }
        HttpMethods.getInstance().check_pay_password(DataManager.getInstance().getToken(),
                MD5Utils.encryptMD5(password),
                new HttpObserver(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o, String msg) {
                        if (mOnInputPasswordListener != null) {
                            mOnInputPasswordListener.afterCheckPassword();
                        }
                        dismiss();
                    }
                }, mContext, (BaseActivity) mContext));
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput() {
        if (etPassword != null) {
            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(etPassword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setOnInputPasswordListener(OnInputPasswordListener onInputPasswordListener) {
        mOnInputPasswordListener = onInputPasswordListener;
    }


    public interface OnInputPasswordListener {
        public void afterCheckPassword();
    }
}

