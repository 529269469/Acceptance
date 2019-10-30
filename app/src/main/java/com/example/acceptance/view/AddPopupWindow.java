package com.example.acceptance.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.File2Adapter;
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
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/24 15
 */
public class AddPopupWindow extends PopupWindow {


    private View view;
    private PopupWindow popupWindow;
    private String id;
    private Activity context;
    private List<DocumentBean> list = new ArrayList<>();

    private boolean isAdd;

    public AddPopupWindow(Activity context, View tvAdd, String id, boolean isAdd) {
        super(context);
        this.id = id;
        this.isAdd = isAdd;
        this.context = context;
        view = context.getLayoutInflater().inflate(R.layout.popup_add11, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setHeight(600);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(tvAdd, Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = context.getWindow().getAttributes();
            lp1.alpha = 1f;
            context.getWindow().setAttributes(lp1);

        });


    }

    private List<FileBean> fileBeans = new ArrayList<>();
    private File2Adapter fileAdapter;

    private List<FileBean> fileBeans2 = new ArrayList<>();
    private File2Adapter fileAdapter2;
    private String parentId;
    private String path = (String) SPUtils.get(context, "path", "") + "/";

    private void addPopup2(int position) {
        EditText tv_code = view.findViewById(R.id.tv_code);
        EditText tv_name = view.findViewById(R.id.tv_name);
        EditText tv_payClassify = view.findViewById(R.id.tv_payClassify);
        EditText tv_secret = view.findViewById(R.id.tv_secret);
        EditText tv_modelCode = view.findViewById(R.id.tv_modelCode);
        EditText tv_productCode = view.findViewById(R.id.tv_productCode);
        EditText tv_productCodeName = view.findViewById(R.id.tv_productCodeName);
        EditText tv_stage = view.findViewById(R.id.tv_stage);

        TextView tv_popup_save = view.findViewById(R.id.tv_popup_save);
        TextView tv_file = view.findViewById(R.id.tv_file);
        MyListView lv_file = view.findViewById(R.id.lv_file);
        TextView tv_file2 = view.findViewById(R.id.tv_file2);
        MyListView lv_file2 = view.findViewById(R.id.lv_file2);

        fileBeans.clear();
        fileBeans2.clear();
        tv_payClassify.setText(list.get(position).getPayClassifyName());
        tv_code.setText(list.get(position).getCode());
        tv_name.setText(list.get(position).getName());
        tv_secret.setText(list.get(position).getSecret());
        tv_modelCode.setText(list.get(position).getModalCode());
        tv_productCode.setText(list.get(position).getProductCode());
        tv_productCodeName.setText(list.get(position).getProductCodeName());
        tv_stage.setText(list.get(position).getStage());

        FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
        List<FileBean> fileBeanList = fileBeanDao.queryBuilder()
                .where(FileBeanDao.Properties.DataPackageId.eq(id))
                .where(FileBeanDao.Properties.DocumentId.eq(list.get(position).getId()))
                .where(FileBeanDao.Properties.Type.eq("主内容"))
                .list();
        fileBeans.addAll(fileBeanList);
        fileAdapter = new File2Adapter(context, fileBeans);
        lv_file.setAdapter(fileAdapter);


        List<FileBean> fileBeanList2 = fileBeanDao.queryBuilder()
                .where(FileBeanDao.Properties.DataPackageId.eq(id))
                .where(FileBeanDao.Properties.DocumentId.eq(list.get(position).getId()))
                .where(FileBeanDao.Properties.Type.eq("附件"))
                .list();
        fileBeans2.addAll(fileBeanList2);
        fileAdapter2 = new File2Adapter(context, fileBeans2);
        lv_file2.setAdapter(fileAdapter2);


        lv_file2.setOnItemClickListener((adapterView, view, i, l) -> {
            File file = new File(path + fileBeans2.get(i).getPath());
            if (file.isFile() && file.exists()) {
                try {
                    context.startActivity(OpenFileUtil.openFile(path + fileBeans2.get(i).getPath()));
                } catch (Exception o) {
                }
            }
        });

        lv_file.setOnItemClickListener((adapterView, view, i, l) -> {
            File file = new File(path + fileBeans.get(i).getPath());
            if (file.isFile() && file.exists()) {
                try {
                    context.startActivity(OpenFileUtil.openFile(path + fileBeans.get(i).getPath()));
                } catch (Exception o) {
                }
            }
        });

        tv_file2.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            context.startActivityForResult(intent, 22);
        });

        tv_file.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            context.startActivityForResult(intent, 1);
        });

        tv_popup_save.setOnClickListener(view -> {
            if (fileBeans.isEmpty()) {
                ToastUtils.getInstance().showTextToast(context, "请添加文件");
                return;
            }
            String deliveryListParentId = System.currentTimeMillis() + "";
            String documentId = System.currentTimeMillis() + "";
            String relatedDocumentId = System.currentTimeMillis() + "";
            DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
            DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();

            if (isAdd) {
                List<DeliveryListBean> deliveryListBeanList = deliveryListBeanDao.queryBuilder()
                        .where(DeliveryListBeanDao.Properties.Id.eq(list.get(position).getPayClassify()))
                        .list();
                DeliveryListBean deliveryListBean = new DeliveryListBean(deliveryListBeanList.get(0).getUId(),
                        id,
                        deliveryListBeanList.get(0).getId(),
                        false + "",
                        tv_payClassify.getText().toString().trim(),
                        deliveryListBeanList.get(0).getParentId());
                deliveryListBeanDao.update(deliveryListBean);

                DocumentBean documentBean = new DocumentBean(list.get(position).getUId(),
                        id,
                        list.get(position).getId(),
                        tv_code.getText().toString().trim(),
                        tv_name.getText().toString().trim(),
                        tv_secret.getText().toString().trim(),
                        list.get(position).getPayClassify(),
                        tv_payClassify.getText().toString().trim(),
                        tv_modelCode.getText().toString().trim(),
                        tv_productCodeName.getText().toString().trim(),
                        tv_productCode.getText().toString().trim(),
                        tv_stage.getText().toString().trim(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");
                documentBeanDao.update(documentBean);


            } else {
                DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                        id,
                        deliveryListParentId,
                        false + "",
                        tv_payClassify.getText().toString(),
                        parentId);
                deliveryListBeanDao.insert(deliveryListBean);

                DocumentBean documentBean = new DocumentBean(null,
                        id,
                        documentId,
                        tv_code.getText().toString().trim(),
                        tv_name.getText().toString().trim(),
                        tv_secret.getText().toString().trim(),
                        deliveryListParentId,
                        tv_payClassify.getText().toString().trim(),
                        tv_modelCode.getText().toString().trim(),
                        tv_productCodeName.getText().toString().trim(),
                        tv_productCode.getText().toString().trim(),
                        tv_stage.getText().toString().trim(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "");
                documentBeanDao.insert(documentBean);


            }

            FileBeanDao fileBeanDaoSave = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
            List<FileBean> fileBeanList1 = fileBeanDaoSave.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(list.get(position).getId()))
                    .list();

            for (int i = 0; i < fileBeans.size(); i++) {
                boolean isFile = false;
                for (int j = 0; j < fileBeanList1.size(); j++) {
                    if (fileBeans.get(i).getUId().equals(fileBeanList1.get(j).getUId())) {
                        isFile = true;
                    }
                }
                if (!isFile) {
                    FileBean fileBean = new FileBean(null,
                            id,
                            list.get(position).getId(),
                            fileBeans.get(i).getName(),
                            fileBeans.get(i).getName(),
                            fileBeans.get(i).getType(),
                            fileBeans.get(i).getSecret());
                    fileBeanDao.insert(fileBean);
                    DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                    List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                            .where(DataPackageDBeanDao.Properties.Id.eq(id))
                            .list();
                    FileUtils.copyFile(fileBeans.get(i).getPath(), dataPackageDBeans.get(0).getUpLoadFile() + "/" + fileBeans.get(i).getName());
                }

            }

            popupWindow.dismiss();
            ToastUtils.getInstance().showTextToast(context, "保存成功");
        });

    }


}
