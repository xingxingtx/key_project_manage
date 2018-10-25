package com.aorise.controller.projectbranchinfo;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DBErrorException;
import com.aorise.exceptions.ProjectException;
import com.aorise.model.projectbranchinfo.ProjectBranchInfoModel;
import com.aorise.service.projectbranchinfo.ProjectBranchInfoService;
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

/**
 * @Description:联保项目分布统计接口
 * @Author:Huangdong
 * @Date:2018/9/26 18:26
 * @Version V1.0
 */
@RestController
@Api(description = "项目分布统计接口")
public class ProjectbranchinfoController {

    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectBranchInfoService projectBranchInfoService;

    /**
     * 联保项目分布统计
     * HTTP 方式：GET
     * API 路径：/api/projectbranchinfo
     * 方法名：getprojectbranchinfo
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation(value = "联保项目分布统计", httpMethod = "GET", response = String.class,
            notes = "联保项目分布统计，供后台联保项目统计分析信息使用")
    @SystemControllerLog(descrption = "联保项目分布统计", actionType = "4",modules = "分布统计模块")
    @RequiresPermissions("LBXMFBTJ")
    @RequestMapping(value = "/api/projectbranchinfo", method = RequestMethod.GET)
    public String getprojectbranchinfo(@ApiParam(value = "区县code", required = false) @RequestParam(value = "county", required = false) String county) {
        logger.info("区县code：" + county);
        try {
            ProjectBranchInfoModel model = projectBranchInfoService.getprojectbranchinfo(county);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        } catch (ProjectException e) {
            logger.error("controller:ProjectbranchinfoController.");
            logger.error("function:getProject..msg:联保项目分部统计出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    StatusDefine.SYS_ERROR, e.getMessage(), null).toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("controller:ProjectbranchinfoController.");
            logger.error("function:getProject..msg:联保项目分部统计出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "数据类型错误", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        } catch (DBErrorException e) {
            logger.error("controller:ProjectbranchinfoController.");
            logger.error("function:getProject..msg:联保项目分部统计出现异常. error:" + e.getMessage());
            return new JsonResponseData(false,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), StatusDefine.DB_ERROR, "",
                    null).toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("controller:ProjectbranchinfoController.");
            logger.error("function:getProject..msg:联保项目分部统计出现异常. error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

}
