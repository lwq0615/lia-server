package com.lia.system.mapper;


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
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SysPower> findSysPowerByRoleId(Integer roleId);

}
