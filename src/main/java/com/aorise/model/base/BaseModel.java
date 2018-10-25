package com.aorise.model.base;

import com.aorise.utils.define.ConstDefine;

import java.util.Date;

/**
 * @Author:wei.peng
 * @Desicription:实体对象相同属性
 * @Date:Created in 2018/9/25
 * @Modified By:
 */
public class BaseModel {
    //主键id
    private Integer id;
    //状态 1未删除 2已删除
    private Integer state = ConstDefine.STATE_ABLE;
    //创建人id
    private Integer creater;
    //创建时间
    private Date createTime;
    //修改人id
    private Integer editer;
    //修改时间
    private Date editTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEditer() {
        return editer;
    }

    public void setEditer(Integer editer) {
        this.editer = editer;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}
