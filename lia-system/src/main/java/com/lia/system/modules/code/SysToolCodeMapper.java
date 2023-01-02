package com.lia.system.modules.code;

import com.lia.system.entity.SysToolCode;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysToolCodeMapper {


    /**
     * 条件查询
     * @param sysToolCode 查询参数
     * @return 查询结果列表
     */
    List<SysToolCode> findSysToolCode(SysToolCode sysToolCode);


    /**
     * 新增
     * @param sysToolCode
     * @return
     */
    int addSysToolCode(SysToolCode sysToolCode);


    /**
     * 编辑
     * @param sysToolCode
     * @return
     */
    int editSysToolCode(SysToolCode sysToolCode);


    /**
     * 批量删除
     * @param sysToolCodeIds
     * @return
     */
    int deleteSysToolCodes(List<Integer> sysToolCodeIds);

}
