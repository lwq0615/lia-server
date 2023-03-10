package com.lia.system.modules.code;

import com.lia.system.result.exception.HttpException;
import com.lia.system.entity.SysToolCode;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysToolCodeService {


    @Autowired
    private SysToolCodeMapper sysToolCodeMapper;


    /**
     * 分页查询
     * @param sysToolCode
     * @return
     */
    public List<SysToolCode> findSysToolCode(SysToolCode sysToolCode) {
        return sysToolCodeMapper.findSysToolCode(sysToolCode);
    }


    /**
     * 新增或编辑
     * @param sysToolCode
     * @return
     */
    public String saveSysToolCode(SysToolCode sysToolCode) {
        if(sysToolCode.getColumns() == null || sysToolCode.getColumns().equals("")){
            throw new HttpException("缺少参数columns");
        }
        if(sysToolCode.getTableName() == null || sysToolCode.getTableName().equals("")){
            throw new HttpException("缺少参数tableName");
        }
        if(sysToolCode.getPrimaryKey() == null || sysToolCode.getPrimaryKey().equals("")){
            throw new HttpException("缺少参数primaryKey");
        }
        int success = 0;
        try {
            if (sysToolCode.getCodeId() == null) {
                // 新增
                sysToolCode.setCreater(LoginUser.getLoginUserId());
                success = sysToolCodeMapper.addSysToolCode(sysToolCode);
            } else {
                // 编辑
                success = sysToolCodeMapper.editSysToolCode(sysToolCode);
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
     *
     * @param sysToolCodeIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysToolCodes(List<Integer> sysToolCodeIds) {
        if (sysToolCodeIds.size() == 0) {
            return 0;
        }
        return sysToolCodeMapper.deleteSysToolCodes(sysToolCodeIds);
    }

}