package com.aorise.model.jointguarantee;
/**
 * @Author:wei.peng
 * @Desicription:联保行动，提供给app调用的实体对象
 * @Date:Created in 2018/9/27
 * @Modified By:
 */
public class JointGuaranteeModel {
    //id
    private Integer id;
    //项目名称
    private String name;
    //联保行动类型
    private String type;
    //联保行动创建时间
    private String  createTime;
    //联保行动指定完成时间
    private String endTime;
    //详情处理状态1-进行中 2-已解决
    private String disposeStateInfo;
    //处理状态0-已处理 1-未处理
    private String disposeState;
    //点评数
    private Integer commentCount;
    //项目所在具体地址
    private String detailedAddr;
    //人数
    private String number;
    //是否发现问题
    private String isQuestion;
    //隐患来源
    private String source;
    //阻工原因
    private String preventReason;
    //指派人员
    private String disposePeople;

    public String getDisposeStateInfo() {
        return disposeStateInfo;
    }

    public void setDisposeStateInfo(String disposeStateInfo) {
        this.disposeStateInfo = disposeStateInfo;
    }

    public String getDisposePeople() {
        return disposePeople;
    }

    public void setDisposePeople(String disposePeople) {
        this.disposePeople = disposePeople;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisposeState() {
        return disposeState;
    }

    public void setDisposeState(String disposeState) {
        this.disposeState = disposeState;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(String isQuestion) {
        this.isQuestion = isQuestion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPreventReason() {
        return preventReason;
    }

    public void setPreventReason(String preventReason) {
        this.preventReason = preventReason;
    }
}
