package com.aorise.controller.projectcominfo;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.config.Config;
import com.aorise.exceptions.DBErrorException;
import com.aorise.exceptions.ProjectException;
import com.aorise.model.projectcominfo.ProjectComInfoModel;
import com.aorise.service.projectcominfo.ProjectComInfoService;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @Description:项目点评接口
 * @Author:Huangdong
 * @Date:2018/9/25 10:12
 * @Version V1.0
 */
@RestController
@Api(description = "项目点评接口")
public class ProjectComInfoController {
    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectComInfoService projectComInfoService;

    /**
     * 新增项目点评
     * HTTP 方式：POST
     * API 路径：/api/projectcominfo/addProjectcomInfo
     * 方法名：addProjectcomInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions(value={"LBXDDP","XMXQDP"},logical= Logical.OR)
    @ApiOperation(value = "新增项目点评", httpMethod = "POST", response = String.class,
            notes = "新增项目点评，供后台新增项目点评接口使用")
    @SystemControllerLog(descrption = "新增项目点评", actionType = "1",modules = "项目点评模块")
    @RequestMapping(value = "/api/projectcominfo/addProjectcomInfo", method = RequestMethod.POST)
    public String addProjectcomInfo(@ApiParam(value = "项目id", required = false) @RequestParam(value = "projectid", required = false) String projectid,
                                    @ApiParam(value = "点评内容", required = false) @RequestParam(value = "describes", required = false) String describes,
                                    @ApiParam(value = "点评人", required = false) @RequestParam(value = "reuser", required = false) String reuser,
                                    @ApiParam(value = "联保行动关联id", required = false) @RequestParam(value = "relateId", required = false) String relateId,
                                    @ApiParam(value = "类别", required = false) @RequestParam(value = "type", required = false) String type,
                                    @ApiParam(value = "创建人id", required = false) @RequestParam(value = "userid", required = false) String userid,
                                    @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token, HttpServletRequest request) {

        logger.info("项目id：" + projectid);
        logger.info("点评内容：" + describes);
        logger.info("点评人：" + reuser);
        logger.info("类别：" + type);
        logger.info("联保行动关联id"+ relateId);

        try {

            //获取当前url
            String url = request.getScheme() + "//" + request.getServerName() +":" + request.getServerPort()+"/";
            ProjectComInfoModel pc = new ProjectComInfoModel();
            pc.setProjectid(Integer.parseInt(projectid));
            pc.setDescribes(describes);
            pc.setReuser(reuser);
            pc.setType(Integer.parseInt(type));
            pc.setState(ConstDefine.STATE_ABLE);
            pc.setCreateTime(Config.DATE_FORMAT.format(new Date()));
            pc.setRelateId(relateId);
            int rel = projectComInfoService.addProjectcomInfo(pc,url,userid);
            if (rel > 0) {
                return new JsonResponseData(true,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "插入成功", "id:" + rel).toString();
            } else {
                return new JsonResponseData(false,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "插入失败", "").toString();
            }
        }catch (ProjectException e){
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:addProjectcomInfo..msg:新增项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:addProjectcomInfo..msg:新增项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:addProjectcomInfo..msg:新增项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:addProjectcomInfo..msg:新增项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }

    /**
     * 查询项目点评
     * HTTP 方式：POST
     * API 路径：/api/projectcominfo/selectProjectcomInfo
     * 方法名：selectProjectcomInfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @RequiresPermissions(value={"XMXQDPCK","LBXDDPCK"},logical= Logical.OR)
    @ApiOperation(value = "查询项目点评", httpMethod = "GET", response = String.class,
            notes = "查询项目点评，供后台查询项目点评接口使用")
    @SystemControllerLog(descrption = "查询项目点评", actionType = "4",modules = "项目点评模块")
    @RequestMapping(value = "/api/projectcominfo/selectProjectcomInfo", method = RequestMethod.GET)
    public String selectProjectcomInfo(@ApiParam(value = "项目id", required = true) @RequestParam(value = "projectid", required = true) String projectid,
                                        @ApiParam(value = "类别 1-基本情况  2-开工摸排 3-群众上访 4-阻工 5-隐患", required = false) @RequestParam(value = "type", required = false) String type,
                                        @ApiParam(value = "联保行动关联id", required = false) @RequestParam(value = "relateId", required = false) String relateId,
                                        @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token) {

        logger.info("项目id：" + projectid);
        logger.info("联保行动关联id"+ relateId);
        logger.info("类别：" + type);
        try {

            List<ProjectComInfoModel> projectComInfoModels = projectComInfoService.selectProjectcomInfo(projectid,type,relateId);
            return new JsonResponseData(true,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "查询成功", projectComInfoModels).toString();
        }catch (ProjectException e){
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:selectProjectcomInfo..msg:查询项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:selectProjectcomInfo..msg:查询项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:selectProjectcomInfo..msg:查询项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            logger.error("controller:ProjectComInfoController.");
            logger.error("function:selectProjectcomInfo..msg:查询项目点评出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }

    }
}
