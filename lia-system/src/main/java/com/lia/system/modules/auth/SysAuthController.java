package com.lia.system.modules.auth;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysAuth;
import com.lia.system.entity.SysDictData;
import com.lia.system.utils.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    /**
     * 获取权限字典表
     */
    @GetMapping("/sysAuthDict")
    @PreAuthorize("hasAuthority('system:auth:sysAuthDict')")
    public List<SysDictData> sysAuthDict(){
        return sysAuthService.getSysAuthDict();
    }


    /**
     * 批量移动权限到某路由
     */
    @PostMapping("/moveToRouter")
    @PreAuthorize("hasAuthority('system:auth:moveToRouter')")
    public int moveToRouter(Integer[] authIds, Integer routerId){
        return sysAuthService.moveToRouter(ArrayUtils.asList(authIds), routerId);
    }


}
