package com.example.acceptance.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.acceptance.R;
import com.example.acceptance.adapter.FileAddAdapter;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.greendao.bean.DeliveryListBean;
import com.example.acceptance.greendao.bean.DocumentBean;
import com.example.acceptance.greendao.bean.FileBean;
import com.example.acceptance.greendao.db.DeliveryListBeanDao;
import com.example.acceptance.greendao.db.DocumentBeanDao;
import com.example.acceptance.greendao.db.FileBeanDao;
import com.example.acceptance.utils.FileUtils;
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/24 15
 */
public class AddPopupWindow3 extends PopupWindow {


    private View view;
    private String id;
    private Activity context;


    /**
     * true:不为空
     * false:为空
     */
    private boolean isAdd;
    private String tvProjectString;

    public AddPopupWindow3(Activity context, View tvAdd, String id, boolean isAdd, String tvProjectString) {
        super(context);
        this.id = id;
        this.isAdd = isAdd;
        this.context = context;
        this.tvProjectString = tvProjectString;
        view = context.getLayoutInflater().inflate(R.layout.popup_add11, null);
        this.setContentView(view);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);
        this.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = context.getWindow().getAttributes();
            lp1.alpha = 1f;
            context.getWindow().setAttributes(lp1);

        });
        addPopup2();
    }

    private List<FileBean> fileBeans = new ArrayList<>();
    private FileAddAdapter fileAdapter;
    private List<FileBean> fileBeans2 = new ArrayList<>();
    private FileAddAdapter fileAdapter2;

    private String path = (String) SPUtils.get(context, "path", "") + "/";
    private String secretStr = "";
    private String tv_codeString = "";

    private void addPopup2() {
        EditText tv_code = view.findViewById(R.id.tv_code);
        EditText tv_name = view.findViewById(R.id.tv_name);
        TextView tv_payClassify = view.findViewById(R.id.tv_payClassify);
        TextView tv_secret = view.findViewById(R.id.tv_secret);
        EditText tv_productCode = view.findViewById(R.id.tv_productCode);
        EditText tv_stage = view.findViewById(R.id.tv_stage);

        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);
        TextView tv_file = view.findViewById(R.id.tv_file);
        MyListView lv_file = view.findViewById(R.id.lv_file);
        TextView tv_file2 = view.findViewById(R.id.tv_file2);
        MyListView lv_file2 = view.findViewById(R.id.lv_file2);
        fileBeans.clear();
        fileBeans2.clear();

        tv_secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                String[] dish = new String[]{"非密", "内部", "秘密", "机密"};
                builder2.setTitle("请选择密级：");
                int dishPos = 0;
                builder2.setSingleChoiceItems(dish, dishPos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_secret.setText(dish[which]);
                        secretStr = dish[which];
                        dialog.dismiss();
                    }
                });
                builder2.setCancelable(false);
                builder2.show();
            }
        });
        tv_payClassify.setText(tvProjectString);
        tv_payClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
                List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                        .where(DeliveryListBeanDao.Properties.IsParent.eq("false"))
                        .list();
                if (deliveryListBeans == null && deliveryListBeans.isEmpty()) {
                    ToastUtils.getInstance().showTextToast(context, "请前往交付清单添加交付类别");
                    return;
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                String[] dish = new String[deliveryListBeans.size()];
                builder2.setTitle("请选择交付类别：");
                int dishPos = 0;
                for (int i = 0; i < deliveryListBeans.size(); i++) {
                    dish[i] = deliveryListBeans.get(i).getProject();
                    if (tv_payClassify.getText().toString().trim().equals(dish[i])) {
                        dishPos = i;
                    }
                }
                builder2.setSingleChoiceItems(dish, dishPos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_payClassify.setText(dish[which]);
                        dialog.dismiss();
                    }
                });
                builder2.setCancelable(false);
                builder2.show();


            }
        });

        if (isAdd) {
            DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                    .where(DocumentBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(DocumentBeanDao.Properties.Id.eq(id))
                    .list();
            tv_payClassify.setText(documentBeans.get(0).getPayClassifyName());
            tv_codeString = documentBeans.get(0).getCode();
            tv_code.setText(tv_codeString);
            tv_name.setText(documentBeans.get(0).getName());
            tv_secret.setText(documentBeans.get(0).getSecret());
            tv_productCode.setText(documentBeans.get(0).getProductCode());
            tv_stage.setText(documentBeans.get(0).getStage());

            FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(FileBeanDao.Properties.DocumentId.eq(id))
                    .where(FileBeanDao.Properties.Type.eq("主内容"))
                    .list();
            fileBeans.addAll(fileBeanList);

            List<FileBean> fileBeanList2 = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(FileBeanDao.Properties.DocumentId.eq(id))
                    .where(FileBeanDao.Properties.Type.eq("附件"))
                    .list();
            fileBeans2.addAll(fileBeanList2);
        }

        fileAdapter = new FileAddAdapter(context, fileBeans);
        lv_file.setAdapter(fileAdapter);

        fileAdapter2 = new FileAddAdapter(context, fileBeans2);
        lv_file2.setAdapter(fileAdapter2);

        fileAdapter.setOnDel(position -> {
            fileBeans.remove(position);
            fileAdapter.notifyDataSetChanged();
        });
        fileAdapter2.setOnDel(position -> {
            fileBeans2.remove(position);
            fileAdapter2.notifyDataSetChanged();
        });


        tv_file2.setOnClickListener(view -> {
            addFile.addfile2();
        });

        tv_file.setOnClickListener(view -> {
            addFile.addfile1();
        });

        tv_popup_save.setOnClickListener(view -> {

            if (StringUtils.isBlank(tv_code.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "编号不能为空");
                return;
            }
            if (StringUtils.isBlank(tv_name.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "名称不能为空");
                return;
            }
            if (StringUtils.isBlank(tv_secret.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "密级不能为空");
                return;
            }
            secretStr = tv_secret.getText().toString().trim();
            if (StringUtils.isBlank(tv_payClassify.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "交付类别不能为空");
                return;
            }
            if (StringUtils.isBlank(tv_productCode.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "产品编号不能为空");
                return;
            }
            if (StringUtils.isBlank(tv_stage.getText().toString().trim())) {
                ToastUtils.getInstance().showTextToast(context, "阶段不能为空");
                return;
            }

            if (fileBeans.isEmpty()) {
                ToastUtils.getInstance().showTextToast(context, "请添加主要文件");
                return;
            }
            if (!tv_code.getText().toString().trim().equals(tv_codeString)) {
                DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
                List<DocumentBean> deliveryBeanList = documentBeanDao.queryBuilder()
                        .where(DocumentBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                        .where(DocumentBeanDao.Properties.Code.eq(tv_code.getText().toString().trim()))
                        .list();
                if (deliveryBeanList != null && !deliveryBeanList.isEmpty()) {
                    ToastUtils.getInstance().showTextToast(context, "请修改编号，编号不能重复");
                    return;
                }
            }


            String documentId = System.currentTimeMillis() + "";
            DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();

            List<DeliveryListBean> deliveryListBeans = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(DeliveryListBeanDao.Properties.IsParent.eq("false"))
                    .where(DeliveryListBeanDao.Properties.Project.eq(tv_payClassify.getText().toString().trim()))
                    .list();

            if (isAdd) {
                List<DocumentBean> documentBeans = documentBeanDao.queryBuilder()
                        .where(DocumentBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                        .where(DocumentBeanDao.Properties.Id.eq(id))
                        .list();
                DocumentBean documentBean = new DocumentBean(documentBeans.get(0).getUId(),
                        (String) SPUtils.get(context, "id", ""),
                        id,
                        tv_code.getText().toString().trim(),
                        tv_name.getText().toString().trim(),
                        tv_secret.getText().toString().trim(),
                        documentBeans.get(0).getPayClassify(),
                        tv_payClassify.getText().toString().trim(),
                        (String) SPUtils.get(context, "modelCode", ""),
                        (String) SPUtils.get(context, "productCode", ""),
                        tv_productCode.getText().toString().trim(),
                        tv_stage.getText().toString().trim(),
                        documentBeans.get(0).getTechStatus(),
                        documentBeans.get(0).getApprover(),
                        documentBeans.get(0).getApprovalDate(),
                        documentBeans.get(0).getIssl(),
                        documentBeans.get(0).getConclusion(),
                        documentBeans.get(0).getDescription(),
                        documentBeans.get(0).getOnLine(),
                        documentBeans.get(0).getInfoUrl(),
                        documentBeans.get(0).getUniqueValue());
                documentBeanDao.update(documentBean);

            } else {
                DocumentBean documentBean = new DocumentBean(null,
                        (String) SPUtils.get(context, "id", ""),
                        documentId,
                        tv_code.getText().toString().trim(),
                        tv_name.getText().toString().trim(),
                        tv_secret.getText().toString().trim(),
                        deliveryListBeans.get(0).getId(),
                        tv_payClassify.getText().toString().trim(),
                        (String) SPUtils.get(context, "modelCode", ""),
                        (String) SPUtils.get(context, "productCode", ""),
                        tv_productCode.getText().toString().trim(),
                        tv_stage.getText().toString().trim(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "false",
                        "",
                        UUID.randomUUID().toString());
                documentBeanDao.insert(documentBean);

            }

            FileBeanDao fileBeanDaoSave = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList1 = fileBeanDaoSave.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(FileBeanDao.Properties.DocumentId.eq(id))
                    .where(FileBeanDao.Properties.Type.eq("主内容"))
                    .list();
            for (int i = 0; i < fileBeanList1.size(); i++) {
                fileBeanDaoSave.deleteByKey(fileBeanList1.get(i).getUId());
            }

            FileBean fileBean11 = new FileBean(null,
                    (String) SPUtils.get(context, "id", ""),
                    isAdd ? id : documentId,
                    fileBeans.get(0).getName(),
                    fileBeans.get(0).getName(),
                    "主内容",
                    fileAdapter.getList().get(0).getSecret(),
                    fileAdapter.getList().get(0).getDisabledSecret());
            fileBeanDaoSave.insert(fileBean11);
            FileUtils.copyFile(fileBeans.get(0).getPath(), path + fileBeans.get(0).getName());


            List<FileBean> fileBeanList2 = fileBeanDaoSave.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq((String) SPUtils.get(context, "id", "")))
                    .where(FileBeanDao.Properties.DocumentId.eq(id))
                    .where(FileBeanDao.Properties.Type.eq("附件"))
                    .list();
            for (int i = 0; i < fileBeanList2.size(); i++) {
                fileBeanDaoSave.deleteByKey(fileBeanList2.get(i).getUId());
            }
            FileBeanDao fileBeanDaoSave2 = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            for (int i = 0; i < fileBeans2.size(); i++) {
                FileBean fileBean = new FileBean(null,
                        (String) SPUtils.get(context, "id", ""),
                        isAdd ? id : documentId,
                        fileBeans2.get(i).getName(),
                        fileBeans2.get(i).getName(),
                        "附件",
                        fileAdapter2.getList().get(i).getSecret(),
                        fileAdapter2.getList().get(i).getDisabledSecret());
                fileBeanDaoSave2.insert(fileBean);
                FileUtils.copyFile(fileBeans2.get(i).getPath(), path + fileBeans2.get(i).getName());
            }
            this.dismiss();
            addFile.addResult();
            ToastUtils.getInstance().showTextToast(context, "保存成功");
        });

    }


    public void setResult(Intent data, int requestCode) {
        if (requestCode == 11) {
            Uri uri = data.getData();
            if (uri != null) {
                String path = FileUtils.getPath(context, uri);
                if (path != null) {
                    File file = new File(path);
                    if (file.exists()) {
                        String upLoadFilePath = file.toString();
                        String upLoadFileName = file.getName();
                        Log.e("TAG", "upLoadFilePath: " + upLoadFilePath);
                        Log.e("TAG", "upLoadFileName: " + upLoadFileName);
                        fileBeans.clear();
                        fileBeans.add(new FileBean(null, "", "", upLoadFileName, upLoadFilePath, "主内容", "非密", ""));
                        fileAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (requestCode == 22) {
            Uri uri = data.getData();
            if (uri != null) {
                String path = FileUtils.getPath(context, uri);
                if (path != null) {
                    File file = new File(path);
                    if (file.exists()) {
                        String upLoadFilePath = file.toString();
                        String upLoadFileName = file.getName();
                        Log.e("TAG", "upLoadFilePath: " + upLoadFilePath);
                        Log.e("TAG", "upLoadFileName: " + upLoadFileName);
                        fileBeans2.add(new FileBean(null, "", "", upLoadFileName, upLoadFilePath, "附件", "非密", ""));
                        fileAdapter2.notifyDataSetChanged();
                    }
                }
            }
        }


    }

    public interface AddFile {
        void addfile1();

        void addfile2();

        void addResult();
    }

    private AddFile addFile;

    public void setAddFile(AddFile addFile) {
        this.addFile = addFile;
    }
}
