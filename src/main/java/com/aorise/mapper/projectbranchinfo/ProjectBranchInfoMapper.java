package com.aorise.mapper.projectbranchinfo;

import com.aorise.model.projectbranchinfo.ProjectBranchInfoModel;
import com.aorise.model.projectbranchinfo.ProjectCountModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:联保项目分布统计数据库持久层
 * @Author:Huangdong
 * @Date:2018/9/26 19:16
 * @Version V1.0
 */
@Mapper
@Component
public interface ProjectBranchInfoMapper {
    /**
     * @Author:Huangdong
     * @Desicription:联保项目分布统计
     * @param icounty 区县code
     * @return ProjectBranchInfoModel
     * @throws Exception
     */
 ProjectBranchInfoModel getprojectbranchinfo(@Param("icounty") String icounty,@Param("state")Integer state) throws DataAccessException;
    /**
     * @Author:Huangdong
     * @Desicription:联保项目分布统计
     * @return ProjectBranchInfoModel
     * @throws Exception
     */
    List<ProjectCountModel> getprojectCount(@Param("icounty") String icounty,@Param("state")Integer state) throws DataAccessException;

}
