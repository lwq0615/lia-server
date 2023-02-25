package com.lia.system.crud.exception;

public class NullValueOfIdException extends RuntimeException{
    public NullValueOfIdException(){
        super("@TableId field can't be null where execute update");
    }
}
