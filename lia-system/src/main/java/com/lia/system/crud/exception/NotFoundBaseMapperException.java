package com.lia.system.crud.exception;


/**
 * 当某个Service继承了BaseService并规定了泛型参数
 * BaseService会在spring容器中寻找具有相同泛型的BaseMapper
 * 如果在spring容器中没有找到，则抛出异常
 */
public class NotFoundBaseMapperException extends RuntimeException {

    public NotFoundBaseMapperException(Class clazz){
        super("can't found a Mapper extends BaseMapper<"+clazz.getName()+">");
    }

}
