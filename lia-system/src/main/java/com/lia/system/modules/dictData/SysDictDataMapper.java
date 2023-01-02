package com.lia.system.modules.dictData;

import com.lia.system.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysDictDataMapper {


    /**
     * 条件查询
     * @param sysDictData 查询参数
     * @return 查询结果列表
     */
    List<SysDictData> findSysDictData(SysDictData sysDictData);


    /**
     * 新增
     * @param sysDictData
     * @return
     */
    int addSysDictData(SysDictData sysDictData);


    /**
     * 编辑
     * @param sysDictData
     * @return
     */
    int editSysDictData(SysDictData sysDictData);


    /**
     * 批量删除
     * @param sysDictDataIds
     * @return
     */
    int deleteSysDictDatas(List<Integer> sysDictDataIds);


    /**
     * 根据key获取字典数据
     */
    List<SysDictData> getDictByKey(String key);

}
