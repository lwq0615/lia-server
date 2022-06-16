package com.lia.system.exception;


import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

    private int status;
    private String msg;

    public HttpException(int status){
        this.status = status;
        this.msg = HttpStatus.valueOf(status).getReasonPhrase();
    }

    public HttpException(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }

    public int getStatus(){
        return this.status;
    }

}
