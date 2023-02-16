package com.lia.system.utils;


import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringUtils {


    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext ac){
        applicationContext = ac;
    }


    /**
     * 获取当前spring容器
     */
    public static ApplicationContext getContext(){
        return applicationContext;
    }

    /**
     * 获取容器中的bean
     */
    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取当前request对象
     */
    public static HttpServletRequest getRequest(){
        //从获取RequestAttributes中获取HttpServletRequest的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取当前request对象
     */
    public static HttpServletResponse getResponse(){
        //从获取RequestAttributes中获取HttpServletRequest的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getResponse();
    }

}
