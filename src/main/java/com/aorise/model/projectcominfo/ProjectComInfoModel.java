package com.aorise.model.projectcominfo;

/**
 * @Description:项目点评实体类
 * @Author:Huangdong
 * @Date:2018/9/25 10:22
 * @Version V1.0
 */
public class ProjectComInfoModel {
    /**主键id*/
    private Integer id;
    /**项目id*/
    private Integer projectid;
    /**点评内容*/
    private String describes;
    /**点评人*/
    private String reuser;
    /**类别 1-基本情况  2-开工摸排 3-群众上访 4-阻工 5-隐患*/
    private Integer type;
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
    /*联保行动关联id*/
    private String relateId;

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
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

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getReuser() {
        return reuser;
    }

    public void setReuser(String reuser) {
        this.reuser = reuser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
