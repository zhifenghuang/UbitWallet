package com.ubit.wallet.bean;

import com.google.gson.Gson;

public class BasicResponse<T> {
    private int code; // 返回的结果标志 200成功
    private String msg;
    private T data;
    private T result;

    public boolean isSuccess() {
        return code == 200 || code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

