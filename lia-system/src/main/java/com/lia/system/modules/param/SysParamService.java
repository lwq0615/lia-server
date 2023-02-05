package com.lia.system.modules.param;


import com.lia.system.crud.BaseService;
import com.lia.system.crud.exception.UniqueException;
import com.lia.system.entity.SysParam;
import com.lia.system.result.exception.HttpException;
import com.lia.system.result.HttpResult;
import com.lia.system.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SysParamService extends BaseService<SysParam> {

    @Autowired
    private SysParamMapper sysParamMapper;


    /**
     * 新增或编辑系统参数
     */
    public int saveParam(SysParam param){
        try {
            return this.save(param);
        }catch (UniqueException e){
            throw new HttpException(ResultCode.PARAM_NAME_EXISTED);
        }
    }



    /**
     * 获取参数值
     * @param name 参数名
     * @return 参数值
     */
    public Object getParamValueByName(String name) {
        if(name == null || name.equals("")){
            throw new HttpException("缺少参数name");
        }
        SysParam sysParam = sysParamMapper.getParamValueByName(name);
        if(sysParam == null){
            throw new HttpException(ResultCode.PARAM_NOT_EXIST);
        }
        return sysParam.getValue();
    }
}

