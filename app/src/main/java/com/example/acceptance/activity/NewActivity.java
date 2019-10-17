package com.example.acceptance.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.acceptance.R;
import com.example.acceptance.adapter.MoAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.DataPackageBean;
import com.example.acceptance.bean.PackageBean;
import com.example.acceptance.greendao.bean.AcceptDeviceBean;
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
import com.example.acceptance.greendao.db.AcceptDeviceBeanDao;
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
import com.example.acceptance.utils.DataUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ZipUtils2;
import com.example.acceptance.view.MyListView;
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
import butterknife.ButterKnife;

public class NewActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_genduo)
    ImageView ivGenduo;
    @BindView(R.id.tv_tuichu)
    TextView tvTuichu;
    @BindView(R.id.bt_yes)
    TextView btYes;
    @BindView(R.id.bt_no)
    TextView btNo;
    @BindView(R.id.tv_moban)
    TextView tvMoban;
    @BindView(R.id.tv_code)
    EditText tvCode;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_type)
    EditText tvType;
    @BindView(R.id.tv_responseUnit)
    EditText tvResponseUnit;
    @BindView(R.id.tv_modelCode)
    EditText tvModelCode;
    @BindView(R.id.tv_productType)
    EditText tvProductType;
    @BindView(R.id.tv_batch)
    EditText tvBatch;
    @BindView(R.id.tv_creator)
    EditText tvCreator;
    @BindView(R.id.tv_productName)
    EditText tvProductName;
    @BindView(R.id.tv_productCode)
    EditText tvProductCode;
    private String moban = "";
    private String name = "";
    private String id;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, NewActivity.class);
        return intent;
    }

    @Override
    protected void initView() {
        ivGenduo.setOnClickListener(this);
        btNo.setOnClickListener(this);
        btYes.setOnClickListener(this);
        tvMoban.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new;
    }

    @Override
    protected void initDataAndEvent() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_genduo:
            case R.id.bt_no:
                finish();
                break;
            case R.id.bt_yes:
                DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                File file = new File(Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim());
                if (!file.exists()) {
                    file.mkdirs();
                }
                id = System.currentTimeMillis() + "";

                DataPackageDBean dataPackageDBean = new DataPackageDBean(null,
                        tvCode.getText().toString().trim(),
                        Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim(),
                        id,
                        tvName.getText().toString().trim(),
                        tvCode.getText().toString().trim(),
                        tvType.getText().toString().trim(),
                        tvResponseUnit.getText().toString().trim(),
                        tvModelCode.getText().toString().trim(),
                        tvProductName.getText().toString().trim(),
                        tvProductCode.getText().toString().trim(),
                        tvProductType.getText().toString().trim(),
                        tvBatch.getText().toString().trim(),
                        tvCreator.getText().toString().trim(),
                        DataUtils.getData());
                dataPackageDBeanDao.insert(dataPackageDBean);

                if (!StringUtils.isBlank(moban)) {
                    try {
                        ZipUtils2.UnZipFolder(moban, Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim());
                        filePath(Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim(), tvCode.getText().toString().trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                startActivity(MainActivity.openIntent(NewActivity.this, id, false, ""));
                finish();
                break;
            case R.id.tv_moban:
                mobanPo();
                break;
        }
    }


    private void filePath(String upLoadFile, String upLoadFileName) {
        File files = new File(upLoadFile);
        File[] subFile = files.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            String filename = subFile[i].getName();
            File file = new File(filename);
            /* 取得扩展名 */
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
            if (end.equals("xml")) {
                input(subFile[i], upLoadFileName, upLoadFile);
                break;
            }

        }
    }


    private void input(File subFile, String upLoadFileName, String upLoadFile) {
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


        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();

        try {

            CheckApplyBean checkApplyBean = new CheckApplyBean(null,
                    id,
                    dataPackageBean.getCheckApply().getId(),
                    dataPackageBean.getCheckApply().getName(),
                    dataPackageBean.getCheckApply().getCode(),
                    dataPackageBean.getCheckApply().getContractCode(),
                    dataPackageBean.getCheckApply().getContractName(),
                    dataPackageBean.getCheckApply().getApplicant(),
                    dataPackageBean.getCheckApply().getApplyCompany(),
                    dataPackageBean.getCheckApply().getPhone(),
                    dataPackageBean.getCheckApply().getConclusion(),
                    dataPackageBean.getCheckApply().getDescription(), "");
            checkApplyBeanDao.insert(checkApplyBean);
        } catch (Exception o) {

        }

        CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();

        CheckTaskBean checkTaskBean = new CheckTaskBean(null,
                id,
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
                dataPackageBean.getCheckTask().getPhone());
        checkTaskBeanDao.insert(checkTaskBean);

        ApplyItemBeanDao applyItemBeanDao = MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();

        for (int i = 0; i < dataPackageBean.getApplyItemSet().getApplyItem().size(); i++) {
            ApplyItemBean applyItemBean = new ApplyItemBean(null,
                    id,
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getId(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductCodeName(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductCode(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductStatus(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getCheckCount(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsPureCheck(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsArmyCheck(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsCompleteChoice(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsCompleteRoutine(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getIsSatisfyRequire(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getDescription(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getProductName(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getPassCheck());
            applyItemBeanDao.insert(applyItemBean);
        }

        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        PropertyBeanXDao propertyBeanXDao = MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();

        AcceptDeviceBeanDao acceptDeviceBeanDao = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
        RelatedDocumentIdSetBeanDao relatedDocumentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        for (int i = 0; i < dataPackageBean.getCheckFileSet().getCheckFile().size(); i++) {
            CheckFileBean checkFileBean = new CheckFileBean(null,
                    id,
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getName(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCode(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getDocType(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getConclusion(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckPerson());
            checkFileBeanDao.insert(checkFileBean);

            try {

                for (int j = 0; j < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().size(); j++) {
                    CheckGroupBean checkGroupBean = new CheckGroupBean(null,
                            id,
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getGroupName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckGroupConclusion(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckPerson(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsConclusion(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsTable());
                    checkGroupBeanDao.insert(checkGroupBean);

                    try {
                        for (int k = 0; k < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getPropertySet().getProperty().size(); k++) {
                            PropertyBean propertyBean = new PropertyBean(null,
                                    id,
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getPropertySet().getProperty().get(k).getName(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getPropertySet().getProperty().get(k).getValue());
                            propertyBeanDao.insert(propertyBean);
                        }
                    } catch (Exception o) {

                    }


                    try {

                        for (int k = 0; k < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().size(); k++) {
                            CheckItemBean checkItemBean = new CheckItemBean(null,
                                    id,
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getName(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getOptions(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getSelected(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getImgAndVideo());
                            checkItemBeanDao.insert(checkItemBean);

                            try {
                                for (int l = 0; l < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getPropertySet().getProperty().size(); l++) {
                                    PropertyBeanX propertyBeanX = new PropertyBeanX(null,
                                            id,
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getPropertySet().getProperty().get(l).getName(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getPropertySet().getProperty().get(l).getValue());
                                    propertyBeanXDao.insert(propertyBeanX);
                                }
                            } catch (Exception o) {

                            }
                            try {
                                for (int l = 0; l < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getRelatedDocumentIdSet().getRelatedDocumentId().size(); l++) {
                                    RelatedDocumentIdSetBean relatedDocumentIdSetBean = new RelatedDocumentIdSetBean(null,
                                            id,
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getId(),
                                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getRelatedDocumentIdSet().getRelatedDocumentId().get(l));
                                    relatedDocumentIdSetBeanDao.insert(relatedDocumentIdSetBean);
                                }
                            } catch (Exception o) {

                            }


                        }

                    } catch (Exception o) {

                    }


                }

            } catch (Exception o) {

            }


        }

        CheckVerdBeanDao checkVerdBeanDao = MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        CheckVerdBean checkVerdBean = new CheckVerdBean(null,
                id,
                dataPackageBean.getCheckVerd().getId(),
                dataPackageBean.getCheckVerd().getName(),
                dataPackageBean.getCheckVerd().getCode(),
                dataPackageBean.getCheckVerd().getQConclusion(),
                dataPackageBean.getCheckVerd().getgConclusion(),
                dataPackageBean.getCheckVerd().getjConclusion(),
                dataPackageBean.getCheckVerd().getConclusion(),
                dataPackageBean.getCheckVerd().getCheckPerson());
        checkVerdBeanDao.insert(checkVerdBean);

        CheckUnresolvedBeanDao checkUnresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();

        CheckUnresolvedBean checkUnresolvedBean = new CheckUnresolvedBean(null,
                id,
                dataPackageBean.getCheckUnresolved().getId(),
                dataPackageBean.getCheckUnresolved().getName(),
                dataPackageBean.getCheckUnresolved().getCode());
        checkUnresolvedBeanDao.insert(checkUnresolvedBean);

        UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();

        FileBeanDao fileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getFileBeanDao();

        try {

            for (int i = 0; i < dataPackageBean.getUnresolvedSet().getUnresolved().size(); i++) {
                UnresolvedBean unresolvedBean = new UnresolvedBean(null,
                        id,
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getProductCode(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getQuestion(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmer(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmTime(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileId());
                unresolvedBeanDao.insert(unresolvedBean);

                try {
                    for (int j = 0; j < dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().size(); j++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getId(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getName(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getPath(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getType());
                        fileBeanDao.insert(fileBean);
                    }
                } catch (Exception o) {

                }


            }

        } catch (Exception o) {

        }
        DeliveryListBeanDao deliveryListBeanDao = MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();

        try {
            for (int i = 0; i < dataPackageBean.getDeliveryLists().getDeliveryList().size(); i++) {

                DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                        id,
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getIsParent(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getProject(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getParentId());
                deliveryListBeanDao.insert(deliveryListBean);
            }
        } catch (Exception o) {

        }
        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();

        try {

            for (int i = 0; i < dataPackageBean.getDocumentListSet().getDocument().size(); i++) {
                DocumentBean documentBean = new DocumentBean(null,
                        id,
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

                for (int j = 0; j < dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().size(); j++) {
                    FileBean fileBean = new FileBean(null,
                            id,
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getId(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getName(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getPath(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getType());
                    fileBeanDao.insert(fileBean);
                }


            }
        } catch (Exception o) {

        }

    }


    private void mobanPo() {
        View poview = getLayoutInflater().inflate(R.layout.moban_item, null);
        PopupWindow daochu = new PopupWindow(poview);
        daochu.setHeight(300);
        daochu.setWidth(600);
        daochu.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        daochu.setOutsideTouchable(true);
        daochu.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        daochu.showAtLocation(tvMoban, Gravity.CENTER, 0, 0);

        daochu.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });


        List<PackageBean> list = new ArrayList<>();
        MyListView lv_moban = poview.findViewById(R.id.lv_moban);
        File files = new File(Environment.getExternalStorageDirectory() + "/模板");
        File[] subFile = files.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            String filename = subFile[i].getName();
            File file = new File(filename);
            /* 取得扩展名 */
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
            if (end.equals("zip")) {
                list.add(new PackageBean(filename, subFile[i] + ""));
            }
        }

        MoAdapter moAdapter = new MoAdapter(this, list);
        lv_moban.setAdapter(moAdapter);

        lv_moban.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moban = list.get(i).getPath();
                name = list.get(i).getName();
                daochu.dismiss();
                tvMoban.setText(name);
            }
        });


    }


}
