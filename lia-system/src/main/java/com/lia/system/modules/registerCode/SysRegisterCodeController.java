
package com.lia.system.modules.registerCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysRegisterCode;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/register/code")
public class SysRegisterCodeController {


    @Autowired
    private SysRegisterCodeService sysRegisterCodeService;


    /**
     * 分页查询
     * @param sysRegisterCode 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:register:code:getPage')")
    public PageInfo<SysRegisterCode> getSysRegisterCodePage(@RequestBody SysRegisterCode sysRegisterCode, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysRegisterCodeService.selectList(sysRegisterCode, true));
    }


    /**
     * 批量生成注册码
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('system:register:code:create')")
    public List<SysRegisterCode> create(Integer roleId, Integer count){
        return sysRegisterCodeService.create(roleId, count);
    }


    /**
     * 编辑
     * @param sysRegisterCode 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('system:register:code:edit')")
    public int editSysRegisterCode(@RequestBody SysRegisterCode sysRegisterCode){
        return sysRegisterCodeService.editCodeRole(sysRegisterCode.getId(), sysRegisterCode.getRoleId());
    }



    /**
     * 批量删除
     * @param sysRegisterCodeIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:register:code:delete')")
    public int delete(@RequestBody List<Integer> sysRegisterCodeIds){
        return sysRegisterCodeService.deleteByIds(sysRegisterCodeIds);
    }


}                               
    