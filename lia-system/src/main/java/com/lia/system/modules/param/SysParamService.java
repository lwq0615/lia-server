package com.lia.system.modules.param;


import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysParam;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SysParamService extends BaseService<SysParam> {

    @Autowired
    private SysParamMapper sysParamMapper;

    /**
     * 获取参数值
     * @param name 参数名
     * @return 参数值
     */
    public Object getParamValueByName(String name) {
        if(name == null || name.equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        return sysParamMapper.getParamValueByName(name);
    }
}

