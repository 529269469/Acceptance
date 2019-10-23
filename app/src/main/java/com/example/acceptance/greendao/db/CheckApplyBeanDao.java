package com.example.acceptance.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.acceptance.greendao.bean.CheckApplyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHECK_APPLY_BEAN".
*/
public class CheckApplyBeanDao extends AbstractDao<CheckApplyBean, Long> {

    public static final String TABLENAME = "CHECK_APPLY_BEAN";

    /**
     * Properties of entity CheckApplyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UId = new Property(0, Long.class, "uId", true, "_id");
        public final static Property DataPackageId = new Property(1, String.class, "dataPackageId", false, "DATA_PACKAGE_ID");
        public final static Property Id = new Property(2, String.class, "id", false, "ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Code = new Property(4, String.class, "code", false, "CODE");
        public final static Property ContractCode = new Property(5, String.class, "contractCode", false, "CONTRACT_CODE");
        public final static Property ContractName = new Property(6, String.class, "contractName", false, "CONTRACT_NAME");
        public final static Property Applicant = new Property(7, String.class, "applicant", false, "APPLICANT");
        public final static Property ApplyCompany = new Property(8, String.class, "applyCompany", false, "APPLY_COMPANY");
        public final static Property Phone = new Property(9, String.class, "phone", false, "PHONE");
        public final static Property Conclusion = new Property(10, String.class, "conclusion", false, "CONCLUSION");
        public final static Property Description = new Property(11, String.class, "description", false, "DESCRIPTION");
        public final static Property DocTypeVal = new Property(12, String.class, "docTypeVal", false, "DOC_TYPE_VAL");
        public final static Property ImgAndVideoList = new Property(13, String.class, "imgAndVideoList", false, "IMG_AND_VIDEO_LIST");
    }


    public CheckApplyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CheckApplyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHECK_APPLY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: uId
                "\"DATA_PACKAGE_ID\" TEXT," + // 1: dataPackageId
                "\"ID\" TEXT," + // 2: id
                "\"NAME\" TEXT," + // 3: name
                "\"CODE\" TEXT," + // 4: code
                "\"CONTRACT_CODE\" TEXT," + // 5: contractCode
                "\"CONTRACT_NAME\" TEXT," + // 6: contractName
                "\"APPLICANT\" TEXT," + // 7: applicant
                "\"APPLY_COMPANY\" TEXT," + // 8: applyCompany
                "\"PHONE\" TEXT," + // 9: phone
                "\"CONCLUSION\" TEXT," + // 10: conclusion
                "\"DESCRIPTION\" TEXT," + // 11: description
                "\"DOC_TYPE_VAL\" TEXT," + // 12: docTypeVal
                "\"IMG_AND_VIDEO_LIST\" TEXT);"); // 13: imgAndVideoList
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHECK_APPLY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CheckApplyBean entity) {
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
 
        String contractCode = entity.getContractCode();
        if (contractCode != null) {
            stmt.bindString(6, contractCode);
        }
 
        String contractName = entity.getContractName();
        if (contractName != null) {
            stmt.bindString(7, contractName);
        }
 
        String applicant = entity.getApplicant();
        if (applicant != null) {
            stmt.bindString(8, applicant);
        }
 
        String applyCompany = entity.getApplyCompany();
        if (applyCompany != null) {
            stmt.bindString(9, applyCompany);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(10, phone);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(11, conclusion);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(12, description);
        }
 
        String docTypeVal = entity.getDocTypeVal();
        if (docTypeVal != null) {
            stmt.bindString(13, docTypeVal);
        }
 
        String imgAndVideoList = entity.getImgAndVideoList();
        if (imgAndVideoList != null) {
            stmt.bindString(14, imgAndVideoList);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CheckApplyBean entity) {
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
 
        String contractCode = entity.getContractCode();
        if (contractCode != null) {
            stmt.bindString(6, contractCode);
        }
 
        String contractName = entity.getContractName();
        if (contractName != null) {
            stmt.bindString(7, contractName);
        }
 
        String applicant = entity.getApplicant();
        if (applicant != null) {
            stmt.bindString(8, applicant);
        }
 
        String applyCompany = entity.getApplyCompany();
        if (applyCompany != null) {
            stmt.bindString(9, applyCompany);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(10, phone);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(11, conclusion);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(12, description);
        }
 
        String docTypeVal = entity.getDocTypeVal();
        if (docTypeVal != null) {
            stmt.bindString(13, docTypeVal);
        }
 
        String imgAndVideoList = entity.getImgAndVideoList();
        if (imgAndVideoList != null) {
            stmt.bindString(14, imgAndVideoList);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CheckApplyBean readEntity(Cursor cursor, int offset) {
        CheckApplyBean entity = new CheckApplyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dataPackageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // code
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // contractCode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // contractName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // applicant
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // applyCompany
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // phone
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // conclusion
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // description
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // docTypeVal
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // imgAndVideoList
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CheckApplyBean entity, int offset) {
        entity.setUId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDataPackageId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContractCode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContractName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setApplicant(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setApplyCompany(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPhone(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setConclusion(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDescription(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDocTypeVal(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setImgAndVideoList(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CheckApplyBean entity, long rowId) {
        entity.setUId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CheckApplyBean entity) {
        if(entity != null) {
            return entity.getUId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CheckApplyBean entity) {
        return entity.getUId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
