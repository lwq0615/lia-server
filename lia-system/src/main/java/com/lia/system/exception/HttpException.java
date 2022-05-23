package com.lia.system.exception;


import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    private Integer status;
    private String msg;

    public HttpException(Integer status){
        this.status = status;
        this.msg = HttpStatus.valueOf(status).getReasonPhrase();
    }

    public HttpException(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }

    public Integer getStatus(){
        return this.status;
    }

}
