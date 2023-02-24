package com.lia.system.result.exception;

import com.alibaba.fastjson2.JSON;
import com.lia.system.entity.SysAuth;
import com.lia.system.modules.auth.SysAuthService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.result.HttpResult;
import com.lia.system.result.SysResult;
import com.lia.system.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.lia.system.entity.SysAuth.AUTH_URL_NAME;


/**
 * 全局的异常处理处理类
 * 返回时的状态码固定为200，真实的状态码封装在HttpResult.code属性中
 */
@RestControllerAdvice
@Component
public class GlobalException {


    @Autowired
    private SysAuthService sysAuthService;


    /**
     * 异常响应客户端
     */
    public static void httpError(SysResult resultCode, HttpServletResponse response){
        try {
            response.setStatus(SysResult.SUCCESS.getCode());
            response.getWriter().write(JSON.toJSONString(HttpResult.error(resultCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 缺少请求参数时返回状态码400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpResult notParamError() {
        return this.httpError(new HttpException(SysResult.REQUEST_ERROR));
    }


    /**
     * 403权限不足
     * 虽然在security中配置了没有权限的自定义处理，但是因为在没有权限时security会抛出异常
     * 而被全局异常处理捕获导致无法正常处理，所以security没有权限的处理应该通过ExceptionHandler处理
     */
    @ExceptionHandler(AccessDeniedException.class)
    public HttpResult noAuth(){
        String uri = SpringUtils.getRequest().getRequestURI();
        String urlName = (String) Redis.getTemplate(RedisDb.SYSTEM).opsForValue().get(AUTH_URL_NAME + uri);
        if (urlName == null) {
            HashMap<String, String> urlNameMap = new HashMap();
            for (SysAuth sysAuth : sysAuthService.findSysAuth(null)) {
                if (sysAuth.getUrl().equals(uri)) {
                    urlName = sysAuth.getName();
                }
                urlNameMap.put(SysAuth.AUTH_URL_NAME+sysAuth.getUrl(), sysAuth.getName());
            }
            Redis.getTemplate().opsForValue().multiSet(urlNameMap);
        }
        return HttpResult.error(SysResult.NOT_AUTH, urlName + SysResult.NOT_AUTH.getMessage());
    }


    /**
     * 请求方法错误，返回405状态码
     * 405错误会导致程序直接抛出异常，所以不能在aop中处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult methodError(){
        return this.httpError(new HttpException(SysResult.REQUEST_METHOD_ERROR));
    }


    /**
     * 常见的HTTP错误处理
     */
    @ExceptionHandler(HttpException.class)
    public HttpResult httpError(HttpException e) {
        // 动态的修改http返回状态码
        return HttpResult.error(SysResult.valueOf(e.getStatus()), e.getMessage());
    }


    /**
     * 程序异常时记录异常日志
     */
    @ExceptionHandler(Exception.class)
    public HttpResult error(Exception e){
        e.printStackTrace();
        return HttpResult.error(SysResult.SERVER_ERROR);
    }

}
