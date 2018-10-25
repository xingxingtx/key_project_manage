package com.aorise.service.projectdasecase.impl;

import com.alibaba.fastjson.JSONArray;
import com.aorise.config.Config;
import com.aorise.config.FileuploadSetting;
import com.aorise.exceptions.ProjectException;
import com.aorise.mapper.projectdasecase.ProjectDaseCaseMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.service.projectdasecase.ProjectDaseCaseService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.excel.ExcelUtil;
import com.aorise.utils.page.Page;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:联保项目基本情况Service实现层
 * @Author:Huangdong
 * @Date:2018/9/20 16:59
 * @Version V1.0
 */
@Service
@Transactional
public class ProjectDaseCaseServiceImpl implements ProjectDaseCaseService {

    @Autowired
    private ProjectDaseCaseMapper projectDaseCaseMapper;

    @Autowired
    private FileuploadSetting fileuploadSetting;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @param list 添加数据
     * @return int
     * @throws Exception
     * @Author:Huangdong
     * @Desicription:新增联保项目基本情况信息
     */
    @Override
    public int addProject(ProjectDaseCaseModel pdcm, List<ProjectAddrInfoModel> list, String url) throws Exception {
        /**根据项目名称查询该项目是否存在*/
        ProjectDaseCaseModel pdcms = projectDaseCaseMapper.getProjectByUserName(pdcm.getName());
        if (pdcms != null) {
            throw new ProjectException("该项目已存在！");
        }
        int j = 0;
        /**保存主表数据*/
        j = projectDaseCaseMapper.addProject(pdcm);

        if (pdcm.getUserModels() != null) {
            for (SysUserModel userModel : pdcm.getUserModels()) {//插入预警员项目地址中间表
                projectDaseCaseMapper.insertWarnProject(pdcm.getId(), userModel.getId());
            }
        }
        Integer id = pdcm.getId();
        /**保存分表数据*/
        for (int i = 0; i < list.size(); i++) {
            ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
            projectAddrInfoModel.setProjectid(id);
            j = projectDaseCaseMapper.addProjectAddr(projectAddrInfoModel);

        }

        if (j > 0) {
            //获取当前用户
            Integer userid = ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId();
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            //获取当前地址
            for (int i = 0; i < list.size(); i++) {
                ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
                String adder = projectAddrInfoModel.getCounty();

                List<SysUserModel> userListByAddrXz = sysUserMapper.findUserListByAddrXz(adder);
                StringBuilder sb = new StringBuilder("");
                if (userListByAddrXz != null) {
                    for (int k = 0; k < userListByAddrXz.size(); k++) {
                        if (k >= userListByAddrXz.size() - 1) {
                            sb.append(userListByAddrXz.get(k).getId());
                        } else {
                            sb.append(userListByAddrXz.get(k).getId() + ",");
                        }
                    }
                }

                nModel.setContent(url + "api/projectdasecase/id/" + pdcm.getId());
                nModel.setTitle(pdcm.getName() + "项目新增通知");
                nModel.setCreater(userid);
                nModel.setType(1);//系统通知
                nModel.setGroupIds(sb.toString());
                nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                nModel.setState(ConstDefine.STATE_ABLE);
                notificationService.insertObject(nModel);

            }
        }
        return j;
    }

    /**
     * @param pdcm 联保项目基本情况实体
     * @param list 联保项目基本情况地址集合
     * @return int
     * @throws Exception
     * @Author:Huangdong
     * @Desicription:编辑联保项目基本情况信息
     */
    @Override
    public int updProject(ProjectDaseCaseModel pdcm, List<ProjectAddrInfoModel> list, String url) throws Exception {
        /**根据项目名称查询该项目是否存在*/
        ProjectDaseCaseModel pdcms = projectDaseCaseMapper.getProjectByUserName(pdcm.getName());
        if (pdcms != null && (int) pdcm.getId() != (int) pdcms.getId()) {
            throw new ProjectException("该项目已存在！");
        }
        /**根据项目id查询该项目是否存在*/
        ProjectDaseCaseModel pd = projectDaseCaseMapper.getProjectById(pdcm.getId());
        if (pd == null) {
            throw new ProjectException("该项目不存在！");
        }
        int j = 0;
        /**编辑主表数据*/
        j = projectDaseCaseMapper.updProject(pdcm);
        projectDaseCaseMapper.deleteWarnProject(pdcm.getId());//先删除中间表数据
        if (pdcm.getUserModels() != null) {
            for (SysUserModel userModel : pdcm.getUserModels()) {//插入预警员项目地址中间表
                projectDaseCaseMapper.insertWarnProject(pdcm.getId(), userModel.getId());
            }
        }
        /**编辑分表数据*/
        for (int i = 0; i < list.size(); i++) {
            ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
            j = projectDaseCaseMapper.updProjectAddr(projectAddrInfoModel);
        }

        //如果项目状态发生改变，就插入一条通知
        if (pd != null && (int) pdcm.getProjectState() != (int) pd.getProjectState()) {
            Integer userid = ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId();
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            //获取当前地址
            for (int i = 0; i < list.size(); i++) {
                ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
                String adder = projectAddrInfoModel.getCounty();

                List<SysUserModel> userListByAddrXz = sysUserMapper.findUserListByAddrXz(adder);
                StringBuilder sb = new StringBuilder("");
                if (userListByAddrXz != null) {
                    for (int k = 0; k < userListByAddrXz.size(); k++) {
                        if (k >= userListByAddrXz.size() - 1) {
                            sb.append(userListByAddrXz.get(k).getId());
                        } else {
                            sb.append(userListByAddrXz.get(k).getId() + ",");
                        }
                    }
                }

                nModel.setContent(url + "api/projectdasecase/id/" + pdcm.getId());
                nModel.setTitle(pdcm.getName() + "项目状态发生改变通知");
                nModel.setCreater(userid);
                nModel.setType(1);//系统通知
                nModel.setGroupIds(sb.toString());
                nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                nModel.setState(ConstDefine.STATE_ABLE);
                notificationService.insertObject(nModel);

            }

        }

        //如果项目进度发生改变，就插入一条通知
        /*if (pd != null && pdcm.getProgress() != pd.getProgress()) {*/
        if (pd != null && pdcm.getProgress().equals(pd.getProgress())) {
            Integer userid = ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId();
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            //获取当前地址
            for (int i = 0; i < list.size(); i++) {
                ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
                String adder = projectAddrInfoModel.getCounty();

                List<SysUserModel> userListByAddrXz = sysUserMapper.findUserListByAddrXz(adder);
                StringBuilder sb = new StringBuilder("");
                if (userListByAddrXz != null) {
                    for (int k = 0; k < userListByAddrXz.size(); k++) {
                        if (k >= userListByAddrXz.size() - 1) {
                            sb.append(userListByAddrXz.get(k).getId());
                        } else {
                            sb.append(userListByAddrXz.get(k).getId() + ",");
                        }
                    }
                }
                nModel.setContent(url + "api/projectdasecase/id/" + pdcm.getId());
                nModel.setTitle(pdcm.getName() + "项目进度发生改变通知");
                nModel.setCreater(userid);
                nModel.setType(1);//系统通知
                nModel.setGroupIds(sb.toString());
                nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                nModel.setState(ConstDefine.STATE_ABLE);
                notificationService.insertObject(nModel);

            }

        }
        return j;
    }

    /**
     * @param pdcm  联保项目基本情况实体
     * @param paim2 联保项目基本情况地址集合
     * @return int
     * @throws Exception
     * @Author:Huangdong
     * @Desicription:删除联保项目基本情况信息
     */
    @Override
    @Transactional
    public int delProject(ProjectDaseCaseModel pdcm, ProjectAddrInfoModel paim2) throws Exception {
        int ret = 0;
        String id = pdcm.getId() + "";
        String[] ids = id.split(",");
        for (String s : ids) {
            if (!Strings.isBlank(s)) {
                projectDaseCaseMapper.delProject(pdcm);
                projectDaseCaseMapper.delProjectaddr(paim2);
                ret++;
            }
        }
        return ret;
    }

    /**
     * @param id 联保项目id
     * @return int
     * @throws Exception
     * @Author:Huangdong
     * @Desicription:联保项目基本详情查询
     */
    @Override
    public ProjectDaseCaseModel getProject(Integer id) throws Exception {
        ProjectDaseCaseModel project = projectDaseCaseMapper.getProject(id);
        List<ProjectAddrInfoModel> model = projectDaseCaseMapper.getProjectAddr(id);
        if (project != null) {
            project.setUserModels(projectDaseCaseMapper.findUser(project.getId()));

        }
        project.setProjectAddrInfoModel(model);
        return project;
    }

    /**
     * @Author:Huangdong
     * @Description：分页查询联保项目基本信息
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    @Override
    public List<ProjectDaseCaseModel> queryProjectdasecaseByPage(Map<String, Object> paramMap, Page page) throws Exception {
        List<ProjectDaseCaseModel> list = null;
        /**查询总数*/
        Long count = projectDaseCaseMapper.queryProjectdasecaseByPageCount(paramMap);

        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = projectDaseCaseMapper.queryProjectdasecaseByPage(paramMap);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }

    /**
     * @Author:Huangdong
     * @Description：App客户端分页查询联保项目基本信息
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    @Override
    public List<ProjectDaseCaseModel> queryProjectdasecaseAppByPage(Map<String, Object> paramMap, Page page) throws Exception {
        List<ProjectDaseCaseModel> list = null;
        /**查询总数*/
        Long count = projectDaseCaseMapper.queryProjectdasecaseByPageAppCount(paramMap);

        if (count > 0) {
            page.setTotalCount(Long.valueOf(count));
            page.setTotalPage(Long.valueOf(count), page.getEveryPage());
            if (page.getCurrentPage() > 0 && page.getEveryPage() > 0) {
                /**查询分页条数*/
                list = projectDaseCaseMapper.queryProjectdasecaseAppByPage(paramMap);
            }
        } else {
            page.setTotalCount(0L);
        }
        return list;
    }

    /**
     * 根据区县，乡镇，村庄查询项目
     *
     * @return
     * @throws Exception
     */
    public List<ProjectDaseCaseModel> getProjectList(Map<String,Object> map) throws Exception {
        return projectDaseCaseMapper.getProjectList(map);
    }

    /**
     * @Author:ZGP
     * @Description：导出Excel
     * @params: paramMap 查询条件
     * @Return: List<ProjectDaseCaseModel> 联保项目信息集合
     * @Modified By:
     */
    public String queryProjectdasecaseExcel(Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<ProjectDaseCaseModel> list = null;
        list = projectDaseCaseMapper.queryProjectdasecaseExcel(paramMap);
        if (list != null) {

            for (ProjectDaseCaseModel proModel : list) {
                if (proModel != null) {
                    proModel.setUserModels(projectDaseCaseMapper.findUser(proModel.getId()));
                }
                List<ProjectAddrInfoModel> model = projectDaseCaseMapper.getProjectAddr(proModel.getId());
                proModel.setProjectAddrInfoModel(model);
            }
        }

        String result = "";
        //拼接表头
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("name", "项目名称");
        headMap.put("type", "项目类别");
        headMap.put("setTime", "立项时间");
        headMap.put("sumMoney", "项目投资总金额（元）");
        headMap.put("finishMoney", "已完成金额（元）");
        headMap.put("projectState", "项目状态");
        headMap.put("openTime", "开工时间");
        headMap.put("completedTime", "计划完工时间");
        headMap.put("isSkip", "是否跨区县");
        headMap.put("projectContent", "项目简介");
        headMap.put("content", "备注");
        headMap.put("policeName1", "1号预警员姓名");
        headMap.put("policePhone1", "1号预警员手机号");
        headMap.put("policeName2", "2号预警员姓名");
        headMap.put("policePhone2", "2号预警员手机号");
        headMap.put("policeName3", "3号预警员姓名");
        headMap.put("policePhone3", "3号预警员手机号");

        headMap.put("countyName", "项目所在区县");
        headMap.put("townshipName", "项目所在乡镇");
        headMap.put("villageName", "项目所在村");
        headMap.put("progroup", "项目所在组");
        headMap.put("countyPerson", "联保联络员（联保办）");
        headMap.put("countyPosition", "职务");
        headMap.put("countyPhone", "手机号");
        headMap.put("townshipPerson", "联保联络员（乡、镇）");
        headMap.put("townshipPosition", "职务");
        headMap.put("townshipPhone", "手机号");
        headMap.put("villagePerson", "联保联络员（村）");
        headMap.put("positions", "职务");
        headMap.put("phone", "手机号");
        headMap.put("progroupPerson", "联保联络员（组）");
        headMap.put("progroupPosition", "职务");
        headMap.put("progroupPhone", "手机号");
        headMap.put("pdetailedAddr", "项目详细地址");
        headMap.put("longitude", "项目所在地经度");
        headMap.put("latitude", "项目所在地纬度");
        headMap.put("policeStation", "所在辖区派出所");
        headMap.put("leader", "负责人");
        headMap.put("leaderPosition", "职务");
        headMap.put("stationPhone", "手机号");
        headMap.put("ownerCorp", "项目业主单位");
        headMap.put("ownerContact", "项目业主联系人");
        headMap.put("ownerPosition", "职务");
        headMap.put("ownerPhone", "手机号");
        headMap.put("constructionOrg", "施工单位");
        headMap.put("constructionContact", "施工单位联系人");
        headMap.put("constructionPosition", "职务");
        headMap.put("constructionPhone", "手机号");


        /**填充数据*/
        JSONArray ja = new JSONArray();
        for (ProjectDaseCaseModel attentionPersonModel : list) {
            ProjectDaseCaseModel model = new ProjectDaseCaseModel();
            model.setName(attentionPersonModel.getName());
            model.setType(attentionPersonModel.getType());
            model.setSetTime(attentionPersonModel.getSetTime());
            model.setSumMoney(attentionPersonModel.getSumMoney());
            model.setFinishMoney(attentionPersonModel.getFinishMoney());
            model.setProjectState(attentionPersonModel.getProjectState());
            model.setOpenTime(attentionPersonModel.getOpenTime());
            model.setCompletedTime(attentionPersonModel.getCompletedTime());
            model.setIsSkip(attentionPersonModel.getIsSkip());
            model.setProjectContent(attentionPersonModel.getProjectContent());
            model.setContent(attentionPersonModel.getContent());
            List<SysUserModel> police=attentionPersonModel.getUserModels();
            if(police!=null&&police.size()>0){
                if(police.size()<=1){
                    model.setPoliceName1(police.get(0).getFullName());
                    model.setPolicePhone1(police.get(0).getPhone());
                }
                if(police.size()>1&&police.size()<3){
                    model.setPoliceName1(police.get(0).getFullName());
                    model.setPolicePhone1(police.get(0).getPhone());
                    model.setPoliceName2(police.get(1).getFullName());
                    model.setPolicePhone2(police.get(1).getPhone());
                }
                if(police.size()>=3){
                    model.setPoliceName1(police.get(0).getFullName());
                    model.setPolicePhone1(police.get(0).getPhone());
                    model.setPoliceName2(police.get(1).getFullName());
                    model.setPolicePhone2(police.get(1).getPhone());
                    model.setPoliceName3(police.get(3).getFullName());
                    model.setPolicePhone3(police.get(3).getPhone());
                }
            }

            List<ProjectAddrInfoModel> plist =attentionPersonModel.getProjectAddrInfoModel();
            if(plist!=null){
               for(int i=0;i<plist.size();i++){
                     if(i==0){
                         model.setCountyName(plist.get(i).getCountyName());
                         model.setTownshipName(plist.get(i).getTownshipName());
                         model.setVillageName(plist.get(i).getVillageName());
                         model.setProgroup(plist.get(i).getProgroup());
                         model.setCountyPerson(plist.get(i).getCountyPerson());
                         model.setCountyPosition(plist.get(i).getCountyPosition());
                         model.setCountyPhone(plist.get(i).getCountyPhone());
                         model.setTownshipPerson(plist.get(i).getTownshipPerson());
                         model.setTownshipPosition(plist.get(i).getTownshipPosition());
                         model.setTownshipPhone(plist.get(i).getTownshipPhone());
                         model.setVillagePerson(plist.get(i).getVillagePerson());
                         model.setPositions(plist.get(i).getPositions());
                         model.setPhone(plist.get(i).getPhone());
                         model.setProgroupPerson(plist.get(i).getProgroupPerson());
                         model.setProgroupPosition(plist.get(i).getProgroupPosition());
                         model.setProgroupPhone(plist.get(i).getProgroupPhone());
                         model.setPdetailedAddr(plist.get(i).getDetailedAddr());
                         model.setLongitude(plist.get(i).getLongitude());
                         model.setLatitude(plist.get(i).getLatitude());
                         model.setPoliceStation(plist.get(i).getPoliceStation());
                         model.setLeader(plist.get(i).getLeader());
                         model.setLeaderPosition(plist.get(i).getLeaderPosition());
                         model.setStationPhone(plist.get(i).getStationPhone());
                         model.setOwnerCorp(plist.get(i).getOwnerCorp());
                         model.setOwnerContact(plist.get(i).getOwnerContact());
                         model.setOwnerPosition(plist.get(i).getOwnerPosition());
                         model.setOwnerPhone(plist.get(i).getOwnerPhone());
                         model.setConstructionOrg(plist.get(i).getConstructionOrg());
                         model.setConstructionContact(plist.get(i).getConstructionContact());
                         model.setConstructionPosition(plist.get(i).getConstructionPosition());
                         model.setConstructionPhone(plist.get(i).getConstructionPhone());
                         ja.add(model);
                     }else{
                         ProjectDaseCaseModel model1=new  ProjectDaseCaseModel();
                         model.setCountyName(plist.get(i).getCountyName());
                         model.setTownshipName(plist.get(i).getTownshipName());
                         model.setVillageName(plist.get(i).getVillageName());
                         model.setProgroup(plist.get(i).getProgroup());
                         model.setCountyPerson(plist.get(i).getCountyPerson());
                         model.setCountyPosition(plist.get(i).getCountyPosition());
                         model.setCountyPhone(plist.get(i).getCountyPhone());
                         model.setTownshipPerson(plist.get(i).getTownshipPerson());
                         model.setTownshipPosition(plist.get(i).getTownshipPosition());
                         model.setTownshipPhone(plist.get(i).getTownshipPhone());
                         model.setVillagePerson(plist.get(i).getVillagePerson());
                         model.setPositions(plist.get(i).getPositions());
                         model.setPhone(plist.get(i).getPhone());
                         model.setProgroupPerson(plist.get(i).getProgroupPerson());
                         model.setProgroupPosition(plist.get(i).getProgroupPosition());
                         model.setProgroupPhone(plist.get(i).getProgroupPhone());
                         model.setPdetailedAddr(plist.get(i).getDetailedAddr());
                         model.setLongitude(plist.get(i).getLongitude());
                         model.setLatitude(plist.get(i).getLatitude());
                         model.setPoliceStation(plist.get(i).getPoliceStation());
                         model.setLeader(plist.get(i).getLeader());
                         model.setLeaderPosition(plist.get(i).getLeaderPosition());
                         model.setStationPhone(plist.get(i).getStationPhone());
                         model.setOwnerCorp(plist.get(i).getOwnerCorp());
                         model.setOwnerContact(plist.get(i).getOwnerContact());
                         model.setOwnerPosition(plist.get(i).getOwnerPosition());
                         model.setOwnerPhone(plist.get(i).getOwnerPhone());
                         model.setConstructionOrg(plist.get(i).getConstructionOrg());
                         model.setConstructionContact(plist.get(i).getConstructionContact());
                         model.setConstructionPosition(plist.get(i).getConstructionPosition());
                         model.setConstructionPhone(plist.get(i).getConstructionPhone());
                         ja.add(model1);
                     }

               }
            }
        }
//        ExcelUtil.download("test",headMap,ja,response);

        /**创建文件名字*/
        String fileName = Config.PROJECT_DASE_INFO;
//            String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());
        String filePath = Utils.getFile(request, fileuploadSetting.getImgURL(), fileName);
        /**在服务器中创建文件*/
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String newFileName = fileName + ".xlsx";
        OutputStream outXlsx = new FileOutputStream(filePath + newFileName);
        /**把数据写入excel中*/
        ExcelUtil.exportExcelX("项目基本情况导出数据", headMap, ja, null, 0, outXlsx);
        outXlsx.close();
        result = newFileName;
        return result;
    }
}
