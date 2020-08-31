package com.ubit.wallet.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.bean.UserBean;
import com.ubit.wallet.bean.UserInfoBean;

import java.lang.reflect.Type;

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager mDataManager;

    private Object mObject;

    private Gson mGson;

    private UserBean mMyInfo;

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

    public void saveMyInfo(UserBean bean) {
        mMyInfo = bean;
        Preferences.getInstacne().setValues("userBean", bean == null ? "" : getGson().toJson(bean));
    }

    public UserBean getMyInfo() {
        if (mMyInfo != null) {
            return mMyInfo;
        }
        String str = Preferences.getInstacne().getValues("userBean", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        mMyInfo = getGson().fromJson(str, UserBean.class);
        return mMyInfo;
    }

    public void saveMyAssets(AssetsBean bean) {
        Preferences.getInstacne().setValues("assets", bean == null ? "" : getGson().toJson(bean));
    }

    public AssetsBean getMyAssets() {
        String str = Preferences.getInstacne().getValues("assets", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, AssetsBean.class);
    }

    public void saveUserInfo(UserInfoBean.Info bean) {
        Preferences.getInstacne().setValues("userInfoBean", bean == null ? "" : getGson().toJson(bean));
    }

    public UserInfoBean.Info getUserInfo() {
        String str = Preferences.getInstacne().getValues("userInfoBean", "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return getGson().fromJson(str, UserInfoBean.Info.class);
    }


    public void loginOut() {
        mMyInfo = null;
        saveMyInfo(null);
        saveMyAssets(null);
        saveUserInfo(null);
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
