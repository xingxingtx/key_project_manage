package com.aorise.service.statistic;

import com.aorise.model.statistic.StatisticModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:行动成效分析
 * @Date:Created in 2018/9/27 15:08
 * @Modified By:
 */
public interface ActionEffectService {

    /**
     *@Author:ZGP
     *@Desicription:阻工处理情况统计，完成与未完成占比
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel> stopWorkDisposeInfo( String timeBegin,String timeEnd);
    /**
     *@Author:ZGP
     *@Desicription:阻工处理满意度统计
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel> stopWorkDisposeIsHappy(String timeBegin, String timeEnd);
    /**
     *@Author:ZGP
     *@Desicription:各月份安全隐患排查
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    List<StatisticModel>  dangerTroubleshoot(String timeBegin, String timeEnd);
}
