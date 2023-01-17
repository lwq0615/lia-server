package com.lia.system.result;


import com.lia.system.exception.NoSuchResultException;

/**
 * 响应码
 */
public enum ResultCode {

    SUCCESS(200, "成功"),
    REGISTER_USED(201, "注册码已被使用"),
    USERNAME_EXISTED(202, "用户名已存在"),
    REGISTER_NOT_EXIST(203, "注册码不存在"),
    USER_STATE_CHANGE(204, "账号状态发生改变"),
    USER_LOGIN_OTHER(205, "账号在其他设备登录"),
    PARAM_NOT_EXIST(206, "系统参数不存在"),
    ROLE_KEY_EXISTED(207, "角色标识符已存在");


    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;



    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    /**
     * 根据响应码获取枚举
     * @param code 响应码
     * @return 枚举对象
     */
    public static ResultCode valueOf(int code){
        for (ResultCode resultCode : values()) {
            if(resultCode.getCode() == code) {
                return resultCode;
            }
        }
        throw new NoSuchResultException(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }}
