package com.lia.system.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SysDictMapper {


    /**
     * 获取角色字典表
     * {roleId: name}
     * @return HashMap
     */
    List<HashMap> getSysRoleDict();


    /**
     * 获取sys_dict字典表内的字典
     * @param type 字典类型
     */
    List<HashMap> getSysDict(String type);

}
