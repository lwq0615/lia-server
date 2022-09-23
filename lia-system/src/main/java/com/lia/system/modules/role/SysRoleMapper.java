package com.lia.system.modules.role;


import com.lia.system.modules.dictData.SysDictData;
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


    /**
     * 新增
     * @param role
     * @return
     */
    int addSysRole(SysRole role);

    /**
     * 编辑
     * @param role
     * @return
     */
    int editSysRole(SysRole role);


    /**
     * 批量删除
     * @param roleIds 角色的id列表
     * @return 删除成功的数量
     */
    int deleteRoles(List<Integer> roleIds);


    /**
     * 删除角色的全部权限
     */
    int deleteAuthsOfRole(Integer roleId);


    /**
     * 给角色添加权限
     */
    int addAuthsToRole(List<Integer> authIds, Integer roleId);


    /**
     * 删除角色的全部路由
     */
    int deleteRoutersOfRole(Integer roleId);


    /**
     * 给角色添加路由
     */
    int addRoutersToRole(List<Integer> routerIds, Integer roleId);

    /**
     * 根据企业ID获取某企业下的角色字典表
     */
    List<SysDictData> getRoleOfCompanyDict(Integer companyId);


    /**
     * 获取角色字典表
     */
    List<SysDictData> getSysRoleDict();


}
