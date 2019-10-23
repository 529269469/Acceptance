package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class DataPackageDBean {
    @Id(autoincrement = true)
    private Long uId;
    private String namePackage;
    private String upLoadFile;
    private String id;
    private String name;
    private String code;
    private String type;
    private String responseUnit;
    private String modelCode;
    private String productName;
    private String productCode;
    private String productType;
    private String batch;
    private String creator;
    private String createTime;
    private String modelSeries;
    private String modelSeriesName;
    private String pkgTemplateId;
    private String lifecycleTemplateId;
    private String lifecycleStateId;



    @Generated(hash = 905604226)
    public DataPackageDBean(Long uId, String namePackage, String upLoadFile,
            String id, String name, String code, String type, String responseUnit,
            String modelCode, String productName, String productCode,
            String productType, String batch, String creator, String createTime,
            String modelSeries, String modelSeriesName, String pkgTemplateId,
            String lifecycleTemplateId, String lifecycleStateId) {
        this.uId = uId;
        this.namePackage = namePackage;
        this.upLoadFile = upLoadFile;
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.responseUnit = responseUnit;
        this.modelCode = modelCode;
        this.productName = productName;
        this.productCode = productCode;
        this.productType = productType;
        this.batch = batch;
        this.creator = creator;
        this.createTime = createTime;
        this.modelSeries = modelSeries;
        this.modelSeriesName = modelSeriesName;
        this.pkgTemplateId = pkgTemplateId;
        this.lifecycleTemplateId = lifecycleTemplateId;
        this.lifecycleStateId = lifecycleStateId;
    }
    @Generated(hash = 1504690886)
    public DataPackageDBean() {
    }


    
    public Long getUId() {
        return this.uId;
    }
    public void setUId(Long uId) {
        this.uId = uId;
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
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getResponseUnit() {
        return this.responseUnit;
    }
    public void setResponseUnit(String responseUnit) {
        this.responseUnit = responseUnit;
    }

    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCode() {
        return this.productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getProductType() {
        return this.productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public String getBatch() {
        return this.batch;
    }
    public void setBatch(String batch) {
        this.batch = batch;
    }
    public String getCreator() {
        return this.creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getNamePackage() {
        return this.namePackage;
    }
    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage;
    }
    public String getUpLoadFile() {
        return this.upLoadFile;
    }
    public void setUpLoadFile(String upLoadFile) {
        this.upLoadFile = upLoadFile;
    }
    public String getModelCode() {
        return this.modelCode;
    }
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }
    public String getModelSeries() {
        return this.modelSeries;
    }
    public void setModelSeries(String modelSeries) {
        this.modelSeries = modelSeries;
    }
    public String getModelSeriesName() {
        return this.modelSeriesName;
    }
    public void setModelSeriesName(String modelSeriesName) {
        this.modelSeriesName = modelSeriesName;
    }
    public String getPkgTemplateId() {
        return this.pkgTemplateId;
    }
    public void setPkgTemplateId(String pkgTemplateId) {
        this.pkgTemplateId = pkgTemplateId;
    }
    public String getLifecycleTemplateId() {
        return this.lifecycleTemplateId;
    }
    public void setLifecycleTemplateId(String lifecycleTemplateId) {
        this.lifecycleTemplateId = lifecycleTemplateId;
    }
    public String getLifecycleStateId() {
        return this.lifecycleStateId;
    }
    public void setLifecycleStateId(String lifecycleStateId) {
        this.lifecycleStateId = lifecycleStateId;
    }
}
