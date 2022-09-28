package com.lia.system.modules.dictData;

import com.lia.system.modules.dictData.SysDictData;
import com.lia.system.modules.dictData.SysDictDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dictData")
public class SysDictDataController {


    @Autowired
    private SysDictDataService sysDictDataService;


    /**
     * 分页查询
     * @param sysDictData 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:dictData:getPage')")
    public PageInfo<SysDictData> getSysDictDataPage(@RequestBody SysDictData sysDictData, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysDictDataService.findSysDictData(sysDictData));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysDictData 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:dictData:save')")
    public String saveSysDictData(@RequestBody SysDictData sysDictData){
        if(sysDictData.getValue() == null || sysDictData.getValue().equals("")){
            throw new HttpException(400,"缺少参数value");
        }
        if(sysDictData.getLabel() == null || sysDictData.getLabel().equals("")){
            throw new HttpException(400,"缺少参数label");
        }
        if(sysDictData.getTypeId() == null){
            throw new HttpException(400,"缺少参数typeId");
        }
        return sysDictDataService.saveSysDictData(sysDictData);
    }



    /**
     * 批量删除
     * @param sysDictDataIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:dictData:delete')")
    public int delete(@RequestBody List<Integer> sysDictDataIds){
        return sysDictDataService.deleteSysDictDatas(sysDictDataIds);
    }


    /**
     * 获取性别字典表
     */
    @GetMapping("/getSexDict")
    @PreAuthorize("hasAuthority('system:dictData:getSexDict')")
    public List<SysDictData> getSexDict(){
        return sysDictDataService.getDictByKey("sys:sex");
    }


    /**
     * 获取性别字典表
     */
    @GetMapping("/getUserStatusDict")
    @PreAuthorize("hasAuthority('system:dictData:getUserStatusDict')")
    public List<SysDictData> getUserStatusDict(){
        return sysDictDataService.getDictByKey("sys:user:status");
    }


}