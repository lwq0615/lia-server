package com.lia.system.security;

import com.lia.system.result.SysResult;
import com.lia.system.result.exception.GlobalException;
import com.lia.system.result.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    private GlobalException globalException;


    /**
     * 认证失败时在Filter还未进入Servlet，无法被全局异常处理器捕获
     * 需要自定义认证失败时的响应报文
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if(response.getStatus() == SysResult.LOGIN_OUT_TIME.getCode()){
            GlobalException.httpError(SysResult.LOGIN_OUT_TIME, response);
        }else{
            GlobalException.httpError(SysResult.NOT_LOGIN, response);
        }
    }


    /**
     * 权限不足处理
     * 该方法会抛出异常，导致被全局异常处理捕获，故此处代码不生效
     * 异常处理逻辑在全局异常处理类中定义
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        globalException.httpError(new HttpException(SysResult.NOT_AUTH));
    }
}
