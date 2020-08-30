package com.ubit.wallet.http;

public interface SubscriberOnNextListener<T> {
    void onNext(T t, String msg);
}
