package com.lia.system.modules.param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.entity.SysParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysParamMapper extends BaseMapper<SysParam> {

    /**
     * 根据参数名查询参数值
     * @param name 参数名
     * @return 参数值
     */
    SysParam getParamValueByName(String name);

}
