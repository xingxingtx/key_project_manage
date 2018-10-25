package com.aorise.model.statistic;

/**
 * @Author:Shenzhiwei
 * @Desicription:统计分析键值对实体对象
 * @Date:Created in 2018-09-25 09:47
 * @Modified By:
 */
public class StatisticModel {

    private String key;
    private Integer value;

    private Integer visits;
    private Integer work;
    private Integer hidden;
    private Integer petition;


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
