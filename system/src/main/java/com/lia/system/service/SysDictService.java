package com.lia.system.service;


import com.lia.system.mapper.SysDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SysDictService {


    @Autowired
    private SysDictMapper sysDictMapper;


    /**
     * 获取sys_dict字典表内的字典
     * @param type 字典类型
     */
    public List<HashMap> getSysDict(String type){
        return sysDictMapper.getSysDict(type);
    }


    /**
     * 获取角色字典表
     */
    public List<HashMap> getSysRoleDict(){
        return sysDictMapper.getSysRoleDict();
    }


    /**
     * 获取用户字典表
     */
    public List<HashMap> getSysUserDict() {
        return sysDictMapper.getSysUserDict();
    }


}
