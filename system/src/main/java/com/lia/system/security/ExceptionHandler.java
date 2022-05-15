package com.lia.system.security;

import com.lia.system.exception.GlobalException;
import com.lia.system.exception.HttpException;
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
     * 认证失败处理
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        globalException.httpError(new HttpException(401, "认证失败请重新登陆"));
    }

    /**
     * 授权失败处理
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        globalException.httpError(new HttpException(403, "权限不足"));
    }
}
