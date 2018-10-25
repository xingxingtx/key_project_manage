package com.aorise.model.projectdasecase;

/**
 * @Description:联保项目地址信息实体对象
 * @Author:Huangdong
 * @Date:2018/9/20 9:58
 * @Version V1.0
 */
public class ProjectAddrInfoModel{
    /**主键id*/
    private Integer id;
    /**项目id*/
    private Integer projectid;
    /**项目所在县(区)*/
    private String county;
    private String countyName;

    /**联保联络员(县区)*/
    private String countyPerson;
    /**职务(县区)*/
    private String countyPosition;
    /**联系方式(县区)*/
    private String countyPhone;
    /**项目所在乡镇*/
    private String township;
    private String townshipName;
    /**联保联络员(乡镇)*/
    private String townshipPerson;
    /**职务(乡镇)*/
    private String townshipPosition;

    /**联系方式(乡镇)*/
    private String townshipPhone;
    /**项目所在村*/
    private String village;
    private String villageName;
    /**联保联络员(村)*/
    private String villagePerson;
    /**职务(村)*/
    private String positions;
    /**联系方式(村)*/
    private String phone;
    /**项目所在组*/
    private String progroup;
    private String progroupName;
    /**联保联络员(组)*/
    private String progroupPerson;
    /**职务(组)*/
    private String progroupPosition;
    /**联系方式(组)*/
    private String progroupPhone;
    /**项目详细地址*/
    private String detailedAddr;
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
    /**经度*/
    private String longitude;
    /**纬度*/
    private String latitude;
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


    public String getProgroupName() {
        return progroupName;
    }

    public void setProgroupName(String progroupName) {
        this.progroupName = progroupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
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

    public String getProgroup() {
        return progroup;
    }

    public void setProgroup(String progroup) {
        this.progroup = progroup;
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

    public String getDetailedAddr() {
        return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
        this.detailedAddr = detailedAddr;
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

}
