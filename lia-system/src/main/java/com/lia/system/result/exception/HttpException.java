package com.lia.system.result.exception;

import com.lia.system.result.ResultCode;


/**
 * 常见的http异常
 */
public class HttpException extends RuntimeException {


    /**
     * 异常状态码
     */
    private int status;

    /**
     * 异常信息
     */
    private String msg;


    public HttpException(int code){
        this.status = ResultCode.valueOf(code).getCode();
        this.msg = ResultCode.valueOf(code).getMessage();
    }

    public HttpException(ResultCode resultCode){
        this.status = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public HttpException(String msg){
        this.status = 400;
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }

    public int getStatus(){
        return this.status;
    }

}
