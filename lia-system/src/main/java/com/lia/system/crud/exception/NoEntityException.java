package com.lia.system.crud.exception;


/**
 * 当某个Service继承了BaseService却没有规定泛型参数时
 * 程序无法判断该Service所管理的实体关系表，则抛出异常
 */
public class NoEntityException extends RuntimeException {

    public NoEntityException(){
        super("BaseService needs a generic such as 'class UserService extends BaseService<User>'");
    }

}
