package com.aorise.service.projectbranchinfo.impl;

import com.aorise.mapper.projectbranchinfo.ProjectBranchInfoMapper;
import com.aorise.model.projectbranchinfo.ProjectBranchInfoModel;
import com.aorise.model.projectbranchinfo.ProjectCountModel;
import com.aorise.service.projectbranchinfo.ProjectBranchInfoService;
import com.aorise.utils.define.ConstDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:联保项目分布统计Service实现层
 * @Author:Huangdong
 * @Date:2018/9/26 19:15
 * @Version V1.0
 */
@Service
public class ProjectBranchInfoServiceImpl implements ProjectBranchInfoService{

    @Autowired
    private ProjectBranchInfoMapper projectBranchInfoMapper;

    /**
     * @Author:Huangdong
     * @Desicription:联保项目分布统计
     * @param icounty 区县code
     * @return ProjectBranchInfoModel
     * @throws Exception
     */
    @Override
    public ProjectBranchInfoModel getprojectbranchinfo(String icounty) throws Exception {
        ProjectBranchInfoModel result = projectBranchInfoMapper.getprojectbranchinfo(icounty, ConstDefine.STATE_ABLE);
         //查询各县级地区
        if(result!=null) {
            List<ProjectCountModel> projectCountModelList = projectBranchInfoMapper.getprojectCount(icounty,ConstDefine.STATE_ABLE);
            result.setList(projectCountModelList);
        }

        return result;
    }


}
