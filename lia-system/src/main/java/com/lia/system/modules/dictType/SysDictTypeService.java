package com.lia.system.modules.dictType;

import com.lia.system.entity.SysDictType;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysDictTypeService {


    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;


    /**
     * 分页查询
     * @param sysDictType
     * @return
     */
    public List<SysDictType> findSysDictType(SysDictType sysDictType) {
        return sysDictTypeMapper.findSysDictType(sysDictType);
    }


    /**
     * 新增或编辑
     * @param sysDictType
     * @return
     */
    public int saveSysDictType(SysDictType sysDictType) {
        if(sysDictType.getName() == null || sysDictType.getName().equals("")){
            throw new HttpException("缺少参数name");
        }
        if(sysDictType.getKey() == null || sysDictType.getKey().equals("")){
            throw new HttpException("缺少参数key");
        }
        int success = 0;
        try {
            if (sysDictType.getTypeId() == null) {
                // 新增
                sysDictType.setCreater(LoginUser.getLoginUserId());
                success = sysDictTypeMapper.addSysDictType(sysDictType);
            } else {
                // 编辑
                success = sysDictTypeMapper.editSysDictType(sysDictType);
            }
        } catch (DuplicateKeyException e) {
            throw new HttpException(SysResult.DICTTYPE_KEY_EXISTED);
        }
        return success;
    }


    /**
     * 批量删除
     *
     * @param sysDictTypeIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysDictTypes(List<Integer> sysDictTypeIds) {
        if (sysDictTypeIds.size() == 0) {
            return 0;
        }
        return sysDictTypeMapper.deleteSysDictTypes(sysDictTypeIds);
    }

}