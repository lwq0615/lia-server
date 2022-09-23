package com.lia.system.modules.dictData;

import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysDictDataService {


    @Autowired
    private SysDictDataMapper sysDictDataMapper;


    /**
     * 分页查询
     * @param sysDictData
     * @return
     */
    public List<SysDictData> findSysDictData(SysDictData sysDictData) {
        return sysDictDataMapper.findSysDictData(sysDictData);
    }


    /**
     * 新增或编辑
     *
     * @param sysDictData
     * @return
     */
    public String saveSysDictData(SysDictData sysDictData) {
        int success = 0;
        try {
            if (sysDictData.getDataId() == null) {
                // 新增
                sysDictData.setCreateBy(LoginUser.getLoginUserId());
                success = sysDictDataMapper.addSysDictData(sysDictData);
            } else {
                // 编辑
                success = sysDictDataMapper.editSysDictData(sysDictData);
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
     * @param sysDictDataIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysDictDatas(List<Integer> sysDictDataIds) {
        if (sysDictDataIds.size() == 0) {
            return 0;
        }
        return sysDictDataMapper.deleteSysDictDatas(sysDictDataIds);
    }


    /**
     * 获取性别字典表
     */
    public List<SysDictData> getSexDict(){
        return sysDictDataMapper.getSexDict();
    }

}

