package com.lia.system.result;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lia.system.utils.SpringUtils;
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


    private HttpResult(ResultCode resultCode, Object data, String message) {
        this.code = resultCode.getCode();
        if(message != null){
            this.message = message;
        }else{
            this.message = resultCode.getMessage();
        }
        this.data = data;
        HttpServletRequest request = SpringUtils.getRequest();
        if (request != null) {
            this.url = request.getRequestURL().toString();
            if (!StringUtils.isEmpty(request.getQueryString())) {
                this.url += "?" + request.getQueryString();
            }
        }
    }


    /**
     * 成功响应
     */
    public static HttpResult ok(Object data) {
        return new HttpResult(ResultCode.SUCCESS, data, null);
    }

    /**
     * 失败响应
     */
    public static HttpResult error(ResultCode resultCode) {
        return new HttpResult(resultCode, null, null);
    }

    public static HttpResult error(ResultCode resultCode, String message) {
        return new HttpResult(resultCode, null, message);
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

    public String getUrl() {
        return url;
    }


}
