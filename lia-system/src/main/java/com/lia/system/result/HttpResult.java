package com.lia.system.result;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lia.system.utils.SpringUtils;
import com.lia.system.utils.StrUtils;
import lombok.ToString;
import org.springframework.beans.factory.support.ScopeNotActiveException;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义请求的响应信息
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@ToString
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
     * 响应类型
     */
    private String type;

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
        this.type = resultCode.getResultClass().getName();
        if(message != null){
            this.message = message;
        }else{
            this.message = resultCode.getMessage();
        }
        this.data = data;
        try{
            HttpServletRequest request = SpringUtils.getBean(RequestTemp.class).getReq();
            if (request != null) {
                this.url = request.getRequestURL().toString();
                if (!StrUtils.isEmpty(request.getQueryString())) {
                    this.url += "?" + request.getQueryString();
                }
            }
        }catch (ScopeNotActiveException e){}
    }


    /**
     * 成功响应
     */
    public static HttpResult ok(Object data) {
        return new HttpResult(SysResult.SUCCESS, data, null);
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

    public String getType() {
        return type;
    }


}
