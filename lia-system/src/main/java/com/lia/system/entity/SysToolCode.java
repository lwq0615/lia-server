package com.lia.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 代码生成
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysToolCode {

    /**
     * 代码ID
     */
    private Long codeId;

    /**
     * 表格列信息
     */
    private String columns;

    /**
     * 表格名
     */
    private String tableName;

    /**
     * 主键信息
     */
    private String primaryKey;

    /**
     * 基础接口地址（Controller组件的@RequestMapping注解值）
     */
    private String httpUrl;

    /**
     * 是否添加创建人字段（0：是，1：否）
     */
    private Character createByFlag;

    /**
     * 是否添加创建时间字段（0：是，1：否）
     */
    private Character createTimeFlag;

    /**
     * 是否添加更新时间字段（0：是，1：否）
     */
    private Character updateTimeFlag;

    /**
     * 是否添加备注字段（0：是，1：否）
     */
    private Character remarkFlag;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 是否添加创建人字段set方法
     * createByFlag值只能是'0' || '1'
     */
    public void setCreateByFlag(Character createByFlag) {
        if(!createByFlag.equals('0') && !createByFlag.equals('1')){
            createByFlag = '1';
        }
        this.createByFlag = createByFlag;
    }

    /**
     * 是否添加创建时间字段set方法
     * createTimeFlag值只能是'0' || '1'
     */
    public void setCreateTimeFlag(Character createTimeFlag) {
        if(!createTimeFlag.equals('0') && !createTimeFlag.equals('1')){
            createTimeFlag = '1';
        }
        this.createTimeFlag = createTimeFlag;
    }

    /**
     * 是否添加更新时间字段set方法
     * updateTimeFlag值只能是'0' || '1'
     */
    public void setUpdateTimeFlag(Character updateTimeFlag) {
        if(!updateTimeFlag.equals('0') && !updateTimeFlag.equals('1')){
            updateTimeFlag = '1';
        }
        this.updateTimeFlag = updateTimeFlag;
    }

    /**
     * 是否添加备注字段set方法
     * remarkFlag值只能是'0' || '1'
     */
    public void setRemarkFlag(Character remarkFlag) {
        if(!remarkFlag.equals('0') && !remarkFlag.equals('1')){
            remarkFlag = '1';
        }
        this.remarkFlag = remarkFlag;
    }
}
