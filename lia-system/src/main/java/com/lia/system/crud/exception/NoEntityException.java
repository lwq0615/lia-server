package com.lia.system.crud.exception;

public class NoEntityException extends RuntimeException {

    public NoEntityException(){
        super("BaseService needs a generic such as 'class UserService extends BaseService<User>'");
    }

}
