package com.lia.system.crud.exception;

public class NotFoundBaseMapperException extends RuntimeException {

    public NotFoundBaseMapperException(Class clazz){
        super("can't found a Mapper extends BaseMapper<"+clazz.getName()+">");
    }

}
