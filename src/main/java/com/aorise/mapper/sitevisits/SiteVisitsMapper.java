package com.aorise.mapper.sitevisits;

import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.model.sitevisits.SiteVisitsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:开工前摸排数据库持久层
 * @Date:Created in 2018-09-25
 * @Modified By:
 */
@Mapper
@Component
public interface SiteVisitsMapper {
    /**
     * 新增开工前摸排信息
     * @param userId 创建人id
     * @param model  开工前摸排信息实体类
     */
    public Integer addSiteVisitsInfo(@Param("userId") String userId, @Param("model") SiteVisitsInfoModel model) throws DataAccessException;
    /**
     * 新增开工前摸排处理信息
     * @param userId 创建人id
     * @param model  开工前摸排处理信息实体类
     */
    public Integer addSiteVisits(@Param("userId")String userId, @Param("model")SiteVisitsModel model) throws DataAccessException;
    /**
     * 修改开工前摸排处理信息
     * @param editer 修改人id
     * @param model  开工前摸排处理信息实体类
     */
    public Integer updateSiteVisits(@Param("editer") String editer, @Param("model")SiteVisitsModel model);

    /**
     *@Author:weipeng
     *@Desicription:分页查询开工前摸排数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    List<SiteVisitsInfoModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:weipeng
     *@Desicription:分页查询开工前摸排数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;

    /**
     * 删除开工前摸排信息
     * @param state 状态
     * @param id  开工前摸排id
     * @param editer  修改人id
     */
    int delteSiteVisitsInfo(@Param("id") Integer[] id,@Param("editer") Integer editer,@Param("state")Integer state) throws DataAccessException;

    /**
     * 根据开工前摸排id查询开工前摸排处理信息
     * @param visitsid  开工前摸排id
     */
    List<SiteVisitsModel> getSiteVisitsById(@Param("visitsid") Integer visitsid) throws DataAccessException;

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询详情
     * @param
     * @Date:2018/10/8 16:26
     * @Return:
     * @Modified By:
     */
    SiteVisitsInfoModel getSiteVisitsInfo(Integer id)throws DataAccessException;

    /**
     * 修改开工前摸排详情数据
     * @param model
     * @return
     */
    int updateSiteVisitsInfo(@Param("model") SiteVisitsInfoModel model)throws DataAccessException;
}
