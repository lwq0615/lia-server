package com.lia.system.crud.exception;

public class IdNullValueException extends RuntimeException{
    public IdNullValueException(){
        super("@TableId field can't be null where execute update");
    }
}
