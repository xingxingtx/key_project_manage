package com.aorise.service.projectdasecase;

import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import com.aorise.utils.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description:联保项目基本情况Service层
 * @Author:Huangdong
 * @Date:2018/9/20 16:59
 * @Version V1.0
 */
public interface ProjectDaseCaseService {
    /**
     * @Author:Huangdong
     * @Desicription:新增联保项目基本情况信息
     * @param pdcm 联保项目基本情况实体
     * @param list 联保项目基本情况地址集合
     * @return int
     * @throws Exception
     */
    public int addProject(ProjectDaseCaseModel pdcm, List<ProjectAddrInfoModel> list,String url)throws Exception;
    /**
     * @Author:Huangdong
     * @Desicription:编辑联保项目基本情况信息
     * @param pdcm 联保项目基本情况实体
     * @param list 联保项目基本情况地址集合
     * @return int
     * @throws Exception
     */
    public int updProject(ProjectDaseCaseModel pdcm, List<ProjectAddrInfoModel> list,String url)throws Exception;
    /**
     * @Author:Huangdong
     * @Desicription:删除联保项目基本情况信息
     * @param pdcm 联保项目基本情况实体
     * @param paim2 联保项目基本情况地址集合
     * @return int
     * @throws Exception
     */
    public int delProject(ProjectDaseCaseModel pdcm, ProjectAddrInfoModel paim2)throws Exception;
    /**
     * @Author:Huangdong
     * @Desicription:联保项目基本详情查询
     * @param id 联保项目id
     * @return int
     * @throws Exception
     */
    public ProjectDaseCaseModel getProject(Integer id)throws Exception;
    /**
     * @Author:Huangdong
     * @Description：分页查询联保项目基本信息
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    List<ProjectDaseCaseModel> queryProjectdasecaseByPage(Map<String, Object> paramMap, Page page)throws Exception;

    /**
     * @Author:Huangdong
     * @Description：App分页查询联保项目基本信息
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    List<ProjectDaseCaseModel> queryProjectdasecaseAppByPage(Map<String, Object> paramMap, Page page)throws Exception;

    /**
     * 根据区县，乡镇，村庄查询项目
     * @return
     * @throws Exception
     */
    List<ProjectDaseCaseModel> getProjectList(Map<String,Object> map)throws Exception;

    /**
     * @Author:ZGP
     * @Description：导出Excel数据查询
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    String queryProjectdasecaseExcel(Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request)throws Exception;


}
