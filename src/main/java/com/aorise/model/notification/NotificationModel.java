package com.aorise.model.notification;

import com.aorise.model.system.SysUserModel;

import java.util.List;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知实体对象表
 * @Date:Created in 2018-09-17 09:32
 * @Modified By:
 */
public class NotificationModel {
    private Integer id;
    private String name;
    private Integer scope;
    private String title;
    private Integer type;
    private String content;
    private Integer state;
    private Integer creater;
    private String createTime;
    private Integer editer;
    private String editTime;
    private String fullName;
    private String groupIds;
    private Integer isRead;


    private List<SysUserModel> usersList;

    public List<SysUserModel> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<SysUserModel> usersList) {
        this.usersList = usersList;
    }

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

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Integer getEditer() {
        return editer;
    }

    public void setEditer(Integer editer) {
        this.editer = editer;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
