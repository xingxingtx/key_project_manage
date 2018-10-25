package com.aorise.controller.system;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.config.Config;
import com.aorise.exceptions.DataValidationException;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.system.SystemService;
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
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:用户管理相关接口
 * @Date:Created in 2018/9/21 14:14
 * @Modified By:
 */
@RestController
@Api(description = "用户管理相关接口")
public class UserController {

    @Autowired
    private SystemService SystemService;

    @Autowired
    private DataValidation dataValidation;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加用户
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "添加用户", httpMethod = "POST", response = String.class, notes = "系统管理-用户管理-添加用户")
    @SystemControllerLog(descrption = "添加用户", actionType = "1",modules = "用户模块")
    @RequestMapping(value = "/api/user", method = RequestMethod.POST, produces = "application/json")
    public String insertObject(@ApiParam(value = "用户名", required = true) @RequestParam(value = "userName", required = true) String userName,
                               @ApiParam(value = "部门", required = false) @RequestParam(value = "deptId", required = false) String deptId,
                               @ApiParam(value = "姓名", required = true) @RequestParam(value = "fullName", required = true) String fullName,
                               @ApiParam(value = "地区", required = false) @RequestParam(value = "adder", required = false) String adder,
                               @ApiParam(value = "电话", required = true) @RequestParam(value = "phone", required = true) String phone,
                               @ApiParam(value = "组别(角色)传数字 1,联保办 2,联保办组长,3,领导组,4,预警员", required = true)
                               @RequestParam(value = "groups", required = true) String groups) {
        try {
            //参数判断
            dataValidation.chkLength(userName, 1, 20, "用户名长度不符合");
            dataValidation.chekeMobile(phone,"手机号码格式错误");
            if(!"4".equals(groups)){
                dataValidation.chekeNotempty(adder,  "地区不能为空");
            }
            dataValidation.chekeNotempty(groups,  "组别不能为空");
            //实体封装
            SysUserModel model = new SysUserModel();
            model.setUserName(userName);
            model.setPassWord(Utils.getMd5DigestAsHex(phone.substring(phone.length()-6)));//默认电话号码后6位
            model.setFullName(fullName);
            model.setDeptId(deptId);
            model.setAdder(adder);
            model.setPhone(phone);
            model.setGroups(groups);
            model.setState(ConstDefine.STATE_ABLE);
            model.setCreater(((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId());//加权限之后通过权限取得id
            model.setCreateTime(Config.DATE_FORMAT.format(new Date()));
            //判断用户是否存在
            SysUserModel oldModel = SystemService.findByUsername(model.getUserName());
            if (oldModel != null) {
                return new JsonResponseData(false, "", StatusDefine.U_EXIST_USER,
                        StatusDefineMessage.GetMessage(StatusDefine.U_EXIST_USER), null).toString();
            }
            //调用接口
            int ret = SystemService.insertObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS),  model.getId()).toString();//返回用户id
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:insertObject...msg:An exception occurred when adding a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:updateObject...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), e.getMessage()).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:insertObject...msg:An exception occurred when adding a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 删除
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改用户", httpMethod = "PUT", response = String.class, notes = "系统管理-用户管理-编辑")
    @SystemControllerLog(descrption = "修改用户", actionType = "3",modules = "用户模块")
    @RequestMapping(value = "/api/user", method = RequestMethod.PUT, produces = "application/json")
    public String editeObject(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                              @ApiParam(value = "用户名", required = true) @RequestParam(value = "userName", required = true) String userName,
                              @ApiParam(value = "部门", required = false) @RequestParam(value = "deptId", required = false) String deptId,
                              @ApiParam(value = "姓名", required = true) @RequestParam(value = "fullName", required = true) String fullName,
                              @ApiParam(value = "地区", required = true) @RequestParam(value = "adder", required = true) String adder,
                              @ApiParam(value = "电话", required = true) @RequestParam(value = "phone", required = true) String phone,
                              @ApiParam(value = "组别(角色)传数字 1,联保办 2,联保办组长,3,领导组,4,预警员", required = true)
                              @RequestParam(value = "groups", required = true) String groups) {
        try {
            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");
            dataValidation.chkLength(userName, 1, 20, "用户名长度不符合");
            dataValidation.chkLength(phone, 1, 20, "联系方式长度不符合");
            dataValidation.chekeNotempty(groups,  "组别不能为空");
            //实体封装
            SysUserModel model = SystemService.findObject(Integer.parseInt(userId));
            model.setUserName(userName);
            model.setFullName(fullName);
            model.setDeptId(deptId);
            model.setAdder(adder);
            model.setPhone(phone);
            model.setGroups(groups);
            model.setEditer(((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId());//加权限之后通过权限取得id
            model.setEditeTime(Config.DATE_FORMAT.format(new Date()));
            //判断用户是否存在
            SysUserModel oldModel = SystemService.findByUsername(model.getUserName());
            if (oldModel != null && oldModel.getId() != Integer.parseInt(userId)) {
                return new JsonResponseData(false, "", StatusDefine.U_EXIST_USER,
                        StatusDefineMessage.GetMessage(StatusDefine.U_EXIST_USER), null).toString();
            }
            //调用接口
            int ret = SystemService.editeObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:updateObject...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:updateObject...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:updateObject...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 删除
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改用户-重置密码", httpMethod = "PUT", response = String.class, notes = "系统管理-用户管理-修改用户-重置密码")
    @SystemControllerLog(descrption = "修改用户-重置密码", actionType = "3",modules = "用户模块")
    @RequestMapping(value = "/api/user/restPwd", method = RequestMethod.PUT, produces = "application/json")
    public String editeObjectResetPwd(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId) {
        try {
            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");
            //实体封装
            SysUserModel model = SystemService.findObject(Integer.parseInt(userId));
            if (model == null) {
                return new JsonResponseData(false, "", StatusDefine.U_INEXISTENCE,
                        StatusDefineMessage.GetMessage(StatusDefine.U_INEXISTENCE), null).toString();
            }
            model.setPassWord(Utils.getMd5DigestAsHex(model.getPhone().substring(model.getPhone().length()-6)));//默认电话号码后6位
            model.setEditer(((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId());//加权限之后通过权限取得id
            model.setEditeTime(Config.DATE_FORMAT.format(new Date()));
            //调用接口
            int ret = SystemService.editeObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:editeObjectResetPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:editeObjectResetPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:editeObjectResetPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 修改密码
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改用户-修改密码", httpMethod = "PUT", response = String.class, notes = "系统管理-用户管理-修改用户-修改密码")
    @SystemControllerLog(descrption = "修改用户-修改密码", actionType = "3",modules = "用户模块")
    @RequestMapping(value = "/api/user/editPwd", method = RequestMethod.PUT, produces = "application/json")
    public String editeObjectEditPwd(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                     @ApiParam(value = "原密码", required = true) @RequestParam(value = "oldPassWord", required = true) String oldPassWord,
                                     @ApiParam(value = "新密码", required = true) @RequestParam(value = "newPassWord", required = true) String newPassWord,
                                     @ApiParam(value = "确认新密码", required = true) @RequestParam(value = "affirmNewPassWord", required = true) String affirmNewPassWord) {
        try {
            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");
            dataValidation.chekeNotempty(oldPassWord, "原密码不能为空");
            dataValidation.chkLength(newPassWord, 6, 16, "新密码长度不正确");
            //实体封装
            SysUserModel model = SystemService.findObject(Integer.parseInt(userId));
            if (!newPassWord.equals(affirmNewPassWord)) {
                return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                        StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), "两次密码不一致").toString();
            }
            if(!Utils.getMd5DigestAsHex(oldPassWord).equals(model.getPassWord())){
                return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                        StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), "原密码不正确").toString();
            }
            if (model == null) {
                return new JsonResponseData(false, "", StatusDefine.U_INEXISTENCE,
                        StatusDefineMessage.GetMessage(StatusDefine.U_INEXISTENCE), null).toString();
            }
            model.setPassWord(Utils.getMd5DigestAsHex(newPassWord));
            model.setEditer(((SysUserModel) SecurityUtils.getSubject().getPrincipal()).getId());//加权限之后通过权限取得id
            model.setEditeTime(Config.DATE_FORMAT.format(new Date()));
            //调用接口
            int ret = SystemService.editeObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:editeObjectEditPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:editeObjectEditPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), e.getMessage()).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:editeObjectEditPwd...msg:An exception occurred when update a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加用户
     * HTTP 方式：DELETE
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除用户", httpMethod = "DELETE", response = String.class, notes = "系统管理-用户管理-删除用户")
    @SystemControllerLog(descrption = "删除用户", actionType = "2",modules = "用户模块")
    @RequestMapping(value = "/api/user", method = RequestMethod.DELETE, produces = "application/json")
    public String deleteObject(@ApiParam(value = "用户id集合用“,”隔开", required = true) @RequestParam(value = "ids", required = true) String ids) {
        try {
            //参数判断
            dataValidation.chekeNotempty(ids, "id不能为空");
            //调用接口
            int ret = SystemService.deleteObject(ids.split(","));
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when delete a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when delete a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when delete a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加用户
     * HTTP 方式：DELETE
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据id查询用户", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-编辑用户信息展示")
    @SystemControllerLog(descrption = "根据id查询用户", actionType = "4",modules = "用户模块")
    @RequestMapping(value = "/api/user/userId/{userId}", method = RequestMethod.GET, produces = "application/json")
    public String findObjectByuserid(@ApiParam(value = "用户id", required = true) @PathVariable(value = "userId", required = true) String userId) {
        try {
            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");

            //调用接口
            SysUserModel model = SystemService.findObject(Integer.parseInt(userId));
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when findUserObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when findUserObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when findUserObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 分页查询
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询用户列表", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-用户列表展示")
    @SystemControllerLog(descrption = "分页查询用户列表", actionType = "4",modules = "用户模块")
    @RequestMapping(value = "/api/user/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public String findUserListByPage(@ApiParam(value = "姓名或者账号", required = false) @RequestParam(value = "userName", required = false) String userName,
                                     @ApiParam(value = "部门", required = false) @RequestParam(value = "deptId", required = false) String deptId,
                                     @ApiParam(value = "地区", required = false) @RequestParam(value = "adder", required = false) String adder,
                                     @ApiParam(value = "组别 1,联保办 2,联保办组长,3,领导组,4,预警员", required = false) @RequestParam(value = "groups", required = false) String groups,
                                     @ApiParam(value = "账号状态1可用，2冻结", required = false) @RequestParam(value = "state", required = false) String state,
                                     @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex") String pageIndex,
                                     @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum") String pageNum) {
        try {

            SysUserModel model = new SysUserModel();
            model.setUserName(userName);
            model.setDeptId(deptId);
            model.setAdder(adder);
            model.setGroups(groups);
            if (!StringUtils.isBlank(state)) {
                model.setState(Integer.parseInt(state));
            }

            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum));
            }
            //调用接口
            List<SysUserModel> list = SystemService.findUserList(model, page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:findUserListByPage...msg:An exception occurred when findUserListByPage a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:findUserListByPage...msg:An exception occurred when findUserListByPage a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:findUserListByPage...msg:An exception occurred when findUserListByPage a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }


    /**
     * 添加用户
     * HTTP 方式：PUT
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "冻结或解冻用户", httpMethod = "PUT", response = String.class, notes = "系统管理-用户管理-冻结解冻用户")
    @SystemControllerLog(descrption = "冻结或解冻用户", actionType = "3",modules = "用户模块")
    @RequestMapping(value = "/api/user/state", method = RequestMethod.PUT, produces = "application/json")
    @Transactional
    public String editStateObject(@ApiParam(value = "用户id集合用“,”隔开", required = true) @RequestParam(value = "ids", required = true) String ids,
                                  @ApiParam(value = "state 1解冻，2冻结", required = true) @RequestParam(value = "state", required = true) String state) {
        try {
            //参数判断
            dataValidation.chekeNotempty(ids, "id不能为空");
            dataValidation.chekeNotempty(state, "state不能为空");
            int ret = 0;
            for (String id : ids.split(",")) {
                SysUserModel model = SystemService.findObject(Integer.parseInt(id));
                model.setState(Integer.parseInt(state));
                ret += SystemService.editeObject(model);
            }
            //调用接口
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), null).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when editStateObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when editStateObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:deleteObject...msg:An exception occurred when editStateObject a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加用户
     * HTTP 方式：PUT
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询角色列表", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-组别下拉框")
    @SystemControllerLog(descrption = "查询角色列表", actionType = "4",modules = "用户模块")
    @RequestMapping(value = "/api/user/roleList", method = RequestMethod.GET, produces = "application/json")
    public String findRoleList() {
        try {

            List<SysRoleModel> list=SystemService.findRoleList();
            Iterator it=list.iterator();
            while(it.hasNext()){
                SysRoleModel model=(SysRoleModel)it.next();
                if("预警员".equals(model.getName())||"超级管理员".equals(model.getName())){
                    it.remove();
                };
            }
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), list).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:findRoleList...msg:An exception occurred when findRoleList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:findRoleList...msg:An exception occurred when findRoleList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:findRoleList...msg:An exception occurred when findRoleList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 添加用户
     * HTTP 方式：DELETE
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据角色地区code查询用户列表", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-根据地区Code查询用户列表")
    @SystemControllerLog(descrption = "根据角色地区code查询用户列表", actionType = "4",modules = "用户模块")
    @RequestMapping(value = "/api/user/adderCode/{adderCode}", method = RequestMethod.GET, produces = "application/json")
    public String findObjectByRoleId(@ApiParam(value = "用户地区code", required = true) @PathVariable(value = "adderCode", required = true) String adder) {
        try {
            //调用接口
            List<SysUserModel> models = SystemService.findByRoleId(0,adder);
            SysUserModel model=(SysUserModel) SecurityUtils.getSubject().getPrincipal();
            Iterator it=models.iterator();
            while(it.hasNext()){
                SysUserModel imodel=(SysUserModel)it.next();
                if(imodel.getUserName().equals(model.getUserName())){
                    it.remove();
                }
            }
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), models).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:findObjectByRoleId...msg:An exception occurred when findObjectByRoleId a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:findObjectByRoleId...msg:An exception occurred when findObjectByRoleId a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:findObjectByRoleId...msg:An exception occurred when findObjectByRoleId a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 分页查询
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据用户姓名查询用户列表", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-通讯录")
    @RequestMapping(value = "/api/user/fullName", method = RequestMethod.GET, produces = "application/json")
    @SystemControllerLog(descrption = "根据用户姓名查询用户列表", actionType = "4",modules = "根据用户姓名查询用户列表")
    public String findUserListByFullName(@ApiParam(value = "姓名", required = false) @RequestParam(value = "fullName", required = false) String fullName) {
        try {

            //调用接口
            List<SysUserModel> list = SystemService.findUserListByFullName(fullName);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), list).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:findUserListByFullName...msg:An exception occurred when findUserList a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:findUserListByFullName...msg:An exception occurred when findUserListBy a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:findUserListByFullName...msg:An exception occurred when findUserListBy a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 分页查询
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询所有的部门", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-查询所有的部门")
    @RequestMapping(value = "/api/user/depart", method = RequestMethod.GET, produces = "application/json")
    @SystemControllerLog(descrption = "查询所有的部门", actionType = "4",modules = "查询所有的部门")
    public String getDepartment() {
        try {
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), Utils.getDepartment()).toString();

        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:getDepartment...msg:An exception occurred when getDepartment a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:getDepartment...msg:An exception occurred when getDepartment a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SystemController. function:getDepartment...msg:An exception occurred when getDepartment a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
    /**
     * 删除
     * HTTP 方式：POST
     * API 路径：/api/user
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "判断用户是否存在", httpMethod = "GET", response = String.class, notes = "判断用户是否存在")
    @SystemControllerLog(descrption = "添加用户-判断用户是否存在", actionType = "3",modules = "用户模块")
    @RequestMapping(value = "/api/user/userName/{userName}", method = RequestMethod.GET, produces = "application/json")
    public String judgeUserExist(@ApiParam(value = "用户名", required = true) @PathVariable(value = "userName", required = true) String userName) {
        try {
            //参数判断
            dataValidation.chekeNotempty(userName, "用户名不能为空");
            //判断用户是否存在
            SysUserModel oldModel = SystemService.findByUsername(userName);
            if (oldModel != null) {
                return new JsonResponseData(false, "", StatusDefine.U_EXIST_USER,
                        StatusDefineMessage.GetMessage(StatusDefine.U_EXIST_USER), false).toString();
            }else{
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), true).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:SystemController. function:judgeUserExist...msg:An exception occurred when judgeUserExist a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:SystemController. function:judgeUserExist...msg:An exception occurred when judgeUserExist a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("controller:SystemController. function:judgeUserExist...msg:An exception occurred when judgeUserExist a user.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
}
