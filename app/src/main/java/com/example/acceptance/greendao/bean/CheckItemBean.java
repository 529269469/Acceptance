package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class CheckItemBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkFileId;
    private String checkGroupId;
    private String id;
    private String name;
    private String options;
    private String selected;
    private String uniqueValue;
    private String sort;
    private String description;
    private String relate;



    @Generated(hash = 1980801197)
    public CheckItemBean(Long uId, String dataPackageId, String checkFileId,
            String checkGroupId, String id, String name, String options,
            String selected, String uniqueValue, String sort, String description,
            String relate) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkFileId = checkFileId;
        this.checkGroupId = checkGroupId;
        this.id = id;
        this.name = name;
        this.options = options;
        this.selected = selected;
        this.uniqueValue = uniqueValue;
        this.sort = sort;
        this.description = description;
        this.relate = relate;
    }
    @Generated(hash = 1394073385)
    public CheckItemBean() {
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
    public String getOptions() {
        return this.options;
    }
    public void setOptions(String options) {
        this.options = options;
    }
    public String getSelected() {
        return this.selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }
    public String getUniqueValue() {
        return this.uniqueValue;
    }
    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }
    public String getSort() {
        return this.sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRelate() {
        return this.relate;
    }
    public void setRelate(String relate) {
        this.relate = relate;
    }


}
