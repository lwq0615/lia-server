package com.lia.system.exception;

public class NoSuchResultException extends RuntimeException {

    public NoSuchResultException(int code){
        super("状态码'"+code+"'不存在于枚举类ResultCode中");
    }

}
