package com.aorise.controller.information;


import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DataValidationException;
import com.aorise.exceptions.ServiceException;
import com.aorise.model.information.InformationModel;
import com.aorise.service.information.InformationService;
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
 * @Desicription:资讯管理api接口类
 * @Date:Created in 2018-09-05 16:54
 * @Modified By:
 */
@RestController
@Api ( description = "资讯管理接口" )
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private DataValidation dataValidation;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 添加资讯
     * HTTP 方式：POST
     * API 路径：/api/information
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @RequiresPermissions ("ZXXZ")
    @ApiOperation ( value = "添加资讯", httpMethod = "POST", response = String.class, notes = "添加资讯-资讯管理-添加功能使用" )
    @SystemControllerLog(descrption = "添加资讯", actionType = "1",modules = "资讯管理模块")
    @RequestMapping ( value = "/api/information", method = RequestMethod.POST, produces = "application/json" )
    public String insertObject(@ApiParam ( value = "资讯名称", required = true ) @RequestParam ( value = "name" ) String name,
                               @ApiParam ( value = "类型", required = true ) @RequestParam ( value = "type" ) int type,
                               @ApiParam ( value = "内容", required = true ) @RequestParam ( value = "content" ) String content,
                               @ApiParam ( value = "创建人id", required = true ) @RequestParam ( value = "userId" ) int userId) {
        try {
            //参数验证
            dataValidation.chkLength(name, 1, 100, "资讯名称长度不符合规则");
            dataValidation.chkLength(name, 1, 5000, "内容长度不符合规则");
            //封装实体
            InformationModel informationModel = new InformationModel();
            informationModel.setContent(content);
            informationModel.setState(ConstDefine.STATE_ABLE);
            informationModel.setType(type);
            informationModel.setName(name);
            informationModel.setCreateTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            informationModel.setCreater(userId);
            //调用接口
            int ret = informationService.insertObject(informationModel);
            //判断返回值
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    e.getMessage(), null).toString();
        }  catch (Exception e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 修改资讯
     * HTTP 方式：POST
     * API 路径：/api/information
     * 方法名：updateObject
     * 方法返回类型：String
     */
    @ApiOperation ( value = "修改资讯", httpMethod = "PUT", response = String.class, notes = "修改资讯-资讯管理-修改功能使用" )
    @SystemControllerLog(descrption = "修改资讯", actionType = "3",modules = "资讯管理模块")
    @RequiresPermissions ("ZXXZ")
    @RequestMapping ( value = "/api/information", method = RequestMethod.PUT, produces = "application/json" )
    public String updateObject(@ApiParam ( value = "资讯id", required = true ) @RequestParam ( value = "id" ) int id,
                               @ApiParam ( value = "资讯名称", required = true ) @RequestParam ( value = "name" ) String name,
                               @ApiParam ( value = "类型", required = true ) @RequestParam ( value = "type" ) int type,
                               @ApiParam ( value = "内容", required = true ) @RequestParam ( value = "content" ) String content,
                               @ApiParam ( value = "修改人id", required = true ) @RequestParam ( value = "userId" ) int userId) {
        try {
            //参数验证
            dataValidation.chkLength(name, 1, 100, "资讯名称长度不符合规则");
            dataValidation.chkLength(name, 1, 5000, "内容长度不符合规则");
            //封装实体
            InformationModel informationModel = new InformationModel();
            informationModel.setId(id);
            informationModel.setContent(content);
            informationModel.setState(ConstDefine.STATE_ABLE);
            informationModel.setType(type);
            informationModel.setName(name);
            informationModel.setEditTime(Utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
            informationModel.setEditer(userId);
            //调用接口
            int ret = informationService.updateObject(informationModel);
            //判断返回值
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), ret).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        } catch (DataAccessException e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    e.getMessage(), null).toString();
        } catch (Exception e) {
            logger.error("controller:InformationController. function:insertObject...msg:An exception occurred when adding a information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
    /**
     * 删除资讯
     * HTTP 方式：DELETE
     * API 路径：
     * 方法名：deleteObject
     * 方法返回类型：String
     */
    @RequiresPermissions ("ZXSC")
    @ApiOperation ( value = "删除资讯", httpMethod = "DELETE", response = String.class, notes = "删除资讯-资讯管理-删除功能使用" )
    @SystemControllerLog(descrption = "删除资讯", actionType = "2",modules = "资讯管理模块")
    @RequestMapping ( value = "/api/information", method = RequestMethod.DELETE, produces = "application/json" )
    public String deleteObject(@ApiParam ( value = "id字符串使用“,”隔开", required = true ) @RequestParam ( value = "ids" ) String ids) {
        try {
            int ret =informationService.deleteObject(ids);
            //判断返回值
            if (ret > 0) {
                return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                        StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), null).toString();
            } else {
                return new JsonResponseData(false, "", StatusDefine.FAILURE,
                        StatusDefineMessage.GetMessage(StatusDefine.FAILURE), null).toString();
            }
        }catch (ServiceException e) {
            logger.error("controller:InformationController. function:deleteObject...msg:An exception occurred when deleting information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SERVICE_ERROR,
                    e.getMessage(), null).toString();
        }  catch (DataAccessException e) {
            logger.error("controller:InformationController. function:deleteObject...msg:An exception occurred when deleting information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:InformationController. function:deleteObject...msg:An exception occurred when deleting information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }

    /**
     * 分页查询
     * HTTP 方式：
     * API 路径：
     * 方法名：
     * 方法返回类型：String
     */
    @RequiresPermissions ("ZXGL")
    @ApiOperation ( value = "分页查询", httpMethod = "GET", response = String.class, notes = "分页查询-资讯管理-列表查询接口使用" )
    @SystemControllerLog(descrption = "分页查询资讯信息", actionType = "4",modules = "资讯管理模块")
    @RequestMapping ( value = "/api/information/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json" )
    public String getPageList(@ApiParam ( value = "页索引", required = true ) @PathVariable ( value = "pageIndex" ) String pageIndex,
                              @ApiParam ( value = "页大小", required = true ) @PathVariable ( value = "pageNum" ) String pageNum){
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
            List<InformationModel> list=informationService.getAllByPage(map,page);
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
     * 查询详情
     * HTTP 方式：GET
     * API 路径：/api/information/{id}
     * 方法名：getObject
     * 方法返回类型：String
     */
    @ApiOperation ( value = "查询详情", httpMethod = "GET", response = String.class, notes = "查询详情-资讯管理-查询资讯详情使用" )
    @SystemControllerLog(descrption = "查询资讯信息详情", actionType = "4",modules = "资讯管理模块")
    @RequestMapping ( value = "/api/information/{id}", method = RequestMethod.GET, produces = "application/json" )
    public String getObject(@ApiParam ( value = "主键id", required = true ) @PathVariable ( value = "id" ) int id){
        try {
          InformationModel model=  informationService.getDetail(id);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), model).toString();
        }catch (DataAccessException e) {
            logger.error("controller:InformationController. function:getObject...msg:Abnormal enquiries for information details.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:InformationController. function:getObject...msg:Abnormal enquiries for information details.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
