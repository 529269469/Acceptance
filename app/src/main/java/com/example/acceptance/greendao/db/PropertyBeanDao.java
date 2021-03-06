package com.example.acceptance.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.acceptance.greendao.bean.PropertyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROPERTY_BEAN".
*/
public class PropertyBeanDao extends AbstractDao<PropertyBean, Long> {

    public static final String TABLENAME = "PROPERTY_BEAN";

    /**
     * Properties of entity PropertyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UId = new Property(0, Long.class, "uId", true, "_id");
        public final static Property DataPackageId = new Property(1, String.class, "dataPackageId", false, "DATA_PACKAGE_ID");
        public final static Property CheckFileId = new Property(2, String.class, "checkFileId", false, "CHECK_FILE_ID");
        public final static Property CheckGroupId = new Property(3, String.class, "checkGroupId", false, "CHECK_GROUP_ID");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Value = new Property(5, String.class, "value", false, "VALUE");
    }


    public PropertyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PropertyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROPERTY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: uId
                "\"DATA_PACKAGE_ID\" TEXT," + // 1: dataPackageId
                "\"CHECK_FILE_ID\" TEXT," + // 2: checkFileId
                "\"CHECK_GROUP_ID\" TEXT," + // 3: checkGroupId
                "\"NAME\" TEXT," + // 4: name
                "\"VALUE\" TEXT);"); // 5: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROPERTY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PropertyBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String checkFileId = entity.getCheckFileId();
        if (checkFileId != null) {
            stmt.bindString(3, checkFileId);
        }
 
        String checkGroupId = entity.getCheckGroupId();
        if (checkGroupId != null) {
            stmt.bindString(4, checkGroupId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(6, value);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PropertyBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String checkFileId = entity.getCheckFileId();
        if (checkFileId != null) {
            stmt.bindString(3, checkFileId);
        }
 
        String checkGroupId = entity.getCheckGroupId();
        if (checkGroupId != null) {
            stmt.bindString(4, checkGroupId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(6, value);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PropertyBean readEntity(Cursor cursor, int offset) {
        PropertyBean entity = new PropertyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dataPackageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // checkFileId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // checkGroupId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // value
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PropertyBean entity, int offset) {
        entity.setUId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDataPackageId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCheckFileId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCheckGroupId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setValue(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PropertyBean entity, long rowId) {
        entity.setUId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PropertyBean entity) {
        if(entity != null) {
            return entity.getUId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PropertyBean entity) {
        return entity.getUId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
