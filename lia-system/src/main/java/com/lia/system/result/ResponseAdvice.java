package com.lia.system.result;

import com.alibaba.fastjson2.JSON;
import com.lia.system.entity.SysFile;
import com.lia.system.utils.SpringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;


/**
 * 对响应信息统一处理，包装为HttpResult类
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
        response.setStatus(SysResult.SUCCESS.getCode());
        /**
         * 响应不是json不做处理
         */
        if(!response.getContentType().split(";")[0].equals("application/json")){
            return null;
        }
        if (o instanceof String){
            /**
             * 当返回类型是String时，消息转换器则是：StringHttpMessageConverter。
             * 但通过ResponseAdvice返回的是一个Perf2WebRPCResult对象，并不是String类型，
             * 通过异常堆栈可知，当在执行 getContentLength 时出错，即对该对象强转String发生异常。
             * 所以将返回的String转为对象之后再转为JSON字符串
             */
            return JSON.toJSONString(HttpResult.ok(o));
        }
        if(o instanceof HttpResult){
            return o;
        }
        return HttpResult.ok(o);
    }
}
