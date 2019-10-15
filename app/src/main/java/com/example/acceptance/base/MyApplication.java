package com.example.acceptance.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.acceptance.greendao.db.DaoMaster;
import com.example.acceptance.greendao.db.DaoSession;


public class MyApplication extends Application {


    public static BaseActivity mContext;
    //静态单例
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
    }

    public static MyApplication getInstances() {
        return instances;
    }

    private DaoMaster.DevOpenHelper applyItemHelper;
    private SQLiteDatabase applyItemDb;
    private DaoMaster applyItemDaoMaster;
    private DaoSession applyItemDaoSession;

    private DaoMaster.DevOpenHelper checkApplyHelper;
    private SQLiteDatabase checkApplyDb;
    private DaoMaster checkApplyDaoMaster;
    private DaoSession checkApplyDaoSession;

    private DaoMaster.DevOpenHelper checkFileHelper;
    private SQLiteDatabase checkFileDb;
    private DaoMaster checkFileDaoMaster;
    private DaoSession checkFileDaoSession;

    private DaoMaster.DevOpenHelper checkGroupHelper;
    private SQLiteDatabase checkGroupDb;
    private DaoMaster checkGroupMaster;
    private DaoSession checkGroupDaoSession;

    private DaoMaster.DevOpenHelper checkItemHelper;
    private SQLiteDatabase checkItemDb;
    private DaoMaster checkItemMaster;
    private DaoSession checkItemDaoSession;

    private DaoMaster.DevOpenHelper checkTaskHelper;
    private SQLiteDatabase checkTaskDb;
    private DaoMaster checkTaskMaster;
    private DaoSession checkTaskDaoSession;

    private DaoMaster.DevOpenHelper checkUnresolvedHelper;
    private SQLiteDatabase checkUnresolvedDb;
    private DaoMaster checkUnresolvedMaster;
    private DaoSession checkUnresolvedDaoSession;

    private DaoMaster.DevOpenHelper checkVerdHelper;
    private SQLiteDatabase checkVerdDb;
    private DaoMaster checkVerdMaster;
    private DaoSession checkVerdDaoSession;

    private DaoMaster.DevOpenHelper dataPackageHelper;
    private SQLiteDatabase dataPackageDb;
    private DaoMaster dataPackageMaster;
    private DaoSession dataPackageDaoSession;

    private DaoMaster.DevOpenHelper deliveryListHelper;
    private SQLiteDatabase deliveryListDb;
    private DaoMaster deliveryListMaster;
    private DaoSession deliveryListDaoSession;

    private DaoMaster.DevOpenHelper documentHelper;
    private SQLiteDatabase documentDb;
    private DaoMaster documentMaster;
    private DaoSession documentDaoSession;

    private DaoMaster.DevOpenHelper fileHelper;
    private SQLiteDatabase fileDb;
    private DaoMaster fileMaster;
    private DaoSession fileDaoSession;

    private DaoMaster.DevOpenHelper propertyHelper;
    private SQLiteDatabase propertyDb;
    private DaoMaster propertyMaster;
    private DaoSession propertyDaoSession;

    private DaoMaster.DevOpenHelper propertyXHelper;
    private SQLiteDatabase propertyXDb;
    private DaoMaster propertyXMaster;
    private DaoSession propertyXDaoSession;

    private DaoMaster.DevOpenHelper relatedDocumentIdSetHelper;
    private SQLiteDatabase relatedDocumentIdSetDb;
    private DaoMaster relatedDocumentIdSetMaster;
    private DaoSession relatedDocumentIdSetDaoSession;

    private DaoMaster.DevOpenHelper unresolvedHelper;
    private SQLiteDatabase unresolvedDb;
    private DaoMaster unresolvedMaster;
    private DaoSession unresolvedDaoSession;

    private DaoMaster.DevOpenHelper applyDeptHelper;
    private SQLiteDatabase applyDeptDb;
    private DaoMaster applyDeptMaster;
    private DaoSession applyDeptDaoSession;

    private void setDatabase() {
        applyDeptHelper = new DaoMaster.DevOpenHelper(this, "applyDept.db");
        applyDeptDb = applyDeptHelper.getWritableDatabase();
        applyDeptMaster = new DaoMaster(applyDeptDb);
        applyDeptDaoSession = applyDeptMaster.newSession();

        relatedDocumentIdSetHelper = new DaoMaster.DevOpenHelper(this, "relatedDocumentIdSet.db");
        relatedDocumentIdSetDb = relatedDocumentIdSetHelper.getWritableDatabase();
        relatedDocumentIdSetMaster = new DaoMaster(relatedDocumentIdSetDb);
        relatedDocumentIdSetDaoSession = relatedDocumentIdSetMaster.newSession();

        unresolvedHelper = new DaoMaster.DevOpenHelper(this, "unresolved.db");
        unresolvedDb = unresolvedHelper.getWritableDatabase();
        unresolvedMaster = new DaoMaster(unresolvedDb);
        unresolvedDaoSession = unresolvedMaster.newSession();

        propertyXHelper = new DaoMaster.DevOpenHelper(this, "propertyX.db");
        propertyXDb = propertyXHelper.getWritableDatabase();
        propertyXMaster = new DaoMaster(propertyXDb);
        propertyXDaoSession = propertyXMaster.newSession();

        propertyHelper = new DaoMaster.DevOpenHelper(this, "property.db");
        propertyDb = propertyHelper.getWritableDatabase();
        propertyMaster = new DaoMaster(propertyDb);
        propertyDaoSession = propertyMaster.newSession();

        fileHelper = new DaoMaster.DevOpenHelper(this, "file.db");
        fileDb = fileHelper.getWritableDatabase();
        fileMaster = new DaoMaster(fileDb);
        fileDaoSession = fileMaster.newSession();

        documentHelper = new DaoMaster.DevOpenHelper(this, "document.db");
        documentDb = documentHelper.getWritableDatabase();
        documentMaster = new DaoMaster(documentDb);
        documentDaoSession = documentMaster.newSession();

        deliveryListHelper = new DaoMaster.DevOpenHelper(this, "deliveryList.db");
        deliveryListDb = deliveryListHelper.getWritableDatabase();
        deliveryListMaster = new DaoMaster(deliveryListDb);
        deliveryListDaoSession = deliveryListMaster.newSession();

        dataPackageHelper = new DaoMaster.DevOpenHelper(this, "dataPackage.db");
        dataPackageDb = dataPackageHelper.getWritableDatabase();
        dataPackageMaster = new DaoMaster(dataPackageDb);
        dataPackageDaoSession = dataPackageMaster.newSession();

        checkVerdHelper = new DaoMaster.DevOpenHelper(this, "checkVerd.db");
        checkVerdDb = checkVerdHelper.getWritableDatabase();
        checkVerdMaster = new DaoMaster(checkVerdDb);
        checkVerdDaoSession = checkVerdMaster.newSession();

        checkUnresolvedHelper = new DaoMaster.DevOpenHelper(this, "checkUnresolved.db");
        checkUnresolvedDb = checkUnresolvedHelper.getWritableDatabase();
        checkUnresolvedMaster = new DaoMaster(checkUnresolvedDb);
        checkUnresolvedDaoSession = checkUnresolvedMaster.newSession();

        checkTaskHelper = new DaoMaster.DevOpenHelper(this, "checkTask.db");
        checkTaskDb = checkTaskHelper.getWritableDatabase();
        checkTaskMaster = new DaoMaster(checkTaskDb);
        checkTaskDaoSession = checkTaskMaster.newSession();

        checkItemHelper = new DaoMaster.DevOpenHelper(this, "checkItem.db");
        checkItemDb = checkItemHelper.getWritableDatabase();
        checkItemMaster = new DaoMaster(checkItemDb);
        checkItemDaoSession = checkItemMaster.newSession();

        checkGroupHelper = new DaoMaster.DevOpenHelper(this, "checkGroup.db");
        checkGroupDb = checkGroupHelper.getWritableDatabase();
        checkGroupMaster = new DaoMaster(checkGroupDb);
        checkGroupDaoSession = checkGroupMaster.newSession();

        checkFileHelper = new DaoMaster.DevOpenHelper(this, "checkFile.db");
        checkFileDb = checkFileHelper.getWritableDatabase();
        checkFileDaoMaster = new DaoMaster(checkFileDb);
        checkFileDaoSession = checkFileDaoMaster.newSession();

        applyItemHelper = new DaoMaster.DevOpenHelper(this, "applyItem.db");
        applyItemDb = applyItemHelper.getWritableDatabase();
        applyItemDaoMaster = new DaoMaster(applyItemDb);
        applyItemDaoSession = applyItemDaoMaster.newSession();

        checkApplyHelper = new DaoMaster.DevOpenHelper(this, "checkApply.db");
        checkApplyDb = checkApplyHelper.getWritableDatabase();
        checkApplyDaoMaster = new DaoMaster(checkApplyDb);
        checkApplyDaoSession = checkApplyDaoMaster.newSession();

    }

    public DaoSession getApplyDeptDaoSession() {
        return applyDeptDaoSession;
    }

    public DaoSession getApplyItemDaoSession() {
        return applyItemDaoSession;
    }

    public DaoSession getCheckApplyDaoSession() {
        return checkApplyDaoSession;
    }

    public DaoSession getCheckFileDaoSession() {
        return checkFileDaoSession;
    }

    public DaoSession getCheckGroupDaoSession() {
        return checkGroupDaoSession;
    }

    public DaoSession getCheckItemDaoSession() {
        return checkItemDaoSession;
    }

    public DaoSession getCheckTaskDaoSession() {
        return checkTaskDaoSession;
    }

    public DaoSession getCheckUnresolvedDaoSession() {
        return checkUnresolvedDaoSession;
    }

    public DaoSession getCheckVerdDaoSession() {
        return checkVerdDaoSession;
    }

    public DaoSession getDataPackageDaoSession() {
        return dataPackageDaoSession;
    }

    public DaoSession getDeliveryListDaoSession() {
        return deliveryListDaoSession;
    }

    public DaoSession getDocumentDaoSession() {
        return documentDaoSession;
    }

    public DaoSession getFileDaoSession() {
        return fileDaoSession;
    }

    public DaoSession getPropertyDaoSession() {
        return propertyDaoSession;
    }

    public DaoSession getPropertyXDaoSession() {
        return propertyXDaoSession;
    }

    public DaoSession getRelatedDocumentIdSetDaoSession() {
        return relatedDocumentIdSetDaoSession;
    }

    public DaoSession getUnresolvedDaoSession() {
        return unresolvedDaoSession;
    }
}
