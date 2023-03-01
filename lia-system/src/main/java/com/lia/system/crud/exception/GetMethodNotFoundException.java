package com.lia.system.crud.exception;

import java.lang.reflect.Field;

public class GetMethodNotFoundException extends RuntimeException {

    public GetMethodNotFoundException(Field field){
        super("can not found get method of field '"+field.getName()+"' in class '"+field.getDeclaringClass().getName()+"'");
    }

}
