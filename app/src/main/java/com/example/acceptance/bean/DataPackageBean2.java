package com.example.acceptance.bean;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 17
 */
@XStreamAlias("DataPackage")
public class DataPackageBean2 {
    /**
     * -id : 337934853302988800
     * name : 单机产品数据包
     * code : P011
     * type : 产品数据包
     * responseUnit : 四部
     * modalCode : string
     * productName : 单机
     * productCode : DD-2-0
     * productType : 电气产品
     * batch : Y-1
     * checkApply : {"-id":"337934853672087552","name":"单机产品数据包验收申请","code":"P011-01","applicant":"申请人","applyCompany":"申请单位","phone":"136XXXXXXXXX","conclusion":"验收内部审查结论","description":"备注"}
     * checkTask : {"-id":"337934853944717312","name":"单机产品数据包验收任务单","code":"P011-02","issuer":"q","issueDept":"q","accepter":"q","applicant":"申请人","applyCompany":"申请单位","phone":"136XXXXXXXXX"}
     * ApplyItemSet : {"ApplyItem":{"isArmyCheck":"true","isCompleteChoice":"true","isCompleteRoutine":"true","isPureCheck":"false","isSatisfyRequire":"true","productCode":"bian-1","productCodeName":"d-12","productName":"产品1"}}
     * CheckFileSet : {"CheckFile":[{"-id":"337934854364147712","name":"单机产品数据包齐套性检查","code":"P011-03","docType":"齐套性检查","conclusion":"齐套检查结论c","CheckGroupSet":{"CheckGroup":[{},{"groupName":"产品外观检查","CheckItemSet":{"CheckItem":[{"-checkGroupId":"353862513528516608","name":"string","options":"是,否","RelatedDocumentIdSet":{"RelatedDocumentId":"354086034652557312"}},{"-checkGroupId":"353862513528516608","name":"是否有摩擦","options":"是,否"}]}},{"groupName":"123","CheckItemSet":{"CheckItem":[{"-checkGroupId":"354200065260400640","name":"123","options":"是,否,有,无"},{"-checkGroupId":"354200065260400640","name":"123414","options":"否,有"}]}}]}},{"-id":"337934854582251520","name":"单机产品数据包过程检查","code":"P011-04","docType":"过程检查","conclusion":"过程检查结论","CheckGroupSet":{"CheckGroup":{"groupName":"qweqrt","CheckItemSet":{"CheckItem":{"-checkGroupId":"354182489243398144","name":"123","options":"是,否,有,无","RelatedDocumentIdSet":{"RelatedDocumentId":"354182582491164672"}}}}}},{"-id":"337934854762606592","name":"单机产品数据包技术类检查","code":"P011-05","docType":"技术类检查","CheckGroupSet":{"CheckGroup":{"groupName":"产品外观检查","CheckItemSet":{"CheckItem":[{"-checkGroupId":"350539759214645248","name":"是否平整","description":"string","checkSetVal":"string","checkConclusion":"string","questionDescription":"string","options":"是,否","selected":"是"},{"-checkGroupId":"350539759214645248","name":"本批验收产品是否一致","description":"string","checkSetVal":"string","checkConclusion":"string","questionDescription":"string","options":"是,否","selected":"是"}]}}}}]}
     * AccordItemSet : {"AccordItem":[{"fileCodeName":"文件代号","fileName":"rrrryytt","techStatus":"111","approver":"批准人111","issl":"true","conclusion":"验收结论www","description":"备注qqq","checkType":"管理文件齐套性"},{"fileCodeName":"22","fileName":"11","techStatus":"33","approver":"44","issl":"true","conclusion":"66","description":"55","checkType":"技术文件齐套性"},{"fileName":"001.xlsx","checkType":"技术文件齐套性","RelatedDocumentIdList":{"RelatedDocumentId":["350842032097570816","350842314068045824","350843123123265536","350844104649121792"]}},{"checkType":"技术文件齐套性","RelatedDocumentIdList":{"RelatedDocumentId":["350830720286175232","350830720290369536"]}},{"fileName":"string","techStatus":"技术状态","issl":"false","conclusion":"通过","checkType":"管理文件齐套性"}]}
     * checkVerd : {"-id":"337934855232368640","name":"单机产品数据包验收结论","code":"P011-06"}
     * UnresolvedSet : {"Unresolved":[{"productCode":"003","question":"问题2","confirmer":"张三1","description":"55","fileId":"0"},{"productCode":"33","question":"33","confirmer":"33","description":"33","fileId":"0"},{"productCode":"went","question":"ee","confirmer":"ee","description":"ee","fileId":"0"}]}
     * DeliveryLists : {"DeliveryList":[{"-id":"351895632832860160","isParent":"true","project":"验收依据文件","parentId":"null"},{"-id":"351895634586079232","isParent":"false","project":"合同","parentId":"351895632832860160","description":"无"},{"-id":"355898312194473984","isParent":"false","project":"任务书","parentId":"351895632832860160","description":"无"},{"-id":"355994406205403136","isParent":"false","project":"111","parentId":"351895632832860160","description":"string"}]}
     * DocumentListSet : {"Document":[{"-id":"354088813618032640","code":"string","name":"合同文件","secret":"非密","payClassify":"合同","modalCode":"string","productCodeName":"string","productCode":"string","stage":"C1","FileSet":{"File":{"name":"001.xlsx","path":"5d4bd99e66dcfc301c7a8b07.xlsx"}}},{"-id":"354171389235736576","code":"string","name":"合同文件","secret":"非密","payClassify":"合同","modalCode":"string","productCodeName":"string","productCode":"string","stage":"C1"},{"-id":"354171714798211072","code":"string","name":"合同文件","secret":"非密","payClassify":"合同","modalCode":"string","productCodeName":"string","productCode":"string","stage":"C1","FileSet":{"File":{"name":"001.xlsx","path":"5d4bd99e66dcfc301c7a8b07.xlsx"}}}]}
     */

    @XStreamAsAttribute()
    @XStreamAlias("id") //属性注解
    private String id;//数据包id
    private String name;//数据包名称
    private String code;//数据包编号
    private String type;//数据包类型
    private String description;//数据包说明
    private String responseUnit;//责任单位
    private String modalCode;//型号代号
    private String productName;//产品名称
    private String productCode;//产品代号
    private String productType;//产品类别
    private String batch;//批次
    private String creator;//创建人
    private String createTime;//创建时间
    @XStreamImplicit(itemFieldName = "checkApply")//节点注解(必须写)
    private CheckApplyBean checkApply;//验收申请单
    @XStreamImplicit(itemFieldName = "checkTask")
    private CheckTaskBean checkTask;//验收任务单
    @XStreamImplicit(itemFieldName = "ApplyItemSet")
    private ApplyItemSetBean ApplyItemSet;//验收产品集
    @XStreamImplicit(itemFieldName = "CheckFileSet")
    private CheckFileSetBean CheckFileSet;//检查单集
    @XStreamImplicit(itemFieldName = "AccordItemSet")
    private AccordItemSetBean AccordItemSet;//依据文件集
    @XStreamImplicit(itemFieldName = "checkVerd")
    private CheckVerdBean checkVerd;//验收结论
    @XStreamImplicit(itemFieldName = "UnresolvedSet")
    private UnresolvedSetBean UnresolvedSet;//验收遗留问题集
    @XStreamImplicit(itemFieldName = "DeliveryLists")
    private DeliveryListsBean DeliveryLists;//交付文档分类
    @XStreamImplicit(itemFieldName = "DocumentListSet")
    private DocumentListSetBean DocumentListSet;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponseUnit() {
        return responseUnit;
    }

    public void setResponseUnit(String responseUnit) {
        this.responseUnit = responseUnit;
    }

    public String getModalCode() {
        return modalCode;
    }

    public void setModalCode(String modalCode) {
        this.modalCode = modalCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public CheckApplyBean getCheckApply() {
        return checkApply;
    }

    public void setCheckApply(CheckApplyBean checkApply) {
        this.checkApply = checkApply;
    }

    public CheckTaskBean getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(CheckTaskBean checkTask) {
        this.checkTask = checkTask;
    }

    public ApplyItemSetBean getApplyItemSet() {
        return ApplyItemSet;
    }

    public void setApplyItemSet(ApplyItemSetBean ApplyItemSet) {
        this.ApplyItemSet = ApplyItemSet;
    }

    public CheckFileSetBean getCheckFileSet() {
        return CheckFileSet;
    }

    public void setCheckFileSet(CheckFileSetBean CheckFileSet) {
        this.CheckFileSet = CheckFileSet;
    }

    public AccordItemSetBean getAccordItemSet() {
        return AccordItemSet;
    }

    public void setAccordItemSet(AccordItemSetBean AccordItemSet) {
        this.AccordItemSet = AccordItemSet;
    }

    public CheckVerdBean getCheckVerd() {
        return checkVerd;
    }

    public void setCheckVerd(CheckVerdBean checkVerd) {
        this.checkVerd = checkVerd;
    }

    public UnresolvedSetBean getUnresolvedSet() {
        return UnresolvedSet;
    }

    public void setUnresolvedSet(UnresolvedSetBean UnresolvedSet) {
        this.UnresolvedSet = UnresolvedSet;
    }

    public DeliveryListsBean getDeliveryLists() {
        return DeliveryLists;
    }

    public void setDeliveryLists(DeliveryListsBean DeliveryLists) {
        this.DeliveryLists = DeliveryLists;
    }

    public DocumentListSetBean getDocumentListSet() {
        return DocumentListSet;
    }

    public void setDocumentListSet(DocumentListSetBean DocumentListSet) {
        this.DocumentListSet = DocumentListSet;
    }

    public static class CheckApplyBean {
        /**
         * -id : 337934853672087552
         * name : 单机产品数据包验收申请
         * code : P011-01
         * applicant : 申请人
         * applyCompany : 申请单位
         * phone : 136XXXXXXXXX
         * conclusion : 验收内部审查结论
         * description : 备注
         */

        @XStreamAsAttribute()
        @XStreamAlias("id") //属性注解
        private String id;//验收申请单id
        private String name;//名称
        private String code;//代号
        private String contractCode;//合同编号
        private String contractName;//合同名称
        private String applicant;//申请人
        private String applyCompany;//申请单位
        private String phone;//联系电话
        private String conclusion;//检查结论
        private String description;//备注


        public String getContractCode() {
            return contractCode;
        }

        public void setContractCode(String contractCode) {
            this.contractCode = contractCode;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public String getApplyCompany() {
            return applyCompany;
        }

        public void setApplyCompany(String applyCompany) {
            this.applyCompany = applyCompany;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getConclusion() {
            return conclusion;
        }

        public void setConclusion(String conclusion) {
            this.conclusion = conclusion;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class CheckTaskBean {
        /**
         * -id : 337934853944717312
         * name : 单机产品数据包验收任务单
         * code : P011-02
         * issuer : q
         * issueDept : q
         * accepter : q
         * applicant : 申请人
         * applyCompany : 申请单位
         * phone : 136XXXXXXXXX
         */

        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;//验收任务单id
        private String name;//名称
        private String code;//代号
        private String issuer;//签发人
        private String issueDept;//签发部门
        private String accepter;//产品接收人
        private String acceptDate;//产品接收时间
        private String checkDate;//产品验收时间
        private String applicant;//申请人
        private String applyCompany;//申请单位
        private String phone;//联系电话
        @XStreamImplicit(itemFieldName = "ApplyItemSet")
        private ApplyItemSetBean ApplyItemSet;//验收部门集


        public String getAcceptDate() {
            return acceptDate;
        }

        public void setAcceptDate(String acceptDate) {
            this.acceptDate = acceptDate;
        }

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public ApplyItemSetBean getApplyItemSet() {
            return ApplyItemSet;
        }

        public void setApplyItemSet(ApplyItemSetBean applyItemSet) {
            ApplyItemSet = applyItemSet;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getIssueDept() {
            return issueDept;
        }

        public void setIssueDept(String issueDept) {
            this.issueDept = issueDept;
        }

        public String getAccepter() {
            return accepter;
        }

        public void setAccepter(String accepter) {
            this.accepter = accepter;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public String getApplyCompany() {
            return applyCompany;
        }

        public void setApplyCompany(String applyCompany) {
            this.applyCompany = applyCompany;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class ApplyItemSetBean {
        /**
         * ApplyItem : {"isArmyCheck":"true","isCompleteChoice":"true","isCompleteRoutine":"true","isPureCheck":"false","isSatisfyRequire":"true","productCode":"bian-1","productCodeName":"d-12","productName":"产品1"}
         */
        @XStreamImplicit(itemFieldName = "ApplyItem")
        private ApplyItemBean ApplyItem;

        public ApplyItemBean getApplyItem() {
            return ApplyItem;
        }

        public void setApplyItem(ApplyItemBean ApplyItem) {
            this.ApplyItem = ApplyItem;
        }

        public static class ApplyItemBean {
            /**
             * isArmyCheck : true
             * isCompleteChoice : true
             * isCompleteRoutine : true
             * isPureCheck : false
             * isSatisfyRequire : true
             * productCode : bian-1
             * productCodeName : d-12
             * productName : 产品1
             */

            private String productCodeName;//产品代号
            private String productName;//产品名称
            private String productCode;//产品编号
            private String productStatus;//产品状态
            private String checkCount;//投产数据量已检收数量／验收数量
            private String isPureCheck;//是否终检
            private String isArmyCheck;//是否军检
            private String isCompleteChoice;//筛选试验是否完成
            private String isCompleteRoutine;//例行试验是否完成
            private String isSatisfyRequire;//是否具备出厂交付条件
            private String passCheck;//是否通过验收
            private String description;//备注

            public String getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(String productStatus) {
                this.productStatus = productStatus;
            }

            public String getCheckCount() {
                return checkCount;
            }

            public void setCheckCount(String checkCount) {
                this.checkCount = checkCount;
            }

            public String getPassCheck() {
                return passCheck;
            }

            public void setPassCheck(String passCheck) {
                this.passCheck = passCheck;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIsArmyCheck() {
                return isArmyCheck;
            }

            public void setIsArmyCheck(String isArmyCheck) {
                this.isArmyCheck = isArmyCheck;
            }

            public String getIsCompleteChoice() {
                return isCompleteChoice;
            }

            public void setIsCompleteChoice(String isCompleteChoice) {
                this.isCompleteChoice = isCompleteChoice;
            }

            public String getIsCompleteRoutine() {
                return isCompleteRoutine;
            }

            public void setIsCompleteRoutine(String isCompleteRoutine) {
                this.isCompleteRoutine = isCompleteRoutine;
            }

            public String getIsPureCheck() {
                return isPureCheck;
            }

            public void setIsPureCheck(String isPureCheck) {
                this.isPureCheck = isPureCheck;
            }

            public String getIsSatisfyRequire() {
                return isSatisfyRequire;
            }

            public void setIsSatisfyRequire(String isSatisfyRequire) {
                this.isSatisfyRequire = isSatisfyRequire;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductCodeName() {
                return productCodeName;
            }

            public void setProductCodeName(String productCodeName) {
                this.productCodeName = productCodeName;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }
    }

    public static class CheckFileSetBean {
        @XStreamImplicit(itemFieldName = "CheckFile")
        private List<CheckFileBean> CheckFile;

        public List<CheckFileBean> getCheckFile() {
            return CheckFile;
        }

        public void setCheckFile(List<CheckFileBean> CheckFile) {
            this.CheckFile = CheckFile;
        }

        public static class CheckFileBean {
            /**
             * -id : 337934854364147712
             * name : 单机产品数据包齐套性检查
             * code : P011-03
             * docType : 齐套性检查
             * conclusion : 齐套检查结论c
             * CheckGroupSet : {"CheckGroup":[{},{"groupName":"产品外观检查","CheckItemSet":{"CheckItem":[{"-checkGroupId":"353862513528516608","name":"string","options":"是,否","RelatedDocumentIdSet":{"RelatedDocumentId":"354086034652557312"}},{"-checkGroupId":"353862513528516608","name":"是否有摩擦","options":"是,否"}]}},{"groupName":"123","CheckItemSet":{"CheckItem":[{"-checkGroupId":"354200065260400640","name":"123","options":"是,否,有,无"},{"-checkGroupId":"354200065260400640","name":"123414","options":"否,有"}]}}]}
             */
            @XStreamAsAttribute()
            @XStreamAlias("id")
            private String id;
            private String name;
            private String code;
            private String docType;
            private String conclusion;
            @XStreamImplicit(itemFieldName = "CheckGroupSet")
            private CheckGroupSetBean CheckGroupSet;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDocType() {
                return docType;
            }

            public void setDocType(String docType) {
                this.docType = docType;
            }

            public String getConclusion() {
                return conclusion;
            }

            public void setConclusion(String conclusion) {
                this.conclusion = conclusion;
            }

            public CheckGroupSetBean getCheckGroupSet() {
                return CheckGroupSet;
            }

            public void setCheckGroupSet(CheckGroupSetBean CheckGroupSet) {
                this.CheckGroupSet = CheckGroupSet;
            }

            public static class CheckGroupSetBean {
                @XStreamImplicit(itemFieldName = "CheckGroup")
                private List<CheckGroupBean> CheckGroup;

                public List<CheckGroupBean> getCheckGroup() {
                    return CheckGroup;
                }

                public void setCheckGroup(List<CheckGroupBean> CheckGroup) {
                    this.CheckGroup = CheckGroup;
                }

                public static class CheckGroupBean {
                    /**
                     * groupName : 产品外观检查
                     * CheckItemSet : {"CheckItem":[{"-checkGroupId":"353862513528516608","name":"string","options":"是,否","RelatedDocumentIdSet":{"RelatedDocumentId":"354086034652557312"}},{"-checkGroupId":"353862513528516608","name":"是否有摩擦","options":"是,否"}]}
                     */

                    private String groupName;
                    @XStreamImplicit(itemFieldName = "CheckItemSet")
                    private CheckItemSetBean CheckItemSet;

                    public String getGroupName() {
                        return groupName;
                    }

                    public void setGroupName(String groupName) {
                        this.groupName = groupName;
                    }

                    public CheckItemSetBean getCheckItemSet() {
                        return CheckItemSet;
                    }

                    public void setCheckItemSet(CheckItemSetBean CheckItemSet) {
                        this.CheckItemSet = CheckItemSet;
                    }

                    public static class CheckItemSetBean {
                        @XStreamImplicit(itemFieldName = "CheckItem")
                        private List<CheckItemBean> CheckItem;

                        public List<CheckItemBean> getCheckItem() {
                            return CheckItem;
                        }

                        public void setCheckItem(List<CheckItemBean> CheckItem) {
                            this.CheckItem = CheckItem;
                        }

                        public static class CheckItemBean {
                            /**
                             * -checkGroupId : 353862513528516608
                             * name : string
                             * options : 是,否
                             * RelatedDocumentIdSet : {"RelatedDocumentId":"354086034652557312"}
                             */

                            @XStreamAsAttribute()
                            @XStreamAlias("checkGroupId")
                            private String checkGroupId;
                            private String name;
                            private String options;
                            @XStreamImplicit(itemFieldName = "RelatedDocumentIdSet")
                            private RelatedDocumentIdSetBean RelatedDocumentIdSet;

                            public String getCheckGroupId() {
                                return checkGroupId;
                            }

                            public void setCheckGroupId(String checkGroupId) {
                                this.checkGroupId = checkGroupId;
                            }

                            public String getName() {
                                return name;
                            }

                            public void setName(String name) {
                                this.name = name;
                            }

                            public String getOptions() {
                                return options;
                            }

                            public void setOptions(String options) {
                                this.options = options;
                            }

                            public RelatedDocumentIdSetBean getRelatedDocumentIdSet() {
                                return RelatedDocumentIdSet;
                            }

                            public void setRelatedDocumentIdSet(RelatedDocumentIdSetBean RelatedDocumentIdSet) {
                                this.RelatedDocumentIdSet = RelatedDocumentIdSet;
                            }

                            public static class RelatedDocumentIdSetBean {
                                /**
                                 * RelatedDocumentId : 354086034652557312
                                 */

                                private String RelatedDocumentId;

                                public String getRelatedDocumentId() {
                                    return RelatedDocumentId;
                                }

                                public void setRelatedDocumentId(String RelatedDocumentId) {
                                    this.RelatedDocumentId = RelatedDocumentId;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static class AccordItemSetBean {
        @XStreamImplicit(itemFieldName = "AccordItem")
        private List<AccordItemBean> AccordItem;

        public List<AccordItemBean> getAccordItem() {
            return AccordItem;
        }

        public void setAccordItem(List<AccordItemBean> AccordItem) {
            this.AccordItem = AccordItem;
        }

        public static class AccordItemBean {
            /**
             * fileCodeName : 文件代号
             * fileName : rrrryytt
             * techStatus : 111
             * approver : 批准人111
             * issl : true
             * conclusion : 验收结论www
             * description : 备注qqq
             * checkType : 管理文件齐套性
             * RelatedDocumentIdList : {"RelatedDocumentId":["350842032097570816","350842314068045824","350843123123265536","350844104649121792"]}
             */

            private String fileCodeName;
            private String fileName;
            private String techStatus;
            private String approver;
            private String issl;
            private String conclusion;
            private String description;
            private String checkType;
            @XStreamImplicit(itemFieldName = "RelatedDocumentIdList")
            private RelatedDocumentIdListBean RelatedDocumentIdList;

            public String getFileCodeName() {
                return fileCodeName;
            }

            public void setFileCodeName(String fileCodeName) {
                this.fileCodeName = fileCodeName;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getTechStatus() {
                return techStatus;
            }

            public void setTechStatus(String techStatus) {
                this.techStatus = techStatus;
            }

            public String getApprover() {
                return approver;
            }

            public void setApprover(String approver) {
                this.approver = approver;
            }

            public String getIssl() {
                return issl;
            }

            public void setIssl(String issl) {
                this.issl = issl;
            }

            public String getConclusion() {
                return conclusion;
            }

            public void setConclusion(String conclusion) {
                this.conclusion = conclusion;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCheckType() {
                return checkType;
            }

            public void setCheckType(String checkType) {
                this.checkType = checkType;
            }

            public RelatedDocumentIdListBean getRelatedDocumentIdList() {
                return RelatedDocumentIdList;
            }

            public void setRelatedDocumentIdList(RelatedDocumentIdListBean RelatedDocumentIdList) {
                this.RelatedDocumentIdList = RelatedDocumentIdList;
            }

            public static class RelatedDocumentIdListBean {
                @XStreamImplicit(itemFieldName = "RelatedDocumentId")
                private List<String> RelatedDocumentId;

                public List<String> getRelatedDocumentId() {
                    return RelatedDocumentId;
                }

                public void setRelatedDocumentId(List<String> RelatedDocumentId) {
                    this.RelatedDocumentId = RelatedDocumentId;
                }
            }
        }
    }

    public static class CheckVerdBean {
        /**
         * -id : 337934855232368640
         * name : 单机产品数据包验收结论
         * code : P011-06
         */

        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;
        private String name;
        private String code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class UnresolvedSetBean {
        @XStreamImplicit(itemFieldName = "Unresolved")
        private List<UnresolvedBean> Unresolved;

        public List<UnresolvedBean> getUnresolved() {
            return Unresolved;
        }

        public void setUnresolved(List<UnresolvedBean> Unresolved) {
            this.Unresolved = Unresolved;
        }

        public static class UnresolvedBean {
            /**
             * productCode : 003
             * question : 问题2
             * confirmer : 张三1
             * description : 55
             * fileId : 0
             */

            private String productCode;
            private String question;
            private String confirmer;
            private String description;
            private String fileId;

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getConfirmer() {
                return confirmer;
            }

            public void setConfirmer(String confirmer) {
                this.confirmer = confirmer;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFileId() {
                return fileId;
            }

            public void setFileId(String fileId) {
                this.fileId = fileId;
            }
        }
    }

    public static class DeliveryListsBean {
        @XStreamImplicit(itemFieldName = "DeliveryList")
        private List<DeliveryListBean> DeliveryList;

        public List<DeliveryListBean> getDeliveryList() {
            return DeliveryList;
        }

        public void setDeliveryList(List<DeliveryListBean> DeliveryList) {
            this.DeliveryList = DeliveryList;
        }

        public static class DeliveryListBean {
            /**
             * -id : 351895632832860160
             * isParent : true
             * project : 验收依据文件
             * parentId : null
             * description : 无
             */

            @XStreamAsAttribute()
            @XStreamAlias("id")
            private String id;
            private String isParent;
            private String project;
            private String parentId;
            private String description;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsParent() {
                return isParent;
            }

            public void setIsParent(String isParent) {
                this.isParent = isParent;
            }

            public String getProject() {
                return project;
            }

            public void setProject(String project) {
                this.project = project;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }

    public static class DocumentListSetBean {
        @XStreamImplicit(itemFieldName = "Document")
        private List<DocumentBean> Document;

        public List<DocumentBean> getDocument() {
            return Document;
        }

        public void setDocument(List<DocumentBean> Document) {
            this.Document = Document;
        }

        public static class DocumentBean {
            /**
             * -id : 354088813618032640
             * code : string
             * name : 合同文件
             * secret : 非密
             * payClassify : 合同
             * modalCode : string
             * productCodeName : string
             * productCode : string
             * stage : C1
             * FileSet : {"File":{"name":"001.xlsx","path":"5d4bd99e66dcfc301c7a8b07.xlsx"}}
             */

            @XStreamAsAttribute()
            @XStreamAlias("id")
            private String id;
            private String code;
            private String name;
            private String secret;
            private String payClassify;
            private String modalCode;
            private String productCodeName;
            private String productCode;
            private String stage;
            @XStreamImplicit(itemFieldName = "FileSet")
            private FileSetBean FileSet;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getPayClassify() {
                return payClassify;
            }

            public void setPayClassify(String payClassify) {
                this.payClassify = payClassify;
            }

            public String getModalCode() {
                return modalCode;
            }

            public void setModalCode(String modalCode) {
                this.modalCode = modalCode;
            }

            public String getProductCodeName() {
                return productCodeName;
            }

            public void setProductCodeName(String productCodeName) {
                this.productCodeName = productCodeName;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getStage() {
                return stage;
            }

            public void setStage(String stage) {
                this.stage = stage;
            }

            public FileSetBean getFileSet() {
                return FileSet;
            }

            public void setFileSet(FileSetBean FileSet) {
                this.FileSet = FileSet;
            }

            public static class FileSetBean {
                /**
                 * File : {"name":"001.xlsx","path":"5d4bd99e66dcfc301c7a8b07.xlsx"}
                 */
                @XStreamImplicit(itemFieldName = "File")
                private FileBean File;

                public FileBean getFile() {
                    return File;
                }

                public void setFile(FileBean File) {
                    this.File = File;
                }

                public static class FileBean {
                    /**
                     * name : 001.xlsx
                     * path : 5d4bd99e66dcfc301c7a8b07.xlsx
                     */

                    private String name;
                    private String path;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getPath() {
                        return path;
                    }

                    public void setPath(String path) {
                        this.path = path;
                    }
                }
            }
        }
    }
}

