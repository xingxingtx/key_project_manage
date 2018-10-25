package com.aorise.model.petitioningdis;

import com.aorise.model.base.BaseModel;
import com.aorise.model.imgfile.ImgFileModel;

import java.util.Date;
import java.util.List;

/**
 * @Author:wei.peng
 * @Desicription:群众上访处理表实体对象
 * @Date:Created in 2018/9/27
 * @Modified By:
 */
public class PetitioningDisModel extends BaseModel{
    //群众上访详情表id
    private Integer petitioningid;
    //指派处理人员
    private String disposePeople;
    //指定完成时间
    private Date fulfillTime;
    //完成要求
    private String fulfillRequire;
    //处理时间
    private Date disposeTime;
    //现场处置措施及结果
    private String appointedResults;
    //处理人姓名
    private String createName;
    //处理状态1-进行中 2-已解决
    private String disposeState;
    //图片集合
    private List<ImgFileModel> imgList;

    public String getDisposeState() {
        return disposeState;
    }

    public void setDisposeState(String disposeState) {
        this.disposeState = disposeState;
    }
    public Integer getPetitioningid() {
        return petitioningid;
    }

    public void setPetitioningid(Integer petitioningid) {
        this.petitioningid = petitioningid;
    }

    public String getDisposePeople() {
        return disposePeople;
    }

    public void setDisposePeople(String disposePeople) {
        this.disposePeople = disposePeople;
    }

    public Date getFulfillTime() {
        return fulfillTime;
    }

    public void setFulfillTime(Date fulfillTime) {
        this.fulfillTime = fulfillTime;
    }

    public String getFulfillRequire() {
        return fulfillRequire;
    }

    public void setFulfillRequire(String fulfillRequire) {
        this.fulfillRequire = fulfillRequire;
    }

    public Date getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Date disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getAppointedResults() {
        return appointedResults;
    }

    public void setAppointedResults(String appointedResults) {
        this.appointedResults = appointedResults;
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
