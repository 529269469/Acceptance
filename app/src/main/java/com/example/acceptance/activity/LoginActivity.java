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
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(intent, 1);


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
                            Log.e("TAG", "upLoadFilePath: " + upLoadFilePath);
                            Log.e("TAG", "upLoadFileName: " + upLoadFileName);

                            String upLoadFile = upLoadFilePath.substring(0, upLoadFilePath.length() - 4);
                            Log.e("TAG", "upLoadFile: " + upLoadFile);



                            try {
                                ZipUtils2.UnZipFolder(upLoadFilePath, upLoadFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            filePath(upLoadFile,upLoadFileName);

                        }
                    }
                }
            }
        }
    }

    private void filePath(String upLoadFile,String upLoadFileName) {
        File files = new File(upLoadFile);
        File[] subFile = files.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            String filename = subFile[i].getName();
            File file = new File(filename);
            /* 取得扩展名 */
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
            if (end.equals("xml")) {
                input(subFile[i],upLoadFileName,upLoadFile);
                break;
            }

        }
    }

    private void input(File subFile,String upLoadFileName,String upLoadFile) {
        String content = "";
        try {
            InputStream instream = new FileInputStream(subFile);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                content += line + "\n";
            }
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "onActivityResult: " + subFile);
//        Log.e("TAG", "onActivityResult: " + content);
        XStream xStream = new XStream();
        xStream.processAnnotations(DataPackageBean.class);
        DataPackageBean dataPackageBean = (DataPackageBean) xStream.fromXML(content);

        Log.e("TAG", "onActivityResult: " + dataPackageBean.toString());


        DataPackageDBeanDao dataPackageDBeanDao= MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        DataPackageDBean dataPackageDBean=new DataPackageDBean(null,
                upLoadFileName,
                upLoadFile,
                dataPackageBean.getId(),
                dataPackageBean.getName(),
                dataPackageBean.getCode(),
                dataPackageBean.getType(),
                dataPackageBean.getResponseUnit(),
                dataPackageBean.getModalCode(),
                dataPackageBean.getProductName(),
                dataPackageBean.getProductCode(),
                dataPackageBean.getProductType(),
                dataPackageBean.getBatch(),
                dataPackageBean.getCreator(),
                dataPackageBean.getCreateTime());
        dataPackageDBeanDao.insert(dataPackageDBean);

        CheckApplyBeanDao checkApplyBeanDao=MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        CheckApplyBean checkApplyBean=new CheckApplyBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getCheckApply().getId(),
                dataPackageBean.getCheckApply().getName(),
                dataPackageBean.getCheckApply().getCode(),
                dataPackageBean.getCheckApply().getContractCode(),
                dataPackageBean.getCheckApply().getContractName(),
                dataPackageBean.getCheckApply().getApplicant(),
                dataPackageBean.getCheckApply().getApplyCompany(),
                dataPackageBean.getCheckApply().getPhone(),
                dataPackageBean.getCheckApply().getConclusion(),
                dataPackageBean.getCheckApply().getDescription());
        checkApplyBeanDao.insert(checkApplyBean);

        CheckTaskBeanDao checkTaskBeanDao=MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        CheckTaskBean checkTaskBean=new CheckTaskBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getCheckTask().getId(),
                dataPackageBean.getCheckTask().getName(),
                dataPackageBean.getCheckTask().getCode(),
                dataPackageBean.getCheckTask().getIssuer(),
                dataPackageBean.getCheckTask().getIssueDept(),
                dataPackageBean.getCheckTask().getAccepter(),
                dataPackageBean.getCheckTask().getAcceptDate(),
                dataPackageBean.getCheckTask().getCheckDate(),
                dataPackageBean.getCheckTask().getApplicant(),
                dataPackageBean.getCheckTask().getApplyCompany(),
                dataPackageBean.getCheckTask().getPhone(),
                dataPackageBean.getCheckTask().getApplyDeptSet());
        checkTaskBeanDao.insert(checkTaskBean);

        ApplyItemBeanDao applyItemBeanDao=MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        for (int i = 0; i <dataPackageBean.getApplyItemSet().getApplyItem().size() ; i++) {
            ApplyItemBean applyItemBean=new ApplyItemBean(null,
                    dataPackageBean.getId(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getId(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductCodeName(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductCode(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductStatus(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsPureCheck(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsArmyCheck(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsCompleteChoice(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsCompleteRoutine(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsSatisfyRequire(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getDescription(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductName());
            applyItemBeanDao.insert(applyItemBean);
        }

        CheckFileBeanDao checkFileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        CheckGroupBeanDao checkGroupBeanDao=MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        PropertyBeanDao propertyBeanDao=MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        CheckItemBeanDao checkItemBeanDao=MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        PropertyBeanXDao propertyBeanXDao=MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        RelatedDocumentIdSetBeanDao relatedDocumentIdSetBeanDao=MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        for (int i = 0; i < dataPackageBean.getCheckFileSet().getCheckFile().size(); i++) {
            CheckFileBean checkFileBean=new CheckFileBean(null,
                    dataPackageBean.getId(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getName(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCode(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getDocType(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getConclusion(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckPerson());
            checkFileBeanDao.insert(checkFileBean);
            
            for (int j = 0; j <dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().size() ; j++) {
                CheckGroupBean checkGroupBean=new CheckGroupBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getGroupName(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckGroupConclusion(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckPerson(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsConclusion(),
                        dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsTable());
                checkGroupBeanDao.insert(checkGroupBean);



                try {
                    PropertyBean propertyBean=new PropertyBean(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getPropertySet().getProperty().getName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getPropertySet().getProperty().getValue());
                    propertyBeanDao.insert(propertyBean);
                }catch (Exception o){

                }


                try {
                    CheckItemBean checkItemBean=new CheckItemBean(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getOptions(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getSelected());
                    checkItemBeanDao.insert(checkItemBean);
                }catch (Exception o){

                }

                try {
                    PropertyBeanX propertyBeanX=new PropertyBeanX(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getPropertySet().getProperty().getName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getPropertySet().getProperty().getValue());
                    propertyBeanXDao.insert(propertyBeanX);
                }catch (Exception o){

                }

                try {
                    RelatedDocumentIdSetBean relatedDocumentIdSetBean=new RelatedDocumentIdSetBean(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().getRelatedDocumentIdSet().getRelatedDocumentId());
                    relatedDocumentIdSetBeanDao.insert(relatedDocumentIdSetBean);
                }catch (Exception o){

                }

              
            }



            
        }

        CheckVerdBeanDao checkVerdBeanDao=MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        CheckVerdBean checkVerdBean=new CheckVerdBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getCheckVerd().getId(),
                dataPackageBean.getCheckVerd().getName(),
                dataPackageBean.getCheckVerd().getCode(),
                dataPackageBean.getCheckVerd().getQConclusion(),
                dataPackageBean.getCheckVerd().getgConclusion(),
                dataPackageBean.getCheckVerd().getjConclusion(),
                dataPackageBean.getCheckVerd().getConclusion(),
                dataPackageBean.getCheckVerd().getCheckPerson());
        checkVerdBeanDao.insert(checkVerdBean);

        CheckUnresolvedBeanDao checkUnresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();
        CheckUnresolvedBean checkUnresolvedBean=new CheckUnresolvedBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getCheckUnresolved().getId(),
                dataPackageBean.getCheckUnresolved().getName(),
                dataPackageBean.getCheckUnresolved().getCode());
        checkUnresolvedBeanDao.insert(checkUnresolvedBean);

        UnresolvedBeanDao unresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        UnresolvedBean unresolvedBean=new UnresolvedBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getId(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getProductCode(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getQuestion(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getConfirmer(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getConfirmTime(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getFileId(),
                dataPackageBean.getUnresolvedSet().getUnresolved().getFileSet());
        unresolvedBeanDao.insert(unresolvedBean);

        DeliveryListBeanDao deliveryListBeanDao=MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        for (int i = 0; i <dataPackageBean.getDeliveryLists().getDeliveryList().size() ; i++) {
            try {
                DeliveryListBean deliveryListBean=new DeliveryListBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getIsParent(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getProject(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getParentId());
                deliveryListBeanDao.insert(deliveryListBean);
            }catch (Exception o){

            }

        }

        DocumentBeanDao documentBeanDao=MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        FileBeanDao fileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
        for (int i = 0; i < dataPackageBean.getDocumentListSet().getDocument().size(); i++) {
            DocumentBean documentBean=new DocumentBean(null,
                    dataPackageBean.getId(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getId(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getCode(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getName(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getSecret(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getPayClassify(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getModalCode(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getProductCodeName(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getProductCode(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getStage(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getTechStatus(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getApprover(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getApprovalDate(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getIssl(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getConclusion(),
                    dataPackageBean.getDocumentListSet().getDocument().get(i).getDescription());
            documentBeanDao.insert(documentBean);

            try {
                FileBean fileBean=new FileBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getId(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().getName(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().getPath(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().getType() );
                fileBeanDao.insert(fileBean);
            }catch (Exception o){

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
