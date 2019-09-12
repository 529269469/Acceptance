package com.example.acceptance.utils.command;

public class ZipCode {
    static {
        System.loadLibrary("native-lib");
    }

    public native static int exec(String cmd);


}
