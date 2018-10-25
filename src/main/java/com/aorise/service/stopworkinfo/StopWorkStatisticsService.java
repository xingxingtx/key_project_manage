package com.aorise.service.stopworkinfo;

import com.aorise.model.stopworkinfo.StopWorkStatisticsModel;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/9/27 15:41
 * @Version V1.0
 */
public interface StopWorkStatisticsService {

    /**
     * @Author:Huangdong
     * @Desicription:根据统计段时间查询阻工情况统计
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    List<StopWorkStatisticsModel> getStopWorkinfo(Map<String, Object> map);
    /**
     * @Author:Huangdong
     * @Desicription:根据地区获取统计数据
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    List<StopWorkStatisticsModel> getActionByArea(Map<String, Object> map);
    /**
     * @Author:Huangdong
     * @Desicription:根据阻工原因获取统计数据
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    List<StopWorkStatisticsModel> getActionByType(Map<String, Object> map);
}
