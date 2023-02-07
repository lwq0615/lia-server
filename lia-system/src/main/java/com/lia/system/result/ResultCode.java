package com.lia.system.result;


/**
 * 响应码
 */
public interface ResultCode {


    /**
     * 获取响应状态码
     */
    int getCode();


    /**
     * 获取响应信息
     */
    String getMessage();


    /**
     * 获取响应类型
     */
    default Class getResultClass(){
        return this.getClass();
    }

}
