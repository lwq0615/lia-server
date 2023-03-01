package com.lia.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lia.system.crud.anno.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_notice")
public class SysNotice {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField("`title`")
    @Like
    @Required
    private String title;

    /**
     * 公告正文
     */
    @TableField("`content`")
    private String content;

    /**
     * 是否置顶(0：否，1：是)
     */
    @TableField("`top_flag`")
    private Character topFlag;

    /**
     * 重要程度(0：普通，1：重要，2：紧急)
     */
    @TableField("`level`")
    @Required
    private Character level;

    /**
     * 推送目标角色
     */
    @Pass
    private List<Integer> publishTo;

    /**
     * 附件列表
     */
    @Pass
    private List<Long> files;

    /**
     * 是否删除(0：否，1：是)
     */
    @TableField("`del_flag`")
    private Character delFlag;

    /**
     * 创建人
     */
    @TableField("`creater`")
    @Creater
    private Long creater;

    /**
     * 创建人名称
     */

    /**
     * 创建时间
     */
    @CreateTime
    @TableField("`create_time`")
    private String createTime;

    /**
     * 更新时间
     */
    @UpdateTime
    @TableField("`update_time`")
    private String updateTime;


    public Character getDelFlag() {
        if(delFlag == null){
            return '0';
        }else{
            return delFlag;
        }
    }
}
