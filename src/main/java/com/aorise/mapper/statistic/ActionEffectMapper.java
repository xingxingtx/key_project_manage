package com.aorise.mapper.statistic;

import com.aorise.model.statistic.StatisticModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:联保成效统计
 * @Date:Created in 2018/9/27 15:11
 * @Modified By:
 */

@Mapper
@Component(value = "ActionEffectMapper")
public interface ActionEffectMapper {
    /**
     *@Author:ZGP
     *@Desicription:阻工处理情况统计，完成与未完成占比
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel> stopWorkDisposeInfo(@Param("timeBegin") String timeBegin, @Param("timeEnd")String timeEnd);
    /**
     *@Author:ZGP
     *@Desicription:阻工处理满意度统计
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel> stopWorkDisposeIsHappy(@Param("timeBegin") String timeBegin, @Param("timeEnd")String timeEnd);
    /**
     *@Author:ZGP
     *@Desicription:各月份安全隐患排查
     * @param times 时间集合
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel>  dangerTroubleshoot(String[] times);

}
