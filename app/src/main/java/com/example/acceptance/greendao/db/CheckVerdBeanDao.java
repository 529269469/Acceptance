package com.example.acceptance.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.acceptance.greendao.bean.CheckVerdBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHECK_VERD_BEAN".
*/
public class CheckVerdBeanDao extends AbstractDao<CheckVerdBean, Long> {

    public static final String TABLENAME = "CHECK_VERD_BEAN";

    /**
     * Properties of entity CheckVerdBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UId = new Property(0, Long.class, "uId", true, "_id");
        public final static Property DataPackageId = new Property(1, String.class, "dataPackageId", false, "DATA_PACKAGE_ID");
        public final static Property Id = new Property(2, String.class, "id", false, "ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Code = new Property(4, String.class, "code", false, "CODE");
        public final static Property QConclusion = new Property(5, String.class, "qConclusion", false, "Q_CONCLUSION");
        public final static Property GConclusion = new Property(6, String.class, "gConclusion", false, "G_CONCLUSION");
        public final static Property JConclusion = new Property(7, String.class, "jConclusion", false, "J_CONCLUSION");
        public final static Property Conclusion = new Property(8, String.class, "conclusion", false, "CONCLUSION");
        public final static Property CheckPerson = new Property(9, String.class, "checkPerson", false, "CHECK_PERSON");
        public final static Property DocTypeVal = new Property(10, String.class, "docTypeVal", false, "DOC_TYPE_VAL");
        public final static Property CheckPersonId = new Property(11, String.class, "checkPersonId", false, "CHECK_PERSON_ID");
        public final static Property CheckDate = new Property(12, String.class, "checkDate", false, "CHECK_DATE");
        public final static Property YConclusion = new Property(13, String.class, "yConclusion", false, "Y_CONCLUSION");
    }


    public CheckVerdBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CheckVerdBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHECK_VERD_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: uId
                "\"DATA_PACKAGE_ID\" TEXT," + // 1: dataPackageId
                "\"ID\" TEXT," + // 2: id
                "\"NAME\" TEXT," + // 3: name
                "\"CODE\" TEXT," + // 4: code
                "\"Q_CONCLUSION\" TEXT," + // 5: qConclusion
                "\"G_CONCLUSION\" TEXT," + // 6: gConclusion
                "\"J_CONCLUSION\" TEXT," + // 7: jConclusion
                "\"CONCLUSION\" TEXT," + // 8: conclusion
                "\"CHECK_PERSON\" TEXT," + // 9: checkPerson
                "\"DOC_TYPE_VAL\" TEXT," + // 10: docTypeVal
                "\"CHECK_PERSON_ID\" TEXT," + // 11: checkPersonId
                "\"CHECK_DATE\" TEXT," + // 12: checkDate
                "\"Y_CONCLUSION\" TEXT);"); // 13: yConclusion
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHECK_VERD_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CheckVerdBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(3, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(5, code);
        }
 
        String qConclusion = entity.getQConclusion();
        if (qConclusion != null) {
            stmt.bindString(6, qConclusion);
        }
 
        String gConclusion = entity.getGConclusion();
        if (gConclusion != null) {
            stmt.bindString(7, gConclusion);
        }
 
        String jConclusion = entity.getJConclusion();
        if (jConclusion != null) {
            stmt.bindString(8, jConclusion);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(9, conclusion);
        }
 
        String checkPerson = entity.getCheckPerson();
        if (checkPerson != null) {
            stmt.bindString(10, checkPerson);
        }
 
        String docTypeVal = entity.getDocTypeVal();
        if (docTypeVal != null) {
            stmt.bindString(11, docTypeVal);
        }
 
        String checkPersonId = entity.getCheckPersonId();
        if (checkPersonId != null) {
            stmt.bindString(12, checkPersonId);
        }
 
        String checkDate = entity.getCheckDate();
        if (checkDate != null) {
            stmt.bindString(13, checkDate);
        }
 
        String yConclusion = entity.getYConclusion();
        if (yConclusion != null) {
            stmt.bindString(14, yConclusion);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CheckVerdBean entity) {
        stmt.clearBindings();
 
        Long uId = entity.getUId();
        if (uId != null) {
            stmt.bindLong(1, uId);
        }
 
        String dataPackageId = entity.getDataPackageId();
        if (dataPackageId != null) {
            stmt.bindString(2, dataPackageId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(3, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(5, code);
        }
 
        String qConclusion = entity.getQConclusion();
        if (qConclusion != null) {
            stmt.bindString(6, qConclusion);
        }
 
        String gConclusion = entity.getGConclusion();
        if (gConclusion != null) {
            stmt.bindString(7, gConclusion);
        }
 
        String jConclusion = entity.getJConclusion();
        if (jConclusion != null) {
            stmt.bindString(8, jConclusion);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(9, conclusion);
        }
 
        String checkPerson = entity.getCheckPerson();
        if (checkPerson != null) {
            stmt.bindString(10, checkPerson);
        }
 
        String docTypeVal = entity.getDocTypeVal();
        if (docTypeVal != null) {
            stmt.bindString(11, docTypeVal);
        }
 
        String checkPersonId = entity.getCheckPersonId();
        if (checkPersonId != null) {
            stmt.bindString(12, checkPersonId);
        }
 
        String checkDate = entity.getCheckDate();
        if (checkDate != null) {
            stmt.bindString(13, checkDate);
        }
 
        String yConclusion = entity.getYConclusion();
        if (yConclusion != null) {
            stmt.bindString(14, yConclusion);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CheckVerdBean readEntity(Cursor cursor, int offset) {
        CheckVerdBean entity = new CheckVerdBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dataPackageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // code
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // qConclusion
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // gConclusion
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // jConclusion
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // conclusion
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // checkPerson
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // docTypeVal
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // checkPersonId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // checkDate
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // yConclusion
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CheckVerdBean entity, int offset) {
        entity.setUId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDataPackageId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setQConclusion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGConclusion(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setJConclusion(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setConclusion(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCheckPerson(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDocTypeVal(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCheckPersonId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCheckDate(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setYConclusion(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CheckVerdBean entity, long rowId) {
        entity.setUId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CheckVerdBean entity) {
        if(entity != null) {
            return entity.getUId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CheckVerdBean entity) {
        return entity.getUId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
