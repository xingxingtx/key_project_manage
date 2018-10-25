package com.aorise.controller.statistic;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.ServiceException;
import com.aorise.service.statistic.ActionEffectService;
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
import java.util.Map;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/28 9:25
 * @Modified By:
 */

@RestController
@Api( description = "联保行动成效分析" )
public class ActionEffectController {

    @Autowired
    private ActionEffectService actionEffectService;

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
    @ApiOperation( value = "根据时间统计联保成效 ", httpMethod = "GET", response = String.class, notes = "根据时间获取统计数据-联保行动成效分析-所有数据" )
    @SystemControllerLog(descrption = "根据时间统计联保成效", actionType = "4",modules = "联保行动成效分析")
    @RequestMapping( value = "/api/actionEffect", method = RequestMethod.GET, produces = "application/json" )
    public String getActionEffectByTime(@ApiParam( value = "开始时间yyyy-MM" ,required = false) @RequestParam( value = "startDate", required = false ) String startDate,
                                  @ApiParam ( value = "结束时间yyyy-MM", required = false ) @RequestParam ( value = "endDate", required = false ) String endDate) {
        try {

            Map<String,Object> retMap=new HashMap<String,Object>();
            retMap.put("stopWorkDisposeInfo",actionEffectService.stopWorkDisposeInfo(startDate,endDate));
            retMap.put("stopWorkDisposeIsHappy",actionEffectService.stopWorkDisposeIsHappy(startDate,endDate));
            retMap.put("dangerTroubleshoot",actionEffectService.dangerTroubleshoot(startDate,endDate));
            return new JsonResponseData(true, "操作成功", StatusDefine.SUCCESS, StatusDefineMessage
                    .GetMessage(StatusDefine.SUCCESS), retMap).toString();
        } catch (DataAccessException e) {
            logger.error("controller:ActionEffectController. function:getActionEffectByTime...msg:An exception occurred when count a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (ServiceException e){
            logger.error("controller:ActionEffectController. function:getActionEffectByTime...msg:An exception occurred when count a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:ActionEffectController. function:getActionEffectByTime...msg:An exception occurred when count a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
