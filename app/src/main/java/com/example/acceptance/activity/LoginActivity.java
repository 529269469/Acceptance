package com.example.acceptance.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.acceptance.R;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.bean.DataPackageBean;
import com.example.acceptance.utils.ZipUtils2;
import com.example.acceptance.utils.command.ZipHelper;
import com.example.acceptance.utils.command.ZipUtils;
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
import java.util.zip.ZipFile;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    @Override
    protected void initView() {

        llNew.setOnClickListener(this);
        llFile.setOnClickListener(this);
        llChecklist.setOnClickListener(this);
        ivSetup.setOnClickListener(this);
        load();
    }


    public void load() {
//        boolean result = ZipHelper.loadBinary(this, "7zr");
//        Toast.makeText(this, "加载7zr结果：" + result, Toast.LENGTH_SHORT).show();

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
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(intent,1);


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
                startActivity(ChecklistActivity.openIntent(LoginActivity.this));
                break;
            case R.id.iv_setup:
                startActivity(SetupActivity.openIntent(LoginActivity.this));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(this, uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            String upLoadFilePath = file.toString();
                            String upLoadFileName = file.getName();

//                            try {
//                                ZipUtils2.UnZipFolder(upLoadFilePath,Environment.getExternalStorageDirectory()+"/数据包/P011");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }


                            startActivity(MainActivity.openIntent(LoginActivity.this));

//                            String content = ""; //文件内容字符串
//                            File file1 = new File(Environment.getExternalStorageDirectory()+"/数据包/P011/单机产品数据包.xml");
//                            try {
//                                InputStream instream = new FileInputStream(file1);
//                                InputStreamReader inputreader  = new InputStreamReader (instream,"gbk");
//                                BufferedReader buffreader = new BufferedReader(inputreader);
//                                String line;
//                                //分行读取
//                                while (( line = buffreader.readLine()) != null) {
//                                    content += line + "\n";
//                                }
//
//                                instream.close();
//
//
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            Log.e("TAG", "onActivityResult: "+content );
//                            XStream xStream=new XStream();
//                            xStream.processAnnotations(DataPackageBean.class);//这里需要注解是你自己根据xml写的bean类(下面附代码解释xml)
//                            DataPackageBean result = (DataPackageBean) xStream.fromXML(content);
//
//                            Log.e("TAG", "onActivityResult: "+result.toString() );


                        }
                    }
                }
            }
        }
    }


    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
//                Log.i(TAG,"isExternalStorageDocument***"+uri.toString());
//                Log.i(TAG,"docId***"+docId);
//                以下是打印示例：
//                isExternalStorageDocument***content://com.android.externalstorage.documents/document/primary%3ATset%2FROC2018421103253.wav
//                docId***primary:Test/ROC2018421103253.wav
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                Log.i(TAG,"isMediaDocument***"+uri.toString());
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
