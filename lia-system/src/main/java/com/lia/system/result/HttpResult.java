package com.lia.system.result;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lia.system.utils.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义请求的响应信息
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HttpResult {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 响应报文
     */
    private Object data;


    private HttpResult(ResultCode resultCode, Object data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
        try{
            //从获取RequestAttributes中获取HttpServletRequest的信息
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            this.url = request.getRequestURL().toString();
            if(!StringUtils.isEmpty(request.getQueryString())){
                this.url += "?"+request.getQueryString();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    /**
     * 成功响应
     */
    public static HttpResult ok(Object data){
        return new HttpResult(ResultCode.SUCCESS, data);
    }


    public static HttpResult error(ResultCode resultCode){
        return new HttpResult(resultCode, null);
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String getUrl(){
        return url;
    }


}
