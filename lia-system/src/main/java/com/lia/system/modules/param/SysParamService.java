package com.lia.system.modules.param;


import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SysParamService {


    @Autowired
    private SysParamMapper sysParamMapper;


    /**
     * 分页查询
     * @param sysParam
     * @return
     */
    public List<SysParam> findSysParam(SysParam sysParam) {
        return sysParamMapper.findSysParam(sysParam);
    }


    /**
     * 新增或编辑
     * @param sysParam
     * @return
     */
    public String saveSysParam(SysParam sysParam) {
        if(sysParam.getName() == null || sysParam.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        int success = 0;
        try {
            if (sysParam.getParamId() == null) {
                // 新增
                sysParam.setCreateBy(LoginUser.getLoginUserId());
                success = sysParamMapper.addSysParam(sysParam);
            } else {
                // 编辑
                success = sysParamMapper.editSysParam(sysParam);
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
     * @param sysParamIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysParams(List<Integer> sysParamIds) {
        if (sysParamIds.size() == 0) {
            return 0;
        }
        return sysParamMapper.deleteSysParams(sysParamIds);
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

