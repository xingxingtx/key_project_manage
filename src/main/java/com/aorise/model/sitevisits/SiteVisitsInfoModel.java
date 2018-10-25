package com.aorise.model.sitevisits;

import com.aorise.model.base.BaseModel;
import com.aorise.model.imgfile.ImgFileModel;

import java.util.Date;
import java.util.List;

/**
 * @Author:wei.peng
 * @Desicription:开工前摸排详情表实体对象
 * @Date:Created in 2018/9/25
 * @Modified By:
 */
public class SiteVisitsInfoModel extends BaseModel{
   //项目id
    private  Integer projectId;
   //项目名称
    private String name;
    //摸排时间
    private Date  visitsTime;
    //摸排地点
    private String address;
    //摸排人数
    private String numbers;
    // 现场处理人员
    private String disposePerson;
    //0-发现  1-未发现
    private Integer isQuestion;
    //问题描述
    private String visitsQuestion;
    //现场处置措施及结果
    private String results;
    //1-进行中 2-已解决
    private String disposeState;
    //区县id
    private String county;
    //开工前摸排处理数据
    List<SiteVisitsModel> list;
    //点评数
    private Integer commentCount;
    //项目所在具体地址
    private String detailedAddr;
    //创建人姓名
    private String createName;
    //图片集合
    private List<ImgFileModel> imgList;

    public String getCounty() {
     return county;
    }

    public void setCounty(String county) {
     this.county = county;
    }

    public SiteVisitsInfoModel(){
       }

    public String getDetailedAddr() {
     return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
     this.detailedAddr = detailedAddr;
    }

    public Integer getCommentCount() {
     return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
     this.commentCount = commentCount;
    }

    public List<SiteVisitsModel> getList() {
     return list;
    }

    public void setList(List<SiteVisitsModel> list) {
     this.list = list;
    }


    public Integer getProjectId() {
     return projectId;
    }

    public void setProjectId(Integer projectId) {
     this.projectId = projectId;
    }

    public String getName() {
     return name;
    }

    public void setName(String name) {
     this.name = name;
    }

    public Date getVisitsTime() {
     return visitsTime;
    }

    public void setVisitsTime(Date visitsTime) {
     this.visitsTime = visitsTime;
    }

    public String getAddress() {
     return address;
    }

    public void setAddress(String address) {
     this.address = address;
    }

    public String getNumbers() {
     return numbers;
    }

    public void setNumbers(String numbers) {
     this.numbers = numbers;
    }

    public String getDisposePerson() {
     return disposePerson;
    }

    public void setDisposePerson(String disposePerson) {
     this.disposePerson = disposePerson;
    }

    public Integer getIsQuestion() {
     return isQuestion;
    }

    public void setIsQuestion(Integer isQuestion) {
     this.isQuestion = isQuestion;
    }

    public String getVisitsQuestion() {
     return visitsQuestion;
    }

    public void setVisitsQuestion(String visitsQuestion) {
     this.visitsQuestion = visitsQuestion;
    }

    public String getResults() {
     return results;
    }

    public void setResults(String results) {
     this.results = results;
    }

    public String getDisposeState() {
     return disposeState;
    }

    public void setDisposeState(String disposeState) {
     this.disposeState = disposeState;
    }

    public String getCreateName() {
     return createName;
    }

    public void setCreateName(String createName) {
     this.createName = createName;
    }

    public List<ImgFileModel> getImgList() {
     return imgList;
    }

    public void setImgList(List<ImgFileModel> imgList) {
     this.imgList = imgList;
    }
}

