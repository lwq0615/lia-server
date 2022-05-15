package com.lia.system.service;


import com.lia.system.entity.SysPower;
import com.lia.system.mapper.SysPowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysPowerService {


    @Autowired
    private SysPowerMapper sysPowerMapper;


    /**
     * 根据角色ID查询该角色拥有的权限
     * @param roleId 角色ID
     * @return 权限集合
     */
    public List<SysPower> findSysPowerByRoleId(Integer roleId){
        return sysPowerMapper.findSysPowerByRoleId(roleId);
    }

}
