package com.aorise.service.stopworkinfo.impl;

import com.aorise.mapper.common.AreaInfoMapper;
import com.aorise.mapper.stopworkinfo.StopWorkStatisticsMapper;
import com.aorise.model.common.AreaInfoModel;
import com.aorise.model.stopworkinfo.StopWorkStatisticsModel;
import com.aorise.service.stopworkinfo.StopWorkStatisticsService;
import com.aorise.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/9/27 15:42
 * @Version V1.0
 */
@Service
public class StopWorkStatisticsServiceImpl implements StopWorkStatisticsService {
    @Autowired
    private StopWorkStatisticsMapper stopWorkMapper;
    @Autowired
    private AreaInfoMapper areaInfoMapper;

    /**
     * @Author:Huangdong
     * @Desicription:根据统计段时间查询阻工情况统计
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    @Override
    public List<StopWorkStatisticsModel> getStopWorkinfo(Map<String, Object> map) {
        //声明变量
        List<StopWorkStatisticsModel> list = new ArrayList<>();
        StopWorkStatisticsModel model;
        //判断查询时间是否为空
        if (!StringUtils.isBlank(map.get("startDate").toString()) || !StringUtils.isBlank(map.get("endDate").toString())) {
            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();
            model=new StopWorkStatisticsModel();
            //循环查询每个月数据
            int months = Utils.monthsBetween(startDate, endDate);
            String currentMonth=endDate;
            int count = stopWorkMapper.getActionByTime(currentMonth);
            model.setValue(count);
            model.setKey(currentMonth);
            list.add(model);
            for (int i = 0; i < months-1; i++) {
                model=new StopWorkStatisticsModel();
                currentMonth= Utils.preMonths(currentMonth);
                count = stopWorkMapper.getActionByTime(currentMonth);
                model.setValue(count);
                model.setKey(currentMonth);
                list.add(model);
            }
        } else {
            //获取当天时间
            model=new StopWorkStatisticsModel();
            String today=Utils.getCurrentTime("yyyy-MM");
            int count = stopWorkMapper.getActionByTime(today);
            model.setValue(count);
            model.setKey(today);
            list.add(model);
            //循环查询6个月内数据
            for (int i = 0; i < 5; i++) {
                model=new StopWorkStatisticsModel();
                today=Utils.preMonths(today);
                count = stopWorkMapper.getActionByTime(today);
                model.setValue(count);
                model.setKey(today);
                list.add(model);
            }
        }
        return list;
    }
    /**
     * @Author:Huangdong
     * @Desicription:根据地区获取统计数据
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    @Override
    public List<StopWorkStatisticsModel> getActionByArea(Map<String, Object> map) {
        List<StopWorkStatisticsModel> list = new ArrayList<>();
        //查询当前地区数据
        List<AreaInfoModel> areaInfoModelList = areaInfoMapper.getListByParent("431200000000");
        //循环查询每个地区行动次数
        for (AreaInfoModel areaInfoModel : areaInfoModelList) {
            map.put("county", areaInfoModel.getAreaCode());
            StopWorkStatisticsModel model=new StopWorkStatisticsModel();
            int count = stopWorkMapper.getActionByArea(map);
            model.setValue(count);
            model.setKey(areaInfoModel.getName());
            list.add(model);
        }
        return list;
    }
    /**
     * @Author:Huangdong
     * @Desicription:根据阻工原因获取统计数据
     * @param map 查询参数
     * @return  List<StopWorkStatisticsModel>
     * @throws Exception
     */
    @Override
    public  List<StopWorkStatisticsModel> getActionByType(Map<String, Object> map) {
        List<StopWorkStatisticsModel> list= new ArrayList<>();
        StopWorkStatisticsModel model=stopWorkMapper.getActionByType(map);
        StopWorkStatisticsModel statisticModel=new StopWorkStatisticsModel();
        statisticModel.setKey("相关款项问题");
        statisticModel.setValue(model.getVisits());
        list.add(statisticModel);
        statisticModel=new StopWorkStatisticsModel();
        statisticModel.setKey("程序不合法");
        statisticModel.setValue(model.getWork());
        list.add(statisticModel);
        statisticModel=new StopWorkStatisticsModel();
        statisticModel.setKey("群众无故闹事");
        statisticModel.setValue(model.getPetition());
        list.add(statisticModel);
        statisticModel=new StopWorkStatisticsModel();
        statisticModel.setKey("对协商结果不满意");
        statisticModel.setValue(model.getHidden());
        list.add(statisticModel);
        statisticModel=new StopWorkStatisticsModel();
        statisticModel.setKey("其它");
        statisticModel.setValue(model.getOther());
        list.add(statisticModel);
        return list;
    }
}
