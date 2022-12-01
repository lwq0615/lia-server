package com.lia.system.modules.param;


import com.lia.system.crud.BaseService;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class SysParamService {

    private BaseService<SysParam> baseService;

    @Autowired
    private SysParamMapper sysParamMapper;



    @PostConstruct
    public void init(){
        this.baseService = new BaseService<>(sysParamMapper);
    }


    /**
     * 分页查询
     * @param sysParam
     * @return
     */
    public List<SysParam> findSysParam(SysParam sysParam) {
        return baseService.selectList(sysParam);
    }


    /**
     * 新增或编辑
     * @param sysParam
     * @return
     */
    public String saveSysParam(SysParam sysParam) {
        return baseService.save(sysParam);
    }


    /**
     * 批量删除
     * @param sysParamIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysParams(List<Integer> sysParamIds) {
        return baseService.deleteByIds(sysParamIds);
    }

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

