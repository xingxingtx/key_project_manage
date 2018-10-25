package com.aorise.service.statistic.impl;

import com.aorise.mapper.common.AreaInfoMapper;
import com.aorise.mapper.statistic.ActionStatisticsMapper;
import com.aorise.model.common.AreaInfoModel;
import com.aorise.model.statistic.StatisticModel;
import com.aorise.service.statistic.ActionStatisticsService;
import com.aorise.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:
 * @Date:Created in 2018-09-25 16:34
 * @Modified By:
 */
@Service
public class ActionStatisticsServiceImpl implements ActionStatisticsService {
    @Autowired
    private ActionStatisticsMapper actionStatisticsMapper;

    @Autowired
    private AreaInfoMapper areaInfoMapper;
    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param map 查询条件
     * @Author:Shenzhiwei
     * @Desicription:根据时间查询联保行动次数
     * @Date:Created in 9:51 2018/9/25
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    @Transactional
    public List<StatisticModel> getActionByTime(Map<String, Object> map) {
        //声明变量
        List<StatisticModel> list = new ArrayList<>();
        StatisticModel model;
        //判断查询时间是否为空
        if (!StringUtils.isBlank(map.get("startDate").toString()) && !StringUtils.isBlank(map.get("endDate").toString())) {
            String startDate = map.get("startDate").toString();
            String endDate = map.get("endDate").toString();
            model=new StatisticModel();
            //循环查询每个月数据
            int months = Utils.monthsBetween(startDate, endDate);
            String currentMonth=endDate;
            int count = actionStatisticsMapper.getActionByTime(currentMonth);
            model.setValue(count);
            model.setKey(currentMonth);
            list.add(model);
            for (int i = 0; i < months-1; i++) {
                model=new StatisticModel();
                currentMonth=Utils.preMonths(currentMonth);
                count = actionStatisticsMapper.getActionByTime(currentMonth);
                model.setValue(count);
                model.setKey(currentMonth);
                list.add(model);
            }
        } else {
            //获取当天时间
            model=new StatisticModel();
            String today=Utils.getCurrentTime("yyyy-MM");
            int count = actionStatisticsMapper.getActionByTime(today);
            model.setValue(count);
            model.setKey(today);
            list.add(model);
            //循环查询6个月内数据
            for (int i = 0; i < 5; i++) {
                model=new StatisticModel();
                today=Utils.preMonths(today);
                count = actionStatisticsMapper.getActionByTime(today);
                model.setValue(count);
                model.setKey(today);
                list.add(model);
            }
        }
        return list;
    }

    /**
     * @param map 查询条件
     * @Author:Shenzhiwei
     * @Desicription:根据地区查询联保行动次数
     * @Date:Created in 9:51 2018/9/25
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    @Transactional
    public List<StatisticModel> getActionByArea(Map<String, Object> map) {
        List<StatisticModel> list = new ArrayList<>();
        //查询当前地区数据
        List<AreaInfoModel> areaInfoModelList = areaInfoMapper.getListByParent("431200000000");
        //循环查询每个地区行动次数
        for (AreaInfoModel areaInfoModel : areaInfoModelList) {
            map.put("county", areaInfoModel.getAreaCode());
            StatisticModel model=new StatisticModel();
             int count = actionStatisticsMapper.getActionByArea(map);
            model.setValue(count);
            model.setKey(areaInfoModel.getName());
            list.add(model);
        }
        return list;
    }

    /**
     * @param map 查询条件
     * @Author:Shenzhiwei
     * @Desicription:根据类型查询联保行动次数
     * @Date:Created in 9:51 2018/9/25
     * @Return:返回数据集合
     * @Modified By:
     */
    @Override
    public List<StatisticModel> getActionByType(Map<String, Object> map) {
        List<StatisticModel> list= new ArrayList<>();
        StatisticModel model=actionStatisticsMapper.getActionByType(map);
        StatisticModel statisticModel=new StatisticModel();
        statisticModel.setKey("开工前摸排");
        statisticModel.setValue(model.getVisits());
        list.add(statisticModel);
        statisticModel=new StatisticModel();
        statisticModel.setKey("施工现场阻工");
        statisticModel.setValue(model.getWork());
        list.add(statisticModel);
        statisticModel=new StatisticModel();
        statisticModel.setKey("群众上访");
        statisticModel.setValue(model.getPetition());
        list.add(statisticModel);
        statisticModel=new StatisticModel();
        statisticModel.setKey("施工过程中隐患");
        statisticModel.setValue(model.getHidden());
        list.add(statisticModel);
        return list;
    }
}
