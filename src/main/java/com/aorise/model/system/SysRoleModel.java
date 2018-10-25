package com.aorise.model.system;
import java.io.Serializable;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/12 10:51
 * @Modified By:
 */
public class SysRoleModel implements Serializable {

    private Integer id;
    private String name;
    private String describes;
    private Integer state;
    private Integer creater;
    private String createTime;
    private String editTime;
    private Integer editer;

    private Long count;

    private List<SysPermissionModel> sysPermissions;

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

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
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

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public Integer getEditer() {
        return editer;
    }

    public void setEditer(Integer editer) {
        this.editer = editer;
    }

    public List<SysPermissionModel> getSysPermissions() {
        return sysPermissions;
    }

    public void setSysPermissions(List<SysPermissionModel> sysPermissions) {
        this.sysPermissions = sysPermissions;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}
