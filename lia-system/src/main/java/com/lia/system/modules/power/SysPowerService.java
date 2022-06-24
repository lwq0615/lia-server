package com.lia.system.modules.power;


import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysPowerService {


    @Autowired
    private SysPowerMapper sysPowerMapper;


    /**
     * 根据角色ID查询该角色拥有的权限
     *
     * @param roleId 角色ID
     * @return 权限集合
     */
    public List<SysPower> findSysPowerByRoleId(Integer roleId) {
        return sysPowerMapper.findSysPowerByRoleId(roleId);
    }


    /**
     * 查询权限列表
     *
     * @param power
     * @return
     */
    public List<SysPower> findSysPower(SysPower power) {
        return sysPowerMapper.findSysPower(power);
    }


    /**
     * 新增或编辑
     *
     * @param power
     * @return
     */
    public String savePower(SysPower power) {
        int success = 0;
        try {
            if (power.getPowerId() == null) {
                // 新增的用户
                power.setCreateBy(LoginUser.getLoginUserId());
                success = sysPowerMapper.addSysPower(power);
            } else {
                success = sysPowerMapper.editSysPower(power);
            }
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            switch (name) {
                case "key":
                    return "标识符重复";
                case "url":
                    return "接口路径重复";
            }
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除
     *
     * @param powerIds id列表
     * @return 删除成功的数量
     */
    public int deletePowers(List<Integer> powerIds) {
        if (powerIds.size() == 0) {
            return 0;
        }
        return sysPowerMapper.deleteSysPowers(powerIds);
    }

}
