package com.lia.system.modules.param;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysParamMapper {


    /**
     * 条件查询
     * @param sysParam 查询参数
     * @return 查询结果列表
     */
    List<SysParam> findSysParam(SysParam sysParam);


    /**
     * 新增
     * @param sysParam
     * @return
     */
    int addSysParam(SysParam sysParam);


    /**
     * 编辑
     * @param sysParam
     * @return
     */
    int editSysParam(SysParam sysParam);


    /**
     * 批量删除
     * @param sysParamIds
     * @return
     */
    int deleteSysParams(List<Integer> sysParamIds);


    /**
     * 根据参数名查询参数值
     * @param name 参数名
     * @return 参数值
     */
    Object getParamValueByName(String name);

}
