package com.ubit.wallet.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.ubit.wallet.R;
import com.ubit.wallet.fragment.LoginFragment;
import com.ubit.wallet.manager.DataManager;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (TextUtils.isEmpty(DataManager.getInstance().getToken())) {
            gotoPager(LoginFragment.class);
        } else {
            gotoPager(MainActivity.class);
        }
        finish();
    }
}