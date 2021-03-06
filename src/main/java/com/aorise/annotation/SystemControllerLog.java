package com.aorise.annotation;

import java.lang.annotation.*;

/**
 * @Description:  定义注解，拦截controller
 * @Author: Huangdong
 * @CreateDate: 2018年8月28日 09:53:21
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)//元注解，定义注解被保留策略，一般有三种策略
                                    //1、RetentionPolicy.SOURCE 注解只保留在源文件中，在编译成class文件的时候被遗弃
                                    //2、RetentionPolicy.CLASS 注解被保留在class中，但是在jvm加载的时候北欧抛弃，这个是默认的声明周期
                                    //3、RetentionPolicy.RUNTIME 注解在jvm加载的时候仍被保留
@Target({ElementType.METHOD}) //定义了注解声明在哪些元素之前
@Documented
public @interface SystemControllerLog {
    //定义成员
    String descrption() default "" ;//描述
    String actionType() default "" ;//操作的类型，1、添加 2、删除 3、修改 4、查询
    String modules() default "";//操作模块
}
