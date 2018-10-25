package com.aorise.controller.jointguarantee;

import com.aorise.model.jointguarantee.JointGuaranteeModel;
import com.aorise.model.stopwork.StopWorkInfoModel;
import com.aorise.service.jointguarantee.JointGuaranteeService;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.page.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:联保行动
 * @Author:wei.peng
 * @Date:2018/10/12 11:25
 * @Version V1.0
 */
@RestController
@Api(description = "联保行动，提供给app调用的接口")
public class JointGuaranteeController {

    /**
     * 注入打印日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JointGuaranteeService jointGuaranteeService;
    /**
     * 分页查询我的联保代办事项
     * HTTP 方式：
     * API 路径：
     * 方法名：
     * 方法返回类型：String
     */
    @ApiOperation( value = "查询列表", httpMethod = "GET", response = String.class, notes = "分页查询-我的联保代办事项-列表查询接口使用" )
    @RequestMapping( value = "/api/jointGuarantee/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json" )
    public String getPageList(@ApiParam( value = "页索引", required = true ) @PathVariable( value = "pageIndex" ) String pageIndex,
                              @ApiParam ( value = "处理状态0-进行中 1-已解决", required = false ) @RequestParam ( value = "disposeState" ,required = false) String disposeState,
                              @ApiParam ( value = "页大小", required = true ) @PathVariable ( value = "pageNum" ) String pageNum,
                              @ApiParam ( value = "用户id", required = false ) @RequestParam ( value = "userId",required = false) String userId,
                              @ApiParam(value = "登录token", required = false) @RequestParam(value = "token", required = false) String token
    ){
        logger.info("页索引"+pageIndex);
        logger.info("页大小"+pageNum);
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
            map.put("disposeState",disposeState);
            map.put("userId",userId);
            List<JointGuaranteeModel> list = jointGuaranteeService.getAllByPage(map,page);
            page.setList(list);
            return new JsonResponseData(true, "", StatusDefine.SUCCESS,
                    StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), page).toString();
        }catch (DataAccessException e) {
            logger.error("controller:JointGuaranteeController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.DB_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR), null).toString();
        } catch (Exception e) {
            logger.error("controller:JointGuaranteeController. function:getPageList...msg:An exception occurred for paging query information.");
            logger.error("error:" + e.getMessage());
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
    }


}
