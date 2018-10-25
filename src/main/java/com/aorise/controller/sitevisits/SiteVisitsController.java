package com.aorise.controller.sitevisits;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.config.Config;
import com.aorise.exceptions.DBErrorException;
import com.aorise.exceptions.DataValidationException;
import com.aorise.exceptions.ProjectException;
import com.aorise.exceptions.ServiceException;
import com.aorise.model.sitevisits.SiteVisitsInfoModel;
import com.aorise.model.sitevisits.SiteVisitsModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.sitevisits.SiteVisitsService;
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
 * @Description:开工前摸排
 * @Author:wei.peng
 * @Date:2018/9/18 9:25
 * @Version V1.0
 */
@RestController
@Api(description = "开工前摸排")
public class SiteVisitsController {
    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SiteVisitsService siteVisitsService;

    @Autowired
    private DataValidation validation;
    /**
     * 新增开工前摸排详情
     * HTTP 方式：POST
     * API 路径：/api/siteVisits/addSiteVisitsInfo
     * 方法名：addSiteVisitsInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDXZ")
    @ApiOperation(value = "新增开工前摸排详情", httpMethod = "POST", response = String.class,
            notes = "新增开工前摸排详情，供后台新增开工前摸排详情使用")
    @SystemControllerLog(descrption = "新增开工前摸排详情", actionType = "1",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisitsInfo", method = RequestMethod.POST)
    public String addSiteVisitsInfo(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                    @ApiParam(value = "项目id", required = true) @RequestParam(value = "projectId", required = true) String projectId,
                                    @ApiParam(value = "项目名称", required = true) @RequestParam(value = "name", required = true) String name,
                                    @ApiParam(value = "摸排时间", required = true) @RequestParam(value = "visitsTime", required = true) String visitsTime,
                                    @ApiParam(value = "摸排地点", required = false) @RequestParam(value = "address", required = false) String address,
                                    @ApiParam(value = "摸排人数", required = true) @RequestParam(value = "numbers", required = true) String numbers,
                                    @ApiParam(value = "摸排及到场处置人员", required = false) @RequestParam(value = "disposePerson", required = false) String disposePerson,
                                    @ApiParam(value = "是否发现问题0-发现1-未发现", required = true) @RequestParam(value = "isQuestion", required = true) String isQuestion,
                                    @ApiParam(value = "问题描述", required = false) @RequestParam(value = "visitsQuestion", required = false) String visitsQuestion,
                                    @ApiParam(value = "现场处置措施及结果", required = false) @RequestParam(value = "results", required = false) String results,
                                    @ApiParam(value = "处理状态", required = false) @RequestParam(value = "disposeState", required = false) String disposeState,
                                    @ApiParam(value = "区域代码", required = false) @RequestParam(value = "county", required = false) String county,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token
    ) {
        logger.info("用户id：" + userId);
        logger.info("项目id", projectId);
        logger.info("项目名称：" + name);
        logger.info("摸排时间：" + visitsTime);
        logger.info("摸排地点：" + address);
        logger.info("摸排人数：" + numbers);
        logger.info("摸排及到场处置人员：" + disposePerson);
        logger.info("是否发现问题：" + isQuestion);
        logger.info("问题描述：" + visitsQuestion);
        logger.info("现场处置措施及结果：" + results);
        logger.info("处理状态：" + disposeState);
        logger.info("区域代码" + county);
        try {
            //验证摸排地点
            validation.chkLength( address, 0, 100, "摸排地点仅支持大小写字母、数字及汉字输入，长度不超过100个字符");

            //验证摸排及到场处置人员
            validation.chkLength( disposePerson, 0, 100, "摸排及到场处置人员仅支持大小写字母、数字及汉字输入，长度不超过100个字符");

            //验证摸排人数
            validation.chkLength(numbers, 0, 20, "摸排人数仅支持数字输入，长度不超过20个字符");
            validation.chekeNumber(numbers, "摸排人数仅支持数字输入");

            //验证是否发现问题
            validation.chekeNumber(isQuestion, "否发现问题仅支持数字");

            //验证问题描述
            validation.chkLength(visitsQuestion, 0, 1000, "问题描述仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");

            //现场处置措施及结果
            validation.chkLength(results, 0, 1000, "现场处置措施及结果仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");

            SiteVisitsInfoModel info = new SiteVisitsInfoModel();
            info.setName(name);
            info.setVisitsTime(Config.DATE_FORMAT.parse(visitsTime));
            info.setAddress(address);
            info.setNumbers(numbers);
            info.setDisposePerson(disposePerson);
            info.setIsQuestion(Integer.parseInt(isQuestion));
            info.setVisitsQuestion(visitsQuestion);
            info.setResults(results);
            info.setDisposeState(disposeState);
            info.setProjectId(Integer.parseInt(projectId));
            info.setCounty(county);
            //新增开工前摸排详情信息
            int rel = siteVisitsService.addSiteVisitsInfo(userId, info);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SiteVisitsController. ");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException d){
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 新增开工前摸排处理信息
     * HTTP 方式：POST
     * API 路径：/api/siteVisits/addSiteVisits
     * 方法名：addSiteVisits
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "新增开工前摸排处理信息", httpMethod = "POST", response = String.class,
            notes = "新增开工前摸排处理信息，供后台新增开工前摸排处理信息使用")
    @SystemControllerLog(descrption = "新增开工前摸排处理信息", actionType = "1",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisits", method = RequestMethod.POST)
    public String addSiteVisits(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                @ApiParam(value = "指派处理人员", required = true) @RequestParam(value = "disposePeople", required = true) String disposePeople,
                                @ApiParam(value = "指定完成时间", required = true) @RequestParam(value = "fulfillTime", required = true) String fulfillTime,
                                @ApiParam(value = "完成要求", required = true) @RequestParam(value = "fulfillRequire", required = true) String fulfillRequire,
                                @ApiParam(value = "开工前摸排详情id", required = true) @RequestParam(value = "visitsId", required = true) String visitsId,
                                @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token,
                                HttpServletRequest request
    ) {
        logger.info("指派处理人员：" + disposePeople);
        logger.info("指定完成时间：" + fulfillTime);
        logger.info("完成要求：" + fulfillRequire);
        logger.info("开工前摸排详情id", visitsId);
        logger.info("用户id" + userId);
        try {
            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() +":" + request.getServerPort()+"/";
            //验证完成要求
            validation.chkLength(fulfillRequire, 0, 1000, "完成要求仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");

            SiteVisitsModel info = new SiteVisitsModel();
            info.setDisposePeople(disposePeople);
            info.setFulfillTime(Config.DATE_FORMAT.parse(fulfillTime));
            info.setFulfillRequire(fulfillRequire);
            info.setVisitsId(Integer.parseInt(visitsId));
            info.setDisposeState("1");//1：未处理状态
            int rel = siteVisitsService.addSiteVisits(userId, info, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisits..msg:新增开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisits..msg:新增开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SiteVisitsController. ");
            logger.error("function:addSiteVisits..msg:新增开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException d){
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排处理信息出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:新增开工前摸排处理信息出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ServiceException e){
            logger.error("controller:ConstructionHiddenController.");
            logger.error("function:addConstructionHiddenInfo..msg:新增开工前摸排处理信息的通知推送出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisits..msg:新增开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 修改开工前摸排处理信息
     * HTTP 方式：POST
     * API 路径：/api/siteVisits/updateSiteVisits
     * 方法名：updateSiteVisits
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "修改开工前摸排处理信息", httpMethod = "PUT", response = String.class,
            notes = "修改开工前摸排处理信息，供后台修改开工前摸排处理信息使用")
    @SystemControllerLog(descrption = "修改开工前摸排处理信息", actionType = "3",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisits", method = RequestMethod.PUT)
    public String updateSiteVisits(@ApiParam(value = "修改人id", required = true) @RequestParam(value = "editer", required = true) String editer,
                                   @ApiParam(value = "处理时间", required = false) @RequestParam(value = "disposeTime", required = false) String disposeTime,
                                   @ApiParam(value = "现场处置措施及结果", required = false) @RequestParam(value = "appointedResults", required = false) String appointedResults,
                                   @ApiParam(value = "开工前摸排处理id", required = false) @RequestParam(value = "id", required = false) String id,
                                   @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token
    ) {
        logger.info("开工前摸排处理id：" + id);
        logger.info("处理时间" + disposeTime);
        logger.info("现场处置措施及结果" + appointedResults);
        logger.info("修改人id" + editer);
        try {
            //验证现场处置措施及结果
            validation.chkLength( appointedResults, 0, 1000, "现场处置措施及结果仅支持大小写字母、数字及汉字输入，长度不超过1000个字符");

            SiteVisitsModel info = new SiteVisitsModel();
            info.setDisposeTime(Config.DATE_FORMAT.parse(disposeTime));
            info.setAppointedResults(appointedResults);
            info.setEditer(Integer.parseInt(editer));
            info.setId(Integer.parseInt(id));
            info.setDisposeState("0");
            int rel = siteVisitsService.updateSiteVisits(editer, info);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "更新成功", rel ).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "更新失败", "").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisits..msg:修改开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisits..msg:修改开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SiteVisitsController. ");
            logger.error("function:updateSiteVisits..msg:修改开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (DataValidationException d){
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:修改开工前摸排处理信息出现异常. error:" + d.getMessage());
            return new JsonResponseData(false, d.getMessage(), StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (ParseException p) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:修改开工前摸排处理信息出现异常. error:" + p.getMessage());
            return new JsonResponseData(false, "时间格式异常：正确格式：yyyy-MM-dd HH:mm:ss", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisits..msg:修改开工前摸排处理信息出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 分页查询开工前摸排
     * HTTP 方式：
     * API 路径：
     * 方法名：
     * 方法返回类型：String
     */
    @RequiresPermissions("LBXDJBQK")
    @ApiOperation(value = "查询列表", httpMethod = "GET", response = String.class, notes = "分页查询-开工前摸排管理-列表查询接口使用")
    @SystemControllerLog(descrption = "分页查询摸排详情", actionType = "4",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisitsInfo/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public String getPageList(@ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex") String pageIndex,
                              @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum") String pageNum,
                              @ApiParam(value = "区县code", required = false) @RequestParam(value = "countyId", required = false) String countyCode,
                              @ApiParam(value = "乡镇code", required = false) @RequestParam(value = "villageId", required = false) String villageCode,
                              @ApiParam(value = "村庄code", required = false) @RequestParam(value = "hamletId", required = false) String hamletCode,
                              @ApiParam(value = "项目id", required = false) @RequestParam(value = "projectId", required = false) String projectId,
                              @ApiParam(value = "摸排查询开始时间", required = false) @RequestParam(value = "startTime", required = false) String startTime,
                              @ApiParam(value = "摸排查询结束时间", required = false) @RequestParam(value = "endTime", required = false) String endTime,
                              @ApiParam(value = "是否存在问题", required = false) @RequestParam(value = "isQuestion", required = false) String isQuestion,
                              @ApiParam(value = "处理状态", required = false) @RequestParam(value = "disposeState", required = false) String disposeState,
                              @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token

    ) {

        logger.info("页索引" + pageIndex);
        logger.info("页大小" + pageNum);
        logger.info("区县id" + countyCode);
        logger.info("乡镇id" + villageCode);
        logger.info("村庄id" + hamletCode);
        logger.info("项目id" + projectId);
        logger.info("摸排查询开始时间" + startTime);
        logger.info("摸排查询结束时间" + endTime);
        logger.info("是否存在问题" + isQuestion);
        logger.info("处理状态" + disposeState);
        try {
            Page page = new Page();
            if (!StringUtils.isBlank(pageIndex)) {
                page.setCurrentPage(Long.parseLong(pageIndex));
            }
            if (!StringUtils.isBlank(pageNum)) {
                page.setEveryPage(Long.parseLong(pageNum));
            }
            /**分页查询条件*/
            Map<String, Object> map = new HashMap<>(3);
            map.put("state", ConstDefine.STATE_ABLE);
            map.put("beginIndex", page.getBeginIndex());
            map.put("endIndex", page.getEndinIndex());
            map.put("countyCode", countyCode);
            map.put("villageCode", villageCode);
            map.put("hamletCode", hamletCode);
            map.put("projectId", projectId);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("isQuestion", isQuestion);
            map.put("disposeState", disposeState);
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
            List<SiteVisitsInfoModel> list = siteVisitsService.getAllByPage(map, page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();
        } catch (DataAccessException e) {
            logger.error("controller:SiteVisitsController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 删除开工前摸排详情
     * HTTP 方式：POST
     * API 路径：/api/siteVisits/deleteSiteVisitsInfo
     * 方法名：deleteSiteVisitsInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDSC")
    @ApiOperation(value = "删除开工前摸排详情", httpMethod = "DELETE", response = String.class,
            notes = "删除开工前摸排详情，供后台删除开工前摸排详情使用")
    @SystemControllerLog(descrption = "删除开工前摸排详情", actionType = "2",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisitsInfo", method = RequestMethod.DELETE)
    public String deleteSiteVisitsInfo(@ApiParam(value = "开工前摸排id", required = true) @RequestParam(value = "id", required = true) Integer[] id,
                                       @ApiParam(value = "修改人id", required = false) @RequestParam(value = "editer", required = false) Integer editer,
                                       @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token

    ) {
        logger.info("开工前摸排id", id);
        logger.info("修改人id" + editer);
        try {
            //删除开工前摸排详情信息
            int rel = siteVisitsService.delteSiteVisitsInfo(id, editer, ConstDefine.STATE_DISABLE);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "删除成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "删除失败", " ").toString();
            }
        } catch (ProjectException e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:删除开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:删除开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SiteVisitsController. ");
            logger.error("function:addSiteVisitsInfo..msg:删除开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:删除开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * @param
     * @Author:Yangzepeng
     * @Desicription:根据id查询摸排信息详情
     * @Date:2018/10/8 16:20
     * @Return:
     * @Modified By:
     */
    @RequiresPermissions("LBXDXQCK")
    @ApiOperation(value = "查询详情", httpMethod = "GET", response = String.class, notes = "详情查询-开工前摸排管理-详情展示接口使用")
    @SystemControllerLog(descrption = "查询摸排详情", actionType = "4",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisitsInfo/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getSiteVisitsInfo(@ApiParam(value = "id", required = true) @PathVariable(value = "id") Integer id,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token) {

        logger.info("开工前摸排id" + id);
        try {
            SiteVisitsInfoModel model = siteVisitsService.getSiteVisitsInfo(id);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (DataAccessException e) {
            logger.error("controller:InformationController. function:getSiteVisitsInfo...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:InformationController. function:getSiteVisitsInfo...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 修改开工前摸排详情
     * HTTP 方式：PUT
     * API 路径：/api/siteVisitsInfo
     * 方法名：updateSiteVisitsInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions("LBXDBJ")
    @ApiOperation(value = "修改开工前摸排详情", httpMethod = "PUT", response = String.class,
            notes = "修改开工前摸排详情，供后台修改开工前摸排详情使用")
    @SystemControllerLog(descrption = "修改开工前摸排详情", actionType = "3",modules = "开工前摸排模块")
    @RequestMapping(value = "/api/siteVisitsInfo", method = RequestMethod.PUT)
    public String updateSiteVisitsInfo(@ApiParam(value = "开工前摸排详情id", required = true) @RequestParam(value = "id", required = true) String id,
                                           @ApiParam(value = "处理状态1-进行中 2-已解决", required = false) @RequestParam(value = "disposeState", required = false) String disposeState,
                                           @ApiParam(value = "修改人id", required = true) @RequestParam(value = "editer", required = true) String editer,
                                           @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token,
                                                   HttpServletRequest request

    ){
        logger.info("开工前摸排详情隐患id",id);
        logger.info("修改人id" + editer);
        logger.info("是否满意:1-满意 2-不满意" + disposeState);
        try {
            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() +":" + request.getServerPort()+"/";
            //修改开工前摸排详情信息
            SiteVisitsInfoModel model = new SiteVisitsInfoModel();
            model.setId(Integer.parseInt(id));
            model.setDisposeState(disposeState);
            model.setEditer(Integer.parseInt(editer));
            int rel = siteVisitsService.updateSiteVisitsInfo(model, url);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "修改成功", rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "修改失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisitsInfo..msg:修改开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisitsInfo..msg:修改开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:SiteVisitsController. ");
            logger.error("function:updateSiteVisitsInfo..msg:修改开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        }catch (ServiceException e){
            logger.error("controller:SiteVisitsController.");
            logger.error("function:addSiteVisitsInfo..msg:修改开工前摸排详情的通知推送出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:SiteVisitsController.");
            logger.error("function:updateSiteVisitsInfo..msg:修改开工前摸排详情出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
