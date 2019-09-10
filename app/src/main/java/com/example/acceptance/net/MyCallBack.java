package com.example.acceptance.net;


public interface MyCallBack<T> {
    void onSuccess(T t);
    void onFaile(String msg);
}
