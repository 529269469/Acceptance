package com.example.acceptance.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.DataPackageBean;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckApplyBean;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckGroupBean;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.CheckTaskBean;
import com.example.acceptance.greendao.bean.CheckUnresolvedBean;
import com.example.acceptance.greendao.bean.CheckVerdBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.PropertyBean;
import com.example.acceptance.greendao.bean.PropertyBeanX;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckApplyBeanDao;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckGroupBeanDao;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.CheckTaskBeanDao;
import com.example.acceptance.greendao.db.CheckUnresolvedBeanDao;
import com.example.acceptance.greendao.db.CheckVerdBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanDao;
import com.example.acceptance.greendao.db.PropertyBeanXDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.greendao.db.UnresolvedBeanDao;
import com.example.acceptance.utils.ZipUtils2;
import com.example.acceptance.view.ChangeTextViewSpace;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ll_new)
    LinearLayout llNew;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.ll_checklist)
    LinearLayout llChecklist;
    @BindView(R.id.iv_setup)
    ImageView ivSetup;
    @BindView(R.id.tv_tv)
    ChangeTextViewSpace tvTv;


    @Override
    protected void initView() {

        llNew.setOnClickListener(this);
        llFile.setOnClickListener(this);
        llChecklist.setOnClickListener(this);
        ivSetup.setOnClickListener(this);

        tvTv.setSpacing(20);
        tvTv.setText("欢迎登录下厂验收系统");
        load();
    }


    public void load() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDataAndEvent() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_new:
                startActivity(NewActivity.openIntent(LoginActivity.this));
                break;
            case R.id.ll_file:
                List<PermissionItem> permissionItems1 = new ArrayList<PermissionItem>();
                permissionItems1.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储", R.drawable.permission_ic_storage));
                HiPermission.create(this)
                        .permissions(permissionItems1)
                        .checkMutiPermission(new PermissionCallback() {
                            @Override
                            public void onClose() {
                            }

                            @Override
                            public void onFinish() {
                                startActivity(ToActivity.openIntent(LoginActivity.this));

                            }

                            @Override
                            public void onDeny(String permisson, int position) {
                            }

                            @Override
                            public void onGuarantee(String permisson, int position) {
                            }
                        });


                break;
            case R.id.ll_checklist:
                startActivity(ChecklistActivity.openIntent(LoginActivity.this,false,"",""));
                break;
            case R.id.iv_setup:
                startActivity(SetupActivity.openIntent(LoginActivity.this));
                break;
        }
    }






}
