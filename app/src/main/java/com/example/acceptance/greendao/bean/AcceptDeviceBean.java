package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/16 09
 */
@Entity
public class AcceptDeviceBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String checkGroupId;
    private String id;
    private String name;
    private String specification;
    private String accuracy;
    private String certificate;
    private String description;
    @Generated(hash = 300549178)
    public AcceptDeviceBean(Long uId, String dataPackageId, String checkFileId,
            String checkGroupId, String id, String name, String specification,
            String accuracy, String certificate, String description) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.checkGroupId = checkGroupId;
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.accuracy = accuracy;
        this.certificate = certificate;
        this.description = description;
    }
    @Generated(hash = 1081029027)
    public AcceptDeviceBean() {
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
    public String getCheckFileId() {
        return this.checkFileId;
    }
    public void setCheckFileId(String checkFileId) {
        this.checkFileId = checkFileId;
    }
    public String getCheckGroupId() {
        return this.checkGroupId;
    }
    public void setCheckGroupId(String checkGroupId) {
        this.checkGroupId = checkGroupId;
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
    public String getSpecification() {
        return this.specification;
    }
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public String getAccuracy() {
        return this.accuracy;
    }
    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
    public String getCertificate() {
        return this.certificate;
    }
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    

}
