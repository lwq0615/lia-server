
package com.lia.system.modules.registerCode;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.entity.SysRegisterCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRegisterCodeMapper extends BaseMapper<SysRegisterCode> {


    /**
     * 批量生成注册码
     */
    int createRegisterCode(List<SysRegisterCode> codes);


    /**
     * 条件查询
     */
    List<SysRegisterCode> getPage(SysRegisterCode code);

}                                   
    