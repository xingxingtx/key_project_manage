package com.aorise.model.notification;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知用户范围表实体对象
 * @Date:Created in 2018-09-17 09:46
 * @Modified By:
 */
public class NotificationUserModel {
    private Integer id;
    private Integer notificationId;
    private Integer userId;
    private Integer isRead;
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
