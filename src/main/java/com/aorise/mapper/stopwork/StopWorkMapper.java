package com.aorise.mapper.stopwork;


import com.aorise.model.stopwork.StopWorkInfoModel;
import com.aorise.model.stopwork.StopWorkModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:wei.peng
 * @Desicription:施工现场阻工数据库持久层
 * @Date:Created in 2018-09-27
 * @Modified By:
 */
@Mapper
@Component
public interface StopWorkMapper {
    /**
     * 新增施工现场阻工信息
     * @param userId 创建人id
     * @param model  施工现场阻工信息实体类
     */
    public Integer addStopWorkInfo(@Param("userId") String userId, @Param("model") StopWorkInfoModel model) throws DataAccessException;
    /**
     * 新增施工现场阻工处理信息
     * @param userId 创建人id
     * @param model  施工现场阻工处理信息实体类
     */
    public Integer addStopWork(@Param("userId") String userId, @Param("model") StopWorkModel model) throws DataAccessException;
    /**
     * 修改施工现场阻工处理信息
     * @param editer 修改人id
     * @param model  施工现场阻工处理信息实体类
     */
    public Integer updateStopWork(@Param("editer") String editer, @Param("model") StopWorkModel model);

    /**
     * 修改施工现场阻工信息
     * @param editer 修改人id
     * @param model  施工现场阻工信息实体类
     */
    public Integer updateStopWorkInfo(@Param("editer") String editer, @Param("model") StopWorkInfoModel model);

    /**
     *@Author:weipeng
     *@Desicription:分页查询施工现场阻工数据
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据集合
     * @Modified By:
     */
    List<StopWorkInfoModel> getAllByPage(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:weipeng
     *@Desicription:分页查询施工现场阻工数据总数
     * @param map 查询条件
     *@Date:Created in 15:28 2018/9/26
     *@Return:返回数据总数
     * @Modified By:
     */
    int getAllByPageCount(Map<String, Object> map) throws DataAccessException;

    /**
     * 删除施工现场阻工信息
     * @param state 状态
     * @param id  施工现场阻工id
     */
    int delteStopWorkInfo(@Param("id") Integer[] id,@Param("editer") Integer editer,  @Param("state") Integer state) throws DataAccessException;

    /**
     * 根据施工现场阻工id查询施工现场阻工处理信息
     * @param visitsid  施工现场阻工id
     */
    List<StopWorkModel> getStopWorkById(@Param("workid") Integer visitsid) throws DataAccessException;

    /**
     * @Author:Yangzepeng
     * @Desicription:根据id查询施工现场阻工详情
     * @param
     * @Date:2018/10/9 9:22
     * @Return:
     * @Modified By:
     */
    StopWorkInfoModel getStopWorkInfo(Integer id);
}
