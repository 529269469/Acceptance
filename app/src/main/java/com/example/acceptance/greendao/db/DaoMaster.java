package com.example.acceptance.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        AcceptDeviceBeanDao.createTable(db, ifNotExists);
        ApplyDeptBeanDao.createTable(db, ifNotExists);
        ApplyItemBeanDao.createTable(db, ifNotExists);
        CheckApplyBeanDao.createTable(db, ifNotExists);
        CheckFileBeanDao.createTable(db, ifNotExists);
        CheckGroupBeanDao.createTable(db, ifNotExists);
        CheckItemBeanDao.createTable(db, ifNotExists);
        CheckTaskBeanDao.createTable(db, ifNotExists);
        CheckUnresolvedBeanDao.createTable(db, ifNotExists);
        CheckVerdBeanDao.createTable(db, ifNotExists);
        DataPackageDBeanDao.createTable(db, ifNotExists);
        DeliveryListBeanDao.createTable(db, ifNotExists);
        DocumentBeanDao.createTable(db, ifNotExists);
        FileBeanDao.createTable(db, ifNotExists);
        PropertyBeanDao.createTable(db, ifNotExists);
        PropertyBeanXDao.createTable(db, ifNotExists);
        RelatedDocumentIdSetBeanDao.createTable(db, ifNotExists);
        UnresolvedBeanDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        AcceptDeviceBeanDao.dropTable(db, ifExists);
        ApplyDeptBeanDao.dropTable(db, ifExists);
        ApplyItemBeanDao.dropTable(db, ifExists);
        CheckApplyBeanDao.dropTable(db, ifExists);
        CheckFileBeanDao.dropTable(db, ifExists);
        CheckGroupBeanDao.dropTable(db, ifExists);
        CheckItemBeanDao.dropTable(db, ifExists);
        CheckTaskBeanDao.dropTable(db, ifExists);
        CheckUnresolvedBeanDao.dropTable(db, ifExists);
        CheckVerdBeanDao.dropTable(db, ifExists);
        DataPackageDBeanDao.dropTable(db, ifExists);
        DeliveryListBeanDao.dropTable(db, ifExists);
        DocumentBeanDao.dropTable(db, ifExists);
        FileBeanDao.dropTable(db, ifExists);
        PropertyBeanDao.dropTable(db, ifExists);
        PropertyBeanXDao.dropTable(db, ifExists);
        RelatedDocumentIdSetBeanDao.dropTable(db, ifExists);
        UnresolvedBeanDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AcceptDeviceBeanDao.class);
        registerDaoClass(ApplyDeptBeanDao.class);
        registerDaoClass(ApplyItemBeanDao.class);
        registerDaoClass(CheckApplyBeanDao.class);
        registerDaoClass(CheckFileBeanDao.class);
        registerDaoClass(CheckGroupBeanDao.class);
        registerDaoClass(CheckItemBeanDao.class);
        registerDaoClass(CheckTaskBeanDao.class);
        registerDaoClass(CheckUnresolvedBeanDao.class);
        registerDaoClass(CheckVerdBeanDao.class);
        registerDaoClass(DataPackageDBeanDao.class);
        registerDaoClass(DeliveryListBeanDao.class);
        registerDaoClass(DocumentBeanDao.class);
        registerDaoClass(FileBeanDao.class);
        registerDaoClass(PropertyBeanDao.class);
        registerDaoClass(PropertyBeanXDao.class);
        registerDaoClass(RelatedDocumentIdSetBeanDao.class);
        registerDaoClass(UnresolvedBeanDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
