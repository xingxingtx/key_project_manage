package com.aorise.model.petitioningdis;

import com.aorise.model.base.BaseModel;
import com.aorise.model.imgfile.ImgFileModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/9/27.
 */
public class PetitioningDisInfoModel extends BaseModel{
    private Integer projectId;//项目id
    private String name;//项目名称
    private Date caseTime;//上访时间
    private String addr;//上访地点
    private String reporter;//接待人员
    private String caseNumber;//上访人数
    private String caseReporter;//上访人员
    private String content;//上访内容
    private String disposeResult;//现场处置措施及结果
    private Integer disposeState;//1-进行中 2-已解决
    private String county;//区县code

    //图片集合
    private List<ImgFileModel> imgList;
    //群众上访排处理数据
    List<PetitioningDisModel> list;
    //点评数
    private Integer commentCount;
    //项目所在具体地址
    private String detailedAddr;
    //创建人姓名
    private String createName;

    public List<PetitioningDisModel> getList() {
        return list;
    }

    public void setList(List<PetitioningDisModel> list) {
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

    public Date getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(Date caseTime) {
        this.caseTime = caseTime;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseReporter() {
        return caseReporter;
    }

    public void setCaseReporter(String caseReporter) {
        this.caseReporter = caseReporter;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String create_name) {
        this.createName = create_name;
    }

    public List<ImgFileModel> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgFileModel> imgList) {
        this.imgList = imgList;
    }
}
