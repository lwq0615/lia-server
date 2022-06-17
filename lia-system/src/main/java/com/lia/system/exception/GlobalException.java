package com.lia.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 全局的异常处理处理类
 */
@ControllerAdvice
@Component
public class GlobalException {


    /**
     * 缺少请求参数时返回状态码400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void notParamError() {
        this.httpError(new HttpException(400));
    }


    /**
     * 403权限不足
     * 虽然在security中配置了没有权限的自定义处理，但是因为在没有权限时security会抛出异常
     * 而被全局异常处理捕获导致无法正常处理，所以security没有权限的处理应该通过ExceptionHandler处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void noPower(){
        this.httpError(new HttpException(403, "没有权限"));
    }


    /**
     * 请求方法错误，返回405状态码
     * 405错误会导致程序直接抛出异常，所以不能在aop中处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void methodError(){
        this.httpError(new HttpException(405));
    }


    /**
     * 常见的HTTP错误处理
     */
    @ExceptionHandler(HttpException.class)
    public void httpError(HttpException e) {
        // 动态的修改http返回状态码
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setStatus(e.getStatus());
        try {
            response.getWriter().write(e.getMsg());
            response.getWriter().close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 程序异常时记录异常日志
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void error(Exception e){
        //从获取RequestAttributes中获取HttpServletRequest的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        e.printStackTrace();
    }

}
