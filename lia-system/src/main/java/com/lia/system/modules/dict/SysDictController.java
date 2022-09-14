package com.lia.system.modules.dict;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;


    /**
     * 获取字典类别列表
     */
    @GetMapping("/typeNameMap")
    @PreAuthorize("hasAuthority('system:dict:typeNameMap')")
    public List<SysDict> typeNameMap(){
        return sysDictService.typeNameMap();
    }


    /**
     * 分页查询字典
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:dict:getPage')")
    public PageInfo<SysDict> getPage(@RequestBody SysDict dict, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysDictService.getSysDict(dict));
    }


    /**
     * 新增和编辑
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:dict:save')")
    public String save(@RequestBody SysDict dict){
        if(dict.getValue() == null || dict.getValue().equals("")){
            throw new HttpException(400,"缺少参数value");
        }
        if(dict.getType() == null || dict.getType().equals("")){
            throw new HttpException(400,"缺少参数type");
        }
        if(dict.getLabel() == null || dict.getLabel().equals("")){
            throw new HttpException(400,"缺少参数label");
        }
        if(dict.getName() == null || dict.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        return sysDictService.saveDict(dict);
    }


    /**
     * 修改字典的类别信息
     * @return
     */
    @PostMapping("/updateDictType")
    @PreAuthorize("hasAuthority('system:dict:updateDictType')")
    public String updateDictType(@RequestBody SysDict dict, String oldType, String oldName){
        if(oldType == null || oldType.equals("")){
            throw new HttpException(400,"缺少参数oldType");
        }
        if(dict.getType() == null || dict.getType().equals("")){
            throw new HttpException(400,"缺少参数type");
        }
        if(dict.getName() == null || dict.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        return sysDictService.updateDictType(dict,oldType, oldName);
    }



    /**
     * 批量删除
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:dict:delete')")
    public int delete(@RequestBody List<Integer> dictIds){
        return sysDictService.deleteDicts(dictIds);
    }


    /**
     * 根据类别删除字典
     */
    @GetMapping("/deleteDictsByType")
    @PreAuthorize("hasAuthority('system:dict:deleteDictsByType')")
    public int deleteDictsByType(String type){
        return sysDictService.deleteDictsByType(type);
    }



    /**
     * 获取sys_dict字典表内字典
     * @param type type值作为查询条件
     */
    @GetMapping("/getSysDict")
    @PreAuthorize("hasAuthority('system:dict:getSysDict')")
    public List<SysDict> getSysDictByType(String type){
        if(type == null || type.equals("")){
            throw new HttpException(400, "缺少参数type");
        }
        SysDict dict = new SysDict();
        dict.setType(type);
        return sysDictService.getSysDict(dict);
    }


    /**
     * 获取角色字典表
     */
    @GetMapping("/sysRoleDict")
    @PreAuthorize("hasAuthority('system:dict:sysRoleDict')")
    public List<SysDict> sysRoleDict(){
        return sysDictService.getSysRoleDict();
    }


    /**
     * 获取用户字典表
     */
    @GetMapping("/sysUserDict")
    @PreAuthorize("hasAuthority('system:dict:sysUserDict')")
    public List<SysDict> sysUserDict(){
        return sysDictService.getSysUserDict();
    }


    /**
     * 获取权限字典表
     */
    @GetMapping("/sysAuthDict")
    @PreAuthorize("hasAuthority('system:dict:sysAuthDict')")
    public List<SysDict> sysAuthDict(){
        return sysDictService.getSysAuthDict();
    }

}
