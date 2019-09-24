package com.example.acceptance.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author :created by ${ WYW }
 * 时间：2019/9/12 17
 */
@XStreamAlias("DataPackage")
public class DataPackageBean {
    @XStreamAsAttribute() @XStreamAlias("id") //属性注解
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
    private CheckApplyBean checkApply;//验收申请单



    public static class CheckApplyBean{


    }

}
