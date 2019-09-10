package com.example.acceptance.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by lxz on 2018/5/18.
 */

public class ToastUtils {
    private static volatile ToastUtils setGrid;
    private Toast toast = null;
    private Toast mToast;
    private TextView textView;

    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (setGrid == null) {
            synchronized (ToastUtils.class) {
                if (setGrid == null) {
                    setGrid = new ToastUtils();
                }
            }
        }
        return setGrid;
    }

//自定义——吐司
//    public void showToast(Context context, String text) {
//        if (toast != null) {
//            toast.cancel();
//        }
//        View toastView = LayoutInflater.from(context).inflate(R.layout.my_toast, null);
//
//        if (mToast == null) {
//            mToast = new Toast(context);
//            mToast.setView(toastView);
//            textView = (TextView) toastView.findViewById(R.id.tv_toast);
//            textView.setText(text);
//        } else {
//            textView.setText(text);
//        }
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.show();
//    }


    public void showTextToast(Context context, String msg) {

        if (mToast != null) {
            mToast.cancel();
        }

        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}



