package com.aorise.service.statistic.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.statistic.ActionEffectMapper;
import com.aorise.model.statistic.StatisticModel;
import com.aorise.service.statistic.ActionEffectService;
import com.aorise.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/27 16:49
 * @Modified By:
 */
@Service
public class ActionEffectServiceImpl implements ActionEffectService{
    @Autowired
    private ActionEffectMapper actionEffectMapper;

    /**
     *@Author:ZGP
     *@Desicription:阻工处理情况统计，完成与未完成占比
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    public List<StatisticModel> stopWorkDisposeInfo(String timeBegin, String timeEnd) {
        return actionEffectMapper.stopWorkDisposeInfo(timeBegin,timeEnd);
    }

    /**
     *@Author:ZGP
     *@Desicription:阻工处理满意度统计
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    public List<StatisticModel> stopWorkDisposeIsHappy(String timeBegin, String timeEnd) {
        return actionEffectMapper.stopWorkDisposeIsHappy(timeBegin,timeEnd);
    }

    /**
     *@Author:ZGP
     *@Desicription:各月份安全隐患排查
     * @param timeBegin 开始时间
     * @param timeEnd   结束时间
     *@Date:Created in 10:04 2018/9/27
     *@Return:对象
     * @Modified By:zgp
     */
    public List<StatisticModel> dangerTroubleshoot(String timeBegin, String timeEnd) {
        try {
            return actionEffectMapper.dangerTroubleshoot(Utils.getMonthBetween(timeBegin, timeEnd));
        }catch (ParseException e){
            throw new ServiceException("隐患排除次数统计错误：时间格式有误");
        }
    }
}
