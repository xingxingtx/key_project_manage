package com.aorise.mapper.statistic;

import com.aorise.model.statistic.StatisticModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:联保行动统计数据访问层
 * @Date:Created in 2018-09-25 09:43
 * @Modified By:
 */
@Mapper
@Component
public interface ActionStatisticsMapper {

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据时间查询联保行动次数
     * @param dateTime 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    int getActionByTime(@Param ( "dateTime" ) String dateTime)throws DataAccessException;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据地区查询联保行动次数
      * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
     int getActionByArea(Map<String, Object> map)throws DataAccessException;

    /**
     *@Author:Shenzhiwei
     *@Desicription:根据类型查询联保行动次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    StatisticModel getActionByType(Map<String, Object> map)throws DataAccessException;
}
