package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class CheckFileBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String name;
    private String code;
    private String docType;
    private String productType;
    private String conclusion;
    private String checkPerson;
    private String checkDate;
    private String sortBy;

    private String checkTime;
    private String sort;
    private String tabsName;
    private String accordFile;
    private String selectEdit;
    private String uniqueValue;
    private String productTypeValue;
    private String description;




    @Generated(hash = 1171325518)
    public CheckFileBean(Long uId, String dataPackageId, String id, String name,
            String code, String docType, String productType, String conclusion,
            String checkPerson, String checkDate, String sortBy, String checkTime,
            String sort, String tabsName, String accordFile, String selectEdit,
            String uniqueValue, String productTypeValue, String description) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.name = name;
        this.code = code;
        this.docType = docType;
        this.productType = productType;
        this.conclusion = conclusion;
        this.checkPerson = checkPerson;
        this.checkDate = checkDate;
        this.sortBy = sortBy;
        this.checkTime = checkTime;
        this.sort = sort;
        this.tabsName = tabsName;
        this.accordFile = accordFile;
        this.selectEdit = selectEdit;
        this.uniqueValue = uniqueValue;
        this.productTypeValue = productTypeValue;
        this.description = description;
    }
    @Generated(hash = 372645879)
    public CheckFileBean() {
    }


    

    public Long getUId() {
        return this.uId;
    }
    public void setUId(Long uId) {
        this.uId = uId;
    }
    public String getDataPackageId() {
        return this.dataPackageId;
    }
    public void setDataPackageId(String dataPackageId) {
        this.dataPackageId = dataPackageId;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDocType() {
        return this.docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
    public String getConclusion() {
        return this.conclusion;
    }
    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    public String getCheckPerson() {
        return this.checkPerson;
    }
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }
    public String getProductType() {
        return this.productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getCheckDate() {
        return this.checkDate;
    }
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    public String getSortBy() {
        return this.sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public String getCheckTime() {
        return this.checkTime;
    }
    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
    public String getSort() {
        return this.sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getTabsName() {
        return this.tabsName;
    }
    public void setTabsName(String tabsName) {
        this.tabsName = tabsName;
    }
    public String getAccordFile() {
        return this.accordFile;
    }
    public void setAccordFile(String accordFile) {
        this.accordFile = accordFile;
    }
    public String getSelectEdit() {
        return this.selectEdit;
    }
    public void setSelectEdit(String selectEdit) {
        this.selectEdit = selectEdit;
    }
    public String getUniqueValue() {
        return this.uniqueValue;
    }
    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }
    public String getProductTypeValue() {
        return this.productTypeValue;
    }
    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
