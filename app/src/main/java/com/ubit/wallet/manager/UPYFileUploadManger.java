package com.ubit.wallet.manager;

import com.ubit.wallet.event.UploadAvatarEvent;
import com.ubit.wallet.utils.Constants;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class UPYFileUploadManger {

    private static final String TAG = "UPYFileUploadManger";

    private static UPYFileUploadManger mUPYFileUploadManger;

    private HashMap<String, Integer> mRetryMap = new HashMap<>();


    private UPYFileUploadManger() {
    }

    public static UPYFileUploadManger getInstance() {
        if (mUPYFileUploadManger == null) {
            synchronized (TAG) {
                if (mUPYFileUploadManger == null) {
                    mUPYFileUploadManger = new UPYFileUploadManger();
                }
            }
        }
        return mUPYFileUploadManger;
    }



    public void uploadFile(File file) {

        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.SPACE);
        paramsMap.put(Params.SAVE_KEY, String.format(Constants.AVATAR_SAVE_PATH, file.getName()));
        paramsMap.put(Params.CONTENT_LENGTH, file.length());
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");

        if (!mRetryMap.containsKey(file.getName())) {
            mRetryMap.put(file.getName(), 1);
        }

        //结束回调，不可为空
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, Response response, Exception error) {
                try {
                    String result = null;
                    if (response != null) {
                        result = response.body().string();
                    } else if (error != null) {
                        result = error.toString();
                    }
                    if (isSuccess) {
                        mRetryMap.remove(file.getName());
                        JSONObject jsonObject = new JSONObject(result);
                        String url = Constants.IMAGE_HTTP_HOST + jsonObject.getString("url");
                        EventBus.getDefault().post(new UploadAvatarEvent(isSuccess, url, ""));
                    } else {
                        int retry = 1;
                        if (mRetryMap.containsKey(file.getName())) {
                            retry = mRetryMap.get(file.getName());
                        }
                        if (retry < 3) {
                            mRetryMap.put(file.getName(), ++retry);
                            uploadFile(file);
                            return;
                        }
                        EventBus.getDefault().post(new UploadAvatarEvent(isSuccess, "", error.getMessage()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    int retry = 1;
                    if (mRetryMap.containsKey(file.getName())) {
                        retry = mRetryMap.get(file.getName());
                    }
                    if (retry < 3) {
                        mRetryMap.put(file.getName(), ++retry);
                        uploadFile(file);
                        return;
                    }
                    EventBus.getDefault().post(new UploadAvatarEvent(false, "", e.toString()));
                }

            }
        };
        UploadEngine.getInstance().formUpload(file, paramsMap, Constants.OPERATER, UpYunUtils.md5(Constants.PASSWORD), completeListener, null);
    }
}
