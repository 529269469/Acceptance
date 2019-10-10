package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class RelatedDocumentIdSetBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String checkGroupId;
    private String checkItemId;
    private String RelatedDocumentId;
    @Generated(hash = 802698798)
    public RelatedDocumentIdSetBean(Long uId, String dataPackageId,
            String checkFileId, String checkGroupId, String checkItemId,
            String RelatedDocumentId) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.checkGroupId = checkGroupId;
        this.checkItemId = checkItemId;
        this.RelatedDocumentId = RelatedDocumentId;
    }
    @Generated(hash = 758760924)
    public RelatedDocumentIdSetBean() {
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
    public String getCheckItemId() {
        return this.checkItemId;
    }
    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }
    public String getRelatedDocumentId() {
        return this.RelatedDocumentId;
    }
    public void setRelatedDocumentId(String RelatedDocumentId) {
        this.RelatedDocumentId = RelatedDocumentId;
    }
}
