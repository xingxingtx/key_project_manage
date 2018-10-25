package com.aorise.mapper.projectcominfo;

import com.aorise.model.projectcominfo.ProjectComInfoModel;
import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:项目点评持久化层
 * @Author:Huangdong
 * @Date:2018/9/25 10:49
 * @Version V1.0
 */
@Mapper
@Component
public interface ProjectComInfoMapper {

    Integer addProjectcomInfo(ProjectComInfoModel pc)throws DataAccessException;

    /**
     * @Author:wei.peng
     * @Desicription:根据项目id查询项目点评列表
     * @param projectId 项目id
     * @return  List<ProjectComInfoModel>
     * @throws Exception
     */
    List<ProjectComInfoModel> selectProjectcomInfo(@Param("projectId") String projectId,@Param("type")  String type,@Param("relateId") String relateId) throws DataAccessException;

    /**
     * @Author:Huangdong
     * @Desicription:通过项目ID查询项目地址
     * @param projectid 查询参数
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
    List<ProjectAddrInfoModel> getProject(Integer projectid) throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:通过项目ID查询项目名称
     * @param projectid 查询参数
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
    ProjectDaseCaseModel getProjectName(Integer projectid) throws DataAccessException;
}
