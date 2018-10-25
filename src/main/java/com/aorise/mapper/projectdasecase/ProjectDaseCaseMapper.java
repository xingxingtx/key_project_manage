package com.aorise.mapper.projectdasecase;

import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import com.aorise.model.system.SysUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:联保基本情况持久化层
 * @Author:Huangdong
 * @Date:2018/9/20 17:17
 * @Version V1.0
 */
@Mapper
@Component
public interface ProjectDaseCaseMapper {
    /**
     * @Author:Huangdong
     * @Desicription:根据项目名称查询该项目是否存在
     * @param name 项目名称
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
    ProjectDaseCaseModel getProjectByUserName(String name)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:添加项目基本详情主表信息
     * @param projectDaseCaseModel 主表信息
     * @return Integer
     * @throws Exception
     */
    Integer addProject(ProjectDaseCaseModel projectDaseCaseModel)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:添加项目基本详情分表信息
     * @param projectAddrInfoModel 分表信息
     * @return Integer
     * @throws Exception
     */
    Integer addProjectAddr(ProjectAddrInfoModel projectAddrInfoModel)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:根据项目id查询该项目是否存在
     * @param id 项目id
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
     ProjectDaseCaseModel getProjectById(Integer id)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:编辑项目基本详情主表信息
     * @param projectDaseCaseModel 主表信息
     * @return Integer
     * @throws Exception
     */
    Integer updProject(ProjectDaseCaseModel projectDaseCaseModel)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:编辑项目基本详情分表信息
     * @param projectAddrInfoModel 分表
     * @return Integer
     * @throws Exception
     */
    Integer updProjectAddr(ProjectAddrInfoModel projectAddrInfoModel)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:删除联保项目基本情况信息
     * @return int
     * @throws Exception
     */
    Integer delProject(ProjectDaseCaseModel pdcm)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:删除联保项目地址信息
     * @return int
     * @throws Exception
     */
    Integer delProjectaddr(ProjectAddrInfoModel paim2)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:根据项目id查询项目详情
     * @param id 项目id
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
    ProjectDaseCaseModel getProject(Integer id)throws DataAccessException;

    /**
     * @Author:Huangdong
     * @Desicription:根据项目id查询项目地址详情
     * @param id 项目id
     * @return ProjectDaseCaseModel
     * @throws Exception
     */
    List<ProjectAddrInfoModel> getProjectAddr(Integer id)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Description 分页查询联保信息总条数
     * @params:  paramMap
     * @Date:2018-05-28  18:03
     * @Return:返回值
     * @Modified By:
     */
    Long queryProjectdasecaseByPageCount(Map<String, Object> paramMap)throws DataAccessException;

    /**
     * @Author:Huangdong
     * @Description App分页查询联保信息总条数
     * @params:  paramMap
     * @Date:2018-05-28  18:03
     * @Return:返回值
     * @Modified By:
     */
    Long queryProjectdasecaseByPageAppCount(Map<String, Object> paramMap)throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Description 分页查询联保信息
     * @params:  paramMap
     * @Date:2018-05-28  18:03
     * @Return:返回值
     * @Modified By:
     */
    List<ProjectDaseCaseModel> queryProjectdasecaseByPage(Map<String, Object> paramMap)throws DataAccessException;

    /**
     * @Author:Huangdong
     * @Description App分页查询联保信息
     * @params:  paramMap
     * @Date:2018-05-28  18:03
     * @Return:返回值
     * @Modified By:
     */
    List<ProjectDaseCaseModel> queryProjectdasecaseAppByPage(Map<String, Object> paramMap)throws DataAccessException;

    /**
     * 根据区县，乡镇，村庄查询项目
     * @return
     * @throws Exception
     */
    List<ProjectDaseCaseModel> getProjectList(Map<String,Object> map)throws DataAccessException;

    /**
     * @Author:ZGP
     * 插入预警员-项目详细地址中间表
     * @param porId 项目详细地址id
     * @param useId 预警员id
     * @return
     * @throws Exception
     */
    int insertWarnProject(@Param("porId") int porId,@Param("useId") int useId)throws DataAccessException;

    /**
     * @Author:ZGP
     * 通过项目地址id 删除 中间表
     * @param porId 项目地址id
     * @return
     * @throws Exception
     */
    int deleteWarnProject(@Param("porId")int porId)throws DataAccessException;
    /**
     * @Author:ZGP
     * 通过项目地址id 查询预警员列表
     * @param porId 项目地址id
     * @return
     * @throws Exception
     */
    List<SysUserModel>  findUser(@Param("porId")int porId)throws DataAccessException;

    /**
     * @Author:ZGP
     * @Description 导出项目信息Excel信息
     * @params:  paramMap
     * @Date:2018-05-28  18:03
     * @Return:返回值
     * @Modified By:
     */
    List<ProjectDaseCaseModel> queryProjectdasecaseExcel(Map<String, Object> paramMap)throws DataAccessException;

}
