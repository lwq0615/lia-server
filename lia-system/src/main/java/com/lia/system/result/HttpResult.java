package com.lia.system.result;


/**
 * 定义请求的响应信息
 */
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
     * 响应报文
     */
    private Object data;


    private HttpResult(ResultCode resultCode, Object data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }


    /**
     * 成功响应
     */
    public static HttpResult success(Object data){
        return new HttpResult(ResultCode.SUCCESS, data);
    }

    public static HttpResult success(){
        return new HttpResult(ResultCode.SUCCESS, null);
    }

    /**
     * 失败响应
     */
    public static HttpResult error(ResultCode resultCode, Object data){
        return new HttpResult(resultCode, data);
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
}
