package com.aorise.controller.stopwork;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.config.Config;
import com.aorise.exceptions.DBErrorException;
import com.aorise.exceptions.DataValidationException;
import com.aorise.exceptions.ProjectException;
import com.aorise.exceptions.ServiceException;
import com.aorise.model.stopwork.StopWorkInfoModel;
import com.aorise.model.stopwork.StopWorkModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.stopwork.StopWorkService;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:施工现场阻工
 * @Author:wei.peng
 * @Date:2018/9/27 9:25
 * @Version V1.0
 */
@RestController
@Api(description = "施工现场阻工")
public class StopWorkController {
    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StopWorkService stopWorkService;
    @Autowired
    private DataValidation validation;
    /**
     * 新增施工现场阻工详情
     * HTTP 方式：POST
     * API 路径：/api/stopWork/addStopWorkInfo
     * 方法名：addStopWorkInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDXZ")
    @ApiOperation(value = "新增施工现场阻工详情", httpMethod = "POST", response = String.class,
            notes = "新增施工现场阻工详情，供后台新增施工现场阻工详情使用")
    @SystemControllerLog(descrption = "新增施工现场阻工详情", actionType = "1",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWorkInfo", method = RequestMethod.POST)
    public String addStopWorkInfo(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                    @ApiParam(value = "项目id", required = true) @RequestParam(value = "projectId", required = true) String projectId,
                                    @ApiParam(value = "项目名称", required = true) @RequestParam(value = "name", required = true) String name,
                                    @ApiParam(value = "阻工时间:yyyy-MM-dd HH:mm:ss", required = true) @RequestParam(value = "stopTime", required = true) String stopTime,
                                    @ApiParam(value = "阻工地点", required = false) @RequestParam(value = "address", required = false) String address,
                                    @ApiParam(value = "阻工人数", required = true) @RequestParam(value = "preventNumber", required = true) String preventNumber,
                                    @ApiParam(value = "阻工原因", required = true) @RequestParam(value = "preventReason", required = true) String preventReason,
                                    @ApiParam(value = "阻工情况描述", required = false) @RequestParam(value = "content", required = false) String content,
                                    @ApiParam(value = "上报及现场处置人员", required = false) @RequestParam(value = "alertReporter", required = false) String alertReporter,
                                    @ApiParam(value = "现场处置措施及结果", required = false) @RequestParam(value = "disposeResult", required = false) String disposeResult,
                                    @ApiParam(value = "处理状态", required = true) @RequestParam(value = "disposeState", required = true) String disposeState,
                                    @ApiParam(value = "区域代码", required = false) @RequestParam(value = "county", required = false) String county,
                                    @ApiParam(value = "其他阻工原因", required = false) @RequestParam(value = "other", required = false) String other,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token
                                ){
        logger.info("用户id：" + userId);
        logger.info("项目id",projectId);
        logger.info("项目名称：" + name);
        logger.info("阻工时间：" + stopTime);
        logger.info("阻工地点：" + address);
        logger.info("阻工人数：" + preventNumber);
        logger.info("阻工原因：" + preventReason);
        logger.info("阻工情况描述：" + content);
        logger.info("上报及现场处置人员：" + alertReporter);
        logger.info("现场处置措施及结果：" + disposeResult);
        logger.info("处理状态：" + disposeState);
        logger.info("区域代码" + county);
        logger.info("其他阻工原因" + other);
        try {
            //验证阻工人数
            validation.chkLength( preventNumber,0,20,"阻工人数仅支持数字输入，长度不超过20个字符");
            validation.chekeNumber( preventNumber,"阻工人数仅支持数字输入");
            //验证项目阻工地点
            Utils.validation(null,address,0,100,"项目阻工地点仅支持大小写字母、数字及汉字输入，长度不超过100个字符");
            //阻工情况描述
            Utils.validation(null,content,0,1000,"阻工情况描述仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");
            //现场处置措施及结果
            Utils.validation(null,disposeResult,0,1000,"现场处置措施及结果仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");
            //上报及现场处置人员
            Utils.validation(null,alertReporter,0,100,"上报及现场处置人员仅支持大小写字母、数字及汉字输入，长度不超过100个字符");
            StopWorkInfoModel info= new StopWorkInfoModel();
            info.setName(name);
            info.setStopTime(Config.DATE_FORMAT.parse(stopTime));
            info.setAddr(address);
            info.setPreventNumber(preventNumber);
            info.setPreventReason(preventReason);
            info.setContent(content);
            info.setAlertReporter(alertReporter);
            info.setDisposeResult(disposeResult);
            info.setDisposeState(Integer.parseInt(disposeState));
            info.setProjectId(Integer.parseInt(projectId));
            info.setCounty(county);
            info.setOther(other);
            //新增施工现场阻工详情信息
            int rel = stopWorkService.addStopWorkInfo(userId,info);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功",  rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException d){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 新增施工现场阻工处理信息
     * HTTP 方式：POST
     * API 路径：/api/stopWork/addStopWork
     * 方法名：addStopWork
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDXZ")
    @ApiOperation(value = "新增施工现场阻工处理信息", httpMethod = "POST", response = String.class,
            notes = "新增施工现场阻工处理信息，供后台新增施工现场阻工处理信息使用")
    @SystemControllerLog(descrption = "新增施工现场阻工处理信息", actionType = "1",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWork", method = RequestMethod.POST)
    public String addStopWork(@ApiParam(value = "用户id", required = false) @RequestParam(value = "userId", required = false) String userId,
                                @ApiParam(value = "指派处理人员", required = false) @RequestParam(value = "disposePeople", required = false) String disposePeople,
                                @ApiParam(value = "指定完成时间", required = false) @RequestParam(value = "fulfillTime", required = false) String fulfillTime,
                                @ApiParam(value = "完成要求", required = false) @RequestParam(value = "fulfillRequire", required = false) String fulfillRequire,
                                @ApiParam(value = "施工现场阻工详情id", required = false) @RequestParam(value = "workId", required = false) String workId,
                                @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token,
                              HttpServletRequest request
    ){
        logger.info("指派处理人员：" + disposePeople);
        logger.info("指定完成时间：" + fulfillTime);
        logger.info("完成要求：" + fulfillRequire);
        logger.info("施工现场阻工详情id",workId);
        logger.info("用户id" + userId);
        try {
            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() +":" + request.getServerPort()+"/";
            //验证完成要求
           Utils.validation(null,fulfillRequire,0,1000,"完成要求仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");
            StopWorkModel info = new StopWorkModel();
            info.setDisposePeople(disposePeople);
            info.setFulfillTime(Config.DATE_FORMAT.parse(fulfillTime));
            info.setFulfillRequire(fulfillRequire);
            info.setWorkId(Integer.parseInt(workId));
            info.setDisposeState("1");//1未处理
            int rel = stopWorkService.addStopWork(userId, info, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWork..msg:新增施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWork..msg:新增施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:addStopWork..msg:新增施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (DataValidationException d){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工处理出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工处理出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ServiceException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:新增施工现场阻工处理的通知推送出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWork..msg:新增施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 修改施工现场阻工处理信息
     * HTTP 方式：POST
     * API 路径：/api/stopWork/updateStopWork
     * 方法名：updateStopWork
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "修改施工现场阻工处理信息", httpMethod = "PUT", response = String.class,
            notes = "修改施工现场阻工处理信息，供后台修改施工现场阻工处理信息使用")
    @SystemControllerLog(descrption = "修改施工现场阻工处理信息", actionType = "3",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWork", method = RequestMethod.PUT)
    public String updateStopWork(@ApiParam(value = "修改人id", required = true) @RequestParam(value = "editer", required = true) String editer,
                                    @ApiParam(value = "处理时间", required = false) @RequestParam(value = "disposeTime", required = false) String disposeTime,
                                    @ApiParam(value = "现场处置措施及结果", required = false) @RequestParam(value = "appointedResults", required = false) String appointedResults,
                                    @ApiParam(value = "施工现场阻工处理id", required = false) @RequestParam(value = "id", required = false) String id,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token
    ){
        logger.info("施工现场阻工处理id：" + id);
        logger.info("处理时间"+disposeTime);
        logger.info("现场处置措施及结果"+appointedResults);
        logger.info("修改人id" + editer);
        try {
            //验证现场处置措施及结果
            Utils.validation(null,appointedResults,0,1000,"现场处置措施及结果仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");

            StopWorkModel info = new StopWorkModel();
            info.setDisposeTime(Config.DATE_FORMAT.parse(disposeTime));
            info.setAppointedResults(appointedResults);
            info.setEditer(Integer.parseInt(editer));
            info.setId(Integer.parseInt(id));
            info.setDisposeState("0");//0：已经处理
            int rel = stopWorkService.updateStopWork(editer,info);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "更新成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "更新失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException d){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:修改施工现场阻工处理信息出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p) {
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:修改施工现场阻工处理信息出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (Exception e) {
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 修改施工现场阻工信息
     * HTTP 方式：POST
     * API 路径：/api/stopWork/updateStopWorkInfo
     * 方法名：updateStopWork
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "修改施工现场阻工信息", httpMethod = "PUT", response = String.class,
            notes = "修改施工现场阻工信息，供后台修改施工现场阻工信息使用")
    @SystemControllerLog(descrption = "修改施工现场阻工信息", actionType = "3",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWorkInfo", method = RequestMethod.PUT)
    public String updateStopWorkInfo(@ApiParam(value = "修改人id", required = true) @RequestParam(value = "editer", required = true) String editer,
                                     @ApiParam(value = "处理状态1-进行中 2-已解决", required = false) @RequestParam(value = "disposeState", required = false) String disposeState,
                                     @ApiParam(value = "是否满意:1-满意 2-不满意", required = false) @RequestParam(value = "isSatisfy", required = false) String isSatisfy,
                                     @ApiParam(value = "原因", required = false) @RequestParam(value = "cause", required = false) String cause,
                                     @ApiParam(value = "施工现场阻工id", required = true) @RequestParam(value = "id", required = true) String id,
                                     @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token,
                                     HttpServletRequest request
    ){
        logger.info("施工现场阻工id：" + id);
        logger.info("是否满意:1-满意 2-不满意"+isSatisfy);
        logger.info("原因"+cause);
        logger.info("修改人id" + editer);
        logger.info("是否满意:1-满意 2-不满意" + disposeState);
        try {
            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() +":" + request.getServerPort()+"/";
            //原因
           Utils.validation(null,cause,0,200,"施工现场阻工原因仅支持大小写字母、数字及汉字输入，长度不超过200个字符");

            StopWorkInfoModel info = new StopWorkInfoModel();
            info.setCause(cause);
            if(!StringUtils.isBlank(isSatisfy)){
                info.setIsSatisfy(Integer.parseInt(isSatisfy));
            }
            info.setEditer(Integer.parseInt(editer));
            info.setId(Integer.parseInt(id));
            if(!StringUtils.isBlank(disposeState)){
                info.setDisposeState(Integer.parseInt(disposeState));
            }

            int rel = stopWorkService.updateStopWorkInfo(editer,info, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "更新成功", rel ).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "更新失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (DataValidationException d){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:修改施工现场阻工处理信息出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ServiceException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:修改施工现场阻工处理信息的通知推送出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController.");
            logger.error("function:updateStopWork..msg:修改施工现场阻工处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 分页查询施工现场阻工
     * HTTP 方式：
     * API 路径：
     * 方法名：
     * 方法返回类型：String
     */
    @RequiresPermissions("LBXDJBQK")
    @ApiOperation ( value = "查询列表", httpMethod = "GET", response = String.class, notes = "分页查询-施工现场阻工管理-列表查询接口使用" )
    @SystemControllerLog(descrption = "分页查询施工现场阻工", actionType = "4",modules = "施工现场阻工模块")
    @RequestMapping ( value = "/api/stopWorkInfo/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json" )
    public String getPageList(@ApiParam ( value = "页索引", required = true ) @PathVariable( value = "pageIndex" ) String pageIndex,
                              @ApiParam ( value = "页大小", required = true ) @PathVariable ( value = "pageNum" ) String pageNum,
                              @ApiParam ( value = "区县code", required = false ) @RequestParam ( value = "countyId" ,required = false) String countyCode,
                              @ApiParam ( value = "乡镇code", required = false ) @RequestParam ( value = "villageId" ,required = false) String villageCode,
                              @ApiParam ( value = "村庄code", required = false ) @RequestParam ( value = "hamletId" ,required = false) String hamletCode,
                              @ApiParam ( value = "项目id", required = false ) @RequestParam ( value = "projectId" ,required = false) String projectId,
                              @ApiParam ( value = "阻工查询开始时间", required = false ) @RequestParam ( value = "startTime" ,required = false) String startTime,
                              @ApiParam ( value = "阻工查询结束时间", required = false ) @RequestParam ( value = "endTime" ,required = false) String endTime,
                              @ApiParam ( value = "阻工原因", required = false ) @RequestParam ( value = "preventReason" ,required = false) String preventReason,
                              @ApiParam ( value = "处理状态", required = false ) @RequestParam ( value = "disposeState" ,required = false) String disposeState,
                              @ApiParam(value = "是否满意:1-满意 2-不满意", required = false) @RequestParam(value = "isSatisfy", required = false) String isSatisfy,
                              @ApiParam(value = "其他阻工原因", required = false) @RequestParam(value = "other", required = false) String other,
                              @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token

    ){
                logger.info("页索引"+pageIndex);
                logger.info("页大小"+pageNum);
                logger.info("区县id"+countyCode);
                logger.info("乡镇id"+villageCode);
                logger.info("村庄id"+hamletCode);
                logger.info("项目id"+projectId);
                logger.info("阻工查询开始时间"+startTime);
                logger.info("阻工查询结束时间"+endTime);
                logger.info("阻工原因"+preventReason);
                logger.info("是否存在问题"+disposeState);
                logger.info("是否满意",isSatisfy);
                logger.info("其他阻工原因" + other);
        try {
            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum));
            }
            /**分页查询条件*/
            Map<String, Object> map = new HashMap<>();
            map.put("state", ConstDefine.STATE_ABLE);
            map.put("beginIndex", page.getBeginIndex());
            map.put("endIndex", page.getEndinIndex());
            map.put("countyCode",countyCode);
            map.put("villageCode",villageCode);
            map.put("hamletCode",hamletCode);
            map.put("projectId",projectId);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("preventReason",preventReason);
            map.put("disposeState",disposeState);
            map.put("isSatisfy",isSatisfy);
            map.put("other",other);
            //获取当前用户角色
            SysUserModel sysUserModel = (SysUserModel) SecurityUtils.getSubject().getPrincipal();
            List<SysRoleModel> role = sysUserModel.getSysRoles();
            for (int i = 0; i < role.size(); i++) {
                //判断是否是预警员
                if(role.get(i).getName().equals("预警员")){
                    map.put("userId",sysUserModel.getId().toString());
                }else {
                    map.put("userId",null);
                }
            }
            List<StopWorkInfoModel> list = stopWorkService.getAllByPage(map,page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();
        }catch (DataAccessException e) {
            logger.error("controller:InformationController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:InformationController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 删除施工现场阻工详情
     * HTTP 方式：POST
     * API 路径：/api/stopWork/deleteStopWorkInfo
     * 方法名：deleteStopWorkInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDSC")
    @ApiOperation(value = "删除施工现场阻工详情", httpMethod = "DELETE", response = String.class,
            notes = "删除施工现场阻工详情，供后台删除施工现场阻工详情使用")
    @SystemControllerLog(descrption = "删除施工现场阻工详情", actionType = "2",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWorkInfo", method = RequestMethod.DELETE)
    public String deleteStopWorkInfo(@ApiParam(value = "施工现场阻工id", required = true) @RequestParam(value = "id", required = true) Integer[] id,
                                     @ApiParam(value = "修改人id", required = false) @RequestParam(value = "editer", required = false) Integer editer,
                                     @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token

    ){
        logger.info("施工现场阻工id",id);
        try {
            //删除施工现场阻工详情信息
            int rel = stopWorkService.delteStopWorkInfo(id,editer,ConstDefine.STATE_DISABLE);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "删除成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "删除失败", " ").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:删除施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:删除施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:StopWorkController. ");
            logger.error("function:addStopWorkInfo..msg:删除施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController.");
            logger.error("function:addStopWorkInfo..msg:删除施工现场阻工详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * @param
     * @Author:Yangzepeng
     * @Desicription:根据id查询施工现场阻工详情
     * @Date:2018/10/8 16:20
     * @Return:
     * @Modified By:
     */
    @RequiresPermissions("LBXDXQCK")
    @ApiOperation(value = "查询详情", httpMethod = "GET", response = String.class, notes = "详情查询-施工现场阻工管理-详情展示接口使用")
    @SystemControllerLog(descrption = "查询施工现场阻工", actionType = "4",modules = "施工现场阻工模块")
    @RequestMapping(value = "/api/stopWork/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getStopWorkInfo(@ApiParam(value = "id", required = true) @PathVariable(value = "id") Integer id,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token) {

        logger.info("施工现场阻工id" + id);
        try {
            StopWorkInfoModel model = stopWorkService.getStopWorkInfo(id);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (DataAccessException e) {
            logger.error("controller:StopWorkController. function:getStopWorkInfo...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:StopWorkController. function:getStopWorkInfo...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
