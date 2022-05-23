package com.lia.system.mapper;


import com.lia.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    /**
     * 查询角色列表
     * @param role 查询的参数信息
     * @return 角色列表
     */
    List<SysRole> findSysRole(SysRole role);

}
