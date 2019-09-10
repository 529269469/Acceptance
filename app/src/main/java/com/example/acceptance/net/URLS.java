package com.example.acceptance.net;


import com.example.acceptance.base.MyApplication;

public class URLS {

    public static final String HTTP = "http://192.168.10.105:2001";


    //签名存储
    public static final String SINGA = MyApplication.getInstances().getExternalFilesDir("SINGA") + "";
    //图片存储
    public static final String JPGIMG = MyApplication.getInstances().getExternalFilesDir("CPDA") + "";
    //头像存储
    public static final String HEADERIMG = MyApplication.getInstances().getExternalFilesDir("CPDAImg") + "";
}
