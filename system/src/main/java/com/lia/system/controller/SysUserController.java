package com.lia.system.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.annotation.PreAuthorize;
import com.lia.system.aop.HttpException;
import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.service.SysPowerService;
import com.lia.system.service.SysRoleService;
import com.lia.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPowerService sysPowerService;


    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/getInfo")
    @PreAuthorize(PreAuthorize.Status.login)
    public SysUser getInfo(HttpServletRequest request){
        return (SysUser) request.getAttribute("sysUser");
    }


    /**
     * 用户登录，登录成功后返回加密的token字符串
     * 之后的请求携带token在header中校验身份
     * @param user 用户信息
     * @return token字符串
     */
    @PostMapping("/login")
    @PreAuthorize
    public String sysUserLogin(@RequestBody SysUser user){
        //判断是否合法用户
        if(user.getUsername() == null || user.getUsername().equals("")
                || user.getPassword() == null || user.getPassword().equals("")){
            return "less param";
        }
        user.setStatus('0');
        user.setDelFlag('0');
        List<SysUser> users = sysUserService.findSysUser(user);
        if(users.size() == 0){
            return "login failed";
        }else{
            SysRole role = null;
            List<SysPower> powers = null;
            //查询用户角色
            if(users.get(0).getRoleId() != null){
                SysRole sysRole = new SysRole();
                sysRole.setRoleId(users.get(0).getRoleId());
                List<SysRole> sysRoles = sysRoleService.findSysRole(sysRole);
                if(sysRoles.size() > 0){
                    role = sysRoles.get(0);
                    List<SysPower> sysPowers = sysPowerService.findSysPowerByRoleId(role.getRoleId());
                    if(sysPowers.size() > 0){
                        powers = sysPowers;
                    }
                }
            }
            return sysUserService.getAuthorization(users.get(0),role,powers);
        }
    }


    /**
     * 分页查询用户列表
     * @param user 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    public PageInfo<SysUser> getSysUserPage(@RequestBody SysUser user, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysUserService.findSysUser(user));
    }


    /**
     * 新增和编辑用户
     * @param user 用户数据，每条数据如果有userId则为修改，userId为null则为新增
     * @return 操作成功的数量
     */
    @PostMapping("/saveUser")
    public String saveUser(@RequestBody SysUser user, HttpServletRequest request){
        if(user.getUsername() == null || user.getUsername().equals("")){
            throw new HttpException(400,"缺少参数username");
        }
        if(user.getPassword() == null || user.getPassword().equals("")){
            throw new HttpException(400,"缺少参数password");
        }
        if(user.getNick() == null || user.getNick().equals("")){
            throw new HttpException(400,"缺少参数nick");
        }
        if(user.getRoleId() == null){
            throw new HttpException(400,"缺少参数roleId");
        }
        SysUser loginSysUser = (SysUser) request.getAttribute("sysUser");
        user.setCreateBy(loginSysUser.getUserId());
        return sysUserService.saveUser(user);
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteUsers")
    public int deleteUsers(@RequestBody List<Integer> userIds){
        return sysUserService.deleteUsers(userIds);
    }


}
