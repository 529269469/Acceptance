package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/14 14
 */
@Entity
public class ApplyDeptBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String checkTaskId;
    private String id;
    private String department;
    private String acceptor;
    private String other;
    @Generated(hash = 1778624720)
    public ApplyDeptBean(Long uId, String dataPackageId, String checkTaskId,
            String id, String department, String acceptor, String other) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.checkTaskId = checkTaskId;
        this.id = id;
        this.department = department;
        this.acceptor = acceptor;
        this.other = other;
    }
    @Generated(hash = 1698551472)
    public ApplyDeptBean() {
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
    public String getCheckTaskId() {
        return this.checkTaskId;
    }
    public void setCheckTaskId(String checkTaskId) {
        this.checkTaskId = checkTaskId;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getAcceptor() {
        return this.acceptor;
    }
    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }
    public String getOther() {
        return this.other;
    }
    public void setOther(String other) {
        this.other = other;
    }

    

}
