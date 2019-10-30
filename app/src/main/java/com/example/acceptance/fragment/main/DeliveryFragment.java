package com.example.acceptance.fragment.main;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.acceptance.R;
import com.example.acceptance.adapter.DeliveryAdapter;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.LegacyAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.CheckItemBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.RelatedDocumentIdSetBean;
import com.example.acceptance.greendao.db.CheckItemBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.RelatedDocumentIdSetBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 交付清单
 */
public class DeliveryFragment extends BaseFragment implements DeliveryAdapter.AddDelivery {
    @BindView(R.id.lv_list)
    ListView lvList;
    private File2Adapter fileAdapter;
    private String id;
    private List<DeliveryListBean> listBeans = new ArrayList<>();
    private DeliveryAdapter legacyAdapter;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .whereOr(DeliveryListBeanDao.Properties.ParentId.eq("null"), DeliveryListBeanDao.Properties.ParentId.eq(""))
                .list();
        listBeans.addAll(deliveryListBeans);
        legacyAdapter = new DeliveryAdapter(getActivity(), listBeans);
        lvList.setAdapter(legacyAdapter);

        legacyAdapter.setAddDelivery(this);


    }

    boolean isAdd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery;
    }

    @Override
    public void setAddDelivery(int position) {
        isAdd = false;
        addPopup2(listBeans,position);
    }

    @Override
    public void setDelivery(List<DeliveryListBean> deliveryListBeans, int position) {
        isAdd = true;
        addPopup2(deliveryListBeans,position);
    }


    private List<FileBean> fileBeans = new ArrayList<>();

    /*
     * 关联文件
     * */
    private void addPopup2(List<DeliveryListBean> deliveryListBeans,int pos) {
        View view = getLayoutInflater().inflate(R.layout.popup_add3, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setHeight(600);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);

        popupWindow.showAtLocation(lvList, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
            List<DeliveryListBean> deliveryListBeans2 = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                    .whereOr(DeliveryListBeanDao.Properties.ParentId.eq("null"), DeliveryListBeanDao.Properties.ParentId.eq(""))
                    .list();
            listBeans.clear();
            listBeans.addAll(deliveryListBeans2);
            legacyAdapter.notifyDataSetChanged();
        });

        EditText tv_code = view.findViewById(R.id.tv_code);
        EditText tv_name = view.findViewById(R.id.tv_name);
        EditText tv_payClassify = view.findViewById(R.id.tv_payClassify);
        EditText tv_secret = view.findViewById(R.id.tv_secret);
        EditText tv_techStatus = view.findViewById(R.id.tv_techStatus);
        EditText tv_approver = view.findViewById(R.id.tv_approver);
        EditText tv_approvalDate = view.findViewById(R.id.tv_approvalDate);
        Switch tv_issl = view.findViewById(R.id.tv_issl);
        EditText tv_conclusion = view.findViewById(R.id.tv_conclusion);
        TextView tv_file = view.findViewById(R.id.tv_file);
        EditText tv_description = view.findViewById(R.id.tv_description);
        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);
        MyListView lv_file = view.findViewById(R.id.lv_file);

        fileBeans.clear();
        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(pos).getId()))
                .list();
        if (documentBeans != null && !documentBeans.isEmpty()) {
            isAdd = true;
            tv_payClassify.setText(documentBeans.get(0).getPayClassifyName());
            tv_code.setText(documentBeans.get(0).getCode());
            tv_name.setText(documentBeans.get(0).getName());
            tv_secret.setText(documentBeans.get(0).getSecret());
            tv_techStatus.setText(documentBeans.get(0).getTechStatus());
            tv_approver.setText(documentBeans.get(0).getApprover());
            tv_approvalDate.setText(documentBeans.get(0).getApprovalDate());
            if (!StringUtils.isBlank(documentBeans.get(0).getIssl()) && documentBeans.get(0).getIssl().equals("true")) {
                tv_issl.setChecked(true);
            } else {
                tv_issl.setChecked(false);
            }
            tv_conclusion.setText(documentBeans.get(0).getConclusion());
            tv_description.setText(documentBeans.get(0).getDescription());

            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                    .list();
            for (int j = 0; j < fileBeanList.size(); j++) {
                fileBeans.add(fileBeanList.get(j));
            }

        }

        fileAdapter = new File2Adapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);
        fileAdapter.setOnDel(new File2Adapter.OnDel() {
            @Override
            public void onDel(int position) {
                DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                        .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                        .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(pos).getId()))
                        .list();
                FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                        .list();
                for (int j = 0; j < fileBeanList.size(); j++) {
                    if (fileBeanList.get(j).getUId().equals(fileBeans.get(position).getUId())) {
                        fileBeanDao.deleteByKey(fileBeanList.get(j).getUId());
                        fileBeans.remove(position);
                        break;
                    }
                }
                fileAdapter.notifyDataSetChanged();
            }
        });

        lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(id))
                        .list();
                File file = new File(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getPath());
                if (file.isFile() && file.exists()) {
                    try {
                        startActivity(OpenFileUtil.openFile(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getPath()));
                    } catch (Exception o) {
                    }
                } else {
                    try {
                        startActivity(OpenFileUtil.openFile(fileBeans.get(i).getPath()));
                    } catch (Exception o) {
                    }
                }

            }
        });
        tv_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

        tv_popup_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileBeans.isEmpty()) {
                    ToastUtils.getInstance().showTextToast(getActivity(), "请添加文件");
                    return;
                }
                DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                String deliveryListParentId = System.currentTimeMillis() + "";
                String documentId = System.currentTimeMillis() + "";

                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(pos).getId()))
                            .list();
                    DocumentBean documentBean = new DocumentBean(documentBeans.get(0).getUId(),
                            id,
                            documentBeans.get(0).getId(),
                            tv_code.getText().toString().trim(),
                            tv_name.getText().toString().trim(),
                            tv_secret.getText().toString().trim(),
                            documentBeans.get(0).getPayClassify(),
                            documentBeans.get(0).getPayClassifyName(),
                            documentBeans.get(0).getModalCode(),
                            documentBeans.get(0).getProductCodeName(),
                            documentBeans.get(0).getProductCode(),
                            documentBeans.get(0).getStage(),
                            tv_techStatus.getText().toString().trim(),
                            tv_approver.getText().toString().trim(),
                            tv_approvalDate.getText().toString().trim(),
                            tv_issl.isChecked() + "",
                            tv_conclusion.getText().toString().trim(),
                            tv_description.getText().toString().trim());
                    documentBeanDao.update(documentBean);
                } else {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    DocumentBean documentBean = new DocumentBean(null,
                            id,
                            documentId,
                            tv_code.getText().toString().trim(),
                            tv_name.getText().toString().trim(),
                            tv_secret.getText().toString().trim(),
                            deliveryListParentId,
                            tv_payClassify.getText().toString().trim(),
                            "",
                            "",
                            "",
                            "",
                            tv_techStatus.getText().toString().trim(),
                            tv_approver.getText().toString().trim(),
                            tv_approvalDate.getText().toString().trim(),
                            tv_issl.isChecked() + "",
                            tv_conclusion.getText().toString().trim(),
                            tv_description.getText().toString().trim());
                    documentBeanDao.insert(documentBean);
                }
                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(pos).getId()))
                            .list();
                    List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                            .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                            .where(DeliveryListBeanDao.Properties.Id.eq(documentBeans.get(0).getPayClassify()))
                            .list();
                    DeliveryListBean deliveryListBean = new DeliveryListBean(
                            deliveryListBeanList.get(0).getUId(),
                            id,
                            deliveryListBeanList.get(0).getId(),
                            false + "",
                            tv_payClassify.getText().toString(),
                            deliveryListBeanList.get(0).getParentId());
                    deliveryListBeanDao.update(deliveryListBean);
                } else {
                    DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                            id,
                            deliveryListParentId,
                            false + "",
                            tv_payClassify.getText().toString(),
                            deliveryListBeans.get(pos).getId());
                    deliveryListBeanDao.insert(deliveryListBean);
                }


                if (isAdd) {
                    DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                    List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                            .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                            .where(DocumentBeanDao.Properties.PayClassify.eq(deliveryListBeans.get(pos).getId()))
                            .list();
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                            .list();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        boolean isFile = false;
                        for (int j = 0; j < fileBeanList.size(); j++) {
                            if (fileBeans.get(i).getName().equals(fileBeanList.get(j).getName())) {
                                isFile = true;
                            }
                        }
                        if (!isFile) {
                            FileBean fileBean = new FileBean(null,
                                    id,
                                    documentBeans.get(0).getId(),
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getType(),
                                    tv_secret.getText().toString().trim());
                            fileBeanDao.insert(fileBean);

                            FileUtils.copyFile(fileBeans.get(i).getPath(), SPUtils.get(getActivity(), "path", "") + "/" + fileBeans.get(i).getName());
                        }

                    }
                } else {

                    FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(documentId))
                            .list();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        boolean isFile = false;
                        for (int j = 0; j < fileBeanList.size(); j++) {
                            if (fileBeans.get(i).getName().equals(fileBeanList.get(j).getName())) {
                                isFile = true;
                            }
                        }
                        if (!isFile) {
                            FileBean fileBean = new FileBean(null,
                                    id,
                                    documentId,
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getName(),
                                    fileBeans.get(i).getType(),
                                    tv_secret.getText().toString().trim());
                            fileBeanDao.insert(fileBean);
                        }

                    }
                }



                popupWindow.dismiss();
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(getActivity(), uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            String upLoadFilePath = file.toString();
                            String upLoadFileName = file.getName();
                            Log.e("TAG", "upLoadFilePath: " + upLoadFilePath);
                            Log.e("TAG", "upLoadFileName: " + upLoadFileName);
                            fileBeans.add(new FileBean(null, "", "", upLoadFileName, upLoadFileName, "", ""));
                            fileAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }}

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
