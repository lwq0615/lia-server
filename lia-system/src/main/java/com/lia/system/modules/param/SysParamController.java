package com.lia.system.modules.param;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/param")
public class SysParamController {


    @Autowired
    private SysParamService sysParamService;


    /**
     * 分页查询
     * @param sysParam 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:param:getPage')")
    public PageInfo<SysParam> getSysParamPage(@RequestBody SysParam sysParam, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysParamService.selectList(sysParam));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysParam 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:param:save')")
    public String saveSysParam(@RequestBody SysParam sysParam){
        return sysParamService.save(sysParam);
    }



    /**
     * 批量删除
     * @param sysParamIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:param:delete')")
    public int delete(@RequestBody List<Integer> sysParamIds){
        return sysParamService.deleteByIds(sysParamIds);
    }


    /**
     * 获取参数值
     * @param name 参数名
     * @return 参数值
     */
    @GetMapping("/getParamValue")
    public Object getParamValue(String name){
        return sysParamService.getParamValueByName(name);
    }

}
