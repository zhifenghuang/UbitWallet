package com.ubit.wallet.activity;

import android.os.Bundle;

import com.ubit.wallet.R;
import com.ubit.wallet.fragment.LoginFragment;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoPager(LoginFragment.class);
        finish();
    }
}