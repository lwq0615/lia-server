package com.lia.system.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysUser;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.service.SysPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/power")
public class SysPowerController {


    @Autowired
    private SysPowerService sysPowerService;


    /**
     * 分页查询权限列表
     * @param power 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:power:getPage')")
    public PageInfo<SysPower> getSysPowerPage(@RequestBody SysPower power, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysPowerService.findSysPower(power));
    }

    /**
     * 新增和编辑
     * @param power 权限数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:power:save')")
    public String save(@RequestBody SysPower power){
        if(power.getName() == null || power.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        if(power.getUrl() == null || power.getUrl().equals("")){
            throw new HttpException(400,"缺少参数url");
        }
        if(power.getKey() == null || power.getKey().equals("")){
            throw new HttpException(400,"缺少参数key");
        }
        return sysPowerService.savePower(power);
    }



    /**
     * 批量删除
     * @param powerIds 权限的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:power:delete')")
    public int delete(@RequestBody List<Integer> powerIds){
        return sysPowerService.deletePowers(powerIds);
    }


}
