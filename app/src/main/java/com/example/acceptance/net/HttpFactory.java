package com.example.acceptance.net;


public class HttpFactory {
    /**
     * @return获取HttpUtils对象
     */
    public static HttpUtils create()
    {
        return HttpUtils.getInstance();
    }

}
