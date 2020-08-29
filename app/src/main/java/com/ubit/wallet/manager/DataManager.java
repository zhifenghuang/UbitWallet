package com.ubit.wallet.manager;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager mDataManager;

    private Object mObject;

    private Gson mGson;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (mDataManager == null) {
            synchronized (TAG) {
                if (mDataManager == null) {
                    mDataManager = new DataManager();
                }
            }
        }
        return mDataManager;
    }

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }


    public void loginOut() {
    }


    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        this.mObject = object;
    }


    public static boolean isFirstStartApp() {
        return Preferences.getInstacne().getValues("isFirstStartApp", true);
    }

    public static void setFirstStartApp() {
        Preferences.getInstacne().setValues("isFirstStartApp", false);
    }

    public Object getObjectByKey(String key, Type tClass) {
        String str = Preferences.getInstacne().getValues(key, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, tClass);
    }

    public void saveObjectByKey(String key, Object object) {
        Preferences.getInstacne().setValues(key, getGson().toJson(object));
    }
}
