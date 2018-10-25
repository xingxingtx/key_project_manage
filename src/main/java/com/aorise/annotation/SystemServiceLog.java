package com.aorise.annotation;

import java.lang.annotation.*;

/**
 * @Description: 定义注解，拦截service
 * @Author: Huangdong
 * @CreateDate: 2018年8月28日 09:53:21
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SystemServiceLog {
    //定义成员
    String decription() default "" ;
}
