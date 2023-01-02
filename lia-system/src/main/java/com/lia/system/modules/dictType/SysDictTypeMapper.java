package com.lia.system.modules.dictType;

import com.lia.system.entity.SysDictType;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysDictTypeMapper {


    /**
     * 条件查询
     * @param sysDictType 查询参数
     * @return 查询结果列表
     */
    List<SysDictType> findSysDictType(SysDictType sysDictType);


    /**
     * 新增
     * @param sysDictType
     * @return
     */
    int addSysDictType(SysDictType sysDictType);


    /**
     * 编辑
     * @param sysDictType
     * @return
     */
    int editSysDictType(SysDictType sysDictType);


    /**
     * 批量删除
     * @param sysDictTypeIds
     * @return
     */
    int deleteSysDictTypes(List<Integer> sysDictTypeIds);

}