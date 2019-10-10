package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class CheckGroupBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String id;
    private String groupName;
    private String checkGroupConclusion;
    private String checkPerson;
    private String isConclusion;
    private String isTable;
    @Generated(hash = 1936408392)
    public CheckGroupBean(Long uId, String dataPackageId, String checkFileId,
            String id, String groupName, String checkGroupConclusion,
            String checkPerson, String isConclusion, String isTable) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.id = id;
        this.groupName = groupName;
        this.checkGroupConclusion = checkGroupConclusion;
        this.checkPerson = checkPerson;
        this.isConclusion = isConclusion;
        this.isTable = isTable;
    }
    @Generated(hash = 36145380)
    public CheckGroupBean() {
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
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGroupName() {
        return this.groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getCheckGroupConclusion() {
        return this.checkGroupConclusion;
    }
    public void setCheckGroupConclusion(String checkGroupConclusion) {
        this.checkGroupConclusion = checkGroupConclusion;
    }
    public String getCheckPerson() {
        return this.checkPerson;
    }
    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }
    public String getIsConclusion() {
        return this.isConclusion;
    }
    public void setIsConclusion(String isConclusion) {
        this.isConclusion = isConclusion;
    }
    public String getIsTable() {
        return this.isTable;
    }
    public void setIsTable(String isTable) {
        this.isTable = isTable;
    }
}
