package com.lia.system.result.exception;


import com.lia.system.result.ResultCode;

public class HttpException extends RuntimeException {

    private int status;
    private String msg;

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
