package com.aorise.service.projectcominfo;

import com.aorise.model.projectcominfo.ProjectComInfoModel;

import java.util.List;

/**
 * @Description:点评项目Service层
 * @Author:Huangdong
 * @Date:2018/9/25 10:46
 * @Version V1.0
 */
public interface ProjectComInfoService {
    /**
     * @Author:Huangdong
     * @Desicription:新增项目点评
     * @param pc 项目点评实体
     * @return int
     * @throws Exception
     */
   public int addProjectcomInfo(ProjectComInfoModel pc,String url,String userid)throws Exception;
    /**
     * @Author:wei.peng
     * @Desicription:根据项目id查询项目点评列表
     * @param projectId 项目id
     * @return  List<ProjectComInfoModel>
     * @throws Exception
     */
    List<ProjectComInfoModel> selectProjectcomInfo(String projectId,String type,String relateId);
}
