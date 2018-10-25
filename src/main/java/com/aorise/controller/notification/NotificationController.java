package com.aorise.controller.notification;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DataValidationException;
import com.aorise.exceptions.SendMsgExeption;
import com.aorise.exceptions.ServiceException;
import com.aorise.model.notification.NotificationModel;
import com.aorise.service.notification.NotificationService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.page.Page;
import com.aorise.utils.validation.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Shenzhiwei
 * @Desicription:通知管理api接口类
 * @Date:Created in 2018-09-20 09:32
 * @Modified By:
 */
@RestController
@Api ( description = "通知相关接口" )
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DataValidation dataValidation;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加通知
     * HTTP 方式：POST
     * API 路径：/api/notification
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation ( value = "添加通知", httpMethod = "POST", response = String.class, notes = "添加通知-通知管理-添加功能使用" )
    @RequiresPermissions ("TZXZ")
    @SystemControllerLog(descrption = "添加通知", actionType = "1",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification", method = RequestMethod.POST, produces = "application/json" )
    public String insertObject(@ApiParam ( value = "通知名称", required = true ) @RequestParam ( value = "title" ) String title,
                               @ApiParam ( value = "类型", required = true ) @RequestParam ( value = "type" ) int type,
                               @ApiParam ( value = "内容", required = true ) @RequestParam ( value = "content" ) String content,
                               @ApiParam ( value = "用户id字符串" ) @RequestParam ( value = "groupIds" , required = false) String groupIds,
                               @ApiParam ( value = "创建人id", required = true ) @RequestParam ( value = "userId" ) int userId) {
        try {
            //参数判断
            dataValidation.chkLength(title, 1, 100, "标题长度不符合规则");
            dataValidation.chkLength(content, 1, 5000, "内容长度不符合规则");
            //实体封装
            NotificationModel model = new NotificationModel();
            model.setContent(content);
            model.setTitle(title);
            model.setType(type);
            model.setGroupIds(groupIds);
            model.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            model.setCreater(userId);
            model.setState(ConstDefine.STATE_ABLE);
            //调用接口
            int ret = notificationService.insertObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:insertObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        }
        catch (DataValidationException e){
            logger.error("controller:NotificationController. function:insertObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    e.getMessage(), null).toString();
        }
        catch (Exception e) {
            logger.error("controller:NotificationController. function:insertObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 获取收件箱数据
     * HTTP 方式：GET
     * API 路径：/api/notification/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：myInbox
     * 方法返回类型：String
     */
    @ApiOperation ( value = "获取收件箱数据", httpMethod = "GET", response = String.class, notes = "获取收件箱数据-通知管理-收件箱功能使用" )
    @SystemControllerLog(descrption = "获取收件箱数据", actionType = "4",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json" )
    public String myInbox(@ApiParam ( value = "用户id", required = true ) @RequestParam ( value = "userId" ) int userId,
                          @ApiParam ( value = "页索引", required = true ) @PathVariable ( value = "pageIndex" ) String pageIndex,
                          @ApiParam ( value = "页大小", required = true ) @PathVariable ( value = "pageNum" ) String pageNum) {
        try {
            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum));
            }
            /**分页查询条件*/
            Map<String, Object> map = new HashMap<>(4);
            map.put("userId", userId);
            map.put("state", ConstDefine.STATE_ABLE);
            map.put("beginIndex", page.getBeginIndex());
            map.put("endIndex", page.getEndinIndex());
            List<NotificationModel> list = notificationService.myInbox(map, page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:myInbox...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:NotificationController. function:myInbox...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 获取发件箱数据
     * HTTP 方式：GET
     * API 路径：/api/notification/pageIndex/{pageIndex}/pageNum/{pageNum}/userId/{userId}
     * 方法名：myOutbox
     * 方法返回类型：String
     */
    @ApiOperation ( value = "获取发件箱数据", httpMethod = "GET", response = String.class, notes = "获取发件箱数据-通知管理-发件箱功能使用" )
    @SystemControllerLog(descrption = "获取发件箱数据", actionType = "4",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification/pageIndex/{pageIndex}/pageNum/{pageNum}/userId/{userId}", method = RequestMethod.GET, produces = "application/json" )
    public String myOutbox(@ApiParam ( value = "用户id", required = true ) @PathVariable ( value = "userId" ) int userId,
                           @ApiParam ( value = "页索引", required = true ) @PathVariable ( value = "pageIndex" ) String pageIndex,
                           @ApiParam ( value = "页大小", required = true ) @PathVariable ( value = "pageNum" ) String pageNum) {
        try {
            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum));
            }
            /**分页查询条件*/
            Map<String, Object> map = new HashMap<>(4);
            map.put("state", ConstDefine.STATE_ABLE);
            map.put("userId", userId);
            map.put("beginIndex", page.getBeginIndex());
            map.put("endIndex", page.getEndinIndex());
            List<NotificationModel> list = notificationService.myOutbox(map, page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:myOutbox...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:NotificationController. function:myOutbox...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 获取通知详情
     * HTTP 方式：GET
     * API 路径：/api/notification/{id}
     * 方法名：getDetail
     * 方法返回类型：String
     */
    @ApiOperation ( value = "获取通知详情", httpMethod = "GET", response = String.class, notes = "获取通知详情-通知管理-获取通知详情功能使用" )
    @SystemControllerLog(descrption = "获取通知详情", actionType = "4",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification/{id}", method = RequestMethod.GET, produces = "application/json" )
    public String getDetail(@ApiParam ( value = "主键id", required = true ) @PathVariable ( value = "id" ) int id,
                            @ApiParam ( value = "用户id", required = true ) @RequestParam ( value = "userId" ) int userId
                           ) {
        try {
            NotificationModel model = notificationService.getDetail(id,userId);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:getDetail...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:NotificationController. function:getDetail...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 获取发件箱通知详情
     * HTTP 方式：GET
     * API 路径：/api/notification/{id}
     * 方法名：getDetail
     * 方法返回类型：String
     */
    @ApiOperation ( value = "获取发件箱通知详情", httpMethod = "GET", response = String.class, notes = "获取发件箱通知详情-通知管理-获取发件箱通知详情功能使用" )
    @SystemControllerLog(descrption = "获取通知详情", actionType = "4",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification/outbox/{id}", method = RequestMethod.GET, produces = "application/json" )
    public String getOutBoxDetail(@ApiParam ( value = "主键id", required = true ) @PathVariable ( value = "id" ) int id) {
        try {
            NotificationModel model = notificationService.getOutDetail(id);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:getDetail...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:NotificationController. function:getDetail...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 删除通知
     * HTTP 方式：DELETE
     * API 路径：/api/notification/
     * 方法名：deleteObject
     * 方法返回类型：String
     */
    @ApiOperation ( value = "删除通知", httpMethod = "DELETE", response = String.class, notes = "删除通知-通知管理-删除通知功能使用" )
    @SystemControllerLog(descrption = "删除通知", actionType = "2",modules = "通知管理模块")
    @RequestMapping ( value = "/api/notification", method = RequestMethod.DELETE, produces = "application/json" )
    public String deleteObject(@ApiParam ( value = "用户id", required = true ) @RequestParam ( value = "userId" ) int userId,
                               @ApiParam ( value = "通知id用,分隔", required = true ) @RequestParam ( value = "notificationId" ) String notificationId,
                               @ApiParam ( value = "1=收件箱，2=发件箱", required = true ) @RequestParam ( value = "type" ) int type) {
        try {
          int ret=  notificationService.updateStatus(userId,notificationId,type);
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), null).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (ServiceException e) {
            logger.error("controller:NotificationController. function:deleteObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SERVICE_ERROR,
                    e.getMessage(), null).toString();
        } catch (DataAccessException e) {
            logger.error("controller:NotificationController. function:deleteObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:NotificationController. function:deleteObject...msg:An exception occurred when adding a notification.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
