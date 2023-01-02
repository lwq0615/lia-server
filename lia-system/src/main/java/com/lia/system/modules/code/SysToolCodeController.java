package com.lia.system.modules.code;

import com.lia.system.entity.SysToolCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/system/tool/code")
public class SysToolCodeController {


    @Autowired
    private SysToolCodeService sysToolCodeService;


    /**
     * 分页查询
     * @param sysToolCode 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:tool:code:getPage')")
    public PageInfo<SysToolCode> getSysToolCodePage(@RequestBody SysToolCode sysToolCode, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysToolCodeService.findSysToolCode(sysToolCode));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysToolCode 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:tool:code:save')")
    public String saveSysToolCode(@RequestBody SysToolCode sysToolCode){
        return sysToolCodeService.saveSysToolCode(sysToolCode);
    }



    /**
     * 批量删除
     * @param sysToolCodeIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:tool:code:delete')")
    public int delete(@RequestBody List<Integer> sysToolCodeIds){
        return sysToolCodeService.deleteSysToolCodes(sysToolCodeIds);
    }


}