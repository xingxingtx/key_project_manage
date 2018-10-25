package com.aorise.model.projectdasecase;

import com.aorise.model.system.SysUserModel;

import java.util.List;

/**
 * @Description:联保项目基本情况表实体对象
 * @Author:Huangdong
 * @Date:2018/9/17 16:55
 * @Version V1.0
 */
public class ProjectDaseCaseModel {
    /**主键id*/
    private Integer id;
    /**用户id*/
    private Integer userid;
    /**项目名称*/
    private String name;
    /**项目类别*/
    private Integer type;
    /**立项时间*/
    private String setTime;
    /**项目总投资金额*/
    private Double sumMoney;
    /**已完成金额*/
    private Double finishMoney;
    /**项目进度*/
    private String progress;
    /** 项目状态 1-未开工 2-已开工 3-已暂停 4-已中止 5-已完工*/
    private Integer projectState;
    /**开工时间*/
    private String openTime;
    /**计划完成时间*/
    private String completedTime;
    /**1-跨区县 2-不跨区县*/
    private Integer isSkip;
    /**项目简介*/
    private String projectContent;
    /**备注*/
    private String content;
    /**状态*/
    private Integer state;
    /**创建人id*/
    private String creater;
    /**创建时间*/
    private String createTime;
    /**修改人id*/
    private String editer;
    /**修改时间*/
    private String editTime;
    /**联保项目地址信息集合*/
    private List<ProjectAddrInfoModel> projectAddrInfoModel;
    /**是否超时*/
    private Integer isOvertime ;
    /**评论总条数*/
    private String commentCount;
    /**地址*/
    private String detailedAddr;

    private List<SysUserModel> userModels;//预警员列表


    //用于导出excel 字段
    private String policeName1;
    private String policePhone1;
    private String policeName2;
    private String policePhone2;
    private String policeName3;
    private String policePhone3;

    private String countyName;
    private String townshipName;
    private String villageName;
    private String progroup;
    private String countyPerson;
    private String countyPosition;
    private String countyPhone;
    /**联保联络员(乡镇)*/
    private String townshipPerson;
    /**职务(乡镇)*/
    private String townshipPosition;
    /**联系方式(乡镇)*/
    private String townshipPhone;
    /**联保联络员(村)*/
    private String villagePerson;
    /**职务(村)*/
    private String positions;
    /**联系方式(村)*/
    private String phone;
    /**联保联络员(组)*/
    private String progroupPerson;
    /**职务(组)*/
    private String progroupPosition;
    /**联系方式(组)*/
    private String progroupPhone;
    /**项目详细地址*/
    private String pdetailedAddr;
    /**经度*/
    private String longitude;
    /**纬度*/
    private String latitude;
    /**所在辖区派出所*/
    private String policeStation;
    /**派出所负责人*/
    private String leader;
    /**派出所负责人职务*/
    private String leaderPosition;
    /**派出所负责人电话*/
    private String stationPhone;
    /**项目业主单位*/
    private String ownerCorp;
    /**项目业主联系人*/
    private String ownerContact;
    /**项目业主联系人职务*/
    private String ownerPosition;
    /**项目业主联系人电话*/
    private String ownerPhone;
    /**施工单位*/
    private String constructionOrg;
    /**施工单位联系人*/
    private String constructionContact;
    /**施工单位联系人职务*/
    private String constructionPosition;
    /**施工单位联系人电话*/
    private String constructionPhone;

    public String getPoliceName1() {
        return policeName1;
    }

    public void setPoliceName1(String policeName1) {
        this.policeName1 = policeName1;
    }

    public String getPolicePhone1() {
        return policePhone1;
    }

    public void setPolicePhone1(String policePhone1) {
        this.policePhone1 = policePhone1;
    }

    public String getPoliceName2() {
        return policeName2;
    }

    public void setPoliceName2(String policeName2) {
        this.policeName2 = policeName2;
    }

    public String getPolicePhone2() {
        return policePhone2;
    }

    public void setPolicePhone2(String policePhone2) {
        this.policePhone2 = policePhone2;
    }

    public String getPoliceName3() {
        return policeName3;
    }

    public void setPoliceName3(String policeName3) {
        this.policeName3 = policeName3;
    }

    public String getPolicePhone3() {
        return policePhone3;
    }

    public void setPolicePhone3(String policePhone3) {
        this.policePhone3 = policePhone3;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipName) {
        this.townshipName = townshipName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getProgroup() {
        return progroup;
    }

    public void setProgroup(String progroup) {
        this.progroup = progroup;
    }

    public String getCountyPerson() {
        return countyPerson;
    }

    public void setCountyPerson(String countyPerson) {
        this.countyPerson = countyPerson;
    }

    public String getCountyPosition() {
        return countyPosition;
    }

    public void setCountyPosition(String countyPosition) {
        this.countyPosition = countyPosition;
    }

    public String getCountyPhone() {
        return countyPhone;
    }

    public void setCountyPhone(String countyPhone) {
        this.countyPhone = countyPhone;
    }

    public String getTownshipPerson() {
        return townshipPerson;
    }

    public void setTownshipPerson(String townshipPerson) {
        this.townshipPerson = townshipPerson;
    }

    public String getTownshipPosition() {
        return townshipPosition;
    }

    public void setTownshipPosition(String townshipPosition) {
        this.townshipPosition = townshipPosition;
    }

    public String getTownshipPhone() {
        return townshipPhone;
    }

    public void setTownshipPhone(String townshipPhone) {
        this.townshipPhone = townshipPhone;
    }

    public String getVillagePerson() {
        return villagePerson;
    }

    public void setVillagePerson(String villagePerson) {
        this.villagePerson = villagePerson;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProgroupPerson() {
        return progroupPerson;
    }

    public void setProgroupPerson(String progroupPerson) {
        this.progroupPerson = progroupPerson;
    }

    public String getProgroupPosition() {
        return progroupPosition;
    }

    public void setProgroupPosition(String progroupPosition) {
        this.progroupPosition = progroupPosition;
    }

    public String getProgroupPhone() {
        return progroupPhone;
    }

    public void setProgroupPhone(String progroupPhone) {
        this.progroupPhone = progroupPhone;
    }

    public String getPdetailedAddr() {
        return pdetailedAddr;
    }

    public void setPdetailedAddr(String pdetailedAddr) {
        this.pdetailedAddr = pdetailedAddr;
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

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderPosition() {
        return leaderPosition;
    }

    public void setLeaderPosition(String leaderPosition) {
        this.leaderPosition = leaderPosition;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getOwnerCorp() {
        return ownerCorp;
    }

    public void setOwnerCorp(String ownerCorp) {
        this.ownerCorp = ownerCorp;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerPosition() {
        return ownerPosition;
    }

    public void setOwnerPosition(String ownerPosition) {
        this.ownerPosition = ownerPosition;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getConstructionOrg() {
        return constructionOrg;
    }

    public void setConstructionOrg(String constructionOrg) {
        this.constructionOrg = constructionOrg;
    }

    public String getConstructionContact() {
        return constructionContact;
    }

    public void setConstructionContact(String constructionContact) {
        this.constructionContact = constructionContact;
    }

    public String getConstructionPosition() {
        return constructionPosition;
    }

    public void setConstructionPosition(String constructionPosition) {
        this.constructionPosition = constructionPosition;
    }

    public String getConstructionPhone() {
        return constructionPhone;
    }

    public void setConstructionPhone(String constructionPhone) {
        this.constructionPhone = constructionPhone;
    }



    public List<SysUserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<SysUserModel> userModels) {
        this.userModels = userModels;
    }
    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDetailedAddr() {
        return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
        this.detailedAddr = detailedAddr;
    }

    public Integer getIsOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(Integer isOvertime) {
        this.isOvertime = isOvertime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Double getFinishMoney() {
        return finishMoney;
    }

    public void setFinishMoney(Double finishMoney) {
        this.finishMoney = finishMoney;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getProjectState() {
        return projectState;
    }

    public void setProjectState(Integer projectState) {
        this.projectState = projectState;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public Integer getIsSkip() {
        return isSkip;
    }

    public void setIsSkip(Integer isSkip) {
        this.isSkip = isSkip;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditer() {
        return editer;
    }

    public void setEditer(String editer) {
        this.editer = editer;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public List<ProjectAddrInfoModel> getProjectAddrInfoModel() {
        return projectAddrInfoModel;
    }

    public void setProjectAddrInfoModel(List<ProjectAddrInfoModel> projectAddrInfoModel) {
        this.projectAddrInfoModel = projectAddrInfoModel;
    }
}
