package com.lia.system.modules.dict;


import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysDictService {


    @Autowired
    private SysDictMapper sysDictMapper;


    /**
     * 获取字典名称与字典type的映射
     */
    public List<SysDict> typeNameMap(){
        return sysDictMapper.typeNameMap();
    }


    /**
     * 获取字典列表
     */
    public List<SysDict> getSysDict(SysDict dict){
        return sysDictMapper.getSysDict(dict);
    }

    /**
     * 新增或编辑
     * @return
     */
    public String saveDict(SysDict dict){
        int success;
        // 判断相同type下是否有重复的value
        SysDict doDict = new SysDict();
        doDict.setType(dict.getType());
        doDict.setValue(dict.getValue());
        List<SysDict> result = sysDictMapper.getSysDict(doDict);
        if(result != null && result.size() > 0){
            doDict = result.get(0);
        }else{
            doDict = null;
        }
        // 没有id，新增
        if(dict.getDictId() == null){
            if(doDict != null){
                return "同类别下不能有重复的key";
            }
            dict.setCreateBy(LoginUser.getLoginUserId());
            success = sysDictMapper.addSysDict(dict);
        }
        // 编辑
        else{
            if(doDict != null && doDict.getDictId() != dict.getDictId()){
                return "同类别下不能有重复的值";
            }
            success = sysDictMapper.editSysDict(dict);
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 修改字典的类别信息
     */
    public String updateDictType(SysDict dict, String oldType, String oldName){
        SysDict doDict = new SysDict();
        doDict.setType(dict.getType());
        List<SysDict> result = sysDictMapper.getSysDict(doDict);
        // 已经存在的type名
        if(result != null && result.size() > 0 && !result.get(0).getName().equals(oldName)){
            return "类别重复";
        }
        if(sysDictMapper.updateDictType(dict.getType(),dict.getName(),oldType) > 0){
            return "success";
        }else{
            return "error";
        }
    }


    /**
     * 批量删除
     * @return 删除成功的数量
     */
    public int deleteDicts(List<Integer> dictIds){
        if(dictIds.size() == 0){
            return 0;
        }
        return sysDictMapper.deleteDicts(dictIds);
    }


    /**
     * 根据类别删除字典
     */
    public int deleteDictsByType(String type){
        return sysDictMapper.deleteDictsByType(type);
    }


    /**
     * 获取角色字典表
     */
    public List<SysDict> getSysRoleDict(){
        return sysDictMapper.getSysRoleDict();
    }


    /**
     * 获取用户字典表
     */
    public List<SysDict> getSysUserDict() {
        return sysDictMapper.getSysUserDict();
    }
    /**
     * 获取权限字典表
     */
    public List<SysDict> getSysPowerDict() {
        return sysDictMapper.getSysPowerDict();
    }


}
