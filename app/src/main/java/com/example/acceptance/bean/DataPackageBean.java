package com.example.acceptance.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 17
 */
@XStreamAlias("DataPackage")
public class DataPackageBean {
    private String name;
    private String code;
    private String type;
    private String responseUnit;
    private String modalCode;
    private String productName;
    private String productCode;
    private String productType;
    private String batch;
    private String createTime;
    private String path;

    @Override
    public String toString() {
        return "DataPackageBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", responseUnit='" + responseUnit + '\'' +
                ", modalCode='" + modalCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productType='" + productType + '\'' +
                ", batch='" + batch + '\'' +
                ", createTime='" + createTime + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponseUnit() {
        return responseUnit;
    }

    public void setResponseUnit(String responseUnit) {
        this.responseUnit = responseUnit;
    }

    public String getModalCode() {
        return modalCode;
    }

    public void setModalCode(String modalCode) {
        this.modalCode = modalCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
