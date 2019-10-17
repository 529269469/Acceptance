package com.example.acceptance.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.acceptance.greendao.bean.ApplyDeptBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APPLY_DEPT_BEAN".
*/
public class ApplyDeptBeanDao extends AbstractDao<ApplyDeptBean, Long> {

    public static final String TABLENAME = "APPLY_DEPT_BEAN";

    /**
     * Properties of entity ApplyDeptBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UId = new Property(0, Long.class, "uId", true, "_id");
        public final static Property DataPackageId = new Property(1, String.class, "dataPackageId", false, "DATA_PACKAGE_ID");
        public final static Property CheckTaskId = new Property(2, String.class, "checkTaskId", false, "CHECK_TASK_ID");
        public final static Property Id = new Property(3, String.class, "id", false, "ID");
        public final static Property Department = new Property(4, String.class, "department", false, "DEPARTMENT");
        public final static Property Acceptor = new Property(5, String.class, "acceptor", false, "ACCEPTOR");
        public final static Property Other = new Property(6, String.class, "other", false, "OTHER");
    }


    public ApplyDeptBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ApplyDeptBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPLY_DEPT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: uId
                "\"DATA_PACKAGE_ID\" TEXT," + // 1: dataPackageId
                "\"CHECK_TASK_ID\" TEXT," + // 2: checkTaskId
                "\"ID\" TEXT," + // 3: id
                "\"DEPARTMENT\" TEXT," + // 4: department
                "\"ACCEPTOR\" TEXT," + // 5: acceptor
                "\"OTHER\" TEXT);"); // 6: other
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APPLY_DEPT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ApplyDeptBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String checkTaskId = entity.getCheckTaskId();
        if (checkTaskId != null) {
            stmt.bindString(3, checkTaskId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(4, id);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(5, department);
        }
 
        String acceptor = entity.getAcceptor();
        if (acceptor != null) {
            stmt.bindString(6, acceptor);
        }
 
        String other = entity.getOther();
        if (other != null) {
            stmt.bindString(7, other);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ApplyDeptBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String checkTaskId = entity.getCheckTaskId();
        if (checkTaskId != null) {
            stmt.bindString(3, checkTaskId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(4, id);
        }
 
        String department = entity.getDepartment();
        if (department != null) {
            stmt.bindString(5, department);
        }
 
        String acceptor = entity.getAcceptor();
        if (acceptor != null) {
            stmt.bindString(6, acceptor);
        }
 
        String other = entity.getOther();
        if (other != null) {
            stmt.bindString(7, other);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ApplyDeptBean readEntity(Cursor cursor, int offset) {
        ApplyDeptBean entity = new ApplyDeptBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dataPackageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // checkTaskId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // department
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // acceptor
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // other
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ApplyDeptBean entity, int offset) {
        entity.setUId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDataPackageId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCheckTaskId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDepartment(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAcceptor(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOther(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ApplyDeptBean entity, long rowId) {
        entity.setUId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ApplyDeptBean entity) {
        if(entity != null) {
            return entity.getUId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ApplyDeptBean entity) {
        return entity.getUId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
