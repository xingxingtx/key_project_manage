package com.aorise.controller.system;

import com.aorise.annotation.SystemControllerLog;
import com.aorise.model.system.SysUserModel;
import com.aorise.utils.Utils;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description ="登录登出相关")
@RestController
public class LoginController {

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = String.class, notes = "用户登录")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json")
    public String login(@ApiParam(value = "用户名", required = true) @RequestParam(value = "username", required = true) String username
            , @ApiParam(value = "密码", required = true) @RequestParam(value = "password", required = true) String password) {

        Subject currentUser = SecurityUtils.getSubject();//获取当前sbuject
        UsernamePasswordToken token = new UsernamePasswordToken(username, Utils.getMd5DigestAsHex(password));
        token.setRememberMe(true);
        try {
//          执行登陆
            currentUser.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(1800000);//设置超时时间 单位ms
        } catch (IncorrectCredentialsException ae) {//密码错误
            return new JsonResponseData(false, "", StatusDefine.U_PWD_FAILED,
                    StatusDefineMessage.GetMessage(StatusDefine.U_PWD_FAILED), null).toString();
        } catch (UnknownAccountException e) {//用户不存在
            return new JsonResponseData(false, "", StatusDefine.U_INEXISTENCE,
                    StatusDefineMessage.GetMessage(StatusDefine.U_INEXISTENCE), null).toString();
        } catch(DisabledAccountException e){//用户冻结
            return new JsonResponseData(false, e.getMessage(), StatusDefine.U_UNACTIVE,
                    StatusDefineMessage.GetMessage(StatusDefine.U_UNACTIVE), null).toString();
        }
        catch (DataAccessException e) {
            return new JsonResponseData(false, StatusDefineMessage.GetMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {
            return new JsonResponseData(false, "", StatusDefine.SYS_ERROR,
                    StatusDefineMessage.GetMessage(StatusDefine.SYS_ERROR), null).toString();
        }
        SysUserModel user = (SysUserModel) currentUser.getPrincipal();
        user.setToken((String) currentUser.getSession().getId());
        return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", user).toString();
    }

    @RequestMapping(value = "/api/403", method = RequestMethod.GET)
    public String unauthorizedRole() {
        return new JsonResponseData(false, "", StatusDefine.PERMISSIONDENIED,
                StatusDefineMessage.GetMessage(StatusDefine.PERMISSIONDENIED), null).toString();
    }

    @RequestMapping(value = "/api/unauth", method = RequestMethod.GET)
    public String unauth() {
        return new JsonResponseData(false, "", StatusDefine.U_LOGIN_OUTTIME,
                StatusDefineMessage.GetMessage(StatusDefine.U_LOGIN_OUTTIME), null).toString();
    }

    @ApiOperation(value = "用户登出", httpMethod = "GET", response = String.class, notes = "用户登出")
    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public String logOut(){
        Subject currentUser=SecurityUtils.getSubject();
        if(currentUser!=null){
            currentUser.logout();
        }
        return new JsonResponseData(true, StatusDefineMessage.GetMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "",null).toString();
    }

}
