package com.lia.system.crud.exception;

public class UniqueException extends RuntimeException {

    private String name;

    public UniqueException(String name, String msg){
        super(msg);
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
