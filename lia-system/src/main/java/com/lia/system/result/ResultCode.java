package com.lia.system.result;


import com.lia.system.result.exception.NoSuchResultException;

/**
 * 响应码
 */
public enum ResultCode {

    /**
     * 响应成功
     */
    SUCCESS(200, "成功"),

    /**
     * 系统级错误
     */
    REQUEST_ERROR(400, "请求有误"),
    NOT_LOGIN(401, "未登录，请先登录"),
    LOGIN_OUT_TIME(402, "登录过期，请重新登陆"),
    NOT_AUTH(403, "没有权限"),
    RECOURSE_NOT_FOUNT(404, "目标资源不存在"),
    REQUEST_METHOD_ERROR(405, "请求类型有误"),
    SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 用户
     */
    REGISTER_USED(201, "注册码已被使用"),
    USERNAME_EXISTED(202, "用户名已存在"),
    REGISTER_NOT_EXIST(203, "注册码不存在"),
    USER_STATE_CHANGE(204, "账号状态发生改变"),
    USER_LOGIN_OTHER(205, "账号在其他设备登录"),
    PHONE_ERROR(206, "手机号格式错误"),
    USER_DEACTIVATE(207, "账号已停用"),

    /**
     * 系统参数
     */
    PARAM_NOT_EXIST(208, "系统参数不存在"),
    PARAM_NAME_EXISTED(209, "系统参数名重复"),

    /**
     * 角色
     */
    ROLE_KEY_EXISTED(210, "角色标识符已存在"),

    /**
     * 权限
     */
    AUTH_KEY_EXISTED(211, "权限标识符重复"),
    AUTH_URL_EXISTED(212, "权限接口路径重复"),

    /**
     * 企业
     */
    COMPANY_NAME_EXISTED(213, "企业名称重复"),

    /**
     * 数据字典
     */
    DICTTYPE_KEY_EXISTED(214, "字典标识符重复"),
    DICTDATA_VALUE_TYPE_EXISTED(215, "同类型字典数据值重复"),

    /**
     * 路由
     */
    ROUTER_ELEMENT_EXISTED(216, "路由组件地址重复"),
    ROUTER_PATH_EXISTED(217, "同目录下路由地址重复"),
    ROUTER_PARENT_OWN(218, "路由父路由不能是自己");


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
