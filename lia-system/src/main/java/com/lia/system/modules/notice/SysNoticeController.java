package com.lia.system.modules.notice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysNotice;
import com.lia.system.result.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/system/notice")
public class SysNoticeController {


    @Autowired
    private SysNoticeService sysNoticeService;


    /**
     * 分页查询
     * @param sysNotice 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:notice:getPage')")
    public PageInfo<SysNotice> getSysNoticePage(@RequestBody SysNotice sysNotice, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysNoticeService.selectList(sysNotice, true));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysNotice 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:notice:save')")
    public int saveSysNotice(@RequestBody SysNotice sysNotice){
        return sysNoticeService.save(sysNotice);
    }



    /**
     * 批量删除
     * @param sysNoticeIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:notice:delete')")
    public int delete(@RequestBody List<Integer> sysNoticeIds){
        return sysNoticeService.deleteByIds(sysNoticeIds);
    }


}
