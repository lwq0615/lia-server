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
    private String message;


    public HttpException(int code){
        this.status = ResultCode.valueOf(code).getCode();
        this.message = ResultCode.valueOf(code).getMessage();
    }

    public HttpException(ResultCode resultCode){
        this.status = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public HttpException(String msg){
        this.status = 400;
        this.message = msg;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

    public int getStatus(){
        return this.status;
    }

}
