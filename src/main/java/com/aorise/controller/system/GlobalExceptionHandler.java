package com.aorise.controller.system;
import com.aorise.exceptions.LoginOvertimeException;
import com.aorise.utils.define.StatusDefine;
import com.aorise.utils.define.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ZGP
 * @Desicription: 全局异常处理
 * @Date:Created in 2018/8/10 15:44
 * @Modified By:
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value=AuthorizationException.class)
    public String allAuthorizationExceptionHandler(HttpServletRequest request,
                                      Exception exception) throws Exception
    {
        return new JsonResponseData(false, "", StatusDefine.PERMISSIONDENIED,
                StatusDefineMessage.GetMessage(StatusDefine.PERMISSIONDENIED), null).toString();
    }


    @ExceptionHandler(value=LoginOvertimeException.class)
    public String LoginOvertimeExceptionHandler(HttpServletRequest request,
                                                   Exception exception) throws Exception
    {
        return new JsonResponseData(false, "", StatusDefine.U_LOGIN_OUTTIME,
                StatusDefineMessage.GetMessage(StatusDefine.U_LOGIN_OUTTIME), null).toString();
    }
}
