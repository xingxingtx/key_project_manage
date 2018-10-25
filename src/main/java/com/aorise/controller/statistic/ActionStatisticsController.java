package com.aorise.controller.statistic;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.model.statistic.StatisticModel;
import com.aorise.service.statistic.ActionStatisticsService;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:联保行动统计接口
 * @Date:Created in 2018-09-27 11:52
 * @Modified By:
 */
@RestController
@Api ( description = "联保行动统计接口" )
public class ActionStatisticsController {


    @Autowired
    private ActionStatisticsService actionStatisticsService;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据时间获取统计数据
     * HTTP 方式：GET
     * API 路径：/api/actionstatistics/time
     * 方法名：getActionByTime
     * 方法返回类型：String
     */
    @ApiOperation ( value = "根据时间获取统计数据", httpMethod = "GET", response = String.class, notes = "根据时间获取统计数据-联保行动统计-第一张统计表" )
    @SystemControllerLog(descrption = "根据时间获取统计数据", actionType = "4",modules = "联保行动统计")
    @RequestMapping ( value = "/api/actionstatistics/time", method = RequestMethod.GET, produces = "application/json" )
    public String getActionByTime(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            if (startDate==null){
                map.put("startDate", "");
            }else {
                map.put("startDate", startDate);
            }
            if (endDate==null)
            {
                map.put("endDate", "");
            }else {
                map.put("endDate", endDate);
            }
            List<StatisticModel> list = actionStatisticsService.getActionByTime(map);
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), list).toString();
        } catch (DataAccessException e) {
            logger.error("controller:ActionStatisticsController. function:getActionByTime...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:ActionStatisticsController. function:getActionByTime...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 根据地区获取统计数据
     * HTTP 方式：GET
     * API 路径：/api/actionstatistics/time
     * 方法名：getActionByTime
     * 方法返回类型：String
     */
    @ApiOperation ( value = "根据地区获取统计数据", httpMethod = "GET", response = String.class, notes = "根据时间获取统计数据-联保行动统计-第二张统计表" )
    @SystemControllerLog(descrption = "根据地区获取统计数据", actionType = "4",modules = "联保行动统计")
    @RequestMapping ( value = "/api/actionstatistics/area", method = RequestMethod.GET, produces = "application/json" )
    public String getActionByArea(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            List<StatisticModel> list = actionStatisticsService.getActionByArea(map);
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), list).toString();
        } catch (DataAccessException e) {
            logger.error("controller:ActionStatisticsController. function:getActionByArea...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:ActionStatisticsController. function:getActionByArea...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 根据类型获取统计数据
     * HTTP 方式：GET
     * API 路径：/api/actionstatistics/type
     * 方法名：getActionByTime
     * 方法返回类型：String
     */
    @ApiOperation ( value = "根据时间获取统计数据", httpMethod = "GET", response = String.class, notes = "根据类型获取统计数据-联保行动统计-第三张统计表" )
    @SystemControllerLog(descrption = "根据时间获取统计数据", actionType = "4",modules = "联保行动统计")
    @RequestMapping ( value = "/api/actionstatistics/type", method = RequestMethod.GET, produces = "application/json" )
    public String getActionByType(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            List<StatisticModel> model= actionStatisticsService.getActionByType(map);
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (DataAccessException e) {
            logger.error("controller:ActionStatisticsController. function:getActionByType...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:ActionStatisticsController. function:getActionByType...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

}
