package com.lia.system.modules.dictType;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysDictType;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/system/dictType")
public class SysDictTypeController {


    @Autowired
    private SysDictTypeService sysDictTypeService;


    /**
     * 分页查询
     * @param sysDictType 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:dictType:getPage')")
    public PageInfo<SysDictType> getSysDictTypePage(@RequestBody SysDictType sysDictType, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysDictTypeService.findSysDictType(sysDictType));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysDictType 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:dictType:save')")
    public HttpResult saveSysDictType(@RequestBody SysDictType sysDictType){
        return sysDictTypeService.saveSysDictType(sysDictType);
    }



    /**
     * 批量删除
     * @param sysDictTypeIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:dictType:delete')")
    public int delete(@RequestBody List<Integer> sysDictTypeIds){
        return sysDictTypeService.deleteSysDictTypes(sysDictTypeIds);
    }


}