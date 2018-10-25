package com.aorise.controller.system;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DataValidationException;
import com.aorise.service.system.SystemService;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.validation.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/27 9:27
 * @Modified By:
 */
@RestController
@Api(description = "角色相关")
public class RoleController {
    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataValidation dataValidation;
    @Autowired
    private SystemService systemService;
    /**
     * 添加用户
     * HTTP 方式：PUT
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "统计用户组人数", httpMethod = "GET", response = String.class, notes = "系统管理-组织架构管理-统计用户组人数")
    @SystemControllerLog(descrption = "统计用户组人数", actionType = "4",modules = "组织架构管理")
    @RequestMapping(value = "/api/role", method = RequestMethod.GET, produces = "application/json")
    public String findRoleCount() {
        try {
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), systemService.findRoleCount()).toString();

        } catch (DataAccessException e) {
            logger.error("controller:RoleController. function:findRoleList...msg:An exception occurred when findRoleCount a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:RoleController. function:findRoleList...msg:An exception occurred when findRoleCount a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:RoleController. function:findRoleList...msg:An exception occurred when findRoleCount a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
}
