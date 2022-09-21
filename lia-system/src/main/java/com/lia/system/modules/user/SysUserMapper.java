package com.lia.system.modules.user;

import com.lia.system.modules.dict.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysUserMapper {


    SysUser getOneSysUser(SysUser user);

    /**
     * 查询用户列表
     * @param user 查询的参数信息
     * @return 用户列表
     */
    List<SysUser> getSysUserPage(SysUser user);


    /**
     * 新增用户
     * @param user
     * @return
     */
    int addSysUser(SysUser user);

    /**
     * 新编辑户
     * @param user
     * @return
     */
    int editSysUser(SysUser user);


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    int deleteUsers(List<Integer> userIds);


    /**
     * 获取创建人字典表
     */
    List<SysDict> getCreateByDict();

}
