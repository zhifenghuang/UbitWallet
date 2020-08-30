package com.ubit.wallet.http;

/**
 * Created by gigabud on 17-5-4.
 */

public interface OnHttpErrorListener {
    void onConnectError(Throwable e);
    void onServerError(int errorCode, String errorMsg);
}
