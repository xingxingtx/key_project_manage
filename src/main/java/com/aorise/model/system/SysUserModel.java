package com.aorise.model.system;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/12 10:50
 * @Modified By:
 */
public class SysUserModel implements Serializable{

    private Integer id;
    private String userName;
    private String passWord;
    private String deptId;
    private String fullName;
    private String adder;
    private String adderName;
    private String phone;
    private String groups;

    private Integer state;
    private Integer creater;
    private String createTime;
    private Integer editer;
    private String editeTime;

    private Integer unreadInform;//通知未读
    private String token;


    public Integer getUnreadInform() {
        return unreadInform;
    }

    public void setUnreadInform(Integer unreadInform) {
        this.unreadInform = unreadInform;
    }

    private List<SysRoleModel> sysRoles;

    private List<SysPermissionModel> sysPermissions;


    public List<SysPermissionModel> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(List<SysPermissionModel> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }

    public String getAdderName() {
        return adderName;
    }

    public void setAdderName(String adderName) {
        this.adderName = adderName;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getEditer() {
        return editer;
    }

    public void setEditer(Integer editer) {
        this.editer = editer;
    }

    public String getEditeTime() {
        return editeTime;
    }

    public void setEditeTime(String editeTime) {
        this.editeTime = editeTime;
    }

    public List<SysRoleModel> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRoleModel> sysRoles) {
        this.sysRoles = sysRoles;

    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
