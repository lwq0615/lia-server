package com.lia.system.modules.dict;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDictMapper {


    /**
     * 获取字典名称与字典type的映射
     */
    List<SysDict> typeNameMap();


    /**
     * 获取字典列表
     * @return
     */
    List<SysDict> getSysDict(SysDict dict);


    /**
     * 新增
     * @return
     */
    int addSysDict(SysDict dict);

    /**
     * 编辑
     * @return
     */
    int editSysDict(SysDict dict);


    /**
     * 修改字典的类别信息
     */
    int updateDictType(String type,String name, String oldType);


    /**
     * 批量删除
     * @return 删除成功的数量
     */
    int deleteDicts(List<Integer> dictIds);


    /**
     * 根据类别删除字典
     */
    int deleteDictsByType(String type);


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
