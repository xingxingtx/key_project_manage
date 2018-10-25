package com.aorise.model.projectbranchinfo;

/**
 * @Description:
 * @Author:wei.peng
 * @Date:2018/9/29 19:08
 * @Version V1.0
 */
public class ProjectCountModel {
    //项目名称
    private String name;
    //项目id
    private Integer projectid;
    //项目所在经度
    private String longitude;
    //项目所在经度纬度
    private String latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
