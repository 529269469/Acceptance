package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class PropertyBeanX {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String checkGroupId;
    private String checkItemId;
    private String name;
    private String value;
    @Generated(hash = 1490272413)
    public PropertyBeanX(Long uId, String dataPackageId, String checkFileId,
            String checkGroupId, String checkItemId, String name, String value) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.checkGroupId = checkGroupId;
        this.checkItemId = checkItemId;
        this.name = name;
        this.value = value;
    }
    @Generated(hash = 2114110642)
    public PropertyBeanX() {
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
