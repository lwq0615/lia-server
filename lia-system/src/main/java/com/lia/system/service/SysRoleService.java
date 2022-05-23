package com.lia.system.service;


import com.lia.system.entity.SysRole;
import com.lia.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 查询角色信息
     * @param role 查询参数
     * @return 角色集合
     */
    public List<SysRole> findSysRole(SysRole role){
        return sysRoleMapper.findSysRole(role);
    }

}
