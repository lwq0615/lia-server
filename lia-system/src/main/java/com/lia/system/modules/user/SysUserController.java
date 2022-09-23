package com.lia.system.modules.user;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.modules.dictData.SysDictData;
import com.lia.system.modules.file.SysFile;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.modules.file.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFileService sysFileService;


    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/getInfo")
    public SysUser getInfo(){
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        return sysUserService.findSysUser(user).get(0);
    }


    /**
     * 获取用户头像
     * @return
     */
    @GetMapping("/getHeadImg")
    public String getHeadImg(){
        SysUser user = this.getInfo();
        if(user.getHeadImg() == null){
            return null;
        }
        SysFile file = new SysFile();
        file.setFileId(user.getHeadImg());
        ArrayList<SysFile> sysFile = sysFileService.getSysFile(file);
        if(sysFile != null && sysFile.size() > 0){
            return sysFile.get(0).getPath();
        }else{
            return null;
        }
    }


    /**
     * 更新用户头像
     * @param file
     * @return
     */
    @PostMapping("/updateHeadImg")
    public SysFile updateHeadImg(MultipartFile file, String fileId){
        Long id = null;
        try {
            id = Long.parseLong(fileId);
        }catch (NumberFormatException e){}
        return sysUserService.updateHeadImg(file, id);
    }



    /**
     * 用户登录，登录成功后返回加密的token字符串
     * 之后的请求携带token在header中校验身份
     * @param user 用户信息
     * @return token字符串
     */
    @PostMapping("/login")
    public String sysUserLogin(@RequestBody SysUser user){
        //判断是否合法用户
        if(user.getUsername() == null || user.getUsername().equals("")
                || user.getPassword() == null || user.getPassword().equals("")){
            return "less param";
        }
        return sysUserService.getAuthorization(user);
    }


    /**
     * 分页查询用户列表
     * @param user 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:user:getPage')")
    public PageInfo<SysUser> getSysUserPage(@RequestBody SysUser user, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysUserService.findSysUser(user));
    }


    /**
     * 新增和编辑用户
     * @param user 用户数据，每条数据如果有userId则为修改，userId为null则为新增
     * @return 操作成功的数量
     */
    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('system:user:saveUser')")
    public String saveUser(@RequestBody SysUser user){
        if(user.getUsername() == null || user.getUsername().equals("")){
            throw new HttpException(400,"缺少参数username");
        }
        if(user.getNick() == null || user.getNick().equals("")){
            throw new HttpException(400,"缺少参数nick");
        }
        if(user.getRoleId() == null){
            throw new HttpException(400,"缺少参数roleId");
        }
        // 新增的用户必须要有password
        if(user.getUserId() == null && (user.getPassword() == null || user.getPassword().equals(""))){
            throw new HttpException(400,"缺少参数password");
        }
        return sysUserService.saveUser(user);
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteUsers")
    @PreAuthorize("hasAuthority('system:user:deleteUsers')")
    public int deleteUsers(@RequestBody List<Integer> userIds){
        return sysUserService.deleteUsers(userIds);
    }


    /**
     * 获取创建人字典表
     */
    @GetMapping("/getCreateByDict")
    @PreAuthorize("hasAuthority('system:user:getCreateByDict')")
    public List<SysDictData> createByDict(){
        return sysUserService.getCreateByDict();
    }

}
