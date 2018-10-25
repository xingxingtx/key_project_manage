package com.aorise.model.common;

/**
 * @Author:Shenzhiwei
 * @Desicription:地区实体
 * @Date:Created in 2018-09-27 09:26
 * @Modified By:
 */
public class AreaInfoModel {

    private Integer id;
    private String name;
    private String areaCode;
    private String parentCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
