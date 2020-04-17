package com.example.acceptance.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.acceptance.R;
import com.example.acceptance.adapter.ToAdapter;
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
import com.example.acceptance.loding.LoadingView;
import com.example.acceptance.utils.SPUtils;
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

public class ToActivity extends BaseActivity {

    @BindView(R.id.help_center_loading_prgbar)
    RelativeLayout helpCenterLoadingPrgbar;

    public static Intent openIntent(Context context) {
        Intent intent = new Intent(context, ToActivity.class);
        return intent;
    }

    @BindView(R.id.iv_genduo)
    ImageView ivGenduo;
    @BindView(R.id.tv_tuichu)
    TextView tvTuichu;
    @BindView(R.id.lv_checklist)
    MyListView lvChecklist;
    private List<PackageBean> list = new ArrayList<>();
    private boolean isPath = false;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    helpCenterLoadingPrgbar.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    helpCenterLoadingPrgbar.setVisibility(View.GONE);
                    break;

            }

        }
    };

    @Override
    protected void initView() {
        ivGenduo.setOnClickListener(view -> finish());

        File files = new File(Environment.getExternalStorageDirectory() + "/数据包");
        if (!files.exists()){
            files.mkdirs();
        }
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
        ToAdapter toAdapter = new ToAdapter(this, list);
        lvChecklist.setAdapter(toAdapter);

        lvChecklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).getPath() != null) {
                    DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
                    List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                            .where(DataPackageDBeanDao.Properties.NamePackage.eq(list.get(i).getName()))
                            .list();

                    if (dataPackageDBeans != null && !dataPackageDBeans.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ToActivity.this);
                        builder.setTitle("数据包已存在，是否覆盖");
                        builder.setPositiveButton("覆盖！！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isPath = true;
                                handler.sendEmptyMessage(1);
                                new Thread(){
                                    @Override
                                    public void run() {
                                        getJieya(list.get(i).getPath());
                                        //需要在子线程中处理的逻辑
                                    }
                                }.start();



                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isPath = false;
                            }
                        });
                        builder.show();

                    } else {
                        isPath = false;
                        handler.sendEmptyMessage(1);
                        new Thread(){
                            @Override
                            public void run() {
                                getJieya(list.get(i).getPath());
                                //需要在子线程中处理的逻辑
                            }
                        }.start();

                    }


                }


            }
        });
    }

    private void getJieya(String path) {
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
            filePath(upLoadFile, upLoadFileName);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to;
    }

    @Override
    protected void initDataAndEvent() {

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

        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(dataPackageBean.getId()))
                .list();
        if (dataPackageDBeans != null && !dataPackageDBeans.isEmpty()) {
            isPath = true;

        }

        if (isPath) {
            for (int i = 0; i < dataPackageDBeans.size(); i++) {
                dataPackageDBeanDao.deleteByKey(dataPackageDBeans.get(i).getUId());
            }
        }
        DataPackageDBean dataPackageDBean = new DataPackageDBean(null,
                upLoadFileName,
                upLoadFile,
                dataPackageBean.getId(),
                dataPackageBean.getName(),
                dataPackageBean.getCode(),
                dataPackageBean.getType(),
                dataPackageBean.getResponseUnit(),
                dataPackageBean.getModelCode(),
                dataPackageBean.getProductName(),
                dataPackageBean.getProductCode(),
                dataPackageBean.getProductType(),
                dataPackageBean.getBatch(),
                dataPackageBean.getCreator(),
                dataPackageBean.getCreateTime(),
                dataPackageBean.getModelSeries(),
                dataPackageBean.getModelSeriesName(),
                dataPackageBean.getPkgTemplateId(),
                dataPackageBean.getLifecycleStateId(),
                dataPackageBean.getLifecycleStateIdentifier(),
                dataPackageBean.getBaseType(),
                dataPackageBean.getModelSeriesId(),
                dataPackageBean.getRepositoryId(),
                dataPackageBean.getIsTemplate(),
                dataPackageBean.getOwnerId(),
                dataPackageBean.getProductTypeValue(),
                dataPackageBean.getApplyCompany(),
                dataPackageBean.getAcceptorUnit(),
                dataPackageBean.getStage(),
                dataPackageBean.getUniqueValue(),
                dataPackageBean.getVersionInfo());
        dataPackageDBeanDao.insert(dataPackageDBean);
        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();

        if (isPath) {
            List<CheckApplyBean> beans = checkApplyBeanDao.queryBuilder()
                    .where(CheckApplyBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkApplyBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }

        FileBeanDao fileBeanDao = MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
        if (isPath) {
            List<FileBean> beans = fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                fileBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        try {

            CheckApplyBean checkApplyBean = new CheckApplyBean(null,
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
                    dataPackageBean.getCheckApply().getDescription(),
                    dataPackageBean.getCheckApply().getDocTypeVal(),
                    dataPackageBean.getCheckApply().getAcceptorUnit(),
                    dataPackageBean.getCheckApply().getAcceptor(),
                    dataPackageBean.getCheckApply().getAcceptorDept());
            checkApplyBeanDao.insert(checkApplyBean);

            for (int i = 0; i <dataPackageBean.getCheckApply().getFileSet().getFile().size() ; i++) {
                FileBean fileBean = new FileBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getCheckApply().getId(),
                        dataPackageBean.getCheckApply().getFileSet().getFile().get(i).getName(),
                        dataPackageBean.getCheckApply().getFileSet().getFile().get(i).getPath(),
                        dataPackageBean.getCheckApply().getFileSet().getFile().get(i).getType(),
                        dataPackageBean.getCheckApply().getFileSet().getFile().get(i).getSecret(),
                        dataPackageBean.getCheckApply().getFileSet().getFile().get(i).getDisabledSecret());
                fileBeanDao.insert(fileBean);
            }

        } catch (Exception o) {

        }

        CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        if (isPath) {
            List<CheckTaskBean> beans = checkTaskBeanDao.queryBuilder()
                    .where(CheckTaskBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkTaskBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        CheckTaskBean checkTaskBean = new CheckTaskBean(null,
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
                dataPackageBean.getCheckTask().getDocTypeVal());
        checkTaskBeanDao.insert(checkTaskBean);

        ApplyDeptBeanDao applyDeptBeanDao = MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();
        if (isPath) {
            List<ApplyDeptBean> beans = applyDeptBeanDao.queryBuilder()
                    .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                applyDeptBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        try {
            for (int i = 0; i < dataPackageBean.getCheckTask().getApplyDeptSet().getApplyDept().size(); i++) {
                ApplyDeptBean applyDeptBean = new ApplyDeptBean(null,
                        dataPackageBean.getId(),
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
        if (isPath) {
            List<ApplyItemBean> beans = applyItemBeanDao.queryBuilder()
                    .where(ApplyItemBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                applyItemBeanDao.deleteByKey(beans.get(i).getUId());
            }

        }
        if (dataPackageBean.getApplyItemSet()!=null&&dataPackageBean.getApplyItemSet().getApplyItem()!=null&&dataPackageBean.getApplyItemSet().getApplyItem().size()!=0){
            for (int i = 0; i < dataPackageBean.getApplyItemSet().getApplyItem().size(); i++) {
                ApplyItemBean applyItemBean = new ApplyItemBean(null,
                        dataPackageBean.getId(),
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
        }


        CheckFileBeanDao checkFileBeanDao = MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        if (isPath) {
            List<CheckFileBean> beans = checkFileBeanDao.queryBuilder()
                    .where(CheckFileBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkFileBeanDao.deleteByKey(beans.get(i).getUId());
            }

        }
        CheckGroupBeanDao checkGroupBeanDao = MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        if (isPath) {
            List<CheckGroupBean> beans = checkGroupBeanDao.queryBuilder()
                    .where(CheckGroupBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkGroupBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        PropertyBeanDao propertyBeanDao = MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        if (isPath) {
            List<PropertyBean> beans = propertyBeanDao.queryBuilder()
                    .where(PropertyBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                propertyBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        CheckItemBeanDao checkItemBeanDao = MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        if (isPath) {
            List<CheckItemBean> beans = checkItemBeanDao.queryBuilder()
                    .where(CheckItemBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkItemBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        PropertyBeanXDao propertyBeanXDao = MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        if (isPath) {
            List<PropertyBeanX> beans = propertyBeanXDao.queryBuilder()
                    .where(PropertyBeanXDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                propertyBeanXDao.deleteByKey(beans.get(i).getUId());
            }
        }
        AcceptDeviceBeanDao acceptDeviceBeanDao = MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
        if (isPath) {
            List<AcceptDeviceBean> beans = acceptDeviceBeanDao.queryBuilder()
                    .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                acceptDeviceBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }

        RelatedDocumentIdSetBeanDao relatedDocumentIdSetBeanDao = MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        if (isPath) {
            List<RelatedDocumentIdSetBean> beans = relatedDocumentIdSetBeanDao.queryBuilder()
                    .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                relatedDocumentIdSetBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }


        for (int i = 0; i < dataPackageBean.getCheckFileSet().getCheckFile().size(); i++) {
            CheckFileBean checkFileBean = new CheckFileBean(null,
                    dataPackageBean.getId(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getName(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCode(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getDocType(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getProductType(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getConclusion(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckPerson(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckDate(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getSortBy(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckTime(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getSort(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getTabsName(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getAccordFile(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getSelectEdit(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getUniqueValue(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getProductTypeValue(),
                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getDescription());
            checkFileBeanDao.insert(checkFileBean);

            try {
                for (int j = 0; j <dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().size() ; j++) {
                    FileBean fileBean = new FileBean(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().get(j).getName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().get(j).getPath(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().get(j).getType(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().get(j).getSecret(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getFileSet().getFile().get(j).getDisabledSecret());
                    fileBeanDao.insert(fileBean);
                }
            }catch (Exception io){

            }


            try {

                for (int j = 0; j < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().size(); j++) {
                    CheckGroupBean checkGroupBean = new CheckGroupBean(null,
                            dataPackageBean.getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getGroupName(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckGroupConclusion(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckPerson(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsConclusion(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getIsTable(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getUniqueValue(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckTime(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getConclusionF(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckPersonF(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getSort(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckTimeF(),
                            dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getTestTable());
                    checkGroupBeanDao.insert(checkGroupBean);

                    try {
                        for (int k = 0; k <dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().size() ; k++) {
                            FileBean fileBean = new FileBean(null,
                                    dataPackageBean.getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().get(k).getName(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().get(k).getPath(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().get(k).getType(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().get(k).getSecret(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getFileSet().getFile().get(k).getDisabledSecret());
                            fileBeanDao.insert(fileBean);
                        }
                    }catch (Exception io){

                    }


                    try {
                        for (int k = 0; k < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getAcceptDeviceSet().getAcceptDevice().size(); k++) {
                            AcceptDeviceBean acceptDeviceBean = new AcceptDeviceBean(null, dataPackageBean.getId(),
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
                                    dataPackageBean.getId(),
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
                                    dataPackageBean.getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getId(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getName(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getOptions(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getSelected(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getUniqueValue(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getSort(),
                                    dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getDescription());
                            checkItemBeanDao.insert(checkItemBean);

                            try {
                                for (int l = 0; l < dataPackageBean.getCheckFileSet().getCheckFile().get(i).getCheckGroupSet().getCheckGroup().get(j).getCheckItemSet().getCheckItem().get(k).getPropertySet().getProperty().size(); l++) {
                                    PropertyBeanX propertyBeanX = new PropertyBeanX(null,
                                            dataPackageBean.getId(),
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
                                            dataPackageBean.getId(),
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
        if (isPath) {
            List<CheckVerdBean> beans = checkVerdBeanDao.queryBuilder()
                    .where(CheckVerdBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkVerdBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        CheckVerdBean checkVerdBean = new CheckVerdBean(null,
                dataPackageBean.getId(),
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
                dataPackageBean.getCheckVerd().getCheckDate(),
                dataPackageBean.getCheckVerd().getyConclusion());
        checkVerdBeanDao.insert(checkVerdBean);

        try {
            for (int k = 0; k <dataPackageBean.getCheckVerd().getFileSet().getFile().size() ; k++) {
                FileBean fileBean = new FileBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getCheckVerd().getId(),
                        dataPackageBean.getCheckVerd().getFileSet().getFile().get(k).getName(),
                        dataPackageBean.getCheckVerd().getFileSet().getFile().get(k).getPath(),
                        dataPackageBean.getCheckVerd().getFileSet().getFile().get(k).getType(),
                        dataPackageBean.getCheckVerd().getFileSet().getFile().get(k).getSecret(),
                        dataPackageBean.getCheckVerd().getFileSet().getFile().get(k).getDisabledSecret());
                fileBeanDao.insert(fileBean);
            }
        }catch (Exception io){

        }

        CheckUnresolvedBeanDao checkUnresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();
        if (isPath) {
            List<CheckUnresolvedBean> beans = checkUnresolvedBeanDao.queryBuilder()
                    .where(CheckUnresolvedBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                checkUnresolvedBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        CheckUnresolvedBean checkUnresolvedBean = new CheckUnresolvedBean(null,
                dataPackageBean.getId(),
                dataPackageBean.getCheckUnresolved().getId(),
                dataPackageBean.getCheckUnresolved().getName(),
                dataPackageBean.getCheckUnresolved().getCode(),
                dataPackageBean.getCheckUnresolved().getDocTypeVal());
        checkUnresolvedBeanDao.insert(checkUnresolvedBean);

        UnresolvedBeanDao unresolvedBeanDao = MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        if (isPath) {
            List<UnresolvedBean> beans = unresolvedBeanDao.queryBuilder()
                    .where(UnresolvedBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                unresolvedBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }


        try {

            for (int i = 0; i < dataPackageBean.getUnresolvedSet().getUnresolved().size(); i++) {
                UnresolvedBean unresolvedBean = new UnresolvedBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getProductCode(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getQuestion(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmer(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getConfirmTime(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileId(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getUniqueValue(),
                        dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getDescription());
                unresolvedBeanDao.insert(unresolvedBean);

                try {
                    for (int j = 0; j < dataPackageBean.getUnresolvedSet().getUnresolved().get(i).getFileSet().getFile().size(); j++) {
                        FileBean fileBean = new FileBean(null,
                                dataPackageBean.getId(),
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
        if (isPath) {
            List<DeliveryListBean> beans = deliveryListBeanDao.queryBuilder()
                    .where(DeliveryListBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                deliveryListBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }
        try {
            for (int i = 0; i < dataPackageBean.getDeliveryLists().getDeliveryList().size(); i++) {

                DeliveryListBean deliveryListBean = new DeliveryListBean(null,
                        dataPackageBean.getId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getIsParent(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getProject(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getParentId(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getUniqueValue(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getTypeDisplay(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getSortBy(),
                        dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getSort());
                deliveryListBeanDao.insert(deliveryListBean);
                try {
                    for (int l = 0; l < dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getRelatedDocumentIdSet().getRelatedDocumentId().size(); l++) {
                        RelatedDocumentIdSetBean relatedDocumentIdSetBean = new RelatedDocumentIdSetBean(null,
                                dataPackageBean.getId(),
                                dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                                dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                                dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getId(),
                                dataPackageBean.getDeliveryLists().getDeliveryList().get(i).getRelatedDocumentIdSet().getRelatedDocumentId().get(l));
                        relatedDocumentIdSetBeanDao.insert(relatedDocumentIdSetBean);
                    }
                } catch (Exception o) {

                }

            }
        } catch (Exception o) {

        }
        DocumentBeanDao documentBeanDao = MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        if (isPath) {
            List<DocumentBean> beans = documentBeanDao.queryBuilder()
                    .where(DocumentBeanDao.Properties.DataPackageId.eq(dataPackageDBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < beans.size(); i++) {
                documentBeanDao.deleteByKey(beans.get(i).getUId());
            }
        }

        try {
            for (int i = 0; i < dataPackageBean.getDocumentListSet().getDocument().size(); i++) {
                try {
                    DocumentBean documentBean = new DocumentBean(null,
                            dataPackageBean.getId(),
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
                }catch (Exception ex){}



                try {
                    for (int j = 0; j < dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().size(); j++) {
                        FileBean fileBean = new FileBean(null,
                                dataPackageBean.getId(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getId(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getName(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getPath(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getType(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getSecret(),
                                dataPackageBean.getDocumentListSet().getDocument().get(i).getFileSet().getFile().get(j).getDisabledSecret());
                        fileBeanDao.insert(fileBean);
                    }
                }catch (Exception ex){

                }


            }
        } catch (Exception o) {

        }

        handler.sendEmptyMessage(2);
        startActivity(MainActivity.openIntent(ToActivity.this, dataPackageBean.getId(), false, ""));
        finish();
    }


}
