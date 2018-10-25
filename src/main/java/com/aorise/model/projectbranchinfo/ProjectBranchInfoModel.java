package com.aorise.model.projectbranchinfo;

import java.util.List;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/9/26 19:08
 * @Version V1.0
 */
public class ProjectBranchInfoModel {
    /**项目总数*/
    private String projectSum;
    /**总投资额*/
    private String sumMoney;
    /**开工数*/
    private String openSum;
    /**未开工数*/
    private String notOpenSum;
    /**开工率*/
    private String openSumRate;
/*项目状态1-未开工 2-已开工 3-已暂停 4-已中止 5-已完工*/
    private Integer prjectState;

    List<ProjectCountModel> list;

    public Integer getPrjectState() {
        return prjectState;
    }

    public void setPrjectState(Integer prjectState) {
        this.prjectState = prjectState;
    }

    public List<ProjectCountModel> getList() {
        return list;
    }

    public void setList(List<ProjectCountModel> list) {
        this.list = list;
    }

    public String getProjectSum() {
        return projectSum;
    }

    public void setProjectSum(String projectSum) {
        this.projectSum = projectSum;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getOpenSum() {
        return openSum;
    }

    public void setOpenSum(String openSum) {
        this.openSum = openSum;
    }

    public String getNotOpenSum() {
        return notOpenSum;
    }

    public void setNotOpenSum(String notOpenSum) {
        this.notOpenSum = notOpenSum;
    }

    public String getOpenSumRate() {
        return openSumRate;
    }

    public void setOpenSumRate(String openSumRate) {
        this.openSumRate = openSumRate;
    }
}
