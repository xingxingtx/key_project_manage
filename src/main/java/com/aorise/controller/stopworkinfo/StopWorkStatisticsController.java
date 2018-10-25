package com.aorise.controller.stopworkinfo;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DBErrorException;
import com.aorise.model.stopworkinfo.StopWorkStatisticsModel;
import com.aorise.service.stopworkinfo.StopWorkStatisticsService;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:阻工情况统计
 * @Author:Huangdong
 * @Date:2018/9/27 15:33
 * @Version V1.0
 */
@RestController
@Api(description = "阻工情况统计接口")
public class StopWorkStatisticsController {
    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StopWorkStatisticsService stopWorkService;

    /**
     * 根据统计段时间查询阻工情况统计
     * HTTP 方式：GET
     * API 路径：/api/stopworkinfo/getstopworkinfo
     * 方法名：getStopWorkinfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "根据统计段时间查询阻工情况统计", httpMethod = "GET", response = String.class, notes = "根据时间获取统计数据-阻工情况统计-第一张统计表")
    @SystemControllerLog(descrption = "根据统计段时间查询阻工情况统计", actionType = "4",modules = "阻工情况统计")
    @RequiresPermissions("ZGQKTJ")
    @RequestMapping(value = "/api/stopworkinfo/getstopworkinfo", method = RequestMethod.GET)
    public String getStopWorkinfo(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        logger.info("startTime: " + startDate);
        logger.info("endTime: " + endDate);
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
            List<StopWorkStatisticsModel> list = stopWorkService.getStopWorkinfo(map);
            return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "", list).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getStopWorkinfo..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getStopWorkinfo..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }
    /**
     * 根据地区获取统计数据
     * HTTP 方式：GET
     * API 路径：/api/stopworkinfo/area
     * 方法名：getActionByArea
     * 方法返回类型：String
     */
    @ApiOperation ( value = "根据地区获取统计数据", httpMethod = "GET", response = String.class, notes = "根据时间获取统计数据-阻工情况统计-第二张统计表" )
    @SystemControllerLog(descrption = "根据地区获取统计数据", actionType = "4",modules = "阻工情况统计")
    @RequiresPermissions("ZGQKTJ")
    @RequestMapping ( value = "/api/stopworkinfo/area", method = RequestMethod.GET, produces = "application/json" )
    public String getActionByArea(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            List<StopWorkStatisticsModel> list = stopWorkService.getActionByArea(map);
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), list).toString();
        }catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getActionByArea..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getActionByArea..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }
    /**
     * 根据阻工原因获取统计数据
     * HTTP 方式：GET
     * API 路径：/api/stopworkinfo/preventreason
     * 方法名：getActionByType
     * 方法返回类型：String
     */
    @ApiOperation ( value = "根据阻工原因获取统计数据", httpMethod = "GET", response = String.class, notes = "根据阻工原因获取统计数据-阻工情况统计-第三张统计表" )
    @SystemControllerLog(descrption = "根据阻工原因获取统计数据", actionType = "4",modules = "阻工情况统计")
    @RequiresPermissions("ZGQKTJ")
    @RequestMapping ( value = "/api/stopworkinfo/preventreason", method = RequestMethod.GET, produces = "application/json" )
    public String getActionByType(@ApiParam ( value = "开始时间yyyy-MM" ) @RequestParam ( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM" ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {
            Map<String, Object> map = new HashMap<>(2);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            List<StopWorkStatisticsModel> model= stopWorkService.getActionByType(map);
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        }catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getActionByArea..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:getActionByArea..msg:阻工情况统计错误. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }
}
