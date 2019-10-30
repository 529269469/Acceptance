package com.example.acceptance.fragment.main.technology;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.kitting.LvFileAdapter;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 技术类检查——依据文件检查
 */
public class TechnologyFileFragment extends BaseFragment {
    @BindView(R.id.lv_file_kitting)
    MyListView lvFileKitting;
    @BindView(R.id.lv_file_kitting2)
    MyListView lvFileKitting2;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;

    private List<DocumentBean> list = new ArrayList<>();
    private List<DocumentBean> list2 = new ArrayList<>();
    private List<FileBean> fileBeans = new ArrayList<>();
    private File2Adapter fileAdapter;
    private String id;
    private LvFileAdapter lvFileAdapter;
    private LvFileAdapter lvFileAdapter2;
    private String parentId;

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        tvAdd.setText("管理文件");
        tvAdd2.setText("添加技术文件");

        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片&视频"))
                .whereOr(DocumentBeanDao.Properties.PayClassifyName.eq("合同"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("明细表"),
                        DocumentBeanDao.Properties.PayClassifyName.eq("任务书"))
                .list();

        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        List<DeliveryListBean> parentIdList = deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .where(DeliveryListBeanDao.Properties.Project.eq("验收依据文件"))
                .list();
        if (parentIdList!=null&&!parentIdList.isEmpty()){
            parentId = parentIdList.get(0).getId();
        }else {
            parentId=System.currentTimeMillis() + "";
            DeliveryListBean deliveryListBean=new DeliveryListBean(null,
                    id,
                    parentId,
                    true+"",
                    "验收依据文件","");
            deliveryListBeanDao.insert(deliveryListBean);
        }


        list.addAll(documentBeans);
        lvFileAdapter = new LvFileAdapter(getActivity(), list);
        lvFileKitting.setAdapter(lvFileAdapter);


        List<DocumentBean> documentBeanList = documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片&视频"))
                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                        DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                        DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                .list();
        list2.addAll(documentBeanList);
        lvFileAdapter2 = new LvFileAdapter(getActivity(), list2);
        lvFileKitting2.setAdapter(lvFileAdapter2);

        tvAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPopup2(false, 0);
            }
        });

        lvFileKitting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addPopup(true, i);
            }
        });

        lvFileKitting2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addPopup2(true, i);
            }
        });

        lvFileKitting2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .where(DocumentBeanDao.Properties.PayClassify.eq(list2.get(i).getId()))
                                .list();

                        if (documentBeans != null && !documentBeans.isEmpty()) {
                            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
                            List<FileBean> fileBeans = fileBeanDao.queryBuilder()
                                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(0).getId()))
                                    .list();

                            for (int j = 0; j < fileBeans.size(); j++) {
                                fileBeanDao.deleteByKey(fileBeans.get(j).getUId());
                            }
                            documentBeanDao.deleteByKey(documentBeans.get(0).getUId());
                        }
                        deliveryListBeanDao.deleteByKey(list2.get(i).getUId());


                        DocumentBeanDao documentBeanDao2 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                        List<DocumentBean> documentBeanList = documentBeanDao2.queryBuilder()
                                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片&视频"))
                                .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                                        DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                                        DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                                .list();
                        list2.clear();
                        list2.addAll(documentBeanList);
                        lvFileAdapter2.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kitting_file;
    }

    private void addPopup2(boolean isAdd, int position) {
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
        popupWindow.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
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
        if (isAdd ) {
            tv_payClassify.setText(list2.get(position).getPayClassifyName());
            tv_code.setText(list2.get(position).getCode());
            tv_name.setText(list2.get(position).getName());
            tv_secret.setText(list2.get(position).getSecret());
            tv_techStatus.setText(list2.get(position).getTechStatus());
            tv_approver.setText(list2.get(position).getApprover());
            tv_approvalDate.setText(list2.get(position).getApprovalDate());
            if (list2.get(position).getIssl().equals("true")) {
                tv_issl.setChecked(true);
            } else {
                tv_issl.setChecked(false);
            }
            tv_conclusion.setText(list2.get(position).getConclusion());
            tv_description.setText(list2.get(position).getDescription());

            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(list2.get(position).getId()))
                    .list();

            fileBeans.addAll(fileBeanList);
        }
        fileAdapter = new File2Adapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);
        tv_popup_save.setVisibility(View.VISIBLE);

        fileAdapter.setOnDel(pos -> {
            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(list2.get(position).getId()))
                    .list();
            if (fileBeanList!=null&&fileBeanList.isEmpty()){
                for (int i = 0; i < fileBeanList.size(); i++) {
                    if (fileBeanList.get(i).getUId().equals(fileBeans.get(pos).getUId())) {
                        fileBeanDao.deleteByKey(fileBeanList.get(i).getUId());
                        fileBeans.remove(pos);
                        break;
                    }
                }
            }else {
                fileBeans.remove(pos);
            }
            fileAdapter.notifyDataSetChanged();
        });

        lv_file.setOnItemClickListener((adapterView, view1, i, l) -> {
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

        });
        tv_file.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });

        tv_popup_save.setOnClickListener(view13 -> {
            if (fileBeans.isEmpty()){
                ToastUtils.getInstance().showTextToast(getActivity(),"请添加文件");
                return;
            }
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
            List<DeliveryListBean> deliveryListBeanList=deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.Id.eq(list2.get(position).getPayClassify()))
                    .list();

            String deliveryListParentId = System.currentTimeMillis() + "";
            if (isAdd) {
                DeliveryListBean deliveryListBean = new DeliveryListBean(deliveryListBeanList.get(0).getUId(),
                        id,
                        deliveryListBeanList.get(0).getId(),
                        false + "",
                        tv_payClassify.getText().toString(),
                        parentId);
                deliveryListBeanDao.update(deliveryListBean);
            } else {
                DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                        id,
                        deliveryListParentId,
                        false + "",
                        tv_payClassify.getText().toString(),
                        parentId);
                deliveryListBeanDao.insert(deliveryListBean);
            }


            DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();

            String documentId = System.currentTimeMillis() + "";
            if (isAdd) {

                DocumentBean documentBean = new DocumentBean(list2.get(position).getUId(),
                        id,
                        list2.get(position).getId(),
                        tv_code.getText().toString().trim(),
                        tv_name.getText().toString().trim(),
                        tv_secret.getText().toString().trim(),
                        list2.get(position).getPayClassify(),
                        tv_payClassify.getText().toString().trim(),
                        list2.get(position).getModalCode(),
                        list2.get(position).getProductCodeName(),
                        list2.get(position).getProductCode(),
                        list2.get(position).getStage(),
                        tv_techStatus.getText().toString().trim(),
                        tv_approver.getText().toString().trim(),
                        tv_approvalDate.getText().toString().trim(),
                        tv_issl.isChecked() + "",
                        tv_conclusion.getText().toString().trim(),
                        tv_description.getText().toString().trim());
                documentBeanDao.update(documentBean);
            } else {
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


            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            for (int i = 0; i < fileBeans.size(); i++) {
                boolean isFile = false;
                if (isAdd){
                    List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(list2.get(position).getId()))
                            .list();
                    for (int j = 0; j < fileBeanList.size(); j++) {
                        if (fileBeans.get(i).getName().equals(fileBeanList.get(j).getName())) {
                            isFile = true;
                        }
                    }
                    if (!isFile) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                list2.get(position).getId(),
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getType(),
                                tv_secret.getText().toString().trim());
                        fileBeanDao.insert(fileBean);
                        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                        List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                                .where(DataPackageDBeanDao.Properties.Id.eq(id))
                                .list();
                        FileUtils.copyFile(fileBeans.get(i).getPath(), dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getName());
                    }
                }else {
                    FileBean fileBean = new FileBean(null,
                            id,
                            documentId,
                            fileBeans.get(i).getName(),
                            fileBeans.get(i).getName(),
                            fileBeans.get(i).getType(),
                            tv_secret.getText().toString().trim());
                    fileBeanDao.insert(fileBean);
                    DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                    List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                            .where(DataPackageDBeanDao.Properties.Id.eq(id))
                            .list();
                    FileUtils.copyFile(fileBeans.get(i).getPath(), dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getName());
                }



            }

            DocumentBeanDao documentBeanDao3 = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            List<DocumentBean> documentBeanList = documentBeanDao3.queryBuilder()
                    .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                    .where(DocumentBeanDao.Properties.PayClassifyName.notEq("照片&视频"))
                    .where(DocumentBeanDao.Properties.PayClassifyName.notEq("合同"),
                            DocumentBeanDao.Properties.PayClassifyName.notEq("明细表"),
                            DocumentBeanDao.Properties.PayClassifyName.notEq("任务书"))
                    .list();
            list2.clear();
            list2.addAll(documentBeanList);
            lvFileAdapter2.notifyDataSetChanged();
            popupWindow.dismiss();
            ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
        });

    }


    private void addPopup(boolean isAdd, int position) {
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
        popupWindow.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
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

        tv_popup_save.setVisibility(View.GONE);

        fileBeans.clear();
        if (isAdd ) {
            tv_payClassify.setText(list.get(position).getPayClassifyName());
            tv_code.setText(list.get(position).getCode());
            tv_name.setText(list.get(position).getName());
            tv_secret.setText(list.get(position).getSecret());
            tv_techStatus.setText(list.get(position).getTechStatus());
            tv_approver.setText(list.get(position).getApprover());
            tv_approvalDate.setText(list.get(position).getApprovalDate());
            if (!StringUtils.isBlank(list.get(position).getIssl())&&list.get(position).getIssl().equals("true")) {
                tv_issl.setChecked(true);
            } else {
                tv_issl.setChecked(false);
            }
            tv_conclusion.setText(list.get(position).getConclusion());
            tv_description.setText(list.get(position).getDescription());

            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(list.get(position).getId()))
                    .list();
            fileBeans.addAll(fileBeanList);
        }
        fileAdapter = new File2Adapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);

        tv_popup_save.setVisibility(View.GONE);

        lv_file.setOnItemClickListener((adapterView, view1, i, l) -> {
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

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
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
                            fileBeans.add(new FileBean(null, "", "", upLoadFileName, upLoadFilePath, "",""));
                            fileAdapter.notifyDataSetChanged();
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
