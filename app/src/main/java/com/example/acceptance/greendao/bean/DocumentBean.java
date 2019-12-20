package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class DocumentBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String code;
    private String name;
    private String secret;
    private String payClassify;
    private String payClassifyName;
    private String modalCode;
    private String productCodeName;
    private String productCode;
    private String stage;
    private String techStatus;
    private String approver;
    private String approvalDate;
    private String issl;
    private String conclusion;
    private String description;
    private String onLine;
    private String infoUrl;
    private String uniqueValue;


    @Generated(hash = 818919459)
    public DocumentBean(Long uId, String dataPackageId, String id, String code,
            String name, String secret, String payClassify, String payClassifyName,
            String modalCode, String productCodeName, String productCode,
            String stage, String techStatus, String approver, String approvalDate,
            String issl, String conclusion, String description, String onLine,
            String infoUrl, String uniqueValue) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.code = code;
        this.name = name;
        this.secret = secret;
        this.payClassify = payClassify;
        this.payClassifyName = payClassifyName;
        this.modalCode = modalCode;
        this.productCodeName = productCodeName;
        this.productCode = productCode;
        this.stage = stage;
        this.techStatus = techStatus;
        this.approver = approver;
        this.approvalDate = approvalDate;
        this.issl = issl;
        this.conclusion = conclusion;
        this.description = description;
        this.onLine = onLine;
        this.infoUrl = infoUrl;
        this.uniqueValue = uniqueValue;
    }
    @Generated(hash = 1348634967)
    public DocumentBean() {
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
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecret() {
        return this.secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public String getPayClassify() {
        return this.payClassify;
    }
    public void setPayClassify(String payClassify) {
        this.payClassify = payClassify;
    }
    public String getModalCode() {
        return this.modalCode;
    }
    public void setModalCode(String modalCode) {
        this.modalCode = modalCode;
    }
    public String getProductCodeName() {
        return this.productCodeName;
    }
    public void setProductCodeName(String productCodeName) {
        this.productCodeName = productCodeName;
    }
    public String getProductCode() {
        return this.productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getStage() {
        return this.stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }
    public String getTechStatus() {
        return this.techStatus;
    }
    public void setTechStatus(String techStatus) {
        this.techStatus = techStatus;
    }
    public String getApprover() {
        return this.approver;
    }
    public void setApprover(String approver) {
        this.approver = approver;
    }
    public String getApprovalDate() {
        return this.approvalDate;
    }
    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }
    public String getIssl() {
        return this.issl;
    }
    public void setIssl(String issl) {
        this.issl = issl;
    }
    public String getConclusion() {
        return this.conclusion;
    }
    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPayClassifyName() {
        return this.payClassifyName;
    }
    public void setPayClassifyName(String payClassifyName) {
        this.payClassifyName = payClassifyName;
    }
    public String getOnLine() {
        return this.onLine;
    }
    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }
    public String getInfoUrl() {
        return this.infoUrl;
    }
    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
    public String getUniqueValue() {
        return this.uniqueValue;
    }
    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }
}
