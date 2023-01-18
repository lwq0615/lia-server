package com.lia.system.result.exception;

import com.alibaba.fastjson2.JSON;
import com.lia.system.result.HttpResult;
import com.lia.system.result.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 全局的异常处理处理类
 * 返回时的状态码固定为200，真实的状态码封装在HttpResult.code属性中
 */
@RestControllerAdvice
@Component
public class GlobalException {



    /**
     * 异常响应客户端
     */
    public static void httpError(ResultCode resultCode, HttpServletResponse response){
        try {
            response.setStatus(ResultCode.SUCCESS.getCode());
            response.getWriter().write(JSON.toJSONString(HttpResult.error(resultCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 缺少请求参数时返回状态码400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void notParamError() {
        this.httpError(new HttpException(ResultCode.REQUEST_ERROR));
    }


    /**
     * 403权限不足
     * 虽然在security中配置了没有权限的自定义处理，但是因为在没有权限时security会抛出异常
     * 而被全局异常处理捕获导致无法正常处理，所以security没有权限的处理应该通过ExceptionHandler处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public HttpResult noAuth(){
        return this.httpError(new HttpException(ResultCode.NOT_AUTH));
    }


    /**
     * 请求方法错误，返回405状态码
     * 405错误会导致程序直接抛出异常，所以不能在aop中处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult methodError(){
        return this.httpError(new HttpException(ResultCode.REQUEST_METHOD_ERROR));
    }


    /**
     * 常见的HTTP错误处理
     */
    @ExceptionHandler(HttpException.class)
    public HttpResult httpError(HttpException e) {
        // 动态的修改http返回状态码
        return HttpResult.error(ResultCode.valueOf(e.getStatus()));
    }


    /**
     * 程序异常时记录异常日志
     */
    @ExceptionHandler(Exception.class)
    public HttpResult error(Exception e){
        e.printStackTrace();
        return HttpResult.error(ResultCode.SERVER_ERROR);
    }

}
