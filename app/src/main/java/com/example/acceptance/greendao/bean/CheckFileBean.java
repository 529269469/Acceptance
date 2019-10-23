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

    @Generated(hash = 486709301)
    public CheckFileBean(Long uId, String dataPackageId, String id, String name,
            String code, String docType, String productType, String conclusion,
            String checkPerson) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.name = name;
        this.code = code;
        this.docType = docType;
        this.productType = productType;
        this.conclusion = conclusion;
        this.checkPerson = checkPerson;
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

}
