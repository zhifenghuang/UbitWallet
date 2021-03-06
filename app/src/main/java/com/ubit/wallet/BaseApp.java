package com.ubit.wallet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ubit.wallet.manager.Preferences;

/**
 *
 */
@SuppressLint("Registered")
public class BaseApp extends Application {

    private int mActivityRecord;  //记录app是否处于app页面，0表示不在，1表示在

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        Preferences.getInstacne().setContext(this);
 //       CrashReport.initCrashReport(getApplicationContext(), "02cfa38619", false);
        mActivityRecord = 0;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                ++mActivityRecord;
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                --mActivityRecord;
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });

    }

}