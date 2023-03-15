package com.lia.system.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
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
    @ExcelIgnore
    private Long userId;

    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    @ColumnWidth(15)
    private String username;

    /**
     * 密码
     */
    @ExcelIgnore
    private String password;

    /**
     * 昵称
     */
    @ExcelProperty("昵称")
    private String nick;

    /**
     * 所属企业
     */
    @ExcelIgnore
    private Integer companyId;

    /**
     * 企业名称
     */
    @ExcelProperty("企业名称")
    @ColumnWidth(15)
    private String companyName;

    /**
     * 角色ID（外键）
     */
    @ExcelIgnore
    private Integer roleId;

    /**
     * 角色名称
     */
    @ExcelProperty("角色名称")
    @ColumnWidth(15)
    private String roleName;

    /**
     * 性别（0男，1女，2其他）
     */
    @ExcelIgnore
    private Character sex;

    /**
     * 性别名称
     */
    @ExcelProperty("性别")
    private String sexName;

    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    @ColumnWidth(15)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    @ColumnWidth(25)
    private String email;

    /**
     * 头像（外键）
     */
    @ExcelIgnore
    private Long headImg;

    /**
     * 帐号状态（0：正常，1：停用）
     */
    @ExcelIgnore
    private Character status;

    /**
     * 状态名称
     */
    @ExcelProperty("帐号状态")
    @ColumnWidth(15)
    private String statusName;

    /**
     * 删除标志（0：存在，1：已删除）
     */
    @ExcelIgnore
    private Character delFlag;

    /**
     * 创建人
     */
    @ExcelIgnore
    private Long creater;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createTime;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    @ColumnWidth(20)
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


    public String getSexName() {
        if(sex == null){
            return null;
        }
        if(sex.equals('0')){
            return "男";
        } else if (sex.equals('1')) {
            return "女";
        }else{
            return "其他";
        }
    }

    public String getStatusName() {
        if(status == null){
            return null;
        }
        if(status.equals('0')){
            return "正常";
        }else{
            return "停用";
        }
    }
}
