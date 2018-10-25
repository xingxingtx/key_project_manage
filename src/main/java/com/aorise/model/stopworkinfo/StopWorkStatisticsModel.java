package com.aorise.model.stopworkinfo;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/9/27 16:00
 * @Version V1.0
 */
public class StopWorkStatisticsModel {

    private String key;
    private Integer value;

    private Integer visits;
    private Integer work;
    private Integer hidden;
    private Integer petition;
    private Integer other;

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getWork() {
        return work;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getPetition() {
        return petition;
    }

    public void setPetition(Integer petition) {
        this.petition = petition;
    }
}
