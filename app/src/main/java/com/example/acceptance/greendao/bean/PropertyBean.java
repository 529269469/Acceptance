package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class PropertyBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String checkGroupId;
    private String name;
    private String value;
    @Generated(hash = 618142320)
    public PropertyBean(Long uId, String dataPackageId, String checkFileId,
            String checkGroupId, String name, String value) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.checkGroupId = checkGroupId;
        this.name = name;
        this.value = value;
    }
    @Generated(hash = 826223949)
    public PropertyBean() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
