package com.lia.system.modules.auth;


import com.lia.system.entity.SysAuth;
import com.lia.system.exception.HttpException;
import com.lia.system.entity.SysDictData;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysAuthService {


    @Autowired
    private SysAuthMapper sysAuthMapper;


    /**
     * 根据角色ID查询该角色拥有的权限
     * @param roleId 角色ID
     * @return 权限集合
     */
    public List<SysAuth> findSysAuthByRoleId(Integer roleId) {
        return sysAuthMapper.findSysAuthByRoleId(roleId);
    }


    /**
     * 查询权限列表
     * @param auth
     * @return
     */
    public List<SysAuth> findSysAuth(SysAuth auth) {
        return sysAuthMapper.findSysAuth(auth);
    }


    /**
     * 新增或编辑
     * @param auth
     * @return
     */
    public String saveAuth(SysAuth auth) {
        if(auth.getName() == null || auth.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        if(auth.getUrl() == null || auth.getUrl().equals("")){
            throw new HttpException(400,"缺少参数url");
        }
        if(auth.getKey() == null || auth.getKey().equals("")){
            throw new HttpException(400,"缺少参数key");
        }
        if(auth.getRouterId() == null){
            throw new HttpException(400,"缺少参数routerId");
        }
        int success = 0;
        try {
            if (auth.getAuthId() == null) {
                // 新增的用户
                auth.setCreateBy(LoginUser.getLoginUserId());
                success = sysAuthMapper.addSysAuth(auth);
            } else {
                success = sysAuthMapper.editSysAuth(auth);
            }
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            switch (name) {
                case "key":
                    return "标识符重复";
                case "url":
                    return "接口路径重复";
            }
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除
     * @param authIds id列表
     * @return 删除成功的数量
     */
    public int deleteAuths(List<Integer> authIds) {
        if (authIds.size() == 0) {
            return 0;
        }
        return sysAuthMapper.deleteSysAuths(authIds);
    }

    /**
     * 获取权限字典表
     */
    public List<SysDictData> getSysAuthDict() {
        return sysAuthMapper.getSysAuthDict();
    }

}
