package com.lia.system.modules.role;


import com.lia.system.entity.SysRole;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.entity.SysDictData;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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


    /**
     * 新增或编辑
     * @param role
     * @return
     */
    public int saveRole(SysRole role){
        if(role.getName() == null || role.getName().equals("")){
            throw new HttpException("缺少参数name");
        }
        if(role.getKey() == null || role.getKey().equals("")){
            throw new HttpException("缺少参数key");
        }
        if(role.getRootRouterId() == null){
            throw new HttpException("缺少参数rootRouterId");
        }
        if(role.getCompanyId() == null){
            throw new HttpException("缺少参数companyId");
        }
        int success;
        try{
            if(role.getRoleId() == null){
                // 新增的角色
                role.setCreateBy(LoginUser.getLoginUserId());
                success = sysRoleMapper.addSysRole(role);
            }else{
                success = sysRoleMapper.editSysRole(role);
            }
            this.changeRoleAuths(role.getAuths(), role.getRoleId());
            this.changeRoleRouters(role.getRouters(), role.getRoleId());
        }catch (DuplicateKeyException e){
            throw new HttpException(SysResult.ROLE_KEY_EXISTED);
        }
        return success;
    }


    /**
     * 更新角色路由
     * @param routerIds 路由ID列表
     * @param roleId 角色ID
     */
    public boolean changeRoleRouters(List<Integer> routerIds, Integer roleId){
        sysRoleMapper.deleteRoutersOfRole(roleId);
        if(routerIds != null && routerIds.size() > 0){
            sysRoleMapper.addRoutersToRole(routerIds, roleId);
        }
        return true;
    }


    /**
     * 更新角色权限
     * 更新时清空redis中的角色权限信息，确保请求能获取到最新的权限信息
     * @param authIds 权限ID列表
     * @param roleId 角色ID
     */
    public boolean changeRoleAuths(List<Integer> authIds, Integer roleId){
        sysRoleMapper.deleteAuthsOfRole(roleId);
        if(authIds != null && authIds.size() > 0){
            sysRoleMapper.addAuthsToRole(authIds, roleId);
        }
        Redis.getTemplate(RedisDb.SYSTEM).delete(LoginUser.REDIS_ROLE_AUTHS+roleId);
        return true;
    }


    /**
     * 批量删除
     * @param roleIds 角色的id列表
     * @return 删除成功的数量
     */
    public int deleteRoles(List<Integer> roleIds){
        // 不允许删除开发者角色
        if(roleIds.contains(SysRole.ADMIN_ROLE_ID)){
            roleIds.remove(roleIds.indexOf(SysRole.ADMIN_ROLE_ID));
        }
        // 不允许删除测试角色
        if(roleIds.contains(SysRole.TEST_ROLE_ID)){
            roleIds.remove(roleIds.indexOf(SysRole.TEST_ROLE_ID));
        }
        // 不允许删除普通用户角色
        if(roleIds.contains(SysRole.COMMON_ROLE_ID)){
            roleIds.remove(roleIds.indexOf(SysRole.COMMON_ROLE_ID));
        }
        if(roleIds.size() == 0){
            return 0;
        }
        return sysRoleMapper.deleteRoles(roleIds);
    }

    /**
     * 根据企业ID获取某企业下的角色字典表
     */
    public List<SysDictData> getRoleOfCompanyDict(Integer companyId){
        return sysRoleMapper.getRoleOfCompanyDict(companyId);
    }


    /**
     * 获取角色字典表
     */
    public List<SysDictData> getSysRoleDict(){
        return sysRoleMapper.getSysRoleDict();
    }

}
