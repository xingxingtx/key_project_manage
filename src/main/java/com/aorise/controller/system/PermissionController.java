package com.aorise.controller.system;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DataValidationException;
import com.aorise.model.system.SysPermissionModel;
import com.aorise.service.system.SystemService;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.validation.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:ZGP
 * @Desicription: 权限相关
 * @Date:Created in 2018/9/25 16:24
 * @Modified By:
 */

@RestController
@Api(description = "权限相关接口")
public class PermissionController {
    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataValidation dataValidation;
    @Autowired
    private SystemService systemService;


    /**
     * 添加权限角色
     * HTTP 方式：PUT
     * API 路径：/api/system
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "添加权限-角色", httpMethod = "POST", response = String.class, notes = "系统管理-组织架构管理-添加权限-角色关系")
    @SystemControllerLog(descrption = "添加权限-角色", actionType = "1",modules = "权限模块")
    @RequestMapping(value = "/api/permission/role", method = RequestMethod.POST, produces = "application/json")
    public String insertPerRole(@ApiParam(value = "权限id集合用“,”隔开", required = true) @RequestParam(value = "ids", required = true) String ids,
                                @ApiParam(value = "角色id", required = true) @RequestParam(value = "roleId", required = true) String roleId) {
        try {
            //参数判断
            dataValidation.chekeNotempty(ids, "id不能为空");
            dataValidation.chekeNotempty(roleId, "角色id不能为空");
            int ret = systemService.insertRolePermission(Integer.parseInt(roleId), ids.split(","));
            //调用接口
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), null).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:PermissionController. function:insertObject...msg:An exception occurred when insertObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:PermissionController. function:insertObject...msg:An exception occurred when insertObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:PermissionController. function:insertObject...msg:An exception occurred when insertObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加权限
     * HTTP 方式：PUT
     * API 路径：/api/system
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "添加权限", httpMethod = "POST", response = String.class, notes = "系统管理-组织架构管理-添加权限")
    @SystemControllerLog(descrption = "添加权限", actionType = "1",modules = "权限模块")
    @RequestMapping(value = "/api/permission", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String insertPermission(@ApiParam(value = "名称", required = true) @RequestParam(value = "name", required = true) String name,
                                   @ApiParam(value = "父级id", required = true) @RequestParam(value = "parent", required = true) String parent,
                                   @ApiParam(value = "编码", required = true) @RequestParam(value = "code", required = true) String code,
                                   @ApiParam(value = "说明", required = false) @RequestParam(value = "describes", required = false) String describes) {
        try {
            //参数判断
            dataValidation.chekeNotempty(name, "名称不能为空");
            dataValidation.chekeNotempty(code, "编码不能为空");

            SysPermissionModel model = new SysPermissionModel();
            model.setParent(Integer.parseInt(parent));
            model.setName(name);
            model.setCode(code);
            model.setDescribes(describes);
            int ret = systemService.insertPermission(model);
            //调用接口
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), null).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:PermissionController. function:insertPermission...msg:An exception occurred when insertPermission a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:PermissionController. function:insertPermission...msg:An exception occurred when insertPermission a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:PermissionController. function:insertPermission...msg:An exception occurred when insertPermission a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加用户
     * HTTP 方式：PUT
     * API 路径：/api/system
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "查看权限列表", httpMethod = "GET", response = String.class, notes = "系统管理-组织架构管理-查看权限列表")
    @SystemControllerLog(descrption = "查看权限列表", actionType = "4",modules = "权限模块")
    @RequestMapping(value = "/api/permission", method = RequestMethod.GET, produces = "application/json")
    public String findPerList() {
        try {
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), systemService.findPerList()).toString();

        } catch (DataAccessException e) {
            logger.error("controller:PermissionController. function:findPerList...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:PermissionController. function:findPerList...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:PermissionController. function:findPerList...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
    /**
     * 根据角色id查询权限列表
     * HTTP 方式：PUT
     * API 路径：/api/system
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据角色id查询权限列表", httpMethod = "GET", response = String.class, notes = "系统管理-组织架构管理-根据角色id查询权限列表")
    @SystemControllerLog(descrption = "根据角色id查询权限列表", actionType = "4",modules = "权限模块")
    @RequestMapping(value = "/api/permission/roleId/{roleId}", method = RequestMethod.GET, produces = "application/json")
    public String findPerListByRoleId(@ApiParam(value = "权限id", required = true) @PathVariable(value = "roleId") String roleId) {
        try {
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), systemService.findPerListByRoleId(Integer.parseInt(roleId))).toString();

        } catch (DataAccessException e) {
            logger.error("controller:PermissionController. function:findPerListByRoleId...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:PermissionController. function:findPerListByRoleId...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:PermissionController. function:findPerListByRoleId...msg:An exception occurred when findPerList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
}
