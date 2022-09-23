package com.lia.system.modules.dictType;

import com.lia.system.security.LoginUser;
import com.lia.system.utils.SnowflakeId;
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
     *
     * @param sysDictType
     * @return
     */
    public String saveSysDictType(SysDictType sysDictType) {
        int success = 0;
        try {
            if (sysDictType.getTypeId() == null) {
                // 新增
                success = sysDictTypeMapper.addSysDictType(sysDictType);
            } else {
                // 编辑
                success = sysDictTypeMapper.editSysDictType(sysDictType);
            }
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            return name + "重复";
        }
        return success > 0 ? "success" : "error";
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