package com.aorise.service.projectbranchinfo;

import com.aorise.model.projectbranchinfo.ProjectBranchInfoModel;

/**
 * @Description:联保项目分布统计Service层
 * @Author:Huangdong
 * @Date:2018/9/26 19:14
 * @Version V1.0
 */
public interface ProjectBranchInfoService {

    /**
     * @Author:Huangdong
     * @Desicription:联保项目分布统计
     * @param icounty 区县code
     * @return ProjectBranchInfoModel
     * @throws Exception
     */
   ProjectBranchInfoModel getprojectbranchinfo(String icounty) throws Exception;

}
