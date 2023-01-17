package com.lia.system.entity;


import com.lia.system.utils.ArrayUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUser {

    /**
     * 管理员用户ID
     */
    public final static Long ADMIN_USER_ID = 1L;

    /**
     * 测试用户ID
     */
    public final static Long TEST_USER_ID = 2L;


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


}
