package com.lia.system.modules.notice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysFile;
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
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:notice:getPage')")
    public PageInfo<SysNotice> getSysNoticePage(Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysNoticeService.selectList());
    }

    /**
     * 新增
     * @param sysNotice 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('system:notice:add')")
    public int addSysNotice(@RequestBody SysNotice sysNotice){
        return sysNoticeService.insert(sysNotice);
    }


    /**
     * 编辑
     * @param sysNotice 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('system:notice:edit')")
    public int editSysNotice(@RequestBody SysNotice sysNotice){
        return sysNoticeService.update(sysNotice);
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

    /**
     * 获取公告相关附件
     * @param noticeId 公告id
     * @return 附件列表
     */
    @PostMapping("/getFilesOfNotice")
    @PreAuthorize("hasAuthority('system:notice:getFilesOfNotice')")
    public List<SysFile> getFilesOfNotice(@RequestBody Long noticeId){
        return sysNoticeService.getFilesOfNotice(noticeId);
    }

}
