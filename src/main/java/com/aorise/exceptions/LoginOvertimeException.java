package com.aorise.exceptions;

/**
 * @Author:ZGP
 * @Desicription:
 * @Date:Created in 2018/10/16 11:26
 * @Modified By:
 */
public class LoginOvertimeException extends RuntimeException {

    public LoginOvertimeException(String  message){
        super(message);
    }
}
