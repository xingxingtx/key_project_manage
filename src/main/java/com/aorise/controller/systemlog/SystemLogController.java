package com.aorise.controller.systemlog;

import com.aorise.exceptions.DBErrorException;
import com.aorise.model.systemlog.SystemLogModel;
import com.aorise.service.systemlog.SystemLogService;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.page.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:系统日志操作
 * @Author:Huangdong
 * @Date:2018/10/12 10:04
 * @Version V1.0
 */
@RestController
@Api(description = "系统日志操作接口")
public class SystemLogController {

    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemLogService systemLogService;


    /**
     * 分页查询系统日志操作
     * HTTP 方式：GET
     * API 路径：/api/systemlog/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getSystemLogs
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询系统日志操作", httpMethod = "GET", response = String.class, notes = "分页查询系统日志操作，提供系统管理-系统操作日志分页查询")
    @RequestMapping(value = "/api/systemlog/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET)
    public String getSystemLog(@ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) String pageIndex,
            @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) String pageNum) {
        logger.debug("页索引:" + pageIndex);
        logger.debug("页大小:" + pageNum);
        try {

            /**分页page*/
            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex.toString()));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum.toString()));
            }

            /**分页查询条件*/
            Map<String, Object> map = new HashMap<>(2);
            map.put("beginIndex", page.getBeginIndex());
            map.put("endIndex", page.getEndinIndex());
            /**分页查询*/
            List<SystemLogModel> model = systemLogService.getSystemLog(map,page);
            page.setList(model);
            return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "", page).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SystemLogController. function:getSystemLog...msg:Abnormal enquiries for information details.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemLogController. function:getSystemLog...msg:Abnormal enquiries for information details.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

}
