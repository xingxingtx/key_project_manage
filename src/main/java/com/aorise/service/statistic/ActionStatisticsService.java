package com.aorise.service.statistic;

import com.aorise.model.statistic.StatisticModel;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-25 16:34
 * @Modified By:
 */
public interface ActionStatisticsService {
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据时间查询联保行动次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    List<StatisticModel> getActionByTime(Map<String, Object> map) ;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据地区查询联保行动次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    List<StatisticModel> getActionByArea(Map<String, Object> map) ;
    /**
     *@Author:Shenzhiwei
     *@Desicription:根据类型查询联保行动次数
     * @param map 查询条件
     *@Date:Created in 9:51 2018/9/25
     *@Return:返回数据集合
     * @Modified By:
     */
    List<StatisticModel> getActionByType(Map<String, Object> map) ;
}
