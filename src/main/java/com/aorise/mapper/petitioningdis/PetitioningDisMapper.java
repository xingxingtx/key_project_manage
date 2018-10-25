package com.aorise.mapper.petitioningdis;

import com.aorise.model.petitioningdis.PetitioningDisInfoModel;
import com.aorise.model.petitioningdis.PetitioningDisModel;
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
public interface PetitioningDisMapper {
    /**
     * 新增群众上访信息
     * @param userId 创建人id
     * @param model  群众上访信息实体类
     */
    public Integer addPetitioningDisInfo(@Param("userId") String userId, @Param("model") PetitioningDisInfoModel model) throws DataAccessException;
    /**
     * 新增群众上访处理信息
     * @param userId 创建人id
     * @param model  群众上访处理信息实体类
     */
    public Integer addPetitioningDis(@Param("userId") String userId, @Param("model") PetitioningDisModel model) throws DataAccessException;
    /**
     * 修改群众上访处理信息
     * @param editer 修改人id
     * @param model  群众上访处理信息实体类
     */
    public Integer updatePetitioningDis(@Param("editer") String editer, @Param("model") PetitioningDisModel model);

    /**
     *@Author:weipeng
     *@Desicription:分页查询群众上访数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    List<PetitioningDisInfoModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:weipeng
     *@Desicription:分页查询群众上访数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;

    /**
     * 删除群众上访排信息
     * @param state 状态
     * @param id  群众上访id
     */
    int deltePetitioningDisInfo(@Param("id") Integer[] id,@Param("editer") Integer editer, @Param("state") Integer state) throws DataAccessException;

    /**
     * 根据开工前摸排id查询群众上访处理信息
     * @param petitioningid  群众上访id
     */
    List<PetitioningDisModel> getPetitioningDisById(@Param("petitioningid") Integer petitioningid) throws DataAccessException;

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询群众上访详情
     * @param
     * @Date:2018/10/8 18:19
     * @Return:
     * @Modified By:
     */
    PetitioningDisInfoModel getPetitioningDisInfo(Integer id)throws DataAccessException;

    /**
     * 修改群众上访详情数据
     * @param model
     * @return
     */
    int updatePetitioningDisInfo(@Param("model") PetitioningDisInfoModel model)throws DataAccessException;
}
