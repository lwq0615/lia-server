package com.lia.system.utils;


/**
 * 定义请求的响应信息
 */
public class HttpResult {

    private static final int SUCCESS_CODE = 200;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应报文
     */
    private Object data;


    public HttpResult(int code, String msg, Object data){
        this.code = code;
        this.message = msg;
        this.data = data;
    }


    /**
     * 成功响应
     */
    public static HttpResult success(Object data){
        return new HttpResult(SUCCESS_CODE, "success", data);
    }

    /**
     * 失败响应
     */
    public static HttpResult error(int code, String msg, Object data){
        return new HttpResult(code, msg, data);
    }

    public static HttpResult error(int code, String msg){
        return new HttpResult(code, msg, null);
    }

    public static HttpResult error(int code){
        return new HttpResult(code, null, null);
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
}
