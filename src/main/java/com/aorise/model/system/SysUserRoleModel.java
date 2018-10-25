package com.aorise.model.system;

/**
 * @Author:Shenzhiwei
 * @Desicription:用户角色实体类
 * @Date:Created in 2018-09-17 11:27
 * @Modified By:
 */
public class SysUserRoleModel {
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
