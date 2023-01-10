package com.lia.system.entity;


import com.lia.system.exception.HttpException;
import com.lia.system.utils.ArrayUtils;
import com.lia.system.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.regex.Pattern;


/**
 * 系统用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 所属企业
     */
    private Integer companyId;

    /**
     * 角色ID（外键）
     */
    private Integer roleId;

    /**
     * 性别（0男，1女，2其他）
     */
    private Character sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像（外键）
     */
    private Long headImg;

    /**
     * 帐号状态（0：正常，1：停用）
     */
    private Character status;

    /**
     * 删除标志（0：存在，1：已删除）
     */
    private Character delFlag;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 性别set方法
     */
    public void setSex(Character sex) {
        if(!ArrayUtils.asList('0', '1', '2').contains(sex)){
            sex = '0';
        }
        this.sex = sex;
    }


    /**
     * 帐号状态set方法
     */
    public void setStatus(Character status) {
        if(!ArrayUtils.asList('0', '1').contains(status)){
            status = '0';
        }
        this.status = status;
    }

    /**
     * 账号是否删除set方法
     */
    public void setDelFlag(Character delFlag) {
        if(!ArrayUtils.asList('0', '1').contains(status)){
            status = '0';
        }
        this.delFlag = delFlag;
    }


    /**
     * 验证数据合法性
     */
    public static void check(SysUser user){
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new HttpException(400, "缺少参数username");
        }
        if (user.getNick() == null || user.getNick().equals("")) {
            throw new HttpException(400, "缺少参数nick");
        }
        if (user.getRoleId() == null) {
            throw new HttpException(400, "缺少参数roleId");
        }
        // 新增的用户必须要有password
        if (user.getUserId() == null && (user.getPassword() == null || user.getPassword().equals(""))) {
            throw new HttpException(400, "缺少参数password");
        }
        // 校验手机号
        if(!StringUtils.isEmpty(user.getPhone())){
            String regex = "^[1]([3-9])[0-9]{9}$";
            if(user.getPhone().length() != 11 || !Pattern.matches(regex, user.getPhone())){
                throw new HttpException(400, "请输入正确的手机号");
            }
        }
    }

}
