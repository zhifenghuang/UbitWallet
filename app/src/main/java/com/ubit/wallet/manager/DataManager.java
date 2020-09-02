package com.ubit.wallet.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ubit.wallet.bean.AssetsBean;
import com.ubit.wallet.bean.DataTypeBean;
import com.ubit.wallet.bean.UserInfoBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public String getToken() {
        return Preferences.getInstacne().getValues("token", "");
    }

    public void saveToken(String token) {
        Preferences.getInstacne().setValues("token", token == null ? "" : token);
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

    public void saveDataType(ArrayList<DataTypeBean.DataType> list) {
        Preferences.getInstacne().setValues("data_type", list == null ? "" : getGson().toJson(list));
    }

    public ArrayList<DataTypeBean.DataType> getDataTypes() {
        String str = Preferences.getInstacne().getValues("data_type", "");
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        return getGson().fromJson(str, new TypeToken<ArrayList<DataTypeBean.DataType>>() {
        }.getType());
    }


    public void loginOut() {
        saveToken(null);
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
