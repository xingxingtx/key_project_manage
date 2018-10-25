package com.aorise.service.projectcominfo.impl;

import com.aorise.mapper.projectcominfo.ProjectComInfoMapper;
import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.notification.NotificationModel;
import com.aorise.model.projectcominfo.ProjectComInfoModel;
import com.aorise.model.projectdasecase.ProjectAddrInfoModel;
import com.aorise.model.projectdasecase.ProjectDaseCaseModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.service.projectcominfo.ProjectComInfoService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:点评项目Service实现层
 * @Author:Huangdong
 * @Date:2018/9/25 10:47
 * @Version V1.0
 */
@Service
public class ProjectComInfoServiceImpl implements ProjectComInfoService {

    @Autowired
    private ProjectComInfoMapper projectComInfoMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * @Author:Huangdong
     * @Desicription:新增项目点评
     * @param pc 项目点评实体
     * @return int
     * @throws Exception
     */
    @Override
    public int addProjectcomInfo(ProjectComInfoModel pc,String url,String userid) throws Exception {
        int j = 0;
       j =projectComInfoMapper.addProjectcomInfo(pc);
       //获取当前用户
        Integer userids = ((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId();
        //通过项目id查询项目名称
        ProjectDaseCaseModel pdc = projectComInfoMapper.getProjectName(pc.getProjectid());
        //通过项目id查询地址
        List<ProjectAddrInfoModel> list = projectComInfoMapper.getProject(pc.getProjectid());
        for (int i = 0; i < list.size(); i++) {
            //如果处理新增成功，添加通知
            NotificationModel nModel = new NotificationModel();
            ProjectAddrInfoModel projectAddrInfoModel = list.get(i);
            String adder = projectAddrInfoModel.getCounty();

            List<SysUserModel> userListByAddrXz = sysUserMapper.findUserListByAddrPl(adder);
            StringBuilder sb = new StringBuilder("");
            if (userListByAddrXz != null) {
                for (int k = 0; k < userListByAddrXz.size(); k++) {

                        sb.append(userListByAddrXz.get(k).getId() + ",");

                }
            }
            sb.append(userid);
            switch (pc.getType()){
                case 1:
                    //基本项目详情
                    nModel.setContent(url + "api/projectdasecase/id/" + pc.getRelateId());
                    break;
                case 2:
                    //开工摸排
                    nModel.setContent(url + "api/siteVisitsInfo/id/" + pc.getRelateId());
                    break;
                case 3:
                    //群众上访
                    nModel.setContent(url + "api/petitioning/id/" + pc.getRelateId());
                    break;
                case 4:
                    //阻工
                    nModel.setContent(url + "api/stopWork/id/" + pc.getRelateId());
                    break;
                case 5:
                    //隐患
                    nModel.setContent(url + "api/constructionHidden/id/" + pc.getRelateId());
                    break;
            }
            nModel.setTitle(pdc.getName()+ "项目新增点评通知");
            nModel.setCreater(userids);
            nModel.setType(1);//系统通知
            nModel.setGroupIds(sb.toString());
            nModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            nModel.setState(ConstDefine.STATE_ABLE);
            notificationService.insertObject(nModel);
        }
        return j;
    }

    /**
     * @Author:wei.peng
     * @Desicription:根据项目id查询项目点评列表
     * @param projectId 项目id
     * @return  List<ProjectComInfoModel>
     * @throws Exception
     */
    @Override
    public List<ProjectComInfoModel> selectProjectcomInfo(String projectId,String type,String relateId) {
        return projectComInfoMapper.selectProjectcomInfo(projectId,type,relateId);
    }
}
