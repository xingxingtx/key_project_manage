package com.aorise.mapper.stopworkinfo;

import com.aorise.model.stopworkinfo.StopWorkStatisticsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/9/27 15:42
 * @Version V1.0
 */
@Mapper
@Component
public interface StopWorkStatisticsMapper {
    /**
     *@Author:Huangdong
     *@Desicription:根据时间查询阻工次数
     * @param dateTime 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    int getActionByTime(@Param( "dateTime" ) String dateTime)throws DataAccessException;

    /**
     *@Author:Huangdong
     *@Desicription:根据地区查询阻工发生次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    int getActionByArea(Map<String, Object> map) throws DataAccessException;
    /**
     *@Author:Huangdong
     *@Desicription:根据阻工原因统计次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    StopWorkStatisticsModel getActionByType(Map<String, Object> map) throws DataAccessException;
}
