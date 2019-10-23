package com.example.acceptance.fragment.main;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
import com.example.acceptance.adapter.UnresolvedAdapter;
import com.example.acceptance.adapter.UnresolvedAdapter2;
import com.example.acceptance.base.BaseFragment;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.ApplyItemBean;
import com.example.acceptance.greendao.bean.CheckFileBean;
import com.example.acceptance.greendao.bean.CheckVerdBean;
import com.example.acceptance.greendao.bean.DataPackageDBean;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.bean.UnresolvedBean;
import com.example.acceptance.greendao.db.ApplyItemBeanDao;
import com.example.acceptance.greendao.db.CheckFileBeanDao;
import com.example.acceptance.greendao.db.CheckVerdBeanDao;
import com.example.acceptance.greendao.db.DataPackageDBeanDao;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.greendao.db.UnresolvedBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.OpenFileUtil;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
import com.example.acceptance.view.LinePathView;
import com.example.acceptance.view.MyListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 验收结论
 */

public class AcceptanceConclusionFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lv_list1)
    MyListView lvList1;
    @BindView(R.id.lv_list2)
    MyListView lvList2;
    @BindView(R.id.iv_signature)
    ImageView ivSignature;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_qConclusion)
    EditText etQConclusion;
    @BindView(R.id.et_gConclusion)
    EditText etGConclusion;
    @BindView(R.id.et_jConclusion)
    EditText etJConclusion;
    @BindView(R.id.tv_time)
    EditText tvTime;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_add2)
    TextView tvAdd2;
    private PopupWindow popupWindow;
    private LinePathView mPathView;
    private String id;
    private List<UnresolvedBean> beanList = new ArrayList<>();
    private UnresolvedAdapter unresolvedAdapter;
    private File2Adapter fileAdapter;
    private List<FileBean> fileBeans = new ArrayList<>();

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String words= editable.toString();
            if (!StringUtils.isBlank(words)) {
                CheckVerdBeanDao checkVerdBeanDao = MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
                List<CheckVerdBean> checkVerdBeans = checkVerdBeanDao.queryBuilder()
                        .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                if (checkVerdBeans != null && !checkVerdBeans.isEmpty()) {
                    CheckVerdBean checkVerdBean = new CheckVerdBean(checkVerdBeans.get(0).getUId(),
                            id,
                            checkVerdBeans.get(0).getId(),
                            checkVerdBeans.get(0).getName(),
                            checkVerdBeans.get(0).getCode(),
                            etQConclusion.getText().toString().trim(),
                            etGConclusion.getText().toString().trim(),
                            etJConclusion.getText().toString().trim(),
                            checkVerdBeans.get(0).getConclusion(),
                            checkVerdBeans.get(0).getCheckPerson(),
                            checkVerdBeans.get(0).getDocTypeVal());
                    checkVerdBeanDao.update(checkVerdBean);
                }
            }

        }
    };

    @Override
    protected void initEventAndData() {
        id = getArguments().getString("id");
        ivSignature.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvAdd2.setOnClickListener(this);
        CheckVerdBeanDao checkVerdBeanDao = MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        List<CheckVerdBean> checkVerdBeans = checkVerdBeanDao.queryBuilder()
                .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                .list();

        tvCode.setText("编号：" + checkVerdBeans.get(0).getCode());
        tvName.setText("名称：" + checkVerdBeans.get(0).getName());
        etQConclusion.setText(checkVerdBeans.get(0).getQConclusion());
        etGConclusion.setText(checkVerdBeans.get(0).getGConclusion());
        etJConclusion.setText(checkVerdBeans.get(0).getJConclusion());

        etQConclusion.addTextChangedListener(textWatcher);
        etGConclusion.addTextChangedListener(textWatcher);
        etJConclusion.addTextChangedListener(textWatcher);

        if (!StringUtils.isBlank(checkVerdBeans.get(0).getCheckPerson())) {
            Glide.with(getActivity())
                    .load(new File(checkVerdBeans.get(0).getCheckPerson()))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivSignature);
        }
        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        List<ApplyItemBean> applyItemBeans = applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        UnresolvedAdapter2 unresolvedAdapter2 = new UnresolvedAdapter2(getActivity(), applyItemBeans);
        lvList1.setAdapter(unresolvedAdapter2);


        UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        List<UnresolvedBean> unresolvedBeans = unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();
        beanList.addAll(unresolvedBeans);
        unresolvedAdapter = new UnresolvedAdapter(getActivity(), beanList);
        lvList2.setAdapter(unresolvedAdapter);

        lvList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addUnresolvedSet(false, i);
            }
        });

        lvList2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("是否删除本条数据");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
                        unresolvedBeanDao.deleteByKey(beanList.get(i).getUId());
                        beanList.remove(i);
                        unresolvedAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_acceptance_conclusion;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_signature:
                pathPopu();
                break;
            case R.id.tv_save:
                CheckVerdBeanDao checkVerdBeanDao = MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
                List<CheckVerdBean> checkVerdBeans = checkVerdBeanDao.queryBuilder()
                        .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                if (checkVerdBeans != null && !checkVerdBeans.isEmpty()) {
                    CheckVerdBean checkVerdBean = new CheckVerdBean(checkVerdBeans.get(0).getUId(),
                            id,
                            checkVerdBeans.get(0).getId(),
                            checkVerdBeans.get(0).getName(),
                            checkVerdBeans.get(0).getCode(),
                            etQConclusion.getText().toString().trim(),
                            etGConclusion.getText().toString().trim(),
                            etJConclusion.getText().toString().trim(),
                            checkVerdBeans.get(0).getConclusion(),
                            checkVerdBeans.get(0).getCheckPerson(),
                            checkVerdBeans.get(0).getDocTypeVal());
                    checkVerdBeanDao.update(checkVerdBean);
                } else {
                    CheckVerdBean checkVerdBean = new CheckVerdBean(null,
                            id,
                            System.currentTimeMillis() + "",
                            "",
                            "",
                            etQConclusion.getText().toString().trim(),
                            etGConclusion.getText().toString().trim(),
                            etJConclusion.getText().toString().trim(),
                            "",
                            "","");
                    checkVerdBeanDao.insert(checkVerdBean);
                }
                ToastUtils.getInstance().showTextToast(getActivity(), "保存成功");
                break;

            case R.id.tv_add2:
                addUnresolvedSet(true, 0);
                break;
        }
    }

    private void addUnresolvedSet(boolean isAdd, int pos) {
        View view = getLayoutInflater().inflate(R.layout.popup_add5, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setHeight(600);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(tvAdd2, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
        EditText tv_productCode = view.findViewById(R.id.tv_productCode);
        EditText tv_question = view.findViewById(R.id.tv_question);
        EditText tv_confirmer = view.findViewById(R.id.tv_confirmer);
        EditText tv_confirmTime = view.findViewById(R.id.tv_confirmTime);
        TextView tv_file = view.findViewById(R.id.tv_file);
        MyListView lv_file = view.findViewById(R.id.lv_file);
        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);
        fileBeans.clear();
        if (!isAdd) {
            tv_productCode.setText(beanList.get(pos).getProductCode());
            tv_question.setText(beanList.get(pos).getQuestion());
            tv_confirmer.setText(beanList.get(pos).getConfirmer());
            tv_confirmTime.setText(beanList.get(pos).getConfirmTime());

            try {
                FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(beanList.get(pos).getFileId()))
                        .list();
                fileBeans.addAll(fileBeanList);
            }catch (Exception o){

            }

        }

        fileAdapter = new File2Adapter(getActivity(), fileBeans);
        lv_file.setAdapter(fileAdapter);

        fileAdapter.setOnDel(new File2Adapter.OnDel() {
            @Override
            public void onDel(int position) {
                FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(beanList.get(pos).getFileId()))
                        .list();
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                        .where(DataPackageDBeanDao.Properties.Id.eq(id))
                        .list();
                for (int i = 0; i < fileBeanList.size(); i++) {
                    if (fileBeanList.get(i).getName().equals(fileBeans.get(position).getName())) {
                        FileUtils.delFile(dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeanList.get(i).getPath());
                        fileBeanDao.deleteByKey(fileBeanList.get(i).getUId());
                        fileBeans.remove(position);
                        break;
                    } else {
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
                UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
                String unresolvedId = System.currentTimeMillis() + "";
                if (isAdd) {
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                unresolvedId,
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getPath(), "");
                        fileBeanDao.insert(fileBean);
                    }
                    UnresolvedBean unresolvedBean = new UnresolvedBean(null,
                            id,
                            unresolvedId,
                            tv_productCode.getText().toString().trim(),
                            tv_question.getText().toString().trim(),
                            tv_confirmer.getText().toString().trim(),
                            tv_confirmTime.getText().toString().trim(),
                            unresolvedId);
                    unresolvedBeanDao.insert(unresolvedBean);
                } else {
                    FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();
                    for (int i = 0; i < fileBeans.size(); i++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                StringUtils.isBlank(beanList.get(pos).getFileId())?unresolvedId:beanList.get(pos).getFileId(),
                                fileBeans.get(i).getName(),
                                fileBeans.get(i).getPath(), "");
                        fileBeanDao.insert(fileBean);
                    }
                    UnresolvedBean unresolvedBean = new UnresolvedBean(beanList.get(pos).getUId(),
                            id,
                            beanList.get(pos).getId(),
                            tv_productCode.getText().toString().trim(),
                            tv_question.getText().toString().trim(),
                            tv_confirmer.getText().toString().trim(),
                            tv_confirmTime.getText().toString().trim(),
                            StringUtils.isBlank(beanList.get(pos).getFileId())?unresolvedId:beanList.get(pos).getFileId());
                    unresolvedBeanDao.update(unresolvedBean);
                }
                UnresolvedBeanDao unresolvedBeanDao2 = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
                List<UnresolvedBean> unresolvedBeans = unresolvedBeanDao2.queryBuilder()
                        .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                        .list();
                beanList.clear();
                beanList.addAll(unresolvedBeans);
                unresolvedAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
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
                            fileBeans.add(new FileBean(null, "", "", upLoadFileName, upLoadFilePath, ""));
                            fileAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }

    private void pathPopu() {
        View poview = getLayoutInflater().inflate(R.layout.path_view, null);
        popupWindow = new PopupWindow(poview);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(ivSignature, Gravity.TOP, 0, 80);

        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });

        mPathView = poview.findViewById(R.id.path_view);
        TextView mBtnClear = poview.findViewById(R.id.m_btn_clear);
        TextView mBtnSave = poview.findViewById(R.id.m_btn_save);

        //修改背景、笔宽、颜色
        mPathView.setBackColor(Color.WHITE);
        mPathView.setPaintWidth(10);
        mPathView.setPenColor(Color.BLACK);
        //清除
        mBtnClear.setOnClickListener(v -> {
            mPathView.clear();
            mPathView.setBackColor(Color.WHITE);
            mPathView.setPaintWidth(10);
            mPathView.setPenColor(Color.BLACK);
        });
        //保存
        String path = System.currentTimeMillis() + ".png";
        mBtnSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                try {
                    mPathView.save(SPUtils.get(getActivity(), "path", "") + File.separator + path, true, 10);
                    Glide.with(getActivity())
                            .load(new File(SPUtils.get(getActivity(), "path", "") + File.separator + path))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivSignature);
                    Toast.makeText(getActivity(), "签名成功~", Toast.LENGTH_SHORT).show();
                    CheckVerdBeanDao checkVerdBeanDao = MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
                    List<CheckVerdBean> checkVerdBeans = checkVerdBeanDao.queryBuilder()
                            .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                            .list();
                    if (checkVerdBeans != null && !checkVerdBeans.isEmpty()) {
                        CheckVerdBean checkVerdBean = new CheckVerdBean(checkVerdBeans.get(0).getUId(),
                                id,
                                checkVerdBeans.get(0).getId(),
                                checkVerdBeans.get(0).getName(),
                                checkVerdBeans.get(0).getCode(),
                                etQConclusion.getText().toString().trim(),
                                etGConclusion.getText().toString().trim(),
                                etJConclusion.getText().toString().trim(),
                                checkVerdBeans.get(0).getConclusion(),
                                SPUtils.get(getActivity(), "path", "") + File.separator + path,
                                checkVerdBeans.get(0).getDocTypeVal());
                        checkVerdBeanDao.update(checkVerdBean);
                    } else {
                        CheckVerdBean checkVerdBean = new CheckVerdBean(null,
                                id,
                                System.currentTimeMillis() + "",
                                "",
                                "",
                                etQConclusion.getText().toString().trim(),
                                etGConclusion.getText().toString().trim(),
                                etJConclusion.getText().toString().trim(),
                                "",
                                SPUtils.get(getActivity(), "path", "") + File.separator + path,"");
                        checkVerdBeanDao.insert(checkVerdBean);
                    }


                    popupWindow.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "您没有签名~", Toast.LENGTH_SHORT).show();
            }
        });

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
