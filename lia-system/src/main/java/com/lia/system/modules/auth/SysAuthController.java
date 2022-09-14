package com.lia.system.modules.auth;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/auth")
public class SysAuthController {


    @Autowired
    private SysAuthService sysAuthService;


    /**
     * 分页查询权限列表
     * @param auth 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:auth:getPage')")
    public PageInfo<SysAuth> getSysAuthPage(@RequestBody SysAuth auth, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysAuthService.findSysAuth(auth));
    }

    /**
     * 新增和编辑
     * @param auth 权限数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:auth:save')")
    public String save(@RequestBody SysAuth auth){
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
        return sysAuthService.saveAuth(auth);
    }



    /**
     * 批量删除
     * @param authIds 权限的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:auth:delete')")
    public int delete(@RequestBody List<Integer> authIds){
        return sysAuthService.deleteAuths(authIds);
    }


}
