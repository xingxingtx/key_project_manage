package com.aorise.model.constructionhidden;

import com.aorise.model.base.BaseModel;
import com.aorise.model.imgfile.ImgFileModel;

import java.util.Date;
import java.util.List;

/**
 * @Author:wei.peng
 * @Desicription:施工过程中隐患详情表实体对象
 * @Date:Created in 2018/9/27
 * @Modified By:
 */
public class ConstructionHiddenInfoModel extends BaseModel{
   //项目id
    private  Integer projectId;
   //项目名称
    private String name;
    //隐患发现时间
    private Date  hiddenTime;
    //隐患发现地点
    private String hiddenAddress;
    //隐患来源
    private Integer source;
   //隐患描述
    private String content;
   //隐患上报及处置人员
    private String caseReporter;
    //现场处置措施及结果
    private String disposeResult;
   //处理状态1-进行中 2-已解决
    private String disposeState;
   //联系方式
   private String phone;
    //区县code
    private String county;
    //开工前摸排处理数据
    List<ConstructionHiddenModel> list;
    //点评数
    private Integer commentCount;
    //项目所在具体地址
    private String detailedAddr;
    //创建人姓名
    private String createName;
    //图片集合
    private List<ImgFileModel> imgList;


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

    public Date getHiddenTime() {
     return hiddenTime;
    }

    public void setHiddenTime(Date hiddenTime) {
     this.hiddenTime = hiddenTime;
    }

    public String getHiddenAddress() {
     return hiddenAddress;
    }

    public void setHiddenAddress(String hiddenAddress) {
     this.hiddenAddress = hiddenAddress;
    }

    public Integer getSource() {
     return source;
    }

    public void setSource(Integer source) {
     this.source = source;
    }

    public String getContent() {
     return content;
    }

    public void setContent(String content) {
     this.content = content;
    }

    public String getCaseReporter() {
     return caseReporter;
    }

    public void setCaseReporter(String caseReporter) {
     this.caseReporter = caseReporter;
    }

    public String getDisposeResult() {
     return disposeResult;
    }

    public void setDisposeResult(String disposeResult) {
     this.disposeResult = disposeResult;
    }

    public String getDisposeState() {
     return disposeState;
    }

    public void setDisposeState(String disposeState) {
     this.disposeState = disposeState;
    }

    public String getPhone() {
     return phone;
    }

    public void setPhone(String phone) {
     this.phone = phone;
    }

    public String getCounty() {
     return county;
    }

    public void setCounty(String county) {
     this.county = county;
    }

    public List<ConstructionHiddenModel> getList() {
     return list;
    }

    public void setList(List<ConstructionHiddenModel> list) {
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

