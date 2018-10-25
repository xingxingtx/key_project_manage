package com.aorise.model.stopwork;

import com.aorise.model.base.BaseModel;
import com.aorise.model.imgfile.ImgFileModel;

import java.util.Date;
import java.util.List;

/**
 * @Author:wei.peng
 * @Desicription:施工现场阻工详情表实体对象
 * @Date:Created in 2018/9/27
 * @Modified By:
 */
public class StopWorkInfoModel extends BaseModel{
   //项目id
    private  Integer projectId;
   //项目名称
    private String name;
    private Date stopTime;//阻工时间
    private String addr;//项目阻工地点
    private String preventReason;//阻工原因
    private String alertReporter;//上报人员
    private String preventNumber;//阻工人数
    private String preventReporter;//阻工人员
    private String content;//阻工情况描述
    private String disposeResult;//现场处置措施及结果
    private Integer disposeState;//1-进行中 2-已解决
    private Integer isSatisfy;//1-满意 2-不满意
    private String cause;//原因
   //区县code
    private String county;
    //施工现场阻工处理数据
    List<StopWorkModel> list;
    //点评数
    private Integer commentCount;
    //项目所在具体地址
    private String detailedAddr;
    //创建人姓名
    private String createName;
    //其他阻工原因
    private String other;
    //图片集合
    private List<ImgFileModel> imgList;

    public String getOther() {
     return other;
    }

    public void setOther(String other) {
     this.other = other;
    }

    public Integer getIsSatisfy() {
     return isSatisfy;
    }

    public void setIsSatisfy(Integer isSatisfy) {
     this.isSatisfy = isSatisfy;
    }

    public String getCause() {
     return cause;
    }

    public void setCause(String cause) {
     this.cause = cause;
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

    public Date getStopTime() {
     return stopTime;
    }

    public void setStopTime(Date stopTime) {
     this.stopTime = stopTime;
    }

    public String getAddr() {
     return addr;
    }

    public void setAddr(String addr) {
     this.addr = addr;
    }

    public String getPreventReason() {
     return preventReason;
    }

    public void setPreventReason(String preventReason) {
     this.preventReason = preventReason;
    }

    public String getAlertReporter() {
     return alertReporter;
    }

    public void setAlertReporter(String alertReporter) {
     this.alertReporter = alertReporter;
    }

    public String getPreventNumber() {
     return preventNumber;
    }

    public void setPreventNumber(String preventNumber) {
     this.preventNumber = preventNumber;
    }

    public String getPreventReporter() {
     return preventReporter;
    }

    public void setPreventReporter(String preventReporter) {
     this.preventReporter = preventReporter;
    }

    public String getContent() {
     return content;
    }

    public void setContent(String content) {
     this.content = content;
    }

    public String getDisposeResult() {
     return disposeResult;
    }

    public void setDisposeResult(String disposeResult) {
     this.disposeResult = disposeResult;
    }

    public Integer getDisposeState() {
     return disposeState;
    }

    public void setDisposeState(Integer disposeState) {
     this.disposeState = disposeState;
    }

    public String getCounty() {
     return county;
    }

    public void setCounty(String county) {
     this.county = county;
    }

    public List<StopWorkModel> getList() {
     return list;
    }

    public void setList(List<StopWorkModel> list) {
     this.list = list;
    }

    public Integer getCommentCount() {
     return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
     this.commentCount = commentCount;
    }

    public String getDetailedAddr() {
     return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
     this.detailedAddr = detailedAddr;
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

