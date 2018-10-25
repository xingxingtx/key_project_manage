package com.aorise.controller.common;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.exceptions.DataValidationException;
import com.aorise.model.common.AreaInfoModel;
import com.aorise.service.common.AreaInfoService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/9/29 10:55
 * @Modified By:
 */
@Api(description ="地区相关")
@RestController
public class AreaInfoController {
    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AreaInfoService areaInfoService;

    @Autowired
    private DataValidation dataValidation;
    /**
     * 根据区域代码查询出区域信息
     * HTTP 方式：GET
     * API 路径：/api/AreaInfo/queryProvince
     * 方法名：queryAreaInfoByCode
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation( value = "根据区域代码查询出区域信息", httpMethod = "GET", response = String.class, notes = "根据父级Code查询子级数据" )
    @SystemControllerLog(descrption = "根据区域代码查询出区域信息", actionType = "4",modules = "地区区域模块")
    @RequestMapping( value = "/api/AreaInfo/areaList/code/{code}", method = RequestMethod.GET )
    public String queryAreaInfoByCode(@ApiParam(value = "区域代码" ,required = true) @PathVariable(value = "code",required = true)String code){
        logger.info("区域代码："+code);
        try {
            dataValidation.chekeNotempty("区域代码",code);
            /**根据区域代码查询*/
            List<AreaInfoModel> areaInfoList = areaInfoService.getListByParent(code);
            if(areaInfoList!=null) {
                return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "",
                        areaInfoList).toString();
            }else{
                return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.SUCCESS, "",
                        "").toString();
            }
        } catch (DataAccessException e){
            logger.error("controller:AreaInfoController. ");
            logger.error("function:queryAreaInfoByCode..msg:根据区域代码查询出区域信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (DataValidationException e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaInfoByCode..msg:根据区域代码查询出区域信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (Exception e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaInfoByCode..msg:根据区域代码查询出区域信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }

    /**
     * 根据区域代码查询出区域信息
     * HTTP 方式：GET
     * API 路径：/api/AreaInfo/queryProvince
     * 方法名：queryAreaInfoByCode
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation( value = "查询怀化市所有地区（区县）", httpMethod = "GET", response = String.class, notes = "查询怀化市所有地区（区县）" )
    @SystemControllerLog(descrption = "查询怀化市所有地区（区县）", actionType = "4",modules = "地区区域模块")
    @RequestMapping( value = "/api/AreaInfo/hhAll", method = RequestMethod.GET )
    public String queryAreaInfoByAll(){
        try {
            /**根据区域代码查询*/
            List<AreaInfoModel> areaInfoList = areaInfoService.getListByParent("431200000000");
            if(areaInfoList!=null) {
                return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "",
                        areaInfoList).toString();
            }else{
                return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.SUCCESS, "",
                        "").toString();
            }
        }  catch (DataAccessException e){
            logger.error("controller:AreaInfoController. ");
            logger.error("function:queryAreaInfoByCode..msg:查询所有地区. error:"+e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaInfoByCode..msg:查询所有地区. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }

    /**
     * 根据区域代码查询出区域信息
     * HTTP 方式：GET
     * API 路径：/api/AreaInfo/queryProvince
     * 方法名：queryAreaInfoByCode
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation( value = "查询怀化市所有地区（包括怀化市）", httpMethod = "GET", response = String.class, notes = "查询怀化市所有地区（包括怀化市）" )
    @SystemControllerLog(descrption = "查询怀化市所有地区（包括怀化市）", actionType = "4",modules = "地区区域模块")
    @RequestMapping( value = "/api/AreaInfo/hhAllIn", method = RequestMethod.GET )
    public String queryAreaInfoByAllIn(){
        try {
            /**根据区域代码查询*/
            List<AreaInfoModel>  areaInfoList = new ArrayList<AreaInfoModel>();
            areaInfoList.add(areaInfoService.getById("431200000000"));
            areaInfoList.addAll(areaInfoService.getListByParent("431200000000"));
            if(areaInfoList!=null) {
                return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "",
                        areaInfoList).toString();
            }else{
                return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.SUCCESS, "",
                        "").toString();
            }
        }  catch (DataAccessException e){
            logger.error("controller:AreaInfoController. ");
            logger.error("function:queryAreaInfoByCode..msg:查询所有地区. error:"+e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaInfoByCode..msg:查询所有地区. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR),
                    null).toString();
        }
    }

    /**
     * 根据区域代码查询出区域名称 省份的就传0
     * HTTP 方式：GET
     * API 路径：/api/AreaInfo/queryProvince
     * 方法名：queryAreaInfoByCode
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation( value = "根据区域代码查询区域信息", httpMethod = "GET", response = String.class, notes = "根据区域代码查询区域信息" )
    @SystemControllerLog(descrption = "根据区域代码查询区域信息", actionType = "4",modules = "地区区域模块")
    @RequestMapping( value = "/api/AreaInfo/AreaInfo/code/{code}", method = RequestMethod.GET )
    public String queryAreaNameByAreaCode(@ApiParam(value = "区域代码" ,required = true) @PathVariable(value = "code",required = true)String code){
        logger.info("区域代码："+code);
        try {

            dataValidation.chekeNotempty("区域代码", code);
            AreaInfoModel model = areaInfoService.getById(code);
            if(model==null){
                return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.FAILURE), StatusDefine.SUCCESS, "区域信息不存在", "").toString();
            }else{
                return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", model).toString();
            }
        }catch (DataAccessException e){
            logger.error("controller:AreaInfoController. ");
            logger.error("function:queryAreaNameByAreaCode..msg:根据区域代码查询信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        }catch (DataValidationException e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaInfoByCode..msg:根据区域代码查询信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DATA_FORMAT_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DATA_FORMAT_ERROR), null).toString();
        }catch (Exception e) {
            logger.error("controller:AreaInfoController.");
            logger.error("function:queryAreaNameByAreaCode..msg:根据区域代码查询信息出现异常. error:"+e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR, StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }
}
