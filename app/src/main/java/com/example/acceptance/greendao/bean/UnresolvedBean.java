package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class UnresolvedBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String productCode;
    private String question;
    private String confirmer;
    private String confirmTime;
    private String fileId;


    @Generated(hash = 762320047)
    public UnresolvedBean(Long uId, String dataPackageId, String id,
            String productCode, String question, String confirmer,
            String confirmTime, String fileId) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.productCode = productCode;
        this.question = question;
        this.confirmer = confirmer;
        this.confirmTime = confirmTime;
        this.fileId = fileId;
    }
    @Generated(hash = 118033364)
    public UnresolvedBean() {
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
    public String getProductCode() {
        return this.productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getQuestion() {
        return this.question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getConfirmer() {
        return this.confirmer;
    }
    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }
    public String getConfirmTime() {
        return this.confirmTime;
    }
    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }
    public String getFileId() {
        return this.fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
