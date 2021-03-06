package com.example.acceptance.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.acceptance.greendao.bean.CheckFileBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHECK_FILE_BEAN".
*/
public class CheckFileBeanDao extends AbstractDao<CheckFileBean, Long> {

    public static final String TABLENAME = "CHECK_FILE_BEAN";

    /**
     * Properties of entity CheckFileBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UId = new Property(0, Long.class, "uId", true, "_id");
        public final static Property DataPackageId = new Property(1, String.class, "dataPackageId", false, "DATA_PACKAGE_ID");
        public final static Property Id = new Property(2, String.class, "id", false, "ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Code = new Property(4, String.class, "code", false, "CODE");
        public final static Property DocType = new Property(5, String.class, "docType", false, "DOC_TYPE");
        public final static Property ProductType = new Property(6, String.class, "productType", false, "PRODUCT_TYPE");
        public final static Property Conclusion = new Property(7, String.class, "conclusion", false, "CONCLUSION");
        public final static Property CheckPerson = new Property(8, String.class, "checkPerson", false, "CHECK_PERSON");
        public final static Property CheckDate = new Property(9, String.class, "checkDate", false, "CHECK_DATE");
        public final static Property SortBy = new Property(10, String.class, "sortBy", false, "SORT_BY");
        public final static Property CheckTime = new Property(11, String.class, "checkTime", false, "CHECK_TIME");
        public final static Property Sort = new Property(12, String.class, "sort", false, "SORT");
        public final static Property TabsName = new Property(13, String.class, "tabsName", false, "TABS_NAME");
        public final static Property AccordFile = new Property(14, String.class, "accordFile", false, "ACCORD_FILE");
        public final static Property SelectEdit = new Property(15, String.class, "selectEdit", false, "SELECT_EDIT");
        public final static Property UniqueValue = new Property(16, String.class, "uniqueValue", false, "UNIQUE_VALUE");
        public final static Property ProductTypeValue = new Property(17, String.class, "productTypeValue", false, "PRODUCT_TYPE_VALUE");
        public final static Property Description = new Property(18, String.class, "description", false, "DESCRIPTION");
    }


    public CheckFileBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CheckFileBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHECK_FILE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: uId
                "\"DATA_PACKAGE_ID\" TEXT," + // 1: dataPackageId
                "\"ID\" TEXT," + // 2: id
                "\"NAME\" TEXT," + // 3: name
                "\"CODE\" TEXT," + // 4: code
                "\"DOC_TYPE\" TEXT," + // 5: docType
                "\"PRODUCT_TYPE\" TEXT," + // 6: productType
                "\"CONCLUSION\" TEXT," + // 7: conclusion
                "\"CHECK_PERSON\" TEXT," + // 8: checkPerson
                "\"CHECK_DATE\" TEXT," + // 9: checkDate
                "\"SORT_BY\" TEXT," + // 10: sortBy
                "\"CHECK_TIME\" TEXT," + // 11: checkTime
                "\"SORT\" TEXT," + // 12: sort
                "\"TABS_NAME\" TEXT," + // 13: tabsName
                "\"ACCORD_FILE\" TEXT," + // 14: accordFile
                "\"SELECT_EDIT\" TEXT," + // 15: selectEdit
                "\"UNIQUE_VALUE\" TEXT," + // 16: uniqueValue
                "\"PRODUCT_TYPE_VALUE\" TEXT," + // 17: productTypeValue
                "\"DESCRIPTION\" TEXT);"); // 18: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHECK_FILE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CheckFileBean entity) {
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
 
        String docType = entity.getDocType();
        if (docType != null) {
            stmt.bindString(6, docType);
        }
 
        String productType = entity.getProductType();
        if (productType != null) {
            stmt.bindString(7, productType);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(8, conclusion);
        }
 
        String checkPerson = entity.getCheckPerson();
        if (checkPerson != null) {
            stmt.bindString(9, checkPerson);
        }
 
        String checkDate = entity.getCheckDate();
        if (checkDate != null) {
            stmt.bindString(10, checkDate);
        }
 
        String sortBy = entity.getSortBy();
        if (sortBy != null) {
            stmt.bindString(11, sortBy);
        }
 
        String checkTime = entity.getCheckTime();
        if (checkTime != null) {
            stmt.bindString(12, checkTime);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(13, sort);
        }
 
        String tabsName = entity.getTabsName();
        if (tabsName != null) {
            stmt.bindString(14, tabsName);
        }
 
        String accordFile = entity.getAccordFile();
        if (accordFile != null) {
            stmt.bindString(15, accordFile);
        }
 
        String selectEdit = entity.getSelectEdit();
        if (selectEdit != null) {
            stmt.bindString(16, selectEdit);
        }
 
        String uniqueValue = entity.getUniqueValue();
        if (uniqueValue != null) {
            stmt.bindString(17, uniqueValue);
        }
 
        String productTypeValue = entity.getProductTypeValue();
        if (productTypeValue != null) {
            stmt.bindString(18, productTypeValue);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(19, description);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CheckFileBean entity) {
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
 
        String docType = entity.getDocType();
        if (docType != null) {
            stmt.bindString(6, docType);
        }
 
        String productType = entity.getProductType();
        if (productType != null) {
            stmt.bindString(7, productType);
        }
 
        String conclusion = entity.getConclusion();
        if (conclusion != null) {
            stmt.bindString(8, conclusion);
        }
 
        String checkPerson = entity.getCheckPerson();
        if (checkPerson != null) {
            stmt.bindString(9, checkPerson);
        }
 
        String checkDate = entity.getCheckDate();
        if (checkDate != null) {
            stmt.bindString(10, checkDate);
        }
 
        String sortBy = entity.getSortBy();
        if (sortBy != null) {
            stmt.bindString(11, sortBy);
        }
 
        String checkTime = entity.getCheckTime();
        if (checkTime != null) {
            stmt.bindString(12, checkTime);
        }
 
        String sort = entity.getSort();
        if (sort != null) {
            stmt.bindString(13, sort);
        }
 
        String tabsName = entity.getTabsName();
        if (tabsName != null) {
            stmt.bindString(14, tabsName);
        }
 
        String accordFile = entity.getAccordFile();
        if (accordFile != null) {
            stmt.bindString(15, accordFile);
        }
 
        String selectEdit = entity.getSelectEdit();
        if (selectEdit != null) {
            stmt.bindString(16, selectEdit);
        }
 
        String uniqueValue = entity.getUniqueValue();
        if (uniqueValue != null) {
            stmt.bindString(17, uniqueValue);
        }
 
        String productTypeValue = entity.getProductTypeValue();
        if (productTypeValue != null) {
            stmt.bindString(18, productTypeValue);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(19, description);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CheckFileBean readEntity(Cursor cursor, int offset) {
        CheckFileBean entity = new CheckFileBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // uId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dataPackageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // code
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // docType
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // productType
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // conclusion
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // checkPerson
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // checkDate
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // sortBy
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // checkTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // sort
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // tabsName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // accordFile
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // selectEdit
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // uniqueValue
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // productTypeValue
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // description
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CheckFileBean entity, int offset) {
        entity.setUId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDataPackageId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDocType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setProductType(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setConclusion(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCheckPerson(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCheckDate(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSortBy(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCheckTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSort(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTabsName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setAccordFile(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setSelectEdit(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setUniqueValue(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setProductTypeValue(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDescription(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CheckFileBean entity, long rowId) {
        entity.setUId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CheckFileBean entity) {
        if(entity != null) {
            return entity.getUId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CheckFileBean entity) {
        return entity.getUId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
