package com.example.acceptance.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.core.content.FileProvider;

import com.example.acceptance.R;
import com.example.acceptance.activity.MainActivity;
import com.example.acceptance.base.MyApplication;

import java.io.File;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/11 16
 */
public class PopupWindowUtils {

    private static PopupWindowUtils instance = null;
    private PopupWindow popupWindow;

    private PopupWindowUtils () {}
    public static  PopupWindowUtils getInstance() {
        if (instance == null) {
            synchronized (PopupWindowUtils.class) {
                if (instance == null) {
                    instance=new PopupWindowUtils();
                }
            }

        }else {
            instance.dismiss();
        }
        return instance;
    }

    private void dismiss() {
        popupWindow.dismiss();
    }


    public void getPopup(Activity context, int i){
        View poview = context.getLayoutInflater().inflate(R.layout.poview, null);
        popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = MyApplication.mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        MyApplication.mContext.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = MyApplication.mContext.getWindow().getAttributes();
            lp1.alpha = 1f;
            MyApplication.mContext.getWindow().setAttributes(lp1);
        });

        popupWindow.showAtLocation(poview , Gravity.CENTER,0,0);

        ImageView iv_camera=poview.findViewById(R.id.iv_camera);
        ImageView iv_video=poview.findViewById(R.id.iv_video);
        ImageView iv_files=poview.findViewById(R.id.iv_files);

        iv_camera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
            Uri uri = Uri.fromFile(cameraSavePath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            context.startActivityForResult(intent, 1);
        });
        iv_video.setOnClickListener(view -> {

        });
        iv_files.setOnClickListener(view -> {

        });
    }



}
