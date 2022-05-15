package com.lia.system.annotation;


import java.lang.annotation.*;


/**
 * Controller类或方法添加该注解后满足一定条件时将不再对该方法进行权限校验
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

    Status value() default Status.all;


    enum Status{
        // 只需要登录
        login,
        // 不需要任何权限
        all
    }
}
