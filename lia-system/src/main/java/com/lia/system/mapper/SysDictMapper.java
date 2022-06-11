package com.lia.system.mapper;


import com.lia.system.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SysDictMapper {


    /**
     * 获取sys_dict字典表内的字典
     * @param type 字典类型
     */
    List<SysDict> getSysDict(String type);


    /**
     * 获取角色字典表
     */
    List<SysDict> getSysRoleDict();


    /**
     * 获取用户字典表
     */
    List<SysDict> getSysUserDict();


    /**
     * 获取权限字典表
     */
    List<SysDict> getSysPowerDict();

}
