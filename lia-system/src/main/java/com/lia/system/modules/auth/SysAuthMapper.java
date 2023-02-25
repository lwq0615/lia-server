package com.lia.system.modules.auth;


import com.lia.system.entity.SysAuth;
import com.lia.system.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysAuthMapper {


    /**
     * 查询权限列表
     * @param auth 查询参数
     * @return 权限列表
     */
    List<SysAuth> findSysAuth(SysAuth auth);


    /**
     * 新增
     * @param auth
     * @return
     */
    int addSysAuth(SysAuth auth);


    /**
     * 编辑
     * @param auth
     * @return
     */
    int editSysAuth(SysAuth auth);


    /**
     * 批量删除
     * @param authIds
     * @return
     */
    int deleteSysAuths(List<Integer> authIds);


    /**
     * 根据角色ID查询权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<SysAuth> findSysAuthByRoleId(Integer roleId);


    /**
     * 根据角色ID查询权限ID列表
     * @param roleId
     * @return
     */
    List<Integer> findIdsbyRoleId(Integer roleId);


    /**
     * 获取权限字典表
     */
    List<SysDictData> getSysAuthDict();


    /**
     * 批量移动权限到某路由
     */
    int moveToRouter(List<Integer> authIds, Integer routerId);


    /**
     * 查询用户是否有某个key的权限
     */
    List<Integer> hasAuth(String key, Long userId);

}
