package com.lia.system.controller;


import com.lia.system.annotation.PreAuthorize;
import com.lia.system.aop.HttpException;
import com.lia.system.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/system/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;


    /**
     * 获取sys_dict字典表内字典
     * @param type type值作为查询条件
     */
    @GetMapping("/getSysDict")
    public List<HashMap> getSysDict(String type){
        if(type == null || type.equals("")){
            throw new HttpException(400, "缺少参数type");
        }
        return sysDictService.getSysDict(type);
    }


    /**
     * 获取角色字典表
     * {roleId: name}
     * @return HashMap
     */
    @GetMapping("/sysRoleDict")
    public List<HashMap> sysRoleDict(){
        return sysDictService.getSysRoleDict();
    }

}
