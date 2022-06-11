package com.lia.system.mapper;


import com.lia.system.entity.SysDict;
import com.lia.system.entity.SysPower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPowerMapper {


    /**
     * 查询权限列表
     * @param power 查询参数
     * @return 权限列表
     */
    List<SysPower> findSysPower(SysPower power);


    /**
     * 新增
     * @param power
     * @return
     */
    int addSysPower(SysPower power);


    /**
     * 编辑
     * @param power
     * @return
     */
    int editSysPower(SysPower power);


    /**
     * 批量删除
     * @param powerIds
     * @return
     */
    int deleteSysPowers(List<Integer> powerIds);


    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SysPower> findSysPowerByRoleId(Integer roleId);


    /**
     * 根据角色ID查询权限ID列表
     * @param roleId
     * @return
     */
    List<Integer> findIdsbyRoleId(Integer roleId);

}
