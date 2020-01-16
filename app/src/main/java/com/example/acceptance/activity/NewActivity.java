package com.example.acceptance.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.acceptance.R;
import com.example.acceptance.adapter.MoAdapter;
import com.example.acceptance.adapter.TypeAdapter;
import com.example.acceptance.base.BaseActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.DataPackageBean;
import com.example.acceptance.bean.PackageBean;
import com.example.acceptance.greendao.bean.AcceptDeviceBean;
import com.example.acceptance.greendao.bean.ApplyDeptBean;
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
import com.example.acceptance.greendao.db.ApplyDeptBeanDao;
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
import com.example.acceptance.utils.SPUtils;
import com.example.acceptance.utils.StringUtils;
import com.example.acceptance.utils.ToastUtils;
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
    @BindView(R.id.help_loading)
    RelativeLayout help_loading;
    @BindView(R.id.tv_modelSeries)
    EditText tvModelSeries;
    @BindView(R.id.tv_modelSeriesName)
    EditText tvModelSeriesName;
    @BindView(R.id.tv_type2)
    TextView tvType2;
    @BindView(R.id.tv_responseUnit2)
    TextView tvResponseUnit2;
    @BindView(R.id.tv_productType2)
    TextView tvProductType2;
    private String moban = "";
    private String name = "";
    private String id;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    help_loading.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    help_loading.setVisibility(View.GONE);
                    startActivity(MainActivity.openIntent(NewActivity.this, id, false, ""));
                    finish();
                    break;
            }

        }
    };

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

        tvType2.setOnClickListener(this);
        tvResponseUnit2.setOnClickListener(this);
        tvProductType2.setOnClickListener(this);
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
                if (StringUtils.isBlank(tvCode.getText().toString().trim())){
                    ToastUtils.getInstance().showTextToast(this,"编号不能为空");
                    return;
                }

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
                        DataUtils.getData(),
                        tvModelSeries.getText().toString().trim(),
                        tvModelSeriesName.getText().toString().trim(),
                        "", "", "",
                        "", "", "",
                        "", "");
                dataPackageDBeanDao.insert(dataPackageDBean);

                if (!StringUtils.isBlank(moban)) {

                    handler.sendEmptyMessage(1);
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                ZipUtils2.UnZipFolder(moban, Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim());
                                filePath(Environment.getExternalStorageDirectory() + "/数据包/" + tvCode.getText().toString().trim(), tvCode.getText().toString().trim());
                                handler.sendEmptyMessage(2);
                                //需要在子线程中处理的逻辑
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                } else {
                    startActivity(MainActivity.openIntent(NewActivity.this, id, false, ""));
                    finish();
                }

                break;
            case R.id.tv_moban:
                mobanPo();
                break;
            case R.id.tv_type2:
                String type= (String) SPUtils.get(this,"PacketType","");
                if (!StringUtils.isBlank(type)){
                    typePo(tvType,type);
                }
                break;
            case R.id.tv_responseUnit2:
                String type2= (String) SPUtils.get(this,"DutyType","");
                if (!StringUtils.isBlank(type2)){
                    typePo(tvResponseUnit,type2);
                }
                break;
            case R.id.tv_productType2:
                String type3= (String) SPUtils.get(this,"ProductType","");
                if (!StringUtils.isBlank(type3)){
                    typePo(tvProductType,type3);
                }
                break;
        }
    }

    private void typePo(EditText editText, String str) {
        View poview = getLayoutInflater().inflate(R.layout.moban_item2, null);
        PopupWindow daochu = new PopupWindow(poview);
        daochu.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        daochu.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        daochu.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        daochu.setOutsideTouchable(true);
        daochu.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        daochu.showAsDropDown(editText);

        daochu.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });

        List<String> list = new ArrayList<>();
        MyListView lv_moban = poview.findViewById(R.id.lv_moban);
        String[] ss = str.split(",");
        for (int i = 0; i < ss.length; i++) {
            list.add(ss[i]);
        }

        TypeAdapter moAdapter = new TypeAdapter(this, list);
        lv_moban.setAdapter(moAdapter);

        lv_moban.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                daochu.dismiss();
                editText.setText(list.get(i));
            }
        });


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
                    dataPackageBean.getCheckApply().getDescription(),
                    dataPackageBean.getCheckApply().getDocTypeVal());
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
                dataPackageBean.getCheckTask().getPhone(),
                dataPackageBean.getCheckTask().getDocTypeVal());
        checkTaskBeanDao.insert(checkTaskBean);

        ApplyDeptBeanDao applyDeptBeanDao = MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();
        try {
            for (int i = 0; i < dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().size(); i++) {
                ApplyDeptBean applyDeptBean = new ApplyDeptBean(null,
                        id,
                        dataPackageBean.getCheckTask().getId(),
                        dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().get(i).getId(),
                        dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().get(i).getDepartment(),
                        dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().get(i).getAcceptor(),
                        dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().get(i).getOther());
                applyDeptBeanDao.insert(applyDeptBean);
            }
        } catch (Exception o) {

        }


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
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getPassCheck(),
                    dataPackageBean.getApplyItemSet().getApplyItem().get(i).getUniqueValue());
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
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getProductType(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getConclusion(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckPerson(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckDate(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getSortBy());
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
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsTable(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getUniqueValue());
                    checkGroupBeanDao.insert(checkGroupBean);

                    try {
                        for (int k = 0; k < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().size(); k++) {
                            AcceptDeviceBean acceptDeviceBean = new AcceptDeviceBean(null, id,
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getName(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getSpecification(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getAccuracy(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getCertificate(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().get(k).getDescription());
                            acceptDeviceBeanDao.insert(acceptDeviceBean);
                        }

                    } catch (Exception o) {

                    }

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
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getUniqueValue());
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
                dataPackageBean.getCheckVerd().getCheckPerson(),
                dataPackageBean.getCheckVerd().getDocTypeVal(),
                dataPackageBean.getCheckVerd().getCheckPersonId(),
                dataPackageBean.getCheckVerd().getCheckDate());
        checkVerdBeanDao.insert(checkVerdBean);

        CheckUnresolvedBeanDao checkUnresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();

        CheckUnresolvedBean checkUnresolvedBean = new CheckUnresolvedBean(null,
                id,
                dataPackageBean.getCheckUnresolved().getId(),
                dataPackageBean.getCheckUnresolved().getName(),
                dataPackageBean.getCheckUnresolved().getCode(),
                dataPackageBean.getCheckUnresolved().getDocTypeVal());
        checkUnresolvedBeanDao.insert(checkUnresolvedBean);

        UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();

        FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();

        try {

            for (int i = 0; i < dataPackageBean.getUnresolvedSet().getUnresolved().size(); i++) {
                UnresolvedBean unresolvedBean = new UnresolvedBean(null,
                        id,
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getProductCode(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getQuestion(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmer(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmTime(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getUniqueValue());
                unresolvedBeanDao.insert(unresolvedBean);

                try {
                    for (int j = 0; j < dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().size(); j++) {
                        FileBean fileBean = new FileBean(null,
                                id,
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getId(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getName(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getPath(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getType(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getSecret(),
                                dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().get(j).getDisabledSecret());
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
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getParentId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getUniqueValue(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getTypeDisplay(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getSortBy());
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
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getPayClassifyName(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getModalCode(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getProductCodeName(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getProductCode(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getStage(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getTechStatus(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getApprover(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getApprovalDate(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getIssl(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getConclusion(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getDescription(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getOnLine(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getInfoUrl(),
                        dataPackageBean.getDocumentListSet().getDocument().get(i).getUniqueValue());
                documentBeanDao.insert(documentBean);

                for (int j = 0; j < dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().size(); j++) {
                    FileBean fileBean = new FileBean(null,
                            id,
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getId(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getName(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getPath(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getType(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getSecret(),
                            dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getDisabledSecret());
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
            if (!subFile[i].exists()||subFile[i].length()==0){
                ToastUtils.getInstance().showTextToast(this,"模板错误");
                return;
            }
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
