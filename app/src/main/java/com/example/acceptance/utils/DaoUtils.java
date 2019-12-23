package com.example.acceptance.utils;

import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.acceptance.R;
import com.example.acceptance.activity.MainActivity;
import com.example.acceptance.base.MyApplication;
import com.example.acceptance.bean.DataPackageBean;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/16 21
 */
public class DaoUtils {


    public static void setDao(String id) {
        XStream xStream = new XStream();

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean dataPackageBean = new DataPackageBean();

        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        DocumentBeanDao documentBeanDao=MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        ApplyItemBeanDao applyItemBeanDao=MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        CheckFileBeanDao checkFileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        CheckGroupBeanDao checkGroupBeanDao=MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        PropertyBeanDao propertyBeanDao=MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        CheckItemBeanDao checkItemBeanDao=MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        AcceptDeviceBeanDao acceptDeviceBeanDao=MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
        PropertyBeanXDao propertyBeanXDao=MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        RelatedDocumentIdSetBeanDao relatedDocumentIdSetBeanDao=MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        CheckVerdBeanDao checkVerdBeanDao=MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        CheckUnresolvedBeanDao checkUnresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();
        FileBeanDao fileBeanDao=MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
        UnresolvedBeanDao unresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        DeliveryListBeanDao deliveryListBeanDao=MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        ApplyDeptBeanDao applyDeptBeanDao = MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();


        List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(id)).list();
        if (dataPackageDBeans != null && !dataPackageDBeans.isEmpty()) {
            dataPackageBean.setId(dataPackageDBeans.get(0).getId());
            dataPackageBean.setBatch(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getBatch() : "");
            dataPackageBean.setCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCode() : "");
            dataPackageBean.setCreateTime(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCreateTime() : "");
            dataPackageBean.setType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getType() : "");
            dataPackageBean.setResponseUnit(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getResponseUnit() : "");
            dataPackageBean.setProductType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductType() : "");
            dataPackageBean.setProductName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductName() : "");
            dataPackageBean.setProductCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductCode() : "");
            dataPackageBean.setName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getName() : "");
            dataPackageBean.setModelCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelCode() : "");
            dataPackageBean.setModelSeries(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeries() : "");
            dataPackageBean.setModelSeriesName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeriesName() : "");
            dataPackageBean.setCreator(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCreator() : "");
            dataPackageBean.setLifecycleStateId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getLifecycleStateId() : "");
            dataPackageBean.setLifecycleStateIdentifier(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getLifecycleStateIdentifier() : "");
            dataPackageBean.setPkgTemplateId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getPkgTemplateId() : "");
            dataPackageBean.setBaseType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getBaseType() : "");
            dataPackageBean.setModelSeriesId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeriesId() : "");
            dataPackageBean.setRepositoryId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getRepositoryId() : "");
            dataPackageBean.setIsTemplate(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getIsTemplate() : "");
            dataPackageBean.setOwnerId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getOwnerId() : "");
            xStream.alias("DataPackage", DataPackageBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.class, "id");

        }

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckApplyBean checkApplyBean = new DataPackageBean.CheckApplyBean();
        List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                .list();
        if (checkApplyBeans != null && !checkApplyBeans.isEmpty()) {
            checkApplyBean.setApplicant(checkApplyBeans.get(0).getApplicant());
            checkApplyBean.setApplyCompany(checkApplyBeans.get(0).getApplyCompany());
            checkApplyBean.setCode(checkApplyBeans.get(0).getCode());
            checkApplyBean.setPhone(checkApplyBeans.get(0).getPhone());
            checkApplyBean.setName(checkApplyBeans.get(0).getName());
            checkApplyBean.setId(checkApplyBeans.get(0).getId());
            checkApplyBean.setDescription(checkApplyBeans.get(0).getDescription());
            checkApplyBean.setContractName(checkApplyBeans.get(0).getContractName());
            checkApplyBean.setContractCode(checkApplyBeans.get(0).getContractCode());
            checkApplyBean.setConclusion(checkApplyBeans.get(0).getConclusion());
            checkApplyBean.setDocTypeVal(checkApplyBeans.get(0).getDocTypeVal());
            xStream.alias("checkApply", DataPackageBean.CheckApplyBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckApplyBean.class, "id");

        }
        dataPackageBean.setCheckApply(checkApplyBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean.CheckTaskBean checkTaskBean = new DataPackageBean.CheckTaskBean();
        List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                .list();
        if (checkTaskBeans != null && !checkTaskBeans.isEmpty()) {
            checkTaskBean.setAcceptDate(checkTaskBeans.get(0).getAcceptDate());
            checkTaskBean.setPhone(checkTaskBeans.get(0).getPhone());
            checkTaskBean.setName(checkTaskBeans.get(0).getName());
            checkTaskBean.setIssuer(checkTaskBeans.get(0).getIssuer());
            checkTaskBean.setIssueDept(checkTaskBeans.get(0).getIssueDept());
            checkTaskBean.setId(checkTaskBeans.get(0).getId());
            checkTaskBean.setCode(checkTaskBeans.get(0).getCode());
            checkTaskBean.setCheckDate(checkTaskBeans.get(0).getCheckDate());
            checkTaskBean.setApplyCompany(checkTaskBeans.get(0).getApplyCompany());
            checkTaskBean.setApplicant(checkTaskBeans.get(0).getApplicant());
            checkTaskBean.setAccepter(checkTaskBeans.get(0).getAccepter());
            checkTaskBean.setDocTypeVal(checkTaskBeans.get(0).getDocTypeVal());
            xStream.alias("checkTask", DataPackageBean.CheckTaskBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckTaskBean.class, "id");

            DataPackageBean.CheckTaskBean.ApplyDeptSetBean applyDeptSetBean = new DataPackageBean.CheckTaskBean.ApplyDeptSetBean();
            List<DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean> applyDeptBeanList = new ArrayList<>();

            List<ApplyDeptBean> applyDeptBeans = applyDeptBeanDao.queryBuilder()
                    .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(id))
                    .where(ApplyDeptBeanDao.Properties.CheckTaskId.eq(checkTaskBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < applyDeptBeans.size(); i++) {
                DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean applyDeptBean = new DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean();
                applyDeptBean.setAcceptor(applyDeptBeans.get(i).getAcceptor());
                applyDeptBean.setDepartment(applyDeptBeans.get(i).getDepartment());
                applyDeptBean.setId(applyDeptBeans.get(i).getId());
                applyDeptBean.setOther(applyDeptBeans.get(i).getOther());
                xStream.alias("ApplyDept", DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean.class);//为类名节点重命名
                xStream.addImplicitCollection(DataPackageBean.CheckTaskBean.ApplyDeptSetBean.class, "ApplyDept");
                xStream.useAttributeFor(DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean.class, "id");
                applyDeptBeanList.add(applyDeptBean);
            }
            if (!applyDeptBeanList.isEmpty()){
                applyDeptSetBean.setApplyDept(applyDeptBeanList);
            }
            checkTaskBean.setApplyDeptSet(applyDeptSetBean);
        }
        dataPackageBean.setCheckTask(checkTaskBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.ApplyItemSetBean applyItemSetBean=new DataPackageBean.ApplyItemSetBean();
        List<DataPackageBean.ApplyItemSetBean.ApplyItemBean> ApplyItem=new ArrayList<>();

        List<ApplyItemBean> applyItemBeans=applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < applyItemBeans.size(); i++) {
            DataPackageBean.ApplyItemSetBean.ApplyItemBean applyItemBean=new DataPackageBean.ApplyItemSetBean.ApplyItemBean();

            applyItemBean.setProductStatus(applyItemBeans.get(i).getProductStatus());
            applyItemBean.setProductName(applyItemBeans.get(i).getProductName());
            applyItemBean.setProductCodeName(applyItemBeans.get(i).getProductCodeName());
            applyItemBean.setPassCheck(applyItemBeans.get(i).getPassCheck());
            applyItemBean.setUniqueValue(applyItemBeans.get(i).getUniqueValue());
            applyItemBean.setIsSatisfyRequire(applyItemBeans.get(i).getIsSatisfyRequire());
            applyItemBean.setIsPureCheck(applyItemBeans.get(i).getIsPureCheck());
            applyItemBean.setIsCompleteRoutine(applyItemBeans.get(i).getIsCompleteRoutine());
            applyItemBean.setIsCompleteChoice(applyItemBeans.get(i).getIsCompleteChoice());
            applyItemBean.setIsArmyCheck(applyItemBeans.get(i).getIsArmyCheck());
            applyItemBean.setDescription(applyItemBeans.get(i).getDescription());
            applyItemBean.setCheckCount(applyItemBeans.get(i).getCheckCount());
            applyItemBean.setProductCode(applyItemBeans.get(i).getProductCode());
            applyItemBean.setId(applyItemBeans.get(i).getId());

            xStream.alias("ApplyItem", DataPackageBean.ApplyItemSetBean.ApplyItemBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.ApplyItemSetBean.class, "ApplyItem");
            xStream.useAttributeFor(DataPackageBean.ApplyItemSetBean.ApplyItemBean.class, "id");
            ApplyItem.add(applyItemBean);
        }
        if (!ApplyItem.isEmpty()){
            applyItemSetBean.setApplyItem(ApplyItem);
        }
        dataPackageBean.setApplyItemSet(applyItemSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckFileSetBean checkFileSetBean=new DataPackageBean.CheckFileSetBean();
        List<DataPackageBean.CheckFileSetBean.CheckFileBean> CheckFile=new ArrayList<>();

        List<CheckFileBean> checkFileBeans=checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < checkFileBeans.size(); i++) {
            DataPackageBean.CheckFileSetBean.CheckFileBean checkFileBean=new DataPackageBean.CheckFileSetBean.CheckFileBean();
            checkFileBean.setId(checkFileBeans.get(i).getId());
            checkFileBean.setCheckPerson(checkFileBeans.get(i).getCheckPerson());
            checkFileBean.setCode(checkFileBeans.get(i).getCode());
            checkFileBean.setConclusion(checkFileBeans.get(i).getConclusion());
            checkFileBean.setDocType(checkFileBeans.get(i).getDocType());
            checkFileBean.setProductType(checkFileBeans.get(i).getProductType());
            checkFileBean.setName(checkFileBeans.get(i).getName());
            checkFileBean.setCheckDate(checkFileBeans.get(i).getCheckDate());
            checkFileBean.setSortBy(checkFileBeans.get(i).getSortBy());

            DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean fileSetBean=
                    new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean();
            List<DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean> File=new ArrayList<>();

            try {
                List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(checkFileBeans.get(i).getId()))
                        .list();
                for (int j = 0; j < fileBeans.size(); j++) {
                    DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean fileBean=
                            new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean();
                    fileBean.setName(fileBeans.get(j).getName());
                    fileBean.setPath(fileBeans.get(j).getPath());
                    fileBean.setType(fileBeans.get(j).getType());
                    fileBean.setSecret(fileBeans.get(j).getSecret());
                    fileBean.setDisabledSecret(fileBeans.get(j).getDisabledSecret());
                    xStream.alias("File", DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.class, "File");
                    File.add(fileBean);
                }
            }catch (Exception o){

            }
            if (!File.isEmpty()){
                fileSetBean.setFile(File);
            }
            checkFileBean.setFileSet(fileSetBean);

            DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean checkGroupSetBean=new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean();
            List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean> CheckGroup=new ArrayList<>();

            List<CheckGroupBean> checkGroupBeans=checkGroupBeanDao.queryBuilder()
                    .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                    .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                    .list();
            for (int j = 0; j < checkGroupBeans.size(); j++) {
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean checkGroupBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean();
                checkGroupBean.setCheckGroupConclusion(checkGroupBeans.get(j).getCheckGroupConclusion());
                checkGroupBean.setCheckPerson(checkGroupBeans.get(j).getCheckPerson());
                checkGroupBean.setGroupName(checkGroupBeans.get(j).getGroupName());
                checkGroupBean.setId(checkGroupBeans.get(j).getId());
                checkGroupBean.setIsConclusion(checkGroupBeans.get(j).getIsConclusion());
                checkGroupBean.setIsTable(checkGroupBeans.get(j).getIsTable());
                checkGroupBean.setUniqueValue(checkGroupBeans.get(j).getUniqueValue());

                DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean fileSetBean2=
                        new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean();
                List<DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean> File2=new ArrayList<>();

                try {
                    List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                            .where(FileBeanDao.Properties.DataPackageId.eq(id))
                            .where(FileBeanDao.Properties.DocumentId.eq(checkGroupBeans.get(j).getId()))
                            .list();
                    for (int k = 0; k < fileBeans.size(); k++) {
                        DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean fileBean=
                                new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean();
                        fileBean.setName(fileBeans.get(k).getName());
                        fileBean.setPath(fileBeans.get(k).getPath());
                        fileBean.setType(fileBeans.get(k).getType());
                        fileBean.setSecret(fileBeans.get(k).getSecret());
                        fileBean.setDisabledSecret(fileBeans.get(k).getDisabledSecret());
                        xStream.alias("File", DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean.class);//为类名节点重命名
                        xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.class, "File");
                        File2.add(fileBean);
                    }
                }catch (Exception o){

                }
                if (!File2.isEmpty()){
                    fileSetBean2.setFile(File2);
                }
                checkGroupBean.setFileSet(fileSetBean2);

                //——————————————————————————————————————————
                List<PropertyBean> propertyBeans=propertyBeanDao.queryBuilder()
                        .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                        .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean propertySetBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean> propertySetBeans=new ArrayList<>();
                for (int k = 0; k <propertyBeans.size() ; k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean propertyBean=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean();
                    propertyBean.setName(propertyBeans.get(k).getName());
                    propertyBean.setValue(propertyBeans.get(k).getValue());
                    propertySetBeans.add(propertyBean);
                    xStream.alias("Property", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.class, "Property");
                }
                if (!propertySetBeans.isEmpty()){
                    propertySetBean.setProperty(propertySetBeans);
                }
                checkGroupBean.setPropertySet(propertySetBean);


                //——————————————————————————————————————————

                List<CheckItemBean> checkItemBeans=checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean checkItemSetBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean> CheckItem=new ArrayList<>();
                for (int k = 0; k < checkItemBeans.size(); k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean checkItemBean=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean();
                    checkItemBean.setId(checkItemBeans.get(k).getId());
                    checkItemBean.setName(checkItemBeans.get(k).getName());
                    checkItemBean.setOptions(checkItemBeans.get(k).getOptions());
                    checkItemBean.setSelected(checkItemBeans.get(k).getSelected());
                    checkItemBean.setUniqueValue(checkItemBeans.get(k).getUniqueValue());
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX propertySetBeanX=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX();
                    List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX> Property=new ArrayList<>();

                    List<PropertyBeanX> propertyBeanXList=propertyBeanXDao.queryBuilder()
                            .where(PropertyBeanXDao.Properties.DataPackageId.eq(id))
                            .where(PropertyBeanXDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                            .where(PropertyBeanXDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                            .where(PropertyBeanXDao.Properties.CheckItemId.eq(checkItemBeans.get(k).getId()))
                            .list();

                    for (int l = 0; l < propertyBeanXList.size(); l++) {
                        DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX propertyBeanX=
                                new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX();
                        propertyBeanX.setName(propertyBeanXList.get(l).getName());
                        propertyBeanX.setValue(propertyBeanXList.get(l).getValue());
                        xStream.alias("Property", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX.class);//为类名节点重命名
                        xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.class, "Property");
                        Property.add(propertyBeanX);
                    }
                    if (!Property.isEmpty()){
                        propertySetBeanX.setProperty(Property);
                    }

                    checkItemBean.setPropertySet(propertySetBeanX);


                    List<RelatedDocumentIdSetBean> relatedDocumentIdSetBeans=relatedDocumentIdSetBeanDao.queryBuilder()
                            .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(id))
                            .where(RelatedDocumentIdSetBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                            .where(RelatedDocumentIdSetBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                            .where(RelatedDocumentIdSetBeanDao.Properties.CheckItemId.eq(checkItemBeans.get(k).getId()))
                            .list();

                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.RelatedDocumentIdSetBean relatedDocumentIdSetBean=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.RelatedDocumentIdSetBean();
                    List<String> RelatedDocumentId=new ArrayList<>();

                    for (int l = 0; l <relatedDocumentIdSetBeans.size() ; l++) {
                        RelatedDocumentId.add(relatedDocumentIdSetBeans.get(l).getRelatedDocumentId());
                        xStream.alias("RelatedDocumentId", String.class);//为类名节点重命名
                        xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.RelatedDocumentIdSetBean.class, "RelatedDocumentId");
                    }
                    if (!RelatedDocumentId.isEmpty()){
                        relatedDocumentIdSetBean.setRelatedDocumentId(RelatedDocumentId);
                    }
                    checkItemBean.setRelatedDocumentIdSet(relatedDocumentIdSetBean);

                    xStream.alias("CheckItem", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.class, "CheckItem");
                    xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.class, "id");
                    CheckItem.add(checkItemBean);
                }
                if (!CheckItem.isEmpty()){
                    checkItemSetBean.setCheckItem(CheckItem);
                }
                checkGroupBean.setCheckItemSet(checkItemSetBean);
                //——————————————————————————————————————————

                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet acceptDeviceSet=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice> AcceptDevice=new ArrayList<>();

                List<AcceptDeviceBean> acceptDeviceBeanList=acceptDeviceBeanDao.queryBuilder()
                        .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(id))
                        .where(AcceptDeviceBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(AcceptDeviceBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                for (int k = 0; k <acceptDeviceBeanList.size() ; k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice acceptDevice=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice();
                    acceptDevice.setId(acceptDeviceBeanList.get(k).getId());
                    acceptDevice.setAccuracy(acceptDeviceBeanList.get(k).getAccuracy());
                    acceptDevice.setCertificate(acceptDeviceBeanList.get(k).getCertificate());
                    acceptDevice.setDescription(acceptDeviceBeanList.get(k).getDescription());
                    acceptDevice.setName(acceptDeviceBeanList.get(k).getName());
                    acceptDevice.setSpecification(acceptDeviceBeanList.get(k).getSpecification());

                    xStream.alias("AcceptDevice", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice.class);//为类名节点重命名
                    xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice.class, "id");
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.class, "AcceptDevice");
                    AcceptDevice.add(acceptDevice);
                }
                if (!AcceptDevice.isEmpty()){
                    acceptDeviceSet.setAcceptDevice(AcceptDevice);
                }
                checkGroupBean.setAcceptDeviceSet(acceptDeviceSet);
                xStream.alias("CheckGroup", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.class);//为类名节点重命名
                xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.class, "CheckGroup");
                xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.class, "id");
                CheckGroup.add(checkGroupBean);
            }
            if (!CheckGroup.isEmpty()){
                checkGroupSetBean.setCheckGroup(CheckGroup);
            }
            checkFileBean.setCheckGroupSet(checkGroupSetBean);
            xStream.alias("CheckFile", DataPackageBean.CheckFileSetBean.CheckFileBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.class, "CheckFile");
            xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.class, "id");
            CheckFile.add(checkFileBean);

        }
        if (!CheckFile.isEmpty()){
            checkFileSetBean.setCheckFile(CheckFile);
        }
        dataPackageBean.setCheckFileSet(checkFileSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckVerdBean checkVerdBean=new DataPackageBean.CheckVerdBean();
        List<CheckVerdBean> checkVerdBeans=checkVerdBeanDao.queryBuilder()
                .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < checkVerdBeans.size(); i++) {
            checkVerdBean.setCheckPerson(checkVerdBeans.get(i).getCheckPerson());
            checkVerdBean.setCode(checkVerdBeans.get(i).getCode());
            checkVerdBean.setConclusion(checkVerdBeans.get(i).getConclusion());
            checkVerdBean.setgConclusion(checkVerdBeans.get(i).getGConclusion());
            checkVerdBean.setId(checkVerdBeans.get(i).getId());
            checkVerdBean.setjConclusion(checkVerdBeans.get(i).getJConclusion());
            checkVerdBean.setName(checkVerdBeans.get(i).getName());
            checkVerdBean.setDocTypeVal(checkVerdBeans.get(i).getDocTypeVal());
            checkVerdBean.setqConclusion(checkVerdBeans.get(i).getQConclusion());
            checkVerdBean.setCheckPersonId(checkVerdBeans.get(i).getCheckPersonId());
            checkVerdBean.setCheckDate(checkVerdBeans.get(i).getCheckDate());
            xStream.alias("checkVerd", DataPackageBean.CheckVerdBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckVerdBean.class, "id");

            DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean fileSetBean2=
                    new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean();
            List<DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean> File2=new ArrayList<>();

            try {
                List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(checkVerdBeans.get(i).getId()))
                        .list();
                for (int k = 0; k < fileBeans.size(); k++) {
                    DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean fileBean=
                            new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean();
                    fileBean.setName(fileBeans.get(k).getName());
                    fileBean.setPath(fileBeans.get(k).getPath());
                    fileBean.setType(fileBeans.get(k).getType());
                    fileBean.setSecret(fileBeans.get(k).getSecret());
                    xStream.alias("File", DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.class, "File");
                    File2.add(fileBean);
                }
            }catch (Exception o){

            }
            if (!File2.isEmpty()){
                fileSetBean2.setFile(File2);
            }
            checkVerdBean.setFileSet(fileSetBean2);

        }

        dataPackageBean.setCheckVerd(checkVerdBean);

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckUnresolvedBean checkUnresolvedBean=new DataPackageBean.CheckUnresolvedBean();

        List<CheckUnresolvedBean> checkUnresolvedBeans=checkUnresolvedBeanDao.queryBuilder()
                .where(CheckUnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i <checkUnresolvedBeans.size() ; i++) {
            checkUnresolvedBean.setCode(checkUnresolvedBeans.get(i).getCode());
            checkUnresolvedBean.setId(checkUnresolvedBeans.get(i).getId());
            checkUnresolvedBean.setName(checkUnresolvedBeans.get(i).getName());
            checkUnresolvedBean.setDocTypeVal(checkUnresolvedBeans.get(i).getDocTypeVal());
            xStream.alias("checkUnresolved", DataPackageBean.CheckUnresolvedBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckUnresolvedBean.class, "id");
        }

        dataPackageBean.setCheckUnresolved(checkUnresolvedBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.UnresolvedSetBean unresolvedSetBean=new DataPackageBean.UnresolvedSetBean();
        List<DataPackageBean.UnresolvedSetBean.UnresolvedBean> Unresolved=new ArrayList<>();
        List<UnresolvedBean> unresolvedBeans=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();

        for (int i = 0; i < unresolvedBeans.size(); i++) {
            DataPackageBean.UnresolvedSetBean.UnresolvedBean unresolvedBean=new DataPackageBean.UnresolvedSetBean.UnresolvedBean();
            unresolvedBean.setConfirmer(unresolvedBeans.get(i).getConfirmer());
            unresolvedBean.setConfirmTime(unresolvedBeans.get(i).getConfirmTime());
            unresolvedBean.setFileId(unresolvedBeans.get(i).getFileId());
            unresolvedBean.setId(unresolvedBeans.get(i).getId());
            unresolvedBean.setProductCode(unresolvedBeans.get(i).getProductCode());
            unresolvedBean.setQuestion(unresolvedBeans.get(i).getQuestion());
            unresolvedBean.setUniqueValue(unresolvedBeans.get(i).getUniqueValue());
            DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean fileSetBean=
                    new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean();
            List<DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean> File=new ArrayList<>();


            try {
                List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                        .where(FileBeanDao.Properties.DataPackageId.eq(id))
                        .where(FileBeanDao.Properties.DocumentId.eq(unresolvedBeans.get(i).getId()))
                        .list();
                for (int j = 0; j < fileBeans.size(); j++) {
                    DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean fileBean=
                            new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean();
                    fileBean.setName(fileBeans.get(j).getName());
                    fileBean.setPath(fileBeans.get(j).getPath());
                    fileBean.setType(fileBeans.get(j).getType());
                    fileBean.setSecret(fileBeans.get(j).getSecret());
                    fileBean.setDisabledSecret(fileBeans.get(j).getDisabledSecret());
                    xStream.alias("File", DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.class, "File");
                    File.add(fileBean);
                }
            }catch (Exception o){

            }
            if (!File.isEmpty()){
                fileSetBean.setFile(File);
            }
            unresolvedBean.setFileSet(fileSetBean);
            xStream.alias("Unresolved", DataPackageBean.UnresolvedSetBean.UnresolvedBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.UnresolvedSetBean.class, "Unresolved");
            xStream.useAttributeFor(DataPackageBean.UnresolvedSetBean.UnresolvedBean.class, "id");
            Unresolved.add(unresolvedBean);
        }
        if (!Unresolved.isEmpty()){
            unresolvedSetBean.setUnresolved(Unresolved);
        }
        dataPackageBean.setUnresolvedSet(unresolvedSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.DeliveryListsBean deliveryListsBean=new DataPackageBean.DeliveryListsBean();
        List<DataPackageBean.DeliveryListsBean.DeliveryListBean> DeliveryList=new ArrayList<>();

        List<DeliveryListBean> deliveryListBeanList=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < deliveryListBeanList.size(); i++) {
            DataPackageBean.DeliveryListsBean.DeliveryListBean deliveryListBean=new DataPackageBean.DeliveryListsBean.DeliveryListBean();
            deliveryListBean.setId(deliveryListBeanList.get(i).getId());
            deliveryListBean.setIsParent(deliveryListBeanList.get(i).getIsParent());
            deliveryListBean.setParentId(deliveryListBeanList.get(i).getParentId());
            deliveryListBean.setProject(deliveryListBeanList.get(i).getProject());
            deliveryListBean.setUniqueValue(deliveryListBeanList.get(i).getUniqueValue());
            xStream.alias("DeliveryList", DataPackageBean.DeliveryListsBean.DeliveryListBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.DeliveryListsBean.class, "DeliveryList");
            xStream.useAttributeFor(DataPackageBean.DeliveryListsBean.DeliveryListBean.class, "id");
            DeliveryList.add(deliveryListBean);

        }
        if (!DeliveryList.isEmpty()){
            deliveryListsBean.setDeliveryList(DeliveryList);
        }
        dataPackageBean.setDeliveryLists(deliveryListsBean);

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean.DocumentListSetBean documentListSetBean=new DataPackageBean.DocumentListSetBean();

        List<DataPackageBean.DocumentListSetBean.DocumentBean> Document=new ArrayList<>();
        List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < documentBeans.size(); i++) {
            DataPackageBean.DocumentListSetBean.DocumentBean documentBean=new DataPackageBean.DocumentListSetBean.DocumentBean();
            documentBean.setId(documentBeans.get(i).getId());
            documentBean.setApprovalDate(documentBeans.get(i).getApprovalDate());
            documentBean.setApprover(documentBeans.get(i).getApprover());
            documentBean.setCode(documentBeans.get(i).getCode());
            documentBean.setConclusion(documentBeans.get(i).getConclusion());
            documentBean.setDescription(documentBeans.get(i).getDescription());
            documentBean.setIssl(documentBeans.get(i).getIssl());
            documentBean.setModalCode(documentBeans.get(i).getModalCode());
            documentBean.setName(documentBeans.get(i).getName());
            documentBean.setPayClassify(documentBeans.get(i).getPayClassify());
            documentBean.setPayClassifyName(documentBeans.get(i).getPayClassifyName());
            documentBean.setProductCode(documentBeans.get(i).getProductCode());
            documentBean.setSecret(documentBeans.get(i).getSecret());
            documentBean.setProductCodeName(documentBeans.get(i).getProductCodeName());
            documentBean.setStage(documentBeans.get(i).getStage());
            documentBean.setTechStatus(documentBeans.get(i).getTechStatus());
            documentBean.setOnLine(documentBeans.get(i).getOnLine());
            documentBean.setInfoUrl(documentBeans.get(i).getInfoUrl());
            documentBean.setUniqueValue(documentBeans.get(i).getUniqueValue());

            DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean  fileSetBean=new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean();
            List<DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean> File=new ArrayList<>();
            List<FileBean> fileBeans=fileBeanDao.queryBuilder()
                    .where(FileBeanDao.Properties.DataPackageId.eq(id))
                    .where(FileBeanDao.Properties.DocumentId.eq(documentBeans.get(i).getId()))
                    .list();
            for (int j = 0; j <fileBeans.size() ; j++) {
                DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean fileBean=new DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean();
                fileBean.setName(fileBeans.get(j).getName());
                fileBean.setType(fileBeans.get(j).getType());
                fileBean.setPath(fileBeans.get(j).getPath());
                fileBean.setSecret(fileBeans.get(j).getSecret());
                fileBean.setDisabledSecret(fileBeans.get(j).getDisabledSecret());
                xStream.alias("File", DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.FileBean.class);//为类名节点重命名
                xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.DocumentBean.FileSetBean.class, "File");
                File.add(fileBean);
            }
            if (!File.isEmpty()){
                fileSetBean.setFile(File);
            }

            documentBean.setFileSet(fileSetBean);
            xStream.alias("Document", DataPackageBean.DocumentListSetBean.DocumentBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.class, "Document");
            xStream.useAttributeFor(DataPackageBean.DocumentListSetBean.DocumentBean.class, "id");
            Document.add(documentBean);
        }
        if (!Document.isEmpty()){
            documentListSetBean.setDocument(Document);
        }
        dataPackageBean.setDocumentListSet(documentListSetBean);


        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        String xml = xStream.toXML(dataPackageBean);
        System.out.println("sss" + xml);


        try {
            writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "\n" + xml, dataPackageDBeans.get(0).getUpLoadFile() + "/" + "DataPackage.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file=new File(dataPackageDBeans.get(0).getUpLoadFile());

        try {
//            deleteFile(new File(Environment.getExternalStorageDirectory()+"/数据包" + "/" + dataPackageDBeans.get(0).getCode()+".zip"));

            IOUtil.getZipFile(file.listFiles(),Environment.getExternalStorageDirectory()+"/数据包" + "/" + dataPackageDBeans.get(0).getCode()+".zip");
//            ZipUtils2.ZipFolder(dataPackageDBeans.get(0).getUpLoadFile() , Environment.getExternalStorageDirectory()+"/数据包" + "/" + dataPackageDBeans.get(0).getCode()+".zip");
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteFile(new File(dataPackageDBeans.get(0).getUpLoadFile()));



        List<ApplyDeptBean> del18=applyDeptBeanDao.queryBuilder()
                .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del18.size() ; i++) {
            applyDeptBeanDao.deleteByKey(del18.get(i).getUId());
        }
        List<DeliveryListBean> del17=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del17.size() ; i++) {
            deliveryListBeanDao.deleteByKey(del17.get(i).getUId());
        }
        List<UnresolvedBean> del16=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del16.size() ; i++) {
            unresolvedBeanDao.deleteByKey(del16.get(i).getUId());
        }
        List<FileBean> del15=fileBeanDao.queryBuilder()
                .where(FileBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del15.size() ; i++) {
            fileBeanDao.deleteByKey(del15.get(i).getUId());
        }
        List<CheckUnresolvedBean> del14=checkUnresolvedBeanDao.queryBuilder()
                .where(CheckUnresolvedBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del14.size() ; i++) {
            checkUnresolvedBeanDao.deleteByKey(del14.get(i).getUId());
        }
        List<CheckVerdBean> del13=checkVerdBeanDao.queryBuilder()
                .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del13.size() ; i++) {
            checkVerdBeanDao.deleteByKey(del13.get(i).getUId());
        }
        List<RelatedDocumentIdSetBean> del12=relatedDocumentIdSetBeanDao.queryBuilder()
                .where(RelatedDocumentIdSetBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del12.size() ; i++) {
            relatedDocumentIdSetBeanDao.deleteByKey(del12.get(i).getUId());
        }
        List<PropertyBeanX> del11=propertyBeanXDao.queryBuilder()
                .where(PropertyBeanXDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del11.size() ; i++) {
            propertyBeanXDao.deleteByKey(del11.get(i).getUId());
        }
        List<AcceptDeviceBean> del10=acceptDeviceBeanDao.queryBuilder()
                .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del10.size() ; i++) {
            acceptDeviceBeanDao.deleteByKey(del10.get(i).getUId());
        }
        List<CheckItemBean> del9=checkItemBeanDao.queryBuilder()
                .where(CheckItemBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del9.size() ; i++) {
            checkItemBeanDao.deleteByKey(del9.get(i).getUId());
        }
        List<PropertyBean> del8=propertyBeanDao.queryBuilder()
                .where(PropertyBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del8.size() ; i++) {
            propertyBeanDao.deleteByKey(del8.get(i).getUId());
        }
        List<CheckGroupBean> del7=checkGroupBeanDao.queryBuilder()
                .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del7.size() ; i++) {
            checkGroupBeanDao.deleteByKey(del7.get(i).getUId());
        }
        List<CheckFileBean> del6=checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del6.size() ; i++) {
            checkFileBeanDao.deleteByKey(del6.get(i).getUId());
        }
        List<ApplyItemBean> del5=applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del5.size() ; i++) {
            applyItemBeanDao.deleteByKey(del5.get(i).getUId());
        }
        List<DataPackageDBean> del1=dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(id)).list();
        for (int i = 0; i <del1.size() ; i++) {
            dataPackageDBeanDao.deleteByKey(del1.get(i).getUId());
        }
        List<DocumentBean> del2=documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del2.size() ; i++) {
            documentBeanDao.deleteByKey(del2.get(i).getUId());
        }
        List<CheckApplyBean> del3=checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del3.size() ; i++) {
            checkApplyBeanDao.deleteByKey(del3.get(i).getUId());
        }
        List<CheckTaskBean> del4=checkTaskBeanDao.queryBuilder()
                .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id)).list();
        for (int i = 0; i <del4.size() ; i++) {
            checkTaskBeanDao.deleteByKey(del4.get(i).getUId());
        }




    }

    //flie：要删除的文件夹的所在位置
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }


    public static void setmoban(String id,String name) {
        XStream xStream = new XStream();
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean dataPackageBean = new DataPackageBean();

        DataPackageDBeanDao dataPackageDBeanDao = MyApplication.getInstances().getDataPackageDaoSession().getDataPackageDBeanDao();
        DocumentBeanDao documentBeanDao=MyApplication.getInstances().getDocumentDaoSession().getDocumentBeanDao();
        CheckApplyBeanDao checkApplyBeanDao = MyApplication.getInstances().getCheckApplyDaoSession().getCheckApplyBeanDao();
        CheckTaskBeanDao checkTaskBeanDao = MyApplication.getInstances().getCheckTaskDaoSession().getCheckTaskBeanDao();
        ApplyItemBeanDao applyItemBeanDao=MyApplication.getInstances().getApplyItemDaoSession().getApplyItemBeanDao();
        CheckFileBeanDao checkFileBeanDao=MyApplication.getInstances().getCheckFileDaoSession().getCheckFileBeanDao();
        CheckGroupBeanDao checkGroupBeanDao=MyApplication.getInstances().getCheckGroupDaoSession().getCheckGroupBeanDao();
        PropertyBeanDao propertyBeanDao=MyApplication.getInstances().getPropertyDaoSession().getPropertyBeanDao();
        CheckItemBeanDao checkItemBeanDao=MyApplication.getInstances().getCheckItemDaoSession().getCheckItemBeanDao();
        AcceptDeviceBeanDao acceptDeviceBeanDao=MyApplication.getInstances().getAcceptDeviceaDaoSession().getAcceptDeviceBeanDao();
        PropertyBeanXDao propertyBeanXDao=MyApplication.getInstances().getPropertyXDaoSession().getPropertyBeanXDao();
        RelatedDocumentIdSetBeanDao relatedDocumentIdSetBeanDao=MyApplication.getInstances().getRelatedDocumentIdSetDaoSession().getRelatedDocumentIdSetBeanDao();
        CheckVerdBeanDao checkVerdBeanDao=MyApplication.getInstances().getCheckVerdDaoSession().getCheckVerdBeanDao();
        CheckUnresolvedBeanDao checkUnresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getCheckUnresolvedBeanDao();
        FileBeanDao fileBeanDao=MyApplication.getInstances().getFileDaoSession().getFileBeanDao();
        UnresolvedBeanDao unresolvedBeanDao=MyApplication.getInstances().getCheckUnresolvedDaoSession().getUnresolvedBeanDao();
        DeliveryListBeanDao deliveryListBeanDao=MyApplication.getInstances().getDeliveryListDaoSession().getDeliveryListBeanDao();
        ApplyDeptBeanDao applyDeptBeanDao = MyApplication.getInstances().getApplyDeptDaoSession().getApplyDeptBeanDao();


        List<DataPackageDBean> dataPackageDBeans = dataPackageDBeanDao.queryBuilder()
                .where(DataPackageDBeanDao.Properties.Id.eq(id)).list();
        if (dataPackageDBeans != null && !dataPackageDBeans.isEmpty()) {
            dataPackageBean.setId(dataPackageDBeans.get(0).getId());
            dataPackageBean.setBatch(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getBatch() : "");
            dataPackageBean.setCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCode() : "");
            dataPackageBean.setCreateTime(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCreateTime() : "");
            dataPackageBean.setType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getType() : "");
            dataPackageBean.setResponseUnit(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getResponseUnit() : "");
            dataPackageBean.setProductType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductType() : "");
            dataPackageBean.setProductName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductName() : "");
            dataPackageBean.setProductCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getProductCode() : "");
            dataPackageBean.setName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getName() : "");
            dataPackageBean.setModelCode(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelCode() : "");
            dataPackageBean.setModelSeries(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeries() : "");
            dataPackageBean.setModelSeriesName(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeriesName() : "");
            dataPackageBean.setCreator(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getCreator() : "");
            dataPackageBean.setLifecycleStateId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getLifecycleStateId() : "");
            dataPackageBean.setLifecycleStateIdentifier(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getLifecycleStateIdentifier() : "");
            dataPackageBean.setPkgTemplateId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getPkgTemplateId() : "");
            dataPackageBean.setBaseType(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getBaseType() : "");
            dataPackageBean.setModelSeriesId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getModelSeriesId() : "");
            dataPackageBean.setRepositoryId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getRepositoryId() : "");
            dataPackageBean.setIsTemplate(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getIsTemplate() : "");
            dataPackageBean.setOwnerId(!dataPackageDBeans.isEmpty() ? dataPackageDBeans.get(0).getOwnerId() : "");
            xStream.alias("DataPackage", DataPackageBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.class, "id");

        }

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckApplyBean checkApplyBean = new DataPackageBean.CheckApplyBean();
        List<CheckApplyBean> checkApplyBeans = checkApplyBeanDao.queryBuilder()
                .where(CheckApplyBeanDao.Properties.DataPackageId.eq(id))
                .list();
        if (checkApplyBeans != null && !checkApplyBeans.isEmpty()) {
            checkApplyBean.setApplicant(checkApplyBeans.get(0).getApplicant());
            checkApplyBean.setApplyCompany(checkApplyBeans.get(0).getApplyCompany());
            checkApplyBean.setCode(checkApplyBeans.get(0).getCode());
            checkApplyBean.setPhone(checkApplyBeans.get(0).getPhone());
            checkApplyBean.setName(checkApplyBeans.get(0).getName());
            checkApplyBean.setId(checkApplyBeans.get(0).getId());
            checkApplyBean.setDescription(checkApplyBeans.get(0).getDescription());
            checkApplyBean.setContractName(checkApplyBeans.get(0).getContractName());
            checkApplyBean.setContractCode(checkApplyBeans.get(0).getContractCode());
            checkApplyBean.setConclusion(checkApplyBeans.get(0).getConclusion());
            checkApplyBean.setDocTypeVal(checkApplyBeans.get(0).getDocTypeVal());
            xStream.alias("checkApply", DataPackageBean.CheckApplyBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckApplyBean.class, "id");

        }
        dataPackageBean.setCheckApply(checkApplyBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean.CheckTaskBean checkTaskBean = new DataPackageBean.CheckTaskBean();
        List<CheckTaskBean> checkTaskBeans = checkTaskBeanDao.queryBuilder()
                .where(CheckTaskBeanDao.Properties.DataPackageId.eq(id))
                .list();
        if (checkTaskBeans != null && !checkTaskBeans.isEmpty()) {
            checkTaskBean.setAcceptDate(checkTaskBeans.get(0).getAcceptDate());
            checkTaskBean.setPhone(checkTaskBeans.get(0).getPhone());
            checkTaskBean.setName(checkTaskBeans.get(0).getName());
            checkTaskBean.setIssuer(checkTaskBeans.get(0).getIssuer());
            checkTaskBean.setIssueDept(checkTaskBeans.get(0).getIssueDept());
            checkTaskBean.setId(checkTaskBeans.get(0).getId());
            checkTaskBean.setCode(checkTaskBeans.get(0).getCode());
            checkTaskBean.setCheckDate(checkTaskBeans.get(0).getCheckDate());
            checkTaskBean.setApplyCompany(checkTaskBeans.get(0).getApplyCompany());
            checkTaskBean.setApplicant(checkTaskBeans.get(0).getApplicant());
            checkTaskBean.setAccepter(checkTaskBeans.get(0).getAccepter());
            checkTaskBean.setDocTypeVal(checkTaskBeans.get(0).getDocTypeVal());
            xStream.alias("checkTask", DataPackageBean.CheckTaskBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckTaskBean.class, "id");

            DataPackageBean.CheckTaskBean.ApplyDeptSetBean applyDeptSetBean = new DataPackageBean.CheckTaskBean.ApplyDeptSetBean();
            List<DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean> applyDeptBeanList = new ArrayList<>();

            List<ApplyDeptBean> applyDeptBeans = applyDeptBeanDao.queryBuilder()
                    .where(ApplyDeptBeanDao.Properties.DataPackageId.eq(id))
                    .where(ApplyDeptBeanDao.Properties.CheckTaskId.eq(checkTaskBeans.get(0).getId()))
                    .list();
            for (int i = 0; i < applyDeptBeans.size(); i++) {
                DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean applyDeptBean = new DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean();
                applyDeptBean.setAcceptor(applyDeptBeans.get(i).getAcceptor());
                applyDeptBean.setDepartment(applyDeptBeans.get(i).getDepartment());
                applyDeptBean.setId(applyDeptBeans.get(i).getId());
                applyDeptBean.setOther(applyDeptBeans.get(i).getOther());
                xStream.alias("ApplyDept", DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean.class);//为类名节点重命名
                xStream.addImplicitCollection(DataPackageBean.CheckTaskBean.ApplyDeptSetBean.class, "ApplyDept");
                xStream.useAttributeFor(DataPackageBean.CheckTaskBean.ApplyDeptSetBean.ApplyDeptBean.class, "id");
                applyDeptBeanList.add(applyDeptBean);
            }
            if (!applyDeptBeanList.isEmpty()){
                applyDeptSetBean.setApplyDept(applyDeptBeanList);
            }
            checkTaskBean.setApplyDeptSet(applyDeptSetBean);
        }
        dataPackageBean.setCheckTask(checkTaskBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.ApplyItemSetBean applyItemSetBean=new DataPackageBean.ApplyItemSetBean();
        List<DataPackageBean.ApplyItemSetBean.ApplyItemBean> ApplyItem=new ArrayList<>();

        List<ApplyItemBean> applyItemBeans=applyItemBeanDao.queryBuilder()
                .where(ApplyItemBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < applyItemBeans.size(); i++) {
            DataPackageBean.ApplyItemSetBean.ApplyItemBean applyItemBean=new DataPackageBean.ApplyItemSetBean.ApplyItemBean();

            applyItemBean.setProductStatus(applyItemBeans.get(i).getProductStatus());
            applyItemBean.setProductName(applyItemBeans.get(i).getProductName());
            applyItemBean.setProductCodeName(applyItemBeans.get(i).getProductCodeName());
            applyItemBean.setPassCheck(applyItemBeans.get(i).getPassCheck());
            applyItemBean.setUniqueValue(applyItemBeans.get(i).getUniqueValue());
            applyItemBean.setIsSatisfyRequire(applyItemBeans.get(i).getIsSatisfyRequire());
            applyItemBean.setIsPureCheck(applyItemBeans.get(i).getIsPureCheck());
            applyItemBean.setIsCompleteRoutine(applyItemBeans.get(i).getIsCompleteRoutine());
            applyItemBean.setIsCompleteChoice(applyItemBeans.get(i).getIsCompleteChoice());
            applyItemBean.setIsArmyCheck(applyItemBeans.get(i).getIsArmyCheck());
            applyItemBean.setDescription(applyItemBeans.get(i).getDescription());
            applyItemBean.setCheckCount(applyItemBeans.get(i).getCheckCount());
            applyItemBean.setProductCode(applyItemBeans.get(i).getProductCode());
            applyItemBean.setId(applyItemBeans.get(i).getId());

            xStream.alias("ApplyItem", DataPackageBean.ApplyItemSetBean.ApplyItemBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.ApplyItemSetBean.class, "ApplyItem");
            xStream.useAttributeFor(DataPackageBean.ApplyItemSetBean.ApplyItemBean.class, "id");
            ApplyItem.add(applyItemBean);
        }
        if (!ApplyItem.isEmpty()){
            applyItemSetBean.setApplyItem(ApplyItem);
        }
        dataPackageBean.setApplyItemSet(applyItemSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckFileSetBean checkFileSetBean=new DataPackageBean.CheckFileSetBean();
        List<DataPackageBean.CheckFileSetBean.CheckFileBean> CheckFile=new ArrayList<>();

        List<CheckFileBean> checkFileBeans=checkFileBeanDao.queryBuilder()
                .where(CheckFileBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < checkFileBeans.size(); i++) {
            DataPackageBean.CheckFileSetBean.CheckFileBean checkFileBean=new DataPackageBean.CheckFileSetBean.CheckFileBean();
            checkFileBean.setId(checkFileBeans.get(i).getId());
            checkFileBean.setCheckPerson(checkFileBeans.get(i).getCheckPerson());
            checkFileBean.setCode(checkFileBeans.get(i).getCode());
            checkFileBean.setConclusion(checkFileBeans.get(i).getConclusion());
            checkFileBean.setDocType(checkFileBeans.get(i).getDocType());
            checkFileBean.setName(checkFileBeans.get(i).getName());
            checkFileBean.setProductType(checkFileBeans.get(i).getProductType());
            checkFileBean.setCheckDate(checkFileBeans.get(i).getCheckDate());
            checkFileBean.setSortBy(checkFileBeans.get(i).getSortBy());

            DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean checkGroupSetBean=new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean();
            List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean> CheckGroup=new ArrayList<>();

            List<CheckGroupBean> checkGroupBeans=checkGroupBeanDao.queryBuilder()
                    .where(CheckGroupBeanDao.Properties.DataPackageId.eq(id))
                    .where(CheckGroupBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                    .list();
            for (int j = 0; j < checkGroupBeans.size(); j++) {
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean checkGroupBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean();
                checkGroupBean.setCheckGroupConclusion(checkGroupBeans.get(j).getCheckGroupConclusion());
                checkGroupBean.setCheckPerson(checkGroupBeans.get(j).getCheckPerson());
                checkGroupBean.setGroupName(checkGroupBeans.get(j).getGroupName());
                checkGroupBean.setId(checkGroupBeans.get(j).getId());
                checkGroupBean.setIsConclusion(checkGroupBeans.get(j).getIsConclusion());
                checkGroupBean.setIsTable(checkGroupBeans.get(j).getIsTable());
                checkGroupBean.setUniqueValue(checkGroupBeans.get(j).getUniqueValue());
                //——————————————————————————————————————————
                List<PropertyBean> propertyBeans=propertyBeanDao.queryBuilder()
                        .where(PropertyBeanDao.Properties.DataPackageId.eq(id))
                        .where(PropertyBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(PropertyBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean propertySetBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean> propertySetBeans=new ArrayList<>();
                for (int k = 0; k <propertyBeans.size() ; k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean propertyBean=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean();
                    propertyBean.setName(propertyBeans.get(k).getName());
                    propertyBean.setValue(propertyBeans.get(k).getValue());
                    propertySetBeans.add(propertyBean);
                    xStream.alias("Property", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.PropertyBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.PropertySetBean.class, "Property");
                }
                if (!propertySetBeans.isEmpty()){
                    propertySetBean.setProperty(propertySetBeans);
                }
                checkGroupBean.setPropertySet(propertySetBean);


                //——————————————————————————————————————————

                List<CheckItemBean> checkItemBeans=checkItemBeanDao.queryBuilder()
                        .where(CheckItemBeanDao.Properties.DataPackageId.eq(id))
                        .where(CheckItemBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(CheckItemBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean checkItemSetBean=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean> CheckItem=new ArrayList<>();
                for (int k = 0; k < checkItemBeans.size(); k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean checkItemBean=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean();
                    checkItemBean.setId(checkItemBeans.get(k).getId());
                    checkItemBean.setName(checkItemBeans.get(k).getName());
                    checkItemBean.setOptions(checkItemBeans.get(k).getOptions());
                    checkItemBean.setSelected(checkItemBeans.get(k).getSelected());
                    checkItemBean.setUniqueValue(checkItemBeans.get(k).getUniqueValue());

                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX propertySetBeanX=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX();
                    List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX> Property=new ArrayList<>();

                    List<PropertyBeanX> propertyBeanXList=propertyBeanXDao.queryBuilder()
                            .where(PropertyBeanXDao.Properties.DataPackageId.eq(id))
                            .where(PropertyBeanXDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                            .where(PropertyBeanXDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                            .where(PropertyBeanXDao.Properties.CheckItemId.eq(checkItemBeans.get(k).getId()))
                            .list();

                    for (int l = 0; l < propertyBeanXList.size(); l++) {
                        DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX propertyBeanX=
                                new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX();
                        propertyBeanX.setName(propertyBeanXList.get(l).getName());
                        xStream.alias("Property", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.PropertyBeanX.class);//为类名节点重命名
                        xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.PropertySetBeanX.class, "Property");
                        Property.add(propertyBeanX);
                    }
                    if (!Property.isEmpty()){
                        propertySetBeanX.setProperty(Property);
                    }
                    checkItemBean.setPropertySet(propertySetBeanX);


                    xStream.alias("CheckItem", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.class);//为类名节点重命名
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.class, "CheckItem");
                    xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.CheckItemSetBean.CheckItemBean.class, "id");
                    CheckItem.add(checkItemBean);
                }
                if (!CheckItem.isEmpty()){
                    checkItemSetBean.setCheckItem(CheckItem);
                }
                checkGroupBean.setCheckItemSet(checkItemSetBean);
                //——————————————————————————————————————————

                DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet acceptDeviceSet=
                        new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet();
                List<DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice> AcceptDevice=new ArrayList<>();

                List<AcceptDeviceBean> acceptDeviceBeanList=acceptDeviceBeanDao.queryBuilder()
                        .where(AcceptDeviceBeanDao.Properties.DataPackageId.eq(id))
                        .where(AcceptDeviceBeanDao.Properties.CheckFileId.eq(checkFileBeans.get(i).getId()))
                        .where(AcceptDeviceBeanDao.Properties.CheckGroupId.eq(checkGroupBeans.get(j).getId()))
                        .list();
                for (int k = 0; k <acceptDeviceBeanList.size() ; k++) {
                    DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice acceptDevice=
                            new DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice();
                    acceptDevice.setId(acceptDeviceBeanList.get(k).getId());
                    acceptDevice.setAccuracy(acceptDeviceBeanList.get(k).getAccuracy());
                    acceptDevice.setCertificate(acceptDeviceBeanList.get(k).getCertificate());
                    acceptDevice.setDescription(acceptDeviceBeanList.get(k).getDescription());
                    acceptDevice.setName(acceptDeviceBeanList.get(k).getName());
                    acceptDevice.setSpecification(acceptDeviceBeanList.get(k).getSpecification());

                    xStream.alias("AcceptDevice", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice.class);//为类名节点重命名
                    xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.AcceptDevice.class, "id");
                    xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.AcceptDeviceSet.class, "AcceptDevice");
                    AcceptDevice.add(acceptDevice);
                }
                if (!AcceptDevice.isEmpty()){
                    acceptDeviceSet.setAcceptDevice(AcceptDevice);
                }
                checkGroupBean.setAcceptDeviceSet(acceptDeviceSet);
                xStream.alias("CheckGroup", DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.class);//为类名节点重命名
                xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.class, "CheckGroup");
                xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.CheckGroupSetBean.CheckGroupBean.class, "id");
                CheckGroup.add(checkGroupBean);
            }
            if (!CheckGroup.isEmpty()){
                checkGroupSetBean.setCheckGroup(CheckGroup);
            }
            checkFileBean.setCheckGroupSet(checkGroupSetBean);
            xStream.alias("CheckFile", DataPackageBean.CheckFileSetBean.CheckFileBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.CheckFileSetBean.class, "CheckFile");
            xStream.useAttributeFor(DataPackageBean.CheckFileSetBean.CheckFileBean.class, "id");
            CheckFile.add(checkFileBean);
        }
        if (!CheckFile.isEmpty()){
            checkFileSetBean.setCheckFile(CheckFile);
        }
        dataPackageBean.setCheckFileSet(checkFileSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckVerdBean checkVerdBean=new DataPackageBean.CheckVerdBean();
        List<CheckVerdBean> checkVerdBeans=checkVerdBeanDao.queryBuilder()
                .where(CheckVerdBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < checkVerdBeans.size(); i++) {
            checkVerdBean.setCheckPerson(checkVerdBeans.get(i).getCheckPerson());
            checkVerdBean.setCode(checkVerdBeans.get(i).getCode());
            checkVerdBean.setConclusion(checkVerdBeans.get(i).getConclusion());
            checkVerdBean.setgConclusion(checkVerdBeans.get(i).getGConclusion());
            checkVerdBean.setId(checkVerdBeans.get(i).getId());
            checkVerdBean.setjConclusion(checkVerdBeans.get(i).getJConclusion());
            checkVerdBean.setName(checkVerdBeans.get(i).getName());
            checkVerdBean.setDocTypeVal(checkVerdBeans.get(i).getDocTypeVal());
            checkVerdBean.setqConclusion(checkVerdBeans.get(i).getQConclusion());
            checkVerdBean.setCheckPersonId(checkVerdBeans.get(i).getCheckPersonId());
            checkVerdBean.setCheckDate(checkVerdBeans.get(i).getCheckDate());
            xStream.alias("checkVerd", DataPackageBean.CheckVerdBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckVerdBean.class, "id");
        }

        dataPackageBean.setCheckVerd(checkVerdBean);

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.CheckUnresolvedBean checkUnresolvedBean=new DataPackageBean.CheckUnresolvedBean();

        List<CheckUnresolvedBean> checkUnresolvedBeans=checkUnresolvedBeanDao.queryBuilder()
                .where(CheckUnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i <checkUnresolvedBeans.size() ; i++) {
            checkUnresolvedBean.setCode(checkUnresolvedBeans.get(i).getCode());
            checkUnresolvedBean.setId(checkUnresolvedBeans.get(i).getId());
            checkUnresolvedBean.setName(checkUnresolvedBeans.get(i).getName());
            checkUnresolvedBean.setDocTypeVal(checkUnresolvedBeans.get(i).getDocTypeVal());
            xStream.alias("checkUnresolved", DataPackageBean.CheckUnresolvedBean.class);//为类名节点重命名
            xStream.useAttributeFor(DataPackageBean.CheckUnresolvedBean.class, "id");
        }

        dataPackageBean.setCheckUnresolved(checkUnresolvedBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.UnresolvedSetBean unresolvedSetBean=new DataPackageBean.UnresolvedSetBean();
        List<DataPackageBean.UnresolvedSetBean.UnresolvedBean> Unresolved=new ArrayList<>();
        List<UnresolvedBean> unresolvedBeans=unresolvedBeanDao.queryBuilder()
                .where(UnresolvedBeanDao.Properties.DataPackageId.eq(id))
                .list();

        for (int i = 0; i < unresolvedBeans.size(); i++) {
            DataPackageBean.UnresolvedSetBean.UnresolvedBean unresolvedBean=new DataPackageBean.UnresolvedSetBean.UnresolvedBean();
            unresolvedBean.setConfirmer(unresolvedBeans.get(i).getConfirmer());
            unresolvedBean.setConfirmTime(unresolvedBeans.get(i).getConfirmTime());
            unresolvedBean.setFileId(unresolvedBeans.get(i).getFileId());
            unresolvedBean.setId(unresolvedBeans.get(i).getId());
            unresolvedBean.setProductCode(unresolvedBeans.get(i).getProductCode());
            unresolvedBean.setQuestion(unresolvedBeans.get(i).getQuestion());
            unresolvedBean.setUniqueValue(unresolvedBeans.get(i).getUniqueValue());
            xStream.alias("Unresolved", DataPackageBean.UnresolvedSetBean.UnresolvedBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.UnresolvedSetBean.class, "Unresolved");
            xStream.useAttributeFor(DataPackageBean.UnresolvedSetBean.UnresolvedBean.class, "id");
            Unresolved.add(unresolvedBean);
        }
        if (!Unresolved.isEmpty()){
            unresolvedSetBean.setUnresolved(Unresolved);
        }
        dataPackageBean.setUnresolvedSet(unresolvedSetBean);
        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */

        DataPackageBean.DeliveryListsBean deliveryListsBean=new DataPackageBean.DeliveryListsBean();
        List<DataPackageBean.DeliveryListsBean.DeliveryListBean> DeliveryList=new ArrayList<>();

        List<DeliveryListBean> deliveryListBeanList=deliveryListBeanDao.queryBuilder()
                .where(DeliveryListBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < deliveryListBeanList.size(); i++) {
            DataPackageBean.DeliveryListsBean.DeliveryListBean deliveryListBean=new DataPackageBean.DeliveryListsBean.DeliveryListBean();
            deliveryListBean.setId(deliveryListBeanList.get(i).getId());
            deliveryListBean.setIsParent(deliveryListBeanList.get(i).getIsParent());
            deliveryListBean.setParentId(deliveryListBeanList.get(i).getParentId());
            deliveryListBean.setProject(deliveryListBeanList.get(i).getProject());
            deliveryListBean.setUniqueValue(deliveryListBeanList.get(i).getUniqueValue());
            xStream.alias("DeliveryList", DataPackageBean.DeliveryListsBean.DeliveryListBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.DeliveryListsBean.class, "DeliveryList");
            xStream.useAttributeFor(DataPackageBean.DeliveryListsBean.DeliveryListBean.class, "id");
            DeliveryList.add(deliveryListBean);

        }
        if (!DeliveryList.isEmpty()){
            deliveryListsBean.setDeliveryList(DeliveryList);
        }
        dataPackageBean.setDeliveryLists(deliveryListsBean);

        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        DataPackageBean.DocumentListSetBean documentListSetBean=new DataPackageBean.DocumentListSetBean();

        List<DataPackageBean.DocumentListSetBean.DocumentBean> Document=new ArrayList<>();
        List<DocumentBean> documentBeans=documentBeanDao.queryBuilder()
                .where(DocumentBeanDao.Properties.DataPackageId.eq(id))
                .list();
        for (int i = 0; i < documentBeans.size(); i++) {
            DataPackageBean.DocumentListSetBean.DocumentBean documentBean=new DataPackageBean.DocumentListSetBean.DocumentBean();
            documentBean.setId(documentBeans.get(i).getId());
            documentBean.setApprovalDate(documentBeans.get(i).getApprovalDate());
            documentBean.setApprover(documentBeans.get(i).getApprover());
            documentBean.setCode(documentBeans.get(i).getCode());
            documentBean.setConclusion(documentBeans.get(i).getConclusion());
            documentBean.setDescription(documentBeans.get(i).getDescription());
            documentBean.setIssl(documentBeans.get(i).getIssl());
            documentBean.setModalCode(documentBeans.get(i).getModalCode());
            documentBean.setName(documentBeans.get(i).getName());
            documentBean.setPayClassify(documentBeans.get(i).getPayClassify());
            documentBean.setPayClassifyName(documentBeans.get(i).getPayClassifyName());
            documentBean.setProductCode(documentBeans.get(i).getProductCode());
            documentBean.setSecret(documentBeans.get(i).getSecret());
            documentBean.setProductCodeName(documentBeans.get(i).getProductCodeName());
            documentBean.setStage(documentBeans.get(i).getStage());
            documentBean.setTechStatus(documentBeans.get(i).getTechStatus());
            documentBean.setOnLine(documentBeans.get(i).getOnLine());
            documentBean.setInfoUrl(documentBeans.get(i).getInfoUrl());
            documentBean.setUniqueValue(documentBeans.get(i).getUniqueValue());

            xStream.alias("Document", DataPackageBean.DocumentListSetBean.DocumentBean.class);//为类名节点重命名
            xStream.addImplicitCollection(DataPackageBean.DocumentListSetBean.class, "Document");
            xStream.useAttributeFor(DataPackageBean.DocumentListSetBean.DocumentBean.class, "id");
            Document.add(documentBean);
        }
        if (!Document.isEmpty()){
            documentListSetBean.setDocument(Document);
        }
        dataPackageBean.setDocumentListSet(documentListSetBean);



        /**
         * 	———————————————————————————————————————————————————————————————————————————————————————
         * */
        String xml = xStream.toXML(dataPackageBean);
        System.out.println("sss" + xml);






        try {
            writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "\n" + xml, dataPackageDBeans.get(0).getUpLoadFile() + "/" + "DataPackage.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file=new File(dataPackageDBeans.get(0).getUpLoadFile()+"/" + "DataPackage.xml");
        try {
            IOUtil.getFile(file.getPath(),Environment.getExternalStorageDirectory()+"/模板" + "/" + name+".zip");
//            ZipUtils2.ZipFolder(dataPackageDBeans.get(0).getUpLoadFile() , Environment.getExternalStorageDirectory()+"/数据包" + "/" + dataPackageDBeans.get(0).getCode()+".zip");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * 写string到file文件中
     */
    public static void writeToFile(String content, String fPath) throws IOException {
        File txt = new File(fPath);
        if (!txt.exists()) {
            txt.mkdirs();
        }

        FileOutputStream fos = new FileOutputStream(txt);
        fos.write(content.getBytes());
        fos.flush();
        fos.close();
    }

}
