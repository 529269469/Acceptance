package com.example.acceptance.net;

import java.util.Map;


public interface IHttp {
    <T> void get(String url, Map<String, String> params, MyCallBack<T> callBack);
    <T> void post(String url, Map<String, String> params, MyCallBack<T> callBack);
}

