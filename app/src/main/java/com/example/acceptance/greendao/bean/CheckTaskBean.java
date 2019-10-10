package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class CheckTaskBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String name;
    private String code;
    private String issuer;
    private String issueDept;
    private String accepter;
    private String acceptDate;
    private String checkDate;
    private String applicant;
    private String applyCompany;
    private String phone;
    private String ApplyDeptSet;
    @Generated(hash = 994896207)
    public CheckTaskBean(Long uId, String dataPackageId, String id, String name,
            String code, String issuer, String issueDept, String accepter,
            String acceptDate, String checkDate, String applicant,
            String applyCompany, String phone, String ApplyDeptSet) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.name = name;
        this.code = code;
        this.issuer = issuer;
        this.issueDept = issueDept;
        this.accepter = accepter;
        this.acceptDate = acceptDate;
        this.checkDate = checkDate;
        this.applicant = applicant;
        this.applyCompany = applyCompany;
        this.phone = phone;
        this.ApplyDeptSet = ApplyDeptSet;
    }
    @Generated(hash = 199812404)
    public CheckTaskBean() {
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
    public String getIssuer() {
        return this.issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public String getIssueDept() {
        return this.issueDept;
    }
    public void setIssueDept(String issueDept) {
        this.issueDept = issueDept;
    }
    public String getAccepter() {
        return this.accepter;
    }
    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }
    public String getAcceptDate() {
        return this.acceptDate;
    }
    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }
    public String getCheckDate() {
        return this.checkDate;
    }
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    public String getApplicant() {
        return this.applicant;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
    public String getApplyCompany() {
        return this.applyCompany;
    }
    public void setApplyCompany(String applyCompany) {
        this.applyCompany = applyCompany;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getApplyDeptSet() {
        return this.ApplyDeptSet;
    }
    public void setApplyDeptSet(String ApplyDeptSet) {
        this.ApplyDeptSet = ApplyDeptSet;
    }

}
