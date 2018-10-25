package com.aorise.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @Author:Huangdong
 * @Date:2018/8/29 14:18
 * @Version V1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface userlog {
    //定义成员
    String descrption() default "" ;//描述
}
