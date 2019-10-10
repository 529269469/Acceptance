package com.example.acceptance.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/9 10
 */
@Entity
public class ApplyItemBean {
    @Id(autoincrement = true)
    private Long uId;
    private String dataPackageId;
    private String id;
    private String productCodeName;
    private String productCode;
    private String productStatus;
    private String isPureCheck;
    private String isArmyCheck;
    private String isCompleteChoice;
    private String isCompleteRoutine;
    private String isSatisfyRequire;
    private String description;
    private String productName;
    @Generated(hash = 244739353)
    public ApplyItemBean(Long uId, String dataPackageId, String id,
            String productCodeName, String productCode, String productStatus,
            String isPureCheck, String isArmyCheck, String isCompleteChoice,
            String isCompleteRoutine, String isSatisfyRequire, String description,
            String productName) {
        this.uId = uId;
        this.dataPackageId = dataPackageId;
        this.id = id;
        this.productCodeName = productCodeName;
        this.productCode = productCode;
        this.productStatus = productStatus;
        this.isPureCheck = isPureCheck;
        this.isArmyCheck = isArmyCheck;
        this.isCompleteChoice = isCompleteChoice;
        this.isCompleteRoutine = isCompleteRoutine;
        this.isSatisfyRequire = isSatisfyRequire;
        this.description = description;
        this.productName = productName;
    }
    @Generated(hash = 2067262448)
    public ApplyItemBean() {
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
    public String getProductCodeName() {
        return this.productCodeName;
    }
    public void setProductCodeName(String productCodeName) {
        this.productCodeName = productCodeName;
    }
    public String getProductCode() {
        return this.productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getProductStatus() {
        return this.productStatus;
    }
    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }
    public String getIsPureCheck() {
        return this.isPureCheck;
    }
    public void setIsPureCheck(String isPureCheck) {
        this.isPureCheck = isPureCheck;
    }
    public String getIsArmyCheck() {
        return this.isArmyCheck;
    }
    public void setIsArmyCheck(String isArmyCheck) {
        this.isArmyCheck = isArmyCheck;
    }
    public String getIsCompleteChoice() {
        return this.isCompleteChoice;
    }
    public void setIsCompleteChoice(String isCompleteChoice) {
        this.isCompleteChoice = isCompleteChoice;
    }
    public String getIsCompleteRoutine() {
        return this.isCompleteRoutine;
    }
    public void setIsCompleteRoutine(String isCompleteRoutine) {
        this.isCompleteRoutine = isCompleteRoutine;
    }
    public String getIsSatisfyRequire() {
        return this.isSatisfyRequire;
    }
    public void setIsSatisfyRequire(String isSatisfyRequire) {
        this.isSatisfyRequire = isSatisfyRequire;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

}
