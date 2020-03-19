package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class DeliveryListBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String isParent;
    private String project;
    private String parentId;
    private String uniqueValue;
    private String typeDisplay;
    private String sortBy;

    private String sort;




    @Generated(hash = 1950769879)
    public DeliveryListBean(Long uId, String dataPackageId, String id,
            String isParent, String project, String parentId, String uniqueValue,
            String typeDisplay, String sortBy, String sort) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.isParent = isParent;
        this.project = project;
        this.parentId = parentId;
        this.uniqueValue = uniqueValue;
        this.typeDisplay = typeDisplay;
        this.sortBy = sortBy;
        this.sort = sort;
    }
    @Generated(hash = 1520076137)
    public DeliveryListBean() {
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
    public String getIsParent() {
        return this.isParent;
    }
    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
    public String getProject() {
        return this.project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getUniqueValue() {
        return this.uniqueValue;
    }
    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }
    public String getTypeDisplay() {
        return this.typeDisplay;
    }
    public void setTypeDisplay(String typeDisplay) {
        this.typeDisplay = typeDisplay;
    }
    public String getSortBy() {
        return this.sortBy;
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public String getSort() {
        return this.sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
}
