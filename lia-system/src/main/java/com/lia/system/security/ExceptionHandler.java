package com.lia.system.security;

import com.lia.system.exception.GlobalException;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    private GlobalException globalException;


    /**
     * 认证失败处理
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if(response.getStatus() == 402){
            globalException.httpError(new HttpException(402, "登录过期"));
        }else{
            globalException.httpError(new HttpException(401, "认证失败请先登录"));
        }
    }


    /**
     * 权限不足处理
     * 该方法会抛出异常，导致被全局异常处理捕获，故此处代码不生效
     * 异常处理逻辑在全局异常处理类中定义
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        globalException.httpError(new HttpException(403, "没有权限"));
    }
}
